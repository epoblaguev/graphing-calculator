/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import equations.Equation;
import equations.EquationInput;
import Constants.ConstValues;
import Settings.GenSettings;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class AddMinMaxPointDialog extends JFrame implements ActionListener, KeyListener {

    public static final int MIN = 1;
    public static final int MAX = 2;
    private GraphingTab graphTab;
    private JTextField txtLowX, txtHighX, txtPointName;
    private JComboBox cbEquation;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;
    private String expression;
    private DecimalFormat df = new DecimalFormat(ConstValues.DF_10);
    private int MinOrMax;

    public AddMinMaxPointDialog(GraphingTab graphTab, double x, double range, int MinOrMax) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Add Min/Max Point");

        this.graphTab = graphTab;
        this.MinOrMax = MinOrMax;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        cbEquation = new JComboBox();
        txtPointName = new JTextField();
        txtLowX = new JTextField(df.format(x - (range / 20.0)));
        txtHighX = new JTextField(df.format(x + (range / 20.0)));

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

    @Override
    public void actionPerformed(ActionEvent e) {

        //If Add.
        if (e.getSource() == btnDraw) {
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getBtnName().getText().equals(cbEquation.getSelectedItem())) {
                    expression = ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText();
                }
            }

            double start = Math.min(Expression.evaluate(txtLowX.getText()),Expression.evaluate(txtHighX.getText()));
            double finish = Math.max(Expression.evaluate(txtLowX.getText()),Expression.evaluate(txtHighX.getText()));
            double interval = Math.abs(finish - start) / 1000;
            boolean foundMin = false;
            double xValue = start;
            double yValue = Equation.evaluate(expression, xValue, true);

            double prev = Equation.evaluate(expression, start, true);
            double cur = Equation.evaluate(expression, start, true);
            double tracker = 0;

            while (interval > ConstValues.smallestNum) {

                for (double i = start + interval; i <= finish; i += interval) {
                    double next = Equation.evaluate(expression, i, true);

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
