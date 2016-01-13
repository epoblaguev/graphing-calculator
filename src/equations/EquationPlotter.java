package equations;

import java.awt.geom.GeneralPath;
import java.util.Vector;

import expressions.Variable;
import expressions.VariableList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class EquationPlotter implements Runnable {
	private GeneralPath polyline;
	private Expression expression;
	private double start, end, step;
	private boolean running;

	public EquationPlotter(String expressionString, GeneralPath polyline, double start, double end, double step) {
		Vector<Variable> variables = VariableList.getVariables();
		this.polyline = polyline;
		this.start = start;
		this.end = end;
		this.step = step;
		this.running = false;

		ExpressionBuilder expBuilder = new ExpressionBuilder(expressionString);
		expBuilder.variable("x");

		// Populate variables.
		for (Variable var : variables) {
			expBuilder.variable(var.getVariableName());
		}

		// Build expression.
		expression = expBuilder.build();

		// Populate variable values.
		for (Variable var : variables) {
			expression.setVariable(var.getVariableName(), var.getVariableValue());
		}
	}

	public GeneralPath getPolyline() {
		return polyline;
	}

	@Override
	public void run() {
		double y;
		this.running = true;
		polyline.moveTo(start, expression.setVariable("x", start).evaluate()); //First Position
		for (double x = start+step; x < end; x += step) {
			if (!this.running)
				break;
			
			expression.setVariable("x", x);
			y = expression.evaluate();
			polyline.lineTo(x, y);
			System.out.println(this.getClass().getName() + String.format(": x=%s, y=%s", x, y));
		}
	}
}
