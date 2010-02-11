/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package expressions;

import java.util.Vector;

/**
 *
 * @author Egor
 */
public class ExpressionList {
    private static Vector<Expression> expressions = new Vector<Expression>();

    public static void createIfEmpty(){

    }

    public static Vector<Expression> getExpressionList(){
        return expressions;
    }
    
    public static void setExpressions(Vector<Expression> expressions) {
        ExpressionList.expressions = expressions;
    }

    public static void clearExpressionList(){
        expressions = new Vector<Expression>();
    }

    public static void addExpression(Expression expression){
        expression.evaluate();
        expressions.add(expression);
    }

    public static void setExpression(int index, Expression expression){
        expressions.set(index, expression);
    }

    public static void removeExpression(Expression expression){
        expressions.remove(expression);
    }
}
