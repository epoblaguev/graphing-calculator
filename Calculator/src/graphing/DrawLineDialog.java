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

/**
 *
 * @author Administrator
 */
public class DrawLineDialog extends JFrame implements ActionListener {

    private JLabel lblFrom, lblTo;
    private GraphingTab graphTab;
    private JComboBox cbFrom, cbTo;
    private JPanel inputPanel, bottomPanel;
    private JButton btnDraw;
    private JButton btnClose;

    public DrawLineDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        lblFrom = new JLabel("Point 1:");
        lblTo = new JLabel("Point 2:");
        cbFrom = new JComboBox(GraphPanel.getPoints().keySet().toArray());
        cbTo = new JComboBox(GraphPanel.getPoints().keySet().toArray());

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);

        inputPanel.add(lblFrom);
        inputPanel.add(cbFrom);
        inputPanel.add(lblTo);
        inputPanel.add(cbTo);
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
            Point2D.Double pt1 = GraphPanel.getPoint((String) cbTo.getSelectedItem());
            Point2D.Double pt2 = GraphPanel.getPoint((String) cbFrom.getSelectedItem());

            if (pt1.equals(pt2)) {
                JOptionPane.showMessageDialog(this, "Select different points.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double slope = (pt1.getY() - pt2.getY()) / (pt1.getX() - pt2.getX());
            double yIntercept = pt1.getY() - (slope * pt1.getX());
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
