/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
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
import java.text.DecimalFormat;

/**
 *-- Needs Commenting --
 * @author Administrator
 */
class DrawTangentLineDialog extends JFrame implements ActionListener, KeyListener {

    private final GraphingTab graphTab;
    private final SmartTextField txtInput;
    private final JComboBox<String> cbEquation;
    private final JPanel inputPanel;
    private final JPanel bottomPanel;
    private final JButton btnDraw;
    private final JButton btnClose;
    private String expression;
    private final DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

    public DrawTangentLineDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Tangent Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();
        
        cbEquation = new JComboBox<>();
        txtInput = new SmartTextField();

        for (Component eq : graphTab.getEquationPanel().getComponents()) {
            if (!((EquationInput) eq).getInput().getText().isEmpty()) {
                cbEquation.addItem(((EquationInput) eq).getBtnName().getText());
            }
        }

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);
        txtInput.addKeyListener(this);
        cbEquation.addKeyListener(this);

        inputPanel.add(new JLabel("Equation:"));
        inputPanel.add(cbEquation);
        inputPanel.add(new JLabel("At X Value:"));
        inputPanel.add(txtInput);
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
            for (Component eq : graphTab.getEquationPanel().getComponents()) {
                if (((EquationInput) eq).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                    expression = ((EquationInput) eq).getInput().getText();
                }
            }

            double pt1;
			try {
				pt1 = Expression.evaluate(this.txtInput.getText()) - ConstValues.smallestNum;
			} catch (Exception e1) {
				pt1 = Double.NaN;
			}
            double pt2;
			try {
				pt2 = Expression.evaluate(this.txtInput.getText()) + ConstValues.smallestNum;
			} catch (Exception e2) {
				pt2= Double.NaN;
			}

			Equation equation = new Equation(expression,null);
            double slope = ((equation.evaluate(pt1) - equation.evaluate(pt2)) / (pt1 - pt2));
            double yIntercept = (equation.evaluate(pt1) - (slope * pt1));
            boolean foundEmpty = false;
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                    ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().setText(df.format(slope) + "x+(" + df.format(yIntercept) + ")");
                    foundEmpty = true;
                    break;
                }
            }
            if (!foundEmpty) {
                graphTab.getBtnAddEquation().doClick();
                ((EquationInput) graphTab.getEquationPanel().getComponent(graphTab.getEquationCount() - 1)).getInput().setText(df.format(slope) + "x+(" + df.format(yIntercept) + ")");
            }
            graphTab.getBtnGraph().doClick();
            this.dispose();
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
