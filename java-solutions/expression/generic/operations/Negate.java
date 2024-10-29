package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.generic.GenericOperation;

public class Negate<T extends Number> extends Unary<T> {

    public Negate(TripleExpression<T> expression, GenericOperation<T> operation) {
        super(expression, operation);
    }

    @Override
    public T evaluate(T a, T b, T c) throws ExpressionException {
        return operation.negate(expression.evaluate(a, b, c));
    }
}