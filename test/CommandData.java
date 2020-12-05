package test;

import java.util.ArrayList;

public class CommandData {
    private String methodName;
    private ArrayList<String> arguments;

    public CommandData(String line)
    {
        String[] splittedLine = line.split(" ");
        arguments = new ArrayList<>();
        methodName = splittedLine[0];

        for (int i=1; i<splittedLine.length; i++)
        {
            arguments.add(splittedLine[i]);
        }
    }

    public String GetMethodName()
    {
        return methodName;
    }

    public ArrayList<String> GetArguments()
    {
        return arguments;
    }
}
