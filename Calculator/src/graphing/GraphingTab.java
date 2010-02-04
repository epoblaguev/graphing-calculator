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
    private JPanel eastPanel, southPanel, graphWrapper;
    private JLabel y1Label;
    private JTextField y1Text;
    private JButton graphButton, leftButton, rightButton, upButton, downButton;

    public GraphingTab() {
        this.setLayout(new BorderLayout());

        //Initialize panels
        graphPanel = new GraphPanel();
        eastPanel = new JPanel();
        southPanel = new JPanel();
        graphWrapper = new JPanel(new BorderLayout());

        //Initialize graphWrapper items;
        leftButton = new JButton("<");
        rightButton = new JButton(">");
        upButton = new JButton("^");
        downButton = new JButton("\\/");

        //Initialize southPanel items.
        y1Label = new JLabel("y1:");
        y1Text = new JTextField(20);
        graphButton = new JButton("Graph");

        //Add to wrapper panel
        graphWrapper.add(graphPanel, BorderLayout.CENTER);
        graphWrapper.add(leftButton, BorderLayout.WEST);
        graphWrapper.add(rightButton, BorderLayout.EAST);
        graphWrapper.add(upButton, BorderLayout.NORTH);
        graphWrapper.add(downButton, BorderLayout.SOUTH);
        
        //Add to southPanel
        southPanel.setLayout(new FlowLayout());
        southPanel.add(y1Label);
        southPanel.add(y1Text);
        southPanel.add(graphButton);

        //Add Action Listeners
        graphButton.addActionListener(this);
        leftButton.addActionListener(this);
        rightButton.addActionListener(this);
        upButton.addActionListener(this);
        downButton.addActionListener(this);




        graphPanel.drawGrid();
        graphPanel.setSize(50,50);
        this.add(graphWrapper, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

        graphPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == graphButton){
            String expression = this.y1Text.getText();
            graphPanel.drawGraph(expression);
        }
        if(e.getSource() == leftButton){
            graphPanel.moveHorizontal(-10);
        }
        if(e.getSource() == rightButton){
            graphPanel.moveHorizontal(10);
        }
        if(e.getSource() == upButton){
            graphPanel.moveVertical(10);
        }
        if(e.getSource() == downButton){
            graphPanel.moveVertical(-10);
        }
    }
}
