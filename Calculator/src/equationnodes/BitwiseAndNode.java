package equationnodes;

public class BitwiseAndNode extends BinOpNode {

	public BitwiseAndNode()
	{
		name = "&";
	}
	
	public int getPriority() {
		// TODO Auto-generated method stub
		return 6;
	}

	/** Gets the Node's value */
	public double getValue() {
		return (int)lchild.getValue() & (int)rchild.getValue();
	}


}
