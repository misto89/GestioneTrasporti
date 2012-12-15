/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.sql.Date;

/**
 *
 * @author Michele
 */
public class Utility {
    
    public static Date dateValueOf(String anno, String mese, String giorno, String nome) {
        
        if (anno.isEmpty() || mese.isEmpty() || giorno.isEmpty()) { //Un o più campi fra gg, mm e aaaa non sono stati inseriti
            throw new IllegalArgumentException("Inserire tutti i campi per la " + nome);
        }
        
        Date data = null;
        if (anno.length() == 2)
            anno = "20" + anno;
                    
        else if (anno.length() == 3)
            anno = "2" + anno;

        if (mese.length() == 1)
            mese = "0" + mese;

        if (giorno.length() == 1) 
            giorno = "0" + giorno;

        try {
            data = Date.valueOf(anno + "-" + mese + "-" + giorno);
        } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
            throw new IllegalArgumentException("Formato " + nome + " non valido! Inserisci la " + nome + " nel formato gg mm aaaa");
        }
        
        return data;
    }
}
