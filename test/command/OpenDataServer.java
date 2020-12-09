package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OpenDataServer extends ExecutableCommand {
    public OpenDataServer(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        if (dataServers.size() == 0)
        {
            DataServer newDataServer = new DataServer(
                    sharedMemory,
                    sharedBind,
                    lock,
                    Integer.parseInt(_arguments.get(0)),
                    Integer.parseInt(_arguments.get(1))
            );
            newDataServer.WaitForInitialize();
            dataServers.add(newDataServer);
        }
        return 0;
    }
}
