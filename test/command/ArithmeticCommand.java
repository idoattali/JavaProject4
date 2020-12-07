package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public class ArithmeticCommand extends ExecutableCommand{
    public ArithmeticCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    // x = { "4+5/7+4" }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        // create a full string of arithmetic line
        // remove white spaces
        // replace with sharedMemory

        // convert to relevant structure
        // run with existed code

        return 0;
    }
}
