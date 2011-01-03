package equationnodes;

public class DigitNode extends EquationNode {

	double val;
	
	public DigitNode(double value)
	{
		type = "d";
		name =""+value;
		val = value;
	}
	
	
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 100;
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
