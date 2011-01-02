package expressions;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;
public class EquationScanner {
	private BufferedReader br;
	public String next,tok,currenttok="x";
	public int currentref;
	private ArrayList<String> tokens=new ArrayList<String>(),functions,operators,bifunc,punc;
	private Scanner s;
	private boolean comment=false;
	symTab sym = new symTab(); //creates symbol table
	
	/**
	 * Initializes a scan object filling in the keyword map, and color/punctuation lists 
	 * as well as reading the file and creating the symbol tables
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public EquationScanner(String[] tokenarray) throws FileNotFoundException
	{
		tokens=new ArrayList<String>();
		 for(String x:tokenarray)
		 {
			tokens.add(x);
		 }
		 
		 Scanner f =new Scanner(new File("./src/functions.txt"));
		 functions=new ArrayList<String>();
		 while(f.hasNext())
		 {
		 functions.add(f.next());
		 }
		 Scanner o =new Scanner(new File("./src/operators.txt"));
		 operators=new ArrayList<String>();
		 while(o.hasNext())
		 {
		 operators.add(o.next()); 
		 }
		 
		 Scanner b =new Scanner(new File("./src/bifunc.txt"));
		 bifunc=new ArrayList<String>();
		 while(b.hasNext())
		 {
			 bifunc.add(b.next()); 
		 }
		 
		 Scanner p =new Scanner(new File("./src/punc.txt"));
		 punc=new ArrayList<String>();
		 while(p.hasNext())
		 {
			 punc.add(p.next()); 
		 }
		 
		 
		 
	}
	
	/**
	 * Scans the file and returns the next token
	 * @return
	 * @throws IOException
	 */
	public String scanNext() throws IOException
	{
		tok=handleComments();
	if(tok == null){return null;}
	if(tok.length()>8)
	{
		System.out.print(errorHandle(tok))      ;
		tok=nextToken();
	}
		
	
	if(functions.contains(tok.toLowerCase()))  //Handles functions
	{
		currenttok="f";
		if(sym.contains(tok.toLowerCase()))
		{
			for(int i=0; i<sym.size(); i++)
			{
				if((sym.getName(i)).equals(tok.toLowerCase()))
				{
					currentref=i;
				}
			}
			return currenttok;
		}
		else
		{
		sym.add("f",tok.toLowerCase(),0);
		currentref=sym.size()-1;
		return currenttok;
		}
	}
	
	if(bifunc.contains(tok.toLowerCase()))  //Handles functions
	{
		currenttok="n";
		if(sym.contains(tok.toLowerCase()))
		{
			for(int i=0; i<sym.size(); i++)
			{
				if((sym.getName(i)).equals(tok.toLowerCase()))
				{
					currentref=i;
				}
			}
			return currenttok;
		}
		else
		{
		sym.add("n",tok.toLowerCase(),0);
		currentref=sym.size()-1;
		return currenttok;
		}
	}
	
	if(operators.contains(tok.toLowerCase()))  //Handles binaryoperators
	{
		currenttok="b";
		if(sym.contains(tok.toLowerCase()))
		{
			for(int i=0; i<sym.size(); i++)
			{
				if((sym.getName(i)).equals(tok.toLowerCase()))
				{
					currentref=i;
				}
			}
			return currenttok;
		}
		else
		{
		sym.add("b",tok.toLowerCase(),0);
		currentref=sym.size()-1;
		return currenttok;
		}
	}
	
											//handles Doubles
	Pattern p1 = Pattern.compile("^\\d+\\.\\d+$");
	Pattern p2 = Pattern.compile("^\\.\\d+$");
	Pattern p3 = Pattern.compile("^\\d+$");
	
	if(p1.matcher(tok).matches() || p2.matcher(tok).matches() || p3.matcher(tok).matches())
	{
	currenttok="d";
	if(sym.contains(tok.toLowerCase()))
	{
		for(int i=0; i<sym.size(); i++)
		{
			if((sym.getName(i)).equals(tok.toLowerCase()))
			{
				currentref=i;
			}
		}
		return currenttok;
	}
	else
	{
	sym.add("d",tok,Double.parseDouble(tok));
	currentref=sym.size()-1;
	return currenttok;
	}
	}
	
		
	if(punc.contains(tok.toLowerCase()))  //Handles Punctuation
	{
		currenttok=tok;
		if(sym.contains(tok.toLowerCase()))
		{
			for(int i=0; i<sym.size(); i++)
			{
				if((sym.getName(i)).equals(tok.toLowerCase()))
				{
					currentref=i;
				}
			}
			return currenttok;
		}
		else
		{
		sym.add(tok,tok,-1);
		currentref=sym.size()-1;
		}
		return currenttok;
	}	
	
	
 
  
	if(isVar(tok.toLowerCase()))
	{
		currenttok="v";						//Handles variables
		if(sym.contains(tok.toLowerCase()))
		{
			for(int i=0; i<sym.size(); i++)
			{
				if((sym.getName(i)).equals(tok.toLowerCase()))
				{
					currentref=i;
				}
			}
			return currenttok;
		}
		else
		{
			sym.add("v",tok.toLowerCase(),0);
			currentref=sym.size()-1;
		}
		return currenttok;
	}
	else
	{
		return errorHandle(tok);
	}
	
		
		
		
	}
	/**
	 * Ignores any comments and new lines and moves to the next token
	 * @return
	 * @throws IOException
	 */
	public String handleComments() throws IOException
	{
		tok=nextToken();
		if( tok == null){return null;}
		boolean x=true;
		while(x)
		{
			x=false;
		if(tok.equals("NL"))
		{
			tok=nextToken();  //skip to next on a new line
			x=true;
		}
		if(tok.equals("%"))
		{
			comment=true;
			tok=nextToken();  //move to comment mode on %
			x=true;
		}
		while(comment)  // Loop till out of comment mode
		{
			tok=nextToken();
			if(tok.equals("NL"))
			{
				comment=false;
				tok=nextToken();
			}
			x=true;
		}
		}
		return tok;
	}
	
