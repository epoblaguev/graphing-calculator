package expressions;

import java.util.ArrayList;

import equationnodes.EquationNode;

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
	
	

}
