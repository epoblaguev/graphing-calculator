package equationnodes;

/*
 * VarNode.java
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
public class VarNode extends EquationNode{

	double val;
	
	public VarNode(String _name)
	{
		type = "v";
		name=_name;
	}
	
	public VarNode(String _name,double value)
	{
		type = "v";
		name = _name;
		val = value;
	}
	
	public String getName()
	{
		return name;
		
	}
	
	
	public void setValue(double value)
	{
		val = value;
	}
	
	
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 90;
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
