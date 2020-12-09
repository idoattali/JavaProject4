package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class NewPlacementCommand extends PlacementCommand {
    public NewPlacementCommand(String assignedTo, ExecutableCommand executableCommand) {
        super(assignedTo, executableCommand);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        lock.lock();
        try {
            double returnedValue = _executableCommand.Execute(sharedMemory, sharedBind, dataServers, clients, lock);
            sharedMemory.put(_assignedTo, returnedValue);
        } finally {
            lock.unlock();
        }
        return 0;
    }
}
