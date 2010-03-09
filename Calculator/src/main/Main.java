/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Egor
 */
public class Main {

    public static void showMainWindow() {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (Exception e) {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                } finally {
                    UIManager.put("swing.boldMetal", Boolean.FALSE);
                    showMainWindow();
                }
            }
        });
    }
}
