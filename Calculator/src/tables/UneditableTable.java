package tables;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Creates an uneditable table.
 * @author Egor
 */
public class UneditableTable extends JTable{
    protected DefaultTableModel tableModel = new DefaultTableModel();

    /**
     * Create the table pane.
     */
    public UneditableTable(TableModel tableModel){
        super(tableModel);
        this.tableModel = (DefaultTableModel) tableModel;
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
    public void addRow(Vector row){
        tableModel.addRow(row);
    }

    /**
     * Sets the row count.
     * @param rowCount
     */
    public void setRowCount(int rowCount){
        tableModel.setRowCount(rowCount);
    }

    /**
     * Refreshes the table.
     */
    public static void refreshTable(){
    }
}
