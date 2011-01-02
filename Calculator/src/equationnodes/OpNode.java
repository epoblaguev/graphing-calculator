package equationnodes;

public abstract class OpNode extends EquationNode{
	
protected EquationNode child;
	

	/** Set the left child for the operator */
	public void setChild(EquationNode _child)
	{
		child = _child;
	}

	/** Set the right child for the operator */
	public void setRChild(EquationNode _child)
	{
		child = _child;
	}

}
