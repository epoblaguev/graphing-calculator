/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Settings.GenSettings;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author Egor
 */
public class GraphingTab extends JPanel implements ActionListener, MouseWheelListener, MouseMotionListener, MouseListener, FocusListener, KeyListener {

    private int equationCount = 3;
    private int xPrev = 0, yPrev = 0;
    private double xClicked = 0, yClicked = 0;
    private GraphPanel graphPanel;
    private JPanel eastPanel, southPanel, directionPanel, boundsPanel, equationPanel, buttonPanel, cursorCordPanel;
    private JLabel lblMinX, lblMaxX, lblMinY, lblMaxY, lblCursorX, lblCursorY;
    private JTextField txtMinX, txtMaxX, txtMinY, txtMaxY;
    private JButton btnGraph, btnLeft, btnRight, btnUp, btnDown, btnCenter, btnAddEquation, btnRemoveEquation, btnZoomIn, btnZoomOut;
    private JScrollPane equationScrollPane;
    private JPopupMenu mnuGraphRightClick;
    private JMenu mnuDrawLine, mnuPoints;
    private JMenuItem miZoomIn, miZoomOut, miAddPoint, miRemovePoint, miDrawLineBetweenPoints, miDrawTangentLine, miAddMinPoint, miAddMaxPoint, miRemoveAllPoints, miClearGraph;

    public GraphingTab() {
        super();

        this.setLayout(new BorderLayout());

        this.createLayout();
        this.createRightClickMenu();

        graphPanel.drawGrid();
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(southPanel, BorderLayout.SOUTH);

        graphPanel.repaint();
        this.setBounds();
    }

    private void createLayout() {
        //Initialize panels
        graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setPreferredSize(new Dimension(140, 100));
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        directionPanel = new JPanel(new GridLayout(3, 3));
        directionPanel.setMaximumSize(new Dimension(140, 100));
        boundsPanel = new JPanel(new GridLayout(0, 2));
        boundsPanel.setBorder(BorderFactory.createTitledBorder("Graph Bounds"));
        boundsPanel.setMaximumSize(new Dimension(140, 100));
        equationPanel = new JPanel();
        equationPanel.setLayout(new GridLayout(0, 1));
        equationScrollPane = new JScrollPane(equationPanel);
        equationScrollPane.setPreferredSize(new Dimension(360, 125));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        cursorCordPanel = new JPanel(new GridLayout(0, 1));
        cursorCordPanel.setMaximumSize(new Dimension(120, 40));

        //Initialize directionPanel items
        btnZoomIn = new JButton(GenSettings.getImageIcon("/images/zoomIn.png"));
        btnZoomOut = new JButton(GenSettings.getImageIcon("/images/zoomOut.png"));
        //btnLeft = new JButton("\u2190");
        btnLeft = new JButton(GenSettings.getImageIcon("/images/arrowLeft.png"));
        btnRight = new JButton(GenSettings.getImageIcon("/images/arrowRight.png"));
        btnUp = new JButton(GenSettings.getImageIcon("/images/arrowUp.png"));
        btnDown = new JButton(GenSettings.getImageIcon("/images/arrowDown.png"));
        btnCenter = new JButton(GenSettings.getImageIcon("/images/center.png"));

        //Initialize buttonPanel items.
        btnAddEquation = new JButton("Equation", GenSettings.getImageIcon("/images/addSmall.png"));
        btnRemoveEquation = new JButton("Equation", GenSettings.getImageIcon("/images/removeSmall.png"));
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
        directionPanel.add(btnZoomOut);
        directionPanel.add(btnUp);
        directionPanel.add(btnZoomIn);
        directionPanel.add(btnLeft);
        directionPanel.add(btnCenter);
        directionPanel.add(btnRight);
        directionPanel.add(new JPanel());
        directionPanel.add(btnDown);
        directionPanel.add(new JPanel());

        //Add to equationWrapper panel.
        equationPanel.add(new EquationInput("y1=", Color.RED));
        equationPanel.add(new EquationInput("y2=", Color.BLUE));
        equationPanel.add(new EquationInput("y3=", Color.YELLOW));
        for (int i = 0; i < equationPanel.getComponentCount(); i++) {
            ((EquationInput) equationPanel.getComponent(i)).getInput().addKeyListener(this);
        }

        //Add to buttonPanel
        buttonPanel.add(btnAddEquation);
        buttonPanel.add(btnRemoveEquation);
        buttonPanel.add(btnGraph);

        //Add to coordinatePanel
        lblCursorX = new JLabel("X: N/A");
        lblCursorY = new JLabel("Y: N/A");
        cursorCordPanel.add(lblCursorX);
        cursorCordPanel.add(lblCursorY);

        //Add to southPanel
        southPanel.add(equationScrollPane);
        southPanel.add(buttonPanel);

        //Add to eastPanel
        eastPanel.add(directionPanel);
        eastPanel.add(boundsPanel);
        eastPanel.add(cursorCordPanel);

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
        btnZoomIn.addActionListener(this);
        btnZoomOut.addActionListener(this);
        btnAddEquation.addActionListener(this);
        btnRemoveEquation.addActionListener(this);
        graphPanel.addMouseMotionListener(this);
        graphPanel.addMouseWheelListener(this);
        graphPanel.addMouseListener(this);
        txtMaxX.addFocusListener(this);
        txtMinX.addFocusListener(this);
        txtMaxY.addFocusListener(this);
        txtMinY.addFocusListener(this);
    }

