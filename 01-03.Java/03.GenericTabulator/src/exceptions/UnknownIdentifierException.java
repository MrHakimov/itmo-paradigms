package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:08:02
 */

public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException(final String id, final String s, final int ind) {
        super("Unknown identifier \'" + id + "\' at position: " + ind + "\n" + s
                + "\n" + showPosition(ind, id.length()));
    }
}
