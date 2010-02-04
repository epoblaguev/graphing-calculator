/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import exceptions.InvalidVariableNameException;
import expressions.VariableList;
import expressions.Expression;
import expressions.Variable;
import expressions.ExpressionList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Egor
 */
public class CalculatorTab extends JPanel implements ActionListener {

    JScrollPane exprScrollPane, varScrollPane;
    JPanel controlPanel, controlPanelEast;
    JPanel centerPanel, centerPanelEast, centerPanelWest, exprControlPanel, varControlPanel;
    JTextField txtInput;
    JButton btnEnter, btnAddVariable, btnRemoveVariable, btnClearExpressions, btnCopyToInput;
    JTable varTable, exprTable;

    public CalculatorTab() {
        this.setLayout(new BorderLayout());
        this.createControlPanel();
        this.createCenterPanel();

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        try {
            this.createTables();
        } catch (InvalidVariableNameException ex) {
            Logger.getLogger(CalculatorTab.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanelEast = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanelEast.setLayout(new FlowLayout());

        txtInput = new JTextField();

        btnEnter = new JButton("Enter");
        btnEnter.addActionListener(this);

        controlPanelEast.add(btnEnter);
        controlPanel.add(txtInput, BorderLayout.CENTER);
        controlPanel.add(controlPanelEast, BorderLayout.EAST);
    }

    private void createCenterPanel() {
        centerPanel = new JPanel();
        centerPanelEast = new JPanel();
        centerPanelWest = new JPanel();
        exprControlPanel = new JPanel();
        varControlPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanelWest.setLayout(new BorderLayout());
        centerPanelEast.setLayout(new BorderLayout());
        varControlPanel.setLayout(new FlowLayout());
        exprControlPanel.setLayout(new FlowLayout());

        //Create variable and expression tables
        varTable = new VariableTablePane();
        exprTable = new ExpressionTablePane();

        //Create Scroll Panes
        exprScrollPane = new JScrollPane(exprTable);
        varScrollPane = new JScrollPane(varTable);

        //Create Buttons
        btnAddVariable = new JButton("Add");
        btnRemoveVariable = new JButton("Remove");
        btnClearExpressions = new JButton("Clear");
        btnCopyToInput = new JButton("Copy To Input");

        //Add Action Listeners
        btnAddVariable.addActionListener(this);
        btnRemoveVariable.addActionListener(this);
        btnClearExpressions.addActionListener(this);
        btnCopyToInput.addActionListener(this);

        //Add buttons to control panels
        varControlPanel.add(btnAddVariable);
        varControlPanel.add(btnRemoveVariable);
        exprControlPanel.add(btnClearExpressions);
        exprControlPanel.add(btnCopyToInput);

        //Add to variable pannel.
        centerPanelEast.add(varScrollPane, BorderLayout.CENTER);
        centerPanelEast.add(varControlPanel, BorderLayout.SOUTH);

        //Add to expression pannel
        centerPanelWest.add(exprScrollPane, BorderLayout.CENTER);
        centerPanelWest.add(exprControlPanel, BorderLayout.SOUTH);

        //Add to center panel
        centerPanel.add(centerPanelWest);
        centerPanel.add(centerPanelEast);
    }

    private void createTables() throws InvalidVariableNameException {
        ExpressionTablePane.refreshTable();
        VariableTablePane.refreshTable();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnter) {
            Expression exp = new Expression(txtInput.getText());
            try {
                ExpressionList.addExpression(exp);
                ExpressionTablePane.refreshTable();
            } catch (Exception exc) {
                ExpressionList.removeExpression(exp);
                JOptionPane.showMessageDialog(this, exc);
            }
        }

        if (e.getSource() == btnAddVariable) {
            JFrame window = new AddVariableWindow();
            window.setVisible(true);
            window.pack();
        }

        if (e.getSource() == btnRemoveVariable) {
            if (varTable.getSelectedRow() >= 0) {
                try {
                    VariableList.removeVariable(varTable.getSelectedRow());
                    VariableTablePane.refreshTable();
                } catch (InvalidVariableNameException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid variable name.");
                }
            } else {

                JOptionPane.showMessageDialog(this, "Please select a variable to remove.");
            }
        }

        if (e.getSource() == btnClearExpressions) {
            try {
                ExpressionList.clearExpressionList();
                ExpressionTablePane.refreshTable();
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, exc);
            }
        }

        if (e.getSource() == btnCopyToInput) {
            if (exprTable.getSelectedRow() >= 0) {
                String copy = ((Expression) ExpressionList.getExpressionList().get(exprTable.getSelectedRow())).getExpression();
                txtInput.setText(txtInput.getText() + "(" + copy + ")");
            } else {
                JOptionPane.showMessageDialog(this, "Please select an expression to copy.");
            }
        }
    }
}
