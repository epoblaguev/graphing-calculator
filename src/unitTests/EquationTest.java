package unitTests;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import equations.Equation;
import exceptions.InvalidVariableNameException;
import expressions.VariableList;

public class EquationTest {
	private Equation equation;
	private static final double DELTA = 1e-15;

	@Before
	public void setUp() throws Exception, InvalidVariableNameException {
		equation = new Equation("1+x", Color.RED);
		VariableList.createIfEmpty();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEvaluate() {
		equation.setExpression("1+x");
		assertEquals((double)2, equation.evaluate(1),DELTA);
		equation.setExpression("1/x");
		assertEquals((double)0, equation.evaluate(0),DELTA); //Makes it easy to handle division by zero in equations?
		equation.setExpression("sin(pi/x)");
		assertEquals((double)0, equation.evaluate(0),DELTA); //Makes it easy to handle division by zero in equations?
		assertEquals((double)0, equation.evaluate(1),DELTA); //Makes it easy to handle division by zero in equations?
		assertEquals((double)1, equation.evaluate(2),DELTA); //Makes it easy to handle division by zero in equations?

	}

}
