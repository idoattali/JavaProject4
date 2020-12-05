package test;

import test.command.*;

import java.util.*;
import java.util.function.Function;

public class LinesScanner {
    private String[] _lines;
    private HashMap<String, Function<ArrayList<String>, ExecutableCommand>> _knownExecutableCommands;
    private HashMap<String, Function<ArrayList<String>, ExecutableCommand>> _knownPlacementCommands;
    private HashSet<String> _knownConditionalCommands;

    private void InitializeKnownExecutableCommands()
    {
        _knownExecutableCommands = new HashMap<>();
        _knownExecutableCommands.put("connect", (args) -> new Connect(args));
        _knownExecutableCommands.put("openDataServer", (args) -> new OpenDataServer(args));
    }

    private void InitializeKnownPlacementCommands()
    {
        _knownPlacementCommands = new HashMap<>();
        _knownPlacementCommands.put("bind", (args) -> new BindCommand(args));
    }

    private void InitializeKnownConditionalCommands()
    {
        _knownConditionalCommands = new HashSet<>();
        _knownConditionalCommands.add("while");
    }

    public LinesScanner(String[] lines)
    {
        _lines = lines;
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

    private ExecutableCommand GetPlacementExecutableCommand(ArrayList<String> commandArgs)
    {
        if (_knownPlacementCommands.keySet().contains(commandArgs.get(0)))
        {
            String commandType = commandArgs.remove(0);
            return _knownPlacementCommands.get(commandType).apply(commandArgs);
        }
        return new ArithmeticCommand(commandArgs);
    }

    private ICommand GetPlacementCommand(String[] splittedLine)
    {
        String firstWord = splittedLine[0];
        ArrayList<String> internalCommandData = new ArrayList<>();

        String assignedTo = firstWord.equals("var") ? splittedLine[1] : splittedLine[0];
        int startIndex = firstWord.equals("var") ? 3 : 2;

        for (int i=startIndex; i < splittedLine.length; i++)
        {
            internalCommandData.add(splittedLine[i]);
        }

        ExecutableCommand internalCommand = GetPlacementExecutableCommand(internalCommandData);
        return firstWord.equals("var")
                ? new NewPlacementCommand(assignedTo, internalCommand)
                : new ExistsPlacementCommand(assignedTo, internalCommand);
    }

    public ArrayList<ICommand> GetCommands()
    {
        ArrayList<ICommand> commands = new ArrayList<>();
        int currentLine = 0;
        String[] currentSplittedLine;
        String currentFirstWord;

        while (currentLine < _lines.length)
        {
            currentSplittedLine = _lines[currentLine].split(" ");
            currentFirstWord = currentSplittedLine[0];
            ICommand command;

            if (_knownExecutableCommands.keySet().contains(currentFirstWord))
            {
                commands.add(GetExecutableCommand(currentSplittedLine));
                currentLine++;
            }
            else if (_knownConditionalCommands.contains(currentFirstWord))
            {
                currentLine++;
            }
            else
            {
                commands.add(GetPlacementCommand(currentSplittedLine));
                currentLine++;
            }
        }

        return commands;
    }
}
