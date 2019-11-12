package parser;

import expression.*;
import exceptions.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:10:23
 */

public class ExpressionParser implements Parser {
    private Tokenizer tokenizer;

    private TripleExpression unary() throws ParsingException {
        TripleExpression currentResult;

        switch (tokenizer.getNextToken()) {
            case CONST:
                currentResult = new Const(tokenizer.getValue());
                tokenizer.getNextToken();
                break;
            case VARIABLE:
                currentResult = new Variable(tokenizer.getName());
                tokenizer.getNextToken();
                break;
            case NEGATE:
                currentResult = new CheckedNegate(unary());
                break;
            case LOG2:
                currentResult = new CheckedLog(unary(), new Const(2));
                break;
            case POW2:
                currentResult = new CheckedPow(new Const(2), unary());
                break;
            case OPENING_BRACKET:
                int position = tokenizer.getIndex();
                currentResult = addAndSub();
                if (tokenizer.getCurrentToken() != Token.CLOSING_BRACKET) {
                    throw new MissingClosingBracketException(tokenizer.getExpression(), position - 1);
                }
                tokenizer.getNextToken();
                break;
            case HIGH:
                currentResult = new High(unary());
                break;
            case LOW:
                currentResult = new Low(unary());
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + tokenizer.getExpression());
        }
        return currentResult;
    }

    private TripleExpression logAndPow() throws ParsingException {
        TripleExpression currentResult = unary();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case LOG:
                    currentResult = new CheckedLog(currentResult, unary());
                    break;
                case POW:
                    currentResult = new CheckedPow(currentResult, unary());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    private TripleExpression mulAndDiv() throws ParsingException {
        TripleExpression currentResult = logAndPow();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case MULTIPLY:
                    currentResult = new CheckedMultiply(currentResult, logAndPow());
                    break;
                case DIVIDE:
                    currentResult = new CheckedDivide(currentResult, logAndPow());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    private TripleExpression addAndSub() throws ParsingException {
        TripleExpression currentResult = mulAndDiv();
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case ADD:
                    currentResult = new CheckedAdd(currentResult, mulAndDiv());
                    break;
                case SUBTRACT:
                    currentResult = new CheckedSubtract(currentResult, mulAndDiv());
                    break;
                default:
                    return currentResult;
            }
        }
    }

    public TripleExpression parse(final String expression) throws ParsingException {
        assert expression != null : "Expression is null";

        tokenizer = new Tokenizer(expression);
        return addAndSub();
    }
}
