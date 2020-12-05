package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExecutableCommand implements ICommand {
    protected ArrayList<String> _arguments;

    public ExecutableCommand(ArrayList<String> arguments)
    {
        _arguments = arguments;
    }

    public abstract int Execute(HashMap<String, Integer> sharedMemory);
}
