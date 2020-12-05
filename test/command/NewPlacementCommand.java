package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public class NewPlacementCommand extends PlacementCommand {
    public NewPlacementCommand(String assignedTo, ExecutableCommand executableCommand) {
        super(assignedTo, executableCommand);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        int returnedValue = _executableCommand.Execute(sharedMemory);
        sharedMemory.put(_assignedTo, returnedValue);
        return 0;
    }
}
