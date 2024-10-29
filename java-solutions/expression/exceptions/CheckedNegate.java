package expression.exceptions;

import expression.OwnExpression;

import java.util.List;

public class CheckedNegate implements OwnExpression {
    private final OwnExpression expression;

    public CheckedNegate(OwnExpression expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate(int a) {
        if (expression.evaluate(a) == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow unary");
        }
        return -(expression.evaluate(a));
    }

    @Override
    public int evaluate(int a, int b, int c) {
        if (expression.evaluate(a, b, c) == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow unary");
        }
        return -(expression.evaluate(a, b, c));
    }
    @Override
    public int evaluate(List<Integer> list) {
        if (expression.evaluate(list) == Integer.MIN_VALUE) {
            throw new ArithmeticException("");
        }
        return -(expression.evaluate(list));
    }

    @Override
    public String toString() {
        return "-(" + expression.toString() + ")";
    }
}
