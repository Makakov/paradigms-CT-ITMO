package expression.parser;

import expression.exceptions.ExpressionException;

public class BaseParser {
    private static final char END = '\0';
    protected CharSource source;
    private char ch = 0xffff;
    private char prevChar = 0xffff;

    protected char take() {
        final char result = ch;
        prevChar = result;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected char getChar() {
        return ch;
    }

    protected char getPrevChar() {
        return prevChar;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void skipWhitespace() {
        while (!eof() && Character.isWhitespace(ch)) {
            take();
        }
    }

    protected void expect(final char expected) throws ExpressionException {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    protected void expect(final String value) throws ExpressionException {
        for (final char c : value.toCharArray()) {
            expect(c);
        }
    }


    protected boolean eof() {
        return take(END);
    }

    protected ExpressionException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
