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

    /**
	 * 
	 */
	private static final long serialVersionUID = 4944091275744603344L;
	private String message = "";

    public InvalidVariableNameException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

}
