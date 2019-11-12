package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:06:29
 */

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(final String s, final int ind) {
        super("Unknown symbol '" + s.charAt(ind) + "' at position " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
