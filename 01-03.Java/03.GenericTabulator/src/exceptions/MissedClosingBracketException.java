package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:05:38
 */

public class MissedClosingBracketException extends ParsingException {
    public MissedClosingBracketException(final String s, final int ind){
        super("Missed closing bracket at position: " + ind + "\n" + s
                + "\n" + showPosition(ind, 1));
    }
}
