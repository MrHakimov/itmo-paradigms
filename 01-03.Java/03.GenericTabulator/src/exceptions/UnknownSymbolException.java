package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:08:42
 */

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(final String s, final int ind) {
        super("Unknown symbol \'" + s.charAt(ind) + "\' at position "
                + ind + "\n" + s + "\n" + showPosition(ind, 1));
    }
}
