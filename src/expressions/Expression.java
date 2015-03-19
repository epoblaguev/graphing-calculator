/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.io.Serializable;

import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * A Class to represent a math expression
 * 
 * @author Egor
 */
public class Expression implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7172720115921875406L;
	private String expression;
	private double value;
	private String angleUnits;

	/**
	 * Constructor that takes a string representing the expression and formats
	 * it
	 * 
	 * @param expression
	 */
	public Expression(String expression) {
		this.expression = Expression.formatExpression(expression);
	}

	/**
	 * Formats the expression in a way that the machine can process Adds an * in
	 * between any implicit multiplication situations
	 * 
	 * @param expression
	 * @return
	 */
	public static String formatExpression(String expression) {
		/*
		expression = expression.replace(" ", "");

		for (int i = 0; i <= 9; i++) {
			expression = expression.replace(i + "(", i + "*(");

			for (char c = 'a'; c <= 'z'; c++) {
				expression = expression.replace(i + String.valueOf(c), i + "*" + c);
				expression = expression.replace(i + String.valueOf(c).toUpperCase(), i + "*" + String.valueOf(c).toUpperCase());
			}
		}

		expression = expression.replace(")(", ")*(");
		*/
		return expression;
	}

	/**
	 * Returns the formatted expression
	 * 
	 * @return
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression to a new expression string
	 * 
	 * @param expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Gets the value of the expression
	 * 
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Gets the angle units for the expression (Radians,Degrees)
	 * 
	 * @return
	 */
	public String getAngleUnits() {
		return angleUnits;
	}

	/**
	 * Evaluates the expression
	 * 
	 * @return
	 * @throws Exception
	 */
	public double evaluate() throws Exception {

		ExpressionBuilder expBuilder = new ExpressionBuilder(this.expression);
		expBuilder.variable("x");

		for (Variable var : VariableList.getVariables()) {
			expBuilder.variable(var.getVariableName());
		}

		net.objecthunter.exp4j.Expression equation = expBuilder.build();

		for (Variable var : VariableList.getVariables()) {
			equation.setVariable(var.getVariableName(), var.getVariableValue());
		}
		this.value=equation.evaluate();
		return value;
	}

	/**
	 * Evaluates an expression
	 * 
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public static double evaluate(String expression) throws Exception {
		Expression expr = new Expression(expression);
		return expr.evaluate();
	}
}
