/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import exceptions.InvalidBoundsException;
import expressions.Expression;
import graphing.EquationInput;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
    private JPanel eastPanel, southPanel, directionPanel, boundsPanel, equationPanel;
    private JLabel lblMinX, lblMaxX, lblMinY, lblMaxY;
    private JTextField txtMinX, txtMaxX, txtMinY, txtMaxY;
    private JButton btnGraph, btnLeft, btnRight, btnUp, btnDown, btnCenter;

    public GraphingTab() {
        super();
        this.setLayout(new BorderLayout());

        //Initialize panels
        graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        southPanel = new JPanel();
        directionPanel = new JPanel(new BorderLayout());
        directionPanel.setSize(120, 80);
        directionPanel.setMaximumSize(directionPanel.getSize());
        boundsPanel = new JPanel(new GridLayout(0, 2));
        boundsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boundsPanel.setSize(120, 80);
        boundsPanel.setMaximumSize(boundsPanel.getSize());
        equationPanel = new JPanel();
        equationPanel.setLayout(new BoxLayout(equationPanel, BoxLayout.Y_AXIS));

        //Initialize directionPanel items
        btnLeft = new JButton("<");
        btnRight = new JButton(">");
        btnUp = new JButton("^");
        btnDown = new JButton("\\/");
        btnCenter = new JButton();

        //Initialize southPanel items.
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

        //Add to equationWrapper panel.
        equationPanel.add(new EquationInput("y1:", Color.RED));
        equationPanel.add(new EquationInput("y2:", Color.BLUE));
        equationPanel.add(new EquationInput("y3:", Color.YELLOW));

        //Add to southPanel
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        southPanel.add(equationPanel);
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

        equationPanel.setBackground(Color.red);

        graphPanel.repaint();

        this.setBounds();
    }

    private void setBounds() {
        DecimalFormat df = new DecimalFormat("#.####");
        this.txtMaxX.setText(df.format(graphPanel.getMaxX()));
        this.txtMinX.setText(df.format(graphPanel.getMinX()));
        this.txtMaxY.setText(df.format(graphPanel.getMaxY()));
        this.txtMinY.setText(df.format(graphPanel.getMinY()));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGraph) {
            try {
                Vector<Equation> eq = new Vector<Equation>();
                for(Component cmp : equationPanel.getComponents()){
                    if(cmp.getClass() == EquationInput.class){
                        EquationInput ei = (EquationInput)cmp;
                        if(!ei.getInput().getText().isEmpty())
                            eq.add(new Equation(ei.getInput().getText(), ei.getColor()));
                    }
                }
                graphPanel.drawGraph(eq);
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
            DecimalFormat df = new DecimalFormat("#.##");
            double x = (e.getX() - graphPanel.getyAxis()) * ((Math.abs(graphPanel.getMaxX()) + Math.abs(graphPanel.getMinX())) / graphPanel.getWidth());
            double y = -(e.getY() - graphPanel.getxAxis()) * ((Math.abs(graphPanel.getMaxY()) + Math.abs(graphPanel.getMinY())) / graphPanel.getHeight());

            graphPanel.setToolTipText("x:" + df.format(x) + "\n, y:" + df.format(y));
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
                Expression expr = new Expression(txtMaxX.getText());
                graphPanel.setMaxX(expr.evaluate());
            }
            if (e.getSource() == txtMinX) {
                Expression expr = new Expression(txtMinX.getText());
                graphPanel.setMinX(expr.evaluate());
            }
            if (e.getSource() == txtMaxY) {
                Expression expr = new Expression(txtMaxY.getText());
                graphPanel.setMaxY(expr.evaluate());
            }
            if (e.getSource() == txtMinY) {
                Expression expr = new Expression(txtMinY.getText());
                graphPanel.setMinY(expr.evaluate());
            }
        } catch (NumberFormatException nfe){
            JOptionPane.showMessageDialog(this, nfe.getMessage());
        } catch (InvalidBoundsException ibe) {
            JOptionPane.showMessageDialog(this, ibe.getMessage());
        }
    }
}
