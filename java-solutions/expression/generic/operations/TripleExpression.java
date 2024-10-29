package expression.generic.operations;

public interface TripleExpression<T extends Number> {
    T evaluate(T a, T b, T c) throws ArithmeticException;
}
