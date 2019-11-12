package expression;

import exceptions.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:06:09
 */

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws EvaluatingException {
        if (right == 0) {
            throw new IllegalOperationException("ERROR: Division by ZERO, or ZORRO, maybe :)");
        }
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new OverflowException();
        }
    }

    protected int calculate(int left, int right) throws EvaluatingException {
        check(left, right);
        return left / right;
    }
}
