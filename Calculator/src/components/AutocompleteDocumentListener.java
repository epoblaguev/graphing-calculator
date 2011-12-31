/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author Egor
 */
public class AutocompleteDocumentListener implements DocumentListener{

    
    public void insertUpdate(DocumentEvent e) {
        e.getDocument();
		System.out.println("Insert Update: " + Document.TitleProperty);
        System.out.println("Offset: " + e.getOffset());
    }

    
    public void removeUpdate(DocumentEvent e) {
       e.getDocument();
	System.out.println("Remove Update: " + Document.TitleProperty);
    }

    
    public void changedUpdate(DocumentEvent e) {
        e.getDocument();
		System.out.println("Change Update:" + Document.TitleProperty);
    }

}
