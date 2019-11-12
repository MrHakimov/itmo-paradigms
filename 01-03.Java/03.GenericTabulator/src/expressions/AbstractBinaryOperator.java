package expressions;

import exceptions.EvaluatingException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:15:03
 */

public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    private final TripleExpression<T> firstOperand;
    private final TripleExpression<T> secondOperand;
    protected final Operation<T> operation;

    protected AbstractBinaryOperator(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        firstOperand = x;
        secondOperand = y;
        operation = z;
    }

    protected abstract T calculate(final T firstOperand, final T secondOperand) throws EvaluatingException;

    public T evaluate(final T x, final T y, final T z) throws EvaluatingException {
        return calculate(firstOperand.evaluate(x, y, z), secondOperand.evaluate(x, y, z));
    }

}
