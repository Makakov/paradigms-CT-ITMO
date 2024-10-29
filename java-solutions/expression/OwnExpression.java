package expression;

import java.util.List;

public interface OwnExpression extends Expression, TripleExpression, ListExpression {
    int evaluate(int a);

    String toString();

    int evaluate(int a, int b, int c);

    int evaluate(List<Integer> list);
}
