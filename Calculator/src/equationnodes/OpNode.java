package equationnodes;

public abstract class OpNode extends EquationNode{
	
protected EquationNode child;
	

	/** Set the child for the operator */
	public void setChild(EquationNode _child)
	{
		child = _child;
	}

	@Override
	public int numChildren() {
		// TODO Auto-generated method stub
		return 1;
	}

	/** Get the child for this node */
	public EquationNode getChild() {
		// TODO Auto-generated method stub
		return child;
	}
	
}
