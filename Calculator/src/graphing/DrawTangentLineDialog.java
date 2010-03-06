/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import Settings.GenSettings;
import expressions.Expression;
import java.awt.BorderLayout;
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
 *
 * @author Administrator
 */
public class DrawTangentLineDialog extends JFrame implements ActionListener, KeyListener {

    private JLabel lblEquation, lblXValue;
    private GraphingTab graphTab;
    private JTextField txtInput;
    private JComboBox cbFrom;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;
    private String expression;
    private DecimalFormat df = new DecimalFormat("#.#######");

    public DrawTangentLineDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Tangent Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        lblEquation = new JLabel("Equation:");
        lblXValue = new JLabel("At X Value:");
        cbFrom = new JComboBox();
        txtInput = new JTextField();

        for (int i = 0; i < graphTab.getEquationCount(); i++) {
            if (!((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                cbFrom.addItem(((EquationInput) graphTab.getEquationPanel().getComponent(i)).getBtnName().getText());
            }
        }

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);
        txtInput.addKeyListener(this);
        cbFrom.addKeyListener(this);

        inputPanel.add(lblEquation);
        inputPanel.add(cbFrom);
        inputPanel.add(lblXValue);
        inputPanel.add(txtInput);
        bottomPanel.add(btnDraw);
        bottomPanel.add(btnClose);

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
        this.setMinimumSize(this.getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //If Add.
        if (e.getSource() == btnDraw) {
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getBtnName().getText().equals(cbFrom.getSelectedItem())) {
                    expression = ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText();
                }
            }

            double pt1 = new Expression(this.txtInput.getText()).evaluate() - ConstValues.smallestNum;
            double pt2 = new Expression(this.txtInput.getText()).evaluate() + ConstValues.smallestNum;

            double slope = ((Equation.evaluate(expression, pt1, true) - Equation.evaluate(expression, pt2, true)) / (pt1 - pt2));
            double yIntercept = (Equation.evaluate(expression, pt1, true) - (slope * pt1));
            boolean foundEmpty = false;
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                    ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().setText(df.format(slope) + "x+(" + df.format(yIntercept) + ")");
                    foundEmpty = true;
                    break;
                }
            }
            if (foundEmpty == false) {
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

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            btnDraw.doClick();
        }
    }
}
