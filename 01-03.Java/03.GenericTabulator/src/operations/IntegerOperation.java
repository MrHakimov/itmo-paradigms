package operations;

import exceptions.IllegalOperationException;
import exceptions.IncorrectConstException;
import exceptions.OverflowException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:25:27
 */

public class IntegerOperation implements Operation<Integer> {
    private final boolean checked;

    public IntegerOperation(final boolean check) {
        checked = check;
    }

    public Integer parseNumber(final String number) throws IncorrectConstException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IncorrectConstException();
        }
    }

    private void checkAdd(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    public Integer add(final Integer firstOperand, final Integer secondOperand) throws OverflowException {
        if (checked) {
            checkAdd(firstOperand, secondOperand);
        }
        return firstOperand + secondOperand;
    }

    private void checkSub(final Integer x, final Integer y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    public Integer sub(final Integer firstOperand, final Integer secondOperand) throws OverflowException {
        if (checked) {
            checkSub(firstOperand, secondOperand);
        }
        return firstOperand - secondOperand;
    }

    private void checkMul(final Integer x, final Integer y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    public Integer mul(final Integer firstOperand, final Integer secondOperand) throws OverflowException {
        if (checked) {
            checkMul(firstOperand, secondOperand);
        }
        return firstOperand * secondOperand;
    }

    private void checkDiv(final Integer x, final Integer y) throws OverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    private void checkZero(final int y, final String reason) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException(reason);
        }
    }

    public Integer div(final Integer firstOperand, final Integer secondOperand)
            throws IllegalOperationException, OverflowException {
        checkZero(secondOperand, "Division by zero");
        if (checked) {
            checkDiv(firstOperand, secondOperand);
        }
        return firstOperand / secondOperand;
    }

    public Integer mod(final Integer firstOperand, final Integer secondOperand) throws IllegalOperationException {
        checkZero(secondOperand, "Taking module by zero");
        return firstOperand % secondOperand;
    }

    private void checkAbs(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    public Integer abs(final Integer operand) throws OverflowException {
        if (checked) {
            checkAbs(operand);
        }
        return Math.abs(operand);
    }
}