    private void createRightClickMenu() {
        mnuGraphRightClick = new JPopupMenu();
        miAddPoint = new JMenuItem("Plot Free Point");
        miRemovePoint = new JMenuItem("Remove Point");
        miZoomIn = new JMenuItem("Zoom In");
        miZoomOut = new JMenuItem("Zoom Out");
        miDrawLineBetweenPoints = new JMenuItem("Between Points");
        miDrawTangentLine = new JMenuItem("Tangent to Equation");
        miAddMinPoint = new JMenuItem("Plot Min Point");
        miAddMaxPoint = new JMenuItem("Plot Max Point");
        miRemoveAllPoints = new JMenuItem("Remove All Points.");
        miClearGraph = new JMenuItem("Clear");

        mnuDrawLine = new JMenu("Draw Line");
        mnuPoints = new JMenu("Plot Points");

        mnuGraphRightClick.add(miZoomIn);
        mnuGraphRightClick.add(miZoomOut);
        mnuGraphRightClick.addSeparator();
        mnuGraphRightClick.add(mnuPoints);
        mnuGraphRightClick.add(mnuDrawLine);
        mnuGraphRightClick.addSeparator();
        mnuGraphRightClick.add(miClearGraph);

        mnuDrawLine.add(miDrawLineBetweenPoints);
        mnuDrawLine.add(miDrawTangentLine);

        mnuPoints.add(miAddPoint);
        mnuPoints.addSeparator();
        mnuPoints.add(miAddMaxPoint);
        mnuPoints.add(miAddMinPoint);
        mnuPoints.addSeparator();
        mnuPoints.add(miRemovePoint);
        mnuPoints.add(miRemoveAllPoints);

        //Add listeners
        miAddPoint.addActionListener(this);
        miRemovePoint.addActionListener(this);
        miRemoveAllPoints.addActionListener(this);
        miZoomIn.addActionListener(this);
        miZoomOut.addActionListener(this);
        miDrawLineBetweenPoints.addActionListener(this);
        miDrawTangentLine.addActionListener(this);
        miAddMinPoint.addActionListener(this);
        miAddMaxPoint.addActionListener(this);
        miClearGraph.addActionListener(this);
    }

    public JButton getBtnAddEquation() {
        return btnAddEquation;
    }

    public JButton getBtnGraph() {
        return btnGraph;
    }

    public int getEquationCount() {
        return equationCount;
    }

