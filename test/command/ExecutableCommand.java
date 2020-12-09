package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public abstract class ExecutableCommand implements ICommand {
    protected ArrayList<String> _arguments;

    public ExecutableCommand(ArrayList<String> arguments)
    {
        _arguments = arguments;
    }

    public abstract double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock);
}
