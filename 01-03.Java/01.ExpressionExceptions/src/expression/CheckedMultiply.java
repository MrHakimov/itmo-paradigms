package expression;

import exceptions.*;
import static expression.OverflowCheck.overflowMultiplyCheck;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:07:15
 */

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws OverflowException  {
        overflowMultiplyCheck(left, right);
    }

    protected int calculate(int left, int right) throws OverflowException  {
        check(left, right);
        return left * right;
    }
}
