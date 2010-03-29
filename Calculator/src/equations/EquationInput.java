/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package equations;

import components.SmartTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author Egor
 */
public class EquationInput extends JPanel implements ActionListener, MouseListener {

    private JButton btnName;
    private JPanel labelPanel;
    private SmartTextField input;
    private JPopupMenu mnuRightClick;
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

    @Override
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

    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }

    @Override
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

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
