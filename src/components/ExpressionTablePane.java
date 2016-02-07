package components;

import Constants.ConstValues;
import expressions.Expression;
import expressions.ExpressionList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * A pane that displays a table of expressions and their computed values;
 * @author Egor
 */
public class ExpressionTablePane extends JTable {

    private static final DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Create the table pane.
     */
    public ExpressionTablePane() {
        this.setModel(tableModel);
        tableModel.addColumn("Expression");
        tableModel.addColumn("Value");
        //tableModel.addColumn("Angle Units");
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
    private static void addRow(Vector<String> row) {
        tableModel.addRow(row);
    }

    /**
     * Sets the row count.
     * @param rowCount
     */
    private static void setRowCount(int rowCount) {
        tableModel.setRowCount(rowCount);
    }

    /**
     * Refreshes the table.
     */
    public static void refreshTable() {
        ExpressionTablePane.setRowCount(0);

        for(Expression curExpression : ExpressionList.getExpressionList()){
            double curValue = curExpression.getValue();
            Vector<String> row = new Vector<>(2);
            row.add(curExpression.getExpression());
            if (!Double.isInfinite(curValue) && !Double.isNaN(curValue)) {
                row.add(new DecimalFormat(ConstValues.DF_10).format(curExpression.getValue()));
            } else {
                row.add("NaN");
            }
            ExpressionTablePane.addRow(row);
        }
    }
}

