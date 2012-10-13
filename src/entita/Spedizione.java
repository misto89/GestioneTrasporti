/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class Spedizione implements Entity {
   
    public static enum tipo {
        NF, F, ALL
    }
    
    private class Bolle {
        public List<Integer> bolle = new LinkedList<Integer>();

        public Bolle() {
        }

        public Bolle(List<Integer> bolle) {
            this.bolle = bolle;
        }

        @Override
        public String toString() {
            if (bolle.isEmpty())
                return null;
            
            String stringa = "";
            for (Integer bolla : bolle) {
                stringa += bolla + "-";
            }
                
           return stringa.substring(0, stringa.length() -1);
        }
        
    }
    
    private Bolle bolle = new Bolle();
    private String numero;
    private Date dataCarico;
    private Date dataDocumento;
    private String descrizione;
    private Integer fornitore;
    private String mezzo;
    private String um;
    private Integer qta;
    private Double traz;
    private Double distrib;
    private Double importo;
    private Integer sconto;
    private Integer percIva;
    private Double iva;
    private Integer percProvvigione;
    private Double provvigione;
    private Double totale;
    private String note;
    private boolean rientrata;
    private Integer numFattura;
    private Date dataFattura;
    private Double valoreMerce;
    private Double imponibile;
   
    public static final int NUM_CAMPI = 16; //numero campi da visualizzare
    
    public Spedizione() {
    }

    public Spedizione(String num, Integer fornitore) {
        this.numero = num;
        this.fornitore = fornitore;
    }

    public Spedizione(String num, Integer fornitore, Date dataCarico, Date dataDocumento, String descrizione, String mezzo,
            String um, int qta, Double traz, Double distrib, Double importo, Integer sconto, Integer percIva, Double iva, 
            Integer percProvv, Double provv, Double totale, String note, boolean rientrata, Integer fattura, Date dataFattura,
            Double valMerce, Double imponibile) {
        
        this.numero = num;
        this.fornitore = fornitore;
        this.dataCarico = dataCarico;
        this.dataDocumento = dataDocumento;
        this.descrizione = descrizione;
        this.qta = qta;
        this.traz = traz;
        this.distrib = distrib;
        this.importo = importo;
        this.mezzo = mezzo;
        this.um = um;
        this.iva = iva;
        this.totale = totale;
        this.sconto = sconto;
        this.percIva = percIva;
        this.percProvvigione = percProvv;
        this.provvigione = provv;
        this.note = note;
        this.rientrata = rientrata;
        this.numFattura = fattura;
        this.dataFattura = dataFattura;
        this.valoreMerce = valMerce;
        this.imponibile = imponibile;
    }

    public List<Integer> getBolle() {
        return bolle.bolle;
    }
    
    public String getStringaBolle() {
        return bolle.toString();
    }
    
    public void addBolla(Integer bolla) {
        bolle.bolle.add(bolla);
    }
    
    public String getNumSpedizione() {
        return numero;
    }
    
    public void setNumSpedizione(String num) {
        numero = num;
    }
    
    public Integer getFornitore() {
        return fornitore;
    }
    
    public void setFornitore(Integer forn) {
        fornitore = forn;
    }
    
    public Date getDataCarico() {
        return dataCarico;
    }

    public void setDataCarico(Date dataCarico) {
        this.dataCarico = dataCarico;
    }

    public Date getDataDocumento() {
        return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {
        this.dataDocumento = dataDocumento;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getMezzo() {
        return mezzo;
    }

    public void setMezzo(String mezzo) {
        this.mezzo = mezzo;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public int getQta() {
        return qta;
    }

    public void setQta(int qta) {
        this.qta = qta;
    }

    public Double getTraz() {
        return traz;
    }

    public void setTraz(Double traz) {
        this.traz = traz;
    }

    public Double getDistrib() {
        return distrib;
    }

    public void setDistrib(Double distrib) {
        this.distrib = distrib;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public Integer getSconto() {
        return sconto;
    }
    
    public void setSconto(int percSconto){
        sconto=percSconto;
    }
    
    public Integer getPercIva() {
        return percIva;
    }
    
    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }
    
    public Integer getPercProvv() {
        return percProvvigione;
    }

    public Double getProvvigione() {
        return provvigione;
    }
    
    public void setProvvigione(double impProvvigione) {
        provvigione=impProvvigione;
    }
    
    public Double getTotale() {
        return totale;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public String getNote() {
       return note;
    }
    
    public Boolean getRientrata() {
        return rientrata;
    }
    
    public Integer getNumFattura() {
        return numFattura;
    }
    
    public void setNumFattura(Integer numFattura) {
        this.numFattura = numFattura;
    }
    
    public Double getValoreMerce() {
        return valoreMerce;
    }
    
    public void setValoreMerce(Double valMerce) {
        this.valoreMerce = valMerce;
    }
    
    public Double getImponibile() {
        return imponibile;
    }
    
    public void setNumFattura(Double imponibile) {
        this.imponibile = imponibile;
    }
    
    public Date getDataFattura() {
        return dataFattura;
    }
    
    public void setDataFattura(Date data) {
        dataFattura = data;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spedizione)) {
            return false;
        }
        Spedizione other = (Spedizione) object;
//        if ((this.fatturaPK == null && other.fatturaPK != null) || (this.fatturaPK != null && !this.fatturaPK.equals(other.fatturaPK))) {
//            return false;
//        }
        return (this.numero.equals(other.numero) && this.dataCarico.equals(other.dataCarico));
    }

    @Override
    public String toString() {
        return "entita.Fattura[ numFattura = " + numero + ", fornitore/cliente = " + fornitore + " ]";
    }
    
    public Object[] toArray(){
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        String dataDoc = "";
        if (dataDocumento != null) 
           dataDoc = sdf.format(dataDocumento);
        
        char rientrata;
        if (this.rientrata)
            rientrata = 'S';
        else
            rientrata = 'N';
        
        Object[] arrayFatt = {bolle.toString(), numero, sdf.format(dataCarico), dataDoc, descrizione, um, qta, traz, distrib, importo, imponibile, valoreMerce, note, rientrata, mezzo, numFattura};
        return arrayFatt;
    }
    
}
