/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equations;

import components.SmartTextField;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Egor
 */
public class EquationInput extends JPanel implements ActionListener, MouseListener {

    private JButton btnName;
    private final JPanel labelPanel;
    private SmartTextField input;
    private final JPopupMenu mnuRightClick;
    private Color color;

    public EquationInput(String name, Color color) {
        super();
        this.setLayout(new FlowLayout());
        this.btnName = new JButton(name);
        this.input = new SmartTextField(20);
        this.labelPanel = new JPanel();
        this.color = color;

        this.btnName.setForeground(color);
        this.btnName.setBorderPainted(true);
        this.btnName.setContentAreaFilled(false);
        this.btnName.setHorizontalAlignment(SwingConstants.RIGHT);
        this.btnName.setPreferredSize(new Dimension(60, 20));
        this.labelPanel.setBackground(Color.lightGray);

        mnuRightClick = new JPopupMenu();

        this.btnName.addActionListener(this);
        this.input.addMouseListener(this);

        this.add(labelPanel);
        labelPanel.add(this.btnName);
        labelPanel.add(input);
    }

    public SmartTextField getInput() {
        return input;
    }

    public void setInput(SmartTextField input) {
        this.input = input;
    }

    public JButton getBtnName() {
        return btnName;
    }

    public void setBtnName(JButton name) {
        this.btnName = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnName) {
            Color clr = JColorChooser.showDialog(btnName, "Color Chooser", btnName.getForeground());
            if (clr != null) {
                this.btnName.setForeground(clr);
                this.color = clr;
            }
            this.revalidate();
        }
    }

    
    public void mouseClicked(MouseEvent e) {
        //
    }

    
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == input && (e.isPopupTrigger() || e.getModifiers() == InputEvent.BUTTON3_MASK)) {
            input.requestFocus();
            JMenuItem mnuItem;
            mnuRightClick.removeAll();

            mnuItem = new JMenuItem(new DefaultEditorKit.CutAction());
            mnuItem.setText("Cut");
            mnuRightClick.add(mnuItem);
            mnuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
            mnuItem.setText("Copy");
            mnuRightClick.add(mnuItem);
            mnuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
            mnuItem.setText("Paste");
            mnuRightClick.add(mnuItem);

            mnuRightClick.show(input, e.getX() + 10, e.getY());
        }
    }

    
    public void mouseReleased(MouseEvent e) {
        //
    }

    
    public void mouseEntered(MouseEvent e) {
        //
    }

   
    public void mouseExited(MouseEvent e) {
        //
    }
}
