package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class DisconnectCommand extends ExecutableCommand{
    public DisconnectCommand(ArrayList<String> arguments) {
        super(arguments);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        if (clients.size() > 0)
        {
            Client client = clients.get(0);
            client.Disconnect();
        }
        return 0;
    }
}
