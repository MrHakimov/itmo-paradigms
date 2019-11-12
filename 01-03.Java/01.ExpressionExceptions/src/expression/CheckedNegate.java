package expression;

import exceptions.EvaluatingException;
import exceptions.OverflowException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:08:08
 */

public class CheckedNegate extends AbstractUnaryOperation {
    public CheckedNegate(TripleExpression operand) {
        super(operand);
    }

    protected void check(int operand) throws EvaluatingException {
        if (operand == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int calculate(int operand) throws EvaluatingException {
        check(operand);
        return -operand;
    }
}
