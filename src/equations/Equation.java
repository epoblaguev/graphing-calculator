/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equations;

import expressions.Variable;
import expressions.VariableList;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author Egor
 */
public class Equation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 443195241156639504L;
	private String expression;
	private Color color;

	public Equation(String expression, Color color) {
		this.expression = expression;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public double evaluate(double x) {
		ExpressionBuilder expBuilder = new ExpressionBuilder(expression);
		Vector<Variable> variables = VariableList.getVariables();
		expBuilder.variable("x");

		for (Variable var : variables) {
			expBuilder.variable(var.getVariableName());
		}
		Expression expression = expBuilder.build();

		for (Variable var : variables) {
			expression.setVariable(var.getVariableName(), var.getVariableValue());
		}
		expression.setVariable("x", x);
		try {
			return expression.evaluate();
		} catch (Exception e) {
			//TODO: Why did I do this? Need to find a better solution.
			return 0;
		}

	}
}
