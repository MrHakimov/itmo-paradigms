package operations;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;
import java.math.BigInteger;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:23:09
 */

public class BigIntegerOperation implements Operation<BigInteger> {
    public BigInteger parseNumber(final String number) throws IncorrectConstException {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    public BigInteger add(final BigInteger firstOperand, final BigInteger secondOperand) {
        return firstOperand.add(secondOperand);
    }

    public BigInteger sub(final BigInteger firstOperand, final BigInteger secondOperand) {
        return firstOperand.subtract(secondOperand);
    }

    public BigInteger mul(final BigInteger firstOperand, final BigInteger secondOperand) {
        return firstOperand.multiply(secondOperand);
    }

    private void checkZero(final BigInteger x, final String reason) throws IllegalOperationException {
        if (x.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException(reason);
        }
    }

    public BigInteger div(final BigInteger firstOperand, final BigInteger secondOperand)
            throws IllegalOperationException {
        checkZero(secondOperand, "Division by zero");
        return firstOperand.divide(secondOperand);
    }

    public BigInteger mod(final BigInteger firstOperand, final BigInteger secondOperand)
            throws EvaluatingException {
        checkZero(secondOperand, "Taking module by zero");
        if (secondOperand.compareTo(BigInteger.ZERO) < 0) {
            throw new EvaluatingException("Taking module by negative number");
        }
        return firstOperand.mod(secondOperand);
    }

    public BigInteger abs(final BigInteger operand) {
        return operand.abs();
    }
}
