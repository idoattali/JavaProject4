package test.command;

import test.Client;
import test.DataServer;
import test.expression.ExpressionExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class ArithmeticCommand extends ExecutableCommand{
    private String _expression;
    public ArithmeticCommand(ArrayList<String> arguments) {
        super(arguments);
        _expression = ConvertArgumentsToString();
    }

    // x = { "4+5/7+4" }

    // ["4+", "x"] -> "4+x" -> "4+5"

    private String ConvertArgumentsToString()
    {
        return String.join("", _arguments);
    }

    private String FormatExpression(HashMap<String, Double> sharedMemory)
    {
        final String[] formattedExpression = {_expression};
        sharedMemory.entrySet().forEach(entry -> {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            formattedExpression[0] = formattedExpression[0].replaceAll(key, value);
        });

        return formattedExpression[0];
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        String formattedExpression = FormatExpression(sharedMemory);
        return ExpressionExecutor.calculate(formattedExpression);
    }
}
