package unitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidVariableNameException;
import expressions.Expression;
import expressions.VariableList;

public class ExpressionTest {
	private static final double DELTA = 1e-15;
	Expression expression;
	double pi;

	@Before
	public void setUp() throws Exception, InvalidVariableNameException {
		expression = new Expression("");
		VariableList.createIfEmpty();
	}

	@Test
	public void testEvaluate() throws Exception {
		expression.setExpression("1+1");
		assertEquals(2, expression.evaluate(), DELTA);
		expression.setExpression("sin(pi/2)");
		assertEquals(1, expression.evaluate(), DELTA);

	}

	@Test
	public void testEvaluateString() throws Exception {
		assertEquals(2, Expression.evaluate("1+1"), DELTA);
		assertEquals(1, Expression.evaluate("sin(pi/2)"), DELTA);
	}

}
