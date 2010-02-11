/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;

import exceptions.InvalidVariableNameException;
import Constants.BlackLists;
import java.io.Serializable;

/**
 *
 * @author Egor
 */
public class Variable implements Serializable{

    private String variableName;
    private double variableValue;

    public Variable(String variableName, double variableValue) throws InvalidVariableNameException {
        this.setVariableName(variableName);
        this.variableValue = variableValue;
    }

    public String getVariableName() {
        return variableName;
    }

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

    public double getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(double variableValue) {
        this.variableValue = variableValue;
    }
}
