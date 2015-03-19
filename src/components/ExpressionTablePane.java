package components;

import Constants.ConstValues;
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
public class ExpressionTablePane extends JTable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4737506904301505173L;
	private static DefaultTableModel tableModel = new DefaultTableModel();

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
    public static void addRow(Vector<String> row) {
        tableModel.addRow(row);
    }

    /**
     * Sets the row count.
     * @param rowCount
     */
    public static void setRowCount(int rowCount) {
        tableModel.setRowCount(rowCount);
    }

    /**
     * Refreshes the table.
     */
    public static void refreshTable() {
        double curValue;
        Vector<String> row;
        ExpressionTablePane.setRowCount(0);
        
        @SuppressWarnings("rawtypes")
		Iterator itr = ExpressionList.getExpressionList().iterator();

        while (itr.hasNext()) {
            Expression curExpression = (Expression) itr.next();
            curValue = curExpression.getValue();
            row = new Vector<String>(2);
            row.add(curExpression.getExpression());
            if (!Double.isInfinite(curValue) && !Double.isNaN(curValue)) {
                row.add(new DecimalFormat(ConstValues.DF_10).format(curExpression.getValue()));
            } else {
                row.add("NaN");
            }
            //row.add(curExpression.getAngleUnits());

			ExpressionTablePane.addRow(row);
        }
    }
}

