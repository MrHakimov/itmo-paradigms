package expression;

import exceptions.*;
import static expression.OverflowCheck.overflowMultiplyCheck;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:09:40
 */

public class CheckedPow extends AbstractBinaryOperation {
    public CheckedPow(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected void check(int left, int right) throws EvaluatingException {
        /*
        if (left == 0 && right == 0) {
           throw new IllegalOperationException("ERROR: 0 ** 0 is undefined!!!");
        }
        */
        if (right < 0) {
            throw new IllegalOperationException("ERROR: Powering in negative!!!");
        }
    }

    protected int binaryPow(int a, int n) throws OverflowException {
        if (n == 0) {
            return 1;
        }
        if (n % 2 == 0) {
            int res = binaryPow(a, n / 2);
            overflowMultiplyCheck(res, res);
            return res * res;
        } else {
            int res = binaryPow(a, n - 1);
            overflowMultiplyCheck(res, a);
            return res * a;
        }
    }

    protected int calculate(int left, int right) throws EvaluatingException {
        check(left, right);
        return binaryPow(left, right);
    }
}
