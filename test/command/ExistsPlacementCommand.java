package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class ExistsPlacementCommand extends PlacementCommand {
    public ExistsPlacementCommand(String assignedTo, ExecutableCommand executableCommand) {
        super(assignedTo, executableCommand);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        lock.lock();
        try {
            double returnedValue = _executableCommand.Execute(sharedMemory, sharedBind, dataServers, clients, lock);
            if (sharedBind.containsKey(_assignedTo) && clients.size() > 0) {
                sharedBind.entrySet().forEach(entry -> {
                    if (entry.getValue().equals(sharedBind.get(_assignedTo)))
                    {
                        sharedMemory.replace(entry.getKey(), returnedValue);
                    }
                });
                Client client = clients.get(0);
                client.SetVariable(sharedBind.get(_assignedTo), returnedValue);
            }
            sharedMemory.replace(_assignedTo, returnedValue);
        } finally {
            lock.unlock();
        }
        return 0;
    }
}
