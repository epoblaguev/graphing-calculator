/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Egor
 */
public class InvalidBoundsException extends Throwable{
/**
	 * 
	 */
	private static final long serialVersionUID = 6753692050820737068L;
private String message = "Invalid Bounds: ";

    public InvalidBoundsException(String string) {
        this.message += string;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
