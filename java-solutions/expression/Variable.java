package expression;

import expression.exceptions.ExpressionException;

import java.util.List;

public class Variable implements OwnExpression, ListExpression {
    private String var;

    public Variable(int idx) {
        this.idx = idx;
    }

    private int idx;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int evaluate(int a) {
        return a;
    }

    @Override
    public int evaluate(int a, int b, int c) {
        if (var.equals("x")) {
            return a;
        }
        if (var.equals("y")) {
            return b;
        } else {
            return c;
        }
    }

    @Override
    public String toString() {
        if (var == null) {
            return "$" + idx;
        }
        return var;
    }

    public String getLeft() {
        return var;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() == Variable.class) {
            return var.equals(((Variable) object).getLeft());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return var.hashCode();
    }

    private boolean checkInList(int idx, List<Integer> list) {
        return idx >= 0 && idx < list.size();
    }

    @Override
    public int evaluate(List<Integer> variables) {
        if (checkInList(idx, variables)) {
            return variables.get(idx);
        }
        throw new ArithmeticException("not var");
    }
}