/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equations;

import expressions.Expression;
import expressions.IEvaluator;
import expressions.EquationEvaluator;
import expressions.Variable;
import expressions.VariableList;

import java.awt.Color;
import java.io.Serializable;

import net.objecthunter.exp4j.ExpressionBuilder;

//import net.objecthunter.exp4j.ExpressionBuilder;

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

	public static double evaluate(String expression, double x, boolean formatFirst) {

		/*
		 * if (formatFirst) { expression =
		 * Expression.formatExpression(expression); } try { IEvaluator m = new
		 * EquationEvaluator(expression);
		 * 
		 * for (Variable var : VariableList.getVariables()) {
		 * m.addVariable(var.getVariableName(), var.getVariableValue()); }
		 * 
		 * m.addVariable("x", x);
		 * 
		 * return m.getValue(); } catch (Exception e) { return 0; }
		 */

		ExpressionBuilder expBuilder = new ExpressionBuilder(expression);
		expBuilder.variable("x");

		for (Variable var : VariableList.getVariables()) {
			expBuilder.variable(var.getVariableName());
		}
		net.objecthunter.exp4j.Expression newExpression = expBuilder.build();

		for (Variable var : VariableList.getVariables()) {
			newExpression.setVariable(var.getVariableName(), var.getVariableValue());
		}
		newExpression.setVariable("x", x);
		try {
			return newExpression.evaluate();
		} catch (Exception e) {
			return 0;
		}

	}
}
