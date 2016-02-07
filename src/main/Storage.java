/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import expressions.Expression;
import expressions.Variable;
import graphing.GraphingTab;

import java.io.Serializable;
import java.util.Vector;

/**
 * A class to store Expressions, Variables, the Graphing Tab, and the Menu Bar
 * @author Egor
 */
class Storage implements Serializable{
    private Vector<Expression> expressions;
    private Vector<Variable> variables;
    private GraphingTab graphTab;
    public Storage(Vector<Expression> expressions, Vector<Variable> variables, GraphingTab graphingTab) {
        this.expressions = expressions;
        this.variables = variables;
        this.graphTab = graphingTab;
    }

    /**
     * Return all the expressions
     * @return
     */
    public Vector<Expression> getExpressions() {
        return expressions;
    }

    /**
     * Set the list of Expressions
     * @param expressions
     */
    public void setExpressions(Vector<Expression> expressions) {
        this.expressions = expressions;
    }
    /**
     * Get the list of Variables
     * @return
     */
    public Vector<Variable> getVariables() {
        return variables;
    }
    /**
     * Set the list of Variables
     * @param variables
     */
    public void setVariables(Vector<Variable> variables) {
        this.variables = variables;
    }

    /**
     * Set the Graphing Tab
     * @param graphTab
     */
    public void setGraphingTab(GraphingTab graphTab){
        this.graphTab = graphTab;
    }
    
    /**
     * Get the graphing tab
     * @return
     */
    public GraphingTab getGraphingTab(){
        return graphTab;
    }
}
