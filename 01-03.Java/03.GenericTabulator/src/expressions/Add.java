package expressions;

import exceptions.OverflowException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:16:26
 */

public class Add<T> extends AbstractBinaryOperator<T> {
    public Add(final TripleExpression<T> x, final TripleExpression<T> y, final Operation<T> z) {
        super(x, y, z);
    }

    protected T calculate(final T firstOperand, final T secondOperand) throws OverflowException {
        return operation.add(firstOperand, secondOperand);
    }
}
