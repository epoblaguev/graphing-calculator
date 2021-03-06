/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Settings;

import java.awt.*;
import java.io.Serializable;

/**
 * Contains getters and setters for settings.
 * @author Egor
 */
public class GraphSettings implements Serializable{
    private static boolean antialiased = true;
    private static float lineWidth = 1;
    private static Color bgColor = Color.gray;
    private static boolean drawGrid = false;
    private static int minCalcPerPixel = 1;
    private static int maxCalcPerPixel = 10;

    /**
     * Check if grid should be drawn.
     * @return - true if grid should be drawn, false otherwise.
     */
    public static boolean isDrawGrid() {
        return drawGrid;
    }

    /**
     * Change the grid setting.
     * @param drawGrid - true if grid should be drawn, false otherwise.
     */
    public static void setDrawGrid(boolean drawGrid) {
        GraphSettings.drawGrid = drawGrid;
    }



    /**
     * Check if graph should be antialiased.
     * @return - true of graph sould be antialiased. False otherwise.
     */
    public static boolean isAntialiased() {
        return antialiased;
    }

    /**
     * Changes the antialias setting.
     * @param antialiased - antialias state.
     */
    public static void setAntialiased(boolean antialiased) {
        GraphSettings.antialiased = antialiased;
    }

    /**
     * Gets prefered width of line for graphing.
     * @return - width of line in pixels.
     */
    public static float getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets prefered width of line for graphing.
     * @param lineWidth - width of line in pixels.
     */
    public static void setLineWidth(float lineWidth) {
        GraphSettings.lineWidth = lineWidth;
    }

    /**
     * Gets prefered background color for the graph.
     * @return - background color for the graph.
     */
    public static Color getBgColor() {
        return bgColor;
    }

    /**
     * Sets prefered background color for the graph.
     * @param bgColor - background color for the graph.
     */
    public static void setBgColor(Color bgColor) {
        GraphSettings.bgColor = bgColor;
    }

    public static void setMaxCalcPerPixel(int maxCalcPerPixel) {
        GraphSettings.maxCalcPerPixel = maxCalcPerPixel;
    }

    public static void setMinCalcPerPixel(int minCalcPerPixel) {
        GraphSettings.minCalcPerPixel = minCalcPerPixel;
    }

    


}
