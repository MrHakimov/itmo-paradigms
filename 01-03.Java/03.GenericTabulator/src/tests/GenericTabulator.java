package tests;

import parser.*;
import exceptions.*;
import operations.*;
import java.util.HashMap;
import expressions.TripleExpression;

/**
 * @author: Muhammadjon Hakimov
 * created: 03.03.2019 01:31:18
 */

public class GenericTabulator implements Tabulator {
    private final static HashMap<String, Operation<?>> modes = new HashMap<>();

    public GenericTabulator() {
        modes.put("i", new IntegerOperation(true));
        modes.put("u", new IntegerOperation(false));
        modes.put("bi", new BigIntegerOperation());
        modes.put("b", new ByteOperation());
        modes.put("l", new LongOperation());
        modes.put("s", new ShortOperation());
        modes.put("d", new DoubleOperation());
        modes.put("f", new FloatOperation());
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1,
                                 int y2, int z1, int z2) throws UnknownModeException {
        return buildTable(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(final String mode) throws UnknownModeException {
        Operation<?> result = modes.get(mode);
        if (result == null) {
            throw new UnknownModeException(mode);
        }
        return result;
    }

    private <T> Object[][][] buildTable(Operation<T> op, String expression, int x1, int x2, int y1,
                                        int y2, int z1, int z2) {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<>(op);
        TripleExpression<T> exp;
        try {
            exp = parser.parse(expression);
        } catch (Exception e) {
            return result;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        result[i - x1][j - y1][k - z1] = exp.evaluate(op.parseNumber(Integer.toString(i)),
                                op.parseNumber(Integer.toString(j)), op.parseNumber(Integer.toString(k)));
                    } catch (ParsingException | EvaluatingException e) {
                        result[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return result;
    }
}
