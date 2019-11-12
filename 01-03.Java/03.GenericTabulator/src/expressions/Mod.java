package expressions;

import exceptions.EvaluatingException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:17:49
 */

public class Mod<T> extends AbstractBinaryOperator<T> {
    public Mod(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T calculate(final T firstOperand, final T secondOperand) throws EvaluatingException {
        return operation.mod(firstOperand, secondOperand);
    }
}
