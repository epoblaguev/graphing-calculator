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
	
	

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 5;
	}
	
	

}
