package expression;

import exceptions.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:10:12
 */

public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws OverflowException {
        if (left >= 0 && right < 0 && right < left - Integer.MAX_VALUE) {
            throw new OverflowException();
        }
        if (left <= 0 && right > 0 && -right < Integer.MIN_VALUE - left) {
            throw new OverflowException();
        }
    }

    protected int calculate(int left, int right) throws OverflowException {
        check(left, right);
        return left - right;
    }
}
