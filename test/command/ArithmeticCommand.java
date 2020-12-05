package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public class ArithmeticCommand extends ExecutableCommand{
    public ArithmeticCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        return 0;
    }
}
