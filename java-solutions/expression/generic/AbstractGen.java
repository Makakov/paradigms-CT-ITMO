package expression.generic;

import expression.exceptions.ExpressionException;

public abstract class AbstractGen<T extends Number> implements GenericOperation<T> {
    @Override
    public T negate(T a) throws ExpressionException {
        return neg(a);
    }
    protected abstract T neg(T a);

    protected abstract T addElem(T a, T b);

    protected abstract T subtractElem(T a, T b);

    protected abstract T mtpElem(T a, T b);

    protected abstract T divElem(T a, T b);

    protected abstract T cnst(String str);

    protected abstract T castNumberToGen(int num);

    @Override
    public T add(T a, T b) throws ExpressionException {
        return addElem(a, b);
    }

    @Override
    public T multiply(T a, T b) throws ExpressionException {
        return mtpElem(a, b);
    }

    @Override
    public T divide(T a, T b) throws ExpressionException {
        return divElem(a, b);
    }

    @Override
    public T subtract(T a, T b) throws ExpressionException {
        return subtractElem(a, b);
    }

    @Override
    public T constant(String string) {
        return cnst(string);
    }

    @Override
    public T fromIntegerToGen(int a) {
        return castNumberToGen(a);
    }
}
