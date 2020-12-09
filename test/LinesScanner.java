package test;

import test.command.*;

import java.util.*;
import java.util.function.Function;

public class LinesScanner {
    private ArrayList<String> _lines;
    private HashMap<String, Function<ArrayList<String>, ExecutableCommand>> _knownExecutableCommands;
    private HashMap<String, Function<ArrayList<String>, ExecutableCommand>> _knownPlacementCommands;
    private HashMap<String, Function<ConditionalCommand, ContainerCommand>> _knownContainerCommands;

    private void InitializeKnownExecutableCommands()
    {
        _knownExecutableCommands = new HashMap<>();
        _knownExecutableCommands.put("connect", (args) -> new ConnectCommand(args));
        _knownExecutableCommands.put("openDataServer", (args) -> new OpenDataServer(args));
        _knownExecutableCommands.put("return", (args) -> new ReturnCommand(args));
        _knownExecutableCommands.put("disconnect", (args) -> new DisconnectCommand(args));
    }

    private void InitializeKnownPlacementCommands()
    {
        _knownPlacementCommands = new HashMap<>();
        _knownPlacementCommands.put(
                "bind",
                (args) -> new BindCommand(args)
        );
    }

    private void InitializeKnownConditionalCommands()
    {
        _knownContainerCommands = new HashMap<>();
        _knownContainerCommands.put("while", (args) -> new WhileCommand(args));
    }

    public LinesScanner(String[] lines)
    {
        _lines = new ArrayList<>(Arrays.asList(lines));
        InitializeKnownExecutableCommands();
        InitializeKnownConditionalCommands();
        InitializeKnownPlacementCommands();
    }

    private ICommand GetExecutableCommand(String[] splittedLine)
    {
        String firstWord = splittedLine[0];
        ArrayList<String> arguments = new ArrayList<>();
        for (int i = 1; i < splittedLine.length; i++)
        {
            arguments.add(splittedLine[i]);
        }

        Function<ArrayList<String>, ExecutableCommand> func = _knownExecutableCommands.get(firstWord);
        return func.apply(arguments);
    }

    private ExecutableCommand GetPlacementExecutableCommand(String assignedTo, ArrayList<String> commandArgs)
    {
        if (_knownPlacementCommands.keySet().contains(commandArgs.get(0)))
        {
            String commandType = commandArgs.remove(0);
            commandArgs.add(0, assignedTo);
            return _knownPlacementCommands.get(commandType).apply(commandArgs);
        }
        return new ArithmeticCommand(commandArgs);
    }

    private ICommand GetPlacementCommand(String[] splittedLine)
    {
        String firstWord = splittedLine[0];
        ArrayList<String> internalPlacementData = new ArrayList<>();
        ArrayList<String> internalCommandData = new ArrayList<>();

        int startIndex = firstWord.equals("var") ? 1 : 0;

        for (int i=startIndex; i < splittedLine.length; i++)
        {
            internalPlacementData.add(splittedLine[i]);
        }

        String internalPlacementString = String.join(" ", internalPlacementData);
        String[] internalPlacementSplitted = internalPlacementString.split("=");
        String assignedTo = internalPlacementSplitted[0].strip();

        if (internalPlacementSplitted.length > 1)
        {
            internalCommandData.addAll(Arrays.asList(internalPlacementSplitted[1].strip().split(" ")));
        }
        else
        {
            internalCommandData.add("0");
        }

        ExecutableCommand internalCommand = GetPlacementExecutableCommand(assignedTo, internalCommandData);
        return firstWord.equals("var")
                ? new NewPlacementCommand(assignedTo, internalCommand)
                : new ExistsPlacementCommand(assignedTo, internalCommand);
    }

    private ContainerCommand GetContainerCommand(String firstWord, String line)
    {
        String conditionString = line.split(firstWord)[1].strip();
        conditionString = conditionString.split("\\{")[0];
        ConditionTypes type;
        String leftString, rightString;
        if (conditionString.contains("==")) {
            type = ConditionTypes.EQ;
            leftString = conditionString.split("==")[0].strip();
            rightString = conditionString.split("==")[1].strip();
        }
        else if (conditionString.contains("!=")) {
            type = ConditionTypes.NE;
            leftString = conditionString.split("!=")[0].strip();
            rightString = conditionString.split("!=")[1].strip();
        }
        else if (conditionString.contains(">=")) {
            type = ConditionTypes.GE;
            leftString = conditionString.split(">=")[0].strip();
            rightString = conditionString.split(">=")[1].strip();
        }
        else if (conditionString.contains("<=")) {
            type = ConditionTypes.LE;
            leftString = conditionString.split("<=")[0].strip();
            rightString = conditionString.split("<=")[1].strip();
        }
        else if (conditionString.contains(">")) {
            type = ConditionTypes.GT;
            leftString = conditionString.split(">")[0].strip();
            rightString = conditionString.split(">")[1].strip();
        }
        else {
            type = ConditionTypes.LT;
            leftString = conditionString.split("<")[0].strip();
            rightString = conditionString.split("<")[1].strip();
        }

        return _knownContainerCommands.get(firstWord).apply(
            new ConditionalCommand(
                type,
                new ArithmeticCommand(new ArrayList<>(Arrays.asList(leftString.split(" ")))),
                new ArithmeticCommand(new ArrayList<>(Arrays.asList(rightString.split(" "))))
            )
        );
    }

    public ArrayList<ICommand> GetCommands()
    {
        ArrayList<String> lines = (ArrayList<String>) _lines.clone();
        return GetCommandsRecursive(lines);
    }

    private ArrayList<ICommand> GetCommandsRecursive(ArrayList<String> lines)
    {
        ArrayList<ICommand> commands = new ArrayList<>();
        int currentLine = 0;
        String[] currentSplittedLine;
        String currentFirstWord;

        while (lines.size() > 0 && !lines.get(0).equals("}"))
        {
            currentSplittedLine = lines.get(currentLine).strip().split(" ");
            currentFirstWord = currentSplittedLine[0];
            ICommand command;

            if (_knownExecutableCommands.keySet().contains(currentFirstWord))
            {
                commands.add(GetExecutableCommand(currentSplittedLine));
                lines.remove(0);
            }
            else if (_knownContainerCommands.keySet().contains(currentFirstWord))
            {
                ContainerCommand containerCommand = GetContainerCommand(currentFirstWord, lines.get(currentLine));
                lines.remove(0);
                ArrayList<ICommand> internalCommands = GetCommandsRecursive(lines);
                internalCommands.forEach((internalCommand) -> containerCommand.AddCommand(internalCommand));
                commands.add(containerCommand);
                lines.remove(0);
            }
            else
            {
                commands.add(GetPlacementCommand(currentSplittedLine));
                lines.remove(0);
            }
        }

        return commands;
    }
}
