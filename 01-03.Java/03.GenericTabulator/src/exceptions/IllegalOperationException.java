package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:04:47
 */

public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(final String message) {
        super("Illegal operation: " + message);
    }
}
