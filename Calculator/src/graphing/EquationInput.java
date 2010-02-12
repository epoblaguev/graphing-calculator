/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Egor
 */
public class EquationInput extends JPanel implements ActionListener {

    private JButton btnName;
    private JPanel labelPanel;
    private JTextField input;
    private Color color;

    public EquationInput(String name, Color color) {
        super();
        this.setLayout(new FlowLayout());
        this.btnName = new JButton(name);
        this.input = new JTextField(20);
        this.labelPanel = new JPanel();
        this.color = color;

        this.btnName.setForeground(color);
        this.btnName.setBorderPainted(true);
        this.btnName.setContentAreaFilled(false);
        this.btnName.setHorizontalAlignment(SwingConstants.RIGHT);
        this.btnName.setPreferredSize(new Dimension(60, 20));
        this.labelPanel.setBackground(Color.lightGray);

        this.btnName.addActionListener(this);

        this.add(labelPanel);
        labelPanel.add(this.btnName);
        labelPanel.add(input);
    }

    public JTextField getInput() {
        return input;
    }

    public void setInput(JTextField input) {
        this.input = input;
    }

    public JButton getBtnName() {
        return btnName;
    }

    public void setBtnName(JButton name) {
        this.btnName = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnName) {
            Random r = new Random();
            this.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));

            this.btnName.setForeground(color);
            this.revalidate();
        }
    }
}
