/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The main Class to run the graphingcalculator application
 * @author Egor
 */
public class Main {

	/**
	 * Creates a new main window and sets it visible
	 */
    public static void showMainWindow() {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

    /**
     * Initiates the graphingcalculator application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    showMainWindow();
            }
        });
    }
}
