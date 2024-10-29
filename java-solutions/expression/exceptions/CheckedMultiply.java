package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedMultiply extends Binary implements OwnExpression {
    public CheckedMultiply(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "*";
    }

    @Override
    protected int calc(int a, int b) {
        int result = a * b;
        if (a < 0 && b < 0 && result == 0) {
            throw new ArithmeticException("overflow");
        }
        if ((a < 0 && b == 1 && result == a) || (a < 0 && b < 0 && a >= Integer.MAX_VALUE / b) ||
                (a == 0 || b == 0) || (a < 0 && b > 0 && a == result / b) ||
                (b < 0 && a > 0 && b == result / a) || (a > 0 && b > 0 && a <= Integer.MAX_VALUE / b)) {
            return result;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}
