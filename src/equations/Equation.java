/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equations;

import expressions.Variable;
import expressions.VariableList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Egor
 */
public class Equation implements Serializable {

    private String expressionString;
	private Expression expression;
	private Color color;

    /**
     * Create an equation out of a string.
     * @param expressionString A string representing one side of a mathematical equation [Example: 5*log(x)]
     */
	public Equation(String expressionString){
		this(expressionString,null);
	}

    /**
     * Create a colored equation out of a string.
     * @param expressionString A string representing one side of a mathematical equation [Example: 5*log(x)]
     * @param color The color of the equation.
     */
	public Equation(String expressionString, Color color) {
		this.expressionString = expressionString;
		this.color = color;

		buildExpression(expressionString);
	}

	private void buildExpression(String exprStr){
        this.expressionString = exprStr;
		ExpressionBuilder expBuilder = new ExpressionBuilder(expressionString);
		Vector<Variable> variables = VariableList.getVariables();
		expBuilder.variable("x");

		for (Variable var : variables) {
			expBuilder.variable(var.getVariableName());
		}
		expression = expBuilder.build();

		for (Variable var : variables) {
			expression.setVariable(var.getVariableName(), var.getVariableValue());
		}
	}

    /**
     * Returns the color of the equation.
     * @return The color of the equation.
     */
	public Color getColor() {
		return color;
	}

    /**
     * Sets the color of the equation.
     * @param color The new color of the equation.
     */
	public void setColor(Color color) {
		this.color = color;
	}

    /**
     * Returns the mathematical expression as a string.
     * @return The mathematical expression as a string.
     */
	public String getExpression() {
		return expressionString;
	}

    /**
     * Sets the new mathematical expression, and rebuilds the equation. Updates the variables from the variable list.
     * @param expression The new mathematical expression.
     */
	public void setExpression(String expression) {
		buildExpression(expression);
	}

    /**
     * Evaluates the equation for a given value of 'x'
     * @param x The value of 'x' for which to evaluate the equation.
     * @return The result of the equation for a given value of 'x'.
     */
	public double evaluate(double x) {
		try {
			expression.setVariable("x",x);
			return expression.evaluate();
		} catch (Exception e) {
			System.out.println("Exception thrown while evaluating '" + expression + "' " + e);
			return 0;
		}

	}
}
