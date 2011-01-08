package expressions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import equationnodes.*;
import exceptions.InvalidExpressionException;
import exceptions.UnsetVariableException;

/*
 * EquationTreeBuilder.java
 * Author: Ben McCormick
 * Written: Jan 1 2011
 * Last Edited: Jan 1 2011
 */

/**
 * The TreeBuilder, it asks the scanner for the next token and then parses it while building the equation tree
 * after parsing, it balances the tree and sets it to root
 * @author Ben McCormick
 */
public class EquationTreeBuilder {
	
private final int TABLE_SIZE =16;
private String instr,ctok;
private int  index=-1,cstate=0;
private EquationScanner myScan;
private Production p;
private EquationNode root=null;
private String[] headings;
private String[][] table;
private ArrayList<Production> prods=new ArrayList<Production>();
private ArrayList<String> stack =new ArrayList<String>(), steps=new ArrayList<String>();
private EquationStack eqstack =new EquationStack();
//private SymbolTable sym;
private boolean radians = true;

	
	/**
 	* Constructor, associates with an EquationScanner object
 	* @param s
 	* @throws IOException 
 	*/
	public EquationTreeBuilder(EquationScanner s) throws IOException
	{
	myScan=s;
	loadTable();
	createProductions();
	//sym=myScan.getSymbolTable();
	}

	/**
	 * Loops through asking for new tokens until there are no more in the scanner
	 * @throws IOException
	 * @throws InvalidExpressionException 
	 * @throws UnsetVariableException 
	 * @throws NumberFormatException 
	 */
	public boolean process() throws IOException, InvalidExpressionException, NumberFormatException
	{
		stack.add("0");
		ctok=myScan.scanNext();
		while(true)  //Until all tokens are processed
		{	
				index=-1;
				for(int i=0; i<headings.length; i++)
				{
					if(headings[i].equals(ctok))
					{
						index=i;   //find the index of the given token
					}
				}
				if(index==-1)  //if the token isn't found, print out area and return
				{
					throw new InvalidExpressionException("This is not a well formed expression, unknown token");
				}
				instr=table[index][cstate];  //find the instruction corresponding to the given state and token
				
				if(instr.equals("")) //handles incorrect programs
				{
					throw new InvalidExpressionException("This is not a well formed expression");
				}
				else
				if(instr.equals("acc"))  //handles the accepts case
				{
					//System.out.println("Made it here");
					root =balanceTree(eqstack.pop());
					return true;
				}
				else
				if(instr.charAt(0)=='s')
				{
					shift(ctok,cstate,Integer.parseInt(instr.substring(1)));
				}
				else
				if(instr.charAt(0)=='r')
				{
					reduce(ctok,cstate,Integer.parseInt(instr.substring(1)));
					
				}
					else
					{
						throw new InvalidExpressionException("This is not a well formed expression.");
					}	
			}
	}
	
	
	/**
	 * Performs a shift operation and updates state
	 * @param currenttok
	 * @param currentstate
	 * @param nextstate
	 * @throws IOException 
	 * @throws UnsetVariableException 
	 */
	private void shift(String currenttok, int currentstate, int nextstate) throws IOException
	{
		stack.add(currenttok);										//places the current token on the stack
		stack.add(currentstate+"");									//places the current state on the stack
	
		if(myScan.getRef()!=-1 && myScan.getReferenceValue(myScan.getRef())!=Double.NEGATIVE_INFINITY)
		{
			eqstack.push(createNode(myScan.getRef()));
		}
		else
		{
			//throw new UnsetVariableException("Variable "+myScan.getReferenceName(myScan.getRef())+" was not set");
		}
		cstate=Integer.parseInt((instr.substring(1)));
		ctok= myScan.scanNext();
		if(ctok == null)
		{
			ctok="$";
		}
	}
	
	

