/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Andle
 */
public class Fattura implements Entity {
        
    protected Integer numero;
    protected Date data;
    protected String metodoPagamento;
    private Double importo;
    private Double provvigione;
    private Double sconto;
    protected Double imponibile;
    protected Double ivaTot;
    protected Double totale;
    private List<Spedizione> spedizioni;
    protected boolean pagata;
    private boolean forfait;
    protected Fornitore cliente;
    protected String note;
    protected Date dataScadenza;
    private String tipo; //solo per fatture d'acquisto
    private int fornitore; //solo per fatture d'acquisto
    private String specificaNumero; //solo per le fatture d'acquisto
    private List<Movimento> movimenti = new LinkedList<Movimento>();
    protected Date dataPagamento;
    
    public static int NUM_CAMPI_EMESSE = 12;
    public static int NUM_CAMPI_ACQUISTO = 13;
   
    public static enum pagata {
        P, NP, ALL
    }
    
    public static enum scaduta {
        S, NS, ALL
    }
    
    public static enum tipo {
        ACQ, VEN, ALL
    }
    
//    public static class order {
//        public static final int CLIENTE = 0;
//        public static final int NUM = 1;
//        public static final int DATA = 2;
//        public static final int MOD_PAG = 3;
//        public static final int PAGATA = 4;
//        public static final int IMPONIBILE = 5;
//        public static final int IVA = 6;
//        public static final int TOTALE = 7;
//        public static final int SCADENZA = 8;
//        public static final int NOTE_PAG = 9;
//        public static final int NOTE = 10;
//    }
        
    public Fattura() {
        
    }
    
    public Fattura(int num, Date data, String metPag, double imp, double prov, double sc, double iva, double tot, List<Spedizione> spedizioni, boolean forfait, boolean pagata, String n,
            Date dataScadenza, Date dataPagamento){
        numero = num;
        this.data = data;
        metodoPagamento = metPag;
        importo = imp;
        provvigione = prov;
        sconto = sc;
        ivaTot = iva;
        imponibile = imp - sc + prov;
        totale = tot;
        note = n;
        this.forfait = forfait;
        this.spedizioni = spedizioni;
        this.pagata = pagata;
        this.dataScadenza = dataScadenza;
        this.dataPagamento = dataPagamento;        
    }
    
    //costruttore per la fattura d'acquisto
    public Fattura (int num,  Date data, String metPag, double imp, double sc, double iva, double tot, String tipo, boolean pagata, int codForn, String note,
            Date dataScadenza, String specificaNumero, Date dataPagamento){
        numero = num;
        this.data = data;
        metodoPagamento = metPag;
        importo = imp;
        sconto = sc;
        ivaTot = iva;
        imponibile = imp - sc;
        totale = tot;
        this.tipo = tipo;
        this.pagata = pagata;
        fornitore = codForn;
        this.note = note;
        this.dataScadenza = dataScadenza;
        this.specificaNumero = specificaNumero;
        this.dataPagamento = dataPagamento;  
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int num) {
        numero = num;
    }
    
    public Date getData() {
        return data;
    }
    
