package expression.parser;

import expression.exceptions.ExpressionException;

public interface CharSource {
    boolean hasNext();
    char next();
    ExpressionException error(String message);
}
