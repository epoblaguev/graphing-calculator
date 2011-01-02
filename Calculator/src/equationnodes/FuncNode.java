package equationnodes;

import java.util.Random;

public class FuncNode extends OpNode {

	public FuncNode(String func)
	{
		name = func;
		type = "f";
	}
	public double getValue() {  //should put this in order of most likely to least likely to be used
		if(name.equals("sin("))
		{
			System.out.println(child.getClass());
			return Math.sin(child.getValue());
		}
		if(name.equals("cos("))
		{
			return Math.cos(child.getValue());
		}
		if(name.equals("tan("))
		{
			return Math.tan(child.getValue());
		}
		if(name.equals("asin("))
		{
			return Math.asin(child.getValue());
		}
		if(name.equals("acos("))
		{
			return Math.acos(child.getValue());
		}
		if(name.equals("atan("))
		{
			return Math.atan(child.getValue());
		}
		if(name.equals("abs("))
		{
			return Math.abs(child.getValue());
		}
		if(name.equals("sqr("))
		{
			return Math.pow(child.getValue(),2.0);
		}
		if(name.equals("sqrt("))
		{
			return Math.sqrt(child.getValue());
		}
		if(name.equals("log(")) //needs to be fixed
		{
			return Math.log(child.getValue());
		}
		if(name.equals("ln("))
		{
			return Math.log(child.getValue());
		}
		if(name.equals("exp("))
		{
			return Math.pow(Math.E,child.getValue());
		}
		if(name.equals("ceil("))
		{
			return Math.ceil(child.getValue());
		}
		if(name.equals("floor("))
		{
			return Math.floor(child.getValue());
		}
		if(name.equals("neg("))
		{
			return 0-(child.getValue());
		}
		if(name.equals("rnd("))
		{
			Random r = new Random((long) child.getValue());
			return r.nextDouble();
		}
		if(name.equals("("))
		{
			return (child.getValue());
		}
		else
		{
			//Throw exception??
			return 0;
		}
	}
	
	public String toString()
	{
		return name+child.toString()+")";
	}
	
	
	public int getPriority() {
		// TODO Auto-generated method stub
		return 10;
	}

}
