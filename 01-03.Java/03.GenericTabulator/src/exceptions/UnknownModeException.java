package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:08:21
 */

public class UnknownModeException extends Exception{
    public UnknownModeException(final String mode) {
        super("Unknown mode for tabulation: " + mode);
    }
}
