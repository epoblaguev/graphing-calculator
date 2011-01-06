package expressions;

import java.io.FileNotFoundException;

import equationnodes.EquationNode;

public class ExpressionEvaluator implements IEvaluator{

	EquationTokenizer et;
	EquationScanner es;
	EquationTreeBuilder etb;
	EquationNode expressiontree = null;
	private boolean radians = true;
	
	public ExpressionEvaluator()
	{
		et = EquationTokenizer.getInstance();
	}
	
	public ExpressionEvaluator(String expression) throws Exception
	{
		et = EquationTokenizer.getInstance();
		es = new EquationScanner(et.tokenize("#"+expression+"#"));
		etb = new EquationTreeBuilder(es);
		if(etb.process())
		{
			expressiontree = etb.getRoot();
		}
			else
			{
				throw new Exception();
			}
	}
	
	public void addVariable(String v, Double val) {
		etb.setVariable(v, val);
		
	}

	public int getAngleUnits() {
		if(radians)
		return IEvaluator.RADIANS;
		else
			return IEvaluator.DEGREES;
	}
	
	/** sets the angle units (gradians not currently supported */
	public void setAngleUnits(int units) {
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

	public Double getValue() {
		
		return new Double(etb.getRoot().getValue());
	}

	public Double getVariable(String varname) {
		
		return etb.getVariable(varname);
	}

	/** Sets the Evaluator to a new Expression */
	public void setExpression(String expression)  throws Exception{
		
		if(es == null){es = new EquationScanner(et.tokenize("#"+expression+"#"));}
		else{es.newExpression(et.tokenize("#"+expression+"#"));}
		etb = new EquationTreeBuilder(es);
		etb.setRadians(radians);
		if(etb.process())
		{
			expressiontree = etb.getRoot();
		}
			else
			{
				throw new Exception();
			}
	}


}
