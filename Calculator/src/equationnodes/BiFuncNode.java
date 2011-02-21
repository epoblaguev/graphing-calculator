package equationnodes;

/*
 * BiFuncNode.java
 * Author: Ben McCormick
 * Written: Jan 2 2011
 * Last Edited: Feb 4 2011
 * Ben McCormick 2011
 * This file is part of The Eikona Project .
 * Eikona is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Eikona is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Eikona.  If not, see <http://www.gnu.org/licenses/>.
 */



public class BiFuncNode extends BinOpNode{

	public BiFuncNode(String _name)
	{
		type = "n";
		name = _name;
	}
	
	public BiFuncNode(String _name, EquationNode leftchild,EquationNode rightchild)
	{
		type = "n";
		name = _name;
		lchild=leftchild;
		rchild=rightchild;
	}
	
	
	public double getValue() {
		
		if(name.equals("max("))
		{
			return Math.max(lchild.getValue(), rchild.getValue());
		}
		if(name.equals("min("))
		{
			return Math.min(lchild.getValue(), rchild.getValue());
		}
		if(name.equals("perm("))
		{
			double value = factorial(lchild.getValue())/factorial(lchild.getValue()-rchild.getValue());
			
			return value;
		}
		if(name.equals("comb("))
		{
			double value = factorial(lchild.getValue())/(factorial(rchild.getValue())*factorial(lchild.getValue()-rchild.getValue()));
			
			return value;
		}
		return 0;
	}
	
	public double factorial(double x)
	{
		int a = (int)x, z=1;
		while(a>0)
		{
			z*=a;
			a--;
		}
		System.out.println(x+" "+z);
		return z;
		
	}
	
	public int getPriority()
	{
		return 10;
	}
	
	public String toString()
	{
		if(lchild != null && rchild != null)
		{
		if(name.equals("max("))
		return "max("+lchild.toString()+","+rchild.toString()+")";
		if(name.equals("min("))
			return "min("+lchild.toString()+","+rchild.toString()+")";
		}
		
				return name;
	}

	


}
