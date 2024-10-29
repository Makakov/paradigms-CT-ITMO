package expression.generic;

import expression.exceptions.ExpressionException;
import expression.generic.operations.Add;
import expression.generic.operations.Const;
import expression.generic.parser.ExpressionParser;

public class Test {
    public static void main(String[] args) throws ExpressionException {
        GenericOperation<Double> operation = new DoubleGen<>();
        ExpressionParser<Double> parser = new ExpressionParser<>(operation);
        for (int i = 0; i < 5; i++) {
            System.out.println(parser.parse("x").evaluate((double) i, 10.0, 10.0));
        }
    }
}
