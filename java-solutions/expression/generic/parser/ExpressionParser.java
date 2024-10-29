package expression.generic.parser;

import expression.exceptions.ExpressionException;
import expression.generic.GenericOperation;
import expression.generic.operations.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;

public final class ExpressionParser<T extends Number> extends BaseParser {
    private int balance;
    private int position;
    private final GenericOperation<T> operation;

    public ExpressionParser(GenericOperation<T> operation) {
        this.operation = operation;
    }

    public TripleExpression<T> parse(String expression) throws ExpressionException {
        startParsing(expression);
        take();
        TripleExpression<T> result = parseOperation(0);
        check();
        return result;
    }

    private void startParsing(String expression) {
        this.source = new StringSource(expression);
        balance = 0;
        position = 0;
    }

    private void check() throws ExpressionException {
        if (balance != 0) {
            throw new ExpressionException("Wrong brackets");
        }
        if (!eof()) {
            if (balance < 0) {
                throw new ExpressionException("No opening parenthesis");
            }
            throw new ExpressionException("The end of expression reached, but there is unknown data: " + getChar());
        }
    }

    private TripleExpression<T> parseOperation(int priority) throws ExpressionException {
        if (priority == 0) {
            TripleExpression<T> result = parseOperation(priority + 1);
            skipWhitespace();
            result = parseIsNextOperator(result, 0, '+', '-');
            return result;
        } else if (priority == 1) {
            TripleExpression<T> result = parsePriority2();
            skipWhitespace();
            result = parseIsNextOperator(result, 1, '*', '/');
            return result;
        } else {
            return parsePriority2();
        }
    }

    private TripleExpression<T> parseIsNextOperator(TripleExpression<T> result, int priority, char... operators) throws ExpressionException {
        while (isNextOperator(operators)) {
            char operator = take();
            position++;
            skipWhitespace();
            if (eof()) {
                throw new ExpressionException("Operation without operands: " + operator, position - 1);
            }
            TripleExpression<T> right = parseOperation(priority + 1);
            skipWhitespace();
            switch (operator) {
                case '+' -> result = new Add<>(result, right, operation);
                case '-' -> result = new Subtract<>(result, right, operation);
                case '*' -> result = new Multiply<>(result, right, operation);
                case '/' -> result = new Divide<>(result, right, operation);
            }
            skipWhitespace();
        }
        return result;
    }

    private TripleExpression<T> parsePriority2() throws ExpressionException {
        skipWhitespace();
        if (take('-')) {
            position++;
            if (between('0', '9')) {
                return parseNegativeNumber();
            } else {
                TripleExpression<T> result = parsePriority1();
                return new Negate<>(result, operation);
            }
        }
        return parsePriority1();
    }

    private TripleExpression<T> ifBracket() throws ExpressionException {
        this.balance++;
        TripleExpression<T> result = parseOperation(0);
        expect(')');
        this.balance--;
        return result;
    }

    private TripleExpression<T> parsePriority1() throws ExpressionException {
        skipWhitespace();
        char ch = take();
        position++;
        if (ch == '(') {
            return ifBracket();
        } else if (Character.isDigit(ch)) {
            return new Const<T>(parseNum(ch));
        } else if (Character.isLetter(ch)) {
            return new Variable<T>(parseVar(ch));
        } else if (ch == '-') {
            if (between('x', 'z')) {
                return new Negate<T>(new Variable<T>(parseVar(take())), operation);
            } else if (between('0', '9')) {
                return parseNegativeNumber();
            }
        } else if (!Character.isDigit(ch) && !Character.isLetter(ch)) {
            if (position != 1) {
                throw new ExpressionException("Missing argument", position - 1);
            }
            throw new ExpressionException("Expression cannot start with an operator: " + ch, position);
        }
        return parseOperation(0);
    }

    private T parseNum(char ch) throws ExpressionException {
        StringBuilder number = new StringBuilder();
        number.append(ch);
        while (between('0', '9')) {
            char nextChar = take();
            position++;
            number.append(nextChar);
        }
        try {
            return operation.constant(number.toString());
        } catch (NumberFormatException e) {
            throw new ExpressionException("Overflow argument " + number + ",", position);
        }
    }

    private TripleExpression<T> parseNegativeNumber() throws ExpressionException {
        StringBuilder sb = new StringBuilder("-");
        do {
            sb.append(take());
            position++;
        } while (between('0', '9'));
        try {
            return new Const<T>(operation.constant(sb.toString()));
        } catch (NumberFormatException e) {
            throw new ExpressionException("Overflow argument " + sb + ",", position);
        }
    }

    private String parseVar(char ch1) throws ExpressionException {
        StringBuilder var = new StringBuilder();
        position++;
        var.append(ch1);
        if (between('x', 'z')) {
            char ch = take();
            position++;
            while (between('x', 'z')) {
                var.append(ch);
                ch = take();
                position++;
            }
            var.append(ch);
        }
        if (var.toString().equals("x") || var.toString().equals("y") || var.toString().equals("z")) {
            return var.toString();
        }
        throw new ExpressionException("Wrong variable type: " + var + ",", position - 2);
    }

    private boolean isNextOperator(char... operators) {
        for (char op : operators) {
            if (test(op)) {
                return true;
            }
        }
        return false;
    }
}
