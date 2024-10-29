package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.generic.GenericOperation;

public class Subtract<T extends Number> extends Binary<T> {

//    private final GenericOperation<T> operation;

    public Subtract(TripleExpression<T> var1, TripleExpression<T> var2, GenericOperation<T> operation) {
        super(var1, var2);
        this.operation = operation;
    }

    @Override
    protected T calc(T a, T b) throws ExpressionException {
        return operation.subtract(a, b);
    }

    @Override
    protected String getSign() {
        return "-";
    }
}
