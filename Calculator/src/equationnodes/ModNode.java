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
	

}
