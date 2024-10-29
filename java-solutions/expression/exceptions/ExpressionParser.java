package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;

import java.util.List;

public final class ExpressionParser extends BaseParser implements TripleParser, ListParser {
    private int balance;
    private List<String> list;
    private boolean isInList;
    private int position;

    @Override
    public OwnExpression parse(String expression) throws ExpressionException {
        startParsing(false, expression);
        take();
        OwnExpression result = parseOperation(0);
        check();
        return result;
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws ExpressionException {
        startParsing(true, expression);
        this.list = variables;
        take();
        OwnExpression result = parseOperation(0);
        check();
        return result;
    }

    private void startParsing(boolean flag, String expression) {
        this.source = new StringSource(expression);
        balance = 0;
        position = 0;
        isInList = flag;
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

    private OwnExpression parseOperation(int priority) throws ExpressionException {
        if (priority == 0) {
            OwnExpression result = parseOperation(priority + 1);
            skipWhitespace();
            result = parseIsNextOperator(result, 0, '+', '-');
            return result;
        } else if (priority == 1) {
            OwnExpression result = parsePriority2();
            skipWhitespace();
            result = parseIsNextOperator(result, 1, '*', '/');
            return result;
        } else {
            return parsePriority2();
        }
    }

    private OwnExpression parseIsNextOperator(OwnExpression result, int priority, char... operators) throws ExpressionException {
        while (isNextOperator(operators)) {
            char operator = take();
            position++;
            skipWhitespace();
            if (eof()) {
                throw new ExpressionException("Operation without operands: " + operator, position - 1);
            }
            OwnExpression right = parseOperation(priority + 1);
            skipWhitespace();
            switch (operator) {
                case '+' -> result = new CheckedAdd(result, right);
                case '-' -> result = new CheckedSubtract(result, right);
                case '*' -> result = new CheckedMultiply(result, right);
                case '/' -> result = new CheckedDivide(result, right);
            }
            skipWhitespace();
        }
        return result;
    }

    private OwnExpression parsePriority2() throws ExpressionException {
        skipWhitespace();
        if (take('-')) {
            position++;
            if (between('0', '9')) {
                return parseNegativeNumber();
            } else {
                OwnExpression result = parsePriority1();
                return new CheckedNegate(result);
            }
        }
        return parsePriority1();
    }

    private OwnExpression ifBracket() throws ExpressionException {
        this.balance++;
        OwnExpression result = parseOperation(0);
        expect(')');
        this.balance--;
        return result;
    }

    private OwnExpression parseZeroes(boolean isL, char ch) throws ExpressionException {
        if (test('0')) {
            take();
            position++;
            if (!test('(') && !test(' ')) {
                if (isL) {
                    throw new ExpressionException("Wrong LZeroes", position);
                }
                throw new ExpressionException("Wrong RZeroes", position);
            }
            OwnExpression lz;
            if (test('(')) {
                take();
                skipWhitespace();
                if (isL) {
                    lz = new LZeroes(parseOperation(0));
                } else {
                    lz = new RZeroes(parseOperation(0));
                }
                expect(')');
                return lz;
            } else if (Character.isLetter(ch) || Character.isDigit(ch)) {
                return isL ? new LZeroes(parseOperation(0)) : new RZeroes(parseOperation(0));
            } else {
                if (isL) {
                    throw new ExpressionException("Wrong LZeroes", position);
                }
                throw new ExpressionException("Wrong RZeroes", position);
            }
        }
        throw new ExpressionException("Wrong Variable type " + getPrevChar(), position);
    }

    private OwnExpression parsePriority1() throws ExpressionException {
        skipWhitespace();
        char ch = take();
        position++;
        if (ch == '(') {
            return ifBracket();
        } else if (Character.isDigit(ch)) {
            return new CheckedConst(parseNum(ch));
        } else if (Character.isLetter(ch) || ch == '$') {
            if (ch == 'l') {
                return parseZeroes(true, ch);
            } else if (ch == 't') {
                return parseZeroes(false, ch);
            } else {
                String var = parseVar(ch);
                if (var.length() == 1) {
                    return new Variable(parseVar(ch));
                }
                return new Variable(list.indexOf(var));
            }
        } else if (ch == '-') {
            if (between('x', 'z')) {
                return new CheckedNegate(new Variable(parseVar(take())));
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

    private int parseNum(char ch) throws ExpressionException {
        StringBuilder number = new StringBuilder();
        number.append(ch);
        while (between('0', '9')) {
            char nextChar = take();
            position++;
            number.append(nextChar);
        }
        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException e) {
            throw new ExpressionException("Overflow argument " + number + ",", position);
        }
    }

    private OwnExpression parseNegativeNumber() throws ExpressionException {
        StringBuilder sb = new StringBuilder("-");
        do {
            sb.append(take());
            position++;
        } while (between('0', '9'));
        try {
            return new CheckedConst(Integer.parseInt(sb.toString()));
        } catch (NumberFormatException e) {
            throw new ExpressionException("Overflow argument " + sb + ",", position);
        }
    }

    private String parseVar(char ch1) throws ExpressionException {
        StringBuilder var = new StringBuilder();
        position++;
        var.append(ch1);
        if (!isInList) {
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
        } else {
            if (between('0', '9')) {
                char ch = take();
                position++;
                while (between('0', '9')) {
                    var.append(ch);
                    ch = take();
                    position++;
                }
                if (ch >= '0' && ch <= '9') {
                    var.append(ch);
                }
            }
            if (list.contains(var.toString())) {
                return var.toString();
            }
            throw new ExpressionException("There is no such Variable in the List: " + var, position);
        }
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
