package operations;

import exceptions.EvaluatingException;
import exceptions.IncorrectConstException;
import exceptions.OverflowException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:26:03
 */

public interface Operation<T> {
    T parseNumber(final String number) throws IncorrectConstException;

    T add(final T firstOperand, final T secondOperand) throws OverflowException;

    T sub(final T firstOperand, final T secondOperand) throws OverflowException;

    T mul(final T firstOperand, final T secondOperand) throws OverflowException;

    T div(final T firstOperand, final T secondOperand) throws EvaluatingException;

    T mod(final T firstOperand, final T secondOperand) throws EvaluatingException;

    T abs(final T operand) throws OverflowException;

    default T sqr(final T operand) throws OverflowException {
        return mul(operand, operand);
    }
}
