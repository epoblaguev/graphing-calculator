package expressions;

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
