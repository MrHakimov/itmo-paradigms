package expressions;

import exceptions.EvaluatingException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:15:44
 */

public abstract class AbstractUnaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> operand;
    protected final Operation<T> operation;

    protected AbstractUnaryOperator(final TripleExpression<T> x, final Operation<T> y) {
        operand = x;
        operation = y;
    }

    protected abstract T calculate(final T operand) throws EvaluatingException;

    public T evaluate(final T x, final T y, final T z) throws EvaluatingException {
        return calculate(operand.evaluate(x, y, z));
    }
}
