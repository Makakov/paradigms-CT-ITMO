package expression.generic.operations;

public class Variable<T extends Number> implements TripleExpression<T> {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }
    @Override
    public T evaluate(T a, T b, T c) {
        switch (var) {
            case "x" -> {
                return a;
            }
            case "y" -> {
                return b;
            }
            case "z" -> {
                return c;
            }
            default -> {
                return null;
            }
        }
    }
}
