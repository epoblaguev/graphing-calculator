/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import components.UneditableTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * -- Needs Commenting --
 * @author Egor
 */
class PointValuesTableWindow extends JFrame implements ActionListener{

    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final UneditableTable table = new UneditableTable(tableModel);
    private JScrollPane scrollPane  = new JScrollPane(table);
    private final JButton btnRefresh;
    private final JButton btnClose;
    private final JPanel buttonPanel;
    private final DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

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
            Vector<String> row = new Vector<>(3);
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
