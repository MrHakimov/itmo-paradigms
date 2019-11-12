package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:07:19
 */

public class OverflowException extends EvaluatingException {
    public OverflowException() {
        super("overflow");
    }
}
