/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Egor
 */
public class AutocompleteDocumentListener implements DocumentListener{

    
    public void insertUpdate(DocumentEvent e) {
        System.out.println("Insert Update: " + e.getDocument().TitleProperty);
        System.out.println("Offset: " + e.getOffset());
    }

    
    public void removeUpdate(DocumentEvent e) {
       System.out.println("Remove Update: " + e.getDocument().TitleProperty);
    }

    
    public void changedUpdate(DocumentEvent e) {
        System.out.println("Change Update:" + e.getDocument().TitleProperty);
    }

}
