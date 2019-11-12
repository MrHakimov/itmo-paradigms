package operations;

import exceptions.IncorrectConstException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:24:54
 */

public class FloatOperation implements Operation<Float> {
    public Float parseNumber(final String number) throws IncorrectConstException {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException NFM) {
            throw new IncorrectConstException();
        }
    }

    public Float add(final Float firstOperand, final Float secondOperand) {
        return firstOperand + secondOperand;
    }

    public Float sub(final Float firstOperand, final Float secondOperand) {
        return firstOperand - secondOperand;
    }

    public Float mul(final Float firstOperand, final Float secondOperand) {
        return firstOperand * secondOperand;
    }

    public Float div(final Float firstOperand, final Float secondOperand) {
        return firstOperand / secondOperand;
    }

    public Float mod(final Float firstOperand, final Float secondOperand) {
        return firstOperand % secondOperand;
    }

    public Float abs(final Float operand) {
        return Math.abs(operand);
    }
}
