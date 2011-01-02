package equationnodes;

public class DivNode extends BinOpNode {

	public DivNode()
	{
		name = "/";
	}
	
	public DivNode(EquationNode leftchild,EquationNode rightchild)
	{
		name= "/";
		lchild=leftchild;
		rchild=rightchild;
	}
	
	public double getValue() {
	
		return lchild.getValue()/rchild.getValue();
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 5;
	}

	public String toString()
	{
		
		if(lchild != null && rchild != null)
			return lchild.toString()+"/"+rchild.toString();
			else
				return "/";
		
	}
}
