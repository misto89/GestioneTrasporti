/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllo;

import connection.Cleaner;
import contabilizzazione.SaldoContabilitaMensile;
import connection.DAO_ASF;
import connection.DAO_CBC;
import connection.Database;
import connection.MailManager;
import contabilizzazione.SaldoCassaMensile;
import contabilizzazione.SaldoIvaMensile;
import eccezioni.EccezioneConnesioneNonRiuscita;
import entita.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import libs.Mail;
import movimentazionecontante.MovimentazioneContante;
import viste.ColorManager;
/**
 *
 * @author Andle
 */
public abstract class FrontController {
    
    /**
     * Viene richiamata da una vista per visualizzare un'altra vista.
     * @param interfaccia 
     */
    public static void open(Window interfaccia) {
        
        ColorManager color = new ColorManager();
                
        if (interfaccia instanceof JFrame) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            interfaccia.setBounds(0,0,screenSize.width, screenSize.height);
            color.changeColor((JFrame) interfaccia);
            
        } else {
            color.changeColor((JDialog) interfaccia);
            interfaccia.setLocationRelativeTo(null);
        }

        interfaccia.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("img/icon.png")).getImage());
        interfaccia.setVisible(true);

    }
    
    /**
     * Restituisce una lista di oggetti entity (appartententi alla categoria anagrafe) corrispondenti 
     * alla classe specificata nel parametro.
     * @param entita Descrive la classe di cui si vuole restituirne una lista di entità.
     * @return Una lista di entità.
     */
    public static List<Entity> getAnagrafe(Class entita) {
        return DAO_ASF.getAnagrafe(entita);
    }
    
    /**
     * Permette la memorizzazione di un nuovo oggetto.
     * @param o L'oggetto entità da memorizzare.
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean insert(Entity o){
        return DAO_ASF.insert(o);
    }
   
    /**
     * Permette l'aggiornamento di un oggetto esistente.
     * @param o L'oggetto entità da aggiornare
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean update(Entity o) {
        return DAO_ASF.update(o);
    }
    
    /**
     * Permette l'eliminazione di un oggetto esistente.
     * @param o L'oggetto entità da aggiornare.
     * @return true se l'operazione va a buon fine, false altrimenti.
     */
    public static boolean delete(Entity o) {
        if (o instanceof Fattura) {
            if (((Fattura)o).getTipo() != null && ((Fattura)o).getSpedizioni() == null)
                return  DAO_CBC.deleteFatturaAcquisto((Fattura)o);
            else
                return DAO_CBC.deleteFattura((Fattura)o);
        }           
        
        return DAO_ASF.delete(o);
    }
    
    /**
     * Ricerca e restituisce l'insieme dei fornitori che soddisfano la ricerca
     * @param nome Il nome da cercare
     * @param tipo Il tipo di ricerca da effettuare
     * @return Una lista contenente l'insieme dei fornitori che soddisfano la ricerca, oppure null
     * se si verficano errori durante la ricerca
     */
    public static List<Fornitore> getFornitori(int tipo, String ric) {
        return DAO_ASF.getFornitori(tipo, ric);
    }
    
    /**
     * Ricerca e restituisce l'insieme dei fornitori che soddisfano la ricerca
     * @param nome Il nome da cercare
     * @param tipo Il tipo di ricerca da effettuare
     * @return Una lista contenente l'insieme dei fornitori che soddisfano la ricerca, oppure null
     * se si verficano errori durante la ricerca
     */
