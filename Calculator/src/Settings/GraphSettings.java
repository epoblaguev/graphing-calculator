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

    public static boolean isAntialiased() {
        return antialiased;
    }

    public static void setAntialiased(boolean antialiased) {
        GraphSettings.antialiased = antialiased;
    }
}
