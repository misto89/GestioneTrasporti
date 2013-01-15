/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import eccezioni.CheckTuttException;
import entita.*;
import java.util.List;
import eccezioni.EccezioneChiaveDuplicata;
import eccezioni.EccezioneConnesioneNonRiuscita;
import eccezioni.EccezioneEliminazioneImpossibile;
import eccezioni.EccezioneEntitaNonValida;
import eccezioni.EccezioneValoreCampoTroppoLungo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andle
 */
public abstract class DAO_ASF {
    
    //Codici di errore MySql
    private static final int DUPLICATE_ENTRY = 1062;
    private static final int DATA_TOO_LONG = 1406;
    
    //Parametri di connessione al db
    private static Connection conn = new DbManager().CreateConnection();
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static String sql;
    
    public static void test() throws EccezioneConnesioneNonRiuscita {
        new Test();
    }
    
    /*
     * Ricevuta in input una List di Fornitori, cicla sul ResultSet avvalorato precedentemente
     * e aggiunge ciclicamente gli elementi alla lista
     */
    private static void ricFornitori(List lista) throws SQLException {
        Integer cod = 0;
        String nome = null;
        String titolare = null;
        String piva = null;
        String codfisc = null;
        String indirizzoLeg = null;
        String telefono1 = null;
        String telefono2 = null;
        String fax = null;
        String email = null;
        String capLeg = null;
        String cittaLeg = null;
        String provLeg = null;
        String nazioneLeg = null;
        String banca = null;
        String iban = null;
        String nomeRef1;
        String nomeRef2;
        String emailRef1;
        String emailRef2;
        String telRef1;
        String telRef2;
        String iscrizioneAlbo;
        String indirizzoOp = null;
        String capOp = null;
        String cittaOp = null;
        String provOp = null;
        String nazioneOp = null;
        
          
        /*
         * Per ogni elemento presente nel ResultSet, crea un oggetto Fornitore
         * e lo aggiunge alla lista
         */
        while (rs.next()) {
            cod = rs.getInt(Tabelle.Fornitori.COD);
            nome = rs.getString(Tabelle.Fornitori.NOME);
            titolare = rs.getString(Tabelle.Fornitori.TITOLARE);
            piva = rs.getString(Tabelle.Fornitori.PIVA);
            codfisc = rs.getString(Tabelle.Fornitori.CODFISCALE);
            indirizzoLeg = rs.getString(Tabelle.Fornitori.INDIRIZZO_LEGALE);
            telefono1 = rs.getString(Tabelle.Fornitori.TELEFONO1);
            telefono2 = rs.getString(Tabelle.Fornitori.TELEFONO2);
            fax = rs.getString(Tabelle.Fornitori.FAX);
            email = rs.getString(Tabelle.Fornitori.EMAIL);
            capLeg = rs.getString(Tabelle.Fornitori.CAP_LEGALE);
            cittaLeg = rs.getString(Tabelle.Fornitori.CITTA_LEGALE);
            provLeg = rs.getString(Tabelle.Fornitori.PROV_LEGALE);
            nazioneLeg = rs.getString(Tabelle.Fornitori.NAZIONE_LEGALE);
            banca = rs.getString(Tabelle.Fornitori.BANCA);
            iban = rs.getString(Tabelle.Fornitori.IBAN);
            nomeRef1 = rs.getString(Tabelle.Fornitori.NOME_REF_1);
            nomeRef2 = rs.getString(Tabelle.Fornitori.NOME_REF_2);
            emailRef1 = rs.getString(Tabelle.Fornitori.EMAIL_REF_1);
            emailRef2 = rs.getString(Tabelle.Fornitori.EMAIL_REF_2);
            telRef1 = rs.getString(Tabelle.Fornitori.TEL_REF_1);
            telRef2 = rs.getString(Tabelle.Fornitori.TEL_REF_2);
            iscrizioneAlbo = rs.getString(Tabelle.Fornitori.ISCRIZIONE_ALBO);
            indirizzoOp = rs.getString(Tabelle.Fornitori.INDIRIZZO_OP);
            capOp = rs.getString(Tabelle.Fornitori.CAP_OP);
            cittaOp = rs.getString(Tabelle.Fornitori.CITTA_OP);
            provOp = rs.getString(Tabelle.Fornitori.PROV_OP);
            nazioneOp = rs.getString(Tabelle.Fornitori.NAZIONE_OP);
            
            lista.add(new Fornitore(cod, nome, titolare, piva, codfisc, indirizzoLeg, telefono1, telefono2, fax, email, capLeg, cittaLeg, provLeg, nazioneLeg, banca, iban,
                    iscrizioneAlbo, indirizzoOp, cittaOp, capOp, provOp, nazioneOp, nomeRef1, nomeRef2, emailRef1, emailRef2, telRef1, telRef2));   
         }
    }
    
