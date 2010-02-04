/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;

import exceptions.InvalidVariableNameException;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Egor
 */
public class VariableList {
    private static Vector variables = new Vector<Variable>();

    public static void createIfEmpty() throws InvalidVariableNameException{
        if(variables.isEmpty()){
            variables.add(new Variable("pi", Math.PI));
            variables.add(new Variable("e", Math.E));

            sort();
        }
    }

    public static Vector<Variable> getVariableList(){
        return variables;
    }

    public static void clearVariableList(){
        variables = new Vector<Variable>();
    }

    public static void addVariable(Variable variable){
        variables.add(variable);
        sort();
    }

    public static void setVariable(int index, Variable variable){
        variables.set(index, variable);
        sort();
    }

    public static void removeVariable(Variable variable){
        variables.remove(variables);
        sort();
    }

    public static void removeVariable(int var){
        variables.remove(var);
        sort();
    }

    public static String replaceVariables(String expression){
        Iterator itr = variables.iterator();

        while(itr.hasNext()){
            Variable var = (Variable) itr.next();
            expression = expression.replace(var.getVariableName(), String.valueOf(var.getVariableValue()));
        }
        return expression;
    }

    private static void sort(){
        boolean swapped;

        do{
            swapped = false;
            for(int i = 0; i < variables.size() - 1; i++){
                String var1 = ((Variable)variables.get(i)).getVariableName();
                String var2 = ((Variable)variables.get(i+1)).getVariableName();

                if(var1.length() < var2.length()){
                    Variable toSwap = (Variable) variables.get(i);
                    variables.set(i, variables.get(i+1));
                    variables.set(i+1, toSwap);
                    swapped = true;
                }
            }
        }while(swapped == true);
    }
}
