package expressions;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationTokenizer {
	
	private static final EquationTokenizer ET = new EquationTokenizer();
	private boolean debug = true;
	
	private EquationTokenizer()
	{
		
	}
	
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
		Pattern number = Pattern.compile("^\\d+\\.?\\d*");
		Pattern space = Pattern.compile("^\\s+");
		Pattern operator = Pattern.compile("^[\\+\\-\\*/!%^&|,)\\[\\]]");
		Pattern function = Pattern.compile("^\\w*\\(");
		Pattern variable = Pattern.compile("^\\w+\\d*");
		LinkedList<String> list = new LinkedList<String>();
		
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
				list.add(eq.substring(0, num.end()));
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
		
		String[] tokens = new String[list.size()];
		int i=0;
		for(String t:list)
		{
			tokens[i] = t;
			i++;
		}
			
		return tokens;
		
	}
	
	public static void main(String[] args)
	{
		EquationTokenizer et =getInstance();
		et.tokenize("3..5+4 x y sin ()");
		et.tokenize("3*y");
	}
	

}
