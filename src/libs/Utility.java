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
    
    /*
     * Ricevuti i parametri
     * Anno
     * Mese
     * Giorno
     * Effettua dei controlli e restituisce l'oggetto sql.Date equivalente.
     */
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
    
    /*
     * Rileva la data odierna e restituisce anno, mese e giorno in formato testuale per l'inserimento nelle JTextField
     * Struttura array:
     * -[0] -> Anno
     * -[1] -> Mese
     * -[2] -> Giorno
     */
    public static String[] todayDate(){
        //Inserisce una data presunta della fattura
        java.util.Date utilDate = new java.util.Date();
        String today = (new java.sql.Date(utilDate.getTime())).toString();
        String[] date = today.split("\\-");        
        return date;        
    }
    
    public static String[] setDataScadenza(String giorno, String mese, String anno, int numGiorni) {
//        JOptionPane.showMessageDialog(null, giorno + "-" + mese + "-" + anno + ": " + numGiorni);
        if (!giorno.isEmpty() && !mese.isEmpty() && !anno.isEmpty()) {

            Date data = Utility.dateValueOf(anno, mese, giorno, "data");
            String[] scadenza = DateUpdate.update(data, numGiorni).toString().split("-");
            return scadenza;

        }
        
        return null;
    }
}
