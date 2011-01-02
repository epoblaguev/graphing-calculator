package equationnodes;

public class ModNode extends BinOpNode {

	public ModNode()
	{
		name = "%";
	}
	
	public ModNode(EquationNode leftchild,EquationNode rightchild)
	{
		name = "%";
		lchild=leftchild;
		rchild=rightchild;
	}
	
	public double getValue() {
		
		return lchild.getValue() % rchild.getValue();
	}


	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 6;
	}
	
	public String toString()
	{
		if(lchild != null && rchild != null)
			return lchild.toString()+"%"+rchild.toString();
			else
				return "%";
		
	}

}
