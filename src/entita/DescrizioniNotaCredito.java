/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

/**
 *
 * @author Michele
 */
public class DescrizioniNotaCredito {
    
   private String descrizione;
   private Double importo;
   private Integer percIva;
   private Double iva;
   private NotaCredito nota;

   public DescrizioniNotaCredito(String descrizione, Double importo, Integer percIva, Double iva) {
        this.descrizione = descrizione;
        this.importo = importo;
        this.percIva = percIva;
        this.iva = iva;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public Integer getPercIva() {
        return percIva;
    }

    public void setPercIva(Integer percIva) {
        this.percIva = percIva;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public NotaCredito getNota() {
        return nota;
    }

    public void setNota(NotaCredito nota) {
        this.nota = nota;
    }
   
}
