package equationnodes;

public class FactorialNode extends OpNode {

	public FactorialNode()
	{
		name = "!";
	}
	
	@Override
	public double getValue() {
		int x =1;
		for(int i=1; i<=getChild().getValue(); i++)
		{
			x=x*i;
		}
		return (double)x;
	}

	@Override
	public int getPriority() {
		
		return 6;
	}
	
}
