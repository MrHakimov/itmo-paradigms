package expression;

/**
 * @author: Muhammadjon Hakimov
 * created: 12.02.2019 23:14:47
 */

public class Low extends AbstractUnaryOperation {
    public Low(TripleExpression operand) {
        super(operand);
    }

    protected int calculate(int operand) {
        if (operand == 0) {
            return 0;
        }
        int answer = 0;
        while ((operand & 1) != 1) {
            operand >>= 1;
            answer++;
        }
        return (1 << answer);
    }
}
