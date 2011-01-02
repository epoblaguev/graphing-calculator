package equationnodes;

public class MultNode extends BinOpNode{
	
	public MultNode()
	{
		name = "*";
	}
	
	public MultNode(EquationNode leftchild,EquationNode rightchild)
	{
		name = "*";
		lchild=leftchild;
		rchild=rightchild;
	}

	/** Gets the value for this node */
	public double getValue() {
		
		return lchild.getValue() * rchild.getValue();
	}
	
	public String toString()
	{
		if(lchild != null && rchild != null)
			return lchild.toString()+"*"+rchild.toString();
			else
				return "*";
		
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	

}