    public JPanel getEquationPanel() {
        return equationPanel;
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    private void setBounds() {
        DecimalFormat df = new DecimalFormat("#.####");
        this.txtMaxX.setText(df.format(graphPanel.getMaxX()));
        this.txtMinX.setText(df.format(graphPanel.getMinX()));
        this.txtMaxY.setText(df.format(graphPanel.getMaxY()));
        this.txtMinY.setText(df.format(graphPanel.getMinY()));
    }

    @Override
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
                JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        if (e.getSource() == btnZoomIn) {
            graphPanel.zoom(-20);
        }
        if (e.getSource() == btnZoomOut) {
            graphPanel.zoom(25);
        }
        if (e.getSource() == btnAddEquation) {
            Random r = new Random();
            equationCount++;
            equationPanel.add(new EquationInput("y" + equationCount + "=",
                    new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
            equationScrollPane.validate();
            ((EquationInput) equationPanel.getComponent(equationCount - 1)).getInput().addKeyListener(this);
        }
        if (e.getSource() == btnRemoveEquation) {
            if (equationCount > 1) {
                equationPanel.remove(equationPanel.getComponentCount() - 1);
                equationCount--;
                equationScrollPane.validate();
            } else {
                JOptionPane.showMessageDialog(this, "Can't remove last equation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == miAddPoint) {
            AddPointDialog addPoint = new AddPointDialog(this, this.xClicked, this.yClicked);
            addPoint.setLocationRelativeTo(this);
            addPoint.setVisible(true);
        }
        if (e.getSource() == miRemovePoint) {
            String remove = JOptionPane.showInputDialog(this, "Name of point to remove:");
            GraphPanel.removePoint(remove);
            this.repaint();
        }
        if (e.getSource() == miRemoveAllPoints) {
            GraphPanel.getPoints().clear();
        }
        if (e.getSource() == miZoomIn) {
            graphPanel.zoom(-50);
        }
        if (e.getSource() == miZoomOut) {
            graphPanel.zoom(100);
        }
        if (e.getSource() == miDrawLineBetweenPoints) {
            if (GraphPanel.getPoints().size() < 2) {
                JOptionPane.showMessageDialog(this, "Less then 2 points are ploted on graph.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DrawLineBetweenPointsDialog dld = new DrawLineBetweenPointsDialog(this);
            dld.setLocationRelativeTo(this);
            dld.setVisible(true);
        }
        if (e.getSource() == miDrawTangentLine || e.getSource() == miAddMinPoint || e.getSource() == miAddMaxPoint) {
            boolean allEmpty = true;
            for (Component eq : equationPanel.getComponents()) {
                if (((EquationInput) eq).getInput().getText().isEmpty() == false) {
                    allEmpty = false;
                    break;
                }
            }
            if (!allEmpty) {
                if (e.getSource() == miDrawTangentLine) {
                    DrawTangentLineDialog dtld = new DrawTangentLineDialog(this);
                    dtld.setLocationRelativeTo(this);
                    dtld.setVisible(true);
                } else if (e.getSource() == miAddMinPoint) {
                    AddMinPointDialog ampd = new AddMinPointDialog(this, this.xClicked, graphPanel.getMaxX() - graphPanel.getMinX());
                    ampd.setLocationRelativeTo(this);
                    ampd.setVisible(true);
                } else if (e.getSource() == miAddMaxPoint) {
                    AddMaxPointDialog ampd = new AddMaxPointDialog(this, this.xClicked, graphPanel.getMaxX() - graphPanel.getMinX());
                    ampd.setLocationRelativeTo(this);
                    ampd.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All equation inputs are empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == miClearGraph) {
            for(Component eq : equationPanel.getComponents()){
                ((EquationInput)eq).getInput().setText("");
                btnGraph.doClick();
            }

            this.repaint();
        }

        //Display the bounds.
        this.setBounds();
    }

    @Override
    public GraphingTab clone() {
        return this.clone();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            DecimalFormat df = new DecimalFormat("#.#####");
            double x = graphPanel.PixelToUnitX(e.getX());
            double y = graphPanel.PixelToUnitY(e.getY());

            lblCursorX.setText("X: " + df.format(x));
            lblCursorY.setText("Y: " + df.format(y));
        }
    }

    @Override
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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            double xMoved = e.getX();
            double yMoved = e.getY();
            double percentX = (xMoved - xPrev) / (graphPanel.getWidth());
            double percentY = (yMoved - yPrev) / (graphPanel.getHeight());
            graphPanel.moveHorizontal(-percentX * 100);
            graphPanel.moveVertical(+percentY * 100);
            this.xPrev = (int) xMoved;
            this.yPrev = (int) yMoved;
            this.setBounds();
        }
    }

    @Override
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

    @Override
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
            JOptionPane.showMessageDialog(this, nfe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidBoundsException ibe) {
            JOptionPane.showMessageDialog(this, ibe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == graphPanel && (e.isPopupTrigger() || e.getModifiers() == InputEvent.BUTTON3_MASK)) {
            this.xClicked = graphPanel.PixelToUnitX(e.getX());
            this.yClicked = graphPanel.PixelToUnitY(e.getY());
            mnuGraphRightClick.show(graphPanel, e.getX() + 5, e.getY() + 3);
        }

        if (e.getSource() == graphPanel) {
            this.xPrev = e.getX();
            this.yPrev = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == graphPanel) {
            lblCursorX.setText("X: N/A");
            lblCursorY.setText("Y: N/A");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().getClass() == JTextField.class) {
            if (e.getKeyCode() == 10) {
                btnGraph.doClick();
            }
        }
    }
}
