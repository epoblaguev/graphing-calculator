/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;


import java.util.Vector;

/**
 * A class to hold and modify a list of Expressions
 * @author Egor
 */
public class ExpressionList {
    private static Vector<Expression> expressions = new Vector<>();
    
    /**
     * Returns the expression list
     * @return
     */
    public static Vector<Expression> getExpressionList(){
        return expressions;
    }
    
    /**
     * Sets the expressionlist to point to a Vector of expressions given as an argument
     * @param expressions
     */
    public static void setExpressions(Vector<Expression> expressions) {
        ExpressionList.expressions = expressions;
    }

    /**
     * Clears the list of expressions
     */
    public static void clearExpressionList(){
        expressions.clear();
    }
    
    /**
     * Adds an expression to the list
     * @param expression
     * @throws Exception 
     */
    public static void addExpression(Expression expression) throws Exception{
        expression.evaluate();
        expressions.add(expression);
    }

    /**
     * Remove a given expression from the list
     * @param expression
     */
    public static void removeExpression(Expression expression){
        expressions.remove(expression);
    }
    /**
     * Remove an expression at a given index
     * @param index
     */
    public static void removeExpression(int index){
        expressions.remove(index);
    }
}
