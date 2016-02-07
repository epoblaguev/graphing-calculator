/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import exceptions.InvalidVariableNameException;
import expressions.Variable;
import expressions.VariableList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;
import java.util.Vector;

/**
 * A pane that displays a table of variables and their values;
 * @author Egor
 */
public class VariableTablePane extends JTable{
    private static final DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Constructor.
     */
    public VariableTablePane(){
        this.setModel(tableModel);
        tableModel.addColumn("Variable");
        tableModel.addColumn("Value");
        tableModel.setColumnCount(2);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private static void addRow(Vector<String> row){
        tableModel.addRow(row);
    }


    private static void setRowCount(int rowCount){
        tableModel.setRowCount(rowCount);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void refreshTable() throws InvalidVariableNameException{
        VariableList.createIfEmpty();
        
        Vector row;
        VariableTablePane.setRowCount(0);

        Iterator<Variable> itr = VariableList.getVariables().iterator();

        while(itr.hasNext()){
            Variable curVariable = itr.next();
            row = new Vector(2);
            row.add(curVariable.getVariableName());
            row.add(curVariable.getVariableValue());

            VariableTablePane.addRow(row);
        }
    }
}
