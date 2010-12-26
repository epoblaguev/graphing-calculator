/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import equations.Equation;
import equations.EquationInput;
import Constants.ConstValues;
import components.SmartTextField;
import expressions.Expression;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * -- Needs Commenting --
 * @author Administrator
 */
public class AddPointAtXValueDialog extends JFrame implements ActionListener, KeyListener {

    private GraphingTab graphTab;
    private SmartTextField txtValue;
    private JTextField txtPointName;
    private JComboBox cbEquation;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;
    private String expression;
    private DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

    public AddPointAtXValueDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Add Point to Equation");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        cbEquation = new JComboBox();
        txtValue = new SmartTextField();
        txtPointName = new JTextField();

        for (Component eq : graphTab.getEquationPanel().getComponents()) {
            if (!((EquationInput) eq).getInput().getText().isEmpty()) {
                cbEquation.addItem(((EquationInput) eq).getBtnName().getText());
            }
        }

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);
        txtValue.addKeyListener(this);
        txtPointName.addActionListener(this);
        cbEquation.addKeyListener(this);

        inputPanel.add(new JLabel("Point Name:"));
        inputPanel.add(txtPointName);
        inputPanel.add(new JLabel("Equation:"));
        inputPanel.add(cbEquation);
        inputPanel.add(new JLabel("At X Value:"));
        inputPanel.add(txtValue);
        bottomPanel.add(btnDraw);
        bottomPanel.add(btnClose);

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
        this.setMinimumSize(this.getSize());
    }

    
    public void actionPerformed(ActionEvent e) {

        //If Add.
        if (e.getSource() == btnDraw) {
            double x = Expression.evaluate(txtValue.getText());

            for (Component eq : graphTab.getEquationPanel().getComponents()) {
                if (((EquationInput) eq).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                    expression = ((EquationInput) eq).getInput().getText();
                }
            }

            GraphPanel.addPoint(txtPointName.getText(), x, Equation.evaluate(expression, x, true));
            graphTab.repaint();
        }

        //If Close
        if (e.getSource() == btnClose) {
            this.dispose();
        }
    }

    public void keyTyped(KeyEvent e) {
        //
    }

    public void keyPressed(KeyEvent e) {
        //
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btnDraw.doClick();
        }
    }
}
