package expression;

import exceptions.OverflowException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 15:11:14
 */

public class OverflowCheck {
    public static void overflowMultiplyCheck(int x, int y) throws OverflowException {
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
}
