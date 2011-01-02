package equationnodes;

public class PowerNode extends BinOpNode {
	
	public PowerNode()
	{
		name = "^";
	}

	@Override
	public int getPriority() {
		return 7;
	}

	@Override
	public double getValue() {
		return Math.pow(lchild.getValue(), rchild.getValue());
	}
	
	public String toString()
	{
		return lchild+"^"+rchild;
	}

}
