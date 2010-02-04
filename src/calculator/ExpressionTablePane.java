/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;

import expressions.Expression;
import expressions.ExpressionList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Egor
 */
public class ExpressionTablePane extends JTable{
    private static DefaultTableModel tableModel = new DefaultTableModel();

    public ExpressionTablePane(){
        this.setModel(tableModel);
        tableModel.addColumn("Expression");
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

    public static void refreshTable(){
        Vector row;
        ExpressionTablePane.setRowCount(0);
        Iterator itr = ExpressionList.getExpressionList().iterator();

        while(itr.hasNext()){
            Expression curExpression = (Expression) itr.next();
            row = new Vector(2);
            row.add(curExpression.getExpression());
            row.add(curExpression.evaluate());

            ExpressionTablePane.addRow(row);
        }
    }
}

