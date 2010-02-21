/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Settings;

/**
 *
 * @author Egor
 */
public class GraphSettings {
    private static boolean antialiased = true;
    private static float lineWidth = 1;

    public static boolean isAntialiased() {
        return antialiased;
    }

    public static void setAntialiased(boolean antialiased) {
        GraphSettings.antialiased = antialiased;
    }

    public static float getLineWidth() {
        return lineWidth;
    }

    public static void setLineWidth(float lineWidth) {
        GraphSettings.lineWidth = lineWidth;
    }
}
