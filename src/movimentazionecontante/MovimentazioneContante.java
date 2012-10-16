/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movimentazionecontante;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Andle
 */
public abstract class MovimentazioneContante {
    
    protected Integer id;
    protected Date dataMovimento;
    protected String banca;
    protected double importo; 
    
    public final static String PRELIEVO = "PRE";
    public final static String VERSAMENTO = "VER";
    public static final int NUM_CAMPI = 4;
    
    public MovimentazioneContante(Integer id, Date data, String b, double imp){
        this.dataMovimento = data;
        this.banca = b;
        this.importo = imp;
        this.id = id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public Date getDataMovimento() {
        return dataMovimento;
    }

    public String getFormattedDataMovimento() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(dataMovimento);
    }
    
    public void setDataMovimento(Date dataM) {
        this.dataMovimento = dataM;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }
        
    public abstract Object[] toArray();

}