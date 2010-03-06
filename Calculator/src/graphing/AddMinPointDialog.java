/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class AddMinPointDialog extends JFrame implements ActionListener, KeyListener {

    private JLabel lblEquation, lblLowX, lblHighX, lblPointName;
    private GraphingTab graphTab;
    private JTextField txtLowX, txtHighX, txtPointName;
    private JComboBox cbEquation;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;
    private String expression;
    private DecimalFormat df = new DecimalFormat("#.#######");

    public AddMinPointDialog(GraphingTab graphTab, double x, double range) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Tangent Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        lblPointName = new JLabel("Point Name:");
        lblEquation = new JLabel("Equation:");
        lblLowX = new JLabel("Low X:");
        lblHighX = new JLabel("High X:");
        cbEquation = new JComboBox();
        txtPointName = new JTextField();
        txtLowX = new JTextField(df.format(x - (range / 20.0)));
        txtHighX = new JTextField(df.format(x + (range / 20.0)));

        for (int i = 0; i < graphTab.getEquationCount(); i++) {
            if (!((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                cbEquation.addItem(((EquationInput) graphTab.getEquationPanel().getComponent(i)).getBtnName().getText());
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

        inputPanel.add(lblPointName);
        inputPanel.add(txtPointName);
        inputPanel.add(lblEquation);
        inputPanel.add(cbEquation);
        inputPanel.add(lblLowX);
        inputPanel.add(txtLowX);
        inputPanel.add(lblHighX);
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

            double start = Double.parseDouble(txtLowX.getText());
            double finish = Double.parseDouble(txtHighX.getText());
            double interval = (finish-start) / 1000;
            boolean foundMin = false;
            double xValue = start;
            double yValue = Equation.evaluate(expression, xValue, true);

            double prev = Equation.evaluate(expression, start, true);
            double cur = Equation.evaluate(expression, start, true);
            double tracker = 0;

            while (interval > ConstValues.smallestNum) {

                for (double i = start + interval; i <= finish; i += interval) {
                    double next = Equation.evaluate(expression, i, true);
                    
                    if (prev > cur && cur < next) {
                        foundMin = true;
                        xValue = i - interval;
                        yValue = cur;
                        break;
                    }
                    prev = cur;
                    cur = next;
                    tracker = i;
                }

                if(!foundMin) break;
                
                start = tracker - interval;
                interval = interval/2;
            }

            if (foundMin) {
                GraphPanel.addPoint(txtPointName.getText(), xValue, yValue);
            }
            else{
                JOptionPane.showMessageDialog(this, "Minimum point not found.");
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
