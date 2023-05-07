import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class CalculatorMainListener extends CalculatorBaseListener {
    public static void main(String[] args) throws Exception {
        CharStream charStreams = CharStreams.fromFileName("./example.txt");
        Integer result = calc("-12 - 2");
        System.out.println("Result = " + result);
    }

    Deque<Integer> numbers = new ArrayDeque<>();



    /*ten dzialal najlepiej z gramatyka ktora dzialala najlepiej*/
    @Override
    public void enterExpression(CalculatorParser.ExpressionContext ctx) {
        System.out.println("enterExpression: " + ctx.getText());
        super.enterExpression(ctx);
    }

    @Override
    public void exitExpression(CalculatorParser.ExpressionContext ctx) {
        System.out.println("exitExpression: " + ctx.getText());
        Integer value = numbers.pop();
        for (int i = 1; i < ctx.getChildCount(); i = i + 2){
            if (Objects.equals(ctx.getChild(i).getText(), "+")){
                value = value + numbers.pop();

            } else{

                value = value - numbers.pop();
            }
        }
        numbers.add(value);
        super.exitExpression(ctx);
    }




    @Override
    public void enterIntegralExpression(CalculatorParser.IntegralExpressionContext ctx) {
        System.out.println("enterIntegralExpression: " + ctx.getText());
        super.enterIntegralExpression(ctx);
    }

    @Override
    public void exitIntegralExpression(CalculatorParser.IntegralExpressionContext ctx) {
        System.out.println("exitIntegralExpression: " + ctx.getText());
        if(ctx.MINUS() != null){
            numbers.add(-1 * Integer.valueOf(ctx.INT().toString()));
        } else {
            numbers.add(Integer.valueOf(ctx.INT().toString()));
        }
        super.exitIntegralExpression(ctx);
    }


    @Override
    public void enterMultiplyingExpression(CalculatorParser.MultiplyingExpressionContext ctx) {
        System.out.println("enterMultiplyingExpression: " + ctx.getText());
        super.enterMultiplyingExpression(ctx);
    }

    @Override
    public void exitMultiplyingExpression(CalculatorParser.MultiplyingExpressionContext ctx) {
        System.out.println("exitMultiplyingExpression: " + ctx.getText());
        Integer value = numbers.pop();
        for (int i = 1; i < ctx.getChildCount(); i = i + 2){
            if (Objects.equals(ctx.getChild(i).getText(), "*")){
                value = value * numbers.pop();

            } else if (Objects.equals(ctx.getChild(i).getText(), "/")){

                value = value / numbers.pop();
            }
        }
        numbers.add(value);
        super.exitMultiplyingExpression(ctx);
    }

    @Override
    public void enterPowExpression(CalculatorParser.PowExpressionContext ctx) {
        System.out.println("enterPowExpression: " + ctx.getText());
        super.enterPowExpression(ctx);
    }

    @Override
    public void exitPowExpression(CalculatorParser.PowExpressionContext ctx) {
        System.out.println("exitPowExpression: " + ctx.getText());
        Integer base = numbers.pop();
        Integer exp = numbers.pop();
        numbers.push((int)Math.pow(base, exp));
        super.exitPowExpression(ctx);
    }

    @Override
    public void enterSqrtExpression(CalculatorParser.SqrtExpressionContext ctx) {
        System.out.println("enterSqrtExpression: " + ctx.getText());
        super.enterSqrtExpression(ctx);
    }

    @Override
    public void exitSqrtExpression(CalculatorParser.SqrtExpressionContext ctx) {
        System.out.println("exitSqrtExpression: " + ctx.getText());
        Integer base = numbers.pop();
        numbers.push((int)Math.sqrt(base));
        super.exitSqrtExpression(ctx);
    }








    private Integer getResult() {
        return numbers.peek();
    }

    public static Integer calc(String expression) {
        return calc(CharStreams.fromString(expression));
    }

    public static Integer calc(CharStream charStream) {
        CalculatorLexer lexer = new CalculatorLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expression();

        ParseTreeWalker walker = new ParseTreeWalker();
        CalculatorMainListener mainListener = new CalculatorMainListener();
        walker.walk(mainListener, tree);
        return mainListener.getResult();
    }


}
