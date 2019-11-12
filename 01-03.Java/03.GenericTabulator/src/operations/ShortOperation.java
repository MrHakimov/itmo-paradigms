package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:23:44
 */

public class ShortOperation implements Operation<Short> {
    public Short parseNumber(final String number) throws IncorrectConstException {
        try {
            return (short) Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    private void checkZero(final short y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Short add(final Short firstOperand, final Short secondOperand) {
        return (short) (firstOperand + secondOperand);
    }

    public Short sub(final Short firstOperand, final Short secondOperand) {
        return (short) (firstOperand - secondOperand);
    }

    public Short mul(final Short firstOperand, final Short secondOperand) {
        return (short) (firstOperand * secondOperand);
    }

    public Short div(final Short firstOperand, final Short secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Division by zero");
        return (short) (firstOperand / secondOperand);
    }

    public Short mod(final Short firstOperand, final Short secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Taking module by zero");
        return (short) (firstOperand % secondOperand);
    }

    public Short abs(final Short operand) {
        return (short) (Math.abs(operand));
    }
}
