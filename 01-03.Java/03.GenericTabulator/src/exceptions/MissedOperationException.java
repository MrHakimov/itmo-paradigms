package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:06:22
 */

public class MissedOperationException extends ParsingException {
    public MissedOperationException(final String s, final int ind) {
        super("Missed operation before position: " + ind + "\n" + s
                + "\n" + showPosition(ind, 1));
    }
}
