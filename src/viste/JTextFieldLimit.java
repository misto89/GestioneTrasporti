/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viste;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Michele
 */
class JTextFieldLimit extends PlainDocument {
    
    private int limit;
    
    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

     JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
     }

    @Override
     public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
          return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
     }

}