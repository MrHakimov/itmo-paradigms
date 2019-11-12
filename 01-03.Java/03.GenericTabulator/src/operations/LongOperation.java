package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;

/**
 * @author: Muhammadjon Hakimov
 * created: 05.03.2019 22:07:07
 */

public class LongOperation implements Operation<Long> {
    public Long parseNumber(final String number) throws IncorrectConstException {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    public Long add(final Long firstOperand, final Long secondOperand) {
        return firstOperand + secondOperand;
    }

    public Long sub(final Long firstOperand, final Long secondOperand) {
        return firstOperand - secondOperand;
    }

    public Long mul(final Long firstOperand, final Long secondOperand) {
        return firstOperand * secondOperand;
    }

    private void checkZero(final long y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Long div(final Long firstOperand, final Long secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Division by zero");
        return firstOperand / secondOperand;
    }

    public Long mod(final Long firstOperand, final Long secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Taking module by zero");
        return firstOperand % secondOperand;
    }

    public Long abs(final Long operand) {
        return Math.abs(operand);
    }
}
