package test.command;

import java.util.HashMap;

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
    public int Execute(HashMap<String, Integer> sharedMemory) {
        int leftValue = _left.Execute(sharedMemory);
        int rightValue = _left.Execute(sharedMemory);
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
