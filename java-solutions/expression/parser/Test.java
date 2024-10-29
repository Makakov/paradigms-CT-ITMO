package expression.parser;

import expression.exceptions.*;

public class Test {
    public static void main(String[] args) throws ExpressionException {
        String str = "l0x";
        CharSource source = new StringSource(str);
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse(str).evaluate(1266102068, -924521350, 579630531));
        //System.out.println(new CheckedDivide(new Const(10), new Const(0)).evaluate(1));
//        System.out.println(new Multiply(new Const(Integer.MIN_VALUE),
//                new Const(-20)).evaluate(1));
//        System.out.println(new Negate(new Const(Integer.MIN_VALUE - 1)).evaluate(1));
        //System.out.println(new CheckedSubtract(new Const(Integer.MIN_VALUE), new Const(Integer.MIN_VALUE + 1)).evaluate(1));
        //System.out.println(new CheckedAdd(new Const(Integer.MIN_VALUE + 1), new Const(Integer.MIN_VALUE)).evaluate(1));
    }
}
