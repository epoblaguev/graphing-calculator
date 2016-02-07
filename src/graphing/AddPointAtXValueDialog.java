/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import components.SmartTextField;
import equations.Equation;
import equations.EquationInput;
import expressions.Expression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * -- Needs Commenting --
 * @author Administrator
 */
class AddPointAtXValueDialog extends JFrame implements ActionListener, KeyListener {

    private final GraphingTab graphTab;
    private final SmartTextField txtValue;
    private final JTextField txtPointName;
    private final JComboBox<String> cbEquation;
    private final JPanel inputPanel;
    private final JPanel bottomPanel;
    private final JButton btnDraw;
    private final JButton btnClose;
    private String expression;
    
    public AddPointAtXValueDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Add Point to Equation");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        cbEquation = new JComboBox<>();
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
            double x;
			try {
				x = Expression.evaluate(txtValue.getText());
			} catch (Exception e1) {
				x=Double.NaN;
			}

            for (Component eq : graphTab.getEquationPanel().getComponents()) {
                if (((EquationInput) eq).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                    expression = ((EquationInput) eq).getInput().getText();
                }
            }
            
            Equation equation = new Equation(expression,null);
            GraphPanel.addPoint(txtPointName.getText(), x, equation.evaluate(x));
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
