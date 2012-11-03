/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Mezzo implements Entity {
    private Integer id;
    private String targa;
    private String marca;
    private Date scadBollo;
    private String scadRevisione;
    private Date scadAtp;
    private Date scadAssicurazione;
    
    public static final int NUM_CAMPI = 6;

    public Mezzo() {
    }

    public Mezzo(Integer id) {
        this.id = id;
    }
    
    public Mezzo(Integer id, String targa, String marca) {
        this.id = id;
        this.targa = targa;
        this.marca = marca;
    }

    public Mezzo(Integer id, String targa, String marca, Date scadBollo, String scadRevisione, Date scadAtp, Date scadAssicurazione) {
        this.id = id;
        this.targa = targa;
        this.marca = marca;
        this.scadBollo = scadBollo;
        this.scadRevisione = scadRevisione;
        this.scadAtp = scadAtp;
        this.scadAssicurazione = scadAssicurazione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getScadAtp() {
        return scadAtp;
    }

    public Date getScadAssicurazione() {
        return scadAssicurazione;
    }

    public Date getScadBollo() {
        return scadBollo;
    }

    public String getScadRevisione() {
        return scadRevisione;
    }

    public String getFormattedScadAtp() {
        if (scadAtp == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadAtp);
    }

    public String getFormattedScadAssicurazione() {
        if (scadAssicurazione == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadAssicurazione);
    }

    public String getFormattedScadBollo() {
        if (scadBollo == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadBollo);
    }
    
    public void setScadAtp(Date scadAtp) {
        this.scadAtp = scadAtp;
    }

    public void setScadAssicurazione(Date scadAssicurazione) {
        this.scadAssicurazione = scadAssicurazione;
    }

    public void setScadBollo(Date scadBollo) {
        this.scadBollo = scadBollo;
    }

    public void setScadRevisione(String scadRevisione) {
        this.scadRevisione = scadRevisione;
    }
        
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        /*
        if (!(object instanceof Mezzo)) {
            return false;
        }
        Mezzo other = (Mezzo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;*/
        if (!(object instanceof Mezzo)) {
            return false;
        }
        
        return targa.equalsIgnoreCase(((Mezzo)object).targa);
    }

    @Override
    public String toString() {
        if (marca != null)
            return targa + " - MODELLO: " + marca;
        else return targa;
    }
    
    public Object[] toArray(){
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        
        String scadBollo = "";
        if (this.scadBollo != null)
            scadBollo = sdf.format(this.scadBollo);
                
        String scadAtp = "";
        if (this.scadAtp != null)
            scadAtp = sdf.format(this.scadAtp);
        
        String scadAssicurazione = "";
        if (this.scadAssicurazione != null)
            scadAssicurazione = sdf.format(this.scadAssicurazione);
        
        Object[] arrayMezzi = {this.targa, this.marca, scadBollo, scadAtp, scadAssicurazione, scadRevisione};
        return arrayMezzi;
    }
}