/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Egor
 */
public class GraphingTab extends JPanel implements ActionListener {

    private GraphPanel graphPanel;
    private JPanel eastPanel, southPanel;
    private JLabel y1Label;
    private JTextField y1Text;
    private JButton graphButton;

    public GraphingTab() {
        this.setLayout(new BorderLayout());

        //Initialize panels
        graphPanel = new GraphPanel();
        eastPanel = new JPanel();
        southPanel = new JPanel();

        //Initialize southPanel items.
        y1Label = new JLabel("y1:");
        y1Text = new JTextField(10);
        graphButton = new JButton("Graph");
        
        //Add to southPanel
        southPanel.setLayout(new FlowLayout());
        southPanel.add(y1Label);
        southPanel.add(y1Text);
        southPanel.add(graphButton);

        //Add Action Listeners
        graphButton.addActionListener(this);




        graphPanel.drawGrid();
        graphPanel.setSize(50,50);
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

        graphPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == graphButton){
            String expression = this.y1Text.getText();
            graphPanel.drawGraph(expression);
        }
    }
}
