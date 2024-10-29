package expression.generic;

import expression.exceptions.ExpressionException;

public class UncheckedInteger<T> extends AbstractGen<Integer> implements GenericOperation<Integer> {
    @Override
    protected Integer neg(Integer a) {
        return -a;
    }

    @Override
    protected Integer addElem(Integer a, Integer b) {
        return a + b;
    }

    @Override
    protected Integer subtractElem(Integer a, Integer b) {
        return a - b;
    }

    @Override
    protected Integer mtpElem(Integer a, Integer b) {
        return a * b;
    }

    @Override
    protected Integer divElem(Integer a, Integer b) {
        // :NOTE: DBZ
        if (b == 0) {
            throw new ExpressionException("division by zero");
        }
        return a / b;
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
