package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public abstract class PlacementCommand implements ICommand {
    protected String _assignedTo;
    protected ExecutableCommand _executableCommand;

    public PlacementCommand(String assignedTo, ExecutableCommand executableCommand)
    {
        _assignedTo = assignedTo;
        _executableCommand = executableCommand;
    }

    public abstract double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock);
}
