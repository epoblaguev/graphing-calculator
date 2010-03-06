/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.io.Serializable;

/**
 *
 * @author Egor
 */
public class Expression implements Serializable{

    private String expression;
    private double value;
    private String angleUnits;

    public Expression(String expression) {
        this.expression = Expression.formatExpression(expression);
    }

    public static String formatExpression(String expression){
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

    public double getValue() {
        return value;
    }

    public String getAngleUnits(){
        return angleUnits;
    }

    public double evaluate() {

        MathEvaluator m = new MathEvaluator(this.expression);

        for (Variable var : VariableList.getVariables()) {
            m.addVariable(var.getVariableName(), var.getVariableValue());
        }

        if(m.isUsingRadians()){
            this.angleUnits = "RAD";
        }
        else{
            this.angleUnits = "DEG";
        }
        this.value = m.getValue();
        return value;
    }

    public static double evaluate(String expression){
        Expression expr = new Expression(expression);
        return expr.evaluate();
    }
}
