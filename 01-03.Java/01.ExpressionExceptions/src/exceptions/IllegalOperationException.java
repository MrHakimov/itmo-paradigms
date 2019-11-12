package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:03:40
 */

public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(final String message) {
        super("Illegal operation: " + message);
    }
}
