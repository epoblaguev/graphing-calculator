package exceptions;

public class UnsetVariableException extends Exception{

		String message;
		
		public UnsetVariableException(String _message)
		{
			message = _message;
		}
		
		public String getMessage()
		{
			return message;
		}
	

}
