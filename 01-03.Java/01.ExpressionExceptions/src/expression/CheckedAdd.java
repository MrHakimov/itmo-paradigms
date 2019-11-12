package expression;

import exceptions.OverflowException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:05:42
 */

public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws OverflowException  {
        if (left > 0 && right > Integer.MAX_VALUE - left) {
            throw new OverflowException();
        }
        if (left < 0 && right < Integer.MIN_VALUE - left) {
            throw new OverflowException();
        }
    }

    protected int calculate(int left, int right) throws OverflowException  {
        check(left, right);
        return left + right;
    }
}
