package equationnodes;

import java.util.Random;

public class FuncNode extends OpNode {

	boolean radians;
	
	public FuncNode(String func,boolean rad)
	{
		name = func;
		type = "f";
		radians = rad;
	}
	public double getValue() {  //should put this in order of most likely to least likely to be used
		if(name.equals("sin("))
		{
			System.out.print("Radians?: " +radians);
					
			if(radians)
			return Math.sin(child.getValue());
			else
				return Math.sin(Math.toRadians(child.getValue()));
		}
		if(name.equals("cos("))
		{
			if(radians)
			return Math.cos(child.getValue());
			else
				return Math.cos(Math.toRadians(child.getValue()));
		}
		if(name.equals("tan("))
		{
			if(radians)
			return Math.tan(child.getValue());
			else
				return Math.tan(Math.toRadians(child.getValue()));
		}
		if(name.equals("asin("))
		{
			if(radians)
			return Math.asin(child.getValue());
			else
				return Math.toDegrees(Math.asin(child.getValue()));
		}
		if(name.equals("acos("))
		{
			if(radians)
			return Math.acos(child.getValue());
			else
				return Math.toDegrees(Math.acos(child.getValue()));
		}
		if(name.equals("atan("))
		{
			if(radians)
			return Math.atan(child.getValue());
			else
				return Math.toDegrees(Math.atan(child.getValue()));
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
			return (Math.log(child.getValue())/Math.log(10.0));
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
		if(child != null)
		return name+child.toString()+")";
		
		return name;
	}
	
	
	public int getPriority() {
		// TODO Auto-generated method stub
		return 10;
	}

}
