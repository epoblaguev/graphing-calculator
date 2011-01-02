package equationnodes;

public class refNode extends EquationNode {
	int ref;
	
	/**
	 * contains a reference to the symbol table
	 * @param x
	 */
	public refNode(int x){
		ref=x;
		type="ref";
	}
	/**
	 * Returns the reference to the symbol table
	 * @return
	 */
	public int getRef()
	{
		return ref;
	}
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
