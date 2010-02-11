package calculator;

import expressions.Expression;
import expressions.ExpressionList;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A pane that displays a table of expressions and their computed values;
 * @author Egor
 */
public class ExpressionTablePane extends JTable{
    private static DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Create the table pane.
     */
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

    /**
     * Adds a row to the table pane.
     * @param row
     *  The row to be added
     */
    public static void addRow(Vector row){
        tableModel.addRow(row);
    }

    /**
     * Sets the row count.
     * @param rowCount
     */
    public static void setRowCount(int rowCount){
        tableModel.setRowCount(rowCount);
    }

    /**
     * Refreshes the table.
     */
    public static void refreshTable(){
        Vector row;
        ExpressionTablePane.setRowCount(0);
        Iterator itr = ExpressionList.getExpressionList().iterator();

        while(itr.hasNext()){
            Expression curExpression = (Expression) itr.next();
            row = new Vector(2);
            row.add(curExpression.getExpression());
            row.add(new DecimalFormat("#.##########").format(curExpression.getValue()));

            ExpressionTablePane.addRow(row);
        }
    }
}

