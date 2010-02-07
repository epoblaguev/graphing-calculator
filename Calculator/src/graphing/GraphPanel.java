/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import exceptions.InvalidBoundsException;
import expressions.MathEvaluator;
import expressions.Variable;
import expressions.VariableList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
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
    private String expression = "";

    public GraphPanel() {
        Toolkit tk = Toolkit.getDefaultToolkit();

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
        g.setColor(Color.black);

        yAxis = xPosition(0);
        xAxis = yPosition(0);

        //Write Numbers
        g.drawString("0", yAxis + 2, xAxis - 1);
        g.drawString(String.valueOf(Math.round(minX * 100) / 100.0), 5, xAxis - 1);
        g.drawString(String.valueOf(Math.round(maxX * 100) / 100.0), this.getWidth() - 35, xAxis - 1);
        g.drawString(String.valueOf(Math.round(minY * 100) / 100.0), yAxis + 2, this.getHeight() - 5);
        g.drawString(String.valueOf(Math.round(maxY * 100) / 100.0), yAxis + 2, 15);

        //Draw crossheir
        g.drawLine(this.getWidth() / 2 - 5, this.getHeight() / 2, this.getWidth() / 2 + 5, this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, this.getHeight() / 2 - 5, this.getWidth() / 2, this.getHeight() / 2 + 5);

        //Draw x axis
        g.drawLine(0, xAxis, this.getWidth(), xAxis);

        //Draw y axis
        g.drawLine(yAxis, 0, yAxis, this.getHeight());


        g.setColor(Color.red);

        if (expression.isEmpty()) {
            return;
        }

        xAnchor = -100;
        yAnchor = this.getHeight() / 2;

        for (double x = minX; x <= maxX; x += (maxX - minX) / this.getWidth()) {

            int toX = xPosition(x);
            int toY = yPosition(evaluate(expression, x));

            g.drawLine(xAnchor, yAnchor, toX, toY);

            xAnchor = toX;
            yAnchor = toY;
        }
    }

    public double getMaxX(int sigFig) {
        double ans = maxX;
        if(sigFig<0){
            return ans;
        }
        else{
            return Math.round(ans * Math.pow(10, sigFig)) / Math.pow(10, sigFig);
        }
    }

    public void setMaxX(double maxX) throws InvalidBoundsException {
        if(maxX < this.minX)
            throw new InvalidBoundsException("Max X must be greater then Min X");
        else
            this.maxX = maxX;
        this.repaint();
    }

    public double getMaxY(int sigFig) {
        double ans = maxY;
        if(sigFig<0){
            return ans;
        }
        else{
            return Math.round(ans * Math.pow(10, sigFig)) / Math.pow(10, sigFig);
        }
    }

    public void setMaxY(double maxY) throws InvalidBoundsException {
        if(maxY < this.minY)
            throw new InvalidBoundsException("Max Y must be greater than Min Y");
        else
            this.maxY = maxY;
        this.repaint();
    }

    public double getMinX(int sigFig) {
        double ans = minX;
        if(sigFig<0){
            return ans;
        }
        else{
            return Math.round(ans * Math.pow(10, sigFig)) / Math.pow(10, sigFig);
        }
    }

    public void setMinX(double minX) throws InvalidBoundsException {
        if(minX > this.maxX)
            throw new InvalidBoundsException("Max X must be greater then Min X");
        else
            this.minX = minX;
        this.repaint();
    }

    public double getMinY(int sigFig) {
        double ans = minY;
        if(sigFig<0){
            return ans;
        }
        else{
            return Math.round(ans * Math.pow(10, sigFig)) / Math.pow(10, sigFig);
        }
    }

    public void setMinY(double minY) throws InvalidBoundsException {
        if(minY > this.maxY)
            throw new InvalidBoundsException("Max Y must be greater then Min Y");
        else
            this.minY = minY;
        this.repaint();
    }

    public int getxAxis() {
        return xAxis;
    }

    public int getyAxis() {
        return yAxis;
    }
    

    private int xPosition(double x) {
        double pixelsPerUnit = this.getWidth() / (maxX - minX);
        double pos = (x - minX) * pixelsPerUnit;
        return (int) pos;
    }

    private int yPosition(double y) {
        double pixelsPerUnit = this.getHeight() / (maxY - minY);
        double pos = (y - minY) * pixelsPerUnit;
        pos = -pos + this.getHeight();
        return (int) pos;
    }

    void drawGraph(String expression) {
        this.expression = expression;
        this.repaint();
    }

    void drawGrid() {
        this.expression = "";
        this.repaint();
    }

    private double evaluate(String expression, double x) {
        String expr;
        for (int i = 0; i <= 9; i++) {
            expression = expression.replace(i + "(", i + "*(");
        }
        expression = expression.replace(")(", ")*(");
        expr = expression.replace(" ", "");

        MathEvaluator m = new MathEvaluator(expr);
        m.setUsingRadians(true);

        for (Variable var : VariableList.getVariableList()) {
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

//        this.minX += this.minX * mult;
//        this.maxX += this.maxX * mult;
//        this.minY += this.minY * mult;
//        this.maxY += this.maxY * mult;
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
