/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.io.Serializable;

/**
 *A Class to represent a math expression
 * @author Egor
 */
public class Expression implements Serializable{

    private String expression;
    private double value;
    private String angleUnits;

    /**
     * Constructor that takes a string representing the expression and formats it
     * @param expression
     */
    public Expression(String expression) {
        this.expression = Expression.formatExpression(expression);
    }
    /**
     * Formats the expression in a way that the machine can process
     * Adds an * in between any implicit multiplication situations
     * @param expression
     * @return
     */
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

    /**
     * Returns the formatted expression
     * @return
     */
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

        if(m.getAngleUnits() == MathEvaluator.RADIANS){
            this.angleUnits = "RAD";
        }
        else if(m.getAngleUnits() == MathEvaluator.DEGREES){
            this.angleUnits = "DEG";
        }else if(m.getAngleUnits() == MathEvaluator.GRADIANS){
            this.angleUnits = "GRAD";
        }
        this.value = m.getValue();
        return value;
    }

    public static double evaluate(String expression){
        Expression expr = new Expression(expression);
        return expr.evaluate();
    }
}
