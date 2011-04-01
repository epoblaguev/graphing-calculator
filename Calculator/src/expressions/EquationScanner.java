package expressions;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;
/*
 * EquationScanner.java
 * Author: Ben McCormick
 * Written: Dec 27 2010
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
public class EquationScanner {
	private BufferedReader br;
	private String tok, currenttok = "x";
	private int currentref;
	private ArrayList<String> tokens = new ArrayList<String>(), functions,
			operators, bifunc, punc, unop;
	private SymbolTable sym = new SymbolTable(); // creates symbol table

	/**
	 * Initializes a scan object reading in an array of tokens as well as all
	 * supported functions and operators
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public EquationScanner(String[] tokenarray) throws FileNotFoundException {
		newExpression(tokenarray);

		Scanner f = new Scanner(new File("./src/functions.txt"));
		functions = new ArrayList<String>();
		while (f.hasNext()) {
			functions.add(f.next());
		}
		Scanner o = new Scanner(new File("./src/operators.txt"));
		operators = new ArrayList<String>();
		while (o.hasNext()) {
			operators.add(o.next());
		}

		Scanner b = new Scanner(new File("./src/bifunc.txt"));
		bifunc = new ArrayList<String>();
		while (b.hasNext()) {
			bifunc.add(b.next());
		}

		Scanner p = new Scanner(new File("./src/punc.txt"));
		punc = new ArrayList<String>();
		while (p.hasNext()) {
			punc.add(p.next());
		}
		
		Scanner u = new Scanner(new File("./src/unaryoperators.txt"));
		unop = new ArrayList<String>();
		while (u.hasNext()) {
			String x=u.next();
			unop.add(x);
		}
	}

	/**
	 * Sets up a new Expression to be scanned
	 */
	public void newExpression(String[] tokenarray) {
		tokens = new ArrayList<String>();
		for (String x : tokenarray) {
			tokens.add(x);
		}
	}

	/**
	 * Scans the file and returns the next token
	 * 
	 * @return
	 * @throws IOException
	 */
	public String scanNext() throws IOException {
		tok = nextToken();
		if (tok == null) {
			return null;
		}

		if (functions.contains(tok.toLowerCase())) // Handles functions
		{
			currenttok = "f";
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("f", tok.toLowerCase(), 0);
				currentref = sym.size() - 1;
				return currenttok;
			}
		}

		if (bifunc.contains(tok.toLowerCase())) // Handles binary argument
												// functions
		{
			currenttok = "n";
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("n", tok.toLowerCase(), 0);
				currentref = sym.size() - 1;
				return currenttok;
			}
		}

		if (operators.contains(tok.toLowerCase())) // Handles binary operators
		{
			currenttok = "b";
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("b", tok.toLowerCase(), 0);
				currentref = sym.size() - 1;
				return currenttok;
			}
		}
		if(unop.contains(tok.toLowerCase()))  //handles unary operators
		{
			currenttok = "u";
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("u", tok.toLowerCase(), 0);
				currentref = sym.size() - 1;
				return currenttok;
			}
		}
		
		
		// handles Doubles
		Pattern p1 = Pattern.compile("^\\-?\\d+\\.\\d+$");
		Pattern p2 = Pattern.compile("^\\-?\\.\\d+$");
		Pattern p3 = Pattern.compile("^\\-?\\d+$");

		if (p1.matcher(tok).matches() || p2.matcher(tok).matches()
				|| p3.matcher(tok).matches()) {
			currenttok = "d";
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("d", tok, Double.parseDouble(tok));
				currentref = sym.size() - 1;
				return currenttok;
			}
		}

		if (punc.contains(tok.toLowerCase())) // Handles Punctuation
		{
			currenttok = tok;
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add(tok, tok, Double.NEGATIVE_INFINITY);
				currentref = sym.size() - 1;
			}
			return currenttok;
		}

		if (isVar(tok.toLowerCase())) {
			currenttok = "v"; // Handles variables
			if (sym.contains(tok.toLowerCase())) {
				for (int i = 0; i < sym.size(); i++) {
					if ((sym.getName(i)).equals(tok.toLowerCase())) {
						currentref = i;
					}
				}
				return currenttok;
			} else {
				sym.add("v", tok.toLowerCase(), 0);
				currentref = sym.size() - 1;
			}
			return currenttok;
		} else {
			return errorHandle(tok);
		}

	}

	/**
	 * Returns the next token from the file
	 * @return
	 * @throws IOException
	 */
	public String nextToken() 
	{
		if (!tokens.isEmpty()) 
		{
			return (String) (tokens.remove(0)); 
		} 
		else {
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
		return (!tokens.isEmpty() || br.ready());
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
	 * Handles invalid tokens
	 * 
	 * @param token
	 */
	private String errorHandle(String token) {
		return ("Error: Invalid token - " + token);
	}

	/**
	 * Checks to see if all 8 character are valid lower case letters
	 * 
	 * @param token
	 * @return
	 */
	private boolean isVar(String token) {
		for (int i = 0; i < token.length(); i++) {
			Character.isLetter(token.charAt(i));
		}

		return true;
	}

	/** Returns the value of a reference */
	public double getReferenceValue(int ref) {
		return sym.getValue(ref);
	}

	/** Returns the type of a reference */
	public String getReferenceType(int ref) {
		return sym.getType(ref);
	}

	/** Returns the name of a reference */
	public String getReferenceName(int ref) {
		return sym.getName(ref);
	}

	/**
	 * Sets the value of a variable in the Sym table and adds it if
	 * necessary,then updates current expression tree
	 */
	public void setVariable(String var, double val) {
		if (sym.contains(var)) {
			sym.setVarValue(var, val);
		} else {
			sym.add("v", var, val);
		}
	}

	/** Gets the value of a variable */
	public Double getVarValue(String varname) {
		return sym.getVarValue(varname);
	}

}
