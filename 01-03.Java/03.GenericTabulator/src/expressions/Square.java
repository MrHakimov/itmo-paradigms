package expressions;

import exceptions.OverflowException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:20:01
 */

public class Square<T> extends AbstractUnaryOperator<T> {
    public Square(final TripleExpression<T> x, final Operation<T> y) {
        super(x, y);
    }

    protected T calculate(final T operand) throws OverflowException {
        return operation.sqr(operand);
    }
}
