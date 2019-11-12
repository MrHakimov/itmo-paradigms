package expressions;

import exceptions.OverflowException;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:14:22
 */

public class Abs<T> extends AbstractUnaryOperator<T> {
    public Abs(final TripleExpression<T> leftOperand, final Operation<T> rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected T calculate(final T operand) throws OverflowException {
        return operation.abs(operand);
    }
}
