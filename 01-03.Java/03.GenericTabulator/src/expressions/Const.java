package expressions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:16:49
 */

public class Const<T> implements TripleExpression<T> {
    private final T value;

    public Const(final T x) {
        value = x;
    }

    public T evaluate(final T x, final T y, final T z) {
        return value;
    }
}
