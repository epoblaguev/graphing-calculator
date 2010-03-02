/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
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
public class DrawTangentLineDialog extends JFrame implements ActionListener {

    private JLabel lblEquation, lblXValue;
    private GraphingTab graphTab;
    private JTextField txtInput;
    private JComboBox cbFrom;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;
    private String expression;

    public DrawTangentLineDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        lblEquation = new JLabel("Equation:");
        lblXValue = new JLabel("At X Value:");
        cbFrom = new JComboBox();
        txtInput = new JTextField();

        for (int i = 0; i < graphTab.getEquationCount(); i++) {
            if (!((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                cbFrom.addItem("y"+(i+1));
                expression = ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText();
            }
        }

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);

        inputPanel.add(lblEquation);
        inputPanel.add(cbFrom);
        inputPanel.add(lblXValue);
        inputPanel.add(txtInput);
        bottomPanel.add(btnDraw);
        bottomPanel.add(btnClose);

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //If Add.
        if (e.getSource() == btnDraw) {
            GraphPanel graphPanel = new GraphPanel();
            double pt1 = Double.parseDouble(this.txtInput.getText())-(1/(2^16));
            double pt2 = Double.parseDouble(this.txtInput.getText())+(1/(2^16));

            System.out.println(graphPanel.evaluate(expression, pt1));
            System.out.println(graphPanel.evaluate(expression, pt2));
            System.out.println(pt1);
            System.out.println(pt2);

            double slope = (graphPanel.evaluate(expression, pt1) - graphPanel.evaluate(expression, pt2)) / (pt1 - pt2);
            double yIntercept = (graphPanel.evaluate(expression, pt1) - (slope * pt1));
            boolean foundEmpty = false;
            for (int i = 0; i < graphTab.getEquationCount(); i++) {
                if (((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().getText().isEmpty()) {
                    ((EquationInput) graphTab.getEquationPanel().getComponent(i)).getInput().setText(slope + "x+(" + yIntercept + ")");
                    foundEmpty = true;
                    break;
                }
            }
            if (foundEmpty == false) {
                graphTab.getBtnAddEquation().doClick();
                ((EquationInput) graphTab.getEquationPanel().getComponent(graphTab.getEquationCount() - 1)).getInput().setText(slope + "x+(" + yIntercept + ")");
            }
            graphTab.getBtnGraph().doClick();
            this.dispose();
        }

        //If Close
        if (e.getSource() == btnClose) {
            this.dispose();
        }
    }
}
