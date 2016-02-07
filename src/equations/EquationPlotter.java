package equations;

import java.awt.*;
import java.util.Vector;

public class EquationPlotter implements Runnable {
    private final double minX, maxX, xStep;
    private boolean running;
	private final Equation equation;
    private final Vector<double[]> xyCoordinates;

    /**
     * Constructor.
     *
     * @param equation The equation to be evaluated.
     * @param minX The 'x' value at which to start evaluating.
     * @param maxX The 'x' value at which to finish evaluating.
     * @param width The width of the plot in pixels, determines how many points to evaluate.
     */
	public EquationPlotter(Equation equation, double minX, double maxX, int width) {
		this.minX = minX;
		this.maxX = maxX;
		this.running = false;
        this.equation = equation;
        this.xStep = (maxX - minX) / width;
        this.xyCoordinates = new Vector<>();
    }

	public Vector<double[]> getXYCoordinates() {
		return xyCoordinates;
	}

    public String getExpression(){
        return equation.getExpression();
    }

    public Equation getEquation(){
        return equation;
    }

    public Color getColor(){
        return equation.getColor();
    }

    /**
     * Notifies thread to stop.
     */
    public void stop(){
        this.running = false;
    }

    public boolean isRunning(){
        return this.running;
    }

	@Override
	public void run() {
		double y;
		this.running = true;
		for (double x = minX; x <= maxX; x += xStep) {
			if (!this.running) {
                //System.out.println("Stopping thread.");
                break;
            }

			y = equation.evaluate(x);
			xyCoordinates.add(new double[]{x,y});
			//System.out.println(this.getClass().getName() + String.format(": x=%s, y=%s", x, y));
		}
        this.running = false;
	}
}
