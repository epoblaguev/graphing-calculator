package components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Vector;

/**
 * Creates an uneditable table.
 * @author Egor
 */
public class UneditableTable extends JTable{
    private DefaultTableModel tableModel = new DefaultTableModel();

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
    public void addRow(Vector<String> row){
        tableModel.addRow(row);
    }

    /**
     * Sets the row count.
     * @param rowCount
     */
    public void setRowCount(int rowCount){
        tableModel.setRowCount(rowCount);
    }

}
