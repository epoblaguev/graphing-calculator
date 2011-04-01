package expressions;

import java.io.FileNotFoundException;
import equationnodes.EquationNode;
import exceptions.InvalidExpressionException;
import exceptions.UnsetVariableException;

/*
 * EquationEvaluator.java
 * Author: Ben McCormick
 * Written: Jan 2 2011
 * Last Edited: Jan 5 2011
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
				e.printStackTrace();
				System.exit(0);
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
