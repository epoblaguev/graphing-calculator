/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import expressions.Expression;
import expressions.Variable;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Egor
 */
public class Storage implements Serializable{
    private Vector<Expression> expressions;
    private Vector<Variable> variables;

    public Storage(Vector<Expression> expressions, Vector<Variable> variables) {
        this.expressions = expressions;
        this.variables = variables;
    }

    public Vector<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(Vector<Expression> expressions) {
        this.expressions = expressions;
    }

    public Vector<Variable> getVariables() {
        return variables;
    }

    public void setVariables(Vector<Variable> variables) {
        this.variables = variables;
    }

    
}
