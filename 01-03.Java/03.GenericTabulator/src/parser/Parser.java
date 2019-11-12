package parser;

import exceptions.ParsingException;
import expressions.TripleExpression;


/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:29:50
 */

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}