	/**
	 * Performs a reduce operation and updates state
	 * @param currenttok
	 * @param currentstate
	 * @param nextstate
	 * @throws IOException
	 */
	private void reduce(String currenttok, int currentstate, int rule) throws IOException
	{
	
		
		p=prods.get(rule);
		handleEqstack(p);
		int start=stack.size()-1,finish=stack.size()-(2*p.plength());
		for(int i=start; i>=finish; i--)
		{
			if(i== finish+1)
			{
				cstate=Integer.parseInt(stack.get(i));
			}
			stack.remove(i);
		}
			stack.add(p.getLeft());
			stack.add(""+cstate);
			for(int i=0; i<headings.length; i++)
			{
				if(headings[i].equals(p.getLeft()))
				{
					index=i;   //find the index of the given token
				}
			}
			steps.add(p.description);
			
			cstate=Integer.parseInt(table[index][cstate]);	
	}
	
	/**
	 * For a reduce operation, changes the eqstack to reflect the results
	 * @param p
	 */
	private void handleEqstack(Production p)
	{
		int pnum=getIndex(p);
		
		if(pnum==1)// Handles S -> SbS
		{
			EquationNode rchild =eqstack.pop();
			BinOpNode b =(BinOpNode)eqstack.pop();
			EquationNode lchild =eqstack.pop();
			b.setLChild(lchild);
			b.setRChild(rchild);
			eqstack.push(b);
		}
		if(pnum==2) // Handles S -> f S )
		{
			EquationNode child =eqstack.pop();
			FuncNode f =(FuncNode)eqstack.pop();
			f.setChild(child);
			eqstack.push(f);
		}
		if(pnum==3) // Handles S -> n S , S )
		{	
			EquationNode lchild =eqstack.pop();
			EquationNode rchild =eqstack.pop();
			BiFuncNode b =(BiFuncNode)eqstack.pop();
			b.setLChild(lchild);
			b.setRChild(rchild);
			eqstack.push(b);
		}
		
	}
	/**
	 * Gets the index of the production or prints an error message
	 * @param p
	 * @return
	 */
	private int getIndex(Production p)
	{
		for(int i=0; i<prods.size(); i++ )
		{
			if(prods.get(i).equals(p))
			{
				return i;
			}
		
		}
			System.out.println("Invalid Production");
			return -1;
		
	}
	
	private EquationNode createNode(int ref) {
		
		if(myScan.getReferenceType(ref).equals("f"))
		{
			
			return new FuncNode(myScan.getReferenceName(ref),radians);
		}
		else
		if(myScan.getReferenceType(ref).equals("d"))
		{
			return new DigitNode(myScan.getReferenceValue(ref));
		}
		else
		if(myScan.getReferenceType(ref).equals("v"))
		{
			return new VarNode(myScan.getReferenceName(ref),myScan.getReferenceValue(ref));
		}
		else
		if(myScan.getReferenceType(ref).equals("n"))
		{
			return new BiFuncNode(myScan.getReferenceName(ref));
		}
		else
		if(myScan.getReferenceType(ref).equals("b"))
		{
			char op = myScan.getReferenceName(ref).charAt(0);
			switch(op)
			{
				case '+': return new PlusNode();
				case '-': return new SubNode();
				case '*': return new MultNode();
				case '/': return new DivNode();
				case '%': return new ModNode();
				case '^': return new PowerNode();
				case '|': return new BitwiseOrNode();
				case '&': return new BitwiseAndNode();
			}
		}
		return null;	
	}
	
