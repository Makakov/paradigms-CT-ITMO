package expression.generic;

public class DoubleGen<T> implements GenericOperation<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double constant(String string) {
        return Double.parseDouble(string);
    }

    @Override
    public Double fromIntegerToGen(int a) {
        return (double) a;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

}
