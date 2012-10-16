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
 * @author Andle
 */
public class JTextFieldFormatDouble extends PlainDocument{
   
    JTextFieldFormatDouble() {
        super();
    }
    
    @Override
     public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
          return;

        if (str.contains(",")){ //E' stata inserita una virgola al posto del punto
            str = str.replace(",", ".");
        }
        super.insertString(offset, str, attr);
     }
}