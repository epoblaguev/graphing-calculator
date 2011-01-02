package expressions;

import java.io.FileNotFoundException;

import equationnodes.EquationNode;

public class ExpressionEvaluator implements IEvaluator{

	EquationTokenizer et;
	EquationScanner es;
	EquationTreeBuilder etb;
	EquationNode expressiontree = null;
	
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
		// TODO Auto-generated method stub
		
	}

	public int getAngleUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Double getValue() {
		System.out.println(expressiontree+" = "+expressiontree.getValue());
		
		return new Double(expressiontree.getValue());
	}

	public Double getVariable(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setExpression(String expression)  throws Exception{
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

	public void trace() {
		// TODO Auto-generated method stub
		
	}

}
