package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedSubtract extends Binary implements OwnExpression {
    public CheckedSubtract(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "-";
    }

    @Override
    protected int calc(int a, int b) {
        int result = a - b;
        if ((b <= 0 && a <= 0 && a <= b) || (a <= b && result <= 0) || (a >= b && result >= 0)) {
            return result;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}
