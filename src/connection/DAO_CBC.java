/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import contabilizzazione.BilancioMensile;
import contabilizzazione.SaldoCassaMensile;
import contabilizzazione.SaldoContabilitaMensile;
import contabilizzazione.SaldoIvaMensile;
import eccezioni.EccezioneChiaveDuplicata;
import eccezioni.EccezioneModificaFattAcquisto;
import eccezioni.EccezioneValoreCampoTroppoLungo;
import entita.DescrizioniNotaCredito;
import entita.Fattura;
import entita.Fornitore;
import entita.Movimento;
import entita.NotaCredito;
import entita.Spedizione;
import movimentazionecontante.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Michele
 */
public class DAO_CBC {
    //Codici di errore MySql
    private static final int DUPLICATE_ENTRY = 1062;
    private static final int DATA_TOO_LONG = 1406;
    
    //Parametri di connessione al db
    private static Connection conn = new DbManager().CreateConnection();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static String sql;
    
    /**
     * Recupera tutti gli anni in cui sono state emesse fatture.
     * @return Una lista di interi.
     */
    public static List<Integer> getAnniEsercizio(Fattura.tipo tipo){
        try {
            if (tipo == Fattura.tipo.VEN) 
                sql = "SELECT YEAR(" + Tabelle.Fatture.DATA + ") as ANNO FROM " + Tabelle.FATTURE + 
                        " UNION " + 
                        "SELECT YEAR(" + Tabelle.NoteCredito.DATA + ") as ANNO FROM " + Tabelle.NOTE_CREDITO + 
                        " ORDER BY ANNO DESC";
            
            else if (tipo == Fattura.tipo.ACQ)
                sql = "SELECT DISTINCT YEAR(" + Tabelle.FattureAcquisto.DATA + ") as ANNO FROM " + Tabelle.FATT_ACQUISTO + 
                        " ORDER BY " + Tabelle.FattureAcquisto.DATA + " DESC";
            else
                sql = "SELECT YEAR(" + Tabelle.Fatture.DATA + ") as ANNO FROM " + Tabelle.FATTURE + 
                        " UNION " + 
                        "SELECT YEAR(" + Tabelle.FattureAcquisto.DATA + ") as ANNO FROM " + Tabelle.FATT_ACQUISTO + 
                        " UNION " + 
                        "SELECT YEAR(" + Tabelle.NoteCredito.DATA + ") as ANNO FROM " + Tabelle.NOTE_CREDITO + 
                        " ORDER BY ANNO DESC";
                
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Integer> anni = new LinkedList<Integer>();
            
            while (rs.next()) {
                int anno = rs.getInt("ANNO");
                if (!anni.contains(anno))
                    anni.add(anno);
            }
            
            return anni;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Recupera tutti gli anni in cui sono state effettuate spedizioni.
     * @return Una lista di interi.
     */
    public static List<Integer> getAnniEsercizioForSpedizioni(){
        try {
            sql = "SELECT DISTINCT YEAR(" + Tabelle.Spedizioni.DATA_CARICO + ") as ANNO FROM " + Tabelle.SPEDIZIONI + " ORDER BY " + Tabelle.Spedizioni.DATA_CARICO + " DESC";
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Integer> anni = new LinkedList<Integer>();
            
            while (rs.next()) {
                int anno = rs.getInt("ANNO");                
                anni.add(anno);
            }
            
            return anni;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static void ricFatt(boolean recuperaCliente, Fornitore cliente, List<Fattura> fatture) throws SQLException {
        Integer numero;
        Date dataFattura;
        String metodoPagamento;
        Double importo;
        Double provvigione;
        Double sconto;
        Double ivaTot;
        Double totale;
        boolean pagata;
        boolean forfait;
        String note;
        Date dataScadenza;
        Date dataPagamento;

        while (rs.next()){
            numero = rs.getInt(Tabelle.Fatture.NUMERO);
            dataFattura = rs.getDate(Tabelle.Fatture.DATA);
            metodoPagamento = rs.getString(Tabelle.Fatture.METODO_PAGAMENTO);
            importo = rs.getDouble(Tabelle.Fatture.IMPORTO);
            provvigione = rs.getDouble(Tabelle.Fatture.PROVVIGIONE);
            sconto = rs.getDouble(Tabelle.Fatture.SCONTO);
            ivaTot = rs.getDouble(Tabelle.Fatture.IVA);
            totale = rs.getDouble(Tabelle.Fatture.TOTALE);
            pagata = rs.getBoolean(Tabelle.Fatture.PAGATA);
            forfait = rs.getBoolean(Tabelle.Fatture.FORFAIT);
            note = rs.getString(Tabelle.Fatture.NOTE);
            dataScadenza = rs.getDate(Tabelle.Fatture.SCADENZA);
            dataPagamento = rs.getDate(Tabelle.Fatture.PAGAMENTO);
            
            Fattura fatt = new Fattura(numero, dataFattura, metodoPagamento, importo, provvigione, sconto, ivaTot, totale, ricSpedizioni(numero, dataFattura) , forfait, pagata, note,
                    dataScadenza, dataPagamento);
            
            int codFornCliente = 0;
            if (!recuperaCliente)
                fatt.setCliente(cliente);

            else { //Recupera i dati relativi al cliente

                Integer cod = rs.getInt(Tabelle.Fornitori.COD);
                String nome = rs.getString(Tabelle.Fornitori.NOME);
                String titolare = rs.getString(Tabelle.Fornitori.TITOLARE);
                String piva = rs.getString(Tabelle.Fornitori.PIVA);
                String codfisc = rs.getString(Tabelle.Fornitori.CODFISCALE);
                String indirizzoLeg = rs.getString(Tabelle.Fornitori.INDIRIZZO_LEGALE);
                String telefono1 = rs.getString(Tabelle.Fornitori.TELEFONO1);
                String telefono2 = rs.getString(Tabelle.Fornitori.TELEFONO2);
                String fax = rs.getString(Tabelle.Fornitori.FAX);
                String email = rs.getString(Tabelle.Fornitori.EMAIL);
                String capLeg = rs.getString(Tabelle.Fornitori.CAP_LEGALE);
                String cittaLeg = rs.getString(Tabelle.Fornitori.CITTA_LEGALE);
                String provLeg = rs.getString(Tabelle.Fornitori.PROV_LEGALE);
                String nazioneLeg = rs.getString(Tabelle.Fornitori.NAZIONE_LEGALE);
                String banca = rs.getString(Tabelle.Fornitori.BANCA);
                String iban = rs.getString(Tabelle.Fornitori.IBAN);
                String nomeRef1 = rs.getString(Tabelle.Fornitori.NOME_REF_1);
                String nomeRef2 = rs.getString(Tabelle.Fornitori.NOME_REF_2);
                String emailRef1 = rs.getString(Tabelle.Fornitori.EMAIL_REF_1);
                String emailRef2 = rs.getString(Tabelle.Fornitori.EMAIL_REF_2);
                String telRef1 = rs.getString(Tabelle.Fornitori.TEL_REF_1);
                String telRef2 = rs.getString(Tabelle.Fornitori.TEL_REF_2);
                String iscrizioneAlbo = rs.getString(Tabelle.Fornitori.ISCRIZIONE_ALBO);
                String indirizzoOp = rs.getString(Tabelle.Fornitori.INDIRIZZO_OP);
                String capOp = rs.getString(Tabelle.Fornitori.CAP_OP);
                String cittaOp = rs.getString(Tabelle.Fornitori.CITTA_OP);
                String provOp = rs.getString(Tabelle.Fornitori.PROV_OP);
                String nazioneOp = rs.getString(Tabelle.Fornitori.NAZIONE_OP);

                fatt.setCliente(new Fornitore(cod, nome, titolare, piva, codfisc, indirizzoLeg, telefono1, telefono2, fax, email, capLeg, cittaLeg, provLeg, nazioneLeg, banca, iban,
                        iscrizioneAlbo, indirizzoOp, cittaOp, capOp, provOp, nazioneOp, nomeRef1, nomeRef2, emailRef1, emailRef2, telRef1, telRef2));   
                
                codFornCliente = cod;
            }

            fatt.setMovimenti(ricMovimenti(numero, dataFattura, codFornCliente, Fattura.tipo.VEN));
            fatture.add(fatt);
        }
    }
    
    private static void ricNota(boolean recuperaCliente, Fornitore cliente, List<Fattura> fatture) throws SQLException {
            
        int id;
        Integer numero;
        Date data;
        String metodoPagamento;
        Double imponibile;
        Double ivaTot;
        Double totale;
        boolean pagata;
        String note;
        Date dataPagamento;
        Date dataScadenza;

        while (rs.next()){
            id = rs.getInt(Tabelle.NoteCredito.ID);
            numero = rs.getInt(Tabelle.NoteCredito.NUMERO);
            data = rs.getDate(Tabelle.NoteCredito.DATA);
            metodoPagamento = rs.getString(Tabelle.NoteCredito.METODO_PAGAMENTO);
            imponibile = rs.getDouble(Tabelle.NoteCredito.IMPONIBILE);
            ivaTot = rs.getDouble(Tabelle.NoteCredito.IVA);
            totale = rs.getDouble(Tabelle.NoteCredito.TOTALE);
            pagata = rs.getBoolean(Tabelle.NoteCredito.PAGATA);
            note = rs.getString(Tabelle.NoteCredito.NOTE);            
            dataPagamento = rs.getDate(Tabelle.NoteCredito.DATA_PAGAMENTO);
            dataScadenza = rs.getDate(Tabelle.NoteCredito.DATA_SCADENZA);
            
            int codFornCliente = 0;
            if (recuperaCliente) { //Recupera i dati relativi al cliente

                Integer cod = rs.getInt(Tabelle.Fornitori.COD);
                String nome = rs.getString(Tabelle.Fornitori.NOME);
                String titolare = rs.getString(Tabelle.Fornitori.TITOLARE);
                String piva = rs.getString(Tabelle.Fornitori.PIVA);
                String codfisc = rs.getString(Tabelle.Fornitori.CODFISCALE);
                String indirizzoLeg = rs.getString(Tabelle.Fornitori.INDIRIZZO_LEGALE);
                String telefono1 = rs.getString(Tabelle.Fornitori.TELEFONO1);
                String telefono2 = rs.getString(Tabelle.Fornitori.TELEFONO2);
                String fax = rs.getString(Tabelle.Fornitori.FAX);
                String email = rs.getString(Tabelle.Fornitori.EMAIL);
                String capLeg = rs.getString(Tabelle.Fornitori.CAP_LEGALE);
                String cittaLeg = rs.getString(Tabelle.Fornitori.CITTA_LEGALE);
                String provLeg = rs.getString(Tabelle.Fornitori.PROV_LEGALE);
                String nazioneLeg = rs.getString(Tabelle.Fornitori.NAZIONE_LEGALE);
                String banca = rs.getString(Tabelle.Fornitori.BANCA);
                String iban = rs.getString(Tabelle.Fornitori.IBAN);
                String nomeRef1 = rs.getString(Tabelle.Fornitori.NOME_REF_1);
                String nomeRef2 = rs.getString(Tabelle.Fornitori.NOME_REF_2);
                String emailRef1 = rs.getString(Tabelle.Fornitori.EMAIL_REF_1);
                String emailRef2 = rs.getString(Tabelle.Fornitori.EMAIL_REF_2);
                String telRef1 = rs.getString(Tabelle.Fornitori.TEL_REF_1);
                String telRef2 = rs.getString(Tabelle.Fornitori.TEL_REF_2);
                String iscrizioneAlbo = rs.getString(Tabelle.Fornitori.ISCRIZIONE_ALBO);
                String indirizzoOp = rs.getString(Tabelle.Fornitori.INDIRIZZO_OP);
                String capOp = rs.getString(Tabelle.Fornitori.CAP_OP);
                String cittaOp = rs.getString(Tabelle.Fornitori.CITTA_OP);
                String provOp = rs.getString(Tabelle.Fornitori.PROV_OP);
                String nazioneOp = rs.getString(Tabelle.Fornitori.NAZIONE_OP);

                cliente = new Fornitore(cod, nome, titolare, piva, codfisc, indirizzoLeg, telefono1, telefono2, fax, email, capLeg, cittaLeg, provLeg, nazioneLeg, banca, iban,
                        iscrizioneAlbo, indirizzoOp, cittaOp, capOp, provOp, nazioneOp, nomeRef1, nomeRef2, emailRef1, emailRef2, telRef1, telRef2);   
                
                codFornCliente = cod;
            }

            NotaCredito nota = new NotaCredito(numero, data, cliente, metodoPagamento, imponibile, ivaTot, totale, null, pagata, note, dataPagamento, dataScadenza);
            nota.setId(id);
            nota.setDescrizioni(ricDescrizioni(nota));           
            nota.setMovimenti(ricMovimenti(numero, data, codFornCliente, Fattura.tipo.VNC));                        
            fatture.add(nota);
        }
    }
    
    /**
     * Recupera tutte le fatture che rispecchiano i parametri in input
     * @param anno L'anno da considerare
     * @param cliente Il cliente relativo
     * @param mesi L'insieme dei mesi di cui recuperarne le fatture
     * @param tipo Il tipo delle fatture da recuperare (pagate, non pagate, tutte)
     * @return Una lista di fatture, una lista vuota nel caso in cui non fosse stato selezionato alcun mese,
     * o null in caso di eventuali errori.
     */
    public static List<Fattura> getFatture(int anno, Fornitore cliente, boolean mesi[], Fattura.pagata tipo, Date dataIntIn, Date dataIntFin, String dateFieldToFilter) {
        
        /*
         * La stringa sql verrà costruita dinamicamente in base alle informazioni
         * presenti nella firma del metodo
         */
        boolean intervalloDate = false;
        boolean recuperaCliente;
        
        if (dataIntIn != null) {
            intervalloDate = true;
        }
        if (cliente.getCod() != null) {//Cliente selezionato
            sql = "SELECT DISTINCT " + Tabelle.FATTURE + ".* FROM " + Tabelle.FATTURE + " JOIN " + Tabelle.SPEDIZIONI + " ON " +
                Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.Spedizioni.NUM_FATTURA + " AND " +  Tabelle.Fatture.DATA + " = " +
                    Tabelle.Spedizioni.DATA_FATTURA + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + cliente.getCod() + " AND (";
            
            recuperaCliente = false;
            
        } else {//Tutti i clienti
            sql = "SELECT DISTINCT " + Tabelle.FATTURE + ".*, " + Tabelle.FORNITORI + ".* FROM " + Tabelle.FATTURE + ", " + Tabelle.SPEDIZIONI + ", " + 
                    Tabelle.FORNITORI + " WHERE " + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.SPEDIZIONI + "." + 
                    Tabelle.Spedizioni.NUM_FATTURA + " AND " +  Tabelle.Fatture.DATA + " = " + Tabelle.Spedizioni.DATA_FATTURA + " AND " + 
                    Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND (";
            
            recuperaCliente = true;
        }
     
        List<Fattura> fatture = new LinkedList<Fattura>();
        
        if (tipo == Fattura.pagata.P) 
            sql += Tabelle.Fatture.PAGATA + " = true AND ("; 
        else if (tipo == Fattura.pagata.NP) 
            sql += Tabelle.Fatture.PAGATA + " = false AND ("; 
        
        int j = 0;
        for (boolean mese : mesi) {
            if (mese)
                j++; //numero di mesi posti a true
        }
        
        if (!intervalloDate){
            if (j > 0 && j < 12) { //Sono stati selezionati solo alcuni mesi
                for (int i = 0; i < mesi.length; i++) {
                    if (mesi[i]) {
                        String m = String.valueOf(i + 1);
                        if (m.length() == 1)
                            m = "0" + m;

                        sql += "(" + Tabelle.Fatture.DATA + " BETWEEN '" + anno + "-" + m + "-01' AND '" + anno + "-" + m + "-31') OR "; 
                    }
                }

            } else if (j == 12) //Sono stati selezionati tutti i mesi
                sql += Tabelle.Fatture.DATA + " BETWEEN '" + anno + "-01-01' AND '" + anno + "-12-31' AND "; 

            else //non è stato selezionato alcun mese
                sql = "";
        } else
            sql += "(" + dateFieldToFilter + " BETWEEN '" + dataIntIn + "' AND '" + dataIntFin + "') OR "; 
            
        if (!sql.equalsIgnoreCase("")) { //E' stato selezionato qualche mese
            if (sql.substring(sql.length()-4).equalsIgnoreCase("AND "))
                sql = sql.substring(0, sql.length()-4).trim(); //Rimuove un eventuale AND finale
            
            if (sql.substring(sql.length()-3).equalsIgnoreCase("OR "))
                sql = sql.substring(0, sql.length()-3).trim(); //Rimuove un eventuale OR finale
            
            //Se si fa la distinzione tra fatture pagate e non, bisogna chiudere la parentesi che era stata aperta all'inizio
            if (tipo != Fattura.pagata.ALL)
                sql += ")";
            
            sql += ") ORDER BY " + Tabelle.Fatture.DATA + ", " + Tabelle.Fatture.NUMERO;
            
            try {
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                ricFatt(recuperaCliente, cliente, fatture);
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }   
        addNoteCredito(fatture, anno, cliente, mesi, tipo, dataIntIn, dataIntFin, dateFieldToFilter);
        return fatture;
    }
    
    /**
     * Recupera tutte le note credito che rispecchiano i parametri in input
     * @param anno L'anno da considerare
     * @param cliente Il cliente relativo
     * @param mesi L'insieme dei mesi di cui recuperarne le fatture
     * @param tipo Il tipo delle note da recuperare (pagate, non pagate, tutte)
     * @return Una lista di note, una lista vuota nel caso in cui non fosse stato selezionato alcun mese,
     * o null in caso di eventuali errori.
     */
    private static void addNoteCredito(List<Fattura> fatture,int anno, Fornitore cliente, boolean mesi[], Fattura.pagata tipo, Date dataIntIn, Date dataIntFin, String dateFieldToFilter) {
        
        /*
         * La stringa sql verrà costruita dinamicamente in base alle informazioni
         * presenti nella firma del metodo
         */
        boolean intervalloDate = false;
        boolean recuperaCliente;
        
        if (dataIntIn != null) {
            intervalloDate = true;
        }
        if (cliente.getCod() != null) {//Cliente selezionato
            sql = "SELECT DISTINCT " + 
                    Tabelle.NOTE_CREDITO + ".* FROM " + 
                    Tabelle.NOTE_CREDITO + 
                    " WHERE " + 
                        Tabelle.NoteCredito.CLIENTE + " = " + cliente.getCod() + 
                        " AND (";
            
            recuperaCliente = false;
            
        } else {//Tutti i clienti
            sql = "SELECT DISTINCT " + 
                    Tabelle.NOTE_CREDITO + ".*, " + 
                    Tabelle.FORNITORI + ".* FROM " + 
                        Tabelle.NOTE_CREDITO + ", " +
                        Tabelle.FORNITORI + 
                    " WHERE " +                         
                        Tabelle.NoteCredito.CLIENTE + " = " + Tabelle.Fornitori.COD + " AND (";
            
            recuperaCliente = true;
        }        
        
        if (tipo == Fattura.pagata.P) 
            sql += Tabelle.NoteCredito.PAGATA + " = true AND ("; 
        else if (tipo == Fattura.pagata.NP) 
            sql += Tabelle.NoteCredito.PAGATA + " = false AND ("; 
        
        int j = 0;
        for (boolean mese : mesi) {
            if (mese)
                j++; //numero di mesi posti a true
        }
        
        if (!intervalloDate){
            if (j > 0 && j < 12) { //Sono stati selezionati solo alcuni mesi
                for (int i = 0; i < mesi.length; i++) {
                    if (mesi[i]) {
                        String m = String.valueOf(i + 1);
                        if (m.length() == 1)
                            m = "0" + m;

                        sql += "(" + Tabelle.NoteCredito.DATA + " BETWEEN '" + anno + "-" + m + "-01' AND '" + anno + "-" + m + "-31') OR "; 
                    }
                }

            } else if (j == 12) //Sono stati selezionati tutti i mesi
                sql += Tabelle.NoteCredito.DATA + " BETWEEN '" + anno + "-01-01' AND '" + anno + "-12-31' AND "; 

            else //non è stato selezionato alcun mese
                sql = "";
        } else
            sql += "(" + dateFieldToFilter + " BETWEEN '" + dataIntIn + "' AND '" + dataIntFin + "') OR "; 
            
        if (!sql.equalsIgnoreCase("")) { //E' stato selezionato qualche mese
            if (sql.substring(sql.length()-4).equalsIgnoreCase("AND "))
                sql = sql.substring(0, sql.length()-4).trim(); //Rimuove un eventuale AND finale
            
            if (sql.substring(sql.length()-3).equalsIgnoreCase("OR "))
                sql = sql.substring(0, sql.length()-3).trim(); //Rimuove un eventuale OR finale
            
            //Se si fa la distinzione tra fatture pagate e non, bisogna chiudere la parentesi che era stata aperta all'inizio
            if (tipo != Fattura.pagata.ALL)
                sql += ")";
            
            sql += ") ORDER BY " + Tabelle.NoteCredito.DATA + ", " + Tabelle.NoteCredito.NUMERO;
            
            try {
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                ricNota(recuperaCliente, cliente, fatture);
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
     /*
     * Restituisce tutte i movimenti appartenenti ad una fattura
     */
    private static List<Movimento> ricMovimenti(int doc, Date dataDoc, int fornCliente, Fattura.tipo tipoDoc) throws SQLException {
        
        sql = "SELECT * FROM " + Tabelle.MOVIMENTI + " WHERE " + Tabelle.Movimenti.NUM_DOC + " = " + doc + " AND " + 
                Tabelle.Movimenti.DATA + " = '" + dataDoc + "' AND " + Tabelle.Movimenti.TIPO + " = '" + tipoDoc.toString() + "' AND " +
                Tabelle.Movimenti.FORN_CLIENTE + " = " + fornCliente;

        System.out.println(sql);
        PreparedStatement psMov = conn.prepareStatement(sql);
        ResultSet rsMov = psMov.executeQuery();
        
        List<Movimento> movimenti = new LinkedList<Movimento>();
        
        try {
            
            Integer id;
            Integer numDoc;
            Date data;
            String tipo;
            String metPag;
            Double valore;
            Integer fornClient;
            
            while (rsMov.next()){
                id = rsMov.getInt(Tabelle.Movimenti.ID);
                numDoc = rsMov.getInt(Tabelle.Movimenti.NUM_DOC);
                data = rsMov.getDate(Tabelle.Movimenti.DATA);
                tipo = rsMov.getString(Tabelle.Movimenti.TIPO);
                metPag = rsMov.getString(Tabelle.Movimenti.MET_PAG);
                valore = rsMov.getDouble(Tabelle.Movimenti.VALORE);
                fornClient = rsMov.getInt(Tabelle.Movimenti.FORN_CLIENTE);
                
                movimenti.add(new Movimento(id, numDoc, data, tipo, metPag, valore, fornClient));
            }
                       
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return movimenti;
    }
    
    /*
     * Restituisce tutte le spedizioni appartenenti ad una fattura
     */
    private static List<Spedizione> ricSpedizioni(int fattura, Date dataFattura) throws SQLException {
        
        sql = "SELECT * FROM " + Tabelle.SPEDIZIONI + " WHERE " + Tabelle.Spedizioni.NUM_FATTURA + " = " + fattura + " AND " + 
                Tabelle.Spedizioni.DATA_FATTURA + " = '" + dataFattura + "' ORDER BY " + Tabelle.Spedizioni.STATO + ", " +
                Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;

        System.out.println(sql);
        PreparedStatement psSped = conn.prepareStatement(sql);
        ResultSet rsSped = psSped.executeQuery();
                  
        List<Spedizione> spedizioni = new LinkedList<Spedizione>();
        
        try {
            String numero;
            Date dataCarico;
            Date dataDocumento;
            String descrizione;
            Integer forn;
            //String mezzo; Il mezzo non viene recuperato perché non è necessario per la stampa
            String um1;
            Double qta1;
            Double traz1;
            Double distrib1;
            String um2;
            Double qta2;
            Double traz2;
            Double distrib2;
            Double importo;
            Integer sconto;
            Integer percIva;
            Double iva;
            Double percProvvigione;
            Double provvigione;
            Double totale;
            String note;
            boolean rientrata;
            Double valoreMerce;
            Double imponibile;
            char stato;

            while (rsSped.next()){
                numero = rsSped.getString(Tabelle.Spedizioni.NUMERO);
                dataCarico = rsSped.getDate(Tabelle.Spedizioni.DATA_CARICO);
                dataDocumento = rsSped.getDate(Tabelle.Spedizioni.DATA_DOCUMENTO);
                descrizione = rsSped.getString(Tabelle.Spedizioni.DESCRIZIONE);
                forn = rsSped.getInt(Tabelle.Spedizioni.FORN_CLIENTE);
                //mezzo = rsSped.getString(Tabelle.Mezzi.TARGA);
                um1 = rsSped.getString(Tabelle.Spedizioni.UM1);
                qta1 = rsSped.getDouble(Tabelle.Spedizioni.QTA1);
                traz1 = rsSped.getDouble(Tabelle.Spedizioni.TRAZ1);
                distrib1 = rsSped.getDouble(Tabelle.Spedizioni.DISTRIB1);
                um2 = rsSped.getString(Tabelle.Spedizioni.UM2);
                qta2 = rsSped.getDouble(Tabelle.Spedizioni.QTA2);
                traz2 = rsSped.getDouble(Tabelle.Spedizioni.TRAZ2);
                distrib2 = rsSped.getDouble(Tabelle.Spedizioni.DISTRIB2);
                importo = rsSped.getDouble(Tabelle.Spedizioni.IMPORTO);
                note = rsSped.getString(Tabelle.Spedizioni.NOTE);
                percIva = rsSped.getInt(Tabelle.Spedizioni.PERC_IVA);
                iva = rsSped.getDouble(Tabelle.Spedizioni.IVA);
                sconto = rsSped.getInt(Tabelle.Spedizioni.SCONTO);
                percProvvigione = rsSped.getDouble(Tabelle.Spedizioni.PERC_PROVV);
                provvigione = rsSped.getDouble(Tabelle.Spedizioni.IMPORTO_PROVV);
                totale = rsSped.getDouble(Tabelle.Spedizioni.TOTALE);
                rientrata = rsSped.getBoolean(Tabelle.Spedizioni.RIENTRATA);
                valoreMerce = rsSped.getDouble(Tabelle.Spedizioni.VALORE_MERCE);
                imponibile = rsSped.getDouble(Tabelle.Spedizioni.IMPONIBILE);
                stato = rsSped.getString(Tabelle.Spedizioni.STATO).charAt(0);
                        
                Spedizione sped = new Spedizione(numero, forn, dataCarico, dataDocumento, descrizione, null, 
                        um1, qta1, traz1, distrib1, importo, sconto, percIva, iva, 
                        percProvvigione, provvigione, totale, note, rientrata, fattura, dataFattura, valoreMerce, imponibile, stato,
                        um2, qta2, traz2, distrib2);

                psSped = conn.prepareStatement("SELECT " + Tabelle.Bolle.BOLLA + " FROM " + Tabelle.BOLLE + " WHERE " + 
                        Tabelle.Bolle.SPEDIZIONE + " = '" + numero + "' AND " + Tabelle.Bolle.DATA_SPEDIZIONE + " = '" + dataCarico + "'");

                ResultSet rsbolle = psSped.executeQuery();
                while (rsbolle.next()) {
                    sped.addBolla(rsbolle.getString(Tabelle.Bolle.BOLLA));
                }

                spedizioni.add(sped);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return spedizioni;
    }
    
    /*
     * Restituisce tutte le spedizioni appartenenti ad una nota credito
     */
    private static List<DescrizioniNotaCredito> ricDescrizioni(NotaCredito nota) throws SQLException {
        
        sql = "SELECT * FROM " + 
                Tabelle.DESCRIZIONI_NOTE_CREDITO + 
                " WHERE " + 
                    Tabelle.DescrizioniNoteCredito.NOTA_CREDITO + " = " + nota.getId() + 
                    " ORDER BY " + Tabelle.DescrizioniNoteCredito.ID;

        System.out.println(sql);
        PreparedStatement psSped = conn.prepareStatement(sql);
        ResultSet rsSped = psSped.executeQuery();
                  
        List<DescrizioniNotaCredito> descrizioni = new LinkedList<DescrizioniNotaCredito>();
        
        try {
            String descrizione;
            Double importo;
            Integer percIva;
            double iva;
                     
            while (rsSped.next()){
                descrizione = rsSped.getString(Tabelle.DescrizioniNoteCredito.DESCRIZIONE);
                importo = rsSped.getDouble(Tabelle.DescrizioniNoteCredito.IMPORTO);
                percIva = rsSped.getInt(Tabelle.DescrizioniNoteCredito.PERC_IVA);
                iva = rsSped.getDouble(Tabelle.DescrizioniNoteCredito.IVA);
                                 
                DescrizioniNotaCredito desc = new DescrizioniNotaCredito(descrizione, importo, percIva, iva);
                desc.setNota(nota);
                descrizioni.add(desc);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return descrizioni;
    }
    
    public static boolean deleteNotaCredito(NotaCredito notaCredito) {
        try {
            
            conn.setAutoCommit(false);
            
            sql = "DELETE FROM " + 
                    Tabelle.NOTE_CREDITO + 
                    " WHERE " + 
                        Tabelle.NoteCredito.ID + " = " + notaCredito.getId();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "DELETE FROM " + 
                    Tabelle.MOVIMENTI + 
                    " WHERE " + 
                        Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.VNC + 
                        "' AND " + Tabelle.Movimenti.NUM_DOC + " = " + notaCredito.getNumero() + 
                        " AND " + Tabelle.Movimenti.DATA + " = '" + notaCredito.getData() + 
                        "' AND " + Tabelle.Movimenti.FORN_CLIENTE + " = " + notaCredito.getCliente().getCod();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "DELETE FROM " +
                    Tabelle.DESCRIZIONI_NOTE_CREDITO + 
                    " WHERE " + 
                        Tabelle.DescrizioniNoteCredito.NOTA_CREDITO + " = " + notaCredito.getId();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }
    
    public static boolean deleteFattura(Fattura fattura) {
        try {
            
            conn.setAutoCommit(false);
            
            sql = "DELETE FROM " + Tabelle.FATTURE + " WHERE " + Tabelle.Fatture.NUMERO + " = " + fattura.getNumero() + " AND " + Tabelle.Fatture.DATA +
                    " = '" + fattura.getData() + "'";
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "DELETE FROM " + Tabelle.MOVIMENTI + " WHERE " + Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.VEN +
                    "' AND " + Tabelle.Movimenti.NUM_DOC + " = " + fattura.getNumero() + 
                    " AND " + Tabelle.Movimenti.DATA + " = '" + fattura.getData() + "' AND " + Tabelle.Movimenti.FORN_CLIENTE +
                    " = " + fattura.getCliente().getCod();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "UPDATE " + Tabelle.SPEDIZIONI + " SET " + Tabelle.Spedizioni.NUM_FATTURA + " = NULL, " + Tabelle.Spedizioni.DATA_FATTURA +
                    " = NULL WHERE " + Tabelle.Spedizioni.NUM_FATTURA + " = " + fattura.getNumero() + " AND " + Tabelle.Spedizioni.DATA_FATTURA +
                    " = '" + fattura.getData() + "'";
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }
    
    /*
     * Inserisce una fattura d'acquisto all'interno del database
     */
    public static boolean insertFattAcquisto(Fattura fatt) {
        try {
//           Togliendo i commenti si avrà il controllo sul numero fattura già inserita nell'anno
            
//            int anno = Integer.parseInt((fatt.getData().toString().split("-"))[0]);
            
            //sql = "SELECT COUNT(*) AS num FROM " + Tabelle.FATT_ACQUISTO + " WHERE " + Tabelle.FattureAcquisto.NUMERO + " = " + 
            //        fatt.getNumero() + " AND " + Tabelle.FattureAcquisto.DATA + " BETWEEN '" + anno + "-01-01' AND '" + anno + "-12-31'";
            
            //System.out.println(sql);
            //ps = conn.prepareStatement(sql);
            //rs = ps.executeQuery();
            
            //rs.next();
//            int num = rs.getInt("num");
//            if (num == 0) { //numero nell'anno non esistente

                sql = "INSERT INTO " + Tabelle.FATT_ACQUISTO + " VALUES (" + fatt.getNumero() + ", " + checkNull(fatt.getData()) + ", " + checkNull(fatt.getMetPag()) + ", " + fatt.getImporto() + ", " + 
                                            fatt.getSconto() + ", " + fatt.getIva() + ", " + fatt.getTotale() + ", " + fatt.getPagata() + ", '" + fatt.getTipo() + "', " + checkNull(fatt.getNote()) +
                                            ", " + fatt.getFornitore() + ", '" + fatt.getDataScadenza() + "' , " + checkNull(fatt.getSpecificaNumero()) + ", NULL)";

                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

                return true;
                
//            } else { //numero nell'anno esistente
//                throw new EccezioneChiaveDuplicata("Numero fattura '" + fatt.getNumero() + "' esistente per l'anno in questione");
//            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getErrorCode() == DATA_TOO_LONG) {
                String errorMsg = ex.getMessage();
                if (errorMsg.contains(Tabelle.FattureAcquisto.METODO_PAGAMENTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Metodo di pagamento troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.IMPORTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Importo troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.SCONTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Sconto troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.IVA))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IVA troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.TOTALE))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Totale troppo lungo!");
                
            } else if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                throw new EccezioneChiaveDuplicata("Numero fattura '" + fatt.getNumero() + "' esistente per la data in questione");
            }
            return false;
        } 
    }
    
    private static void ricFattAcq(boolean recuperaForn, Fornitore fornitore, List<Fattura> fatture) throws SQLException {
        
        Integer numero;
        Date dataFattura;
        String metodoPagamento;
        Double importo;
        Double sconto;
        Double ivaTot;
        Double totale;
        boolean pagata;
        String note;
        String tipoAcquisto;
        int codForn;
        Date dataScadenza;
        String specificaNumero;
        Date dataPagamento;

        while (rs.next()){
            numero = rs.getInt(Tabelle.FattureAcquisto.NUMERO);
            dataFattura = rs.getDate(Tabelle.FattureAcquisto.DATA);
            metodoPagamento = rs.getString(Tabelle.FattureAcquisto.METODO_PAGAMENTO);
            importo = rs.getDouble(Tabelle.FattureAcquisto.IMPORTO);
            sconto = rs.getDouble(Tabelle.FattureAcquisto.SCONTO);
            ivaTot = rs.getDouble(Tabelle.FattureAcquisto.IVA);
            totale = rs.getDouble(Tabelle.FattureAcquisto.TOTALE);
            pagata = rs.getBoolean(Tabelle.FattureAcquisto.PAGATA);
            tipoAcquisto = rs.getString(Tabelle.FattureAcquisto.TIPO);
            codForn = rs.getInt(Tabelle.FattureAcquisto.FORNITORE);
            note = rs.getString(Tabelle.FattureAcquisto.NOTE);
            dataScadenza = rs.getDate(Tabelle.FattureAcquisto.SCADENZA);
            specificaNumero = rs.getString(Tabelle.FattureAcquisto.SPECIFICA_NUMERO);
            dataPagamento = rs.getDate(Tabelle.FattureAcquisto.PAGAMENTO);
            
            Fattura fatt = new Fattura(numero, dataFattura, metodoPagamento, importo, sconto, ivaTot, totale, tipoAcquisto, pagata, codForn, note,
                    dataScadenza, specificaNumero, dataPagamento);

            int codFornCliente = 0;
            if (!recuperaForn)
                fatt.setCliente(fornitore);

            else { //Recupera i dati relativi al cliente

                Integer cod = rs.getInt(Tabelle.Fornitori.COD);
                String nome = rs.getString(Tabelle.Fornitori.NOME);
                String titolare = rs.getString(Tabelle.Fornitori.TITOLARE);
                String piva = rs.getString(Tabelle.Fornitori.PIVA);
                String codfisc = rs.getString(Tabelle.Fornitori.CODFISCALE);
                String indirizzoLeg = rs.getString(Tabelle.Fornitori.INDIRIZZO_LEGALE);
                String telefono1 = rs.getString(Tabelle.Fornitori.TELEFONO1);
                String telefono2 = rs.getString(Tabelle.Fornitori.TELEFONO2);
                String fax = rs.getString(Tabelle.Fornitori.FAX);
                String email = rs.getString(Tabelle.Fornitori.EMAIL);
                String capLeg = rs.getString(Tabelle.Fornitori.CAP_LEGALE);
                String cittaLeg = rs.getString(Tabelle.Fornitori.CITTA_LEGALE);
                String provLeg = rs.getString(Tabelle.Fornitori.PROV_LEGALE);
                String nazioneLeg = rs.getString(Tabelle.Fornitori.NAZIONE_LEGALE);
                String banca = rs.getString(Tabelle.Fornitori.BANCA);
                String iban = rs.getString(Tabelle.Fornitori.IBAN);
                String iscrizioneAlbo = rs.getString(Tabelle.Fornitori.ISCRIZIONE_ALBO);
                String indirizzoOp = rs.getString(Tabelle.Fornitori.INDIRIZZO_OP);
                String capOp = rs.getString(Tabelle.Fornitori.CAP_OP);
                String cittaOp = rs.getString(Tabelle.Fornitori.CITTA_OP);
                String provOp = rs.getString(Tabelle.Fornitori.PROV_OP);
                String nazioneOp = rs.getString(Tabelle.Fornitori.NAZIONE_OP);

                fatt.setCliente(new Fornitore(cod, nome, titolare, piva, codfisc, indirizzoLeg, telefono1, telefono2, fax, email, capLeg, cittaLeg, provLeg, nazioneLeg, banca, iban, iscrizioneAlbo,
                        indirizzoOp, cittaOp, capOp, provOp, nazioneOp));   
                
                codFornCliente = cod;
            }

            fatt.setMovimenti(ricMovimenti(numero, dataFattura, codFornCliente, Fattura.tipo.ACQ));
            fatture.add(fatt);
        }
    }
    
    /**
     * Recupera tutte le fatture che rispecchiano i parametri in input
     * @param anno L'anno da considerare
     * @param cliente Il cliente relativo
     * @param mesi L'insieme dei mesi di cui recuperarne le fatture
     * @param tipo Il tipo delle fatture da recuperare (pagate, non pagate, tutte)
     * @param tipoFatt  Il tipo di fattura da recuperare (terzi, stipendi...)
     * @return Una lista di fatture, una lista vuota nel caso in cui non fosse stato selezionato alcun mese,
     * o null in caso di eventuali errori.
     */
    public static List<Fattura> getFattureAcquisto(int anno, Fornitore fornitore, boolean mesi[], Fattura.pagata tipo, String tipoFatt, Date dataIntIn, Date dataIntFin, String dateFieldToFilter) {
        
        /*
         * La stringa sql verrà costruita dinamicamente in base alle informazioni
         * presenti nella firma del metodo
         */
        boolean recuperaForn;
        boolean intervalloDate = false;
        
        if (dataIntIn != null) {
            intervalloDate = true;
        }
            
        if (fornitore.getCod() != null) {//Cliente selezionato
            sql = "SELECT DISTINCT " + Tabelle.FATT_ACQUISTO + ".* FROM " + Tabelle.FATT_ACQUISTO +
                    " WHERE " + Tabelle.FattureAcquisto.FORNITORE + " = " + fornitore.getCod() + " AND (";
            
            recuperaForn = false;
            
        } else {//Tutti i clienti
            sql = "SELECT DISTINCT " + Tabelle.FATT_ACQUISTO + ".*, " + Tabelle.FORNITORI + ".* FROM " + Tabelle.FATT_ACQUISTO + " JOIN " + 
                    Tabelle.FORNITORI + " ON " + Tabelle.FattureAcquisto.FORNITORE + " = " + Tabelle.Fornitori.COD +
                    " AND (";
            
            recuperaForn = true;
        }
        
        if (!(tipoFatt.equalsIgnoreCase("all")))
            sql += Tabelle.FattureAcquisto.TIPO + " = '" + tipoFatt + "' AND (";
        
        List<Fattura> fatture = new LinkedList<Fattura>();
        
        if (tipo == Fattura.pagata.P) 
            sql += Tabelle.FattureAcquisto.PAGATA + " = true AND ("; 
        else if (tipo == Fattura.pagata.NP) 
            sql += Tabelle.FattureAcquisto.PAGATA + " = false AND ("; 
        
        int j = 0;
        for (boolean mese : mesi) {
            if (mese)
                j++; //numero di mesi posti a true
        }
        
        if (!intervalloDate){
            if (j > 0 && j < 12) { //Sono stati selezionati solo alcuni mesi
                for (int i = 0; i < mesi.length; i++) {
                    if (mesi[i]) {
                        String m = String.valueOf(i + 1);
                        if (m.length() == 1)
                            m = "0" + m;

                        sql += "(" + Tabelle.FattureAcquisto.DATA + " BETWEEN '" + anno + "-" + m + "-01' AND '" + anno + "-" + m + "-31') OR "; 
                    }
                }

            } else if (j == 12) //Sono stati selezionati tutti i mesi
                sql += Tabelle.FattureAcquisto.DATA + " BETWEEN '" + anno + "-01-01' AND '" + anno + "-12-31' AND "; 

            else //non è stato selezionato alcun mese
                sql = "";
        } else 
            sql += "(" + dateFieldToFilter + " BETWEEN '" + dataIntIn + "' AND '" + dataIntFin + "') OR "; 
        
        
        if (!sql.equalsIgnoreCase("")) { //E' stato selezionato qualche mese
            if (sql.substring(sql.length()-4).equalsIgnoreCase("AND "))
                sql = sql.substring(0, sql.length()-4).trim(); //Rimuove un eventuale AND finale
            
            if (sql.substring(sql.length()-3).equalsIgnoreCase("OR "))
                sql = sql.substring(0, sql.length()-3).trim(); //Rimuove un eventuale OR finale
            
            //Se si fa la distinzione tra fatture pagate e non, bisogna chiudere la parentesi che era stata aperta all'inizio
            if (tipo != Fattura.pagata.ALL)
                sql += ")";
            
            if (!(tipoFatt.equalsIgnoreCase("all")))
                sql += ")";
            
            sql += ") ORDER BY " + Tabelle.FattureAcquisto.DATA + ", " + Tabelle.FattureAcquisto.NUMERO;
            
            try {
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                ricFattAcq(recuperaForn, fornitore, fatture);

            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }   
        return fatture;
    }
    
    /*
     * Controlla che il singolo campo contenga una stringa vuota. In tal caso imposta il valore NULL
     */
    private static String checkNull(Object campo) {
        if (campo == null) {
            campo = "NULL";
            
        } else if (campo instanceof String) {
            if (((String)campo).isEmpty()) { //se il campo == "" lo imposta al valore NULL
                campo = "NULL";
          
            } else { //altrimenti aggiunge gli apici ' all'inizio e alla fine della stringa
                campo = ((String)campo).replaceAll("'", "''");
                campo = ((String)campo).replaceAll("\\\\", "\\\\\\\\");
                char[] field = ((String) campo).toCharArray();
                char[] newField = new char[field.length + 2];
                newField[0] = '\'';
                newField[newField.length -1] = '\'';
                System.arraycopy(field, 0, newField, 1, field.length);
                campo = new String(newField);
            }
                            
        } else if (campo instanceof Integer) {
            campo = Integer.toString((Integer) campo);
            
        } else if (campo instanceof Date) {
            char[] field = ((Date)campo).toString().toCharArray();
            char[] newField = new char[field.length + 2];
            newField[0] = '\'';
            newField[newField.length -1] = '\'';
            System.arraycopy(field, 0, newField, 1, field.length);
            campo = new String(newField);
        } 
        return (String) campo;
    }

    public static void updatePagamentoFattura(Fattura.tipo tipo, Fattura fattura, boolean pagata, List<Movimento> movimenti) {
        try {
           
            conn.setAutoCommit(false);
            if (!pagata)
                fattura.setDataPagamento(null);
            
            if (tipo == Fattura.tipo.ACQ) {
                sql = "UPDATE " + Tabelle.FATT_ACQUISTO + " SET " + Tabelle.FattureAcquisto.PAGATA + " = " + pagata + 
                        ", " + Tabelle.FattureAcquisto.PAGAMENTO + " = " + checkNull(fattura.getDataPagamento()) + 
                        " WHERE " + Tabelle.FattureAcquisto.NUMERO + " = " + fattura.getNumero() + " AND " + 
                        Tabelle.FattureAcquisto.DATA + " = '" + fattura.getData() + "'" + " AND " + Tabelle.FattureAcquisto.FORNITORE + 
                        " = " + fattura.getCliente().getCod();    
                
            } else if (tipo == Fattura.tipo.VEN) {
                sql = "UPDATE " + Tabelle.FATTURE + " SET " + Tabelle.Fatture.PAGATA + " = " + pagata +
                        ", " + Tabelle.Fatture.PAGAMENTO + " = " + checkNull(fattura.getDataPagamento()) + 
                        " WHERE " + Tabelle.Fatture.NUMERO + " = " + fattura.getNumero() + " AND " + 
                        Tabelle.Fatture.DATA + " = '" + fattura.getData() + "'";
                
            } else if (tipo == Fattura.tipo.VNC) {
                sql = "UPDATE " +
                        Tabelle.NOTE_CREDITO +
                        " SET " + Tabelle.NoteCredito.PAGATA + " = " + pagata +
                        ", " + Tabelle.NoteCredito.DATA_PAGAMENTO + " = " + checkNull(fattura.getDataPagamento()) +
                        " WHERE " + Tabelle.NoteCredito.ID + " = " + ((NotaCredito)fattura).getId();
            }
               
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            sql = "DELETE FROM " + Tabelle.MOVIMENTI + " WHERE " + Tabelle.Movimenti.NUM_DOC + " = " + fattura.getNumero() + " AND " +
                    Tabelle.Movimenti.DATA + " = '" + fattura.getData() + "' AND " + Tabelle.Movimenti.TIPO + " = '" + tipo + "' AND " +
                    Tabelle.Movimenti.FORN_CLIENTE + " = " + fattura.getCliente().getCod();

            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
                
            if (movimenti != null) {

                for (Movimento movimento : movimenti) {
                    sql = "INSERT INTO " + Tabelle.MOVIMENTI + " VALUES (NULL, " + movimento.getNumDoc() + ", '" + movimento.getData() + "', '" +
                            movimento.getTipo() + "', '" + movimento.getMetPag() + "', " + movimento.getValore() + ", " + movimento.getFornCliente() + ")";

                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                }
                
            } 
            
            conn.commit();
            conn.setAutoCommit(true);
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 
    }
    
    /*
     * Seleziona i totali fra le fatture emesse e quelle d'acquisto e produce una movimentazione mensile con saldo
     */
    public static List<SaldoContabilitaMensile> getContabilitaFatture(String[] anniMesi, Fattura.pagata tipo, int fornCliente){
        try {
            String sqlEmesse = "";
            String sqlUnionNoteCredito = "";
            String sqlRicevute = "";
            PreparedStatement psEmesse;
            ResultSet rsEmesse;
            PreparedStatement psAcquisto;
            ResultSet rsAcquisto;
            LinkedList<SaldoContabilitaMensile> movimMensili = new LinkedList<SaldoContabilitaMensile>();
            
            for (String meseAnno : anniMesi){
                sqlEmesse = "SELECT DISTINCT " 
                        + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + ", "
                        + Tabelle.FATTURE + "." + Tabelle.Fatture.DATA + ", " + 
                        Tabelle.FATTURE + "." + Tabelle.Fatture.TOTALE +
                        " FROM " 
                        + Tabelle.FATTURE 
                            + " JOIN " + Tabelle.SPEDIZIONI + " ON " +
                            Tabelle.Fatture.DATA + " = " + Tabelle.Spedizioni.DATA_FATTURA 
                            + " AND " + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.Spedizioni.NUM_FATTURA + " WHERE "; 
                
                sqlUnionNoteCredito = "SELECT DISTINCT " 
                        + Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.NUMERO + ", " 
                        + Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.DATA + ", " + 
                        Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.TOTALE + " AS " + Tabelle.Fatture.TOTALE + 
                        " FROM " 
                        + Tabelle.NOTE_CREDITO 
                             + " WHERE ";
                
                sqlRicevute = "SELECT " + Tabelle.FattureAcquisto.TOTALE + " FROM " + Tabelle.FATT_ACQUISTO +  " WHERE "; 
                
                if (fornCliente != -1) {
                    sqlEmesse += Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornCliente + " AND ";
                    sqlRicevute += Tabelle.FattureAcquisto.FORNITORE + " = " + fornCliente + " AND ";
                    sqlUnionNoteCredito += Tabelle.NoteCredito.CLIENTE + " = " + fornCliente + " AND ";
                }  
                if (tipo == Fattura.pagata.P) {
                    sqlEmesse += Tabelle.Fatture.PAGATA + " = true AND ";
                    sqlRicevute += Tabelle.FattureAcquisto.PAGATA + " = true AND ";
                    sqlUnionNoteCredito += Tabelle.NoteCredito.PAGATA + " = true AND ";
                }
                else if (tipo == Fattura.pagata.NP) {
                    sqlEmesse += Tabelle.Fatture.PAGATA + " = false AND ";
                    sqlRicevute += Tabelle.FattureAcquisto.PAGATA + " = false AND "; 
                    sqlUnionNoteCredito += Tabelle.NoteCredito.PAGATA + " = false AND ";
                }
                
                sqlEmesse += Tabelle.Fatture.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                sqlRicevute += Tabelle.FattureAcquisto.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";               
                sqlUnionNoteCredito += Tabelle.NoteCredito.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                                
                System.out.println(sqlEmesse);
                System.out.println(sqlUnionNoteCredito);
                System.out.println(sqlRicevute);
                psEmesse = conn.prepareStatement(sqlEmesse);
                rsEmesse= psEmesse.executeQuery();
                psAcquisto = conn.prepareStatement(sqlRicevute);
                rsAcquisto= psAcquisto.executeQuery();
                
                SaldoContabilitaMensile mese = new SaldoContabilitaMensile(meseAnno);
                while (rsEmesse.next()) {
                    double tot = rsEmesse.getDouble(Tabelle.Fatture.TOTALE);
                    mese.addPos(tot);
                }
                psEmesse = conn.prepareStatement(sqlUnionNoteCredito);
                rsEmesse = psEmesse.executeQuery();
                while (rsEmesse.next()) {
                    double tot = rsEmesse.getDouble(Tabelle.NoteCredito.TOTALE);
                    mese.addPos(tot);
                }
                while (rsAcquisto.next()){
                    double tot = rsAcquisto.getDouble(Tabelle.FattureAcquisto.TOTALE);
                    mese.addNeg(tot);
                }
                mese.setSaldo();
                movimMensili.add(mese);
                
             }
            return movimMensili;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          
        } 
        return null;
    }
    
    /*
     * Seleziona tutte le fatture emesse e produce un bilancio delle fatture incassate, da incassare e saldos
     */
    public static List<BilancioMensile> getBilancioFattureEmesse(String[] anniMesi, int fornCliente){
        try {
            String sql = "";
            String sqlUnionNoteCredito = "";            
            PreparedStatement ps;
            ResultSet rs;                        
            LinkedList<BilancioMensile> bilancio = new LinkedList<BilancioMensile>();
            
            for (String meseAnno : anniMesi){
                sql = "SELECT DISTINCT " 
                        + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + ", "
                        + Tabelle.FATTURE + "." + Tabelle.Fatture.PAGATA + ", "
                        + Tabelle.FATTURE + "." + Tabelle.Fatture.DATA + ", " + 
                        Tabelle.FATTURE + "." + Tabelle.Fatture.TOTALE +
                        " FROM " 
                        + Tabelle.FATTURE 
                            + " JOIN " + Tabelle.SPEDIZIONI + " ON " +
                            Tabelle.Fatture.DATA + " = " + Tabelle.Spedizioni.DATA_FATTURA 
                            + " AND " + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.Spedizioni.NUM_FATTURA + " WHERE "; 
                
                sqlUnionNoteCredito = "SELECT DISTINCT " 
                        + Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.NUMERO + ", " 
                        + Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.PAGATA + ", " 
                        + Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.DATA + ", " + 
                        Tabelle.NOTE_CREDITO + "." + Tabelle.NoteCredito.TOTALE + " AS " + Tabelle.Fatture.TOTALE + 
                        " FROM " 
                        + Tabelle.NOTE_CREDITO 
                             + " WHERE ";
                
                //sqlRicevute = "SELECT " + Tabelle.FattureAcquisto.TOTALE + " FROM " + Tabelle.FATT_ACQUISTO +  " WHERE "; 
                
                if (fornCliente != -1) {
                    sql += Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornCliente + " AND ";
                    //sqlRicevute += Tabelle.FattureAcquisto.FORNITORE + " = " + fornCliente + " AND ";
                    sqlUnionNoteCredito += Tabelle.NoteCredito.CLIENTE + " = " + fornCliente + " AND ";
                }  
                
                sql += Tabelle.Fatture.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                //sqlRicevute += Tabelle.FattureAcquisto.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";               
                sqlUnionNoteCredito += Tabelle.NoteCredito.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                                
                System.out.println(sql);
                System.out.println(sqlUnionNoteCredito);
                //System.out.println(sqlRicevute);
                ps = conn.prepareStatement(sql);
                rs= ps.executeQuery();
                //psAcquisto = conn.prepareStatement(sqlRicevute);
                //rsAcquisto= psAcquisto.executeQuery();
                
                BilancioMensile mese = new BilancioMensile(meseAnno);
                while (rs.next()) {
                    double tot = rs.getDouble(Tabelle.Fatture.TOTALE);
                    boolean pagata = rs.getBoolean(Tabelle.Fatture.PAGATA);
                    mese.setTotale(mese.getTotale() + tot);
                    if(pagata)
                        mese.setTotaleLiquidato(mese.getTotaleLiquidato() + tot);
                    else
                        mese.setTotaleDaLiquidare(mese.getTotaleDaLiquidare() + tot);
                }
                ps = conn.prepareStatement(sqlUnionNoteCredito);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double tot = rs.getDouble(Tabelle.NoteCredito.TOTALE);
                    boolean pagata = rs.getBoolean(Tabelle.NoteCredito.PAGATA);
                    mese.setTotale(mese.getTotale() + tot);
                    if(pagata)
                        mese.setTotaleLiquidato(mese.getTotaleLiquidato() + tot);
                    else
                        mese.setTotaleDaLiquidare(mese.getTotaleDaLiquidare() + tot);
                }
                
//                mese.setSaldo();
                bilancio.add(mese);
                
             }
            return bilancio;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          
        } 
        return null;
    }
    
    /*
     * Seleziona tutte le fatture emesse e produce un bilancio delle fatture incassate, da incassare e saldos
     */
    public static List<BilancioMensile> getBilancioFattureAcquisto(String[] anniMesi, int fornCliente){
        try {
            String sql = "";           
            PreparedStatement ps;
            ResultSet rs;                        
            LinkedList<BilancioMensile> bilancio = new LinkedList<BilancioMensile>();
            
            for (String meseAnno : anniMesi){
                
                sql = "SELECT " + Tabelle.FattureAcquisto.TOTALE + ", " +
                        Tabelle.FattureAcquisto.PAGATA +
                        " FROM " + Tabelle.FATT_ACQUISTO +  " WHERE "; 
                
                if (fornCliente != -1) {                   
                    sql += Tabelle.FattureAcquisto.FORNITORE + " = " + fornCliente + " AND ";                    
                }  
                                
                sql += Tabelle.FattureAcquisto.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";                             
                                
                System.out.println(sql);              
                ps = conn.prepareStatement(sql);
                rs= ps.executeQuery();
                
                BilancioMensile mese = new BilancioMensile(meseAnno);
                while (rs.next()) {
                    double tot = rs.getDouble(Tabelle.FattureAcquisto.TOTALE);
                    boolean pagata = rs.getBoolean(Tabelle.FattureAcquisto.PAGATA);
                    mese.setTotale(mese.getTotale() + tot);
                    if(pagata)
                        mese.setTotaleLiquidato(mese.getTotaleLiquidato() + tot);
                    else
                        mese.setTotaleDaLiquidare(mese.getTotaleDaLiquidare() + tot);
                }                
                
//                mese.setSaldo();
                bilancio.add(mese);
                
             }
            return bilancio;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          
        } 
        return null;
    }
    
    /*
     * Seleziona i valori di pagamento e i loro rispettivi metodo all'interno del periodo prestabilito
     */
    public static List<SaldoCassaMensile> getMovimentazioneCassaMensile(String[] anniMesi, int fornCliente, Fattura.tipo tipoMov){
        try {
            List<SaldoCassaMensile> movimMensili = new LinkedList<SaldoCassaMensile>();
            
            for (String meseAnno : anniMesi){
                if (tipoMov == Fattura.tipo.VEN){
                    sql = "SELECT DISTINCT " +  Tabelle.Movimenti.ID + ", " + Tabelle.Movimenti.MET_PAG + ", " + Tabelle.Movimenti.VALORE + " FROM " + Tabelle.MOVIMENTI +
                                " JOIN " + Tabelle.FATTURE + " ON " + Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.DATA + " = " + Tabelle.FATTURE + "." + Tabelle.Fatture.DATA +
                                  " AND " + Tabelle.Movimenti.NUM_DOC + " = " + Tabelle.Fatture.NUMERO +
                                        " JOIN " + Tabelle.SPEDIZIONI + " ON " + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.SPEDIZIONI + "." + Tabelle.Spedizioni.NUM_FATTURA + 
                                            " AND " +  Tabelle.FATTURE + "." + Tabelle.Fatture.DATA + " = " + Tabelle.SPEDIZIONI + "." + Tabelle.Spedizioni.DATA_FATTURA + 
                                                " WHERE " + Tabelle.Movimenti.TIPO + " = '" + tipoMov + "' AND "; 


                    if (fornCliente != -1)
                        sql += Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornCliente + " AND ";

                    sql += Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                    
                    String sqlUnion = " UNION SELECT DISTINCT " 
                            + Tabelle.Movimenti.ID + ", " 
                            + Tabelle.Movimenti.MET_PAG 
                            + ", " + Tabelle.Movimenti.VALORE 
                            + " FROM " + Tabelle.MOVIMENTI +
                            " WHERE " + Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.VNC + "' AND ";
                    
                    if (fornCliente != -1)
                        sqlUnion += Tabelle.Movimenti.FORN_CLIENTE + " = " + fornCliente + " AND ";

                    sqlUnion += Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                    
                    sql += sqlUnion;
                    
                } else if (tipoMov == Fattura.tipo.ACQ){
                    sql = "SELECT " + Tabelle.Movimenti.MET_PAG + ", " + Tabelle.Movimenti.VALORE + " FROM " + Tabelle.MOVIMENTI +
                                " JOIN " + Tabelle.FATT_ACQUISTO + " ON " + Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.DATA + " = " + Tabelle.FATT_ACQUISTO + "." + Tabelle.FattureAcquisto.DATA +
                                    " AND " + Tabelle.Movimenti.NUM_DOC + " = " + Tabelle.FattureAcquisto.NUMERO +
                                        " WHERE " + Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.TIPO + " = '" + tipoMov + "' AND ";
                    
                     if (fornCliente != -1)
                        sql += Tabelle.FattureAcquisto.FORNITORE + " = " + fornCliente + " AND ";

                    sql += Tabelle.MOVIMENTI + "." + Tabelle.Movimenti.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                }
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                SaldoCassaMensile mese = new SaldoCassaMensile(meseAnno, tipoMov);
                while (rs.next()) {
                    double importo = rs.getDouble(Tabelle.Movimenti.VALORE);
                    String metPag = rs.getString(Tabelle.Movimenti.MET_PAG);
                    if (metPag.equalsIgnoreCase(SaldoCassaMensile.ASSEGNO))
                        mese.addAssegno(importo);
                    else if (metPag.equalsIgnoreCase(SaldoCassaMensile.CONTANTE))
                        mese.addContante(importo);
                    else if (metPag.equalsIgnoreCase(SaldoCassaMensile.BONIFICO))
                        mese.addBonifico(importo);
                    else if (metPag.equalsIgnoreCase(SaldoCassaMensile.RIBA))
                        mese.addRiba(importo);
                    else if (metPag.equalsIgnoreCase(SaldoCassaMensile.ACCREDITO))
                        mese.addAccredito(importo);
                }
               
                movimMensili.add(mese);
                
             }
            return movimMensili;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          
        } 
        return null;
    }
   
    /*
     * Inserisce un nuovo versamento di contanti all'interno del database.
     */
    public static boolean doCashMovement(MovimentazioneContante m) {
        String type = "";
        if (m instanceof Versamento)
            type = MovimentazioneContante.VERSAMENTO;
        else
            type = MovimentazioneContante.PRELIEVO;
        try{
            Date data = m.getDataMovimento();
            String banca = m.getBanca();
            double importo = m.getImporto();
            
            sql = "INSERT INTO " +  Tabelle.MOVIMENTICONTANTE + " VALUES (NULL, '" + data + "', " + checkNull(banca) + "," + importo + ",'" + type + "')";
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

            return true;
        } catch(SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /*
     * Preleva il totale dei versamenti effettuati dalla cassa all'interno del periodo prescelto
     */
    public static List<MovimentazioneContante> getCashMovements (String[] anniMesi){
        try {
            //Estrazione dall'array di mesi della data iniziale e finale
            String dI = anniMesi[0];
            String dF = anniMesi[anniMesi.length - 1];
            Date dataIniziale = Date.valueOf(dI + "-" + "01");
            Date dataFinale = Date.valueOf(dF + "-" + "31");
            List<MovimentazioneContante> movimentiContante = new LinkedList<MovimentazioneContante>();
            sql = "SELECT *" + " FROM " + Tabelle.MOVIMENTICONTANTE + " WHERE " + Tabelle.MovimentoContante.DATA + " BETWEEN '" + dataIniziale + "' AND '" + dataFinale + "' ORDER BY " + Tabelle.MovimentoContante.DATA + " DESC";
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            
            Date data = new Date(0);
            String banca = "";
            double importo = 0.00;
            String type = "";
            Integer id = null;
            
            while(rs.next()) {
                id = rs.getInt(Tabelle.MovimentoContante.NUMERO);
                data = rs.getDate(Tabelle.MovimentoContante.DATA);
                banca = rs.getString(Tabelle.MovimentoContante.BANCA);
                importo = rs.getDouble(Tabelle.MovimentoContante.IMPORTO);
                type = rs.getString(Tabelle.MovimentoContante.TIPO);
                MovimentazioneContante m;
                if (type.equalsIgnoreCase(MovimentazioneContante.PRELIEVO))
                    m = new Prelievo(id, data, banca, importo);
                else 
                    m = new Versamento(id, data, banca, importo);
                movimentiContante.add(m);
            }
           
            return movimentiContante;
            
      } catch (SQLException ex) {
        Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
        return null;
      } 
    }

    /*
     * Utilizzato per lo scadenziario
     */
    public static List<Fattura> getFatture(Fornitore forn_cliente, Fattura.tipo tipo) {
        
        boolean recuperaFornCliente;

        if (tipo == Fattura.tipo.VEN) {
            if (forn_cliente.getCod() != null) {//Cliente selezionato
                sql = "SELECT DISTINCT " + Tabelle.FATTURE + ".* FROM " + Tabelle.FATTURE + " JOIN " + Tabelle.SPEDIZIONI + " ON " +
                    Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.Spedizioni.NUM_FATTURA + " AND " +  Tabelle.Fatture.DATA + " = " +
                    Tabelle.Spedizioni.DATA_FATTURA + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + forn_cliente.getCod() + " AND " +
                    Tabelle.Fatture.PAGATA + " = false";
            
                recuperaFornCliente = false;
            
            } else {//Tutti i clienti
                sql = "SELECT DISTINCT " + Tabelle.FATTURE + ".*, " + Tabelle.FORNITORI + ".* FROM " + Tabelle.FATTURE + ", " + Tabelle.SPEDIZIONI + ", " + 
                    Tabelle.FORNITORI + " WHERE " + Tabelle.FATTURE + "." + Tabelle.Fatture.NUMERO + " = " + Tabelle.SPEDIZIONI + "." + 
                    Tabelle.Spedizioni.NUM_FATTURA + " AND " +  Tabelle.Fatture.DATA + " = " + Tabelle.Spedizioni.DATA_FATTURA + " AND " + 
                    Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND " + Tabelle.Fatture.PAGATA + " = false";
            
                recuperaFornCliente = true;
            }
            
        } else {
            if (forn_cliente.getCod() != null) {//Cliente selezionato
                sql = "SELECT DISTINCT " + Tabelle.FATT_ACQUISTO + ".* FROM " + Tabelle.FATT_ACQUISTO +
                    " WHERE " + Tabelle.FattureAcquisto.FORNITORE + " = " + forn_cliente.getCod() + " AND " + Tabelle.FattureAcquisto.PAGATA + " = false";
            
                recuperaFornCliente = false;
            
            } else {//Tutti i clienti
                sql = "SELECT DISTINCT " + Tabelle.FATT_ACQUISTO + ".*, " + Tabelle.FORNITORI + ".* FROM " + Tabelle.FATT_ACQUISTO + " JOIN " + 
                        Tabelle.FORNITORI + " ON " + Tabelle.FattureAcquisto.FORNITORE + " = " + Tabelle.Fornitori.COD +
                        " AND " + Tabelle.FattureAcquisto.PAGATA + " = false";

                recuperaFornCliente = true;
            }
            
        }
     
        sql += " ORDER BY ";
        if (tipo == Fattura.tipo.VEN)
            sql += Tabelle.Fatture.DATA + ", " + Tabelle.Fatture.NUMERO;
        else
            sql += Tabelle.FattureAcquisto.DATA + ", " + Tabelle.FattureAcquisto.NUMERO;
                    
        List<Fattura> fatture = new LinkedList<Fattura>();
            
        try {
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (tipo == Fattura.tipo.VEN) {
                ricFatt(recuperaFornCliente, forn_cliente, fatture);
            } else {
                ricFattAcq(recuperaFornCliente, forn_cliente, fatture);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return fatture;
    }
    
    /*
     * Presi in input i mesiAnni di intervallo produce un prospetto del calcolo IVA mensile.
     * Calcolo IVA mensile: IVA CREDITO (Fatture acquisto) - IVA DEBITO (Fatture Emesse)
     */
    public static List<SaldoIvaMensile> getSaldoIvaMensile(String[] anniMesi){
        try {
            String sqlEmesse = "";
            String sqlRicevute = "";
            String sqlNoteCredito = "";
            PreparedStatement psEmesse;
            ResultSet rsEmesse;
            PreparedStatement psAcquisto;
            ResultSet rsAcquisto;
            LinkedList<SaldoIvaMensile> saldiMensili = new LinkedList<SaldoIvaMensile>();
            
            for (String meseAnno : anniMesi){
                sqlEmesse = "SELECT " + 
                            Tabelle.Fatture.IVA 
                                + " FROM " 
                                    + Tabelle.FATTURE + " WHERE "; 
                
                sqlNoteCredito = "SELECT " + 
                                        Tabelle.NoteCredito.IVA 
                                            + " FROM " 
                                                + Tabelle.NOTE_CREDITO + " WHERE ";
                
                sqlRicevute = "SELECT " 
                                + Tabelle.FattureAcquisto.IVA 
                                    + " FROM " 
                                        + Tabelle.FATT_ACQUISTO 
                                            +  " WHERE "; 
                
                sqlEmesse += Tabelle.Fatture.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                sqlRicevute += Tabelle.FattureAcquisto.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";
                sqlNoteCredito += Tabelle.NoteCredito.DATA + " BETWEEN '" + meseAnno + "-01' AND '" + meseAnno + "-31'";              
                
                System.out.println(sqlEmesse);
                System.out.println(sqlNoteCredito);
                System.out.println(sqlRicevute);
                psEmesse = conn.prepareStatement(sqlEmesse);
                rsEmesse= psEmesse.executeQuery();
                psAcquisto = conn.prepareStatement(sqlRicevute);
                rsAcquisto= psAcquisto.executeQuery();
                
                SaldoIvaMensile mese = new SaldoIvaMensile(meseAnno);
                while (rsEmesse.next()) {
                    double tot = rsEmesse.getDouble(1);
                    mese.addIvaDebito(tot);
                }
                //Utilizzimo lo stesso rsEmesse per estrarre i totali delle note credito e sommarli al totale IvaDebito del mese  
                psEmesse = conn.prepareStatement(sqlNoteCredito);
                rsEmesse = psEmesse.executeQuery();
                while (rsEmesse.next()) {
                    double totNotaCredito = rsEmesse.getDouble(1);
                    mese.addIvaDebito(totNotaCredito);
                }
                while (rsAcquisto.next()){
                    double tot = rsAcquisto.getDouble(1);
                    mese.addIvaCredito(tot);
                }
                mese.setSaldo();
                saldiMensili.add(mese);
                
             }
            return saldiMensili;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          
        } 
        return null;
    }
    
    public static boolean updateMovimentoContante(MovimentazioneContante movimento) {
        try {
            sql = "UPDATE " + Tabelle.MOVIMENTICONTANTE + " SET " + Tabelle.MovimentoContante.BANCA + " = " + checkNull(movimento.getBanca()) + 
                    ", " + Tabelle.MovimentoContante.IMPORTO + " = " + movimento.getImporto() + " WHERE " + Tabelle.MovimentoContante.NUMERO + 
                    " = " + movimento.getId();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean updateFatturaAcquisto(Fattura toUpdate, Fattura old){
        try {
            conn.setAutoCommit(false);
            
            sql = "UPDATE " + Tabelle.FATT_ACQUISTO + " SET " + Tabelle.FattureAcquisto.FORNITORE + " = " + toUpdate.getFornitore() + 
                    ", " + Tabelle.FattureAcquisto.NUMERO + " = " + toUpdate.getNumero() + ", " + Tabelle.FattureAcquisto.DATA + " = " + checkNull(toUpdate.getData()) +
                    ", " + Tabelle.FattureAcquisto.IMPORTO + " = " + toUpdate.getImporto() + ", " + Tabelle.FattureAcquisto.IVA + " = " + toUpdate.getIva() + 
                    ", " + Tabelle.FattureAcquisto.METODO_PAGAMENTO + " = " + checkNull(toUpdate.getMetPag()) + ", " + Tabelle.FattureAcquisto.NOTE + " = " + checkNull(toUpdate.getNote()) + 
                    ", " + Tabelle.FattureAcquisto.PAGATA + " = " + toUpdate.getPagata() + ", " + Tabelle.FattureAcquisto.SCONTO + " = " + toUpdate.getSconto() + 
                    ", " + Tabelle.FattureAcquisto.TIPO + " = '" + toUpdate.getTipo() + "', " + Tabelle.FattureAcquisto.TOTALE + " = " + toUpdate.getTotale() + 
                    ", " + Tabelle.FattureAcquisto.SCADENZA + " = '" + toUpdate.getDataScadenza() + "', " + Tabelle.FattureAcquisto.SPECIFICA_NUMERO + " = " + 
                    checkNull(toUpdate.getSpecificaNumero()) +
                    " WHERE " + Tabelle.FattureAcquisto.NUMERO + " = " + old.getNumero() + " AND " + Tabelle.FattureAcquisto.DATA + " = '" + old.getData()
                    + "' AND " + Tabelle.FattureAcquisto.FORNITORE + " = " + old.getFornitore();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            /*
             * Se la fattura è pagata, e sono stati modificati uno o più valori (importo, iva, sconto o totale), vengono
             * cancellati tutte le modalità di pagamento e la fattura torna allo stato di "non pagata" (N).
             */
            if (toUpdate.getPagata() && (toUpdate.getImporto() != old.getImporto() || toUpdate.getSconto() != old.getSconto() ||
                    toUpdate.getIva() != old.getIva() || toUpdate.getTotale() != old.getTotale())) {
                
                sql = "DELETE FROM " + Tabelle.MOVIMENTI + " WHERE " + Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.ACQ +
                        "' AND " + Tabelle.Movimenti.NUM_DOC + " = " + old.getNumero() + 
                        " AND " + Tabelle.Movimenti.DATA + " = '" + old.getData() + "' AND " + Tabelle.Movimenti.FORN_CLIENTE + 
                        " = " + old.getFornitore();
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                sql = "UPDATE " + Tabelle.FATT_ACQUISTO + " SET " + Tabelle.FattureAcquisto.PAGATA + " = 0, " + Tabelle.FattureAcquisto.PAGAMENTO + " = NULL" +
                        " WHERE " + Tabelle.FattureAcquisto.NUMERO + " = " + old.getNumero() + " AND " + Tabelle.FattureAcquisto.DATA + " = '" + old.getData()
                        + "' AND " + Tabelle.FattureAcquisto.FORNITORE + " = " + old.getFornitore();
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                conn.commit();
                conn.setAutoCommit(true);
                throw new EccezioneModificaFattAcquisto("La modifica è stata eseguita, ma la fattura è tornata allo stato di \"Non pagata\".\n" +
                        "Questo perché sono stati modificati uno o più valori tra importo, sconto, iva e totale");
                
            }
            
            /*
             * Se sono stati modificati uno o più valori appartenenti alla chiave dei movimenti, aggiorna i movimenti esistenti.
             */
            if (toUpdate.getNumero() != old.getNumero() || !toUpdate.getData().equals(old.getData()) || 
                    !toUpdate.getCliente().equals(old.getCliente())) {
                
                sql = "UPDATE " + Tabelle.MOVIMENTI + " SET " + Tabelle.Movimenti.NUM_DOC + " = " + toUpdate.getNumero() + ", " + 
                        Tabelle.Movimenti.DATA + " = '" + toUpdate.getData() + "', " + Tabelle.Movimenti.FORN_CLIENTE + " = " +
                        toUpdate.getCliente().getCod() + " WHERE " + Tabelle.Movimenti.NUM_DOC + " = " + old.getNumero() + " AND " +
                        Tabelle.Movimenti.DATA + " = '" + old.getData() + "' AND " + Tabelle.Movimenti.FORN_CLIENTE + " = " + 
                        old.getCliente().getCod() + " AND " + Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.ACQ + "'";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
            }
            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
            if (ex.getErrorCode() == DATA_TOO_LONG) {
                String errorMsg = ex.getMessage();
                if (errorMsg.contains(Tabelle.FattureAcquisto.METODO_PAGAMENTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Metodo di pagamento troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.IMPORTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Importo troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.SCONTO))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Sconto troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.IVA))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IVA troppo lungo!");

                else if (errorMsg.contains(Tabelle.FattureAcquisto.TOTALE))
                    throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Totale troppo lungo!");
                
            } else if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                throw new EccezioneChiaveDuplicata("Numero fattura '" + toUpdate.getNumero() + "' esistente per la data in questione");
            }
            return false;
        } 
    }
    
    public static boolean deleteFatturaAcquisto(Fattura fattura) {
        try {
            
            conn.setAutoCommit(false);
            
            sql = "DELETE FROM " + Tabelle.FATT_ACQUISTO + " WHERE " + Tabelle.FattureAcquisto.NUMERO + " = " + fattura.getNumero() + " AND " + Tabelle.FattureAcquisto.DATA +
                    " = '" + fattura.getData() + "' AND " + Tabelle.FattureAcquisto.FORNITORE + " = " + fattura.getFornitore();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            sql = "DELETE FROM " + Tabelle.MOVIMENTI + " WHERE " + Tabelle.Movimenti.NUM_DOC + " = " + fattura.getNumero() + " AND " +
                        Tabelle.Movimenti.DATA + " = '" + fattura.getData() + "' AND " + Tabelle.Movimenti.TIPO + " = '" + Fattura.tipo.ACQ + "' AND " +
                    Tabelle.Movimenti.FORN_CLIENTE + " = " + fattura.getCliente().getCod();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            
            conn.commit();      
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }

    public static boolean deleteMovimentoContante(MovimentazioneContante movimento) {
        try {
            sql = "DELETE FROM " + Tabelle.MOVIMENTICONTANTE + " WHERE " + Tabelle.MovimentoContante.NUMERO + " = " + movimento.getId();
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean insertNotaCredito(NotaCredito notaCredito) {

        try {
            conn.setAutoCommit(false);
            sql = "INSERT INTO " 
                    + Tabelle.NOTE_CREDITO 
                    + " VALUES ("
                    + "NULL, "
                    + notaCredito.getNumero() + ", '"
                    + notaCredito.getData() + "', "
                    + notaCredito.getCliente().getCod() + ", '"
                    + notaCredito.getMetPag() + "', "
                    + notaCredito.getImponibile() + ", "
                    + notaCredito.getIva() + ", "
                    + notaCredito.getTotale() + ", "
                    + notaCredito.getPagata() + ", "
                    + checkNull(notaCredito.getNote()) + ", "
                    + checkNull(notaCredito.getDataPagamento()) + ", "
                    + checkNull(notaCredito.getDataScadenza())
                    + ") ";
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();           
            
            sql = "SELECT "
                    + Tabelle.NoteCredito.ID
                    + " FROM "
                    + Tabelle.NOTE_CREDITO
                    + " WHERE "
                    + Tabelle.NoteCredito.NUMERO + " = " + notaCredito.getNumero()
                    + " AND "
                    + Tabelle.NoteCredito.DATA + " = '" + notaCredito.getData() + "'";
                              
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();    
            rs.next();
            int id = rs.getInt(Tabelle.NoteCredito.ID);
            
            List<DescrizioniNotaCredito> descrizioni = notaCredito.getDescrizioni();
            
            for (DescrizioniNotaCredito descrizione : descrizioni) {
                sql = "INSERT INTO "
                        + Tabelle.DESCRIZIONI_NOTE_CREDITO
                        + " VALUES ("
                        + "NULL, "
                        + id + ", "
                        + checkNull(descrizione.getDescrizione()) + ", "
                        + descrizione.getImporto() + ", "
                        + descrizione.getPercIva() + ", "
                        + descrizione.getIva()
                        + ")";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();    
            }
           
            conn.commit();
            conn.setAutoCommit(true);
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO_CBC.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }
}