package test;

import test.command.ICommand;

import java.util.ArrayList;
import java.util.HashMap;

public class MyInterpreter {

	public static int interpret(String[] lines){
		// scan the given lines into relevant structure for interpreter
		LinesScanner scanner = new LinesScanner(lines);
		ArrayList<ICommand> commands = scanner.GetCommands();
		HashMap<String, Integer> sharedMemory = new HashMap<>();

		commands.forEach(command -> {
			command.Execute(sharedMemory);
		});

		return 0;
	}
}
