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
import javax.swing.JMenuBar;

/**
 *
 * @author Egor
 */
public class Storage implements Serializable{
    private Vector<Expression> expressions;
    private Vector<Variable> variables;
    private GraphingTab graphTab;
    private JMenuBar menuBar;

    public Storage(Vector<Expression> expressions, Vector<Variable> variables, GraphingTab graphingTab, JMenuBar menuBar) {
        this.expressions = expressions;
        this.variables = variables;
        this.graphTab = graphingTab;
        this.menuBar = menuBar;
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

    public void setGraphingTab(GraphingTab graphTab){
        this.graphTab = graphTab;
    }
    public GraphingTab getGraphingTab(){
        return graphTab;
    }
}
