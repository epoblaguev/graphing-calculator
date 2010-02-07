/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import exceptions.InvalidBoundsException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Dimension2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Egor
 */
public class GraphingTab extends JPanel implements ActionListener, MouseWheelListener, MouseMotionListener, FocusListener {

    private GraphPanel graphPanel;
    private JPanel eastPanel, southPanel, directionPanel, boundsPanel;
    private JLabel lblY1, lblMinX, lblMaxX, lblMinY, lblMaxY;
    private JTextField txtY1, txtMinX, txtMaxX, txtMinY, txtMaxY;
    private JButton btnGraph, btnLeft, btnRight, btnUp, btnDown, btnCenter;

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
        directionPanel.setSize(120, 80);
        directionPanel.setMaximumSize(directionPanel.getSize());
        boundsPanel = new JPanel(new GridLayout(0, 2));
        boundsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boundsPanel.setSize(120, 80);
        boundsPanel.setMaximumSize(boundsPanel.getSize());

        //Initialize directionPanel items
        btnLeft = new JButton("<");
        btnRight = new JButton(">");
        btnUp = new JButton("^");
        btnDown = new JButton("\\/");
        btnCenter = new JButton();

        //Initialize southPanel items.
        lblY1 = new JLabel("y1:");
        txtY1 = new JTextField(20);
        btnGraph = new JButton("Graph");

        //Initialize boundsPanel items
        lblMaxX = new JLabel("Max X:");
        txtMaxX = new JTextField(5);
        lblMinX = new JLabel("Min X:");
        txtMinX = new JTextField(5);
        lblMaxY = new JLabel("Max Y:");
        txtMaxY = new JTextField(5);
        lblMinY = new JLabel("Min Y:");
        txtMinY = new JTextField(5);

        //Add to directionPanel
        directionPanel.add(btnLeft, BorderLayout.WEST);
        directionPanel.add(btnRight, BorderLayout.EAST);
        directionPanel.add(btnUp, BorderLayout.NORTH);
        directionPanel.add(btnDown, BorderLayout.SOUTH);
        directionPanel.add(btnCenter, BorderLayout.CENTER);

        //Add to southPanel
        southPanel.setLayout(new FlowLayout());
        southPanel.add(lblY1);
        southPanel.add(txtY1);
        southPanel.add(btnGraph);

        //Add to eastPanel
        eastPanel.add(directionPanel);
        eastPanel.add(boundsPanel);

        //Add to boundsPanel
        boundsPanel.add(lblMaxX);
        boundsPanel.add(txtMaxX);
        boundsPanel.add(lblMinX);
        boundsPanel.add(txtMinX);
        boundsPanel.add(lblMaxY);
        boundsPanel.add(txtMaxY);
        boundsPanel.add(lblMinY);
        boundsPanel.add(txtMinY);

        //Add Listeners
        btnGraph.addActionListener(this);
        btnLeft.addActionListener(this);
        btnRight.addActionListener(this);
        btnUp.addActionListener(this);
        btnDown.addActionListener(this);
        btnCenter.addActionListener(this);
        graphPanel.addMouseMotionListener(this);
        graphPanel.addMouseWheelListener(this);
        txtMaxX.addFocusListener(this);
        txtMinX.addFocusListener(this);
        txtMaxY.addFocusListener(this);
        txtMinY.addFocusListener(this);


        graphPanel.drawGrid();
        graphPanel.setSize(50, 50);
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

        graphPanel.repaint();

        this.setBounds();
    }

    private void setBounds() {
        this.txtMaxX.setText(String.valueOf(graphPanel.getMaxX(4)));
        this.txtMinX.setText(String.valueOf(graphPanel.getMinX(4)));
        this.txtMaxY.setText(String.valueOf(graphPanel.getMaxY(4)));
        this.txtMinY.setText(String.valueOf(graphPanel.getMinY(4)));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGraph) {
            try {
                String expression = this.txtY1.getText();
                graphPanel.drawGraph(expression);
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this, "Error: " + exc.getMessage());
            }
        }
        if (e.getSource() == btnLeft) {
            graphPanel.moveHorizontal(-10);
        }
        if (e.getSource() == btnRight) {
            graphPanel.moveHorizontal(10);
        }
        if (e.getSource() == btnUp) {
            graphPanel.moveVertical(10);
        }
        if (e.getSource() == btnDown) {
            graphPanel.moveVertical(-10);
        }
        if (e.getSource() == btnCenter) {
            graphPanel.center();
        }

        //Display the bounds.
        this.setBounds();
    }

    public void mouseMoved(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            double x = (e.getX() - graphPanel.getyAxis()) * ((Math.abs(graphPanel.getMaxX(-1)) + Math.abs(graphPanel.getMinX(-1))) / this.getWidth());
            double y = -(e.getY() - graphPanel.getxAxis()) * ((Math.abs(graphPanel.getMaxY(-1)) + Math.abs(graphPanel.getMinY(-1))) / this.getHeight());

            x = Math.round(x * 100) / 100.0;
            y = Math.round(y * 100) / 100.0;

            graphPanel.setToolTipText("x:" + x + "\n, y:" + y);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getSource() == graphPanel) {
            //If zoom in
            if (e.getWheelRotation() < 0) {
                graphPanel.zoom(-20);
            } //If zoom out
            else {
                graphPanel.zoom(25);
            }

            this.setBounds();
        }
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void focusGained(FocusEvent e) {
        if (e.getSource() == txtMaxX) {
            txtMaxX.selectAll();
        }
        if (e.getSource() == txtMinX) {
            txtMinX.selectAll();
        }
        if (e.getSource() == txtMaxY) {
            txtMaxY.selectAll();
        }
        if (e.getSource() == txtMinY) {
            txtMinY.selectAll();
        }
    }

    public void focusLost(FocusEvent e) {
        try {
            if (e.getSource() == txtMaxX) {
                graphPanel.setMaxX(Double.valueOf(txtMaxX.getText()));
            }
            if (e.getSource() == txtMinX) {
                graphPanel.setMinX(Double.valueOf(txtMinX.getText()));
            }
            if (e.getSource() == txtMaxY) {
                graphPanel.setMaxY(Double.valueOf(txtMaxY.getText()));
            }
            if (e.getSource() == txtMinY) {
                graphPanel.setMinY(Double.valueOf(txtMinY.getText()));
            }
        } catch (InvalidBoundsException ibe) {
            JOptionPane.showMessageDialog(this, ibe.getMessage());
        }
    }
}
