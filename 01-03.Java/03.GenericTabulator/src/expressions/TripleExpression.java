package expressions;

import exceptions.EvaluatingException;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:21:23
 */

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws EvaluatingException;
}
