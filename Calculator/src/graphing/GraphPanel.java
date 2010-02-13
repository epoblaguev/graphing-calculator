/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import exceptions.InvalidBoundsException;
import expressions.Expression;
import expressions.MathEvaluator;
import expressions.Variable;
import expressions.VariableList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Egor
 */
public class GraphPanel extends JPanel {

    private int id = 0;
    private double minX = -30;
    private double maxX = 30;
    private double minY = -30;
    private double maxY = 30;
    private int xAxis = 0;
    private int yAxis = 0;
    private int xAnchor = 0;
    private int yAnchor = 0;
    private Vector<Equation> equations = new Vector<Equation>();

    public GraphPanel() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setBackground(Color.lightGray);

        MediaTracker tracker = new MediaTracker(this);
        try {
            tracker.waitForID(id);
        } catch (InterruptedException ie) {
            // BOO! LOADING ERROR
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        DecimalFormat df = new DecimalFormat("#.##");
        g.setColor(Color.black);

        yAxis = UnitToPixelX(0);
        xAxis = UnitToPixelY(0);

        //Write Numbers
        g.drawString("0", yAxis + 2, xAxis - 1);
        g.drawString(df.format(minX), 5, xAxis - 1);
        g.drawString(df.format(maxX), this.getWidth() - 35, xAxis - 1);
        g.drawString(df.format(minY), yAxis + 2, this.getHeight() - 5);
        g.drawString(df.format(maxY), yAxis + 2, 15);

        //Draw crossheir
        g.drawLine(this.getWidth() / 2 - 5, this.getHeight() / 2, this.getWidth() / 2 + 5, this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, this.getHeight() / 2 - 5, this.getWidth() / 2, this.getHeight() / 2 + 5);

        //Draw x axis
        g.drawLine(0, xAxis, this.getWidth(), xAxis);

        //Draw y axis
        g.drawLine(yAxis, 0, yAxis, this.getHeight());

        if (equations.isEmpty()) {
            return;
        }

        //Loop through each string.
        for (Equation eq : equations) {
            xAnchor = UnitToPixelX(minX);
            yAnchor = UnitToPixelY(evaluate(eq.getExpression(), minX));
            g.setColor(eq.getColor());
            for (double x = minX; x <= maxX; x += (maxX - minX) / this.getWidth()) {

                int toX = UnitToPixelX(x);
                int toY = UnitToPixelY(evaluate(eq.getExpression(), x));

                g.drawLine(xAnchor, yAnchor, toX, toY);

                xAnchor = toX;
                yAnchor = toY;
            }
        }
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) throws InvalidBoundsException {
        if (maxX <= this.minX) {
            throw new InvalidBoundsException("Max X must be greater then Min X");
        } else {
            this.maxX = maxX;
        }
        this.repaint();
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) throws InvalidBoundsException {
        if (maxY <= this.minY) {
            throw new InvalidBoundsException("Max Y must be greater than Min Y");
        } else {
            this.maxY = maxY;
        }
        this.repaint();
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) throws InvalidBoundsException {
        if (minX >= this.maxX) {
            throw new InvalidBoundsException("Max X must be greater then Min X");
        } else {
            this.minX = minX;
        }
        this.repaint();
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) throws InvalidBoundsException {
        if (minY >= this.maxY) {
            throw new InvalidBoundsException("Max Y must be greater then Min Y");
        } else {
            this.minY = minY;
        }
        this.repaint();
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }

    /**
     * Converts specified x value to it's pixel location.
     * @param x - the value of x for which to find the pixel location on the graph.
     * @return - the pixel value.
     */
    public int UnitToPixelX(double x) {
        double pixelsPerUnit = this.getWidth() / (maxX - minX);
        double pos = (x - minX) * pixelsPerUnit;
        return (int) pos;
    }

    /**
     * Converts specified y value to it's pixel location.
     * @param y - the value of y for which to find the pixel location on the graph.
     * @return - the pixel value.
     */
    public int UnitToPixelY(double y) {
        double pixelsPerUnit = this.getHeight() / (maxY - minY);
        double pos = (y - minY) * pixelsPerUnit;
        pos = -pos + this.getHeight();
        return (int) pos;
    }

    /**
     * Converts a horizontal pixel location to it's x value
     * @param pix - pixel location to convert.
     * @return - x value of pixel location.
     */
    public double PixelToUnitX(int pix){
        double unitsPerPixel = (maxY-minY)/this.getWidth();
        double x = (pix*unitsPerPixel) + minX;
        return x;
    }

    /**
     * Converts a vertical pixel location to it's y value.
     * @param pix - pixel location to convert.
     * @return - y value of pixel location.
     */
    public double PixelToUnitY(int pix){
        double unitsPerPixel = (maxY-minY)/this.getHeight();
        double y = ((this.getHeight() - pix)*unitsPerPixel) + minY;
        return y;
    }

    void drawGraph(Vector<Equation> eq) {
        this.equations = new Vector<Equation>();
        for (Equation e : eq) {
            Expression expr = new Expression(e.getExpression());
            this.equations.add(new Equation(expr.getExpression(), e.getColor()));
        }
        this.repaint();
    }

    void drawGrid() {
        this.equations = new Vector<Equation>();
        this.repaint();
    }

    private double evaluate(String expression, double x) {
        MathEvaluator m = new MathEvaluator(expression);

        for (Variable var : VariableList.getVariables()) {
            m.addVariable(var.getVariableName(), var.getVariableValue());
        }

        m.addVariable("x", x);

        return m.getValue();
    }

    public void zoom(double percent) {

        double xCenter = (this.maxX + this.minX) / 2;
        double yCenter = (this.maxY + this.minY) / 2;
        double mult = percent / 100;

        double xSpan = this.maxX - this.minX;
        double ySpan = this.maxY - this.minY;

        this.minX = xCenter - (xSpan + xSpan * mult) / 2;
        this.maxX = xCenter + (xSpan + xSpan * mult) / 2;
        this.minY = yCenter - (ySpan + ySpan * mult) / 2;
        this.maxY = yCenter + (ySpan + ySpan * mult) / 2;

        this.repaint();
    }

    public void moveHorizontal(double percent) {
        double move = (this.maxX - this.minX) * (percent / 100);
        this.minX += move;
        this.maxX += move;
        this.repaint();
    }

    public void moveVertical(double percent) {
        double move = (this.maxY - this.minY) * (percent / 100);
        this.minY += move;
        this.maxY += move;
        this.repaint();
    }

    public void center() {
        double x = maxX - minX;
        double y = maxY - minY;

        minX = -(x / 2);
        maxX = x / 2;
        minY = -(y / 2);
        maxY = y / 2;

        this.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
