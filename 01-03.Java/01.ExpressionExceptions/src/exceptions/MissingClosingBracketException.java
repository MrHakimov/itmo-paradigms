package exceptions;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:04:02
 */

public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException(final String s, final int ind){
        super("Missing closing bracket for opening one at position: " + ind + "\n" + s + "\n" + getPlace(ind, 1));
    }
}
