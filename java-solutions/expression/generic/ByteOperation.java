package expression.generic;

import expression.exceptions.ExpressionException;

public class ByteOperation<T> extends AbstractGen<Byte> implements GenericOperation<Byte> {
    @Override
    protected Byte neg(Byte a) {
        return (byte) -a;
    }

    @Override
    protected Byte addElem(Byte a, Byte b) {
        return (byte) (a + b);
    }

    @Override
    protected Byte subtractElem(Byte a, Byte b) {
        return (byte) (a - b);
    }

    @Override
    protected Byte mtpElem(Byte a, Byte b) {
        return (byte) (a * b);
    }

    @Override
    protected Byte divElem(Byte a, Byte b) {
        if (b == 0) {
            throw new ExpressionException("division by zero");
        }
        return (byte) (a / b);
    }

    @Override
    protected Byte cnst(String str) {
        return Byte.parseByte(str);
    }

    @Override
    protected Byte castNumberToGen(int num) {
        return (byte) num;
    }
}
