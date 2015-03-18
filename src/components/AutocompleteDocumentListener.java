/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import Settings.Printer;

/**
 *
 * @author Egor
 */
public class AutocompleteDocumentListener implements DocumentListener{

    
    public void insertUpdate(DocumentEvent e) {
        e.getDocument();
		Printer.print("Insert Update: " + Document.TitleProperty);
        Printer.print("Offset: " + e.getOffset());
    }

    
    public void removeUpdate(DocumentEvent e) {
       e.getDocument();
	Printer.print("Remove Update: " + Document.TitleProperty);
    }

    
    public void changedUpdate(DocumentEvent e) {
        e.getDocument();
		Printer.print("Change Update:" + Document.TitleProperty);
    }

}
