package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class ReturnCommand extends ExecutableCommand{
    private ArithmeticCommand _internalCommand;

    public ReturnCommand(ArrayList<String> arguments) {
        super(arguments);
        _internalCommand = new ArithmeticCommand(arguments);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        return _internalCommand.Execute(sharedMemory, sharedBind, dataServers, clients, lock);
    }
}
