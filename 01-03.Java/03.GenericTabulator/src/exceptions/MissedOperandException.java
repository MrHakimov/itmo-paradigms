package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:05:59
 */

public class MissedOperandException extends ParsingException {
    public MissedOperandException(final String s, final int ind) {
        super("Missed operand before position: " + ind + "\n" + s
                + "\n" + showPosition(ind, 1));
    }
}
