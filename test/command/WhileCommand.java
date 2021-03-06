package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class WhileCommand extends ContainerCommand{
    public WhileCommand(ConditionalCommand condition) {
        super(condition);
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        while (_condition.Execute(sharedMemory, sharedBind, dataServers, clients, lock) == 1)
        {
            _commands.forEach(command -> command.Execute(sharedMemory, sharedBind, dataServers, clients, lock));
        }
        return 0;
    }
}
