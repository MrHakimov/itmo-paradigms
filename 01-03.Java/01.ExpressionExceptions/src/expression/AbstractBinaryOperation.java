package expression;

import exceptions.EvaluatingException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:03:17
 */

public abstract class AbstractBinaryOperation implements TripleExpression {
    private final TripleExpression left;
    private final TripleExpression right;

    public AbstractBinaryOperation(TripleExpression left, TripleExpression right) {
        assert left != null && right != null: "ERROR: One of the binary operators is NULL!!!";
        this.left = left;
        this.right = right;
    }

    protected abstract void check(int left, int right) throws EvaluatingException;

    protected abstract int calculate(int left, int right) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
