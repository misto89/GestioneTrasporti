/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Spedizione implements Entity {
   
    public static enum tipo {
        NF, F, ALL
    }
    
    private class Bolle {
        public List<String> bolle = new LinkedList<String>();

        public Bolle() {
        }

        public Bolle(List<String> bolle) {
            this.bolle = bolle;
        }

        @Override
        public String toString() {
            if (bolle.isEmpty())
                return null;
            
            String stringa = "";
            for (String bolla : bolle) {
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
    private String um1;
    private Double qta1;
    private Double traz1;
    private Double distrib1;
    private String um2;
    private Double qta2;
    private Double traz2;
    private Double distrib2;
    private Double importo;
    private Integer sconto;
    private Integer percIva;
    private Double iva;
    private Double percProvvigione;
    private Double provvigione;
    private Double totale;
    private String note;
    private boolean rientrata;
    private Integer numFattura;
    private Date dataFattura;
    private Double valoreMerce;
    private Double imponibile;
    private Character stato;
    private Fornitore cliente;
    
    public static final int NUM_CAMPI = 22; //numero campi da visualizzare
    public static final int NUM_CAMPI_STORICO = 9; //numero campi da visualizzare per lo storico
    
    
    public Spedizione() {
    }

    public Spedizione(String num, Integer fornitore) {
        this.numero = num;
        this.fornitore = fornitore;
    }

    public Spedizione(String num, Integer fornitore, Date dataCarico, Date dataDocumento, String descrizione, String mezzo,
            String um1, Double qta1, Double traz1, Double distrib1, Double importo, Integer sconto, Integer percIva, Double iva, 
            Double percProvv, Double provv, Double totale, String note, boolean rientrata, Integer fattura, Date dataFattura,
            Double valMerce, Double imponibile, Character stato, String um2, Double qta2, Double traz2, Double distrib2) {
        
        this.numero = num;
        this.fornitore = fornitore;
        this.dataCarico = dataCarico;
        this.dataDocumento = dataDocumento;
        this.descrizione = descrizione;
        this.qta1 = qta1;
        this.traz1 = traz1;
        this.distrib1 = distrib1;
        this.importo = importo;
        this.mezzo = mezzo;
        this.um1 = um1;
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
        this.stato = stato;
        this.qta2 = qta2;
        this.traz2 = traz2;
        this.distrib2 = distrib2;
        this.um2 = um2;
    }

    public List<String> getBolle() {
        return bolle.bolle;
    }
    
    public String getStringaBolle() {
        return bolle.toString();
    }
    
    public void addBolla(String bolla) {
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

    public String getUm1() {
        return um1;
    }

    public void setUm1(String um) {
        this.um1 = um;
    }

    public Double getQta1() {
        return qta1;
    }

    public void setQta1(double qta) {
        this.qta1 = qta;
    }

    public Double getTraz1() {
        return traz1;
    }

    public void setTraz1(Double traz) {
        this.traz1 = traz;
    }

    public Double getDistrib1() {
        return distrib1;
    }

    public void setDistrib1(Double distrib) {
        this.distrib1 = distrib;
    }

    public String getUm2() {
        return um2;
    }

    public void setUm2(String um) {
        this.um2 = um;
    }

    public Double getQta2() {
        return qta2;
    }

    public void setQta2(double qta) {
        this.qta2 = qta;
    }

    public Double getTraz2() {
        return traz2;
    }

    public void setTraz2(Double traz) {
        this.traz2 = traz;
    }

    public Double getDistrib2() {
        return distrib2;
    }

    public void setDistrib2(Double distrib) {
        this.distrib2 = distrib;
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
    
    public Double getPercProvv() {
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

    public char getStato() {
        return stato;
    }

    public void setStato(char stato) {
        this.stato = stato;
    }
    
    public void setCliente(Fornitore cliente){
        this.cliente = cliente;
    }
    
    public Fornitore getCliente(){
        return cliente;
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
    
    public Object[] toArrayForStorico(){
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        String dataDoc = "";
        if (dataDocumento != null) 
           dataDoc = sdf.format(dataDocumento);
        
        String stato;
        if (this.stato == 'C')
            stato = "Consegna";
        else
            stato = "Ritiro";
        
        Object[] arrayFatt = {numero, cliente.getNome(), bolle.toString(), dataDoc, stato, descrizione, totale, mezzo, numFattura};
        return arrayFatt;
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
        
        String stato;
        if (this.stato == 'C')
            stato = "Consegna";
        else
            stato = "Ritiro";
        
        Object[] arrayFatt = {bolle.toString(), stato, numero, sdf.format(dataCarico), dataDoc, descrizione, um1, um2, qta1, qta2, traz1, traz2, distrib1, distrib2, importo, imponibile, valoreMerce, provvigione, note, rientrata, mezzo, numFattura};
        return arrayFatt;
    }
    
}