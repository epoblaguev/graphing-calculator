package expressions;
import java.util.ArrayList;


public class symTab {
	private ArrayList[] sym;
	
	/**
	 * Creates the symbol table
	 */
	public symTab() {
		
		sym=new ArrayList[3];   //Symbol Table
		sym[0]=new ArrayList<String>();
		 sym[1]=new ArrayList<String>();
		 sym[2]=new ArrayList<Double>();
	}
	
	/**
	 * Adds a new entry to the symbol table
	 * @param a
	 * @param b
	 * @param c
	 */
	public void add(String a, String b, double c)
	{
		sym[0].add(a);
		sym[1].add(b);
		if(c==-1)
		{
			sym[2].add(null);
		}
		else
		sym[2].add(new Double(c));
	}
	
	/**
	 * Tests if the symbol table already contains a specific token
	 * @param a
	 * @return
	 */
	public boolean contains(String a)
	{
		return (sym[1].contains(a));
	}
	/**
	 * Gives the number of entries in the Symbol table
	 * @return
	 */
	public int size()
	{
		return (sym[0].size());
	}
	
	/**
	 * Returns the type of the token at index x
	 * @return
	 */
	public String getType(int x)
	{
		return (String) sym[0].get(x);
	}
	/**
	 * Returns the name of the token at index x
	 * @param x
	 * @return
	 */
	public String getName(int x)
	{
		return (String) sym[1].get(x);
	}
	/**
	 * returns the value of the token at index x
	 * @param x
	 * @return
	 */
	public double getValue(int x)
	{
		if(((Double)sym[2].get(x)) != null)
		return ((Double)sym[2].get(x)).doubleValue();
		else
			return -1;
	}
	/**
	 * Sets the value of a symbol table entry
	 * @param ref
	 * @param value
	 */
	public void setValue(int ref, int value)
	{
	sym[2].set(ref, new Integer(value));	
	}
	
	/**
	 * Returns the int value associated with a particular variable name
	 * @param var
	 * @return
	 */
	public double getVarValue(String var)
	{
	for(int i=0; i<size(); i++)
	{
		if(getName(i).equals(var))
		{
			return getValue(i);
		}
	}
	return -1;
	}

	
	public void print()
	{
		for(int i=0; i<size(); i++)	
		{
			System.out.println(getType(i)+" "+getName(i)+" "+getValue(i));
		}
	}
	
}
