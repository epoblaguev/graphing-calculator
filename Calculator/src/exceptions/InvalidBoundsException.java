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
private String message = "Invalid Bounds: ";

    public InvalidBoundsException(String string) {
        this.message += string;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
