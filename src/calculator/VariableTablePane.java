/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

import exceptions.InvalidVariableNameException;
import expressions.VariableList;
import expressions.Variable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Egor
 */
public class VariableTablePane extends JTable{
    private static DefaultTableModel tableModel = new DefaultTableModel();

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

        Iterator itr = VariableList.getVariableList().iterator();

        while(itr.hasNext()){
            Variable curVariable = (Variable) itr.next();
            row = new Vector(2);
            row.add(curVariable.getVariableName());
            row.add(curVariable.getVariableValue());

            VariableTablePane.addRow(row);
        }
    }
}
