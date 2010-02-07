/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

/**
 *
 * @author Egor
 */
public class Expression {

    private String expression;

    public Expression(String expression) {
        this.expression = this.formatExpression(expression);
    }

    private String formatExpression(String expression){
        expression = expression.replace(" ", "");
        
        for (int i = 0; i <= 9; i++) {
            expression = expression.replace(i + "(", i + "*(");

            for (char c = 'a'; c <= 'z'; c++) {
                expression = expression.replace(i + String.valueOf(c), i + "*" + c);
                expression = expression.replace(i + String.valueOf(c).toUpperCase(), i + "*" + String.valueOf(c).toUpperCase());
            }
        }

        expression = expression.replace(")(", ")*(");
        
        return expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double evaluate() {

        MathEvaluator m = new MathEvaluator(this.expression);
        m.setUsingRadians(false);

        for (Variable var : VariableList.getVariableList()) {
            m.addVariable(var.getVariableName(), var.getVariableValue());
        }

        return m.getValue();
    }
}