	/** Gets the root of the equation 
	 * @throws InvalidExpressionException 
	 * @throws UnsetVariableException 
	 * @throws NumberFormatException */
	public double getValue() throws InvalidExpressionException, NumberFormatException
	{
		try {
			process();
			return root.getValue();
		} catch (IOException e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/** Sets the value of a variable in the Sym table and adds it if necessary,then updates current expression tree */
	public void setVariable(String var, double val)
	{
		myScan.setVariable(var, val);
		updateTreeVar(var,val,root);
	}
	
	/** Updates the tree with updated variable information */
	private void updateTreeVar(String var, double val,EquationNode node)
	{
		if(node instanceof VarNode  && ((VarNode)node).getName().equals(var))
		{
			((VarNode) node).setValue(val);
		}
		else
		{
			if(node != null && node.numChildren() == 1)
			{
				updateTreeVar(var,val,((OpNode)node).getChild());
			}
			else
				if(node != null && node.numChildren() == 2)
				{
					updateTreeVar(var,val,((BinOpNode)node).getLChild());
					updateTreeVar(var,val,((BinOpNode)node).getRChild());
				}
		}
	}
	
	
	/**
	 * Loads the SLR Table
	 * @throws IOException
	 */
	private void loadTable() throws IOException
	{
		BufferedReader br= new BufferedReader(new FileReader(new File("./src/parsedata.txt")));
		String temp;
		String[] t,terms, vars;
		String[][]t1;              	//[x][y]  (so it goes 1,1  2,1 3,1)
									//		              1,2  2,2 3,2)
		
		terms=(br.readLine()).split("&");
		
		t1=new String[terms.length][TABLE_SIZE];  //fill in terminals
		
		for(int i=0; i<TABLE_SIZE; i++)
		{
			temp=br.readLine();
			t=temp.split("&");
			
			
			for(int h=1; h<terms.length+1; h++)
			{
				t1[h-1][i]=t[h];
			}
		}	
		vars=(br.readLine()).split("&");
		
		table=new String[vars.length+terms.length][TABLE_SIZE];  //fill in vars
		for(int i=0; i<TABLE_SIZE; i++)
		{
			temp=br.readLine();
			t=temp.split("&");
			for(int h=1; h<vars.length+1; h++)
			{
				
				table[h-1][i]=t1[h-1][i];					//put all in one table
				table[h+terms.length-1][i]=t[h];
			}
			for(int k=vars.length; k<terms.length; k++)
			{
				table[k][i]=t1[k][i];   //fills in extra terms
			}
			
		}	
		headings=new String[vars.length+terms.length];
		
		for (int i=0; i< terms.length; i++)
		{
			headings[i]=terms[i];
		}
		for (int j=0; j<vars.length; j++)
		{
			headings[j+terms.length]=vars[j];
		}
		
		
	}
	
	/**
	 * Adds all productions to the productions list (this is ugly, I attempted a prettier, more modular version but it failed... will fix if I have time
	 * @throws IOException
	 */
	private void createProductions() throws IOException
	{
		prods.add(new Production(null,null,null));
		String[] tem={"S","b","S"};
		prods.add(new Production("S",tem,"(1) <Segment> > <Segment> <binop> <Segment>"));
		String[] tem2={"f","S",")"};
		prods.add(new Production("S",tem2,"(2) <Segment> > function( <Segment> )"));
		String[] tem3={"n","S",",","S",")"};
		prods.add(new Production("S",tem3, "(3) <Segment> > bifunction( <Segment> , <Segment> )"));
		String[] tem4={"d"};
		prods.add(new Production("S",tem4, "(4) <Segment> > double"));
		String[] tem5={"v"};
		prods.add(new Production("S",tem5,"(5) <Segment> > variable"));
	}
	
	/**Balances the tree to preserve order of operations and returns the new root */
	private EquationNode balanceTree(EquationNode node)
	{
		
		
		
		if(node.numChildren() == 0){return node;}
		else
		if(node.numChildren() == 1)
		{
			((OpNode)node).setChild(balanceTree(((OpNode)node).getChild()));
		}
		else
		{
			((BinOpNode)node).setLChild(balanceTree(((BinOpNode)node).getLChild()));
			((BinOpNode)node).setRChild(balanceTree(((BinOpNode)node).getRChild()));
			EquationNode lchild = ((BinOpNode)node).getLChild();
			EquationNode rchild = ((BinOpNode)node).getRChild();
			if(lchild.getPriority() <node.getPriority())
			{
				EquationNode nlchild = ((BinOpNode)lchild).getRChild();
				((BinOpNode)lchild).setRChild(node);
				((BinOpNode)node).setLChild(nlchild);
				return lchild;
			}
			if(rchild.getPriority() <node.getPriority())
			{
				EquationNode nrchild = ((BinOpNode)rchild).getRChild();
				((BinOpNode)rchild).setLChild(node);
				((BinOpNode)node).setRChild(nrchild);
				return rchild;
			}
		}
		
		
		return node;
	}
	
	/** True sets the angle value to radians, false sets it to degrees */
	public void setRadians(boolean rad)
	{
		radians = rad;
	}

	/** Gets the value of the variable with name varname */
	public Double getVariable(String varname) {
		return myScan.getVarValue(varname);
	}
}
