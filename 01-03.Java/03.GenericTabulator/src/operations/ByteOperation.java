package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:23:44
 */

public class ByteOperation implements Operation<Byte> {
    public Byte parseNumber(final String number) throws IncorrectConstException {
        try {
            return (byte) Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    private void checkZero(final byte y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Byte add(final Byte firstOperand, final Byte secondOperand) {
        return (byte) (firstOperand + secondOperand);
    }

    public Byte sub(final Byte firstOperand, final Byte secondOperand) {
        return (byte) (firstOperand - secondOperand);
    }

    public Byte mul(final Byte firstOperand, final Byte secondOperand) {
        return (byte) (firstOperand * secondOperand);
    }

    public Byte div(final Byte firstOperand, final Byte secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Division by zero");
        return (byte) (firstOperand / secondOperand);
    }

    public Byte mod(final Byte firstOperand, final Byte secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Taking module by zero");
        return (byte) (firstOperand % secondOperand);
    }

    public Byte abs(final Byte operand) {
        return (byte) (Math.abs(operand));
    }
}
