/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import Constants.BlackLists;
import expressions.Variable;
import expressions.VariableList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Egor
 */
public class SmartTextField extends JTextField implements KeyListener, ActionListener {

    private static final ArrayList<String> list = new ArrayList<>();
    private final JPopupMenu autoCompleteMenu = new JPopupMenu();
    private String curString;
    private int position;

    public SmartTextField(String text, int columns) {
        super(text, columns);
        setUp();
    }

    public SmartTextField(int columns) {
        super(columns);
        setUp();
    }

    public SmartTextField(String text) {
        super(text);
        setUp();
    }

    public SmartTextField() {
        setUp();
    }

    private void setUp() {
        list.clear();
        this.addKeyListener(this);
        this.getCaret().setMagicCaretPosition(new Point(0, 0));
        this.curString = "";

        SmartTextField.rebuildList();
    }

    public static void rebuildList(){
        list.clear();
        Collections.addAll(list, BlackLists.variableBlackList);
        for(Variable var : VariableList.getVariables()){
            list.add(var.getVariableName());
        }
    }

    
    public void keyTyped(KeyEvent e) {
        position = this.getCaretPosition();
        autoCompleteMenu.setVisible(false);
        autoCompleteMenu.removeAll();


        if (e.getKeyChar() == '(') {
            //If '(' was typed, add a matching ')'
            this.setText(this.getText().substring(0, position) + ")" + this.getText().substring(position));
            this.setCaretPosition(position);
            this.curString = "";
        } else if (e.getKeyChar() == ')' && this.getText().length() != position && this.getText().substring(position, position + 1).equals(")")) {
            //If ')' was typed, and the next character is ')'
            int left = 0;
            int right = 0;
            String txt = this.getText();
            for (int i = 0; i < txt.length(); i++) {
                if (txt.charAt(i) == '(' && i < position) {
                    left++;
                }
                if (txt.charAt(i) == ')') {
                    right++;
                }
            }
            if(left >= right){
                //If there are more opening than closing parens, ignore input.
                this.setText(this.getText().substring(0, position) + this.getText().substring(position+1));
                this.setCaretPosition(position);
            }

        } else if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') || (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')) {
            //If character typed is [a-zA-Z], add possible autocomplete terms to menu and display menu.
            this.curString += e.getKeyChar();
            for (String str : list) {
                if (str.startsWith(curString)) {
                    JMenuItem mi = new JMenuItem(str);
                    mi.addActionListener(this);
                    mi.addKeyListener(this);
                    autoCompleteMenu.add(mi);
                }
            }
            if (autoCompleteMenu.getComponents().length > 0) {
                autoCompleteMenu.show(this, this.getCaret().getMagicCaretPosition().x + 7, (int) (this.getHeight() * 0.75));
                this.requestFocusInWindow();
            }
        } else {
            this.curString = "";
        }
        this.setCaretPosition(position);
        System.out.println("curString: " + this.curString);

    }

    
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            //If '(' was deleted and ')' is next char, delete ')' as well.
            position = this.getCaretPosition();
            if (position != 0 && position != this.getText().length()) {
                String prev = this.getText().substring(position - 1, position);
                String next = this.getText().substring(position, position + 1);
                if (prev.equals("(") && next.equals(")")) {
                    this.setText(this.getText().substring(0, position - 1) + this.getText().substring(position));
                    this.setCaretPosition(position);
                }
            }
        }
    }

   
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == this) {
            int[] validCodes = {KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};
            if (e.getKeyCode() < 65 || e.getKeyCode() > 90) {
                //If KeyCode is not a letter.
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
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("Focus on menu");
                autoCompleteMenu.setFocusable(true);
                autoCompleteMenu.requestFocusInWindow();
            }
        }
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == JMenuItem.class) {
            position = this.getCaretPosition();
            String str = this.getText();
            String toInsert = ((JMenuItem) e.getSource()).getText();
            this.setText(str.substring(0, position - curString.length()) + toInsert + str.substring(position));
            this.setCaretPosition(position + toInsert.length() - curString.length());
            this.curString = "";
        }
    }
}
