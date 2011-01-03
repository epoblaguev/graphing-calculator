package equationnodes;

public abstract class BinOpNode extends EquationNode {

	protected EquationNode lchild, rchild;
	
	protected BinOpNode()
	{
		type = "b";
	}
	
	
	/** Set the left child for the operator */
	public void setLChild(EquationNode _lsib)
	{
		lchild = _lsib;
	}

	/** Set the right child for the operator */
	public void setRChild(EquationNode _rsib)
	{
		rchild = _rsib;
	}

	/** Get the Left child for the operator */
	public EquationNode getLChild()
	{
		return lchild;
	}
	
	/** Get the right child for the operator */
	public EquationNode getRChild()
	{
		return rchild;
	}
	
	
	public String toString()
	{
		if(lchild != null && rchild != null)
		{
			return lchild+name+rchild;
		}
		else return name;
	}
	
	@Override
	public int numChildren() {
		// TODO Auto-generated method stub
		return 2;
	}
	

}
