package test;

import test.command.ICommand;
import test.command.ReturnCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyInterpreter {

	public static int interpret(String[] lines){
		// scan the given lines into relevant structure for interpreter
		LinesScanner scanner = new LinesScanner(lines);
		ArrayList<ICommand> commands = scanner.GetCommands();
		HashMap<String, Double> sharedMemory = new HashMap<>();
		HashMap<String, String> sharedBind = new HashMap<>();
		ArrayList<DataServer> dataServers = new ArrayList<>();
		ArrayList<Client> clientSocket = new ArrayList<>();
		Lock lock = new ReentrantLock();

		for (ICommand command : commands)
		{
			double currentValue = command.Execute(sharedMemory, sharedBind, dataServers, clientSocket, lock);
			if (command.getClass() == ReturnCommand.class)
			{
				CleanUp(dataServers);
				return (int) currentValue;
			}
		}

		CleanUp(dataServers);
		return 0;
	}

	private static void CleanUp(ArrayList<DataServer> dataServers)
	{
		if (dataServers.size() > 0)
		{
			dataServers.get(0).Disconnect();
		}
	}
}
