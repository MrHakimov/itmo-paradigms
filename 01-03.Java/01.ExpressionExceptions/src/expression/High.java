package expression;

/**
 * @author: Muhammadjon Hakimov
 * created: 12.02.2019 22:23:49
 */

public class High extends AbstractUnaryOperation {
    public High(TripleExpression operand) {
        super(operand);
    }

    protected int calculate(int operand) {
        for (int i = 1; i <= 16; i *= 2) {
            operand |= (operand >> i);
        }
        return operand != -1 ? operand - (operand >> 1) : Integer.MIN_VALUE;
    }
}
