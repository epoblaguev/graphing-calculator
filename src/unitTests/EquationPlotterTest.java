package unitTests;

import equations.Equation;
import equations.EquationPlotter;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.GeneralPath;

public class EquationPlotterTest {
	private GeneralPath polyline;
	private final double start = 0;
    private final double end = 10;

	@Before
	public void setUp() throws Exception {

        polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, (int)end+1);
	}

	@Test
	public void test() throws InterruptedException {
		Equation equation = new Equation("x + 1");
		EquationPlotter equationPlotter = new EquationPlotter(equation,start,end,10);
		Thread epThread = new Thread(equationPlotter);
		epThread.start();
		//equationPlotter.stop();
		epThread.join();
		System.out.println(polyline.toString());
		System.out.println(polyline.getPathIterator(null));
	}

}
