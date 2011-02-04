package expressions;

import java.util.ArrayList;

import equationnodes.EquationNode;

/*
 * EquationStack.java
 * Author: Ben McCormick
 * Written: Jan 11 2011
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

/**
 * A class to represent a stack for equation Nodes 
 * @author Ben McCormick
 *
 */
public class EquationStack {
	
	private ArrayList<EquationNode> stack;
	
	/** A standard constructor */
	public EquationStack()
	{
		stack = new ArrayList<EquationNode>();
	}
	
	/** Pops the top item off the stack */
	public EquationNode pop()
	{
		return stack.remove(stack.size()-1);
	}
	
	/** Pushes an item onto the stack */
	public void push(EquationNode node)
	{
		stack.add(node);
	}
	
	/** Lets the user view the top item on the stack without using it */
	public EquationNode peek()
	{
		EquationNode node = stack.get(stack.size()-1);
		return node;  //Potentially insecure?
	}
	
	/** Returns true if the stack is empty, false otherwise */
	public boolean isEmpty()
	{
		return stack.isEmpty();
	}
	
	/**Returns a String representation of the stack */
	public String toString()
	{
		return stack.toString();
	}
	

}
