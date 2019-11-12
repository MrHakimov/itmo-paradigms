package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:03:09
 */

public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(final String reason, final String s, final int ind) {
        super("Constant '" + reason + "' is unsuitable for int at position: " + ind + "\n" + s + "\n" + getPlace(ind, reason.length()));
    }
}
