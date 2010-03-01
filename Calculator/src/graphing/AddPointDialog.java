/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField; 

/**
 *
 * @author Egor
 */
public class AddPointDialog extends JFrame implements ActionListener {

    private JLabel lblPointName, lblXValue, lblYValue;
    private JTextField txtPointName, txtXValue, txtYValue;
    private JButton btnAdd, btnClose;
    private JPanel topPanel, bottomPanel, caller;

    /**
     * Constructor. Creates the form.
     */
    public AddPointDialog(JPanel caller) {
        super();
        this.caller = caller;
        makeLayout();
        this.pack();
    }

    public AddPointDialog(JPanel caller, double x, double y) {
        super();
        makeLayout();

        this.caller = caller;
        DecimalFormat df = new DecimalFormat("#.#########");
        txtXValue.setText(df.format(x));
        txtYValue.setText(df.format(y));
        this.pack();
    }

    private void makeLayout() {
        this.setLayout(new BorderLayout());
        this.setTitle("Add Point");

        topPanel = new JPanel(new GridLayout(0, 2));
        bottomPanel = new JPanel(new FlowLayout());

        lblPointName = new JLabel("Point Name:");
        txtPointName = new JTextField(10);
        lblXValue = new JLabel("X:");
        txtXValue = new JTextField(10);
        lblYValue = new JLabel("Y:");
        txtYValue = new JTextField(10);

        btnAdd = new JButton("Add");
        btnClose = new JButton("Close");
        btnAdd.addActionListener(this);
        btnClose.addActionListener(this);

        topPanel.add(lblPointName);
        topPanel.add(txtPointName);

        topPanel.add(lblXValue);
        topPanel.add(txtXValue);

        topPanel.add(lblYValue);
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
                double x = Double.parseDouble(txtXValue.getText());
                double y = Double.parseDouble(txtYValue.getText());
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
}
