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
 *  ---NEEDS TO BE COMMENTED---
 * @author Administrator
 */
class AddMinMaxPointDialog extends JFrame implements ActionListener, KeyListener {

    public static final int MIN = 1;
    public static final int MAX = 2;
    private final GraphingTab graphTab;
    private final SmartTextField txtLowX;
    private final SmartTextField txtHighX;
    private final JTextField txtPointName;
    private final JComboBox<String> cbEquation;
    private final JPanel inputPanel;
    private final JPanel bottomPanel;
    private final JButton btnDraw;
    private final JButton btnClose;
    private String expression;
    private final DecimalFormat df = new DecimalFormat(ConstValues.DF_10);
    private final int MinOrMax;

    public AddMinMaxPointDialog(GraphingTab graphTab, double x, double range, int MinOrMax) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Add Min/Max Point");

        this.graphTab = graphTab;
        this.MinOrMax = MinOrMax;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        cbEquation = new JComboBox<>();
        txtPointName = new JTextField();
        txtLowX = new SmartTextField(df.format(x - (range / 20.0)));
        txtHighX = new SmartTextField(df.format(x + (range / 20.0)));

        for (Component eq : graphTab.getEquationPanel().getComponents()) {
            if (!((EquationInput) eq).getInput().getText().isEmpty()) {
                cbEquation.addItem(((EquationInput) eq).getBtnName().getText());
            }
        }

        btnDraw = new JButton("Plot Point");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);
        txtLowX.addKeyListener(this);
        txtHighX.addKeyListener(this);
        txtPointName.addKeyListener(this);
        cbEquation.addKeyListener(this);

        inputPanel.add(new JLabel("Point Name:"));
        inputPanel.add(txtPointName);
        inputPanel.add(new JLabel("Equation:"));
        inputPanel.add(cbEquation);
        inputPanel.add(new JLabel("Low X:"));
        inputPanel.add(txtLowX);
        inputPanel.add(new JLabel("High X:"));
        inputPanel.add(txtHighX);
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
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                    expression = ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText();
                }
            }

            double start;
			try {
				start = Math.min(Expression.evaluate(txtLowX.getText()),Expression.evaluate(txtHighX.getText()));
			} catch (Exception e2) {
				start = Double.NaN;
			}
            double finish;
			try {
				finish = Math.max(Expression.evaluate(txtLowX.getText()),Expression.evaluate(txtHighX.getText()));
			} catch (Exception e1) {
				finish = Double.NaN;
			}
			Equation equation = new Equation(expression,null);
            double interval = Math.abs(finish - start) / 1000;
            boolean foundMin = false;
            double xValue = start;
            double yValue = equation.evaluate(xValue);

            double prev = equation.evaluate(start);
            double cur = equation.evaluate(start);
            double tracker = 0;

            while (interval > ConstValues.smallestNum) {

                for (double i = start + interval; i <= finish; i += interval) {
                    double next = equation.evaluate(i);

                    if (MinOrMax == AddMinMaxPointDialog.MIN) {
                        if (prev > cur && cur < next) {
                            foundMin = true;
                            xValue = i - interval;
                            yValue = cur;
                            break;
                        }
                    } else {
                        if (prev < cur && cur > next) {
                            foundMin = true;
                            xValue = i - interval;
                            yValue = cur;
                            break;
                        }
                    }

                    prev = cur;
                    cur = next;
                    tracker = i;
                }

                if (!foundMin) {
                    break;
                }

                start = tracker - interval;
                interval = interval / 2;
            }

            if (foundMin) {
                GraphPanel.addPoint(txtPointName.getText(), xValue, yValue);
            } else {
                if (MinOrMax == AddMinMaxPointDialog.MIN) {
                    JOptionPane.showMessageDialog(this, "Minimum point not found.");
                } else {
                    JOptionPane.showMessageDialog(this, "Maximum point not found.");
                }
            }
            graphTab.repaint();
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
