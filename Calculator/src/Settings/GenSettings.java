/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Egor
 */
public class GenSettings {

    public static ImageIcon getImageIcon(String location) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(GenSettings.class.getResource(location)));
    }

    public static Image getImage(String location) {
        return Toolkit.getDefaultToolkit().getImage(GenSettings.class.getResource(location));
    }
}
