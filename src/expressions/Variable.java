/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;

import exceptions.InvalidVariableNameException;
import Constants.BlackLists;
import java.io.Serializable;

/**
 * A Class to represent a Variable for a graphingcalculator application
 * @author Egor
 */
public class Variable implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1816036425056304281L;
	private String variableName;
    private double variableValue;

    /**
     * Constructor for a variable
     * @param variableName
     * @param variableValue
     * @throws InvalidVariableNameException
     */
    public Variable(String variableName, double variableValue) throws InvalidVariableNameException {
        this.setVariableName(variableName);
        this.variableValue = variableValue;
    }
    
    /**
     * Gets the variable name
     * @return- the variable name
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Sets the variable name
     * @param variableName
     * @throws InvalidVariableNameException
     */
    public void setVariableName(String variableName) throws InvalidVariableNameException {
        String tempVar = variableName.toLowerCase();

        for(String s : BlackLists.variableBlackList){
            if(tempVar.equals(s)){
                throw new InvalidVariableNameException("Invalid Variable Name: " + s);
            }
        }


        for(char i = 'a'; i<='z'; i++){
            tempVar = tempVar.replace(String.valueOf(i), "");
        }

        if(!tempVar.isEmpty()){
            throw new InvalidVariableNameException("Invalid Characters In Variable: " + tempVar);
        }
        this.variableName = variableName;
    }
    
    /**
     * Gets the value of the variable
     * @return 
     */
    public double getVariableValue() {
        return variableValue;
    }
    
    /**
     * Set the variable value
     * @param variableValue
     */
    public void setVariableValue(double variableValue) {
        this.variableValue = variableValue;
    }
}
