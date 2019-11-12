package expression;

import exceptions.EvaluatingException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:04:09
 */

public abstract class AbstractUnaryOperation implements TripleExpression {
    private final TripleExpression operand;

    protected AbstractUnaryOperation(final TripleExpression value) {
        operand = value;
    }

    protected abstract int calculate(int operand) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return calculate(operand.evaluate(x, y, z));
    }
}
