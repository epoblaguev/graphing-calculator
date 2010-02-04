/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Egor
 */
public class Main {

    public static void showMainWindow(){
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                showMainWindow();
            }
        });
    }

}
