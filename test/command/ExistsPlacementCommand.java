package test.command;

import java.util.HashMap;

public class ExistsPlacementCommand extends PlacementCommand {
    public ExistsPlacementCommand(String assignedTo, ExecutableCommand executableCommand) {
        super(assignedTo, executableCommand);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        int returnedValue = _executableCommand.Execute(sharedMemory);
        sharedMemory.replace(_assignedTo, returnedValue);
        return 0;
    }
}
