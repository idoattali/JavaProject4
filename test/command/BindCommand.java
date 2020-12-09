package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class BindCommand extends ExecutableCommand{

    private String _assignedTo;
    private String _bindTo;

    public BindCommand(ArrayList<String> arguments) {
        super(arguments);
        _assignedTo = arguments.get(0);
        _bindTo = arguments.get(1);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        if (dataServers.size() > 0)
        {
            DataServer dataServer = dataServers.get(0);
            sharedBind.put(_assignedTo, _bindTo);
            return dataServer.GetData(_bindTo);
        }
        return 0;
    }
}
