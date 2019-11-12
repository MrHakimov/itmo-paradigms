package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:04:24
 */

public class MissingOperandException extends ParsingException {
    public MissingOperandException(final String s, final int ind) {
        super("Missing operand before position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
