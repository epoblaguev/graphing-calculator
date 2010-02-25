/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Settings.GraphSettings;
import exceptions.InvalidBoundsException;
import expressions.Expression;
import expressions.MathEvaluator;
import expressions.Variable;
import expressions.VariableList;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author Egor
 */
public class GraphPanel extends JPanel implements Runnable {

    private double minX = -10;
    private double maxX = 10;
    private double minY = -10;
    private double maxY = 10;
    private int xAxis = 0;
    private int yAxis = 0;
    private Vector<Equation> equations = new Vector<Equation>();
    private static HashMap<String, Point2D.Double> points = new HashMap<String, Point2D.Double>();

    public GraphPanel() {
        this.setBackground(GraphSettings.getBgColor());
        GraphPanel.points.put("A", new Point2D.Double(5.3, 4.2));
    }

    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(GraphSettings.getBgColor());

        GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, this.getWidth());
        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g2);

        DecimalFormat df = new DecimalFormat("#.###");
        g2.setColor(Color.black);

        yAxis = UnitToPixelX(0);
        xAxis = UnitToPixelY(0);

        //Draw crossheir
        g2.drawLine(this.getWidth() / 2 - 5, this.getHeight() / 2, this.getWidth() / 2 + 5, this.getHeight() / 2);
        g2.drawLine(this.getWidth() / 2, this.getHeight() / 2 - 5, this.getWidth() / 2, this.getHeight() / 2 + 5);

        //Draw x and y axis
        g2.drawLine(0, xAxis, this.getWidth(), xAxis);
        g2.drawLine(yAxis, 0, yAxis, this.getHeight());

        //Set antialiasing
        if (GraphSettings.isAntialiased()) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        //Write Numbers
        g2.drawString("0", yAxis + 2, xAxis - 1);
        g2.drawString(df.format(minX), 5, xAxis - 1);
        g2.drawString(df.format(maxX), this.getWidth() - 35, xAxis - 1);
        g2.drawString(df.format(minY), yAxis + 2, this.getHeight() - 5);
        g2.drawString(df.format(maxY), yAxis + 2, 15);

        g2.setStroke(new BasicStroke(GraphSettings.getLineWidth()));

        //Loop through each string.
        for (Equation eq : equations) {
            String expr = eq.getExpression();
            polyline.moveTo(UnitToPixelX(minX), UnitToPixelY(evaluate(expr, minX)));
            g2.setColor(eq.getColor());
            for (double x = minX; x <= maxX; x += (maxX - minX) / this.getWidth()) {
                polyline.lineTo(UnitToPixelX(x), UnitToPixelY(evaluate(expr, x)));
            }
            g2.draw(polyline);
            polyline.reset();
        }

        //Add points
        g2.setColor(Color.BLACK);
        for (String key : points.keySet()) {
            Point2D.Double pt = points.get(key);
            int x = UnitToPixelX(pt.getX());
            int y = UnitToPixelY(pt.getY());
            g2.fillOval(x - 2, y - 2, 5, 5);
            g2.drawString(key+"(" + df.format(pt.getX()) + "," + df.format(pt.getY()) + ")", x + 5, y);
        }

        g2.dispose();
    }

    public static void addPoint(String name, double x, double y) {
        Point2D.Double pt = new Point2D.Double(x, y);
        points.put(name, pt);
    }

    public static void removePoint(String name){
        points.remove(name);
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
        (new Thread(this)).start();
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
        (new Thread(this)).start();
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
        (new Thread(this)).start();
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
        (new Thread(this)).start();
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
    public double PixelToUnitX(int pix) {
        double unitsPerPixel = (maxY - minY) / this.getWidth();
        double x = (pix * unitsPerPixel) + minX;
        return x;
    }

    /**
     * Converts a vertical pixel location to it's y value.
     * @param pix - pixel location to convert.
     * @return - y value of pixel location.
     */
    public double PixelToUnitY(int pix) {
        double unitsPerPixel = (maxY - minY) / this.getHeight();
        double y = ((this.getHeight() - pix) * unitsPerPixel) + minY;
        return y;
    }

    void drawGraph(Vector<Equation> eq) {
        this.equations = new Vector<Equation>();
        for (Equation e : eq) {
            Expression expr = new Expression(e.getExpression());
            this.equations.add(new Equation(expr.getExpression(), e.getColor()));
        }
        (new Thread(this)).start();
    }

    void drawGrid() {
        this.equations = new Vector<Equation>();
        (new Thread(this)).start();
    }

    public double evaluate(String expression, double x) {
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

        (new Thread(this)).start();
    }

    public void moveHorizontal(double percent) {
        double move = (this.maxX - this.minX) * (percent / 100);
        this.minX += move;
        this.maxX += move;
        (new Thread(this)).start();
    }

    public void moveVertical(double percent) {
        double move = (this.maxY - this.minY) * (percent / 100);
        this.minY += move;
        this.maxY += move;
        (new Thread(this)).start();
    }

    public void center() {
        double x = maxX - minX;
        double y = maxY - minY;

        minX = -(x / 2);
        maxX = x / 2;
        minY = -(y / 2);
        maxY = y / 2;

        (new Thread(this)).start();
    }

    public void run() {
        repaint();
    }
}
