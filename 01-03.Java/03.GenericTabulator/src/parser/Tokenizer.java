package parser;

import exceptions.*;
import operations.Operation;

import java.util.HashMap;
import java.util.HashSet;


/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:30:35
 */

public class Tokenizer<T> {
    private String expression;
    private int ind;

    final private Operation<T> op;

    private Token currentToken;
    private static HashSet<Token> operations = new HashSet<>();
    private static HashSet<Token> binaryOperations = new HashSet<>();
    private static HashMap<String, Token> identifiers = new HashMap<>();

    static {
        identifiers.put("square", Token.SQR);
        identifiers.put("mod", Token.MOD);
        identifiers.put("abs", Token.ABS);
        identifiers.put("x", Token.VARIABLE);
        identifiers.put("y", Token.VARIABLE);
        identifiers.put("z", Token.VARIABLE);

        operations.add(Token.SQR);
        operations.add(Token.ABS);
        operations.add(Token.ADD);
        operations.add(Token.SUB);
        operations.add(Token.MUL);
        operations.add(Token.DIV);
        operations.add(Token.MOD);

        binaryOperations.add(Token.ADD);
        binaryOperations.add(Token.SUB);
        binaryOperations.add(Token.MUL);
        binaryOperations.add(Token.DIV);
        binaryOperations.add(Token.MOD);
    }

    private T value;
    private char name;
    private int balance;

    public Tokenizer(final String s, final Operation<T> operation) {
        expression = s;
        ind = 0;
        currentToken = Token.BEGIN;
        balance = 0;
        op = operation;
    }

    public Token getNextToken() throws ParsingException {
        nextToken();
        return currentToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public T getValue() {
        return value;
    }

    public char getName() {
        return name;
    }

    public int getIndex() {
        return ind;
    }

    public String getExpression() {
        return expression;
    }

    private void skipWhiteSpaces() {
        while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
            ind++;
        }
    }

    private void checkOperand(final int pos) throws MissedOperandException {
        if (operations.contains(currentToken) || currentToken == Token.OPEN_BRACKET || currentToken == Token.BEGIN) {
            throw new MissedOperandException(expression, pos);
        }
    }

    private void checkOperation(final int pos) throws MissedOperationException {
        if (currentToken == Token.CLOSE_BRACKET || currentToken == Token.VARIABLE || currentToken == Token.NUMBER) {
            throw new MissedOperationException(expression, pos);
        }
    }

    private boolean isPartOfNumber(final char x) {
        return Character.isDigit(x) || x == '.' || x == 'e';
    }

    private String getNumber() {
        int l = ind;
        while (ind < expression.length() && isPartOfNumber(expression.charAt(ind))) {
            ind++;
        }
        int r = ind;
        ind--;
        return expression.substring(l, r);
    }

    private boolean isPartOfIdentifier(final char x) {
        return Character.isLetterOrDigit(x) || x == '_';
    }

    private String getIdentifier() throws UnknownSymbolException {
        if (!Character.isLetter(expression.charAt(ind))) {
            throw new UnknownSymbolException(expression, ind);
        }
        int l = ind;
        while (ind < expression.length() && isPartOfIdentifier(expression.charAt(ind))) {
            ind++;
        }
        int r = ind;
        ind--;
        return expression.substring(l, r);
    }

    private void nextToken() throws ParsingException {
        skipWhiteSpaces();
        if (ind >= expression.length()) {
            checkOperand(ind);
            currentToken = Token.END;
            return;
        }
        char ch = expression.charAt(ind);
        switch (ch) {
            case '-':
                if (currentToken == Token.NUMBER || currentToken == Token.VARIABLE || currentToken == Token.CLOSE_BRACKET) {
                    currentToken = Token.SUB;
                } else {
                    if (ind + 1 >= expression.length()) {
                        throw new MissedOperandException(expression, ind + 1);
                    }
                    if (Character.isDigit(expression.charAt(ind + 1))) {
                        ind++;
                        String temp = getNumber();
                        try {
                            value = op.parseNumber("-" + temp);
                        } catch (IncorrectConstException e) {
                            throw new IllegalConstantException("-" + temp, expression, ind - temp.length());
                        }
                        currentToken = Token.NUMBER;
                    }
                }
                break;
            case '+':
                checkOperand(ind);
                currentToken = Token.ADD;
                break;
            case '*':
                checkOperand(ind);
                currentToken = Token.MUL;
                break;
            case '/':
                checkOperand(ind);
                currentToken = Token.DIV;
                break;
            case '(':
                if (currentToken == Token.CLOSE_BRACKET || currentToken == Token.NUMBER || currentToken == Token.VARIABLE) {
                    throw new UnsuitableOpeningBracketException(expression, ind);
                }
                balance++;
                currentToken = Token.OPEN_BRACKET;
                break;
            case ')':
                if (operations.contains(currentToken) || currentToken == Token.OPEN_BRACKET) {
                    throw new MissedOperandException(expression, ind);
                }
                balance--;
                if (balance < 0) {
                    throw new UnsuitableClosingBracketException(expression, ind);
                }
                currentToken = Token.CLOSE_BRACKET;
                break;
            default:
                if (Character.isDigit(ch)) {
                    checkOperation(ind);
                    String temp = getNumber();
                    try {
                        value = op.parseNumber(temp);
                    } catch (IncorrectConstException e) {
                        throw new IllegalConstantException(temp, expression, ind - temp.length() + 1);
                    }
                    currentToken = Token.NUMBER;
                } else {
                    String curInd = getIdentifier();
                    if (!identifiers.containsKey(curInd)) {
                        throw new UnknownIdentifierException(curInd, expression, ind - curInd.length() + 1);
                    }
                    if (binaryOperations.contains(identifiers.get(curInd))) {
                        checkOperand(ind - curInd.length() + 1);
                    } else {
                        checkOperation(ind - curInd.length() + 1);
                    }
                    currentToken = identifiers.get(curInd);
                    if (currentToken == Token.VARIABLE) {
                        name = curInd.charAt(0);
                    }
                }

        }
        ind++;
    }
}
