/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import Constants.BlackLists;
import java.awt.Component;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 *
 * @author Egor
 */
public class SmartTextField extends JTextField implements KeyListener, ActionListener {

    private ArrayList<String> list = new ArrayList<String>();
    private JPopupMenu autoCompleteMenu = new JPopupMenu();
    private String curString;

    public SmartTextField(String text, int columns) {
        super(text, columns);
    }

    public SmartTextField(int columns) {
        super(columns);
    }

    public SmartTextField(String text) {
        super(text);
    }

    public SmartTextField() {
        setUp();
    }

    private void setUp() {
        this.addKeyListener(this);
        this.getCaret().setMagicCaretPosition(new Point(0, 0));
        this.curString = "";

        for (String str : BlackLists.variableBlackList) {
            list.add(str);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int position = this.getCaretPosition();
        autoCompleteMenu.setVisible(false);
        autoCompleteMenu.removeAll();
        if (e.getKeyChar() == '(') {
            this.setText(this.getText().substring(0, position) + ")" + this.getText().substring(position));
            this.setCaretPosition(position);
            this.curString = "";
        }
        else if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z')||(e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')) {
            this.curString += e.getKeyChar();
            for (String str : list) {
                if (str.contains(curString)) {
                    JMenuItem mi = new JMenuItem(str);
                    mi.addActionListener(this);
                    autoCompleteMenu.add(mi);
                }
            }
            if (autoCompleteMenu.getComponents().length > 0) {
                autoCompleteMenu.show(this, this.getCaret().getMagicCaretPosition().x + 7, (this.getHeight() / 2) + 4);
            }
            this.requestFocus();
        } else {
            this.curString = "";
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 8) {
            int position = this.getCaretPosition();
            if (position != 0 && position != this.getText().length()) {
                String prev = this.getText().substring(position - 1, position);
                String next = this.getText().substring(position, position + 1);
                if (prev.equals("(") && next.equals(")")) {
                    this.setText(this.getText().substring(0, position - 1) + this.getText().substring(position));
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(curString);
        int[] validCodes = {16, 17, 18};
        if (e.getKeyCode() < 65 || e.getKeyCode() > 90) {
            boolean valid = false;
            for (int i : validCodes) {
                if (e.getKeyCode() == i) {
                    valid = true;
                }
            }
            if (!valid) {
                this.curString = "";
                autoCompleteMenu.setVisible(false);
            }
        }
        System.out.println(curString);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().getClass() == JMenuItem.class){
            int position = this.getCaretPosition();
            String str = this.getText();
            String toInsert = ((JMenuItem)e.getSource()).getText();
             this.setText(str.substring(0, position-curString.length()) + toInsert + str.substring(position));
            this.setCaretPosition(position + toInsert.length() - curString.length());
            this.curString = "";
        }
    }
}
