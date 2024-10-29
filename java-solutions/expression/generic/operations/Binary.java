package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.generic.GenericOperation;

public abstract class Binary<T extends Number> implements TripleExpression<T> {

    protected GenericOperation<T> operation;
    private final TripleExpression<T> var1;
    private final TripleExpression<T> var2;

    public Binary(TripleExpression<T> var1, TripleExpression<T> var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    protected abstract T calc(T a, T b) throws ExpressionException;

    protected abstract String getSign();

    @Override
    public String toString() {
        return "(" + var1 + " " + getSign() + " " + var2 + ")";
    }

    @Override
    public T evaluate(T a, T b, T c) throws ExpressionException {
        return calc(var1.evaluate(a, b, c), var2.evaluate(a, b, c));
    }
}
