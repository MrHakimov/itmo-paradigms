package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:05:06
 */

public class OverflowException extends EvaluatingException {
    public OverflowException() {
        super("overflow");
    }
}
