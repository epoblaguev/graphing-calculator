/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Egor
 */
public class InvalidVariableNameException extends Throwable{

    private String message = "";

    public InvalidVariableNameException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
