package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public class Connect extends ExecutableCommand {

    public Connect(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        System.out.println("Connect");
        for (int i=0; i<_arguments.size(); i++)
        {
            System.out.println(_arguments.get(i));
        }
        return 0;
    }
}