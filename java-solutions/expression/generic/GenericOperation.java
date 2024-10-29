package expression.generic;

import expression.exceptions.ExpressionException;

public interface GenericOperation<T extends Number> {
    T add(T a, T b) throws ExpressionException;

    T multiply(T a, T b) throws ExpressionException;

    T divide(T a, T b) throws ExpressionException;

    T subtract(T a, T b) throws ExpressionException;

    T constant(String string);

    T fromIntegerToGen(int a);

    T negate(T a);

    // :NOTE: negate
}
