package expression.generic;

import expression.exceptions.ExpressionException;

import java.math.BigInteger;
import java.util.Objects;

// :NOTE: зачем здесь <T>
public class BigIntegerGen<T> implements GenericOperation<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) throws ExpressionException {
        // :NOTE: BigInteger.ZERO instead of BigInteger.valueOf(0)
        if (Objects.equals(b, BigInteger.ZERO)) {
            throw new ExpressionException("division by zero");
        }
        return a.divide(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger constant(String string) {
        return new BigInteger(string);
    }

    @Override
    public BigInteger fromIntegerToGen(int a) {
        return BigInteger.valueOf(a);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.multiply(BigInteger.valueOf(-1));
    }
}
