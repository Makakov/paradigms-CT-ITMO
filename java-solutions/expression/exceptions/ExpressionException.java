package expression.exceptions;

// :NOTE: ошибки вычисления не должны быть проверяемыми
public class ExpressionException extends ArithmeticException {
    private final int position;
    public ExpressionException(String message, int position) {
        super(message + " at position " + position);
        this.position = position;
    }
    public ExpressionException(String message) {
        super(message);
        this.position = getPosition();
    }
    public int getPosition() {
        return position;
    }
}
