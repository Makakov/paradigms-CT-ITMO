package expression.generic.operations;

public class Const<T extends Number> implements TripleExpression<T> {
    private final T const1;

    public Const(T const1) {
        this.const1 = const1;
    }

    @Override
    public T evaluate(T a, T b, T c) {
        return const1;
    }

    @Override
    public String toString() {
        return String.valueOf(const1);
    }
}
