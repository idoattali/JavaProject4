package test.command;

import java.util.HashMap;

public class WhileCommand extends ContainerCommand{
    public WhileCommand(ConditionalCommand condition) {
        super(condition);
    }

    @Override
    public int Execute(HashMap<String, Integer> sharedMemory) {
        while (_condition.Execute(sharedMemory) == 1)
        {
            _commands.forEach(command -> command.Execute(sharedMemory));
        }
        return 0;
    }
}
