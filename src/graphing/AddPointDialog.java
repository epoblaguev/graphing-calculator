/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import components.SmartTextField;
import expressions.Expression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

/**
 *  -- Needs Commenting --
 * @author Egor
 */
class AddPointDialog extends JFrame implements ActionListener, KeyListener {

    private JTextField txtPointName;
    private SmartTextField txtXValue, txtYValue;
    private JButton btnAdd, btnClose;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private final JPanel caller;

    public AddPointDialog(JPanel caller, double x, double y) {
        super();
        makeLayout();

        this.caller = caller;
        DecimalFormat df = new DecimalFormat(ConstValues.DF_10);
        txtXValue.setText(df.format(x));
        txtYValue.setText(df.format(y));
        this.pack();
        this.setMinimumSize(this.getSize());
    }

    private void makeLayout() {
        this.setLayout(new BorderLayout());
        this.setTitle("Add Point");

        topPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel(new FlowLayout());

        txtPointName = new JTextField();
        txtXValue = new SmartTextField();
        txtYValue = new SmartTextField();

        btnAdd = new JButton("Add");
        btnClose = new JButton("Close");
        btnAdd.addActionListener(this);
        btnClose.addActionListener(this);
        txtPointName.addKeyListener(this);
        txtXValue.addKeyListener(this);
        txtYValue.addKeyListener(this);

        topPanel.add(new JLabel("Point Name:"));
        topPanel.add(txtPointName);

        topPanel.add(new JLabel("X:"));
        topPanel.add(txtXValue);

        topPanel.add(new JLabel("Y:"));
        topPanel.add(txtYValue);

        bottomPanel.add(btnAdd);
        bottomPanel.add(btnClose);

        this.add(topPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

   
    public void actionPerformed(ActionEvent e) {
        try {
            //If Add.
            if (e.getSource() == btnAdd) {
                String name = txtPointName.getText();
                double x = Expression.evaluate(txtXValue.getText());
                double y = Expression.evaluate(txtYValue.getText());
                GraphPanel.addPoint(name, x, y);

                this.caller.repaint();
                this.dispose();
            }

            //If Close
            if (e.getSource() == btnClose) {
                this.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
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
            btnAdd.doClick();
        }
    }
}
