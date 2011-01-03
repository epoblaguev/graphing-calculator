package equationnodes;

public class VarNode extends EquationNode{

	double val;
	
	public VarNode(String _name)
	{
		type = "v";
		name=_name;
	}
	
	public VarNode(String name,double value)
	{
		type = "v";
		val = value;
	}
	
	
	public void setValue(double value)
	{
		val = value;
	}
	
	
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 90;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return val;
	}
	
	
	public String toString()
	{
		return name;
	}
	
	@Override
	public int numChildren() {
		// TODO Auto-generated method stub
		return 0;
	}

}
