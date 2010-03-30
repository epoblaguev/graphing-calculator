/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;

import Constants.ConstValues;
import components.SmartTextField;
import exceptions.InvalidVariableNameException;
import java.util.Vector;

/**
 *
 * @author Egor
 */
public class VariableList {
    private static Vector<Variable> variables = new Vector<Variable>();

    public static void createIfEmpty() throws InvalidVariableNameException{
        if(variables.isEmpty()){
            variables.add(new Variable("pi", ConstValues.pi));
            variables.add(new Variable("e", ConstValues.e));
            sort();
        }
    }

    public static Vector<Variable> getVariables(){
        return variables;
    }

    public static void setVariables(Vector<Variable> variables) {
        VariableList.variables = variables;
    }

    public static void clearVariableList(){
        variables = new Vector<Variable>();
        sort();
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
        variables.remove(variable);
        sort();
    }

    public static void removeVariable(int var){
        variables.remove(var);
        sort();
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
        SmartTextField.rebuildList();
    }
}
