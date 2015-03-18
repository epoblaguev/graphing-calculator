/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphing;

import Constants.ConstValues;
import equations.Equation;
import Settings.GraphSettings;
import Settings.Printer;
import exceptions.InvalidBoundsException;
import expressions.Expression;
import expressions.Variable;
import expressions.VariableList;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DebugGraphics;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Class to represent the graph
 * 
 * @author Egor
 */
public class GraphPanel extends JPanel implements Runnable, ComponentListener {

	private static final long serialVersionUID = -8880798842884968375L;
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
	private static Vector<Equation> equations = new Vector<Equation>();
	private static volatile Vector<GeneralPath> polylines = new Vector<GeneralPath>();
	private Vector<Thread> threads = new Vector<Thread>();
	private boolean stopThreads = false;
	private boolean painting = false;
	private int currentEq = 0;
	private static HashMap<String, Point2D.Double> points = new HashMap<String, Point2D.Double>();
	private Graphics2D g2;

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
		painting = true;

		this.setBackground(GraphSettings.getBgColor());

		g2 = (Graphics2D) g;

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

		// Draw crossheir
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
		g2.drawString(df.format(maxX), this.getWidth()
				- df.format(maxX).length() * 7, xAxis - 1);
		g2.drawString(df.format(minY), yAxis + 2, this.getHeight() - 5);
		g2.drawString(df.format(maxY), yAxis + 2, 15);

