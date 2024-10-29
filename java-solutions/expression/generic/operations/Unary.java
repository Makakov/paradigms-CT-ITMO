package expression.generic.operations;

import expression.exceptions.ExpressionException;
import expression.generic.GenericOperation;

public abstract class Unary<T extends Number> implements TripleExpression<T> {
    protected final TripleExpression<T> expression;

    protected final GenericOperation<T> operation;
    public Unary(TripleExpression<T> expression, GenericOperation<T> operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public abstract T evaluate(T a, T b, T c) throws ExpressionException;
}