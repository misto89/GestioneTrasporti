/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contabilizzazione;

import libs.DoubleFormatter;

/**
 *
 * @author Andle
 */
public class SaldoIvaMensile implements MovimentazioneMensile{
    
    private double ivaCredito = 0.00;
    private double ivaDebito = 0.00;
    private double saldoIva = 0.00;
    private String meseAnnoRif;
    
    public static final int NUM_CAMPI = 4;
    
    public SaldoIvaMensile(String msAnRif) {
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
    
    public void addIvaCredito(double val){
        ivaCredito += val;
    }
    
    public void addIvaDebito(double val){
        ivaDebito += val;
    }
    
    public String getMeseAnno(){
        return meseAnnoRif;
    }
    
    public double getIvaCredito(){
        return ivaCredito;
    }
    
    public double getIvaDebito(){
        return ivaDebito;
    }
    
    public void setSaldo(){
        saldoIva = ivaCredito - ivaDebito;
    }
    
    public double getSaldoIva() {
        return saldoIva;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {this.getMeseAnno(), DoubleFormatter.roundTwoDecimals(ivaCredito), DoubleFormatter.roundTwoDecimals(ivaDebito), DoubleFormatter.roundTwoDecimals(saldoIva)};
    }

}
