package equationnodes;

public class ParenNode extends OpNode {

	public ParenNode()
	{
		name = "()";
		type = "f";
	}
	
	
	public double getValue() {
		return child.getValue();
	}
	
	public String toString()
	{
		return "("+child.toString()+")";
		
	}


	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 10;
	}

}
