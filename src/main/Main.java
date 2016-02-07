/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.*;

/**
 * The main Class to run the graphingcalculator application
 * @author Egor
 */
class Main {

	/**
	 * Creates a new main window and sets it visible
	 */
    private static void showMainWindow() {
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
                    try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        System.setProperty("apple.laf.useScreenMenuBar", "true");
					} catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                showMainWindow();
            }
        });
    }
}
