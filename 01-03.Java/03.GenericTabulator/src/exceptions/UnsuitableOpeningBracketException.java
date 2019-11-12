package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:07:00
 */

public class UnsuitableOpeningBracketException extends ParsingException {
    public UnsuitableOpeningBracketException(final String s, final int ind) {
        super("The number of opening brackets is odd at position: " + ind
                + "\n" + s + "\n" + showPosition(ind, 1));
    }
}
