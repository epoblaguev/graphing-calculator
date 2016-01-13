/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressions;

import java.io.Serializable;
import java.util.Vector;

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
		this.expression = expression;
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
		Vector<Variable> variables = VariableList.getVariables();

		for (Variable var : variables) {
			expBuilder.variable(var.getVariableName());
		}

		net.objecthunter.exp4j.Expression equation = expBuilder.build();

		for (Variable var : variables) {
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
