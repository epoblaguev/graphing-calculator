/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import Settings.GraphSettings;
import equations.Equation;
import equations.EquationPlotter;
import exceptions.InvalidBoundsException;
import helpers.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

/**
 * Class to represent the graph
 * 
 * @author Egor
 */
public class GraphPanel extends JPanel implements ComponentListener {

    private double minX = -10;
	private double maxX = 10;
	private double minY = -10;
	private double maxY = 10;
	private double xInterval;
	private double yInterval;
	private int xAxis = 0;
	private int yAxis = 0;
	private int panelHeight = 0;
	private int panelWidth = 0;
	private boolean firstResize = true;
	private Vector<Equation> equations = new Vector<>();
	private static final HashMap<String, Point2D.Double> points = new HashMap<>();
    private EquationPlotter[] equationPlotters = {};

    /**
	 * Constructor, sets the background
	 */
	public GraphPanel() {
		this.setBackground(GraphSettings.getBgColor());
		this.addComponentListener(this);
	}

	/**
	 * Paints the graph
	 */
	public void paintComponent(Graphics g) {
		this.setBackground(GraphSettings.getBgColor());

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g2);

		DecimalFormat df = new DecimalFormat(ConstValues.DF_3);

		yAxis = UnitToPixelX(0);
		xAxis = UnitToPixelY(0);

		// Draw Grid
		if (GraphSettings.isDrawGrid()) {
			g2.setColor(Color.GRAY);
			xInterval = Math.pow(10, String.valueOf((int) (maxX - minX) / 4).length() - 1);
			yInterval = Math.pow(10, String.valueOf((int) (maxY - minY) / 4).length() - 1);
			
			xInterval = yInterval = Math.min(xInterval, yInterval);

			for (double i = 0 + xInterval; i <= maxX; i += xInterval) {
				g2.drawLine(UnitToPixelX(i), 0, UnitToPixelX(i), this.getHeight());
			}
			for (double i = 0 - xInterval; i >= minX; i -= xInterval) {
				g2.drawLine(UnitToPixelX(i), 0, UnitToPixelX(i), this.getHeight());
			}
			for (double i = 0 + yInterval; i <= maxY; i += yInterval) {
				g2.drawLine(0, UnitToPixelY(i), this.getWidth(), UnitToPixelY(i));
			}
			for (double i = 0 - yInterval; i >= minY; i -= yInterval) {
				g2.drawLine(0, UnitToPixelY(i), this.getWidth(), UnitToPixelY(i));
			}
		}

		g2.setColor(Color.black);

		// Draw crosshair
		g2.drawLine(this.getWidth() / 2 - 5, this.getHeight() / 2, this.getWidth() / 2 + 5, this.getHeight() / 2);
		g2.drawLine(this.getWidth() / 2, this.getHeight() / 2 - 5, this.getWidth() / 2, this.getHeight() / 2 + 5);

		// Draw x and y axis
        g2.drawLine(0, xAxis, this.getWidth(), xAxis);
		g2.drawLine(yAxis, 0, yAxis, this.getHeight());

		// Set antialiasing
		if (GraphSettings.isAntialiased()) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Write Numbers
		g2.drawString("0", yAxis + 2, xAxis - 1);
		g2.drawString(df.format(minX), 5, xAxis - 1);
		g2.drawString(df.format(maxX), this.getWidth() - df.format(maxX).length() * 7, xAxis - 1);
		g2.drawString(df.format(minY), yAxis + 2, this.getHeight() - 5);
		g2.drawString(df.format(maxY), yAxis + 2, 15);

		g2.setStroke(new BasicStroke(GraphSettings.getLineWidth()));

		// Loop through each equation.
        for(EquationPlotter equationPlotter : equationPlotters){
            try {
                GeneralPath eqLine = new GeneralPath();
                boolean firstPoint = true;
                double x, y;
                Vector<double[]> xyCoordinates = equationPlotter.getXYCoordinates();
                //Convert coordinates vector to GeneralPath.
                for(double[] coordinate : xyCoordinates.toArray(new double[xyCoordinates.size()][])){
                    x = this.UnitToPixelX(coordinate[0]);
                    y = this.UnitToPixelY(coordinate[1]);
                    if(firstPoint){
                        eqLine.moveTo(x,y);
                        firstPoint = false;
                    } else {
                        eqLine.lineTo(x,y);
                    }
                }
                g2.setColor(equationPlotter.getColor());
                g2.draw(eqLine);
            } catch(Exception e){
                e.printStackTrace();
                System.out.println("Exception while graphing polyline: " + e.getMessage());
            }

        }

