/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import exceptions.InvalidVariableNameException;
import expressions.VariableList;
import expressions.Variable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A pane that displays a table of variables and their values;
 * @author Egor
 */
public class VariableTablePane extends JTable{
    private static DefaultTableModel tableModel = new DefaultTableModel();

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

    public static void addRow(Vector row){
        tableModel.addRow(row);
    }


    public static void setRowCount(int rowCount){
        tableModel.setRowCount(rowCount);
    }

    public static void refreshTable() throws InvalidVariableNameException{
        VariableList.createIfEmpty();
        
        Vector row;
        VariableTablePane.setRowCount(0);

        Iterator itr = VariableList.getVariables().iterator();

        while(itr.hasNext()){
            Variable curVariable = (Variable) itr.next();
            row = new Vector(2);
            row.add(curVariable.getVariableName());
            row.add(curVariable.getVariableValue());

            VariableTablePane.addRow(row);
        }
    }
}
