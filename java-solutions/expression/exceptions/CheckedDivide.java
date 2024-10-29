package expression.exceptions;

import expression.Binary;
import expression.OwnExpression;

public class CheckedDivide extends Binary implements OwnExpression {
    public CheckedDivide(OwnExpression var11, OwnExpression var21) {
        super(var11, var21);
    }

    @Override
    protected String getSign() {
        return "/";
    }

    @Override
    protected int calc(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("division by zero");
        }
        int result = a / b;
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ArithmeticException("division by zero");
        }
        return result;
    }
}