		g2.setStroke(new BasicStroke(GraphSettings.getLineWidth()));
		// Loop through each equation.
		for (int i = 0; i < polylines.size(); i++) {
			g2.setColor(equations.get(i).getColor());
			try {
				g2.draw(polylines.get(i));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(i + " / " + polylines.size());
			}
		}
		// for (GeneralPath polyline : polylines) {
		// g2.draw(polyline);
		// }

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
		painting = false;
	}

	/**
	 * gets the x interval
	 * 
	 * @return
	 */
	public double getxInterval() {
		return xInterval;
	}

	/**
	 * Gets the y interval
	 * 
	 * @return
	 */
	public double getyInterval() {
		return yInterval;
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
	 * Get the x axis
	 * 
	 * @return
	 */
	public int getxAxis() {
		return xAxis;
	}

	/**
	 * Get the Y axis
	 * 
	 * @return
	 */
	public int getyAxis() {
		return yAxis;
	}

	/**
	 * Converts specified x value to it's pixel location.
	 * 
	 * @param x
	 *            - the value of x for which to find the pixel location on the
	 *            graph.
	 * @return - the pixel value.
	 */
	public synchronized int UnitToPixelX(double x) {
		double pixelsPerUnit = this.getWidth() / (maxX - minX);
		double pos = (x - minX) * pixelsPerUnit;
		return (int) pos;
	}

	/**
	 * Converts specified y value to it's pixel location.
	 * 
	 * @param y
	 *            - the value of y for which to find the pixel location on the
	 *            graph.
	 * @return - the pixel value.
	 */
	public synchronized int UnitToPixelY(double y) {
		double pixelsPerUnit = this.getHeight() / (maxY - minY);
		double pos = (y - minY) * pixelsPerUnit;
		pos = -pos + this.getHeight();
		return (int) pos;
	}

	/**
	 * Converts a horizontal pixel location to it's x value
	 * 
	 * @param pix
	 *            - pixel location to convert.
	 * @return - x value of pixel location.
	 */
	public double PixelToUnitX(int pix) {
		double unitsPerPixel = (maxY - minY) / this.getWidth();
		double x = (pix * unitsPerPixel) + minX;
		return x;
	}

	/**
	 * Converts a vertical pixel location to it's y value.
	 * 
	 * @param pix
	 *            - pixel location to convert.
	 * @return - y value of pixel location.
	 */
	public double PixelToUnitY(int pix) {
		double unitsPerPixel = (maxY - minY) / this.getHeight();
		double y = ((this.getHeight() - pix) * unitsPerPixel) + minY;
		return y;
	}

	/**
	 * Draws the graph with the equations
	 * 
	 * @param eq
	 */
	void drawGraph(Vector<Equation> eq) {
		GraphPanel.equations = new Vector<Equation>();
		for (Equation e : eq) {
			Expression expr = new Expression(e.getExpression());
			GraphPanel.equations.add(new Equation(expr.getExpression(), e.getColor()));
		}
		startDrawing();
	}

	/**
	 * Draws the grid
	 */
	void drawGrid() {
		GraphPanel.equations = new Vector<Equation>();
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

	/**
	 * Returns the next unused equation, and increments the currentEq value;
	 * 
	 * @return The next unused equation.
	 */
	private synchronized int getNextEQ() {
		if (currentEq > equations.size() - 1) {
			currentEq = 0;
		}
		// JOptionPane.showMessageDialog(this, currentEq);
		Printer.print(currentEq);
		return currentEq++;
	}

	private synchronized void increasePolylineNumber(int eqNumber) {
		while (polylines.size() < eqNumber + 1) {
			polylines.add(new GeneralPath(GeneralPath.WIND_EVEN_ODD, this.getWidth()));
		}
	}

	public void startDrawing() {
		stopThreads = true;
		for (Thread t : threads) {
			t.stop(); //TODO: Terbile design. This should be changed later. But it works for testing.
		}
		threads.clear();
		polylines.clear();
		stopThreads = false;
		repaint();
		for (int i=0; i<equations.size(); i++) {
			threads.add(new Thread(this));
			threads.lastElement().start();
		}
	}

	/**
	 * Repaints the pane
	 */
	public void run() {
		try {
			int eqNumber = this.getNextEQ();
			Equation eq = equations.get(eqNumber);

			increasePolylineNumber(eqNumber);
			GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, this.getWidth());

			boolean firstPoint = true;
			double interval, intervalFormula, slope;
			Double eqVal;
			Double eqPrev = 0.0;
			String expr = eq.getExpression();
			
			ExpressionBuilder expBuilder = new ExpressionBuilder(expr);
			expBuilder.variable("x");

			for (Variable var : VariableList.getVariables()) {
				expBuilder.variable(var.getVariableName());
			}
			net.objecthunter.exp4j.Expression equation = expBuilder.build();

			for (Variable var : VariableList.getVariables()) {
				equation.setVariable(var.getVariableName(), var.getVariableValue());
			}

			// Set values for loop.
			try {
				//eqPrev = Equation.evaluate(expr, minX, false);
				eqPrev=equation.setVariable("x", minX).evaluate();
			} catch (Exception exc) {
				equations.clear();
				JOptionPane.showMessageDialog(this, "Invalid Argument.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			polyline.moveTo(UnitToPixelX(minX), UnitToPixelY(eqPrev));
			// Printer.print("\neqNumber:" + eqNumber);
			// Printer.print("Size:" + polylines.size());
			polylines.set(eqNumber, polyline);
			interval = intervalFormula = (maxX - minX) / (this.getWidth());

			// Start loop.
			int loop = 0;
			for (double x = minX;; x += interval) {
				if (stopThreads) {
					break;
				}

				// eqVal and pixValX are used a lot. Solve only once.
				//eqVal = Equation.evaluate(expr, x, false);
				try{
				eqVal=equation.setVariable("x", x).evaluate();
				} catch (Exception e){
					System.out.println(e.getMessage());
					continue;
				}
				int pixValX = UnitToPixelX(x);

				if (eqVal.isNaN() || eqVal.isInfinite()) {
					firstPoint = true;
				} else if (firstPoint) {
					polyline.moveTo(pixValX, UnitToPixelY(eqVal));
					polylines.set(eqNumber, polyline);
					firstPoint = false;
				} else {
					polyline.lineTo(pixValX, UnitToPixelY(eqVal));
					polylines.set(eqNumber, polyline);
				}

				// Set interval.
				slope = Math.abs((eqVal - eqPrev) / (x - (x - interval)));
				if (slope > GraphSettings.getMinCalcPerPixel()) {
					if (slope > GraphSettings.getMaxCalcPerPixel()) {
						slope = GraphSettings.getMaxCalcPerPixel();
					}
					interval = intervalFormula / slope;
				} else {
					interval = intervalFormula;
				}
				eqPrev = eqVal;

				if ((loop++ % 10 == 0 || x >= maxX) && !painting) {
					repaint();
				}
				if (x >= maxX) {
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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
