package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:06:01
 */

public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException(final String id, final String s, final int ind) {
        super("Unknown identifier '" + id + "' at position: " + ind + "\n" + s + "\n" + getPlace(ind, id.length()));
    }
}
