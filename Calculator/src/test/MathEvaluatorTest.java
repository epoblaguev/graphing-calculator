import java.text.DecimalFormat;

import expressions.MathEvaluator;
import junit.framework.TestCase;

/** A JUnit Test class to make sure that the MathEvaluator class is evaluating functions properly */
public class MathEvaluatorTest extends TestCase {
	
	/**Constructor */
	public MathEvaluatorTest(String name)
	{
		super(name);
	}

	/** A test of simple addition */
	public void testAddition()
	{
		MathEvaluator me = new MathEvaluator("1+1");
		double x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** A test of simple subtraction */
	public void testSubtraction()
	{
		MathEvaluator me = new MathEvaluator("2-1");
		double x = me.getValue();
		assertEquals(x,1.0);
	}
	/** A test of simple multiplication */
	public void testMultiplication()
	{
		MathEvaluator me = new MathEvaluator("2*3");
		double x = me.getValue();
		assertEquals(x,6.0);
	}
	
	/** A test of simple division */
	public void testDivision()
	{
		MathEvaluator me = new MathEvaluator("10/2");
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
	
	/**Tests the ^ operator */
	public void testPowers()
	{
		MathEvaluator me = new MathEvaluator("3^2");
		double x = me.getValue();
		assertEquals(x,9.0);
	}
	
	/** Tests the % operator */
	public void testModDiv()
	{
		MathEvaluator me = new MathEvaluator("3%2");
		double x = me.getValue();
		assertEquals(x,1.0);
		
		me.setExpression("1%0");
		x = me.getValue();
		assertEquals(x,Double.NaN);
		
		me.setExpression("10%5");
		x = me.getValue();
		assertEquals(x,0.0);	
	}
	
	/** Tests & and | */
	public void testBitwiseOps()
	{
		
		MathEvaluator me = new MathEvaluator("7 & 3");
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
	
	/** Test the sqr() function */
	public void testSqr()
	{
		MathEvaluator me = new MathEvaluator("sqr(2)");
		double x = me.getValue();
		assertEquals(x,4.0);
	}
	
	/** Test the sqrt() function */
	public void testSqrt()
	{
		MathEvaluator me = new MathEvaluator("sqrt(4)");
		double x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the Log-Base 10 function */
	public void testLog()
	{
		MathEvaluator me = new MathEvaluator("log(10)");
		double x = me.getValue();
		assertEquals(x,1.0);
		
	}
	
	/**Test the natural log function */
	public void testLn()
	{
		MathEvaluator me = new MathEvaluator("ln("+Math.E+")");
		double x = me.getValue();
		assertEquals(x,1.0);
	}
	
	
	/**Tests the order of operations **/
	public void testArithmeticOrderOfOperations()
	{
		MathEvaluator me = new MathEvaluator("3+2*3");
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
	
	/**Tests the various trig functions making sure they're precise to the 15th decimal point in degree format */
	public void testTriginDegrees()
	{

		DecimalFormat df = new DecimalFormat("#.###############");
		MathEvaluator me = new MathEvaluator();
		me.setAngleUnits(MathEvaluator.DEGREES);
		
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
	
	/**Tests the Arc Trig functions */
	public void testArcTriginDegrees()
	{
		DecimalFormat df = new DecimalFormat("#.###############");
		MathEvaluator me = new MathEvaluator();
		me.setAngleUnits(MathEvaluator.DEGREES);
		
		//arc-sine
		me.setExpression("asin(0.5)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(30));
		
		//arc-cosine
		me.setExpression("cos(0.5)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(60));
		
		//arc-tangent
		me.setExpression("tan(1)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(45));
	}
	
	/**Tests the various trig functions making sure they're precise to the 15th decimal point in degree format */
	public void testTriginRadians()
	{

		DecimalFormat df = new DecimalFormat("#.###############");
		MathEvaluator me = new MathEvaluator();
		me.setAngleUnits(MathEvaluator.RADIANS);
		
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
	
	/**Tests the Arc Trig functions in radians */
	public void testArcTriginRadians()
	{
		DecimalFormat df = new DecimalFormat("#.###############");
		MathEvaluator me = new MathEvaluator();
		me.setAngleUnits(MathEvaluator.RADIANS);
		
		//arc-sine
		me.setExpression("asin(0.5)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/6));
		
		//arc-cosine
		me.setExpression("cos(0.5)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/3));
		
		//arc-tangent
		me.setExpression("tan(1)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.PI/4));
	}
	
	/** Tests the minimum and maximum functions */
	public void testMinMax()
	{
		MathEvaluator me = new MathEvaluator("min(1,10)");
		double x = me.getValue();
		assertEquals(x,1.0);
		

		me.setExpression("max(0.5,1)");
		x = me.getValue();
		assertEquals(x,1.0);
	}
	
	/** Tests the exp(x) function which returns e^x */
	public void testExp()
	{
		DecimalFormat df = new DecimalFormat("#.#############");
		
		MathEvaluator me = new MathEvaluator("exp(1)");
		double x = me.getValue();
		assertEquals(df.format(x),df.format(Math.E));
		

		me.setExpression("exp(2)");
		x = me.getValue();
		assertEquals(df.format(x),df.format(Math.pow(Math.E,2.0)));
	}
	
	/** Test the floor and ceil functions */
	public void testFloorAndCeil()
	{
		MathEvaluator me = new MathEvaluator("floor(2.99999999999)");
		double x = me.getValue();
		assertEquals(x,2.0);
		
		me.setExpression("ceil(1.000000001)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	
	/** Test the abs() function */
	public void testAbs()
	{
		MathEvaluator me = new MathEvaluator("abs(2)");
		double x = me.getValue();
		assertEquals(x,2.0);
		
		me.setExpression("abs(-2.0)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the neg() function */
	public void testNeg()
	{
		MathEvaluator me = new MathEvaluator("neg(2)");
		double x = me.getValue();
		assertEquals(x,-2.0);
		
		me.setExpression("neg(-2.0)");
		x = me.getValue();
		assertEquals(x,2.0);
	}
	
	/** Test the rnd() function */
	public void testRandom()
	{
		MathEvaluator me = new MathEvaluator("rnd(2.3)");
		double x = me.getValue();
		double y = me.getValue();
		
		assertFalse(x == y); 
	}
	
}
