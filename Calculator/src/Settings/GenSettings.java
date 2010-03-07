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


    /**
     * Loads Icon image. Works inside JAR files.
     * @param location - location of icon image.
     * @return - the icon image.
     */
    public static ImageIcon getImageIcon(String location) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(GenSettings.class.getResource(location)));
    }

    /**
     * Loads an image. Works inside JAR files.
     * @param location - location of image.
     * @return - the image.
     */
    public static Image getImage(String location) {
        return Toolkit.getDefaultToolkit().getImage(GenSettings.class.getResource(location));
    }
}
