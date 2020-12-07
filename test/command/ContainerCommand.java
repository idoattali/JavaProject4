package test.command;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ContainerCommand implements ICommand{

    protected ConditionalCommand _condition;
    protected ArrayList<ICommand> _commands;

    public ContainerCommand(ConditionalCommand condition)
    {
        _condition = condition;
        _commands = new ArrayList<>();
    }

    public void AddCommand(ICommand command)
    {
        _commands.add(command);
    }
}
