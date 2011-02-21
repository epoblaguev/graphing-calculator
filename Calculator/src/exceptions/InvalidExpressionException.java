package exceptions;
/*
 * InvalidExpressionException.java
 * Author: Ben McCormick
 * Written: Jan 8 2010
 * Last Edited: Feb 4 2011
 * Ben McCormick 2011
 * This file is part of The Eikona Project .
 * Eikona is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Eikona is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Eikona.  If not, see <http://www.gnu.org/licenses/>.
 */
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
