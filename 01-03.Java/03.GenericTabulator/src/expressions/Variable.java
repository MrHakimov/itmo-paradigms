package expressions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:21:49
 */

public strictfp class Variable<T> implements TripleExpression<T> {
    private final char name;

    public Variable(final char x) {
        name = x;
    }

    public T evaluate(final T x, final T y, final T z) {
        switch (name) {
            case 'x':
                return x;
            case 'y':
                return y;
            case 'z':
                return z;
            default:
                return x;
        }
    }
}
