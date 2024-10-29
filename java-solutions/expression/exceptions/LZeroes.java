package expression.exceptions;

import expression.OwnExpression;

import java.util.List;

public class LZeroes implements OwnExpression {
    private OwnExpression num;

    public LZeroes(OwnExpression num) {
        this.num = num;
    }

    @Override
    public int evaluate(int a) {
        return 32 - Integer.toBinaryString(num.evaluate(a)).length();
    }

    @Override
    public int evaluate(int a, int b, int c) {
        if (num.evaluate(a, b, c) != 0) {
            return 32 - Integer.toBinaryString(num.evaluate(a, b, c)).length();
        }
        return 32;
    }

    @Override
    public int evaluate(List<Integer> list) {
        return 32 - Integer.toBinaryString(num.evaluate(list)).length();
    }
    public String toString() {
        return "l0(" + num + ")";
    }
}
