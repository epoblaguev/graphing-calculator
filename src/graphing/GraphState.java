package graphing;

import equations.Equation;
import equations.EquationPlotter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by egor on 2/5/16.
 */
public class GraphState implements Runnable{
    private static final GraphState graphState = new GraphState();
    private final Map<EquationPlotter,Thread> equationPlotterThreads = new HashMap<>();
    private GraphPanel graphPanel;
    private boolean running = false;

    public static GraphState getInstance() {
        return graphState;
    }

    private GraphState() {
    }

    public synchronized void updateGraphState(GraphPanel graphPanel, Vector<Equation> equations){
        stopPlotting();

        this.graphPanel = graphPanel;
        equationPlotterThreads.clear();

        double minX = this.graphPanel.getMinX();
        double maxX = this.graphPanel.getMaxX();

        for(Equation equation : equations){
            EquationPlotter equationPlotter = new EquationPlotter(equation,minX,maxX, graphPanel.getWidth());
            Thread equationThread = new Thread(equationPlotter);
            equationPlotterThreads.put(equationPlotter,equationThread);
        }

        equationPlotterThreads.values().forEach(Thread::start);

        Thread t = new Thread(this);
        this.running = true;
        t.start();
    }

    private void stopPlotting(){
        this.running = false;
        for(EquationPlotter equationPlotter : equationPlotterThreads.keySet()){
            equationPlotter.stop();
            try {
                equationPlotterThreads.get(equationPlotter).join();
            } catch (InterruptedException e) {
                System.out.println("Failed to join thread - " + equationPlotter.getExpression());
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        boolean eqRunning;
        while(this.running){
            Set<EquationPlotter> keys = equationPlotterThreads.keySet();
            eqRunning = false;
            for(EquationPlotter key : keys){
                if(key.isRunning()) eqRunning = true;
            }
            graphPanel.drawGraph(keys.toArray(new EquationPlotter[keys.size()]));


            if(!eqRunning || keys.size() == 0){
                this.running = false;
                //System.out.println("Finished rendering.");
            }

        }
    }
}
