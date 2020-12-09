package test.command;

import test.Client;
import test.DataServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class ConditionalCommand implements ICommand {

    private ConditionTypes _type;
    private ArithmeticCommand _left;
    private ArithmeticCommand _right;

    public ConditionalCommand(ConditionTypes type, ArithmeticCommand left, ArithmeticCommand right)
    {
        _type = type;
        _left = left;
        _right = right;
    }

    @Override
    public double Execute(HashMap<String, Double> sharedMemory, HashMap<String, String> sharedBind, ArrayList<DataServer> dataServers, ArrayList<Client> clients, Lock lock) {
        double leftValue = _left.Execute(sharedMemory, sharedBind, dataServers, clients, lock);
        double rightValue = _right.Execute(sharedMemory, sharedBind, dataServers, clients, lock);
        switch(_type) {
            case LT:
                return leftValue < rightValue ? 1 : 0;
            case LE:
                return leftValue <= rightValue ? 1 : 0;
            case EQ:
                return leftValue == rightValue ? 1 : 0;
            case GE:
                return leftValue >= rightValue ? 1 : 0;
            case GT:
                return leftValue > rightValue ? 1 : 0;
            case NE:
                return leftValue != rightValue ? 1 : 0;
            default:
                return 0;
        }
    }
}
