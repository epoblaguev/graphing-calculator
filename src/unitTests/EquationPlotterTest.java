package unitTests;

import static org.junit.Assert.*;

import java.awt.geom.GeneralPath;

import org.junit.Before;
import org.junit.Test;

import equations.EquationPlotter;

public class EquationPlotterTest {
	GeneralPath polyline,polylineControl;
	double start,end,step;

	@Before
	public void setUp() throws Exception {
		start=0;
		end=5;
		step=1;
		
		polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, (int)end+1);
		polylineControl = new GeneralPath(GeneralPath.WIND_EVEN_ODD, (int)end+1);
	}

	@Test
	public void test() throws InterruptedException {
		String expression = "x";
		EquationPlotter equationPlotter = new EquationPlotter(expression, polyline, start, end, step);
		Thread epThread = new Thread(equationPlotter);
		epThread.start();
		epThread.join();
		System.out.println(equationPlotter.getPolyline());
		System.out.println(polyline);
	}

}
