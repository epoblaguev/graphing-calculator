/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import Constants.BlackLists;
import expressions.Variable;
import expressions.VariableList;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 *
 * @author Egor
 */
public class SmartTextField extends JTextField implements KeyListener, ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5457545908621424875L;
	private static ArrayList<String> list = new ArrayList<String>();
    private JPopupMenu autoCompleteMenu = new JPopupMenu();
    private String curString;

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
        for (String str : BlackLists.variableBlackList) {
            list.add(str);
        }
        for(Variable var : VariableList.getVariables()){
            list.add(var.getVariableName());
        }
    }

    
    public void keyTyped(KeyEvent e) {
        int position = this.getCaretPosition();
        autoCompleteMenu.setVisible(false);
        autoCompleteMenu.removeAll();
        if (e.getKeyChar() == '(') {
            this.setText(this.getText().substring(0, position) + ")" + this.getText().substring(position));
            this.setCaretPosition(position);
            this.curString = "";
        } else if (this.getText().length() != position && e.getKeyChar() == ')' && this.getText().substring(position, position + 1).equals(")")) {
            //Check if parens are balanced.
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
                this.setText(this.getText().substring(0, position) + this.getText().substring(position+1));
                this.setCaretPosition(position);
            }

        } else if ((e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') || (e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z')) {
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
                int pos =this.getSelectionEnd();
                this.select(pos,pos);
                this.setCaretPosition(pos);
            }
        } else {
            this.curString = "";
        }

    }

    
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == 8) {
            int position = this.getCaretPosition();
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
            int[] validCodes = {16, 17, 18, 38, 40};
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
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                autoCompleteMenu.requestFocusInWindow();
            }
        }
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == JMenuItem.class) {
            int position = this.getCaretPosition();
            String str = this.getText();
            String toInsert = ((JMenuItem) e.getSource()).getText();
            this.setText(str.substring(0, position - curString.length()) + toInsert + str.substring(position));
            this.setCaretPosition(position + toInsert.length() - curString.length());
            this.curString = "";
        }
    }
}