    public String getFormattedData() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(data);
    }
    
    public void setData(Date data) {
        this.data = data;
    }
    
    public String getMetPag() {
        return metodoPagamento;
    }
    
    public void setMetPag(String metPag) {
        metodoPagamento = metPag;
    }
    
    public double getImporto() {
        return importo;
    }
    
    public void setImporto(double imp) {
        importo = imp; 
    }
    
    public double getSconto() {
        return sconto;
    }
    
    public void setSconto(double sc) {
        sconto = sc; 
    }
    
    public double getIva() {
        return ivaTot;
    }
    
    public void setIva(double iva) {
        ivaTot = iva; 
    }
    
    public double getProvvigione() {
        return provvigione;
    }
    
    public void setProvvigione(double prov) {
        provvigione = prov; 
    }
    
    public double getImponibile() {
        return imponibile;
    }
    
    public void setImponibile(double imp, double sc, double prov) {
        imponibile = imp - sc + prov;
    }
    
    public double getTotale() {
        return totale;
    }
    
    public void setTotale(double tot) {
        totale = tot;
    }
    
    public List<Spedizione> getSpedizioni() {
        return spedizioni;
    }    
    
    public boolean getPagata() {
        return pagata;
    }
    
    public void setPagata(boolean pagata){
        this.pagata = pagata;
    }
    
    public boolean getForfait(){
        return forfait;
    }
    
    public Fornitore getCliente() {
        return cliente;
    }    
    
    public void setCliente(Fornitore cliente) {
        this.cliente = cliente;
    }
    
    //Metodo esistente solo per le fatture d'acquisto
    public String getTipo() {
        return tipo;
    }
    
    //Metodo esistente solo per le fatture d'acquisto
    public int getFornitore() {
        return fornitore;
    }
    
    public void setNote(String nota) {
        this.note = nota;
    }
    
    public String getNote(){
        return note;
    }

    public String getSpecificaNumero() {
        return specificaNumero;
    }

    public void setSpecificaNumero(String specificaNumero) {
        this.specificaNumero = specificaNumero;
    }
       
    public String getNotePag(){
        if (movimenti.isEmpty())
            return null;
        
        else {
            String notePag = "";
            for (Movimento mov : movimenti)
                notePag += mov.getMetPag() + ": " + mov.getValore() + " - ";
            
            return notePag.substring(0, notePag.length() -2).trim();
        }
    }
    
    public List<Movimento> getMovimenti() {
        return movimenti;
    }
    
    public void setMovimenti(List<Movimento> movimenti) {
        this.movimenti = movimenti;
    }
    
    public int getPercIva() {
        return (int) Math.round((ivaTot * 100) / imponibile);
    }

    @Override
    public boolean equals(Object obj) {
        Fattura fatt = (Fattura) obj;
        
//        if (spedizioni == null)
//            return this.numero.equals(fatt.numero) && this.dataFattura.equals(fatt.dataFattura) && this.cliente.equals(fatt.getCliente());
//        else
            return this.numero.equals(fatt.numero) && this.data.equals(fatt.data);
    }
        
    public Object[] toArray() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        String metodoPagam = metodoPagamento.replace("-", " a ");
        metodoPagam = metodoPagam + " gg";
        
        char pagata;
        if (this.pagata)
            pagata = 'S';
        else
            pagata = 'N';
        
        String strCliente = cliente.getNome();
        if (cliente.getTitolare() != null)
            strCliente += " di " + cliente.getTitolare();
        
        return new Object[] {strCliente, numero, sdf.format(data), imponibile, ivaTot, totale, metodoPagam, pagata, (dataPagamento != null) ? sdf.format(dataPagamento) : "", (dataScadenza != null) ? sdf.format(dataScadenza) : "", getNotePag(), note};
    }
    
    public Object[] fattAcquistoToArray() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        String metodoPagam = metodoPagamento.replace("-", " a ");
        metodoPagam = metodoPagam + " gg";
        
        char pagata;
        if (this.pagata)
            pagata = 'S';
        else
            pagata = 'N';
        
        String strCliente = cliente.getNome();
        if (cliente.getTitolare() != null)
            strCliente += " di " + cliente.getTitolare();
        
        return new Object[] {strCliente, tipo, (specificaNumero != null) ? numero + "-" + specificaNumero : numero, sdf.format(data), imponibile, ivaTot, totale, metodoPagam, pagata, (dataPagamento != null) ? sdf.format(dataPagamento) : "", sdf.format(getDataScadenza()), getNotePag(), note};
    }
    
    public boolean isScaduta() {
        Date scadenza = getDataScadenza();
       
        GregorianCalendar calendar = new GregorianCalendar();
        String anno = String.valueOf(calendar.get(Calendar.YEAR));
        String mese = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String giorno = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                
        if (mese.length() == 1)
            mese = "0" + mese;
        
        if (giorno.length() == 1)
            giorno = "0" + giorno;
        
        Date dataOdierna = Date.valueOf(anno + "-" + mese + "-" + giorno);
        return (dataOdierna.compareTo(scadenza) >= 0);
    }
    
    public Date getDataScadenza() {
//        String[] mP = metodoPagamento.split("\\-");
//        int giorni = Integer.parseInt(mP[1]);
//
//        return DateUpdate.update(dataFattura, giorni);

        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }
    
    public String getFormattedDataScadenza() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return sdf.format(getDataScadenza());
    }
    
    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
   
    public String getFormattedDataPagamento() {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        if (dataPagamento != null)
            return sdf.format(dataPagamento);
        else 
            return "";
    }    
    
}