/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Egor
 */
public class GraphingTab extends JPanel implements ActionListener {

    private GraphPanel graphPanel;
    private JPanel eastPanel, southPanel, directionPanel, boundsPanel;
    private JLabel lblY1, lblMinX, lblMaxX, lblMinY, lblMaxY;
    private JTextField txtY1, txtMinX, txtMaxX, txtMinY, txtMaxY;
    private JButton btnGraph, btnLeft, btnRight, btnUp, btnDown;

    public GraphingTab() {
        super();
        this.setLayout(new BorderLayout());

        //Initialize panels
        graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        directionPanel = new JPanel(new BorderLayout());
        directionPanel.setSize(100, 80);
        directionPanel.setMaximumSize(directionPanel.getSize());
        boundsPanel = new JPanel(new GridLayout(0,2));
        boundsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boundsPanel.setSize(100, 80);
        boundsPanel.setMaximumSize(boundsPanel.getSize());

        //Initialize directionPanel items
        btnLeft = new JButton("<");
        btnRight = new JButton(">");
        btnUp = new JButton("^");
        btnDown = new JButton("\\/");

        //Initialize southPanel items.
        lblY1 = new JLabel("y1:");
        txtY1 = new JTextField(20);
        btnGraph = new JButton("Graph");

        //Initialize boundsPanel items
        lblMinX = new JLabel("Min X:");
        txtMinX = new JTextField(5);
        lblMaxX = new JLabel("Max X:");
        txtMaxX = new JTextField(5);
        lblMinY = new JLabel("Min Y:");
        txtMinY = new JTextField(5);
        lblMaxY = new JLabel("Max Y:");
        txtMaxY = new JTextField(5);

        //Add to directionPanel
        directionPanel.add(btnLeft, BorderLayout.WEST);
        directionPanel.add(btnRight, BorderLayout.EAST);
        directionPanel.add(btnUp, BorderLayout.NORTH);
        directionPanel.add(btnDown, BorderLayout.SOUTH);
        
        //Add to southPanel
        southPanel.setLayout(new FlowLayout());
        southPanel.add(lblY1);
        southPanel.add(txtY1);
        southPanel.add(btnGraph);

        //Add to eastPanel
        eastPanel.add(directionPanel);
        eastPanel.add(boundsPanel);

        //Add to boundsPanel
        boundsPanel.add(lblMinX);
        boundsPanel.add(txtMinX);
        boundsPanel.add(lblMaxX);
        boundsPanel.add(txtMaxX);
        boundsPanel.add(lblMinY);
        boundsPanel.add(txtMinY);
        boundsPanel.add(lblMaxY);
        boundsPanel.add(txtMaxY);

        //Add Action Listeners
        btnGraph.addActionListener(this);
        btnLeft.addActionListener(this);
        btnRight.addActionListener(this);
        btnUp.addActionListener(this);
        btnDown.addActionListener(this);




        graphPanel.drawGrid();
        graphPanel.setSize(50,50);
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

        graphPanel.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnGraph){
            String expression = this.txtY1.getText();
            graphPanel.drawGraph(expression);
        }
        if(e.getSource() == btnLeft){
            graphPanel.moveHorizontal(-10);
        }
        if(e.getSource() == btnRight){
            graphPanel.moveHorizontal(10);
        }
        if(e.getSource() == btnUp){
            graphPanel.moveVertical(10);
        }
        if(e.getSource() == btnDown){
            graphPanel.moveVertical(-10);
        }
    }
}
