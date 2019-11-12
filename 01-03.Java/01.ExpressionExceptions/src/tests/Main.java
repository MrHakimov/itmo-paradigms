package tests;

import exceptions.*;
import parser.ExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Muhammadjon Hakimov
 *    idea: Andrey Ivshin
 * created: 18.12.2018 01:10:34
 */

public class Main {
    private static List<Boolean> testResult = new ArrayList<>();
    private static int numberOfTests = 1;

    private static void border() {
        System.out.println("________________\n");
    }

    private static void testNumber() {
        System.out.printf("Test #%d:\n", numberOfTests++);
    }

    private static void result() {
        if (!testResult.contains(false)) {
            System.out.println("Testing completed successfully!");
        } else {
            System.out.println("TESTING FAILED!!!");
            int testNumber = 0;
            for (boolean currentIsCorrect: testResult) {
                ++testNumber;
                if (!currentIsCorrect) {
                    System.out.printf("Wrong answer on test #%d\n", testNumber);
                }
            }
        }
        System.out.println("====");
    }

    private static String evaluateExpression(String expression, int x, int y, int z) {
        return expression.replaceAll("x", String.valueOf(x)).
                replaceAll("y", String.valueOf(y)).
                replaceAll("z", String.valueOf(z));
    }

    private static void calculate(String expression, int x, int y, int z, int result) {
        assert expression != null : "ERROR: Expression is NULL!!!";

        int tmp;
        ExpressionParser parser = new ExpressionParser();
        try {
            testNumber();
            System.out.printf("%s = %s\n", evaluateExpression(expression, x, y, z), (tmp = parser.parse(expression).evaluate(x, y, z)));
            testResult.add(tmp == result);
        } catch (ParsingException | EvaluatingException e) {
            System.out.printf("\t\"%s\"\n\n%s\n", evaluateExpression(expression, x, y, z), e.getMessage());
        } catch (NullPointerException e) {
            System.out.print(e.getMessage());
        }
        border();
    }

    private static void calculateExpression(String expression, int x, int y, int z, int result) {
        calculate(expression, x, y, z, result);
    }

    private static void calculateExpression(String expression, int result) {
        calculate(expression, 0, 0, 0, result);
    }


    private static void test(ExpressionParser parser) {
        calculateExpression("1 + 2 * 38", 77);
        calculateExpression("76 + pow2 10", 1100);
        calculateExpression("log2 1024 + 1", 11);
        calculateExpression("log2 x + pow2 x * 9 + (y + z) / 12", 1, 2, 3, 18);
        calculateExpression(")", -1);
        calculateExpression("pow2 x / (x - 1)", 1, 0, 0, -1);
        calculateExpression("d * 2 + 5", 12, 13, 9, -1);
        calculateExpression("log2 -9", -1);
        calculateExpression("^9 + 58", -1);
        calculateExpression("babakh 0", -1);
        calculateExpression("2147483647 + 12", -1);
        calculateExpression("-2147483648 - 1", -1);
        calculateExpression("0 - (-2147483648)", -1);
        calculateExpression("pow2 31 + 1", -1);
        calculateExpression("high 15", 8);
        calculateExpression("high -4", Integer.MIN_VALUE);
        calculateExpression("low 18", 2);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ExpressionParser testParser = new ExpressionParser();

        test(testParser);

        result();
        long finishTime = System.currentTimeMillis();
        System.out.println("Time: " + (finishTime - startTime) + " ms");
    }
}
