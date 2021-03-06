/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import equations.EquationInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

/**
 *-- Needs Commenting --
 * @author Administrator
 */
class DrawLineBetweenPointsDialog extends JFrame implements ActionListener {

    private final GraphingTab graphTab;
    private final JComboBox<?> cbFrom;
    private final JComboBox<?> cbTo;
    private final JPanel inputPanel;
    private final JPanel bottomPanel;
    private final JButton btnDraw;
    private final JButton btnClose;
    private final DecimalFormat df = new DecimalFormat(ConstValues.DF_10);

    public DrawLineBetweenPointsDialog(GraphingTab graphTab) {
        super();
        this.setLayout(new BorderLayout());
        this.setTitle("Draw Line");

        this.graphTab = graphTab;

        inputPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel();

        cbFrom = new JComboBox<>(GraphPanel.getPoints().keySet().toArray());
        cbTo = new JComboBox<>(GraphPanel.getPoints().keySet().toArray());

        btnDraw = new JButton("Draw");
        btnClose = new JButton("Close");
        btnDraw.addActionListener(this);
        btnClose.addActionListener(this);

        inputPanel.add(new JLabel("Point 1:"));
        inputPanel.add(cbFrom);
        inputPanel.add(new JLabel("Point 2:"));
        inputPanel.add(cbTo);
        bottomPanel.add(btnDraw);
        bottomPanel.add(btnClose);

        this.add(inputPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
    }

    public void actionPerformed(ActionEvent e) {

        //If Add.
        if (e.getSource() == btnDraw) {
            Point2D.Double pt1 = GraphPanel.getPoint((String) cbTo.getSelectedItem());
            Point2D.Double pt2 = GraphPanel.getPoint((String) cbFrom.getSelectedItem());

            if (pt1.equals(pt2)) {
                JOptionPane.showMessageDialog(this, "Select different points.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(pt1.getX() == pt2.getX()){
                JOptionPane.showMessageDialog(this, "Can not draw vertical lines!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double slope = (pt1.getY() - pt2.getY()) / (pt1.getX() - pt2.getX());
            double yIntercept = (pt1.getY() - (slope * pt1.getX()));
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
}
