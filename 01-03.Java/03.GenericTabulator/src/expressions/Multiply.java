package expressions;

import exceptions.OverflowException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:18:08
 */

public class Multiply<T> extends AbstractBinaryOperator<T> {
    public Multiply(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T calculate(final T firstOperand, final T secondOperand) throws OverflowException {
        return operation.mul(firstOperand, secondOperand);
    }
}