	/**
	 * Returns the next token from the file, returns NL on a new line
	 * @return
	 * @throws IOException
	 */
	public String nextToken() throws IOException
	{
		if(!tokens.isEmpty())
		{
			return (String)(tokens.remove(0));   // (returns first token in buffer)
		}
		else{
			return null;
		}
	}
	
	/**
	 * Lets driver know if there are any tokens left to be processed
	 * @return
	 * @throws IOException 
	 */
	public boolean hasTokens() throws IOException
	{
		return (!tokens.isEmpty()||br.ready());
	}
	
	
	/**
	 * Returns the Current Reference to the symbol table
	 * @return
	 */
	public int getRef()
	{
		return currentref;
	}

	/**
	 * Returns the current Token Type
	 * @return
	 */
	public String getType()
	{
		return currenttok;
	}
	
	/**
	 * Returns the String value for reference ref
	 * @param ref
	 * @return
	 */
	public String getSVal(int ref)
	{
		return (String)sym.getName(ref);
	}
	
	/**
	 * Returns the Integer value for the reference ref
	 * @param ref
	 * @return
	 */
	public double getIVal(int ref)
	{
		return (sym.getValue(ref));
	}
	
	/**
	 * Handles invalid tokens
	 * @param token
	 */
	public String errorHandle(String token)
	{
		return ("Error: Invalid token -"+token);
	}
	
	/**
	 * Checks to see if all 8 character are valid lower case letters
	 * @param token
	 * @return
	 */
	public boolean isVar(String token)
	{
		for(int i=0; i<token.length(); i++)
		{
			Character.isLetter(token.charAt(i));
		}	
		
		return true;
	}
	/**
	 * Gives the symbol table
	 * @return
	 */
	public symTab getSymbolTable()
	{
		return sym;
	}
	
}
