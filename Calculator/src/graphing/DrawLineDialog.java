/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphing;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Set;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class DrawLineDialog extends JFrame{
    private JLabel lblFrom, lblTo;
    private JComboBox cbFrom, cbTo;
    private JPanel equationPanel;
    public DrawLineDialog(JPanel equationPanel){
        super();
        this.setLayout(new GridLayout(0, 1));
        this.setTitle("Draw Line");
        cbFrom = new JComboBox(GraphPanel.getPoints().keySet().toArray());
        cbTo = new JComboBox(GraphPanel.getPoints().keySet().toArray());

        this.add(cbFrom);
        this.add(cbTo);
        this.pack();
    }
}
