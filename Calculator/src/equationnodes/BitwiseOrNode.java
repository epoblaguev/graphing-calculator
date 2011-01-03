package equationnodes;

public class BitwiseOrNode extends BinOpNode {

	public BitwiseOrNode()
	{
		name = "|";
	}
	
	public int getPriority() {
		// TODO Auto-generated method stub
		return 6;
	}

	/** Gets the Node's value */
	public double getValue() {
		return (int)lchild.getValue() | (int)rchild.getValue();
	}


}
