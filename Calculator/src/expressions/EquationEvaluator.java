package expressions;

import java.io.FileNotFoundException;

import equationnodes.EquationNode;
import exceptions.InvalidExpressionException;
import exceptions.UnsetVariableException;
/**
 * A class to evaluate mathematical expressions
 * @author Ben McCormick
 *
 */
public class EquationEvaluator implements IEvaluator{

	EquationTokenizer et;
	EquationScanner es;
	EquationTreeBuilder etb;
	private boolean radians = true;
	
	/**
	 * A plain constructor, instantiates a new EquationEvaluator
	 */
	public EquationEvaluator()
	{
		et = EquationTokenizer.getInstance();
	}
	
	/**
	 * Constructor with an expression
	 * @param expression
	 * @throws Exception
	 */
	public EquationEvaluator(String expression) throws Exception
	{
		et = EquationTokenizer.getInstance();
		es = new EquationScanner(et.tokenize("#"+expression+"#"));
		etb = new EquationTreeBuilder(es);
	}
	
	/**
	 * Adds a variable to the evaluator
	 */
	public void addVariable(String v, Double val) 
	{
		etb.setVariable(v, val);
	}
	
	/** 
	 * Gets the angle Units (Radians or Degrees)
	 */
	public int getAngleUnits() 
	{
		if(radians)
		return IEvaluator.RADIANS;
		else
			return IEvaluator.DEGREES;
	}
	
	/** 
	 * sets the angle units (gradians not currently supported )
	 */
	public void setAngleUnits(int units) 
	{
		if(etb==null)
		{
			try{
			es = new EquationScanner(new String[0]);
			etb = new EquationTreeBuilder(es);
			}
			catch(Exception e)
			{
				
			}
		}
		if(units == IEvaluator.RADIANS || units == IEvaluator.GRADIANS)
		{
			etb.setRadians(true);
			radians = true;
		}
		else
		{
			etb.setRadians(false);
			radians = false;
		}
	}
	
	/**
	 * Returns the value of the expression
	 */
	public Double getValue() throws InvalidExpressionException, NumberFormatException 
	{
		return new Double(etb.getValue());
	}
	/**
	 * Returns the value of the variable
	 */
	public Double getVariable(String varname) 
	{
		return etb.getVariable(varname);
	}

	/** 
	 * Sets the Evaluator to evaluate a new expression 
	 * */
	public void setExpression(String expression)  throws Exception
	{
		if(es == null){es = new EquationScanner(et.tokenize("#"+expression+"#"));}
		else{es.newExpression(et.tokenize("#"+expression+"#"));}
		etb = new EquationTreeBuilder(es);
		etb.setRadians(radians);
	}
}
