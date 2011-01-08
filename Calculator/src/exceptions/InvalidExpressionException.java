package exceptions;

public class InvalidExpressionException extends Exception{
	
	String message;
	
	public InvalidExpressionException(String _message)
	{
		message = _message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	

}
