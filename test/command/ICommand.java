package test.command;

import java.util.HashMap;

public interface ICommand {
    int Execute(HashMap<String, Integer> sharedMemory);
}
