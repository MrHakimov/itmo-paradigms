package parser;

import exceptions.MissedClosingBracketException;
import exceptions.ParsingException;
import expressions.*;
import expressions.TripleExpression;
import operations.Operation;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:26:48
 */

public class ExpressionParser<T> implements Parser<T> {
    private Tokenizer<T> myTokenizer;
    private Operation<T> myOp;

    public ExpressionParser(final Operation<T> x) {
        myOp = x;
    }

    private TripleExpression<T> unary() throws ParsingException {
        TripleExpression<T> res;
        switch (myTokenizer.getNextToken()) {
            case NUMBER:
                res = new Const<>(myTokenizer.getValue());
                myTokenizer.getNextToken();
                break;
            case VARIABLE:
                res = new Variable<>(myTokenizer.getName());
                myTokenizer.getNextToken();
                break;
            case ABS:
                res = new Abs<>(unary(), myOp);
                break;
            case SQR:
                res = new Square<>(unary(), myOp);
                break;
            case OPEN_BRACKET:
                res = addSub();
                if (myTokenizer.getCurrentToken() != Token.CLOSE_BRACKET) {
                    throw new MissedClosingBracketException(myTokenizer.getExpression(), myTokenizer.getIndex());
                }
                myTokenizer.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + myTokenizer.getExpression());
        }
        return res;
    }

    private TripleExpression<T> mulDivMod() throws ParsingException {
        TripleExpression<T> res = unary();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case MOD:
                    res = new Mod<>(res, unary(), myOp);
                    break;
                case MUL:
                    res = new Multiply<>(res, unary(), myOp);
                    break;
                case DIV:
                    res = new Divide<>(res, unary(), myOp);
                    break;
                default:
                    return res;
            }
        } while (true);
    }

    private TripleExpression<T> addSub() throws ParsingException {
        TripleExpression<T> res = mulDivMod();
        do {
            switch (myTokenizer.getCurrentToken()) {
                case ADD:
                    res = new Add<>(res, mulDivMod(), myOp);
                    break;
                case SUB:
                    res = new Subtract<>(res, mulDivMod(), myOp);
                    break;
                default:
                    return res;
            }
        } while (true);
    }


    public TripleExpression<T> parse(final String s) throws ParsingException {
        myTokenizer = new Tokenizer<>(s, myOp);
        return addSub();
    }
}