    /**
     * Restituisce una lista di oggetti entity (appartententi alla categoria anagrafe) corrispondenti 
     * alla classe specificata nel parametro.
     * @param entita Descrive la classe di cui si vuole restituirne una lista di entità.
     * @return Una lista di entità.
     */
    public static List<Entity> getAnagrafe(Class entita) {
        
        /*
         * Il metodo determina inanzitutto, la classe di appartenenza del parametro. Successivamente
         * recupera dal database l'elenco completo dalla relativa tabella e popola una lista con tali
         * elementi.
         */
        
        if (entita.equals(Fornitore.class)) {
            try {
                sql = "SELECT * FROM " + Tabelle.FORNITORI + " ORDER BY " + Tabelle.Fornitori.NOME + ", " + Tabelle.Fornitori.PIVA
                        + ", " + Tabelle.Fornitori.CODFISCALE;
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                List<Entity> fornitori = new LinkedList<Entity>();
                ricFornitori(fornitori);
              
                return fornitori;
            
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (entita.equals(Mezzo.class)) {
            try {
                sql = "SELECT * FROM " + Tabelle.MEZZI + " WHERE " + Tabelle.Mezzi.TARGA + " <> 'Vettore' ORDER BY " + Tabelle.Mezzi.TARGA;
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                List<Entity> mezzi = new LinkedList<Entity>();
                
                Integer id = 0;
                String targa = null;
                String marca = null;
                String scadBollo = null;
                Date scadRevisione = null;
                Date scadAtp = null;
                Date scadAssicurazione1 = null;
                Date scadAssicurazione2 = null;
            
                while (rs.next()){
                    id = rs.getInt(Tabelle.Mezzi.ID);
                    targa = rs.getString(Tabelle.Mezzi.TARGA);
                    marca = rs.getString(Tabelle.Mezzi.MARCA);
                    scadBollo = rs.getString(Tabelle.Mezzi.SCAD_BOLLO);
                    scadRevisione = rs.getDate(Tabelle.Mezzi.SCAD_REVISIONE);
                    scadAtp = rs.getDate(Tabelle.Mezzi.SCAD_ATP);
                    scadAssicurazione1 = rs.getDate(Tabelle.Mezzi.SCAD_ASSICURAZIONE1);
                    scadAssicurazione2 = rs.getDate(Tabelle.Mezzi.SCAD_ASSICURAZIONE2);
                
                    mezzi.add(new Mezzo(id, targa, marca, scadBollo, scadRevisione, scadAtp, scadAssicurazione1, scadAssicurazione2));   
                }
            
                return mezzi;
            
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        throw new EccezioneEntitaNonValida();
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
    
    /**
     * Permette la memorizzazione di un nuovo oggetto.
     * @param o L'oggetto entità da memorizzare.
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean insert(Entity o) {
        
       /*
        * Il metodo dopo aver determinato il tipo dell'entità, inserisce nel db il nuovo elemento,
         * controllando per tutti i possibili campi che possono assumere il valore null, se il campo
         * è un campo vuoto. In tal caso viene posto il valore NULL nella stringa SQL, altrimenti
         * la il valore del campo viene delimitato dalla coppia di apici.
        */
        
       if (o instanceof Fornitore) {
            Fornitore f = (Fornitore) o;
            try {
                sql = "INSERT INTO " + Tabelle.FORNITORI + " VALUES (NULL, " + checkNull(f.getNome()) + ", " + checkNull(f.getTitolare()) + ", " + checkNull(f.getPiva()) + ", " + 
                        checkNull(f.getCodfiscale()) + ", " + checkNull(f.getIndirizzoLeg()) + ", " + checkNull(f.getTelefono1()) + ", " + checkNull(f.getTelefono2()) + ", " + checkNull(f.getFax()) + ", " +
                        checkNull(f.getEmail()) + ", " + checkNull(f.getCapLeg()) + ", " + checkNull(f.getCittaLeg()) + ", " + checkNull(f.getProvLeg()) + ", " + 
                        checkNull(f.getNazioneLeg()) + ", " + checkNull(f.getBanca()) + ", " + checkNull(f.getIban()) + ", " + checkNull(f.getNomeRef1()) + 
                        ", " + checkNull(f.getEmailRef1()) + ", " + checkNull(f.getTelRef1()) + ", " + checkNull(f.getNomeRef2()) + ", " + checkNull(f.getEmailRef2()) + 
                        ", " + checkNull(f.getTelRef2()) + ", " + checkNull(f.getIscrizioneAlbo()) + ", " + checkNull(f.getIndirizzoOp()) + ", " + checkNull(f.getCapOp()) + 
                        ", " + checkNull(f.getCittaOp()) + ", " + checkNull(f.getProvOp()) + ", " + checkNull(f.getNazioneOp()) + ")";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                    if (ex.getMessage().contains(Tabelle.Fornitori.PIVA))
                        throw new EccezioneChiaveDuplicata("Partita IVA '" + f.getPiva() + "' esistente!");
                    else
                        throw new EccezioneChiaveDuplicata("Codice fiscale '" + f.getCodfiscale() + "' esistente!");
                    
                } else if (ex.getErrorCode() == DATA_TOO_LONG) {
                    
                    if (ex.getMessage().contains(Tabelle.Fornitori.NOME))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nome troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TITOLARE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Titolare troppo lungo!");
                        
                    else if (ex.getMessage().contains(Tabelle.Fornitori.INDIRIZZO_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Indirizzo legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TELEFONO1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono 1 troppo lungo!"); 
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TELEFONO2))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono 2 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.FAX))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Fax troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.EMAIL))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo E-Mail troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CAP_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo CAP legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CITTA_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Città legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.PROV_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provincia legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NAZIONE_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nazione legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.BANCA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Banca troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.IBAN))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IBAN troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NOME_REF_1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nome Referente 1 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NOME_REF_2))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nome Referente 2 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.EMAIL_REF_1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Email Referente 1 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.EMAIL_REF_2))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Email Referente 2 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TEL_REF_1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono Referente 1 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TEL_REF_2))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono Referente 2 troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.ISCRIZIONE_ALBO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Iscrizione all'albo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.INDIRIZZO_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Indirizzo operativo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CAP_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo CAP operativo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CITTA_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Città operativa troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.PROV_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provincia operativa troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NAZIONE_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nazione operativa troppo lungo!");
                    
                    
                }
                       
                return false;
            }
            
        } else if (o instanceof Mezzo){
            Mezzo m =(Mezzo) o;
            try {
                sql = "INSERT INTO " + Tabelle.MEZZI + " VALUES (NULL, '" + m.getTarga() + "', " + checkNull(m.getMarca()) + 
                        ", " + checkNull(m.getScadBollo()) + ", " + checkNull(m.getScadRevisione()) + ", " + checkNull(m.getScadAtp()) +
                        ", " + checkNull(m.getScadAssicurazione1()) + ", " + checkNull(m.getScadAssicurazione2()) +")";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getErrorCode() == DUPLICATE_ENTRY) 
                    throw new EccezioneChiaveDuplicata("Targa '" + m.getTarga() + "' esistente!");
                
                return false;
            }
            
        } else if (o instanceof Spedizione) {
            Spedizione sped = (Spedizione) o;
            try {
                Integer idMezzo = null;
                if (sped.getMezzo() != null) {
                    sql = "SELECT " + Tabelle.Mezzi.ID + " FROM " + Tabelle.MEZZI + " WHERE " + Tabelle.Mezzi.TARGA + " = '" + sped.getMezzo() + "'";
                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    rs.next();
                    idMezzo = rs.getInt(Tabelle.Mezzi.ID);
                }
                
                conn.setAutoCommit(false);
                
                sql = "INSERT INTO " + Tabelle.SPEDIZIONI + " VALUES (" + Integer.parseInt(sped.getNumSpedizione())+", " + checkNull(sped.getDataCarico()) + ", " + checkNull(sped.getDataDocumento()) + ", " 
                        + checkNull(sped.getDescrizione()) + ", " + sped.getFornitore() + ", " + checkNull(idMezzo) + ", " + checkNull(sped.getUm1()) + ", " + sped.getQta1() + ", " 
                        + sped.getTraz1() + ", " + sped.getDistrib1() + ", " + sped.getImporto() + ", " + sped.getSconto() + ", " + sped.getPercIva() + ", " + sped.getIva() +
                        ", " + sped.getPercProvv() + ", " + sped.getProvvigione() + ", " + sped.getTotale() + ", " + checkNull(sped.getNote()) + ", " + sped.getRientrata() + 
                        ", NULL, NULL, " + sped.getValoreMerce() + ", " + sped.getImponibile() + ", '" + sped.getStato() + "', " + checkNull(sped.getUm2()) + ", " + sped.getQta2() + ", " 
                        + sped.getTraz2() + ", " + sped.getDistrib2() +")";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                List<String> bolle = sped.getBolle();
                for (String bolla : bolle) {
                    sql = "INSERT INTO " + Tabelle.BOLLE + " VALUES ('" + sped.getNumSpedizione() + "', '" + sped.getDataCarico() + "', '" + bolla + "')";
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
                    Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex1);
                }
                if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                    throw new EccezioneChiaveDuplicata("Numero spedizione '" + sped.getNumSpedizione() + "' esistente per l'anno in questione!");
                                        
                } else if (ex.getErrorCode() == DATA_TOO_LONG) {
                    String errorMsg = ex.getMessage();
                    if (errorMsg.contains(Tabelle.Spedizioni.NUMERO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Numero troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.DESCRIZIONE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Descrizione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.UM1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo UM troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.QTA1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Quantità troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.TRAZ1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Traz. troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.DISTRIB1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Distrib. troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPORTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Importo troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.SCONTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Sconto troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.PERC_IVA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo % IVA troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IVA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IVA troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.PERC_PROVV))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo % Provvigione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPORTO_PROVV))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provvigione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.TOTALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Totale troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.VALORE_MERCE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Valore merce troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPONIBILE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Imponibile troppo lungo!");
                    
                }
                return false;
            }
            
        } else if (o instanceof Fattura) {
            Fattura fatt = (Fattura) o;
            
            try {
                conn.setAutoCommit(false);
                
                sql = "INSERT INTO " + Tabelle.FATTURE + " VALUES (" + fatt.getNumero() + ", " + checkNull(fatt.getData()) + ", " + checkNull(fatt.getMetPag()) + ", " + fatt.getImporto() + ", " + 
                                            fatt.getSconto() + ", " + fatt.getProvvigione() + ", " + fatt.getIva() + ", " + fatt.getTotale() + ", " + fatt.getForfait() + ", " + fatt.getPagata() + ", "
                                            + checkNull(fatt.getNote()) + ", '" + fatt.getDataScadenza() + "', NULL)";

                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();

                List<Spedizione> spedizioni = fatt.getSpedizioni();
                int fornitore = spedizioni.get(0).getFornitore();
                //Date data = fatt.getData();
                for (Spedizione sped: spedizioni){
                    sql = "UPDATE " + Tabelle.SPEDIZIONI + " SET " + Tabelle.Spedizioni.NUM_FATTURA + " = " + fatt.getNumero() + 
                            ", " + Tabelle.Spedizioni.DATA_FATTURA + " = '" + fatt.getData() + "' WHERE " + Tabelle.Spedizioni.NUMERO + " = " + sped.getNumSpedizione() +
                            " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore + " AND " + Tabelle.Spedizioni.DATA_CARICO + " = '" + sped.getDataCarico() + "'";

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
                    Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex1);
                }
                if (ex.getErrorCode() == DATA_TOO_LONG) {
                    String errorMsg = ex.getMessage();
                    if (errorMsg.contains(Tabelle.Fatture.METODO_PAGAMENTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Metodo di pagamento troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Fatture.IMPORTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Importo troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Fatture.SCONTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Sconto troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Fatture.PROVVIGIONE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provvigione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Fatture.IVA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IVA troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Fatture.TOTALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Totale troppo lungo!");
                } else if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                    throw new EccezioneChiaveDuplicata("Numero fattura '" + fatt.getNumero() + "' esistente per la data in questione!");
                }
                return false;
            } 
            
        }
       return false;
    }     
    
    /**
     * Permette l'aggiornamento di un oggetto esistente.
     * @param o L'oggetto entità da aggiornare
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean update(Entity o) {
        if (o instanceof Fornitore) {
            Fornitore f = (Fornitore) o;
            try {
                sql = "UPDATE " + Tabelle.FORNITORI + " SET " + Tabelle.Fornitori.NOME + " = " + checkNull(f.getNome()) + ", " + 
                        Tabelle.Fornitori.TITOLARE + " = " + checkNull(f.getTitolare()) + ", " + Tabelle.Fornitori.PIVA + " = " + checkNull(f.getPiva()) + ", " + 
                        Tabelle.Fornitori.CODFISCALE + " = " + checkNull(f.getCodfiscale()) + ", " + Tabelle.Fornitori.INDIRIZZO_LEGALE + " = " + checkNull(f.getIndirizzoLeg()) + 
                        ", " + Tabelle.Fornitori.TELEFONO1 + " = " + checkNull(f.getTelefono1()) + ", " + Tabelle.Fornitori.EMAIL + " = " + checkNull(f.getEmail()) + ", " +
                        Tabelle.Fornitori.CAP_LEGALE + " = " + checkNull(f.getCapLeg()) + ", " + Tabelle.Fornitori.CITTA_LEGALE + " = " + checkNull(f.getCittaLeg()) + ", " + 
                        Tabelle.Fornitori.PROV_LEGALE + " = " + checkNull(f.getProvLeg()) + ", " + Tabelle.Fornitori.NAZIONE_LEGALE + " = " + checkNull(f.getNazioneLeg()) + ", " +
                        Tabelle.Fornitori.BANCA + " = " + checkNull(f.getBanca()) + ", " + Tabelle.Fornitori.IBAN + " = " + checkNull(f.getIban()) + ", " +
                        Tabelle.Fornitori.NOME_REF_1 + " = " + checkNull(f.getNomeRef1()) + ", " + Tabelle.Fornitori.NOME_REF_2 + " = " + checkNull(f.getNomeRef2()) + 
                        ", " + Tabelle.Fornitori.EMAIL_REF_1 + " = " + checkNull(f.getEmailRef1()) + ", " + Tabelle.Fornitori.EMAIL_REF_2 + " = " + checkNull(f.getEmailRef2()) +
                        ", " + Tabelle.Fornitori.TEL_REF_1 + " = " + checkNull(f.getTelRef1()) + ", " + Tabelle.Fornitori.TEL_REF_2 + " = " + checkNull(f.getTelRef2()) + 
                        ", " + Tabelle.Fornitori.TELEFONO2 + " = " + checkNull(f.getTelefono2()) + ", " + Tabelle.Fornitori.FAX + " = " + checkNull(f.getFax()) +
                        ", " + Tabelle.Fornitori.ISCRIZIONE_ALBO + " = " + checkNull(f.getIscrizioneAlbo()) + ", " + Tabelle.Fornitori.INDIRIZZO_OP + " = " + checkNull(f.getIndirizzoOp()) + 
                        ", " + Tabelle.Fornitori.CITTA_OP + " = " + checkNull(f.getCittaOp()) + ", " + Tabelle.Fornitori.CAP_OP + " = " + checkNull(f.getCapOp()) + 
                        ", " + Tabelle.Fornitori.PROV_OP + " = " + checkNull(f.getProvOp()) + ", " + Tabelle.Fornitori.NAZIONE_OP + " = " + checkNull(f.getNazioneOp()) +
                        " WHERE " + Tabelle.Fornitori.COD + " = " + f.getCod();
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getErrorCode() == DUPLICATE_ENTRY) {
                    if (ex.getMessage().contains(Tabelle.Fornitori.PIVA))
                        throw new EccezioneChiaveDuplicata("Partita IVA '" + f.getPiva() + "' esistente!");
                    else
                        throw new EccezioneChiaveDuplicata("Codice fiscale '" + f.getCodfiscale() + "' esistente!");
                    
                } else if (ex.getErrorCode() == DATA_TOO_LONG) {
                    
                    if (ex.getMessage().contains(Tabelle.Fornitori.NOME))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nome troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TITOLARE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Titolare troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.INDIRIZZO_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Indirizzo legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TELEFONO1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono 1 troppo lungo!"); 
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.TELEFONO2))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Telefono 2 troppo lungo!"); 
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.FAX))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Fax troppo lungo!"); 
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.EMAIL))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo E-Mail troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CAP_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo CAP legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CITTA_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Città legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.PROV_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provincia legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NAZIONE_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nazione legale troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.BANCA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Banca troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.IBAN))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IBAN troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.ISCRIZIONE_ALBO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Iscrizione all'albo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.INDIRIZZO_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Indirizzo operativo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CAP_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo CAP operativo troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.CITTA_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Città operativa troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.PROV_OP))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provincia operativa troppo lungo!");
                    
                    else if (ex.getMessage().contains(Tabelle.Fornitori.NAZIONE_LEGALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Nazione operativa troppo lungo!");
                    
                }
                return false;
            }
            
        } else if (o instanceof Mezzo) {
            Mezzo m =(Mezzo) o;
            try {
                sql = "UPDATE " + Tabelle.MEZZI + " SET " + Tabelle.Mezzi.TARGA + " = '" + m.getTarga() + 
                        "', " + Tabelle.Mezzi.MARCA + " = " + checkNull(m.getMarca()) + 
                        ", " + Tabelle.Mezzi.SCAD_BOLLO + " = " + checkNull(m.getScadBollo()) + 
                        ", " + Tabelle.Mezzi.SCAD_REVISIONE + " = " + checkNull(m.getScadRevisione()) +
                        ", " + Tabelle.Mezzi.SCAD_ATP + " = " + checkNull(m.getScadAtp()) +
                        ", " + Tabelle.Mezzi.SCAD_ASSICURAZIONE1 + " = " + checkNull(m.getScadAssicurazione1()) +
                        ", " + Tabelle.Mezzi.SCAD_ASSICURAZIONE2 + " = " + checkNull(m.getScadAssicurazione2()) +
                        " WHERE " + Tabelle.Mezzi.ID + " = " + m.getId();
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getErrorCode() == DUPLICATE_ENTRY) 
                    throw new EccezioneChiaveDuplicata("Targa '" + m.getTarga() + "' esistente!");
                
                return false;
            }
        } else if (o instanceof Spedizione) {
            Spedizione s = (Spedizione) o;
            try {
                Integer idMezzo = null;
                if (s.getMezzo() != null) {
                    sql = "SELECT " + Tabelle.Mezzi.ID + " FROM " + Tabelle.MEZZI + " WHERE " + Tabelle.Mezzi.TARGA + " = '" + s.getMezzo() + "'";
                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();
                    rs.next();
                    idMezzo = rs.getInt(Tabelle.Mezzi.ID);
                    
                }                             
                conn.setAutoCommit(false);
                
                sql = "UPDATE " + Tabelle.SPEDIZIONI + " SET " + Tabelle.Spedizioni.DATA_CARICO + " = " + checkNull(s.getDataCarico()) + ", " + 
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " = " + checkNull(s.getDataDocumento()) + ", " + Tabelle.Spedizioni.DESCRIZIONE + 
                    " = " + checkNull(s.getDescrizione()) + ", " + Tabelle.Spedizioni.MEZZO + " = " + checkNull(idMezzo) + ", " + 
                    Tabelle.Spedizioni.UM1 + " = " + checkNull(s.getUm1()) + ", " + Tabelle.Spedizioni.QTA1 + " = " + s.getQta1() + ", " + 
                    Tabelle.Spedizioni.TRAZ1 + " = " + s.getTraz1() + ", " + Tabelle.Spedizioni.DISTRIB1 + " = " + s.getDistrib1() + ", " +
                    Tabelle.Spedizioni.IMPORTO + " = " + s.getImporto() + ", " + Tabelle.Spedizioni.SCONTO + " = " + checkNull(s.getSconto()) + 
                    ", " + Tabelle.Spedizioni.PERC_IVA + " = " + s.getPercIva() + ", " + Tabelle.Spedizioni.IVA + " = " + s.getIva() + 
                    ", " + Tabelle.Spedizioni.PERC_PROVV + " = " + s.getPercProvv() + ", " + Tabelle.Spedizioni.IMPORTO_PROVV + " = " + 
                    s.getProvvigione() + ", " + Tabelle.Spedizioni.TOTALE + " = " + s.getTotale() + ", " + Tabelle.Spedizioni.NOTE + " = " +
                    checkNull(s.getNote()) + ", " + Tabelle.Spedizioni.RIENTRATA + " = " + s.getRientrata() + ", " + Tabelle.Spedizioni.NUM_FATTURA + 
                    " = " + checkNull(s.getNumFattura()) + ", " + Tabelle.Spedizioni.VALORE_MERCE + " = " + s.getValoreMerce() + ", " +
                    Tabelle.Spedizioni.IMPONIBILE + " = " + s.getImponibile() + ", " + Tabelle.Spedizioni.STATO + " = '" + s.getStato() + "', " +                   
                    Tabelle.Spedizioni.UM2 + " = " + checkNull(s.getUm2()) + ", " + Tabelle.Spedizioni.QTA2 + " = " + s.getQta2() + ", " + 
                    Tabelle.Spedizioni.TRAZ2 + " = " + s.getTraz2() + ", " + Tabelle.Spedizioni.DISTRIB2 + " = " + s.getDistrib2() +
                    " WHERE " + Tabelle.Spedizioni.NUMERO + " = '" + s.getNumSpedizione() + "' AND " + Tabelle.Spedizioni.DATA_CARICO + " = '" + s.getDataCarico() + "'";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql); 
                ps.executeUpdate(); //esegue l'update
                
                sql = "DELETE FROM " + Tabelle.BOLLE + " WHERE " + Tabelle.Bolle.SPEDIZIONE + " = '" + s.getNumSpedizione() + "' AND " + 
                        Tabelle.Bolle.DATA_SPEDIZIONE + " = '" + s.getDataCarico() + "'";
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate(); //elimina tutte le bolle della spedizione corrente
                
                List<String> bolle = s.getBolle();
                for (String bolla : bolle) {
                    sql = "INSERT INTO " + Tabelle.BOLLE + " VALUES ('" + s.getNumSpedizione() + "', '" + s.getDataCarico() + "', '" + bolla + "')";
                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    
                }
                
                conn.commit();
                conn.setAutoCommit(true);
                return true;
                
            } catch (SQLException ex) {
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex1);
                }
                if (ex.getErrorCode() == DATA_TOO_LONG) {
                    String errorMsg = ex.getMessage();
                    if (errorMsg.contains(Tabelle.Spedizioni.NUMERO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Numero troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.DESCRIZIONE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Descrizione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.UM1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo UM troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.QTA1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Quantità troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.TRAZ1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Traz. troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.DISTRIB1))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Distrib. troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPORTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Importo troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.SCONTO))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Sconto troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.PERC_IVA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo % IVA troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IVA))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo IVA troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.PERC_PROVV))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo % Provvigione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPORTO_PROVV))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Provvigione troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.TOTALE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Totale troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.VALORE_MERCE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Valore merce troppo lungo!");
                    
                    else if (errorMsg.contains(Tabelle.Spedizioni.IMPONIBILE))
                        throw new EccezioneValoreCampoTroppoLungo("Valore immesso per il campo Imponibile troppo lungo!");
                        
                }
                return false;
            }
        }
        return false;
    }
    
    /**
     * Permette l'eliminazione di un oggetto esistente.
     * @param o L'oggetto entità da aggiornare.
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean delete(Entity o) {
        if (o instanceof Fornitore) {
            Fornitore f = (Fornitore) o;
            
            try {
                
                sql = "SELECT COUNT(*) AS NUM FROM " + Tabelle.SPEDIZIONI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + f.getCod();
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next())
                    if (rs.getInt("NUM") > 0)
                        throw new EccezioneEliminazioneImpossibile("Impossibile eliminare il fornitore/cliente selezionato!\n"
                                + "Sono presenti delle spedizioni associate");
            
                sql = "SELECT COUNT(*) AS NUM FROM " + Tabelle.FATT_ACQUISTO + " WHERE " + Tabelle.FattureAcquisto.FORNITORE + " = " + f.getCod();
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next())
                    if (rs.getInt("NUM") > 0)
                        throw new EccezioneEliminazioneImpossibile("Impossibile eliminare il fornitore/cliente selezionato!\n"
                                + "Sono presenti delle fatture di acquisto associate");
                
                sql = "DELETE FROM " + Tabelle.FORNITORI + " WHERE " + Tabelle.Fornitori.COD + " = " + f.getCod();
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        } else if (o instanceof Mezzo) {
            Mezzo m = (Mezzo) o;
            try {
                sql = "DELETE FROM " + Tabelle.MEZZI + " WHERE " + Tabelle.Mezzi.ID + " = " + m.getId();
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();;
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        } else if (o instanceof Spedizione) {
            Spedizione s = (Spedizione) o;
            try {
                conn.setAutoCommit(false);
                
                sql = "DELETE FROM " + Tabelle.SPEDIZIONI + " WHERE " + Tabelle.Spedizioni.NUMERO + " = '" + s.getNumSpedizione()+ "' AND " + Tabelle.Spedizioni.DATA_CARICO + " = '"+ s.getDataCarico() + "'";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                sql = "DELETE FROM " + Tabelle.BOLLE + " WHERE " + Tabelle.Bolle.SPEDIZIONE + " = '" + s.getNumSpedizione() + "' AND " + 
                        Tabelle.Bolle.DATA_SPEDIZIONE + " = '" + s.getDataCarico() + "'";
                
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                
                conn.commit();
                conn.setAutoCommit(true);
                
//                resetNumSpedizione(Integer.parseInt(s.getNumSpedizione()), s.getDataCarico());
                return true;
                
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    conn.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return false;
            }
        }
        return false;
    }
    
    public static boolean cambiaCliente(List<Spedizione> spedizioni, Fornitore cliente) {
        try {
            conn.setAutoCommit(false);
            
            for (Spedizione spedizione : spedizioni) {
                sql = "UPDATE " + Tabelle.SPEDIZIONI + " SET " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + cliente.getCod() + " WHERE " +
                        Tabelle.Spedizioni.NUMERO + " = " + spedizione.getNumSpedizione() + " AND " + Tabelle.Spedizioni.DATA_CARICO + " = '" +
                        spedizione.getDataCarico() + "'";
                
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
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
    }
    
    /**
     * Ricerca e restituisce l'insieme dei fornitori che hanno il nome 
     * corrispondente al parametro.
     * @param nome Il nome da cercare
     * @return Una lista contenente l'insieme dei fornitori che soddisfano la ricerca, oppure null
     * se si verficano errori durante la ricerca
     */
    public static List<Fornitore> getFornitori(int tipo, String ric) {        
        switch (tipo) {
            case Fornitore.RIC_NOME: 
                
                ric = ric.replaceAll("'", "''");
                ric = ric.replaceAll("\\\\", "\\\\\\\\");
                
                sql = "SELECT * FROM " + Tabelle.FORNITORI + " WHERE " + Tabelle.Fornitori.NOME + " LIKE '" + ric + "%' ORDER BY " + 
                        Tabelle.Fornitori.NOME + ", " + Tabelle.Fornitori.PIVA + ", " + Tabelle.Fornitori.CODFISCALE;
                break;
                    
            case Fornitore.RIC_PIVA: 
                sql = "SELECT * FROM " + Tabelle.FORNITORI + " WHERE " + Tabelle.Fornitori.PIVA + " LIKE '" + ric + "%' ORDER BY " + 
                        Tabelle.Fornitori.NOME + ", " + Tabelle.Fornitori.PIVA + ", " + Tabelle.Fornitori.CODFISCALE;
                break;
                    
            case Fornitore.RIC_CODFISC: 
                sql = "SELECT * FROM " + Tabelle.FORNITORI + " WHERE " + Tabelle.Fornitori.CODFISCALE + " LIKE '" + ric + "%' ORDER BY " + 
                        Tabelle.Fornitori.NOME + ", " + Tabelle.Fornitori.PIVA + ", " + Tabelle.Fornitori.CODFISCALE;
                break;
        }
        
        try {
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Fornitore> fornitori = new LinkedList<Fornitore>();
            ricFornitori(fornitori);
            return fornitori;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /*
     * Ricevuto il parametro di ordinamento (nome, piva, codfisc), produce una lista ordinata sul parametro
     * ricevuto di oggetti Fornitore.
     */
//    public static List<Fornitore> orderFornitori(int tipo) {
//        switch (tipo) {
//            case Fornitore.RIC_NOME: 
//                sql = "SELECT * FROM " + Tabelle.FORNITORI + " ORDER BY " + Tabelle.Fornitori.NOME;
//                break;
//                    
//            case Fornitore.RIC_PIVA: 
//                sql = "SELECT * FROM " + Tabelle.FORNITORI + " ORDER BY " + Tabelle.Fornitori.PIVA;
//                break;
//                    
//            case Fornitore.RIC_CODFISC: 
//                sql = "SELECT * FROM " + Tabelle.FORNITORI + " ORDER BY " + Tabelle.Fornitori.CODFISCALE;
//                break;
//        }
//        
//        try {
//            System.out.println(sql);
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            List<Fornitore> fornitori = new LinkedList<Fornitore>();
//            ricFornitori(fornitori);
//            return fornitori;
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    
    /**
     * Ricerca e restituisce l'unico mezzo con la targa corrispondente al parametro.
     * @param targa La targa da ricercare
     * @return Il mezzo corrispondente alla targa
     */
    public static Mezzo getMezzo(String targaRic) {
        try {
            sql = "SELECT * FROM " + Tabelle.MEZZI + " WHERE " + Tabelle.Mezzi.TARGA + " = '" + targaRic + "' ORDER BY " + Tabelle.Mezzi.TARGA;
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            Integer id = 0;
            String targa = null;
            String marca = null;
            String scadBollo = null;
            Date scadRevisione = null;
            Date scadAtp = null;
            Date scadAssicurazione1 = null;
            Date scadAssicurazione2= null;
            Mezzo mezzo = new Mezzo();
            
            if (rs.next()){
                id = rs.getInt(Tabelle.Mezzi.ID);
                targa = rs.getString(Tabelle.Mezzi.TARGA);
                marca = rs.getString(Tabelle.Mezzi.MARCA);
                scadBollo = rs.getString(Tabelle.Mezzi.SCAD_BOLLO);
                scadRevisione = rs.getDate(Tabelle.Mezzi.SCAD_REVISIONE);
                scadAtp = rs.getDate(Tabelle.Mezzi.SCAD_ATP);
                scadAssicurazione1 = rs.getDate(Tabelle.Mezzi.SCAD_ASSICURAZIONE1);
                scadAssicurazione2 = rs.getDate(Tabelle.Mezzi.SCAD_ASSICURAZIONE2);

                mezzo = new Mezzo(id, targa, marca, scadBollo, scadRevisione, scadAtp, scadAssicurazione1, scadAssicurazione2);   
            }
            
            return mezzo;
            
        } catch (SQLException ex) {
          Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
          return null;
        }
    }
    
    /*
     * Ricevuta in input una List di Spedizioni, cicla sul ResultSet avvalorato precedentemente
     * e aggiunge ciclicamente gli elementi alla lista
     */
    private static void ricSpedizioni(List<Spedizione> spedizioni, boolean memCliente) {
        try {
            String numero;
            Date dataCarico;
            Date dataDocumento;
            String descrizione;
            Integer forn;
            String mezzo;
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
            Integer numFattura;
            Date dataFattura;
            Double valoreMerce;
            Double imponibile;
            char stato;
            Fornitore cliente = null;
            
                
            while (rs.next()){
                numero = rs.getString(Tabelle.Spedizioni.NUMERO);
                dataCarico = rs.getDate(Tabelle.Spedizioni.DATA_CARICO);
                dataDocumento = rs.getDate(Tabelle.Spedizioni.DATA_DOCUMENTO);
                descrizione = rs.getString(Tabelle.Spedizioni.DESCRIZIONE);
                forn = rs.getInt(Tabelle.Spedizioni.FORN_CLIENTE);
                mezzo = rs.getString(Tabelle.Mezzi.TARGA);
                um1 = rs.getString(Tabelle.Spedizioni.UM1);
                qta1 = rs.getDouble(Tabelle.Spedizioni.QTA1);
                traz1 = rs.getDouble(Tabelle.Spedizioni.TRAZ1);
                distrib1 = rs.getDouble(Tabelle.Spedizioni.DISTRIB1);
                um2 = rs.getString(Tabelle.Spedizioni.UM2);
                qta2 = rs.getDouble(Tabelle.Spedizioni.QTA2);
                traz2 = rs.getDouble(Tabelle.Spedizioni.TRAZ2);
                distrib2 = rs.getDouble(Tabelle.Spedizioni.DISTRIB2);
                importo = rs.getDouble(Tabelle.Spedizioni.IMPORTO);
                note = rs.getString(Tabelle.Spedizioni.NOTE);
                percIva = rs.getInt(Tabelle.Spedizioni.PERC_IVA);
                iva = rs.getDouble(Tabelle.Spedizioni.IVA);
                sconto = rs.getInt(Tabelle.Spedizioni.SCONTO);
                percProvvigione = rs.getDouble(Tabelle.Spedizioni.PERC_PROVV);
                provvigione = rs.getDouble(Tabelle.Spedizioni.IMPORTO_PROVV);
                totale = rs.getDouble(Tabelle.Spedizioni.TOTALE);
                rientrata = rs.getBoolean(Tabelle.Spedizioni.RIENTRATA);
                numFattura = rs.getInt(Tabelle.Spedizioni.NUM_FATTURA);
                dataFattura = rs.getDate(Tabelle.Spedizioni.DATA_FATTURA);
                valoreMerce = rs.getDouble(Tabelle.Spedizioni.VALORE_MERCE);
                imponibile = rs.getDouble(Tabelle.Spedizioni.IMPONIBILE);
                stato = rs.getString(Tabelle.Spedizioni.STATO).charAt(0);
                
                if (memCliente){
                    cliente = new Fornitore(forn);
                    cliente.setNome(rs.getString(Tabelle.Fornitori.NOME));    
                }
                
                if (numFattura == 0)
                    numFattura = null;
                    
                Spedizione sped = new Spedizione(numero, forn, dataCarico, dataDocumento, descrizione, mezzo, 
                        um1, qta1, traz1, distrib1, importo, sconto, percIva, iva, 
                        percProvvigione, provvigione, totale, note, rientrata, numFattura, dataFattura, valoreMerce, 
                        imponibile, stato, um2, qta2, traz2, distrib2);
                
                if (memCliente)
                    sped.setCliente(cliente);
                
                ps = conn.prepareStatement("SELECT " + Tabelle.Bolle.BOLLA + " FROM " + Tabelle.BOLLE + " WHERE " + 
                        Tabelle.Bolle.SPEDIZIONE + " = '" + numero + "' AND " + Tabelle.Bolle.DATA_SPEDIZIONE + " = '" + dataCarico + "'");
                    
                ResultSet rsbolle = ps.executeQuery();
                while (rsbolle.next()) {
                    sped.addBolla(rsbolle.getString(Tabelle.Bolle.BOLLA));
                }
                    
                spedizioni.add(sped);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Restituisce l'elenco di tutte le spedizioni effettuate dal singolo fornitore/cliente
     * specificato dal parametro.
     * @param fornitore Il fornitore di cui recuperare le spedizioni e il tipo di spedizioni da recuperare
     * nf indica le spedizioni non fatturate
     * f indica le spedizioni giù fatturate
     * all indica tutte le spedizioni
     * @return Una lista di spedizioni
     */
    public static List<Spedizione> getSpedizioni(Fornitore fornitore, Spedizione.tipo type) {
        try {
            if (type == Spedizione.tipo.NF)
                
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                        Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                        " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NULL " +
                        " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                        Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL AND " + 
                        Tabelle.Spedizioni.NUM_FATTURA + " IS NULL ORDER BY " + Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + 
                        ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.F)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                        Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                        " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL" + 
                        " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                        Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL AND " + 
                        Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL ORDER BY "  + Tabelle.Spedizioni.STATO + ", " + 
                        Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.ALL)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                        Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                        " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + 
                        " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                        Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL ORDER BY " + 
                        Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
                       
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Spedizione> spedizioni = new LinkedList<Spedizione>();
            ricSpedizioni(spedizioni, false);
            
            return spedizioni;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Restituisce l'elenco di tutte le spedizioni effettuate dal singolo fornitore/cliente
     * specificato dal parametro nelle date comprese fra quelle indicate nei parametri.
     * @param fornitore Il fornitore di cui recuperare le spedizioni
     * @param dataInizio La data di inizio dell'intervallo da considerare
     * @param dataFine La data di fine dell'intervallo da considerare
     *      * @param emesse Indica se recuperare le spedizioni fatturate o meno
     * @return Una lista di spedizioni
     */
    public static List<Spedizione> getSpedizioniDateInterval(Fornitore fornitore, Date dataInizio, Date dataFine, Spedizione.tipo type){
        try {
            if (type == Spedizione.tipo.NF)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                    " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " +
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine + "' AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NULL" + 
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                    Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL AND " + 
                    Tabelle.Spedizioni.NUM_FATTURA + " IS NULL " + " AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " +
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine + "' ORDER BY " + Tabelle.Spedizioni.STATO + ", " + 
                    Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.F)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                    " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " +
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine + "' AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL" +
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                    Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL AND " + 
                    Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL " + " AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " +
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine + "' ORDER BY " + Tabelle.Spedizioni.STATO + ", " + 
                    Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.ALL)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + " WHERE " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + 
                    " AND " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " +
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine + "'" + 
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " +
                    Tabelle.Spedizioni.FORN_CLIENTE + " = " + fornitore.getCod() + " AND " + Tabelle.Spedizioni.MEZZO + " IS NULL AND " + 
                    Tabelle.Spedizioni.DATA_DOCUMENTO + " >= '" + dataInizio + "' AND " + Tabelle.Spedizioni.DATA_DOCUMENTO + " <= '" + dataFine +
                    "' ORDER BY "  + Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
                           
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Spedizione> spedizioni = new LinkedList<Spedizione>();
            ricSpedizioni(spedizioni, false);    
            
            return spedizioni;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /*
     * Restituisce tutte le spedizioni all'interno di un determinato anno
     */
    public static List<Spedizione> getStoricoSpedizioni(int anno, Spedizione.tipo type){
        try {
            if (type == Spedizione.tipo.NF)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + "," + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + ", " + Tabelle.FORNITORI + " WHERE " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31' AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NULL" + 
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " , " + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + Tabelle.SPEDIZIONI + ", " + Tabelle.FORNITORI + " WHERE " +
                    Tabelle.Spedizioni.MEZZO + " IS NULL AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NULL " + " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31'" 
                    + " ORDER BY " + Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.F)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + "," + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + ", " + Tabelle.FORNITORI + " WHERE " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31' AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL" + 
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " , " + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + Tabelle.SPEDIZIONI + ", " + Tabelle.FORNITORI + " WHERE " +
                    Tabelle.Spedizioni.MEZZO + " IS NULL AND " + Tabelle.Spedizioni.NUM_FATTURA + " IS NOT NULL " + " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31'" 
                    + " ORDER BY " + Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
            
            else if (type == Spedizione.tipo.ALL)
                sql = "SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.MEZZI + "." + Tabelle.Mezzi.TARGA + "," + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + 
                    Tabelle.SPEDIZIONI + ", " + Tabelle.MEZZI + ", " + Tabelle.FORNITORI + " WHERE " + Tabelle.Spedizioni.MEZZO + " = " + Tabelle.Mezzi.ID + " AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31'"  + 
                    " UNION SELECT " + Tabelle.SPEDIZIONI + ".*, " + Tabelle.Spedizioni.MEZZO + " , " + Tabelle.FORNITORI + "." + Tabelle.Fornitori.NOME + " FROM " + Tabelle.SPEDIZIONI + ", " + Tabelle.FORNITORI + " WHERE " +
                    Tabelle.Spedizioni.MEZZO + " IS NULL AND " + Tabelle.Spedizioni.FORN_CLIENTE + " = " + Tabelle.Fornitori.COD + " AND "
                    + Tabelle.Spedizioni.DATA_DOCUMENTO + " BETWEEN '" + anno +"-01-01' AND '" + anno + "-12-31'" 
                    + " ORDER BY " + Tabelle.Spedizioni.STATO + ", " + Tabelle.Spedizioni.DATA_DOCUMENTO + ", " + Tabelle.Spedizioni.NUMERO;
                           
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Spedizione> spedizioni = new LinkedList<Spedizione>();
            ricSpedizioni(spedizioni, true);    
            
            return spedizioni;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Restituisce il prossimo numero della fattura o spedizione
     */
    public static int getNumber(Class table, Date dataDoc) {
        try {
            String dataDocYear = (dataDoc.toString()).substring(0, 4);
            Date data = null;
            if (table.equals(Fattura.class)){
                String sqlMaxDate = "SELECT MAX(" + Tabelle.Fatture.DATA + ") AS MAXDATE FROM " + Tabelle.FATTURE;
                System.out.println(sqlMaxDate);
                PreparedStatement psMaxDate = conn.prepareStatement(sqlMaxDate);
                ResultSet rsMaxDate = psMaxDate.executeQuery();
                if (rsMaxDate.next()){ 
                    data = rsMaxDate.getDate("MAXDATE");
                }
                sql = "SELECT MAX(" + Tabelle.Fatture.NUMERO + ") AS MAXNUM FROM " + Tabelle.FATTURE + " WHERE " + Tabelle.Fatture.DATA + " >= '" + dataDocYear + "-01-01'" + " AND " + Tabelle.Fatture.DATA + " <= '" + dataDocYear + "-12-31'";  
                
            } else if (table.equals(Spedizione.class)) {
                String sqlMaxDate = "SELECT MAX(" + Tabelle.Spedizioni.DATA_CARICO + ") AS MAXDATE FROM " + Tabelle.SPEDIZIONI;
                System.out.println(sqlMaxDate);
                PreparedStatement psMaxDate = conn.prepareStatement(sqlMaxDate);
                ResultSet rsMaxDate = psMaxDate.executeQuery();
                if (rsMaxDate.next()){ 
                    data = rsMaxDate.getDate("MAXDATE");
                }
                sql = "SELECT MAX(" + Tabelle.Spedizioni.NUMERO + ") AS MAXNUM FROM " + Tabelle.SPEDIZIONI + " WHERE " + Tabelle.Spedizioni.DATA_CARICO + " >= '" + dataDocYear + "-01-01'" + " AND " + Tabelle.Spedizioni.DATA_CARICO + " <= '" + dataDocYear + "-12-31'";  
            } else if (table.equals(NotaCredito.class)) {
                String sqlMaxDate = "SELECT MAX(" + Tabelle.NoteCredito.DATA + ") AS MAXDATE FROM " + Tabelle.NOTE_CREDITO;
                System.out.println(sqlMaxDate);
                PreparedStatement psMaxDate = conn.prepareStatement(sqlMaxDate);
                ResultSet rsMaxDate = psMaxDate.executeQuery();
                if (rsMaxDate.next()){ 
                    data = rsMaxDate.getDate("MAXDATE");
                }
                sql = "SELECT MAX(" + Tabelle.NoteCredito.NUMERO + ") AS MAXNUM FROM " + Tabelle.NOTE_CREDITO + " WHERE " + Tabelle.NoteCredito.DATA + " >= '" + dataDocYear + "-01-01'" + " AND " + Tabelle.NoteCredito.DATA + " <= '" + dataDocYear + "-12-31'";  
            }
            
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            int actualNum = 1;
            String ultimoDocYear = "";
            
            if (rs.next()){
               if (!(data == null)){
                   
                   //Estraiamo l'anno dell'ultimo documento memorizzato nel db
                   ultimoDocYear = (data.toString()).substring(0, 4); 
                   
                   /*
                    * Controlla se l'anno della data odierna è maggiore dell'anno dell'ultima fattura inserita nel db. In caso 
                    * affermativo il contatore del numero fattura riparte da 0; in caso negativo il numero della fattura vien
                    * semplicemente incrementato di 1.
                    */
                    if (Integer.parseInt(dataDocYear) <= Integer.parseInt(ultimoDocYear))
                        actualNum = rs.getInt("MAXNUM") + 1;
                }
            }            
            return actualNum;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    
    /*
     * Si occupa di resettare i numeri di spedizione quanto una di queste viene eliminata.
     * @param numero: il numero di spedizione eliminata
     * @param data: la data della spedizione eliminata
     */
//    public static boolean resetNumSpedizione(int numero, Date dataSped){
//        try {
//            String annoDataSped = (dataSped.toString()).substring(0, 4);
//            sql = "SELECT " + Tabelle.Spedizioni.NUMERO + ", " + Tabelle.Spedizioni.DATA_CARICO + " FROM " + Tabelle.SPEDIZIONI + " WHERE " + Tabelle.Spedizioni.NUMERO + " > " + numero + " AND " + Tabelle.Spedizioni.DATA_CARICO + " >= '" + annoDataSped + "-01-01' AND " + Tabelle.Spedizioni.DATA_CARICO + " <= '" + annoDataSped + "-12-31'"; 
//        
//            System.out.println(sql);
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            
//            ps = conn.prepareStatement("START TRANSACTION");
//            ps.executeUpdate();
//            
//            while (rs.next()){
//                int modNum = rs.getInt(1);
//                Date dataCarico = rs.getDate(2);
//                sql = "UPDATE " + Tabelle.SPEDIZIONI + " SET " + Tabelle.Spedizioni.NUMERO + " = " + Tabelle.Spedizioni.NUMERO + " -1 WHERE " + Tabelle.Spedizioni.NUMERO + " = " + modNum + " AND " + Tabelle.Spedizioni.DATA_CARICO + " = '" + dataCarico + "'";
//                ps = conn.prepareStatement(sql);
//                System.out.println(sql);
//                ps.executeUpdate(); 
//                
//                sql = "UPDATE " + Tabelle.BOLLE + " SET " + Tabelle.Bolle.SPEDIZIONE + " = " + Tabelle.Bolle.SPEDIZIONE + " -1 " 
//                        + " WHERE " + Tabelle.Bolle.SPEDIZIONE + " = " + modNum + " AND " + Tabelle.Spedizioni.DATA_CARICO + " = '" + dataCarico + "'";
//                
//                ps = conn.prepareStatement(sql);
//                System.out.println(sql);
//                ps.executeUpdate(); 
//            }
//            
//            ps = conn.prepareStatement("COMMIT");
//            ps.executeUpdate();
//            
//            return true;
//        } catch (SQLException ex) {
//            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//    }
   
    public static int checkTutt(Date dataDoc, int forcedNumber){
        int valoreReturn = 1;
        if (forcedNumber != -1){
             valoreReturn = checkForcedNumber(forcedNumber, dataDoc);
             if(valoreReturn == 1) //numero esistente
                 throw new CheckTuttException("Il numero inserito corrisponde a una fattura esistente.");
             else if (valoreReturn == 2) //Nessun intervallo disponibile
                throw new CheckTuttException("Il numero inserito non è disponibile nell'intervallo, " + 
                                            "oppure non c'è nessun intervallo di fatture alla data selezionata.");
             else 
             return valoreReturn; 
        }else {
            //Il sistema suggerisce automaticamente un numero
            try {
                String dataDocYear = (dataDoc.toString()).substring(0, 4);
                String sqlDataPrec = "SELECT " + Tabelle.Fatture.DATA + ", " + Tabelle.Fatture.NUMERO + " FROM " + 
                        Tabelle.FATTURE + " WHERE " + Tabelle.Fatture.DATA + " BETWEEN '" +  dataDocYear + "-01-01' AND '" + dataDoc + "' ORDER BY " +
                        Tabelle.Fatture.DATA + " DESC, " + Tabelle.Fatture.NUMERO + " DESC LIMIT 1";

                System.out.println(sqlDataPrec);
                ps = conn.prepareStatement(sqlDataPrec);
                rs = ps.executeQuery();

                Date dataPrec = null;
                int numPrec = 0;
                if (rs.next()){
                    dataPrec = rs.getDate(Tabelle.Fatture.DATA);
                    numPrec = rs.getInt(Tabelle.Fatture.NUMERO);
                }

                String sqlDataSucc = "SELECT " + Tabelle.Fatture.DATA + ", " + Tabelle.Fatture.NUMERO + " FROM " + 
                        Tabelle.FATTURE + " WHERE " + Tabelle.Fatture.DATA + " BETWEEN '" + dataDoc + "' AND '" + dataDocYear+"-12-31' AND " + Tabelle.Fatture.NUMERO + " > " + numPrec + " ORDER BY " +
                        Tabelle.Fatture.DATA + " ASC, " + Tabelle.Fatture.NUMERO + " ASC LIMIT 1";

                System.out.println(sqlDataSucc);
                ps = conn.prepareStatement(sqlDataSucc);
                rs = ps.executeQuery();

                Date dataSucc = null;
                int numSucc = 0;
                if (rs.next()){
                    dataSucc = rs.getDate(Tabelle.Fatture.DATA);
                    numSucc = rs.getInt(Tabelle.Fatture.NUMERO);
                }
                //System.out.println("Nprec: " + numPrec + "\nNsucc: " + numSucc);
                //L'utente forza la data e il sistema tenta di trovare un intervallo
                if (numSucc - numPrec == 1) {
                    throw new CheckTuttException("Nessun intervallo di fatture presente alla data selezionata.");

                } else 
                    valoreReturn = numPrec + 1;

                return valoreReturn;
            
            } catch (SQLException ex) {
                Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
                throw new CheckTuttException("C'è stato un problema nella ricerca dei numeri fattura. Ti preghiamo di riprovare");
            }
        }
    }
    
    /*
     * Gestisce l'inserimento della fattura nel caso in cui il numero venga forzato dall'utente
     */
    private static int checkForcedNumber(int forcedNumber, Date documentDate){
        int valoreReturn = -1;
        //Controllo di esistenza del numero
        String dataDocYear = (documentDate.toString()).substring(0, 4);
        String sqlNumExistency = "SELECT " +  Tabelle.Fatture.NUMERO + " FROM " + Tabelle.FATTURE + " WHERE " + Tabelle.Fatture.NUMERO +
                        " = " + forcedNumber + " AND " + Tabelle.Fatture.DATA + " BETWEEN '" +  dataDocYear + "-01-01' AND '" + dataDocYear + "-12-31'";
        System.out.println(sqlNumExistency);
        try{
            PreparedStatement psNumExistency = conn.prepareStatement(sqlNumExistency);
            ResultSet rsNumExistency = psNumExistency.executeQuery();
            if (rsNumExistency.next())
                valoreReturn = 1; //Valore di esistenza del numero
            
            else {
                String sqlLeftJoin = "SELECT " +
                                        "COALESCE(PRECPARTITION."+Tabelle.Fatture.NUMERO+", 0) AS NUMPREC, " +
                                        "PRECPARTITION."+Tabelle.Fatture.DATA+" AS DATAPREC,"+ 
                                        "COALESCE(POSTPARTITION."+Tabelle.Fatture.NUMERO+", 0) AS NUMSUCC," +                        
                                        "POSTPARTITION."+Tabelle.Fatture.DATA+" AS DATASUCC " + 
                                     "FROM ( " +
                                       "SELECT "+Tabelle.Fatture.NUMERO+", " +
                                       Tabelle.Fatture.DATA +" FROM fatture " + 
                                       "WHERE " +Tabelle.Fatture.NUMERO+" < " + forcedNumber + " " +
                                       "and " +Tabelle.Fatture.DATA+ " between '" + dataDocYear + "-01-01' and '" + dataDocYear + "-12-31'" +  
                                       "ORDER BY " +Tabelle.Fatture.NUMERO+ " DESC " + 
	                               "LIMIT 1" + 
                                     ") PRECPARTITION " + 
                                     "left join " +
                                     "(SELECT "+Tabelle.Fatture.NUMERO+", " +
                                       Tabelle.Fatture.DATA +" FROM fatture " + 
                                       "WHERE " +Tabelle.Fatture.NUMERO+" > " + forcedNumber + " " +
                                       "and " +Tabelle.Fatture.DATA+ " between '" + dataDocYear + "-01-01' and '" + dataDocYear + "-12-31'" +  
                                       "ORDER BY " +Tabelle.Fatture.NUMERO+ " " + 
	                               "LIMIT 1" + 
                                       ")POSTPARTITION "+ 
                                       "on 1=1";
                String sqlRightJoin = "SELECT " +
                                        "COALESCE(PRECPARTITION."+Tabelle.Fatture.NUMERO+", 0) AS NUMPREC, " +
                                        "PRECPARTITION."+Tabelle.Fatture.DATA+" AS DATAPREC,"+ 
                                        "COALESCE(POSTPARTITION."+Tabelle.Fatture.NUMERO+", 0) AS NUMSUCC," +                        
                                        "POSTPARTITION."+Tabelle.Fatture.DATA+" AS DATASUCC " + 
                                     "FROM ( " +
                                       "SELECT "+Tabelle.Fatture.NUMERO+", " +
                                       Tabelle.Fatture.DATA +" FROM fatture " + 
                                       "WHERE " +Tabelle.Fatture.NUMERO+" < " + forcedNumber + " " +
                                       "and " +Tabelle.Fatture.DATA+ " between '" + dataDocYear + "-01-01' and '" + dataDocYear + "-12-31'" +  
                                       "ORDER BY " +Tabelle.Fatture.NUMERO+ " DESC " + 
	                               "LIMIT 1" + 
                                     ") PRECPARTITION " + 
                                     "right join " +
                                     "(SELECT "+Tabelle.Fatture.NUMERO+", " +
                                       Tabelle.Fatture.DATA +" FROM fatture " + 
                                       "WHERE " +Tabelle.Fatture.NUMERO+" > " + forcedNumber + " " +
                                       "and " +Tabelle.Fatture.DATA+ " between '" + dataDocYear + "-01-01' and '" + dataDocYear + "-12-31'" +  
                                       "ORDER BY " +Tabelle.Fatture.NUMERO+ " " + 
	                               "LIMIT 1" + 
                                       ")POSTPARTITION "+ 
                                       "on 1=1";
                
                String sql = sqlLeftJoin + " UNION " + sqlRightJoin;
                System.out.println(sql);
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                int numPrec = 0;
                Date dataPrec = null;
                int numSucc = 0;
                Date dataSucc = null;
                if (rs.next()){
                    numPrec = rs.getInt("NUMPREC");
                    dataPrec = rs.getDate("DATAPREC");
                    numSucc = rs.getInt("NUMSUCC");
                    dataSucc = rs.getDate("DATASUCC");
                }else 
                    valoreReturn = 0; //Nessun numero di fattura nell'anno
                
                if (valoreReturn != 0){ //viene posto a 0 nell'istruzione precedente se il resultset è vuoto
                    //La data è minore o uguale a quella della fattura successiva e non ci sono fatture precedenti 
                    if ((numPrec == 0) && (documentDate.compareTo(dataSucc) <= 0)){
                        valoreReturn = 0;
                    }
                    //La data è maggiore o uguale a quella della fattura precedente e non ci sono fatture successive 
                    else if ((numSucc == 0) && (documentDate.compareTo(dataPrec) >= 0)){
                        valoreReturn = 0;
                    }
                    //La data è maggiore o uguale a quella della fattura precedente, minore o uguale a quella della fattura successiva
                    else if ((numPrec > 0) && (numSucc > 0)){
                        if ((documentDate.compareTo(dataPrec) >=0 ) && (documentDate.compareTo(dataSucc) <= 0)){
                            valoreReturn = 0;
                        } else
                            valoreReturn = 2;
                    } else
                        valoreReturn = 2;
                }
            }
        } catch(SQLException ex){
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valoreReturn;
    }

    public static boolean updateModalitaPagamento(Fattura fattura) {
        try {
            
            if (fattura instanceof NotaCredito)
                sql = "UPDATE " + 
                    Tabelle.NOTE_CREDITO + 
                    " SET " + Tabelle.NoteCredito.METODO_PAGAMENTO + " = " + checkNull(fattura.getMetPag()) + 
                    ", " + Tabelle.NoteCredito.DATA_SCADENZA + " = '" + fattura.getDataScadenza() + "' " +
                    " WHERE " +
                        Tabelle.NoteCredito.ID + " = " + ((NotaCredito)fattura).getId();
            else
                sql = "UPDATE " + 
                    Tabelle.FATTURE + 
                    " SET " + Tabelle.Fatture.METODO_PAGAMENTO + " = " + checkNull(fattura.getMetPag()) + 
                    ", " + Tabelle.Fatture.SCADENZA + " = '" + fattura.getDataScadenza() + "' " +
                    " WHERE " +
                    Tabelle.Fatture.NUMERO + " = " + fattura.getNumero() + 
                        " AND " + Tabelle.Fatture.DATA + " = '" + fattura.getData() + "'";
           
            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO_ASF.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
    }
}
