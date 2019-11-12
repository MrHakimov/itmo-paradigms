package parser;

import expression.TripleExpression;
import exceptions.ParsingException;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:10:54
 */

public interface Parser {
    TripleExpression parse(String expression) throws ParsingException;
}
