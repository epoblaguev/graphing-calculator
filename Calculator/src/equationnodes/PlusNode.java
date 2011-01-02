package equationnodes;

public class PlusNode extends BinOpNode{
	
	public PlusNode()
	{
		name = "+";
	}
	
	public PlusNode(EquationNode leftchild,EquationNode rightchild)
	{
		name = "+";
		lchild=leftchild;
		rchild=rightchild;
	}

	
	public double getValue() {
		return lchild.getValue() + rchild.getValue();
	}


	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString()
	{
		return lchild.toString()+"+"+rchild.toString();
		
	}
	
	

}
