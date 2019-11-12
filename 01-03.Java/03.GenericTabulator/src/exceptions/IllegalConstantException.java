package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:04:23
 */

public class IllegalConstantException extends ParsingException {
    public IllegalConstantException(final String reason, final String s, final int ind) {
        super("Constant is unsuitable for current mode: " + reason + "\n" + s
                + "\n" + showPosition(ind, reason.length()));
    }
}
