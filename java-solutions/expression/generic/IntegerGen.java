package expression.generic;

import expression.exceptions.ExpressionException;

public class IntegerGen<T> extends AbstractGen<Integer> implements GenericOperation<Integer> {

    @Override
    protected Integer neg(Integer a) {
        if (a == Integer.MIN_VALUE) {
            throw new ExpressionException("overflow");
        }
        return -a;
    }

    @Override
    protected Integer addElem(Integer a, Integer b) {
        if ((a > 0 && b > 0 && b > Integer.MAX_VALUE - a) || (a < 0 && b < 0 && b < Integer.MIN_VALUE - a)) {
            throw new ExpressionException("overflow");
        } else {
            return a + b;
        }
    }

    @Override
    protected Integer subtractElem(Integer a, Integer b) {
        if ((b <= 0 && a <= 0 && a <= b) || (a <= b && a - b <= 0) || (a >= b && a - b >= 0)) {
            return a - b;
        } else {
            throw new ExpressionException("overflow");
        }
    }

    @Override
    protected Integer mtpElem(Integer a, Integer b) {
        if (a < 0 && b == 1 || a < 0 && b < 0 && a >= Integer.MAX_VALUE / b || a == 0 || b == 0 ||
                a < 0 && b > 0 && a == a * b / b
                || b < 0 && a > 0 && b == a * b / a || a > 0 && b > 0 && a <= Integer.MAX_VALUE / b) {
            return a * b;
        } else {
            throw new ExpressionException("overflow");
        }
    }

    @Override
    protected Integer divElem(Integer a, Integer b) {
        if (b == 0) {
            throw new ExpressionException("division by zero");
        }
        int result = a / b;
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ExpressionException("overflow");
        }
        return result;
    }

    @Override
    protected Integer cnst(String str) {
        return Integer.parseInt(str);
    }

    @Override
    protected Integer castNumberToGen(int num) {
        return num;
    }
}
