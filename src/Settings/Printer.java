/**
 * 
 */
package Settings;

/**
 * @author Egor
 *
 */
public class Printer {
	private static final boolean verbose = false;
	
	public static void print(String input){
		if(verbose) System.out.println(input);
	}
	
	public static void print(double input){
		if(verbose) System.out.println(input);
	}
	
	public static void print(int input){
		if(verbose) System.out.println(input);
	}
}
