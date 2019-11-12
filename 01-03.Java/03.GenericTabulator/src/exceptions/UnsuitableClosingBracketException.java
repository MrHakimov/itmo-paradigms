package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:06:42
 */

public class UnsuitableClosingBracketException extends ParsingException {
    public UnsuitableClosingBracketException(final String s, int ind) {
        super("The number of closing brackets is odd at position: " + ind
                + "\n" + s + "\n" + showPosition(ind, 1));
    }
}
