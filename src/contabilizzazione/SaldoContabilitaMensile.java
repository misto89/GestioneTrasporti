/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contabilizzazione;

/**
 *
 * @author Andle
 */
public class SaldoContabilitaMensile implements MovimentazioneMensile {
   
    private double totPos = 0.00;
    private double totNeg = 0.00;
    private double saldo = 0.00;
    private String meseAnnoRif;

    public static final int NUM_CAMPI = 4;
    
    public SaldoContabilitaMensile(String msAnRif) {
        meseAnnoRif = setMeseAnno(msAnRif);
    }
    
    @Override
    public String setMeseAnno(String msAnno){
        String anno = " - " + msAnno.substring(0,4);
        String mese = msAnno.substring(5, 7);
        String meseAnno = "";
        
        if (mese.equalsIgnoreCase("01")) 
            meseAnno = "Gennaio" + anno;
        else if (mese.equalsIgnoreCase("02")) 
            meseAnno = "Febbraio" + anno;
        else if (mese.equalsIgnoreCase("03"))
            meseAnno = "Marzo" + anno;
        else if (mese.equalsIgnoreCase("04"))
            meseAnno = "Aprile" + anno;
        else if (mese.equalsIgnoreCase("05")) 
            meseAnno = "Maggio" + anno;
        else if (mese.equalsIgnoreCase("06")) 
            meseAnno = "Giugno" + anno;
        else if (mese.equalsIgnoreCase("07")) 
            meseAnno = "Luglio" + anno;
        else if (mese.equalsIgnoreCase("08")) 
            meseAnno = "Agosto" + anno;
        else if (mese.equalsIgnoreCase("09")) 
            meseAnno = "Settembre" + anno;
        else if (mese.equalsIgnoreCase("10")) 
            meseAnno = "Ottobre" + anno;
        else if (mese.equalsIgnoreCase("11"))
            meseAnno = "Novembre" + anno;
        else if (mese.equalsIgnoreCase("12"))
            meseAnno = "Dicembre" + anno;
        
        return meseAnno;
    }
    
    public void addPos(double val){
        totPos += val;
    }
    
    public void addNeg(double val){
        totNeg += val;
    }
    
    public String getMeseAnno(){
        return meseAnnoRif;
    }
    
    public double getTotPos(){
        return totPos;
    }
    
    public double getTotNeg(){
        return totNeg;
    }
    
    public void setSaldo(){
        saldo = totPos - totNeg;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {this.getMeseAnno(), roundTwoDecimals(totPos), roundTwoDecimals(totNeg), roundTwoDecimals(saldo)};
    }

    /*
     * Arrotonda a due cifre decimali il valore del double ricevuto come parametro
     */
    private static double roundTwoDecimals(double d) {
        return Math.rint(d * Math.pow(10,2)) / Math.pow(10,2);
    }
}