//    public static List<Fornitore> orderFornitori(int tipo){
//        return DAO_ASF.orderFornitori(tipo);
//    }
    
    /**
     * Ricerca e restituisce l'unico mezzo con la targa corrispondente al parametro.
     * @param targa La targa da ricercare
     * @return Il mezzo corrispondente alla targa
     */
    public static Mezzo getMezzo(String targaRic) {
        return DAO_ASF.getMezzo(targaRic);
    }
    
    /**
     * Restituisce l'elenco di tutte le spedizioni effettuate dal singolo fornitore/cliente
     * specificato dal parametro.
     * @param fornitore Il fornitore di cui recuperare le spedizioni
     * @return Una lista di spedizioni
     */
    public static List<Spedizione> getSpedizioni(Fornitore fornitore, Spedizione.tipo type) {
        return DAO_ASF.getSpedizioni(fornitore, type);
    }
    
    public static boolean cambiaCliente(List<Spedizione> spedizioni, Fornitore cliente) {
        return DAO_ASF.cambiaCliente(spedizioni, cliente);
    }
    
    /**
     * Restituisce l'elenco di tutte le spedizioni effettuate dal singolo fornitore/cliente
     * specificato dal parametro nelle date comprese fra quelle indicate nei parametri.
     * @param fornitore Il fornitore di cui recuperare le spedizioni
     * @param dataInizio La data di inizio dell'intervallo da considerare
     * @param dataFine La data di fine dell'intervallo da considerare
     * @param emesse Indica se recuperare le spedizioni fatturate o meno
     * @return Una lista di spedizioni
     */
    public static List<Spedizione> getSpedizioniDateInterval(Fornitore fornitore, Date dataInizio, Date dataFine, Spedizione.tipo type) {
        return DAO_ASF.getSpedizioniDateInterval(fornitore, dataInizio, dataFine, type);
    }
    
    /**
     * Effettua un test di connessione al database, per verificare se è presente il file
     * di configurazione nella cartella utente, e se non ci sono problemi nei parametri per 
     * la connessione
     * @throws EccezioneConnesioneNonRiuscita Viene sollevata in caso la connessione abbia un esito negativo
     */
    public static void test() throws EccezioneConnesioneNonRiuscita {
        DAO_ASF.test();
    }
    
    /*
     * Valuta il successivo numero della fattura da memorizzare nel database, controllando l'anno di inserimeto.
     */
    public static int getNumber(Class table, Date data){
        return DAO_ASF.getNumber(table, data);
    }
    
    public static String[] getUnitaMisura() {
        String[] um = {null, "Pd", "Pz", "Cc", "q.li", "Kg", "mc"};
        return um;
    }
    
    public static String[] getMetodiPagamento() {
        String[] metPagam = {null, "Contante", "Bonifico Bancario", "Accredito c/c" , "Assegno Bancario", "RIBA", "Rimessa diretta"};
        return metPagam;
    }
        
    public static List<Integer> getAnniEsercizio(Fattura.tipo tipo) {
        return DAO_CBC.getAnniEsercizio(tipo);
    }

    public static List<Fattura> getFatture(Integer anno, Fornitore cliente, boolean[] mesi, Fattura.pagata pagata, Date dataIntIn, Date dataIntFin) {
        return DAO_CBC.getFatture(anno, cliente, mesi, pagata, dataIntIn, dataIntFin);
    }
   
    public static List<Fattura> getFatture(Fornitore forn_cliente, Fattura.tipo tipo) {
        return DAO_CBC.getFatture(forn_cliente, tipo);
    }
    
    public static int checkTutt(Date dataDoc, int forcedNumber){
        return DAO_ASF.checkTutt(dataDoc, forcedNumber);
    }
    
    public static boolean insertFattAcquisto(Fattura fatt) {
        return DAO_CBC.insertFattAcquisto(fatt);
    }
    
    public static List<Fattura> getFattureAcquisto(Integer anno, Fornitore cliente, boolean[] mesi, Fattura.pagata pagata, String tipo, Date dataIntIn, Date dataIntFin) {
        return DAO_CBC.getFattureAcquisto(anno, cliente, mesi, pagata, tipo, dataIntIn, dataIntFin);
    }

    public static void updatePagataFattura(Fattura.tipo tipo, Fattura fattura, boolean pagata) {
        DAO_CBC.updatePagamentoFattura(tipo, fattura, pagata, null);
    }
    
    public static void updatePagataFattura(Fattura.tipo tipo, Fattura fattura, boolean pagata, List<Movimento> movimenti) {
        DAO_CBC.updatePagamentoFattura(tipo, fattura, pagata, movimenti);
    }
    
    public static List<SaldoContabilitaMensile> getContabilitaFatture(String[] anniMesi, Fattura.pagata tipo, int fornCliente){
       return DAO_CBC.getContabilitaFatture(anniMesi, tipo, fornCliente);
    }
    
    public static List<SaldoCassaMensile> getMovimentazioneCassaMensile(String[] anniMesi, int fornCliente, Fattura.tipo tipoMov){
        return DAO_CBC.getMovimentazioneCassaMensile(anniMesi, fornCliente, tipoMov);
    }
    
    public static boolean doCashMovement(MovimentazioneContante m) {
        return DAO_CBC.doCashMovement(m);
    }
    
    public static List<MovimentazioneContante> getCashMovements (String[] anniMesi){
        return DAO_CBC.getCashMovements(anniMesi);
    }
    
    public static boolean sendMail(List<String> to, String cc, String subject, String text, File file) throws AddressException, MessagingException {
        return Mail.send(to, cc, subject, text, file);
    }
    
    public static boolean sendMail(List<String> to, String cc, String subject, String text) throws AddressException, MessagingException {
        return Mail.send(to, cc, subject, text);
    }
    
    public static MailManager getMailParams() {
        return MailManager.getParams();
    }
    
    public static String backup() throws IOException {
        return new Database().backup();
    }
    
    public static void restore(File file) throws IOException {
        new Database().restore(file);
    }
    
    public static List<SaldoIvaMensile> getSaldoIvaMensile(String[] anniMesi){
        return DAO_CBC.getSaldoIvaMensile(anniMesi);
    }
    
    public static boolean updateMovimentazioneContante(MovimentazioneContante movimento) {
        return DAO_CBC.updateMovimentoContante(movimento);
    }

    public static boolean deleteTempFiles() {
        return new Cleaner().cleanTempFiles();
    }

    public static boolean updateFatturaAcquisto(Fattura fatt, Fattura old) {
        return DAO_CBC.updateFatturaAcquisto(fatt, old);
    }

    public static boolean updateModalitaPagamento(Fattura fattura) {
        return DAO_ASF.updateModalitaPagamento(fattura);
    }
}
