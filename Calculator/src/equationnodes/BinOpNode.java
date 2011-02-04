package equationnodes;

/*
 * BinOpNode.java
 * Author: Ben McCormick
 * Written: Jan 2 2011
 * Last Edited: Feb 4 2011
 * ©Ben McCormick 2011
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


public abstract class BinOpNode extends EquationNode {

	protected EquationNode lchild, rchild;
	
	protected BinOpNode()
	{
		type = "b";
	}
	
	
	/** Set the left child for the operator */
	public void setLChild(EquationNode _lsib)
	{
		lchild = _lsib;
	}

	/** Set the right child for the operator */
	public void setRChild(EquationNode _rsib)
	{
		rchild = _rsib;
	}

	/** Get the Left child for the operator */
	public EquationNode getLChild()
	{
		return lchild;
	}
	
	/** Get the right child for the operator */
	public EquationNode getRChild()
	{
		return rchild;
	}
	
	
	public String toString()
	{
		if(lchild != null && rchild != null)
		{
			return lchild+name+rchild;
		}
		else return name;
	}
	
	@Override
	public int numChildren() {
		// TODO Auto-generated method stub
		return 2;
	}
	

}
