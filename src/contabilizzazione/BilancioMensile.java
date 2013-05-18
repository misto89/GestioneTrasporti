/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contabilizzazione;

import libs.DoubleFormatter;

/**
 *
 * @author Andrea
 */
public class BilancioMensile implements MovimentazioneMensile {

    private double totale = 0.00;
    private double totaleLiquidato = 0.00;  //Totale già incassato/pagato
    private double totaleDaLiquidare = 0.00; //Totale non ancora incassato/pagato
    private double saldo = 0.00;
    private String meseAnnoRif;

    public static final int NUM_CAMPI = 5;
    
    public BilancioMensile(String msAnRif) {
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

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public double getTotaleLiquidato() {
        return totaleLiquidato;
    }

    public void setTotaleLiquidato(double totaleLiquidato) {
        this.totaleLiquidato = totaleLiquidato;
    }

    public double getTotaleDaLiquidare() {
        return totaleDaLiquidare;
    }

    public void setTotaleDaLiquidare(double totaleDaLiquidare) {
        this.totaleDaLiquidare = totaleDaLiquidare;
    }

    public String getMeseAnnoRif() {
        return meseAnnoRif;
    }

    public void setMeseAnnoRif(String meseAnnoRif) {
        this.meseAnnoRif = meseAnnoRif;
    }
    
    public void setSaldo(){
        saldo = totaleLiquidato - totaleDaLiquidare;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {this.getMeseAnnoRif(), DoubleFormatter.roundTwoDecimals(totale), 
            DoubleFormatter.roundTwoDecimals(totaleLiquidato), DoubleFormatter.roundTwoDecimals(totaleDaLiquidare), 
            DoubleFormatter.roundTwoDecimals(saldo)};
    }
}
