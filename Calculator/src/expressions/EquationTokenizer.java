package expressions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * EquationTokenizer.java
 * Author: Ben McCormick
 * Written: Jan 1 2011
 * Last Edited: Jan 1 2011
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



/**
 * A Singleton object designed to break a mathematical expression into tokens
 * @author BenMcCormick
 *
 */
public class EquationTokenizer {
	
	private static final EquationTokenizer ET = new EquationTokenizer();
	private boolean debug = false;
	
	/**
	 * Private Constructor
	 */
	private EquationTokenizer()
	{
		
	}
	
	/**
	 * Returns the instance of the EquationTokenizer
	 * @return
	 */
	public static EquationTokenizer getInstance()
	{
		return ET;
	}
	
	/**
	 * Breaks the equation down into a string array of tokens
	 * @param eq
	 * @return
	 */
	public String[] tokenize(String eq)
	{
		Pattern number = Pattern.compile("^-?\\d+\\.?\\d*");
		Pattern space = Pattern.compile("^\\s+");
		Pattern operator = Pattern.compile("^[\\+\\-\\*/!%^&|,)\\[\\]#]");
		Pattern function = Pattern.compile("^\\w*\\(");
		Pattern variable = Pattern.compile("^\\w+\\d*");
		ArrayList<String> list = new ArrayList<String>();
		
		while(eq.length() >0)
		{
			if(debug)
			{
				System.out.println("Equation: "+eq);
				System.out.println("Token List "+ list);
			}
			
			
			Matcher num = number.matcher(eq);
			if(num.find())
			{
				double n = Double.parseDouble(eq.substring(0, num.end()));
				
				String last=null;
				if(!list.isEmpty())
				last=list.get(list.size()-1);
				
				if(n <0 && last != null && (last.equals(")")||number.matcher(last).matches()||variable.matcher(last).matches()))  //handle negative numbers
				{
				list.add("-");
				list.add((-n)+"");
				}
				else
				{
					list.add((n)+"");	
				}
				eq = eq.substring(num.end());
				continue;
			}
			Matcher spc = space.matcher(eq);
			if(spc.find())
			{
				eq = eq.substring(spc.end());
				continue;
			}
			Matcher op = operator.matcher(eq);
			if(op.find())
			{
				list.add(eq.substring(0, op.end()));
				eq = eq.substring(op.end());
				continue;
			}
			Matcher func = function.matcher(eq);
			if(func.find())
			{
				list.add(eq.substring(0, func.end()));
				eq = eq.substring(func.end());
				continue;
			}
			Matcher var = variable.matcher(eq);
			if(var.find())
			{
				list.add(eq.substring(0, var.end()));
				eq = eq.substring(var.end());
				continue;
			}
			
		}
		if(debug)
		{
			System.out.println("Equation: "+eq);
			System.out.println("Token List "+ list);
		}
		addImplicitMult(list,number,variable,function);
		String[] tokens = new String[list.size()];
		int i=0;
		for(String t:list)
		{
			tokens[i] = t;
			i++;
		}
		return tokens;	
	}
	/**
	 * Handles implicit multiplication between numbers and variables and numbers/vars and parens
	 * @param list
	 * @param number
	 * @param var
	 * @param func
	 */
	private void addImplicitMult(ArrayList<String> list,Pattern number, Pattern var,Pattern func) {
		for(int i=0; i<list.size()-1; i++)
		{
			String a = list.get(i);
			String b = list.get(i+1);
			
			if(number.matcher(a).matches())
				if(var.matcher(b).matches()||func.matcher(b).matches())
				{
					list.add(i+1,"*");
				}
			if(a.equals(")"))
					if(func.matcher(b).matches())
					{
						list.add(i+1,"*");
					}	
		}	
	}
}
