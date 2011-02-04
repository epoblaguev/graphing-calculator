package expressions;

import exceptions.InvalidExpressionException;
import exceptions.UnsetVariableException;

/*
 * IEvaluator.java
 * Author: Ben McCormick
 * Written: January 8 2011
 * Last Edited: Feb 4 2011
 * ©Ben McCormick 2011
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


public interface IEvaluator {

	public static final int RADIANS = 1;
	public static final int DEGREES = 2;
	public static final int GRADIANS = 3;

	/***
	 * adds a variable and its value in the MathEvaluator
	 */
	public abstract void addVariable(String v, Double val);

	/***
	 * sets the expression
	 * @throws Exception 
	 */
	public abstract void setExpression(String s) throws Exception;

	/***
	 * evaluates and returns the value of the expression
	 * @throws InvalidExpressionException 
	 * @throws UnsetVariableException 
	 * @throws NumberFormatException 
	 */
	public abstract Double getValue() throws InvalidExpressionException, NumberFormatException;

	/***
	 * gets the variable's value that was assigned previously 
	 */
	public abstract Double getVariable(String s);

	/**
	 * Get the angleUnits (Radians, Degrees)
	 * @return the angle
	 */
	public abstract int getAngleUnits();
	
	/** Sets the angle units (Radians, Degrees) */
	public abstract void setAngleUnits(int units);

}