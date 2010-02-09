/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphing;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Egor
 */
public class EquationInput extends JPanel{
    private JLabel label;
    private JPanel labelPanel;
    private JTextField input;
    private Color color;

    public EquationInput(String name, Color color){
        super();
        this.setLayout(new FlowLayout());
        this.label = new JLabel(name);
        this.input = new JTextField(20);
        this.labelPanel = new JPanel();
        this.color = color;
        
        this.label.setForeground(color);
        this.labelPanel.setBackground(Color.lightGray);

        this.add(labelPanel);
        labelPanel.add(label);
        labelPanel.add(input);
    }

    public JTextField getInput() {
        return input;
    }

    public void setInput(JTextField input) {
        this.input = input;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    
}
