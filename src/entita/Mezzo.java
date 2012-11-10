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
    private String scadBollo;
    private Date scadRevisione;
    private Date scadAtp;
    private Date scadAssicurazione1;
    private Date scadAssicurazione2;
    
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

    public Mezzo(Integer id, String targa, String marca, String scadBollo, Date scadRevisione, Date scadAtp, Date scadAssicurazione1, Date scadAssicurazione2) {
        this.id = id;
        this.targa = targa;
        this.marca = marca;
        this.scadBollo = scadBollo;
        this.scadRevisione = scadRevisione;
        this.scadAtp = scadAtp;
        this.scadAssicurazione1 = scadAssicurazione1;
        this.scadAssicurazione2 = scadAssicurazione2;
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

    public Date getScadAssicurazione1() {
        return scadAssicurazione1;
    }
    
    public Date getScadAssicurazione2() {
        return scadAssicurazione2;
    }

    public String getScadBollo() {
        return scadBollo;
    }

    public Date getScadRevisione() {
        return scadRevisione;
    }

    public String getFormattedScadAtp() {
        if (scadAtp == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadAtp);
    }

    public String getFormattedScadAssicurazione1() {
        if (scadAssicurazione1 == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadAssicurazione1);
    }
    
        public String getFormattedScadAssicurazione2() {
        if (scadAssicurazione2 == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadAssicurazione2);
    }

    public String getFormattedScadRevisione() {
        if (scadRevisione == null)
            return "";
        
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(scadRevisione);
    }
    
    public void setScadAtp(Date scadAtp) {
        this.scadAtp = scadAtp;
    }

    public void setScadAssicurazione1(Date scadAssicurazione) {
        this.scadAssicurazione1 = scadAssicurazione;
    }
    
    public void setScadAssicurazione2(Date scadAssicurazione) {
        this.scadAssicurazione2 = scadAssicurazione;
    }

    public void setScadBollo(String scadBollo) {
        this.scadBollo = scadBollo;
    }

    public void setScadRevisione(Date scadRevisione) {
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
        
        String scadRevisione = "";
        if (this.scadRevisione != null)
            scadRevisione = sdf.format(this.scadRevisione);
                
        String scadAtp = "";
        if (this.scadAtp != null)
            scadAtp = sdf.format(this.scadAtp);
        
        String scadAssicurazione1 = "";
        if (this.scadAssicurazione1 != null)
            scadAssicurazione1 = sdf.format(this.scadAssicurazione1);
        
        String scadAssicurazione2 = "";
        if (this.scadAssicurazione2 != null)
            scadAssicurazione2 = sdf.format(this.scadAssicurazione2);
        
        Object[] arrayMezzi = {this.targa, this.marca, scadRevisione, scadAtp, 
            (!scadAssicurazione2.isEmpty()) ? scadAssicurazione1 + " - " + scadAssicurazione2 : scadAssicurazione1, scadBollo};
        return arrayMezzi;
    }
}