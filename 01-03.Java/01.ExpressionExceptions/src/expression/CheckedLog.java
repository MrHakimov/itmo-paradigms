package expression;

import exceptions.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:06:41
 */

public class CheckedLog extends AbstractBinaryOperation {
    public CheckedLog(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws EvaluatingException {
        if (left <= 0) {
            throw new IllegalOperationException("ERROR: Logarithm's form is not positive!!!");
        }

        if (right <= 0 || right == 1) {
            throw new IllegalOperationException("ERROR: Logarithm's base is incorrect!!!");
        }
    }

    protected int calculate(int left, int right) throws EvaluatingException {
        check(left, right);
        int l = 0;
        int r = 31;
        while (r - l > 1) {
            int m = (l + r) / 2;
            try {
                int res = new CheckedPow(new Const(right), new Const(m)).evaluate(0, 0, 0);
                if (res <= left) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (EvaluatingException e) {
                r = m;
            }
        }
        return l;
    }
}
