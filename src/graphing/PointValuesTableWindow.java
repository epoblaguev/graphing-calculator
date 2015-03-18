/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import components.UneditableTable;

/**
 * -- Needs Commenting --
 * @author Egor
 */
public class PointValuesTableWindow extends JFrame implements ActionListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7855337057420942613L;
	private DefaultTableModel tableModel = new DefaultTableModel();
    private UneditableTable table = new UneditableTable(tableModel);
    private JScrollPane scrollPane  = new JScrollPane(table);
    private JButton btnRefresh, btnClose;
    private JPanel buttonPanel;
    private DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

    public PointValuesTableWindow() {
        this.setTitle("Table of Points");
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnClose = new JButton("Close");

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        this.refreshTable();

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(200, 300));

        btnRefresh.addActionListener(this);
        btnClose.addActionListener(this);

    }

    private void refreshTable(){
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("Name");
        tableModel.addColumn("X Value");
        tableModel.addColumn("Y Value");
        for(String key : GraphPanel.getPoints().keySet()){
            Vector<String> row = new Vector<String>(3);
            row.add(key);
            row.add(df.format(GraphPanel.getPoint(key).getX()));
            row.add(df.format(GraphPanel.getPoint(key).getY()));

            tableModel.addRow(row);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnRefresh){
            refreshTable();
        }
        if(e.getSource() == btnClose){
            this.dispose();
        }
    }
}
