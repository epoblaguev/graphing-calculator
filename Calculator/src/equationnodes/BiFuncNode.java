package equationnodes;

public class BiFuncNode extends BinOpNode{

	
	
	public BiFuncNode(String _name)
	{
		type = "n";
		name = _name;
	}
	
	public BiFuncNode(String _name, EquationNode leftchild,EquationNode rightchild)
	{
		type = "n";
		name = _name;
		lchild=leftchild;
		rchild=rightchild;
	}
	
	
	public double getValue() {
		
		if(name.equals("max"))
		{
			return Math.max(lchild.getValue(), rchild.getValue());
		}
		if(name.equals("min"))
		{
			return Math.min(lchild.getValue(), rchild.getValue());
		}
		
		return 0;
	}
	
	public int getPriority()
	{
		return 10;
	}


}
