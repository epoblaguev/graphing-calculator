/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphing;

import expressions.MathEvaluator;
import expressions.Variable;
import expressions.VariableList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

/**
 *
 * @author Egor
 */
public class GraphPanel extends JPanel implements MouseMotionListener, MouseWheelListener{
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
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        

        MediaTracker tracker = new MediaTracker(this);
        try {
            tracker.waitForID(id);
        } catch (InterruptedException ie) {
            // BOO! LOADING ERROR
        }

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);

        yAxis = (int) ((this.getWidth()) * (Math.abs(minX) / (Math.abs(maxX) + Math.abs(minX))));
        xAxis = (int) ((this.getHeight()) * (Math.abs(maxY)/(Math.abs(maxY) + Math.abs(minY))));

        if(minX > 0 || maxX < 0){
            yAxis = -50;
        }
        if(minY > 0 || maxY < 0){
            xAxis = -50;
        }
        
        //Write Numbers
        g.drawString("0", yAxis+2, xAxis-1);
        g.drawString(String.valueOf(Math.round(minX* 100)/100.0), 0, xAxis-1);
        g.drawString(String.valueOf(Math.round(maxX * 100)/100.0), this.getWidth()-30, xAxis-1);
        g.drawString(String.valueOf(Math.round(minY* 100)/100.0), yAxis+2, this.getHeight()-1);
        g.drawString(String.valueOf(Math.round(maxY* 100)/100.0), yAxis+2, 10);

        //Draw x axis
        g.drawLine(0, xAxis, this.getWidth(), xAxis);

        //Draw y axis
        g.drawLine(yAxis, 0, yAxis, this.getHeight());

        
        g.setColor(Color.red);

        if(expression.isEmpty()){
            return;
        }

        xAnchor = (int) ((minX / ((Math.abs(maxX) + Math.abs(minX))/this.getWidth())) + yAxis) - 100;
        yAnchor = (int) -((minY / ((Math.abs(maxY) + Math.abs(minY))/this.getHeight())) + xAxis) - 100;

        for(double x = minX; x<=maxX; x += (Math.abs(maxX) + Math.abs(minX))/this.getWidth()){
            int toX = (int) (x / ((Math.abs(maxX) + Math.abs(minX))/this.getWidth())) + yAxis;
            int toY = (int) -((evaluate(expression, x) / ((Math.abs(maxY) + Math.abs(minY))/this.getHeight())) + xAxis);

            g.drawLine(xAnchor, yAnchor, toX, toY + this.getHeight());
            
            xAnchor = toX;
            yAnchor = toY + this.getHeight();
        }
    }

    void drawGraph(String expression){
        this.expression = expression;
        this.repaint();
    }

    void drawGrid() {
        this.expression = "";
        this.repaint();
    }

    private double evaluate(String expression, double x){
        String expr;
        for(int i = 0; i <= 9; i++){
            expression = expression.replace(i+"(", i+"*(");
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

    public void zoom(double percent){
        double mult = percent/100;
        this.minX += this.minX * mult;
        this.maxX += this.maxX * mult;
        this.minY += this.minY * mult;
        this.maxY += this.maxY * mult;
        this.repaint();
    }

    public void moveHorizontal(double percent){
        double move = (this.maxX - this.minX)*(percent/100);
        this.minX += move;
        this.maxX += move;
        this.repaint();
    }

    public void moveVertical(double percent){
        double move = (this.maxY - this.minY)*(percent/100);
        this.minY += move;
        this.maxY += move;
        this.repaint();
    }

    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent e) {
        double x = (e.getX() - yAxis) * ((Math.abs(maxX) + Math.abs(minX))/this.getWidth());
        double y = -(e.getY() - xAxis) * ((Math.abs(maxY) + Math.abs(minY))/this.getHeight());

        x = Math.round(x*100)/100.0;
        y = Math.round(y*100)/100.0;
        
        this.setToolTipText("x:" + x + "\n, y:" + y);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        //If zoom in
        if(e.getWheelRotation() < 0){
            zoom(-10);
        }
        //If zoom out
        else{
            zoom(100/9);
        }
    }
}
