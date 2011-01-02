package expressions;

import java.io.FileNotFoundException;

import equationnodes.EquationNode;

public class ExpressionEvaluator implements IEvaluator{

	EquationTokenizer et;
	EquationScanner es;
	EquationTreeBuilder etb;
	EquationNode expressiontree;
	
	public ExpressionEvaluator()
	{
		et = EquationTokenizer.getInstance();
	}
	
	public void addVariable(String v, Double val) {
		// TODO Auto-generated method stub
		
	}

	public int getAngleUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Double getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getVariable(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setExpression(String s)  {
		try{
		es = new EquationScanner(et.tokenize("#"+s+"#"));
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void trace() {
		// TODO Auto-generated method stub
		
	}

}
