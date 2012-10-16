/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viste;

import java.util.LinkedList;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Michele
 */
public class JTextFieldLimitDecimal extends PlainDocument {
    
    private int limit;
    private List<Character> currentString = new LinkedList<Character>();
    private LinkedList<Character> charAfterDot = new LinkedList<Character>();
    
    public static final int LIMIT = 2;
    
    JTextFieldLimitDecimal(int limit) {
        super();
        this.limit = limit;
    }

     JTextFieldLimitDecimal(int limit, boolean upper) {
        super();
        this.limit = limit;
     }
     
     @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null)
            return;
                
        if (currentString.contains('.')) {
                       
            if ((charAfterDot.size()) < limit) {
                super.insertString(offs, str, a);
                currentString.add(str.charAt(0));
                charAfterDot.add(str.charAt(0));
            } 
            
        } else {
            super.insertString(offs, str, a);
            currentString.add(str.charAt(0));
        }          
            
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        
//        if (len > 1)
//            System.out.println("Ho rimosso l'intera stringa");
        
        if (len == 1) {
        
            if (!charAfterDot.isEmpty())
                charAfterDot.removeLast();    
            
            currentString.remove(offs);
            super.remove(offs, len);
            
        } 
        
//        for (char c : currentString)
//            System.out.print(c);
//        
//        System.out.println();
    }
    
}