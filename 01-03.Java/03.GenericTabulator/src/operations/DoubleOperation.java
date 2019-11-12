package operations;

import exceptions.IncorrectConstException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:24:22
 */

public class DoubleOperation implements Operation<Double> {
    public Double parseNumber(final String number) throws IncorrectConstException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException NFM) {
            throw new IncorrectConstException();
        }
    }

    public Double add(final Double firstOperand, final Double secondOperand) {
        return firstOperand + secondOperand;
    }

    public Double sub(final Double firstOperand, final Double secondOperand) {
        return firstOperand - secondOperand;
    }

    public Double mul(final Double firstOperand, final Double secondOperand) {
        return firstOperand * secondOperand;
    }

    public Double div(final Double firstOperand, final Double secondOperand) {
        return firstOperand / secondOperand;
    }

    public Double mod(final Double firstOperand, final Double secondOperand) {
        return firstOperand % secondOperand;
    }

    public Double abs(final Double operand) {
        return Math.abs(operand);
    }
}
