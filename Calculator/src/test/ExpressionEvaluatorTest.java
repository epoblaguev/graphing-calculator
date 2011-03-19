package test;

import java.text.DecimalFormat;

import expressions.EquationEvaluator;
import expressions.IEvaluator;
import expressions.MathEvaluator;
import junit.framework.TestCase;

/*
 * ExpressionEvaluatorTest.java
 * Author: Ben McCormick
 * Written: Dec 25 2010
 * Last Edited: Feb 3 2011
 * Ben McCormick 2011
 * This file is part of The Eikona Project .
 * Eikona is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Eikona is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Eikona.  If not, see <http://www.gnu.org/licenses/>.
 */


/** A JUnit Test class to make sure that the MathEvaluator class is evaluating functions properly */
public class ExpressionEvaluatorTest extends TestCase {
	
	/**Constructor */
	public ExpressionEvaluatorTest(String name)
	{
		super(name);
	}

	/** A test of simple addition 
	 * @throws Exception */
	public void testAddition() throws Exception
	{
		IEvaluator me = new EquationEvaluator("1+1");
		double x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** A test of simple subtraction 
	 * @throws Exception */
	public void testSubtraction() throws Exception
	{
		IEvaluator me = new EquationEvaluator("2-1");
		double x = me.getValue();
		assertEquals(x,1.0);
	}
	/** A test of simple multiplication 
	 * @throws Exception */
	public void testMultiplication() throws Exception
	{
		IEvaluator me = new EquationEvaluator("2*3");
		double x = me.getValue();
		assertEquals(x,6.0);
	}
	
	/** A test of simple division 
	 * @throws Exception */
	public void testDivision() throws Exception
	{
		IEvaluator me = new EquationEvaluator("10/2");
		double x = me.getValue();
		assertEquals(x,5.0);
		
		me.setExpression("1/0");
		x = me.getValue();
		assertEquals(x,Double.POSITIVE_INFINITY);
		
		me.setExpression("500/0");
		x = me.getValue();
		assertEquals(x,Double.POSITIVE_INFINITY);
		
		me.setExpression("-1/0");
		x = me.getValue();
		assertEquals(x,Double.NEGATIVE_INFINITY);
	}
	
	/**Tests the ^ operator 
	 * @throws Exception */
	public void testPowers() throws Exception
	{
		IEvaluator me = new EquationEvaluator("3^2");
		double x = me.getValue();
		assertEquals(x,9.0);
	}
	
	/** Tests the % operator 
	 * @throws Exception */
	public void testModDiv() throws Exception
	{
		IEvaluator me = new EquationEvaluator("3%2");
		double x = me.getValue();
		assertEquals(x,1.0);
		
		me.setExpression("1%0");
		x = me.getValue();
		assertEquals(x,Double.NaN);
		
		me.setExpression("10%5");
		x = me.getValue();
		assertEquals(x,0.0);	
	}
	
	/** Tests & and | 
	 * @throws Exception */
	public void testBitwiseOps() throws Exception
	{
		
		IEvaluator me = new EquationEvaluator("7&3");
		double x = me.getValue();
		assertEquals(x,3.0);
		
		me.setExpression("8&3");
		x = me.getValue();
		assertEquals(x,0.0);
		
		me.setExpression("7|3");
		x = me.getValue();
		assertEquals(x, 7.0);
		
		me.setExpression("8|3");
		x = me.getValue();
		assertEquals(x,11.0);
		
	}
	
	/** Test the sqr() function 
	 * @throws Exception */
	public void testSqr() throws Exception
	{
		IEvaluator me = new EquationEvaluator("sqr(2)");
		double x = me.getValue();
		assertEquals(x,4.0);
	}
	
	/** Test the sqrt() function 
	 * @throws Exception */
	public void testSqrt() throws Exception
	{
		IEvaluator me = new EquationEvaluator("sqrt(4)");
		double x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the Log-Base 10 function 
	 * @throws Exception */
	public void testLog() throws Exception
	{
		IEvaluator me = new EquationEvaluator("log(10)");
		double x = me.getValue();
		assertEquals(x,1.0);
		
	}
	
	/**Test the natural log function 
	 * @throws Exception */
	public void testLn() throws Exception
	{
		IEvaluator me = new EquationEvaluator("ln("+Math.E+")");
		double x = me.getValue();
		assertEquals(x,1.0);
	}
	
	
	/**Tests the order of operations 
	 * @throws Exception **/
	public void testArithmeticOrderOfOperations() throws Exception
	{
		IEvaluator me = new EquationEvaluator("3+2*3");
		double x = me.getValue();
		assertEquals(x,9.0);
		
		me.setExpression("3*2+3");
		x = me.getValue();
		assertEquals(x,9.0);
		
		me.setExpression("3*(2+3)");
		x = me.getValue();
		assertEquals(x,15.0);
		
		me.setExpression("3*(2+3)");
		x = me.getValue();
		assertEquals(x,15.0);
		
		me.setExpression("2^(2+3)");
		x = me.getValue();
		assertEquals(x,32.0);
		
		me.setExpression("3^2+3");
		x = me.getValue();
		assertEquals(x,12.0);
	}
	
	/**Tests the various trig functions making sure they're precise to the 15th decimal point in degree format 
	 * @throws Exception */
	public void testTriginDegrees() throws Exception
	{

		DecimalFormat df = new DecimalFormat("#.###############");
		EquationEvaluator me = new EquationEvaluator();
		me.setAngleUnits(IEvaluator.DEGREES);
		
		//sine
		me.setExpression("sin(30)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(0.5));
		
		//cosine
		me.setExpression("cos(60)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(0.5));
		
		//tangent
		me.setExpression("tan(45)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(1));
		
	}
	
	/**Tests the Arc Trig functions 
	 * @throws Exception */
	public void testArcTriginDegrees() throws Exception
	{
		DecimalFormat df = new DecimalFormat("#.#############");
		EquationEvaluator me = new EquationEvaluator();
		me.setAngleUnits(IEvaluator.DEGREES);
		
		//arc-sine
		me.setExpression("asin(0.5)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(30));
		
		//arc-cosine
		me.setExpression("acos(0.5)");
		x = me.getValue();
	   	assertEquals(df.format(x),df.format(60));
		
		//arc-tangent
		me.setExpression("atan(1)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(45));
	}
	
	/**Tests the various trig functions making sure they're precise to the 15th decimal point in degree format 
	 * @throws Exception */
	public void testTriginRadians() throws Exception
	{

		DecimalFormat df = new DecimalFormat("#.###############");
		EquationEvaluator me = new EquationEvaluator();
		me.setAngleUnits(IEvaluator.RADIANS);
		
		//sine
		me.setExpression("sin("+(Math.PI/6.0)+")");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(0.5));
		
		//cosine
		me.setExpression("cos("+(Math.PI/3.0)+")");
		x = me.getValue();
		assertEquals(df.format(x),df.format(0.5));
		
		//tangent
		me.setExpression("tan("+(Math.PI/4.0)+")");
		x = me.getValue();
		assertEquals(df.format(x),df.format(1));
		
	}
	
	/**Tests the Arc Trig functions in radians 
	 * @throws Exception */
	public void testArcTriginRadians() throws Exception
	{
		DecimalFormat df = new DecimalFormat("#.###############");
		EquationEvaluator me = new EquationEvaluator();
		me.setAngleUnits(IEvaluator.RADIANS);
		
		//arc-sine
		me.setExpression("asin(0.5)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/6));
		
		//arc-cosine
		me.setExpression("acos(0.5)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/3));
		
		//arc-tangent
		me.setExpression("atan(1)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/4));
	}
	
	/** Tests the minimum and maximum functions 
	 * @throws Exception */
	public void testMinMax() throws Exception
	{
		IEvaluator me = new EquationEvaluator("min(1,10)");
		double x = me.getValue();
		assertEquals(x,1.0);
		

		me.setExpression("max(0.5,1)");
		x = me.getValue();
		assertEquals(x,1.0);
	}
	
	/** Tests the exp(x) function which returns e^x 
	 * @throws Exception */
	public void testExp() throws Exception
	{
		DecimalFormat df = new DecimalFormat("#.#############");
		
		IEvaluator me = new EquationEvaluator("exp(1)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(Math.E));
		

		me.setExpression("exp(2)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.pow(Math.E,2.0)));
	}
	
	/** Test the floor and ceil functions 
	 * @throws Exception */
	public void testFloorAndCeil() throws Exception
	{
		IEvaluator me = new EquationEvaluator("floor(2.99999999999)");
		double x = me.getValue();
		assertEquals(x,2.0);
		
		me.setExpression("ceil(1.000000001)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	
	/** Test the abs() function 
	 * @throws Exception */
	public void testAbs() throws Exception
	{
		IEvaluator me = new EquationEvaluator("abs(2)");
		double x = me.getValue();
		assertEquals(x,2.0);
		
		me.setExpression("abs(-2.0)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the neg() function 
	 * @throws Exception */
	public void testNeg() throws Exception
	{
		IEvaluator me = new EquationEvaluator("neg(2)");
		double x = me.getValue();
		assertEquals(x,-2.0);
		
		me.setExpression("neg(-2.0)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the rnd() function 
	 * @throws Exception */
	public void testRandom() throws Exception
	{
		IEvaluator me = new EquationEvaluator("rnd(2.3)");
		double x = me.getValue();
		double y = me.getValue();
		
		assertNotSame(x, y); 
	}
	
	/**Test Order of Operations with functions involved 
	 * @throws Exception */
	public void testOrderOfOperationsWithFunctions() throws Exception
	{
		DecimalFormat df = new DecimalFormat("#.###############");
		EquationEvaluator me = new EquationEvaluator("abs(2+10*10)");
		//me.setAngleUnits(IEvaluator.DEGREES);
		double x = me.getValue();
		assertEquals(df.format(x),df.format(102.0));
		
		me.setExpression("(neg((2^1-12^0-11)*3))");
		x = me.getValue();
		assertEquals(df.format(x),df.format(30));	
	}
	
	/**Tests the evaluators ability to handle variables 
	 * @throws Exception */
	public void testVariables() throws Exception
	{
		IEvaluator me = new EquationEvaluator("x");
		me.addVariable("x", 15.0);
		double x = me.getValue();
		assertEquals(x,15.0);
		
		me.setExpression("3+x");
		x = me.getValue();
		assertEquals(x,18.0);
	}
	
	
	/**Tests implicit multiplications 
	 * @throws Exception */
	public void testImplicitMultiplication() throws Exception
	{
		IEvaluator me = new EquationEvaluator("3(2+3)");
		double x = me.getValue();
		assertEquals(x,15.0);
		
		me.setExpression("(2-1)(1+1)");
	    x = me.getValue();
		assertEquals(x,2.0);
		
		me.setExpression("3x");
		me.addVariable("x", 15.0);
		x = me.getValue();
		assertEquals(x,45.0);	
	}
	
	/**Tests Factorials with !
	 * @throws Exception */
	public void testFactorial() throws Exception
	{
		IEvaluator me = new EquationEvaluator("3!");
		double x = me.getValue();
		assertEquals(x,6.0);
		
		me.setExpression("10!");
	    x = me.getValue();
		assertEquals(x,3628800.0);
	}
	
	/**Tests Combinations with Comb(
	 * @throws Exception */
	public void testCombinations() throws Exception
	{
		IEvaluator me = new EquationEvaluator("Comb(3,2)");
		double x = me.getValue();
		assertEquals(x,3.0);
		
		me.setExpression("Comb(4,2)");
	    x = me.getValue();
		assertEquals(x,6.0);
	}
	
	
	/**Tests Permutations with Perm(
	 * @throws Exception */
	public void testPermutation() throws Exception
	{
		IEvaluator me = new EquationEvaluator("Perm(3,2)");
		double x = me.getValue();
		assertEquals(x,6.0);
		
		me.setExpression("Perm(4,2)");
	    x = me.getValue();
		assertEquals(x,12.0);
	}
	
	
	
	
	
}