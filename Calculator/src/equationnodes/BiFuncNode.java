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
		
		if(name.equals("max("))
		{
			return Math.max(lchild.getValue(), rchild.getValue());
		}
		if(name.equals("min("))
		{
			return Math.min(lchild.getValue(), rchild.getValue());
		}
		
		return 0;
	}
	
	public int getPriority()
	{
		return 10;
	}
	
	public String toString()
	{
		if(lchild != null && rchild != null)
		{
		if(name.equals("max("))
		return "max("+lchild.toString()+","+rchild.toString()+")";
		if(name.equals("min("))
			return "min("+lchild.toString()+","+rchild.toString()+")";
		}
		
				return name;
	}


}
