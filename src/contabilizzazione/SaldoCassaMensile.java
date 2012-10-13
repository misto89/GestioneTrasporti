/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contabilizzazione;

import controllo.FrontController;
import entita.Fattura;
import libs.DoubleFormatter;

/**
 *
 * @author Andle
 */
public class SaldoCassaMensile implements MovimentazioneMensile{
   
    private double assegni = 0.00;
    private double contanti = 0.00;
    private double riba = 0.00;
    private double bonifico = 0.00;
    private Fattura.tipo tipoCassa; //Cassa attiva o passiva
    private String meseAnnoRif;
    
    public static final int NUM_CAMPI = 6;
    
    public static final String CONTANTE; 
    public static final String BONIFICO; 
    public static final String ASSEGNO; 
    public static final String RIBA;
    
    static {
        String[] metPag = FrontController.getMetodiPagamento();
        CONTANTE = metPag[1];
        BONIFICO = metPag[2];
        ASSEGNO = metPag[3];
        RIBA = metPag[4];
    }
    
    public SaldoCassaMensile(String annoMese, Fattura.tipo tipo) {
        tipoCassa = tipo;
        meseAnnoRif = setMeseAnno(annoMese);
    }
    
    @Override
    public String setMeseAnno(String msAnno) {
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
    
    public void addContante(double importo) {
        this.contanti += importo;
    }
    
    public void addAssegno(double importo) {
        this.assegni += importo;
    }
     
    public void addBonifico(double importo) {
        this.bonifico += importo;
    }
      
    public void addRiba(double importo) {
        this.riba += importo;
    }
    
    public double getAssegni() {
        return assegni;
    }

    public void setAssegni(double assegni) {
        this.assegni = assegni;
    }

    public double getBonifico() {
        return bonifico;
    }

    public void setBonifico(double bonifico) {
        this.bonifico = bonifico;
    }

    public double getContanti() {
        return contanti;
    }

    public void setContanti(double contanti) {
        this.contanti = contanti;
    }

    public String getMeseAnnoRif() {
        return meseAnnoRif;
    }

    public void setMeseAnnoRif(String meseAnnoRif) {
        this.meseAnnoRif = meseAnnoRif;
    }

    public double getRiba() {
        return riba;
    }

    public void setRiba(double riba) {
        this.riba = riba;
    }

    @Override
    public Object[] toArray() {
        return new Object[] {this.getMeseAnnoRif(),  DoubleFormatter.roundTwoDecimals(contanti), DoubleFormatter.roundTwoDecimals(assegni), DoubleFormatter.roundTwoDecimals(bonifico), DoubleFormatter.roundTwoDecimals(riba)};
    }
    
}
