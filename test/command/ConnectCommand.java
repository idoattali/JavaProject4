package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class ConnectCommand extends ExecutableCommand {

    public ConnectCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        if (clients.size() == 0)
        {
            Client newClient = new Client(_arguments.get(0), Integer.parseInt(_arguments.get(1)), lock);
            clients.add(newClient);
        }
        return 0;
    }
}