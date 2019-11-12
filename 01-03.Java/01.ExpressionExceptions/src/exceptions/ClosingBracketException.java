package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:00:19
 */

public class ClosingBracketException extends ParsingException {
    public ClosingBracketException(final String s, int ind) {
        super("Redundant closing bracket at position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
