/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import Settings.Printer;
import components.SmartTextField;
import components.UneditableTable;
import equations.Equation;
import equations.EquationInput;
import expressions.Expression;

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
class EquationValueTableWindow extends JFrame implements ActionListener {

    private final DefaultTableModel tableModel = new DefaultTableModel();
    private final UneditableTable table = new UneditableTable(tableModel);
    private final SmartTextField txtLowX;
    private final SmartTextField txtHighX;
    private final SmartTextField txtInterval;
    private JScrollPane scrollPane = new JScrollPane(table);
    private final JButton btnRefresh;
    private final JButton btnClose;
    private final JPanel buttonPanel;
    private final JPanel optionsPanel;
    private final JPanel equationPanel;
    private final JComboBox<String> cbEquation;
    private final DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

    public EquationValueTableWindow(JPanel equationPanel) {
        this.setTitle("Table of Points");
        this.setLayout(new BorderLayout());

        this.equationPanel = equationPanel;

        txtLowX = new SmartTextField();
        txtHighX = new SmartTextField();
        txtInterval = new SmartTextField();
        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();
        optionsPanel = new JPanel(new GridLayout(0, 2));
        cbEquation = new JComboBox<>();
        btnRefresh = new JButton("Refresh");
        btnClose = new JButton("Close");

        for (Component eq : equationPanel.getComponents()) {
            if (!((EquationInput) eq).getInput().getText().isEmpty()) {
                cbEquation.addItem(((EquationInput) eq).getBtnName().getText());
            }
        }

        optionsPanel.add(new JLabel("Equation:"));
        optionsPanel.add(cbEquation);
        optionsPanel.add(new JLabel("From X ="));
        optionsPanel.add(txtLowX);
        optionsPanel.add(new JLabel("To X ="));
        optionsPanel.add(txtHighX);
        optionsPanel.add(new JLabel("Interval:"));
        optionsPanel.add(txtInterval);

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);

        this.add(optionsPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(200, 300));

        btnRefresh.addActionListener(this);
        btnClose.addActionListener(this);

        tableModel.addColumn("X Value");
        tableModel.addColumn("Y Value");
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        tableModel.addColumn("X Value");
        tableModel.addColumn("Y Value");
        double start;
        double finish;
        double interval;
        String expression = "";
        Equation equation = new Equation(expression,null);
        
        try{
        start = Expression.evaluate(txtLowX.getText());
        }catch(Exception e)
        {
        	start = Double.NaN;
        }
        try{
        finish = Expression.evaluate(txtHighX.getText());
        }catch(Exception e)
        {
        	finish= Double.NaN;
        }
        try{
        	interval = Expression.evaluate(txtInterval.getText());
        }catch(Exception e)
        {
        	interval = Double.NaN;
        }
        Printer.print(start);
        Printer.print(finish);
        Printer.print(interval);


        //What does this do?
        /*
        for (Component eq : equationPanel.getComponents()) {
            ((EquationInput) eq).getInput().getText();
            if (((EquationInput) eq).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                expression = ((EquationInput) eq).getInput().getText();
                break;
            }
        }
        */
        
        for (double i = start; i <= finish; i+=interval) {
            Vector<String> row = new Vector<>(2);
            row.add(df.format(i));
            row.add(df.format(equation.evaluate(i)));

            tableModel.addRow(row);
        }
        this.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRefresh) {
            refreshTable();
        }
        if (e.getSource() == btnClose) {
            this.dispose();
        }
    }
}
