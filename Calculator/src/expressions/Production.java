package expressions;

/*
 * Production.java
 * Author: Ben McCormick
 * Written: Dec 27 2010
 * Last Edited: Feb 4 2011
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
 * A Class to represent a Production in parsing an arithmetic expression
 * @author Ben McCormick
 *
 */
public class Production {
	
	String left, description;
	String[] right;

	/**
	 * Constructs a new Production object, associating a Variable with a set of terminals/variables
	 * @param l
	 * @param r
	 * @param descr
	 */
	public Production(String l, String[] r, String descr)
	{
		left=l;
		right=r;
		description=descr;
		
	}
	/**
	 * checks to see if a set of strings fits the right side of the production
	 */
	public boolean isValid(String[] stack)
	{
		if(!(stack.length==right.length)){
			
			return false;
		}
		
		for(int i=0; i<right.length; i++)
		{
			if(i>=stack.length)
				return false;
			if(!stack[i].equals(right[i]))
			{
			
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the size of the right side
	 * @return
	 */
	public int plength()
	{
		return right.length;
	}
	
	/**
	 * Returns the left side of the production
	 * @return
	 */
	public String getLeft()
	{
		return left;
	}
	
	
	
}
