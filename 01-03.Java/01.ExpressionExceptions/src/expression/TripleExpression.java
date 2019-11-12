package expression;

import exceptions.EvaluatingException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:12:00
 */

public interface TripleExpression {
    int evaluate(int x, int y, int z) throws EvaluatingException;
}
