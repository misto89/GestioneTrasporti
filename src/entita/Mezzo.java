/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

public class Mezzo implements Entity {
    private Integer id;
    private String targa;
    private String marca;
    private String scadBollo;
    private String scadRevisione;
    private String scadAtp;
    private String scadAssicurazione;
    
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

    public Mezzo(Integer id, String targa, String marca, String scadBollo, String scadRevisione, String scadAtp, String scadAssicurazione) {
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

    public String getScadAtp() {
        return scadAtp;
    }

    public String getScadAssicurazione() {
        return scadAssicurazione;
    }

    public String getScadBollo() {
        return scadBollo;
    }

    public String getScadRevisione() {
        return scadRevisione;
    }

    public void setScadAtp(String scadAtp) {
        this.scadAtp = scadAtp;
    }

    public void setScadAssicurazione(String scadAssicurazione) {
        this.scadAssicurazione = scadAssicurazione;
    }

    public void setScadBollo(String scadBollo) {
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
        Object[] arrayMezzi = {this.targa, this.marca, this.scadBollo, this.scadRevisione, this.scadAtp, this.scadAssicurazione};
        return arrayMezzi;
    }
}