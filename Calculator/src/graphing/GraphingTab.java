/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import exceptions.InvalidBoundsException;
import expressions.Expression;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Egor
 */
public class GraphingTab extends JPanel implements ActionListener, MouseWheelListener, MouseMotionListener, MouseListener, FocusListener {

    private int equationCount = 3;
    private int xPrev = 0, yPrev = 0;
    private GraphPanel graphPanel;
    private JPanel eastPanel, southPanel, directionPanel, boundsPanel, equationPanel, buttonPanel, coordinatePanel;
    private JLabel lblMinX, lblMaxX, lblMinY, lblMaxY, lblXCoordinate, lblYCoordinate;
    private JTextField txtMinX, txtMaxX, txtMinY, txtMaxY;
    private JButton btnGraph, btnLeft, btnRight, btnUp, btnDown, btnCenter, btnAddEquation, btnRemoveEquation;
    private JScrollPane equationScrollPane;

    public GraphingTab() {
        super();
        this.setLayout(new BorderLayout());

        //Initialize panels
        graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        directionPanel = new JPanel(new BorderLayout());
        directionPanel.setMaximumSize(new Dimension(120, 80));
        boundsPanel = new JPanel(new GridLayout(0, 2));
        boundsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boundsPanel.setMaximumSize(new Dimension(120, 80));
        equationPanel = new JPanel();
        equationPanel.setLayout(new GridLayout(0, 1));
        equationScrollPane = new JScrollPane(equationPanel);
        equationScrollPane.setPreferredSize(new Dimension(330, 125));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        coordinatePanel = new JPanel(new GridLayout(0, 1));
        coordinatePanel.setMaximumSize(new Dimension(120, 40));

        //Initialize directionPanel items
        btnLeft = new JButton("<");
        btnRight = new JButton(">");
        btnUp = new JButton("^");
        btnDown = new JButton("\\/");
        btnCenter = new JButton();

        //Initialize buttonPanel items.
        btnAddEquation = new JButton("Equation +");
        btnRemoveEquation = new JButton("Equation  -");
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
        equationPanel.add(new EquationInput("y1=", Color.RED));
        equationPanel.add(new EquationInput("y2=", Color.BLUE));
        equationPanel.add(new EquationInput("y3=", Color.YELLOW));

        //Add to buttonPanel
        buttonPanel.add(btnAddEquation);
        buttonPanel.add(btnRemoveEquation);
        buttonPanel.add(btnGraph);

        //Add to coordinatePanel
        lblXCoordinate = new JLabel("X: N/A");
        lblYCoordinate = new JLabel("Y: N/A");
        coordinatePanel.add(lblXCoordinate);
        coordinatePanel.add(lblYCoordinate);

        //Add to southPanel
        southPanel.add(equationScrollPane);
        southPanel.add(buttonPanel);

        //Add to eastPanel
        eastPanel.add(directionPanel);
        eastPanel.add(boundsPanel);
        eastPanel.add(coordinatePanel);

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
        btnAddEquation.addActionListener(this);
        btnRemoveEquation.addActionListener(this);
        graphPanel.addMouseMotionListener(this);
        graphPanel.addMouseWheelListener(this);
        graphPanel.addMouseListener(this);
        txtMaxX.addFocusListener(this);
        txtMinX.addFocusListener(this);
        txtMaxY.addFocusListener(this);
        txtMinY.addFocusListener(this);


        graphPanel.drawGrid();
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

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
                for (Component cmp : equationPanel.getComponents()) {
                    if (cmp.getClass() == EquationInput.class) {
                        EquationInput ei = (EquationInput) cmp;
                        if (!ei.getInput().getText().isEmpty()) {
                            eq.add(new Equation(ei.getInput().getText(), ei.getColor()));
                        }
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
        if (e.getSource() == btnAddEquation) {
            Random r = new Random();
            equationCount++;
            equationPanel.add(new EquationInput("y" + equationCount + "=",
                    new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
            equationScrollPane.validate();
        }
        if (e.getSource() == btnRemoveEquation) {
            if (equationCount > 1) {
                equationPanel.remove(equationPanel.getComponentCount() - 1);
                equationCount--;
                equationScrollPane.validate();
            } else {
                JOptionPane.showMessageDialog(this, "Can't remove last equation.");
            }
        }

        //Display the bounds.
        this.setBounds();
    }

    public void mouseMoved(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            DecimalFormat df = new DecimalFormat("#.#####");
            double x = graphPanel.PixelToUnitX(e.getX());
            double y = graphPanel.PixelToUnitY(e.getY());

            lblXCoordinate.setText("X: " + df.format(x));
            lblYCoordinate.setText("Y: " + df.format(y));
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
        if (e.getSource() == graphPanel) {
            double xMoved = e.getX();
            double yMoved = e.getY();
            double percentX = (xMoved - xPrev)/(graphPanel.getWidth());
            double percentY = (yMoved - yPrev)/(graphPanel.getHeight());
            graphPanel.moveHorizontal(-percentX*100);
            graphPanel.moveVertical(+percentY*100);
            this.xPrev = (int) xMoved;
            this.yPrev = (int) yMoved;
            this.setBounds();
        }
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
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, nfe.getMessage());
        } catch (InvalidBoundsException ibe) {
            JOptionPane.showMessageDialog(this, ibe.getMessage());
        }
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked:" + e.getPoint().toString());
    }

    public void mousePressed(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            this.xPrev = e.getX();
            this.yPrev = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released:" + e.getPoint().toString());
    }

    public void mouseEntered(MouseEvent e) {
        //
    }

    public void mouseExited(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            lblXCoordinate.setText("X: N/A");
            lblYCoordinate.setText("Y: N/A");
        }
    }
}
