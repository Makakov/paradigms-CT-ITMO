package expression.exceptions;

import expression.OwnExpression;

import java.util.List;

public class RZeroes implements OwnExpression {
    private OwnExpression num;

    public RZeroes(OwnExpression num) {
        this.num = num;
    }

    @Override
    public int evaluate(int a) {
        if (num.evaluate(a) == 0) {
            return 32;
        }
        String str = Integer.toBinaryString(num.evaluate(a));
        int cnt = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                cnt++;
            } else {
                break;
            }
        }
        return cnt;
    }

    @Override
    public int evaluate(int a, int b, int c) {
        if (num.evaluate(a, b, c) == 0) {
            return 32;
        }
        String str = Integer.toBinaryString(num.evaluate(a, b, c));
        int cnt = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                cnt++;
            } else {
                break;
            }
        }
        return cnt;
    }

    @Override
    public int evaluate(List<Integer> list) {
        if (num.evaluate(list) == 0) {
            return 32;
        }
        String str = Integer.toBinaryString(num.evaluate(list));
        int cnt = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '0') {
                cnt++;
            } else {
                break;
            }
        }
        return cnt;
    }

    public String toString() {
        return "t0(" + num + ")";
    }
}
