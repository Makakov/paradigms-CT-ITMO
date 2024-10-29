package expression.generic;

import expression.exceptions.ExpressionException;
import expression.generic.operations.TripleExpression;
import expression.generic.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ExpressionException {
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        // :NOTE: копипаста
        switch (mode) {
            case "d" ->
                    middleTabulator(new DoubleGen<>(), new ExpressionParser<>(new DoubleGen<>()), x1, x2, y1, y2, z1, z2, expression, table);
            case "i" ->
                    middleTabulator(new IntegerGen<>(), new ExpressionParser<>(new IntegerGen<>()), x1, x2, y1, y2, z1, z2, expression, table);
            case "bi" ->
                    middleTabulator(new BigIntegerGen<>(), new ExpressionParser<>(new BigIntegerGen<>()), x1, x2, y1, y2, z1, z2, expression, table);
            case "u" ->
                    middleTabulator(new UncheckedInteger<>(), new ExpressionParser<>(new UncheckedInteger<>()), x1, x2, y1, y2, z1, z2, expression, table);
            case "b" ->
                    middleTabulator(new ByteOperation<>(), new ExpressionParser<>(new ByteOperation<>()), x1, x2, y1, y2, z1, z2, expression, table);
            default -> {
            }
        }
        return table;
    }

    private <T extends Number> void middleTabulator(GenericOperation<T> operation, ExpressionParser<T> parser,
                                                    int x1, int x2, int y1, int y2, int z1, int z2,
                                                    String expression, Object[][][] table) throws ExpressionException {
        genericStart(operation, parser, x1, x2, y1, y2, z1, z2, expression, table);
    }

    public static <T extends Number> void genericStart(GenericOperation<T> operation, ExpressionParser<T> parser,
                                                       int x1, int x2, int y1, int y2, int z1, int z2,
                                                       String str, Object[][][] table) throws ExpressionException {
        TripleExpression<T> expr = parser.parse(str);
        fillTable(table, x1, x2, y1, y2, z1, z2, expr, operation);
    }

    private static <T extends Number> void fillTable(Object[][][] table, int x1, int x2, int y1, int y2, int z1, int z2, TripleExpression<T> expr, GenericOperation<T> operator) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = expr.evaluate(operator.fromIntegerToGen(i), operator.fromIntegerToGen(j), operator.fromIntegerToGen(k));
                    } catch (ArithmeticException e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
    }
}
