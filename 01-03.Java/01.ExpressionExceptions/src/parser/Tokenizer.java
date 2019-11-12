package parser;

import exceptions.*;
import java.util.*;

/**
 * @author: Muhammadjon Hakimov
 * created: 16.12.2018 17:12:04
 */

public class Tokenizer {
    private String expression;
    private int index;

    private Token currentToken;

    private static Set <Token> operations = EnumSet.of(Token.ADD, Token.DIVIDE, Token.MULTIPLY, Token.NEGATE,
            Token.SUBTRACT, Token.LOG, Token.LOG2, Token.POW, Token.POW2, Token.HIGH, Token.LOW);

    private static Set <Token> binaryOperations = EnumSet.of(Token.ADD, Token.DIVIDE, Token.MULTIPLY,
            Token.SUBTRACT, Token.LOG, Token.POW);

    private static Map <String, Token> toToken = new HashMap <>();

    static {
        toToken.put("log2", Token.LOG2);
        toToken.put("pow2", Token.POW2);
        toToken.put("x", Token.VARIABLE);
        toToken.put("y", Token.VARIABLE);
        toToken.put("z", Token.VARIABLE);
        toToken.put("high", Token.HIGH);
        toToken.put("low", Token.LOW);
    }

    public Tokenizer(final String expression) {
        this.expression = expression;
        index = 0;
        currentToken = Token.BEGIN;
        numberOfBrackets = 0;
    }

    private int value;
    private String name;
    private int numberOfBrackets;

    public Token getNextToken() throws ParsingException {
        nextToken();
        return currentToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public String getExpression() {
        return expression;
    }

    private void checkOperand(final int position) throws MissingOperandException {
        if (operations.contains(currentToken) || currentToken == Token.OPENING_BRACKET || currentToken == Token.BEGIN) {
            throw new MissingOperandException(expression, position);
        }
    }

    private void checkOperation(final int position) throws MissingOperationException {
        if (currentToken == Token.CLOSING_BRACKET || currentToken == Token.VARIABLE || currentToken == Token.CONST) {
            throw new MissingOperationException(expression, position);
        }
    }

    private void skipSpaces() {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            ++index;
        }
    }

    private String getNumber() {
        StringBuilder newNumber = new StringBuilder();
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            newNumber.append(expression.charAt(index));
            index++;
        }
        --index;
        return newNumber.toString();
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(index))) {
            throw new UnknownSymbolException(expression, index);
        }
        StringBuilder newIdentifier = new StringBuilder();
        while (index < expression.length() && Character.isLetterOrDigit(expression.charAt(index))) {
            newIdentifier.append(expression.charAt(index));
            ++index;
        }
        --index;
        return newIdentifier.toString();
    }

    private void nextToken() throws ParsingException {
        skipSpaces();
        if (index >= expression.length()) {
            checkOperand(index);
            currentToken = Token.END;
            return;
        }

        char currentSymbol = expression.charAt(index);
        switch (expression.charAt(index)) {
            case '+':
                checkOperand(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                currentToken = Token.ADD;
                break;
            case '-':
                if (currentToken == Token.CONST || currentToken == Token.VARIABLE
                        || currentToken == Token.CLOSING_BRACKET) {
                    currentToken = Token.SUBTRACT;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, index + 1);
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        ++index;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException e) {
                            throw new IllegalConstantException("-" + temp, expression, index - temp.length());
                        }
                        currentToken = Token.CONST;
                    } else {
                        currentToken = Token.NEGATE;
                    }
                }
                break;
            case '/':
                checkOperand(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                if (expression.charAt(index) == '/') {
                    currentToken = Token.DIVIDE;
                }
                break;
            case '*':
                checkOperand(index);
                if (index + 1 >= expression.length()) {
                    throw new MissingOperandException(expression, index + 1);
                }
                if (expression.charAt(index) == '*') {
                    currentToken = Token.MULTIPLY;
                }
                break;
            case '(':
                checkOperation(index);
                ++numberOfBrackets;
                currentToken = Token.OPENING_BRACKET;
                break;
            case ')':
                checkOperand(index);
                --numberOfBrackets;
                if (numberOfBrackets < 0) {
                    throw new ClosingBracketException(expression, index);
                }
                currentToken = Token.CLOSING_BRACKET;
                break;
            default:
                if (Character.isDigit(currentSymbol)) {
                    checkOperation(index);
                    String currentNumber = getNumber();
                    try {
                        value = Integer.parseInt(currentNumber);
                    } catch (NumberFormatException NFE) {
                        throw new IllegalConstantException(currentNumber,
                                expression, index - currentNumber.length() + 1);
                    }
                    currentToken = Token.CONST;
                } else {
                    String currentIdentifier = getIdentifier();
                    if (!toToken.containsKey(currentIdentifier)) {
                        throw new UnknownIdentifierException(currentIdentifier,
                                expression, index - currentIdentifier.length() + 1);
                    }
                    if (binaryOperations.contains(toToken.get(currentIdentifier))) {
                        checkOperand(index - currentIdentifier.length() + 1);
                    } else {
                        checkOperation(index - currentIdentifier.length() + 1);
                    }
                    currentToken = toToken.get(currentIdentifier);
                    if (currentToken == Token.VARIABLE) {
                        name = Character.toString(currentIdentifier.charAt(0));
                    }
                }
        }
        ++index;
    }
}
