/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import exceptions.InvalidVariableNameException;
import expressions.Variable;
import expressions.VariableList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Egor
 */
public class AddVariableWindow extends JFrame implements ActionListener {

    private JLabel lblVariableName, lblVariableValue;
    private JTextField txtVariableName, txtVariableValue;
    private JButton btnAdd, btnCancel;
    private JPanel topPanel, middlePanel, bottomPanel;

    public AddVariableWindow() {
        super();
        this.setLayout(new GridLayout(0, 1));
        this.setTitle("Add Variable");

        topPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        bottomPanel = new JPanel(new FlowLayout());

        lblVariableName = new JLabel("Variable Name:");
        txtVariableName = new JTextField(10);
        lblVariableValue = new JLabel("Variable Value:");
        txtVariableValue = new JTextField(10);

        btnAdd = new JButton("Add");
        btnCancel = new JButton("Cancel");
        btnAdd.addActionListener(this);
        btnCancel.addActionListener(this);

        topPanel.add(lblVariableName, BorderLayout.WEST);
        topPanel.add(txtVariableName, BorderLayout.EAST);

        middlePanel.add(lblVariableValue, BorderLayout.WEST);
        middlePanel.add(txtVariableValue, BorderLayout.EAST);

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnCancel);

        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnAdd) {
                Variable var = new Variable(txtVariableName.getText(), Double.valueOf(txtVariableValue.getText()));

                Iterator itr = VariableList.getVariableList().iterator();

                while (itr.hasNext()) {
                    Variable curVar = (Variable) itr.next();
                    if (var.getVariableName().equals(curVar.getVariableName())) {
                        JOptionPane.showMessageDialog(this, "Variable with that name already exists");
                        return;
                    }
                }
                VariableList.addVariable(var);
                VariableTablePane.refreshTable();
            }

            if (e.getSource() == btnCancel) {
                this.dispose();
            }
        } catch (InvalidVariableNameException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
