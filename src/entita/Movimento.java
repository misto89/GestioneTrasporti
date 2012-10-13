/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.sql.Date;

/**
 *
 * @author Michele
 */
public class Movimento implements Entity {
    
    private Integer id;
    private Integer numDoc;
    private Date data;
    private String tipo;
    private String metPag;
    private Double valore;

    public Movimento() {
    }

    public Movimento(Integer id) {
        this.id = id;
    }

    public Movimento(Integer id, Integer numDoc, Date data, String tipo, String metPag, Double valore) {
        this.id = id;
        this.numDoc = numDoc;
        this.data = data;
        this.tipo = tipo;
        this.metPag = metPag;
        this.valore = valore;
    }
    
    public Movimento(Integer numDoc, Date data, String tipo, String metPag, Double valore) {
        this.numDoc = numDoc;
        this.data = data;
        this.tipo = tipo;
        this.metPag = metPag;
        this.valore = valore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(Integer numDoc) {
        this.numDoc = numDoc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMetPag() {
        return metPag;
    }

    public void setMetPag(String metPag) {
        this.metPag = metPag;
    }

    public Double getValore() {
        return valore;
    }

    public void setValore(Double valore) {
        this.valore = valore;
    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Movimenti)) {
//            return false;
//        }
//        Movimenti other = (Movimenti) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "Numero documento: " + numDoc + " - Data: " + data + " - Tipo: " + tipo + " - Pagamento: " + metPag + " - Valore: " + valore;
    }
    
}