		// Add points
		g2.setColor(Color.BLACK);
		for (String key : points.keySet()) {
			Point2D.Double pt = points.get(key);
			int x = UnitToPixelX(pt.getX());
			int y = UnitToPixelY(pt.getY());
			g2.fillOval(x - 2, y - 2, 5, 5);
			g2.drawString(key + "(" + df.format(pt.getX()) + ","
					+ df.format(pt.getY()) + ")", x + 5, y);
		}
		g2.dispose();
	}

    /**
	 * Add a point to the graph
	 * 
	 * @param key
	 * @param x
	 * @param y
	 */
	public static void addPoint(String key, double x, double y) {
		Point2D.Double pt = new Point2D.Double(x, y);
		points.put(key, pt);
	}

	/**
	 * Remove point
	 * 
	 * @param key
	 */
	public static void removePoint(String key) {
		points.remove(key);
	}

	/**
	 * Get the points on the graph
	 * 
	 * @return
	 */
	public static HashMap<String, Point2D.Double> getPoints() {
		return points;
	}

	/**
	 * Gets a point with the specified key
	 * 
	 * @param key
	 * @return
	 */
	public static Point2D.Double getPoint(String key) {
		return points.get(key);
	}

	/**
	 * Get the maximum x value
	 * 
	 * @return
	 */
	public double getMaxX() {
		return maxX;
	}

	/**
	 * Set the maximum x
	 * 
	 * @param maxX
	 * @throws InvalidBoundsException
	 */
	public void setMaxX(double maxX) throws InvalidBoundsException {
		if (maxX <= this.minX) {
			throw new InvalidBoundsException("Max X must be greater then Min X");
		} else {
			this.maxX = maxX;
		}
		startDrawing();
	}

	/**
	 * Gets the maximum Y
	 * 
	 * @return
	 */
	public double getMaxY() {
		return maxY;
	}

	/**
	 * Set the maximum Y
	 * 
	 * @param maxY
	 * @throws InvalidBoundsException
	 */
	public void setMaxY(double maxY) throws InvalidBoundsException {
		if (maxY <= this.minY) {
			throw new InvalidBoundsException("Max Y must be greater than Min Y");
		} else {
			this.maxY = maxY;
		}
		startDrawing();
	}

	/**
	 * Get the minimum x
	 * 
	 * @return
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * Set the minimum x
	 * 
	 * @param minX
	 * @throws InvalidBoundsException
	 */
	public void setMinX(double minX) throws InvalidBoundsException {
		if (minX >= this.maxX) {
			throw new InvalidBoundsException("Max X must be greater then Min X");
		} else {
			this.minX = minX;
		}
		startDrawing();
	}

	/**
	 * Get minimum Y
	 * 
	 * @return
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * Set minimum Y
	 * 
	 * @param minY
	 * @throws InvalidBoundsException
	 */
	public void setMinY(double minY) throws InvalidBoundsException {
		if (minY >= this.maxY) {
			throw new InvalidBoundsException("Max Y must be greater then Min Y");
		} else {
			this.minY = minY;
		}
		startDrawing();
	}

	/**
	 * Converts specified x value to it's pixel location.
	 *
	 * @param x
	 *            - the value of x for which to find the pixel location on the
	 *            graph.
	 * @return - the pixel value.
	 */
	private int UnitToPixelX(double x) {
		return Helpers.UnitToPixelX(x,minX,maxX,this.getWidth());
	}

	/**
	 * Converts specified y value to it's pixel location.
	 *
	 * @param y
	 *            - the value of y for which to find the pixel location on the
	 *            graph.
	 * @return - the pixel value.
	 */
	private int UnitToPixelY(double y) {
		return Helpers.UnitToPixelY(y,minY,maxY,this.getHeight());
	}

	/**
	 * Converts a horizontal pixel location to it's x value
	 *
	 * @param pix
	 *            - pixel location to convert.
	 * @return - x value of pixel location.
	 */
	public synchronized double PixelToUnitX(int pix) {
		return Helpers.PixelToUnitX(pix,minX,maxX,this.getWidth());
	}

	/**
	 * Converts a vertical pixel location to it's y value.
	 *
	 * @param pix
	 *            - pixel location to convert.
	 * @return - y value of pixel location.
	 */
	public synchronized double PixelToUnitY(int pix) {
		return Helpers.PixelToUnitY(pix,minY,maxY,this.getHeight());
	}

    void drawGraph(EquationPlotter[] equationPlotters){
        this.equationPlotters = equationPlotters;
        repaint();
    }

	/**
	 * Draws the grid
	 */
	void drawGrid() {
		this.equations = new Vector<>();
		startDrawing();
	}

	/**
	 * Zooms the graph
	 * 
	 * @param percent
	 */
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

		startDrawing();
	}

	/**
	 * Moves the x values view
	 * 
	 * @param percent
	 */
	public void moveHorizontal(double percent) {
		double move = (this.maxX - this.minX) * (percent / 100);
		this.minX += move;
		this.maxX += move;
		startDrawing();
	}

	/**
	 * Moves the Y values view
	 * 
	 * @param percent
	 */
	public void moveVertical(double percent) {
		double move = (this.maxY - this.minY) * (percent / 100);
		this.minY += move;
		this.maxY += move;
		startDrawing();
	}

	/**
	 * Centers the graph on the middle
	 */
	public void center() {
		double x = maxX - minX;
		double y = maxY - minY;

		minX = -(x / 2);
		maxX = x / 2;
		minY = -(y / 2);
		maxY = y / 2;

		startDrawing();
	}


	public void startDrawing() {
        GraphState graphState = GraphState.getInstance();
        graphState.updateGraphState(this,equations);
	}

    public void setEquations(Vector<Equation> equations){
        this.equations = equations;
    }



	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	public void componentResized(ComponentEvent e) {
		if(firstResize){
			panelWidth = this.getWidth();
			panelHeight = this.getHeight();
			firstResize = false;
			return;
		}
		
		int oldWidth = panelWidth;
		int oldHeight = panelHeight;
		panelWidth = this.getWidth();
		panelHeight = this.getHeight();

		try {
			this.setMinX(this.getMinX() * panelWidth / oldWidth);
			this.setMaxX(this.getMaxX() * panelWidth / oldWidth);
			this.setMinY(this.getMinY() * panelHeight / oldHeight);
			this.setMaxY(this.getMaxY() * panelHeight / oldHeight);
		} catch (InvalidBoundsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.startDrawing();
	}

	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
	}
}
