/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import Settings.Printer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 *
 * @author Egor
 */
class AutocompleteDocumentListener implements DocumentListener{

    
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
