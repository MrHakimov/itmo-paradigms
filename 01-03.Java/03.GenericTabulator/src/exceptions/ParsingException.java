package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:07:39
 */

public class ParsingException extends Exception {
    public ParsingException(final String message) {
        super(message);
    }

    static protected String showPosition(final int ind, final int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            result.append(' ');
        }
        result.append('^');
        for (int i = 1; i < size; i++) {
            result.append('~');
        }
        return result.toString();
    }
}
