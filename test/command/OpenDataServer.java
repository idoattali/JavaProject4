package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public class OpenDataServer extends ExecutableCommand {
    public OpenDataServer(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        System.out.println("OpenDataServer");
        for (int i=0; i<_arguments.size(); i++)
        {
            System.out.println(_arguments.get(i));
        }
        return 0;
    }
}
