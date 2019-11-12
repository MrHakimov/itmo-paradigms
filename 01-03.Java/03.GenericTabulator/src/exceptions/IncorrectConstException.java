package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:05:11
 */

public class IncorrectConstException extends ParsingException {
    public IncorrectConstException() {
        super("Given constant is incorrect!");
    }
}
