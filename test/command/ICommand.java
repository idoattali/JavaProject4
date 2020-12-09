package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public interface ICommand {
    double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock);
}
