package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:04:49
 */

public class MissingOperationException extends ParsingException {
    public MissingOperationException(final String s, final int ind) {
        super("Missing operation before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
