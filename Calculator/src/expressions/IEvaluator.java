package expressions;

public interface IEvaluator {

	public static final int RADIANS = 1;
	public static final int DEGREES = 2;
	public static final int GRADIANS = 3;

	/***
	 * adds a variable and its value in the MathEvaluator
	 */
	public abstract void addVariable(String v, Double val);

	/***
	 * sets the expression
	 * @throws Exception 
	 */
	public abstract void setExpression(String s) throws Exception;

	/***
	 * resets the evaluator
	 */
	public abstract void reset();

	/***
	 * evaluates and returns the value of the expression
	 */
	public abstract Double getValue();

	/***
	 * gets the variable's value that was assigned previously 
	 */
	public abstract Double getVariable(String s);

	/**
	 * Get the angleUnits (Radians, Degrees, Gradians)
	 * @return the angle
	 */
	public abstract int getAngleUnits();

	/***
	 * trace the binary tree for debug
	 */
	public abstract void trace();

}