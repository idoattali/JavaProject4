package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlacementCommand implements ICommand {
    protected String _assignedTo;
    protected ExecutableCommand _executableCommand;

    public PlacementCommand(String assignedTo, ExecutableCommand executableCommand)
    {
        _assignedTo = assignedTo;
        _executableCommand = executableCommand;
    }

    public abstract int Execute(HashMap<String, Integer> sharedMemory);
}
