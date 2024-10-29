package expression.exceptions;

import expression.Binary;
import expression.Const;
import expression.OwnExpression;

import java.util.List;

public class CheckedConst implements OwnExpression {
    private final int const1;

    public CheckedConst(int const1) {
        this.const1 = const1;
    }

    @Override
    public int evaluate(int a) {
        return const1;
    }
    @Override
    public int evaluate(int a, int b, int c) {
        return const1;
    }

    @Override
    public String toString() {
        return String.valueOf(const1);
    }

    public int getRight() {
        return const1;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() == CheckedConst.class) {
            return const1 == ((CheckedConst) object).getRight();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return const1 * 69;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return const1;
    }
}