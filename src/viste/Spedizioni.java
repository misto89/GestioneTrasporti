/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Spedizioni.java
 *
 * Created on 21-apr-2012, 11.48.06
 */
package viste;

import libs.DoubleFormatter;
import com.itextpdf.text.DocumentException;
import controllo.FrontController;
import eccezioni.CheckTuttException;
import eccezioni.EccezioneValoreCampoTroppoLungo;
import entita.Entity;
import entita.Fattura;
import entita.Spedizione;
import entita.Fornitore;
import entita.Mezzo;
import entita.Movimento;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import libs.DateUpdate;
import stampa.StampaSpedizioni;

/**
 *
 * @author Michele
 */
public class Spedizioni extends javax.swing.JFrame {

    private void calcolaTotale() {
        double importo = 0.00;
        double sconto = 0.00;
        double imponibile = 0.00;
        double iva = 0.00;
        double totale = 0.00;
        
        int percIva = 21;
        try {
            percIva = Integer.parseInt(txtPercIvaForfait.getText());
        } catch (NumberFormatException e) {
            txtPercIvaForfait.setText(String.valueOf(percIva));
        }
            
        
        if (!(txtImpForfait.getText().equals(""))) {
            try {
                importo = Double.parseDouble(txtImpForfait.getText());
                
            } catch (NumberFormatException e) {
                txtImpForfait.setText(Double.toString(importo));
            }
            
            
            if (!(txtScontoForfait.getText().equals(""))) {
                try {
                    sconto = Double.parseDouble(txtScontoForfait.getText());
                    
                } catch (NumberFormatException e) {
                    txtScontoForfait.setText(String.valueOf(sconto));
                }
                
                imponibile = importo - sconto;
                
            } else {
                imponibile = importo;
            }
            
            txtImponibileForfait.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(imponibile)));
            
            iva = imponibile * percIva/100.0;
            
            txtIvaForfait.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(iva)));
            
            totale= imponibile+iva;
            
            txtTotForfait.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totale)));
        }
        
    }
    
    private class SpedizioniTableModel extends DefaultTableModel {

        private final String[] COLONNE;
        private boolean[] canEdit;
        private final Class[] TYPES = {
            String.class, String.class, Object.class, Object.class, Object.class, String.class, String.class, Double.class, Double.class,
            Double.class, Double.class, Double.class, Double.class, Double.class, String.class, Character.class, String.class, Integer.class
        };
        
        
        public SpedizioniTableModel(Object[][] righe, String[] colonne, boolean[] edit) {
            super(righe, colonne);
            COLONNE = colonne;
            canEdit = edit;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return TYPES[columnIndex];
        }  
                
        /*       
        public void setEditable(boolean[] edit) {
            canEdit = edit;
        }*/
               
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }

        /*
         * Preso in input l'indice e il valore di un campo di tipo intero, verifica che il valore sia
         * effettivamente un intero. Restituisce 0 se il campo è stato lasciato vuoto, null se il valore inserito
         * non è valido
         */
        private Integer checkIntField(int index, Object campo) {
            try {
                if (campo instanceof String)
                    return Integer.parseInt((String)campo);
                else
                    return (Integer) campo;

            } catch (NumberFormatException e) {
                if (!((String)campo).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Valore immesso per il campo " + COLONNE[index] + " non valido!", "Formato non valido", JOptionPane.ERROR_MESSAGE);
                    return null;
                } else 
                    return 0;

            }
        }

        /*
         * Preso in input l'indice e il valore di un campo di tipo double, verifica che il valore sia
         * effettivamente un double. Restituisce 0.0 se il campo è stato lasciato vuoto, null se il valore inserito
         * non è valido
         */
        private Double checkDoubleField(int index, Object campo) {
            try {
                if (campo instanceof String)
                    return Double.parseDouble((String)campo);
                else
                    return (Double) campo;

            } catch (NumberFormatException e) {
                if (!((String)campo).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Valore immesso per il campo " + COLONNE[index] + " non valido!", "Formato non valido", JOptionPane.ERROR_MESSAGE);
                    return null;
                } else 
                    return 0.0;

            }
        }
               
        @Override
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);
            //if (!mnuSelezionate.isSelected())
                //mnuSelezionate.setEnabled(false);
            
//            if (!chkEmesse.isSelected()) {
                /*
                 * Dopo aver acquisito la stringa corrispondente alle bolle, dal campo della tabella, verifica
                 * che la stringa rispetti il formato corretto, dopodiché ad ogni '-' le separa mettendole in un
                 * elemento di un array
                 */
                String stringaBolle = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), BOLLE);
                if (stringaBolle != null)
                    if (!stringaBolle.isEmpty())
                        if (!checkBolle(stringaBolle)) {
                            JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[BOLLE] + " non valido! Inserire i numeri delle bolle separati da un -", 
                                "Formato errato", JOptionPane.ERROR_MESSAGE);
                            ricaricaTabella();
                            return;
                        }

                String[] bolle  = null;
                if (stringaBolle != null)
                    if (!stringaBolle.isEmpty())
                        bolle = stringaBolle.split("-");


                String num = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), NUMERO);
                String dataCarico = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), DATA_CARICO);
                String dataDocumento = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), DATA_DOCUMENTO);

                /*
                 * Se la data di carico non è vuota ma non rispetta il formato corretto, mostra un messaggio di errore,
                 * dopodiché ricarica la tabella
                 */
                if (!dataCarico.isEmpty()) {
                    if (!checkData(dataCarico)) {
                        JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[DATA_CARICO] + " non valido! Inserire la data nel formato gg/mm/aaaa", 
                            "Formato errato", JOptionPane.ERROR_MESSAGE);
                        ricaricaTabella();
                        return;
                    }

                } else { //altrimenti se la data è vuota, mostra un messaggio di errore e ricarica la tabella
                    JOptionPane.showMessageDialog(null, "Il campo " + COLONNE[DATA_CARICO] + " non può essere vuoto! Inserire la data nel formato gg/mm/aaaa", 
                        "Formato errato", JOptionPane.ERROR_MESSAGE);
                    ricaricaTabella();
                    return;
                } 

                /*
                 * Se la data documento non è vuota ma non rispetta il formato corretto, mostra un messaggio di errore,
                 * dopodiché ricarica la tabella
                 */ 
                if (!dataDocumento.isEmpty())
                    if (!checkData(dataDocumento)) {
                        JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[DATA_DOCUMENTO] + " non valido! Inserire la data nel formato gg/mm/aaaa", 
                            "Formato errato", JOptionPane.ERROR_MESSAGE);
                        ricaricaTabella();
                        return;
                    }

                String descrizione = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), DESCRIZIONE);
                if (descrizione!= null && descrizione.length() > MAX_LENGTH_DESCRIZIONE) {
                    JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[DESCRIZIONE] + " troppo lungo! Sono ammessi massimo " + MAX_LENGTH_DESCRIZIONE + 
                            " caratteri", "Formato errato", JOptionPane.ERROR_MESSAGE);
                    ricaricaTabella();
                    return;
                }
                
                
                String um1 = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), UM);

                String mezzo = null;
                Object mezz = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), MEZZO);
                if (mezz instanceof Mezzo) //E' stato selezionato dalla combo quindi il valore inserito è un Mezzo
                    mezzo = ((Mezzo)mezz).getTarga();
                else  //E' stato modificato un altro campo quindi il valore acquisito per il mezzo è una stringa
                    mezzo =  (String) mezz;
                
                if (mezzo != null && mezzo.equals(""))
                    mezzo = null;

                /*
                 * Per tutti i campi che seguono, viene acquisito il rispettivo valore, viene controllata
                 * la correttezza del valore, mostrando un messaggio di errore in caso negativo e ricaricando
                 * la tabella con in valori precedenti.
                 */

                Object quant1 = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), QTA);
                Double qta1 = checkDoubleField(QTA, quant1);
                if (qta1 == null) {
                    ricaricaTabella();
                    return;
                }

                Object tr1 = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), TRAZ);
                Double traz1 = checkDoubleField(TRAZ, tr1);
                if (traz1 == null) {
                    ricaricaTabella();
                    return;
                }

                Object distr1 = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), DISTRIB);
                Double distrib1 = checkDoubleField(DISTRIB, distr1);
                if (distrib1 == null) {
                    ricaricaTabella();
                    return;
                }

                Object impProv = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), PROVVIGIONE);
                Double importoProvv = checkDoubleField(PROVVIGIONE, impProv);
                if (importoProvv == null) {
                    ricaricaTabella();
                    return;
                }
                
                /*
                Object sc = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), SCONTO);
                Integer sconto = checkIntField(SCONTO, sc);
                if (sconto == null) {
                    ricaricaTabella();
                    return;
                }

                Object pProv = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), PROVVIGIONE);
                Integer percProvv = checkIntField(PROVVIGIONE, pProv);
                if (percProvv == null) {
                    ricaricaTabella();
                    return;
                }

                Object pIva = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), IVA);
                Integer percIva = checkIntField(IVA, pIva);
                if (percIva == null) {
                    ricaricaTabella();
                    return;
                }*/

                Object imp = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), IMPORTO);
                Double importo = checkDoubleField(IMPORTO, imp);
                if (importo == null) {
                    ricaricaTabella();
                    return;
                }
                
                Object valMerce = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), VAL_MERCE);
                Double valoreMerce = checkDoubleField(VAL_MERCE, valMerce);
                if (valoreMerce == null) {
                    ricaricaTabella();
                    return;
                }

                boolean rientrata;
                char valRientrata = (Character) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), RIENTRATA);
                if (valRientrata == 'S')
                    rientrata = true;
                else
                    rientrata = false;                    
                
                String note = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), NOTE);

                Object imponib = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), IMPONIBILE);
                Double imponibile = checkDoubleField(IMPONIBILE, imponib);
                if (imponibile == null) {
                    ricaricaTabella();
                    return;
                }

                Object nFattura = tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), FATTURA);
                Integer numFattura = null;
                try {
                    String nF = (String) nFattura;
                    if (nF != null)
                    if (!nF.isEmpty())
                        numFattura = Integer.parseInt(nF);

                } catch (ClassCastException e) {
                    numFattura = (Integer) nFattura;
                }


                double totale;
                double iva;

                //Converte la data carico nel formato corretto da poter inserire nel db
                String[] dataArray = dataCarico.split("/");
                Date dataCaricoDate = null;
                
                if (dataArray[2].length() == 2)
                    dataArray[2] = "20" + dataArray[2];
                
                else if (dataArray[2].length() == 3)
                    dataArray[2] = "2" + dataArray[2];
    
                if (dataArray[1].length() == 1)
                    dataArray[1] = "0" + dataArray[1];

                if (dataArray[0].length() == 1) 
                    dataArray[0] = "0" + dataArray[0];
                
                try {
                    dataCaricoDate = Date.valueOf(dataArray[2] + "-" + dataArray[1] + "-" + dataArray[0]);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[DATA_CARICO] + " non valido! Inserire la data nel formato gg/mm/aaaa", 
                            "Formato errato", JOptionPane.ERROR_MESSAGE);
                        ricaricaTabella();
                        return;
                }

                //Converte la data documento nel formato corretto da poter inserire nel db se non è vuota, altrimenti assegna null
                Date dataDocumentoDate = null;
                if (!dataDocumento.isEmpty()) {
                    dataArray = dataDocumento.split("/");
                    
                    if (dataArray[2].length() == 2)
                        dataArray[2] = "20" + dataArray[2];
                    
                    else if (dataArray[2].length() == 3)
                        dataArray[2] = "2" + dataArray[2];
    
                    if (dataArray[1].length() == 1)
                        dataArray[1] = "0" + dataArray[1];

                    if (dataArray[0].length() == 1) 
                        dataArray[0] = "0" + dataArray[0];
                    
                    try {
                        dataDocumentoDate = Date.valueOf(dataArray[2] + "-" + dataArray[1] + "-" + dataArray[0]);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(null, "Valore inserito per il campo " + COLONNE[DATA_DOCUMENTO] + " non valido! Inserire la data nel formato gg/mm/aaaa", 
                            "Formato errato", JOptionPane.ERROR_MESSAGE);
                        ricaricaTabella();
                        return;
                    }

                }
                
                Date dataFattura = spedizioniInTabella.get(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow())).getDataFattura();
                
                int sconto = spedizioniInTabella.get(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow())).getSconto();
                double percProvv = spedizioniInTabella.get(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow())).getPercProvv();
                int percIva = spedizioniInTabella.get(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow())).getPercIva();

                //Effettua il calcolo dei valori per determinare il totale

                //Controlla se si sta modificando la cella dell'importo per inserire un valore fortettario.
                boolean forfettario = false;
                if (tblSpedizioni.getSelectedColumn() == IMPORTO)
                    forfettario = true;
                
                if ((traz1 != 0 || distrib1 != 0) && !forfettario)
                    importo = (qta1 * traz1) + (qta1 * distrib1);

                double valoreSconto = importo * (sconto / 100.0); 
//                provv = valoreMerce * (percProvv / 100.0);
                imponibile = importo - valoreSconto + importoProvv;
                iva = (percIva / 100.0) * (imponibile);

                totale = imponibile + iva;
                
                char stato;
                String statoString = (String) tblSpedizioni.getValueAt(tblSpedizioni.getSelectedRow(), STATO);
                if (statoString.equals("Consegna"))
                    stato = 'C';
                else
                    stato = 'R';

                //Crea l'oggetto spedizione da modificare, aggiungendovi in seguito le bolle estratte dalla stringa

                Spedizione sp = new Spedizione(num, id_fornitore, dataCaricoDate, dataDocumentoDate, descrizione, mezzo, 
                    um1, qta1, traz1, distrib1, importo, sconto, percIva, iva, 
                    percProvv, importoProvv, totale, note, rientrata, numFattura, dataFattura, valoreMerce, imponibile, stato);


                if (bolle != null) {
                    for (String bolla : bolle)
                    sp.addBolla(bolla);
                }

                //Per evitare problemi la data carico non deve essere modificata, quindi il codice seguente è stato commentato
                /*
                if (column == DATA_CARICO) {
                    /*
                     * Si sta modificando la colonna Data carico. Dato che quest'ultimo campo fa parte
                     * della chiave primaria, la modifica non avrebbe effetto. E' necessario quindi 
                     * eliminare prima la spedizione corrente e poi inserirne una uguale ma con la nuova
                     * data carico.
                     
                    try {
                        FrontController.delete(spedizioniInTabella.get(row));
                        FrontController.insert(sp);
                        spedizioniInTabella.set(tblSpedizioni.getSelectedRow(), sp);
                    } catch (EccezioneValoreCampoTroppoLungo e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                    } finally {
                        ricaricaTabella();
                    }
                    
                } else { 
                */
                    /*
                     * Tenta l'aggiornamento mostrando un messaggio di errore in caso negativo, e 
                     * in ogni caso ricaricando la tabella per mostrare i valori aggiornati.
                     */
                try {
                    FrontController.update(sp);
                    spedizioniInTabella.set(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow()), sp);
                } catch (EccezioneValoreCampoTroppoLungo e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                } finally {
                    ricaricaTabella();
                }
                //}
//            }
        }
    }
    
    /** Creates new form Spedizioni */
    public Spedizioni() {
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlForfait);
        color.changeColor(pnlRiepilogo);
        vista = this;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboFornitori = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSpedizioni = new javax.swing.JTable();
        jSeparator2 = new javax.swing.JSeparator();
        btnNew = new javax.swing.JButton();
        btnElimina = new javax.swing.JButton();
        chkEmesse = new javax.swing.JCheckBox();
        pnlRiepilogo = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtImpTot = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtScontoTot = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtProvvigioneTot = new javax.swing.JTextField();
        txtImponibile = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        txtTotale = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtGiornoFatt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMeseFatt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAnnoFatt = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtNumFatt = new javax.swing.JTextField();
        btnEmetti = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboGiorni = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        chkPagata = new javax.swing.JCheckBox();
        chkForfait = new javax.swing.JCheckBox();
        pnlForfait = new javax.swing.JPanel();
        lblImpForfait = new javax.swing.JLabel();
        txtTotForfait = new javax.swing.JTextField();
        lblTotForfait = new javax.swing.JLabel();
        txtPercIvaForfait = new javax.swing.JTextField();
        txtImpForfait = new javax.swing.JTextField();
        txtScontoForfait = new javax.swing.JTextField();
        lblImponibileForfait = new javax.swing.JLabel();
        txtImponibileForfait = new javax.swing.JTextField();
        lblPercIvaForfait = new javax.swing.JLabel();
        lblIvaForfait = new javax.swing.JLabel();
        txtIvaForfait = new javax.swing.JTextField();
        lblScontoForfait = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboMetPag = new javax.swing.JComboBox();
        txtNote = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtGiornoScadenza = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtMeseScadenza = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtAnnoScadenza = new javax.swing.JTextField();
        btnModifica = new javax.swing.JButton();
        chkNonFatturate = new javax.swing.JCheckBox();
        btnRegistroEmesse = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFiltra = new javax.swing.JMenu();
        mnuTutti = new javax.swing.JCheckBoxMenuItem();
        mnuIntervalloDate = new javax.swing.JCheckBoxMenuItem();
        mnuSelezionate = new javax.swing.JCheckBoxMenuItem();
        mnuSped = new javax.swing.JMenu();
        mnuStorico = new javax.swing.JMenuItem();
        mnuCambiaCliente = new javax.swing.JMenuItem();
        mnuStampa = new javax.swing.JMenuItem();

        setTitle("Spedizioni");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Cliente");

        cboFornitori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleziona" }));
        cboFornitori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornitoriActionPerformed(evt);
            }
        });

        tblSpedizioni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblSpedizioni);

        btnNew.setBackground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuovaspedizione.png"))); // NOI18N
        btnNew.setText("Inserisci Nuova");
        btnNew.setToolTipText("Inserisci una nuova sperizione per il cliente selezionato");
        btnNew.setEnabled(false);
        btnNew.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnElimina.setBackground(new java.awt.Color(255, 255, 255));
        btnElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancella.png"))); // NOI18N
        btnElimina.setText("Elimina spedizione");
        btnElimina.setToolTipText("Elimina la spedizione selezionata");
        btnElimina.setEnabled(false);
        btnElimina.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaActionPerformed(evt);
            }
        });

        chkEmesse.setText("Fatturate");
        chkEmesse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEmesseActionPerformed(evt);
            }
        });

        pnlRiepilogo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Riepilogo Fattura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel21.setText("Importo totale");

        txtImpTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpTot.setEnabled(false);
        txtImpTot.setFocusable(false);

        jLabel22.setText("Sconto totale");

        txtScontoTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtScontoTot.setEnabled(false);
        txtScontoTot.setFocusable(false);

        jLabel23.setText("Provvigione");

        jLabel24.setText("Imponibile");

        txtProvvigioneTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProvvigioneTot.setEnabled(false);
        txtProvvigioneTot.setFocusable(false);

        txtImponibile.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImponibile.setEnabled(false);
        txtImponibile.setFocusable(false);

        jLabel25.setText("Totale finale");

        jLabel26.setText("IVA");

        txtIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIva.setEnabled(false);
        txtIva.setFocusable(false);

        txtTotale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotale.setEnabled(false);
        txtTotale.setFocusable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel9.setText("Data Fattura");

        jLabel10.setText("gg");

        txtGiornoFatt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiornoFattFocusLost(evt);
            }
        });

        jLabel11.setText("mm");

        txtMeseFatt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMeseFattFocusLost(evt);
            }
        });

        jLabel12.setText("aaaa");

        txtAnnoFatt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAnnoFattFocusLost(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel27.setText("Numero Fattura");

        txtNumFatt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumFattFocusLost(evt);
            }
        });

        btnEmetti.setBackground(new java.awt.Color(255, 255, 255));
        btnEmetti.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnEmetti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/emettifattura.png"))); // NOI18N
        btnEmetti.setText("Emetti Fattura");
        btnEmetti.setToolTipText("Emetti la fattura per le spedizioni selezionate");
        btnEmetti.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnEmetti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmettiActionPerformed(evt);
            }
        });

        jLabel2.setText("Pagamento");

        cboGiorni.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105", "110", "115", "120" }));
        cboGiorni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiorniActionPerformed(evt);
            }
        });

        jLabel3.setText("Giorni");

        chkPagata.setFont(new java.awt.Font("Tahoma", 1, 12));
        chkPagata.setText("Fattura pagata");
        chkPagata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPagataActionPerformed(evt);
            }
        });

        chkForfait.setFont(new java.awt.Font("Tahoma", 1, 12));
        chkForfait.setText("Importo Forfettario");
        chkForfait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkForfaitActionPerformed(evt);
            }
        });

        lblImpForfait.setText("Importo");

        txtTotForfait.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtTotForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotForfait.setText("0,00");
        txtTotForfait.setEnabled(false);
        txtTotForfait.setFocusable(false);

        lblTotForfait.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblTotForfait.setText("Totale");

        txtPercIvaForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPercIvaForfait.setText("21");
        txtPercIvaForfait.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPercIvaForfaitFocusLost(evt);
            }
        });

        txtImpForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpForfait.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImpForfaitFocusLost(evt);
            }
        });

        txtScontoForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtScontoForfait.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtScontoForfaitFocusLost(evt);
            }
        });

        lblImponibileForfait.setText("Imponibile");

        txtImponibileForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImponibileForfait.setText("0,00");
        txtImponibileForfait.setEnabled(false);
        txtImponibileForfait.setFocusable(false);

        lblPercIvaForfait.setText("IVA");

        lblIvaForfait.setText("IVA");

        txtIvaForfait.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaForfait.setText("0,00");
        txtIvaForfait.setEnabled(false);
        txtIvaForfait.setFocusable(false);

        lblScontoForfait.setText("Sconto");

        jLabel15.setText("€");

        jLabel16.setText("€");

        jLabel17.setText("€");

        jLabel18.setText("€");

        jLabel19.setText("€");

        jLabel20.setText("%");

        javax.swing.GroupLayout pnlForfaitLayout = new javax.swing.GroupLayout(pnlForfait);
        pnlForfait.setLayout(pnlForfaitLayout);
        pnlForfaitLayout.setHorizontalGroup(
            pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlForfaitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImpForfait)
                    .addComponent(lblImponibileForfait)
                    .addComponent(lblIvaForfait))
                .addGap(18, 18, 18)
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtIvaForfait, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(txtImponibileForfait, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addComponent(txtImpForfait, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlForfaitLayout.createSequentialGroup()
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPercIvaForfait)
                            .addComponent(lblScontoForfait))
                        .addGap(28, 28, 28))
                    .addGroup(pnlForfaitLayout.createSequentialGroup()
                        .addComponent(lblTotForfait)
                        .addGap(18, 18, 18)))
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtScontoForfait, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(txtPercIvaForfait, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(txtTotForfait, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlForfaitLayout.setVerticalGroup(
            pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlForfaitLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblImpForfait)
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlForfaitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlForfaitLayout.createSequentialGroup()
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblScontoForfait)
                            .addComponent(txtScontoForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPercIvaForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPercIvaForfait)
                            .addComponent(jLabel20)))
                    .addGroup(pnlForfaitLayout.createSequentialGroup()
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImpForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(6, 6, 6)
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImponibileForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblImponibileForfait)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlForfaitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIvaForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotForfait)
                            .addComponent(txtTotForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIvaForfait)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))))
                .addGap(21, 21, 21))
        );

        pnlForfaitLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel15, jLabel16, txtImpForfait, txtImponibileForfait});

        pnlForfaitLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel18, txtScontoForfait});

        pnlForfaitLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel19, txtTotForfait});

        pnlForfaitLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel20, txtPercIvaForfait});

        jLabel4.setText("Note");

        jLabel5.setText("€");

        jLabel6.setText("€");

        jLabel7.setText("€");

        jLabel8.setText("€");

        jLabel13.setText("€");

        jLabel14.setText("€");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel28.setText("Scadenza");

        jLabel29.setText("gg");

        jLabel30.setText("mm");

        jLabel31.setText("aaaa");

        javax.swing.GroupLayout pnlRiepilogoLayout = new javax.swing.GroupLayout(pnlRiepilogo);
        pnlRiepilogo.setLayout(pnlRiepilogoLayout);
        pnlRiepilogoLayout.setHorizontalGroup(
            pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(txtNumFatt, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGiornoFatt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMeseFatt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addGap(1, 1, 1)
                                .addComponent(txtAnnoFatt, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(28, 28, 28)
                                .addComponent(cboMetPag, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboGiorni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel3))
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addComponent(chkPagata)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel21))
                                .addGap(18, 18, 18)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtScontoTot)
                                    .addComponent(txtImpTot, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(chkForfait)))
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtGiornoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMeseScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31)
                        .addGap(1, 1, 1)
                        .addComponent(txtAnnoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEmetti, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtImponibile)
                            .addComponent(txtProvvigioneTot, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(60, 60, 60))
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)))
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTotale)
                            .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addComponent(pnlForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel28, jLabel9});

        pnlRiepilogoLayout.setVerticalGroup(
            pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtImpTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel27)
                            .addComponent(txtNumFatt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtGiornoFatt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(txtMeseFatt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(txtAnnoFatt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtScontoTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel6)))
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cboGiorni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(cboMetPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                            .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel25)
                                .addComponent(jLabel14)))
                        .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                            .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtProvvigioneTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23)
                                .addComponent(jLabel26)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtImponibile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24)
                                .addComponent(jLabel8)))))
                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pnlForfait, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                        .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(chkForfait))
                            .addGroup(pnlRiepilogoLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(txtGiornoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30)
                                    .addComponent(txtMeseScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31)
                                    .addComponent(txtAnnoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlRiepilogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(chkPagata)
                                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))))
                        .addGap(18, 18, 18)
                        .addComponent(btnEmetti)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, txtImpTot});

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, txtScontoTot});

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel7, txtProvvigioneTot});

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel8, txtImponibile});

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, txtIva});

        pnlRiepilogoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel14, txtTotale});

        btnModifica.setBackground(new java.awt.Color(255, 255, 255));
        btnModifica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modifica.png"))); // NOI18N
        btnModifica.setText("Modifica spedizione");
        btnModifica.setToolTipText("Modifica la spedizione");
        btnModifica.setEnabled(false);
        btnModifica.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificaActionPerformed(evt);
            }
        });

        chkNonFatturate.setText("Non Fatturate");
        chkNonFatturate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNonFatturateActionPerformed(evt);
            }
        });

        btnRegistroEmesse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/regfattemesse.png"))); // NOI18N
        btnRegistroEmesse.setToolTipText("Vai al registro fatture emesse");
        btnRegistroEmesse.setLabel("Registro fatture emesse");
        btnRegistroEmesse.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnRegistroEmesse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroEmesseActionPerformed(evt);
            }
        });

        mnuFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filtra.png"))); // NOI18N
        mnuFiltra.setText("Filtra");
        mnuFiltra.setEnabled(false);

        mnuTutti.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnuTutti.setSelected(true);
        mnuTutti.setText("Mostra tutti");
        mnuTutti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mostratutto.png"))); // NOI18N
        mnuTutti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTuttiActionPerformed(evt);
            }
        });
        mnuFiltra.add(mnuTutti);

        mnuIntervalloDate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        mnuIntervalloDate.setSelected(true);
        mnuIntervalloDate.setText("Per intervallo date documento");
        mnuIntervalloDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/intervallodate.png"))); // NOI18N
        mnuIntervalloDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuIntervalloDateActionPerformed(evt);
            }
        });
        mnuFiltra.add(mnuIntervalloDate);

        mnuSelezionate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSelezionate.setText("Selezionate");
        mnuSelezionate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/selezionate.png"))); // NOI18N
        mnuSelezionate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSelezionateActionPerformed(evt);
            }
        });
        mnuFiltra.add(mnuSelezionate);

        jMenuBar1.add(mnuFiltra);

        mnuSped.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/spedizioni.png"))); // NOI18N
        mnuSped.setText("Spedizioni");

        mnuStorico.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnuStorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/storico_spedizioni.png"))); // NOI18N
        mnuStorico.setText("Storico Spedizioni");
        mnuStorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStoricoActionPerformed(evt);
            }
        });
        mnuSped.add(mnuStorico);

        mnuCambiaCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuCambiaCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cambiacliente.png"))); // NOI18N
        mnuCambiaCliente.setText("Cambia cliente");
        mnuCambiaCliente.setEnabled(false);
        mnuCambiaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCambiaClienteActionPerformed(evt);
            }
        });
        mnuSped.add(mnuCambiaCliente);

        mnuStampa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnuStampa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stampa.png"))); // NOI18N
        mnuStampa.setText("Stampa");
        mnuStampa.setEnabled(false);
        mnuStampa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStampaActionPerformed(evt);
            }
        });
        mnuSped.add(mnuStampa);

        jMenuBar1.add(mnuSped);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cboFornitori, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(chkEmesse)
                                .addGap(18, 18, 18)
                                .addComponent(chkNonFatturate))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pnlRiepilogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRegistroEmesse, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(430, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnElimina, btnModifica, btnNew});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cboFornitori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkEmesse)
                            .addComponent(chkNonFatturate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRiepilogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                        .addComponent(btnModifica, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
                        .addComponent(btnElimina))
                    .addComponent(btnRegistroEmesse, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnElimina, btnModifica, btnNew, btnRegistroEmesse});

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
     * Popola la select dei fornitori con tutti questi.
     */
    private void popolaSelect(List<Entity> items) {
        for (Entity item : items)
            cboFornitori.addItem((Fornitore) item);
        
    }

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    List<Entity> fornitori = FrontController.getAnagrafe(Fornitore.class);
    popolaSelect(fornitori);
    
    pnlForfait.setVisible(false);
    chkNonFatturate.setSelected(true);
    pnlRiepilogo.setVisible(false);
    
    String[] metPagam = FrontController.getMetodiPagamento();
    for (String metodo : metPagam)
        cboMetPag.addItem((String) metodo);
    
    cboMetPag.setSelectedItem(metPagam[metPagam.length-1]); //Seleziona l'ultimo metodo di pagamento (Rimessa diretta)
    
    //Set the locks
    txtGiornoFatt.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
    txtMeseFatt.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
    txtAnnoFatt.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
    txtGiornoScadenza.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
    txtMeseScadenza.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
    txtAnnoScadenza.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
    txtPercIvaForfait.setDocument(new JTextFieldLimit(MAX_LENGTH_PERCIVA));
    
    txtImpForfait.setDocument(new JTextFieldFormatDouble());
    txtScontoForfait.setDocument(new JTextFieldFormatDouble());
}//GEN-LAST:event_formWindowOpened

/*
 * Verifica che il campo per l'inserimento delle bolle contenga una serie di interi
 * separati da un un '-'
 */
private boolean checkBolle(String bolle) {
//    Pattern pattern = Pattern.compile("(\\d){1,}(/[a-z]{1,}){0,1}(-(\\d){1,}(/[a-z]{1,}){0,1})*");
//    Matcher match = pattern.matcher(bolle);
//    return match.matches();    
    
    return true;
}

/*
 * Verifica che i campi per le date contengano date nel formato gg/mm/aaaa
 */
private boolean checkData(String data) {
    Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
    Matcher match = pattern.matcher(data);
    return match.matches();
      
}

/*
 * Popola la tabella con tutte le spedizioni effetttuate da/ad un fornitore/cliente
 */
private void popolaTabella(List<Spedizione> spedizioni, boolean[] canEdit) {
    spedizioniInTabella = spedizioni;
    spedizioniDaFatt.clear();
    for (Spedizione s: spedizioniInTabella)
        spedizioniDaFatt.add(s);
    
    Object[] arSped = spedizioni.toArray();
    Object[][] arraySped = new Object[arSped.length][Spedizione.NUM_CAMPI];
    int contSped = 0;
    for (Object sped : arSped) {
        arraySped[contSped++] = ((Spedizione) sped).toArray();
    }
       
    final String[] COLONNE = {
        "BOLLE", "STATO", "NUMERO", "DATA CARICO", "DATA DOCUMENTO", "DESCRIZIONE", "UM", "QTA", "TRAZ.", 
        "DISTRIB.", "IMPORTO", "IMPONIBILE", "VAL. MERCE", "PROVVIGIONE", "NOTE", "RIENTRATA", "MEZZO", "NUM. FATTURA"
    };
       
    SpedizioniTableModel model = new SpedizioniTableModel(arraySped, COLONNE, canEdit);
    tblSpedizioni.setModel(model);
    tblSpedizioni.setRowSorter(new TableRowSorter(model) {
    
        class DateComparator implements Comparator {

            @Override
            public int compare(Object o1, Object o2) {
                String[] data1_str = ((String) o1).split("/");
                String[] data2_str = ((String) o2).split("/");

                Date data1 = Date.valueOf(data1_str[2] + "-" + data1_str[1] + "-" + data1_str[0]);
                Date data2 = Date.valueOf(data2_str[2] + "-" + data2_str[1] + "-" + data2_str[0]);

                return data1.compareTo(data2);
            }
            
        }

        @Override
        public void sort() {
            setComparator(DATA_CARICO, new DateComparator());
            setComparator(DATA_DOCUMENTO, new DateComparator());
            setComparator(NUMERO, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    Integer n1 = Integer.parseInt((String)o1);
                    Integer n2 = Integer.parseInt((String)o2);
                    return n1.compareTo(n2);
                }
            });
            super.sort();
        }
            
    });
      
    List<Entity> mezzi = FrontController.getAnagrafe(Mezzo.class);
    Object[] arrMezzi = mezzi.toArray();
    Mezzo[] arrayMezzi = new Mezzo[arrMezzi.length + 2];
    Character[] valoriRientrata = {'S','N'};
    String[] valoriStato = {"Consegna", "Ritiro"};
    System.arraycopy(arrMezzi, 0, arrayMezzi, 2, arrMezzi.length);
    arrayMezzi[0] = new Mezzo(null, "", null);
    arrayMezzi[1] = new Mezzo(null, "Vettore", null);
    DefaultCellEditor comboMezziEditor = new DefaultCellEditor(new JComboBox(arrayMezzi));
    DefaultCellEditor comboUMEditor = new DefaultCellEditor(new JComboBox(FrontController.getUnitaMisura()));
    DefaultCellEditor comboEditor = new DefaultCellEditor(new JComboBox(valoriRientrata));
    DefaultCellEditor statoEditor = new DefaultCellEditor(new JComboBox(valoriStato));
    comboEditor.setClickCountToStart(2); //Imposta l'edit della cella contente la checkbox al doppio click
    comboMezziEditor.setClickCountToStart(2); //Imposta l'edit della cella contente la combobox al doppio click
    comboUMEditor.setClickCountToStart(2);
    statoEditor.setClickCountToStart(2);
    tblSpedizioni.getColumnModel().getColumn(MEZZO).setCellEditor(comboMezziEditor); //Imposta una combobox come elemento grafico di default per la modifica del mezzo       
    tblSpedizioni.getColumnModel().getColumn(RIENTRATA).setCellEditor(comboEditor); 
    tblSpedizioni.getColumnModel().getColumn(UM).setCellEditor(comboUMEditor);   
    tblSpedizioni.getColumnModel().getColumn(STATO).setCellEditor(statoEditor);
    
    /*
    tblFatture.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

        @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == RIENTRATA) {
                    boolean rientrata = (Boolean) value;
                    if (rientrata)
                        setBackground(Color.red);
                }
                                
                return this;
            }
            
        });
        
        for (int i = 0; i < tblFatture.getRowCount(); i++) {
            boolean rientrata = (Boolean) tblFatture.getModel().getValueAt(i, RIENTRATA);
            if (rientrata) {
                for (int j = BOLLE; j <= RIENTRATA; j++) {
                    tblFatture.getCellRenderer(i, j).getTableCellRendererComponent(tblFatture,
                            tblFatture.getModel().getValueAt(i, j), false, false, i, j).setBackground(Color.red);
                }
                    
            } 
                
        }*/
                    
    jScrollPane1.setViewportView(tblSpedizioni);
    tblSpedizioni.getColumnModel().getColumn(BOLLE).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(STATO).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(NUMERO).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(DATA_CARICO).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(DATA_DOCUMENTO).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(DESCRIZIONE).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(MEZZO).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(UM).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(QTA).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(TRAZ).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(DISTRIB).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(IMPORTO).setResizable(true);
    //tblSpedizioni.getColumnModel().getColumn(SCONTO).setResizable(true);
    //tblSpedizioni.getColumnModel().getColumn(IVA).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(PROVVIGIONE).setResizable(true);
    //tblSpedizioni.getColumnModel().getColumn(TOTALE).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(NOTE).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(RIENTRATA).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(IMPONIBILE).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(FATTURA).setResizable(true);
    tblSpedizioni.getColumnModel().getColumn(VAL_MERCE).setResizable(true);
        
    tblSpedizioni.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    tblSpedizioni.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    //tblSpedizioni.getColumnModel().getColumn(SCONTO).setPreferredWidth(5);
    //tblSpedizioni.getColumnModel().getColumn(IVA).setPreferredWidth(5);
//    tblSpedizioni.getColumnModel().getColumn(PROVVIGIONE).setPreferredWidth(5);
    //tblSpedizioni.getColumnModel().getColumn(TOTALE).setPreferredWidth(5);
    tblSpedizioni.getColumnModel().getColumn(DESCRIZIONE).setPreferredWidth(170);
    tblSpedizioni.getColumnModel().getColumn(NOTE).setPreferredWidth(165);
    
   
    /*
     * Aggiunge un evento alla tabella. In particolare, al click su una cella della tabella,
     * visualizza i valori corrispondenti ai campi della riga selezionata nelle rispettive text.
    */
    tblSpedizioni.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent me) {
            /*
            if (!chkEmesse.isSelected()) {
                
                Spedizione sp = null;
                try {
                    sp = spedizioniInTabella.get(tblSpedizioni.getSelectedRow());
                } catch (IndexOutOfBoundsException e) {return;}
                
                //Chiama il metodo setRiepilogo inviando l'oggetto sp in modo da permettere una fatturazione singola.
                spedizioniDaFatt.clear();
                spedizioniDaFatt.add(sp);
                setRiepilogoFattura(spedizioniDaFatt);
            }*/
            
            //if (!chkEmesse.isSelected())
                //mnuSelezionate.setEnabled(true);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //super.mousePressed(e);
            //mouseClicked(null);
        }

    });

    /*
    class GraphButtonCellRenderer extends JCheckBox implements TableCellRenderer {
        public GraphButtonCellRenderer() {
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(isSelected)
                setSelected(true);
            else
                setSelected(false);
            setMargin(new Insets(0, 16, 0, 0));
            setIconTextGap(0);

            return this;
        }}
    
    tblSpedizioni.getColumnModel().getColumn(BOLLE).setCellRenderer(new GraphButtonCellRenderer());
    */
    
    for (int i = TRAZ; i <= PROVVIGIONE; i++)
        tblSpedizioni.getColumnModel().getColumn(i).setCellRenderer(new DoubleFormatter());
    
}

//ricarica i dati nella tabella dopo la modifica dal form di inserimento spedizioni
void ricaricaTabella(int rigaAggiornata, Spedizione spedizione) {
    if (rigaAggiornata >= 0) {
        spedizioniInTabella.set(rigaAggiornata, spedizione);
        ricaricaTabella();
    }
   
}

//ricarica i dati nella tabella
void ricaricaTabella() {
    Fornitore f = (Fornitore) cboFornitori.getSelectedItem();
    List<Spedizione> spedizioni = new LinkedList<Spedizione>();
    
    if (chkEmesse.isSelected()) { //richiamare le spedizioni le cui fatture sono già state emesse.
        if (mnuIntervalloDate.isSelected()) {
            if (chkNonFatturate.isSelected()) 
                spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.ALL);
            else
                spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.F);
        } else if (mnuTutti.isSelected()) {
            if (chkNonFatturate.isSelected())
                spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.ALL);
            else 
                spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.F);
        } else 
            spedizioni = spedizioniInTabella;
        
        popolaTabella(spedizioni, nonModificareCelle);

    } else {
        btnElimina.setEnabled(true);
        btnModifica.setEnabled(true);
        if (mnuIntervalloDate.isSelected()) {
           if (chkNonFatturate.isSelected()) 
                spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.NF);
            else
                spedizioni = new LinkedList<Spedizione>();
        } else if (mnuTutti.isSelected()) { 
            if (chkNonFatturate.isSelected())
                spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.NF);
            else
                spedizioni = new LinkedList<Spedizione>();
        } else 
            spedizioni = spedizioniInTabella;
        
        popolaTabella(spedizioni, modificaCelle);
        setRiepilogoFattura(spedizioni);
    }


}

private void mnuIntervalloDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuIntervalloDateActionPerformed
// TODO add your handling code here:    
    chkEmesse.setEnabled(true);
    chkNonFatturate.setEnabled(true);
    boolean okDate = false;
    java.util.Calendar currentime = Calendar.getInstance();
    dataIniziale = null;
    dataFinale = new Date((currentime.getTime()).getTime()); //data odierna
   
    /*
     * Il ciclo viene ripetuto fino a quando viene inserita una data nel formato corretto, oppure
     * fino a quando l'utente non decide di annullare l'operazione di filtraggio.
     */
    while (!okDate) {
        String dataI;
        try {
            /*
             * Il ciclo viene ripetuto fino a quando l'utente continua a cliccare ok sull'input senza
             * inserire alcun valore.
             */
            while ((dataI = JOptionPane.showInputDialog(rootPane, "Inserisci la data iniziale")).isEmpty());
            
        } catch (NullPointerException e) { //L'utente ha premuto annulla sull'input dialog
            mnuIntervalloDate.setSelected(false);
            return;
        }
    
        if (!checkData(dataI)) //Il formato della data inserita dall'utente, non è gg/mm/aaaa
            JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato gg/mm/aaaa", "Formato errato", JOptionPane.ERROR_MESSAGE);
        
        else { //Il formato è corretto
            String splitted[] = dataI.split("\\/");
            String giorno = splitted[0];
            String mese = splitted[1];
            String anno = splitted[2];
            
            if (anno.length() == 2)
                anno = "20" + anno;
                    
            else if (anno.length() == 3)
                anno = "2" + anno;

            if (mese.length() == 1)
                mese = "0" + mese;

            if (giorno.length() == 1) 
                giorno = "0" + giorno;
            
            try {
                dataIniziale = Date.valueOf(anno + "-" + mese + "-" + giorno);
                okDate = true;
                
            } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
                JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato corretto", "Valore errato", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    okDate = false;
    
    //Come quello di cui sopra
    while (!okDate) {
        String dataF = JOptionPane.showInputDialog(rootPane, "Inserisci la data finale oppure premi OK se vuoi utilizzare la data odierna.");
        if (dataF == null) { //L'utente ha premuto annulla sull'input dialog
            mnuIntervalloDate.setSelected(false);
            return;
        }
        
        if (!(dataF.isEmpty())) { //La data inserita non la stringa vuota
            if (!checkData(dataF)) //Il formato della data inserita dall'utente, non è gg/mm/aaaa
                JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato gg/mm/aaaa", "Formato errato", JOptionPane.ERROR_MESSAGE);
            
            else { //Il formato è corretto
                String splitted[] = dataF.split("\\/");
                String giorno = splitted[0];
                String mese = splitted[1];
                String anno = splitted[2];
                
                if (anno.length() == 2)
                    anno = "20" + anno;
                    
                else if (anno.length() == 3)
                    anno = "2" + anno;

                if (mese.length() == 1)
                    mese = "0" + mese;

                if (giorno.length() == 1) 
                    giorno = "0" + giorno;
                
                try {
                    dataFinale = Date.valueOf(anno + "-" + mese + "-" + giorno);
                    okDate = true;
                    
                } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
                    JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato corretto", "Valore errato", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else //L'utente non ha inserito alcuna data, quindi viene considerata la data odierna come data finale
            okDate = true;
   }
       
   Spedizione.tipo type = null;
   if (!chkEmesse.isSelected()) {
       if (chkNonFatturate.isSelected())
           type = Spedizione.tipo.NF; 
       //else (non è stata abilitata alcuna check)
       
   } else if (chkNonFatturate.isSelected())
       type = Spedizione.tipo.ALL;
   else 
       type = Spedizione.tipo.F;
      
   List<Spedizione> spedizioniInDate = null;
   if (type != null)
       spedizioniInDate = FrontController.getSpedizioniDateInterval((Fornitore)cboFornitori.getSelectedItem(), dataIniziale, dataFinale, type);
   else
       spedizioniInDate = new LinkedList<Spedizione>();
   
   if (type == Spedizione.tipo.NF)
       popolaTabella(spedizioniInDate, modificaCelle);
   else
       popolaTabella(spedizioniInDate, nonModificareCelle);
      
   setRiepilogoFattura(spedizioniInDate);
   mnuTutti.setSelected(false);
   mnuIntervalloDate.setSelected(true);
   mnuSelezionate.setSelected(false);
}//GEN-LAST:event_mnuIntervalloDateActionPerformed

private void mnuTuttiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTuttiActionPerformed
// TODO add your handling code here:
    cboFornitoriActionPerformed(null);
    chkEmesse.setEnabled(true);
    chkNonFatturate.setEnabled(true);
}//GEN-LAST:event_mnuTuttiActionPerformed

private void btnModificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificaActionPerformed
// TODO add your handling code here:
    if (tblSpedizioni.getSelectedRows().length > 1) {
        JOptionPane.showMessageDialog(null, "Seleziona solo una riga!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        
    } else {
        int selectedRow = tblSpedizioni.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Seleziona la riga da modificare!", "Attenzione", JOptionPane.WARNING_MESSAGE);

        } else {
            Spedizione spedizione = spedizioniInTabella.get(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow()));
            FrontController.open(new InsSpedizione(this, rootPaneCheckingEnabled, (Fornitore)cboFornitori.getSelectedItem(), spedizione, selectedRow));
        }
    }
}//GEN-LAST:event_btnModificaActionPerformed

private void btnEmettiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmettiActionPerformed
    //Memorizzazione nel database della fattura appena generata
    
    //int numFattura = Integer.parseInt(txtNumFatt.getText());
    String note = txtNote.getText();
    Date dataFattura = null;
    String anno = txtAnnoFatt.getText();
    String mese = txtMeseFatt.getText();
    String giorno = txtGiornoFatt.getText();
    if (anno.isEmpty() || mese.isEmpty() || giorno.isEmpty()) { //Un o più campi fra gg, mm e aaaa non sono stati inseriti
        JOptionPane.showMessageDialog(null, "Inserire la data fattura nel formato gg mm aaaa", 
            "Campo obbligatorio mancante", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (anno.length() == 2)
        anno = "20" + anno;
    
    if (mese.length() == 1)
        mese = "0" + mese;
    
    if (giorno.length() == 1) 
        giorno = "0" + giorno;
    
    Date dataScadenza = null;
    String annoScad = txtAnnoScadenza.getText();
    String meseScad = txtMeseScadenza.getText();
    String giornoScad = txtGiornoScadenza.getText();
    if (annoScad.isEmpty() || meseScad.isEmpty() || giornoScad.isEmpty()) { //Un o più campi fra gg, mm e aaaa non sono stati inseriti
        JOptionPane.showMessageDialog(null, "Inserire la data scadenza nel formato gg mm aaaa", 
            "Campo obbligatorio mancante", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (annoScad.length() == 2)
        anno = "20" + anno;
    
    if (meseScad.length() == 1)
        mese = "0" + mese;
    
    if (giornoScad.length() == 1) 
        giorno = "0" + giorno;
        
    /*
     * Se la data non è inserita nel formato corretto, mostra un messaggio di errore.
     * Per formato corretto si intende che il giorno non superi 31, o che il mese non
     * sia superiore a 12, oppure che sia stato inserito il giorno corretto in base al 
     * mese inserito. (es. il 31/11 non esiste)
     */
    try {
        dataFattura = Date.valueOf(anno + "-" + mese + "-" + giorno);
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Valore inserito per la data fattura non valido! Inserire la data nel formato gg/mm/aaaa", 
            "Formato errato", JOptionPane.ERROR_MESSAGE);
            return;
    }
    
    try {
        dataScadenza = Date.valueOf(annoScad + "-" + meseScad + "-" + giornoScad);
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Valore inserito per la data scadenza non valido! Inserire la data nel formato gg/mm/aaaa", 
            "Formato errato", JOptionPane.ERROR_MESSAGE);
            return;
    }
    
    String metodoPagamento = null;
    if (cboMetPag.getSelectedIndex() > 0) {
        metodoPagamento = (String) cboMetPag.getSelectedItem() + "-" + cboGiorni.getSelectedItem();
        
    } else {
        metodoPagamento = cboMetPag.getItemAt(cboMetPag.getItemCount()-1) + "-0"; //Prende l'ultimo metodo di pagamento (Rimessa diretta)
    }
    
    boolean forfait = false;
    
    if (chkForfait.isSelected())
        forfait = true;
    
    Double importo = 0.00;
    try {
        if (!forfait)
            importo = Double.parseDouble(txtImpTot.getText());
        else
            importo = Double.parseDouble(txtImpForfait.getText());

    } catch (NumberFormatException e) {}
    
    Double provvigione = 0.00;
    try {
        if (!forfait)
            provvigione = Double.parseDouble(txtProvvigioneTot.getText());
    } catch (NumberFormatException e) {}
    
    Double sconto = 0.00;
    try {
        if (!forfait)
            sconto = Double.parseDouble(txtScontoTot.getText());
        else
            sconto = Double.parseDouble(txtScontoForfait.getText());

    } catch (NumberFormatException e) {}
    
    Double ivaTot = 0.00;
    try {
        if (!forfait)
            ivaTot = Double.parseDouble(txtIva.getText());
        else
            ivaTot = Double.parseDouble(txtIvaForfait.getText());
  
    } catch (NumberFormatException e) {}
    
    Double totale = 0.00;
    try {
        if (!forfait)
            totale = Double.parseDouble(txtTotale.getText());
        else
            totale = Double.parseDouble(txtTotForfait.getText());
    } catch (NumberFormatException e) {}
    
    boolean pagata = chkPagata.isSelected();
    
    int numFattura = Integer.parseInt(txtNumFatt.getText());
    
    try {
        FrontController.checkTutt(dataFattura, numFattura);
        
    } catch (CheckTuttException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'emissione della fattura?", "Conferma fatturazione", JOptionPane.OK_CANCEL_OPTION);
    if (RESPONSE == JOptionPane.OK_OPTION) {
        Fattura fatt = new Fattura(numFattura, dataFattura, metodoPagamento, importo, provvigione, sconto, ivaTot, totale, spedizioniDaFatt, 
                forfait, pagata, note, dataScadenza);
        
        fatt.setCliente(new Fornitore(id_fornitore));
        try {
            if (FrontController.insert(fatt)) {
                if (pagata) {
                    if (movimenti != null){
                        //List<Movimento> movimento = new LinkedList<Movimento>();
                        //movimento.add(new Movimento(numFattura, dataFattura, Fattura.tipo.VEN.toString(), (String)cboMetPag.getSelectedItem(), totale, id_fornitore));
                        for (Movimento m : movimenti){
                            m.setFornCliente(id_fornitore);
                            m.setData(fatt.getData());
                            m.setNumDoc(fatt.getNumero());
                        }
                        FrontController.updatePagataFattura(Fattura.tipo.VEN, fatt, pagata, movimenti);
                        movimenti = null;
                    }
                }
                
                cboMetPag.setSelectedIndex(cboMetPag.getItemCount()-1);
                cboGiorni.setSelectedIndex(0);
                txtNote.setText(null);
                chkPagata.setSelected(false);
                JOptionPane.showMessageDialog(null, "Fatturazione eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                forced = false;
                //Stampa della fattura e visualizzazione a video
                try {
                    new stampa.StampaFattura(fatt, (Fornitore) cboFornitori.getSelectedItem(), false).printAndOpen();

                } catch (DocumentException ex) {
                    Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
                }

                mnuTuttiActionPerformed(null);
//                    mnuTutti.setSelected(true);
//                    mnuSelezionate.setSelected(false);
//                    ricaricaTabella();

            } else {
                JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la fatturazione", "Errore" ,JOptionPane.ERROR_MESSAGE);
            }
        } catch (EccezioneValoreCampoTroppoLungo e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
        }

    }
}//GEN-LAST:event_btnEmettiActionPerformed

private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
// TODO add your handling code here:
    FrontController.open(new InsSpedizione(this, rootPaneCheckingEnabled, (Fornitore) cboFornitori.getSelectedItem()));
}//GEN-LAST:event_btnNewActionPerformed

private void btnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaActionPerformed
// TODO add your handling code here:
    int selectedRow = tblSpedizioni.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Seleziona una riga da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        
    } else {
        final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'eliminazione?", "Conferma eliminazione", JOptionPane.OK_CANCEL_OPTION);
        if (RESPONSE == JOptionPane.OK_OPTION) {
            DefaultTableModel model = (DefaultTableModel) tblSpedizioni.getModel();
            String numToRemove = (String) tblSpedizioni.getValueAt(selectedRow, NUMERO);
            String extDate = (String) tblSpedizioni.getValueAt(selectedRow, DATA_CARICO);
            String[] splittedExtDate = extDate.split("\\/");
            Date dateSped = Date.valueOf(splittedExtDate[2] + "-" + splittedExtDate[1] + "-" + splittedExtDate[0]);
            model.removeRow(selectedRow);
            
            Spedizione sp = new Spedizione(numToRemove, id_fornitore);
            sp.setDataCarico(dateSped);
            if (FrontController.delete(sp)){
                JOptionPane.showMessageDialog(null, "Eliminazione eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                spedizioniInTabella.remove(getIndexSpedizioneAt(tblSpedizioni.getSelectedRow()));
                ricaricaTabella();
            } else {
                JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'eliminazione", "Errore" ,JOptionPane.ERROR_MESSAGE);
                ricaricaTabella();
            }
                
        }
        
    }
}//GEN-LAST:event_btnEliminaActionPerformed

private void chkEmesseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEmesseActionPerformed
// TODO add your handling code here:
    List<Spedizione> spedizioni = new LinkedList<Spedizione>();
    
    if (cboFornitori.getSelectedIndex() != 0) {
        Fornitore f = (Fornitore) cboFornitori.getSelectedItem();
        if (chkEmesse.isSelected()) { //richiamare le spedizioni le cui fatture sono già state emesse.
            if (mnuIntervalloDate.isSelected()) {
                if (chkNonFatturate.isSelected()) 
                    spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.ALL);
                else
                    spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.F);
                
            } else if (mnuTutti.isSelected()) {
                if (chkNonFatturate.isSelected())
                    spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.ALL);
                else 
                    spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.F);
            } else 
                spedizioni = spedizioniInTabella;
            
            popolaTabella(spedizioni, nonModificareCelle);
            txtImpTot.setText(null);
            txtImponibile.setText(null);
            txtIva.setText(null);
            txtProvvigioneTot.setText(null);
            txtScontoTot.setText(null);
            txtTotale.setText(null);
            txtAnnoFatt.setText(null);
            txtGiornoFatt.setText(null);
            txtMeseFatt.setText(null);
            txtNumFatt.setText(null);
            pnlRiepilogo.setVisible(false);
            btnElimina.setEnabled(false);
            btnModifica.setEnabled(false);
            mnuSped.setEnabled(true);
            mnuCambiaCliente.setEnabled(false);
            
        } else {
            btnElimina.setEnabled(true);
            btnModifica.setEnabled(true);
            if (mnuIntervalloDate.isSelected()){
                if (chkNonFatturate.isSelected()) 
                    spedizioni = FrontController.getSpedizioniDateInterval(f, dataIniziale, dataFinale, Spedizione.tipo.NF);
                else
                    spedizioni = new LinkedList<Spedizione>();
                
            } else if (mnuTutti.isSelected()) { 
                if (chkNonFatturate.isSelected())
                    spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.NF);
                else
                    spedizioni = new LinkedList<Spedizione>();
            } else 
                spedizioni = spedizioniInTabella;
            
            if (!chkNonFatturate.isSelected()){
                mnuSped.setEnabled(false);
            }else
                mnuSped.setEnabled(true);
            
            mnuCambiaCliente.setEnabled(true);
            popolaTabella(spedizioni, modificaCelle);
            setRiepilogoFattura(spedizioni);
        }
    }
}//GEN-LAST:event_chkEmesseActionPerformed

private void cboFornitoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornitoriActionPerformed
// TODO add your handling code here:
    chkEmesse.setEnabled(true);
    chkNonFatturate.setEnabled(true);
    if (cboFornitori.getSelectedIndex() != 0) {
        chkForfait.setSelected(false);
        pnlForfait.setVisible(false);
        Fornitore f = (Fornitore) cboFornitori.getSelectedItem();
        id_fornitore = f.getCod();
        List<Spedizione> spedizioni = new LinkedList<Spedizione>();
        if (!(chkEmesse.isSelected())) {
            if (chkNonFatturate.isSelected()){
                spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.NF);
                setRiepilogoFattura(spedizioni);
                popolaTabella(spedizioni, modificaCelle);
                btnNew.setEnabled(true);
                btnElimina.setEnabled(true);
                btnModifica.setEnabled(true);
                mnuSped.setEnabled(true);
                mnuCambiaCliente.setEnabled(true);
                mnuStampa.setEnabled(true);
            } else
                popolaTabella(new LinkedList<Spedizione>(), modificaCelle);
            
        } else if (chkNonFatturate.isSelected()) {
            spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.ALL);
            pnlRiepilogo.setVisible(false);
            popolaTabella(spedizioni, nonModificareCelle);
            btnNew.setEnabled(true);
            btnElimina.setEnabled(false);
            btnModifica.setEnabled(false);
            mnuSped.setEnabled(true);
            mnuCambiaCliente.setEnabled(false);
        } else {
            spedizioni = FrontController.getSpedizioni(f, Spedizione.tipo.F);
            pnlRiepilogo.setVisible(false);
            popolaTabella(spedizioni, nonModificareCelle);
            btnNew.setEnabled(true);
            btnElimina.setEnabled(false);
            btnModifica.setEnabled(false);
            mnuSped.setEnabled(true);
            mnuCambiaCliente.setEnabled(false);
            mnuStampa.setEnabled(true);
        }
                
        mnuFiltra.setEnabled(true);
        mnuTutti.setSelected(true);
        mnuIntervalloDate.setSelected(false);
        mnuSelezionate.setSelected(false);
        //mnuSelezionate.setEnabled(false);
        
    } else {
        btnNew.setEnabled(false);
        btnElimina.setEnabled(false);
        btnModifica.setEnabled(false);
        mnuFiltra.setEnabled(false);
        pnlRiepilogo.setVisible(false);
        chkEmesse.setSelected(false);
        //mnuSped.setEnabled(false);
        mnuCambiaCliente.setEnabled(false);
        mnuStampa.setEnabled(false);
        popolaTabella(new LinkedList<Spedizione>(), nonModificareCelle);
    }
}//GEN-LAST:event_cboFornitoriActionPerformed

private void chkNonFatturateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNonFatturateActionPerformed
// TODO add your handling code here:
    chkEmesseActionPerformed(null);
}//GEN-LAST:event_chkNonFatturateActionPerformed

private void chkForfaitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkForfaitActionPerformed
// TODO add your handling code here:
    if (chkForfait.isSelected()){
        pnlForfait.setVisible(true);
        txtImpForfait.requestFocus();
    } else
        pnlForfait.setVisible(false);
    txtImpForfait.setText("");
    txtScontoForfait.setText("");
    txtImponibileForfait.setText("");
    txtIvaForfait.setText("");
    txtTotForfait.setText("");
    txtPercIvaForfait.setText("");
}//GEN-LAST:event_chkForfaitActionPerformed

private void txtImpForfaitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImpForfaitFocusLost
// TODO add your handling code here:
    calcolaTotale();
}//GEN-LAST:event_txtImpForfaitFocusLost

private void txtScontoForfaitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtScontoForfaitFocusLost
// TODO add your handling code here:
    calcolaTotale();
}//GEN-LAST:event_txtScontoForfaitFocusLost

private void txtPercIvaForfaitFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPercIvaForfaitFocusLost
// TODO add your handling code here:
    calcolaTotale();
}//GEN-LAST:event_txtPercIvaForfaitFocusLost

private void mnuSelezionateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSelezionateActionPerformed
// TODO add your handling code here:
    int[] indici = tblSpedizioni.getSelectedRows();
    List<Spedizione> spedizioniSelezionate = new ArrayList<Spedizione>();
    for (int i : indici)
        spedizioniSelezionate.add(spedizioniInTabella.get(getIndexSpedizioneAt(i)));
    
    if (chkEmesse.isSelected()) {
        popolaTabella(spedizioniSelezionate, nonModificareCelle);
        txtImpTot.setText(null);
        txtImponibile.setText(null);
        txtIva.setText(null);
        txtProvvigioneTot.setText(null);
        txtScontoTot.setText(null);
        txtTotale.setText(null);
        txtAnnoFatt.setText(null);
        txtGiornoFatt.setText(null);
        txtMeseFatt.setText(null);
        txtNumFatt.setText(null);
        pnlRiepilogo.setVisible(false);
        btnElimina.setEnabled(false);
        btnModifica.setEnabled(false);
    } else {
        popolaTabella(spedizioniSelezionate, modificaCelle);
        btnElimina.setEnabled(true);
        btnModifica.setEnabled(true);
        setRiepilogoFattura(spedizioniSelezionate);
    }
    
    mnuSelezionate.setSelected(true);
    mnuTutti.setSelected(false);
    mnuIntervalloDate.setSelected(false);
    chkEmesse.setEnabled(false);
    chkNonFatturate.setEnabled(false);
}//GEN-LAST:event_mnuSelezionateActionPerformed

private void mnuCambiaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCambiaClienteActionPerformed
// TODO add your handling code here:
    if (spedizioniInTabella.isEmpty()) {
        JOptionPane.showMessageDialog(this, "La tabella deve contenere almeno una spedizione da modificare!", "Tabella vuota", JOptionPane.WARNING_MESSAGE);
    } else {
        List<Spedizione> spedizioni = null;
        String msg = null;
        int[] righeSelezionate = tblSpedizioni.getSelectedRows();
        if (righeSelezionate.length == 0) {
            spedizioni = spedizioniInTabella;
            msg = "Sei sicuro di voler procedere con il cambio del cliente per tutte le spedizioni presenti al momento in tabella?";
        } else {
            spedizioni = new ArrayList<Spedizione>();
            for (int riga : righeSelezionate) {
                spedizioni.add(spedizioniInTabella.get(getIndexSpedizioneAt(riga)));
            }
            msg = "Sei sicuro di voler procedere con il cambio del cliente per tutte le spedizioni selezionate?";
        }
        
        Object[] clienti = FrontController.getAnagrafe(Fornitore.class).toArray();
        Object nuovoCliente = JOptionPane.showInputDialog(this, "Seleziona il nuovo cliente", "Cambio cliente", JOptionPane.PLAIN_MESSAGE, null, clienti, null);

        if (nuovoCliente != null) {
            if (!nuovoCliente.equals((Fornitore)cboFornitori.getSelectedItem())) {      
                final int RESPONSE = JOptionPane.showConfirmDialog(null, msg, "Conferma cambio cliente", JOptionPane.OK_CANCEL_OPTION);
                if (RESPONSE == JOptionPane.OK_OPTION) {
                    if (FrontController.cambiaCliente(spedizioni, (Fornitore) nuovoCliente)) {
                        cboFornitoriActionPerformed(evt);
                        JOptionPane.showMessageDialog(this, "Cliente modificato con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Si è verificato un errore durante la modifica!", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Il cliente selezionato è uguale a quello attuale!", "", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}//GEN-LAST:event_mnuCambiaClienteActionPerformed

private void txtNumFattFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFattFocusLost
// TODO add your handling code here:
    //proposedNumber è il numero ottenuto dal sistema sulla base della data all'interno delle text
    int proposedNumber;
    try {
        proposedNumber = FrontController.checkTutt(dataPresunta, -1);
        
    } catch (CheckTuttException e) {
//        JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        return;
    }
    //textNumber è il numero eventualmente inserito all'interno della text
    int textNumber = Integer.parseInt(txtNumFatt.getText());
    
    //se i numeri divergono, c'è stata una forzatura da parte dell'utente
    if (textNumber != proposedNumber) {
        forced = true;
        try {
            FrontController.checkTutt(dataPresunta, textNumber);
            
        } catch (CheckTuttException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            //txtNumFatt.requestFocus();
            return;
        }
    }
}//GEN-LAST:event_txtNumFattFocusLost

private void txtGiornoFattFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiornoFattFocusLost
// TODO add your handling code here:
    setNumber();
}//GEN-LAST:event_txtGiornoFattFocusLost

private void txtMeseFattFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMeseFattFocusLost
// TODO add your handling code here:
    setNumber();
}//GEN-LAST:event_txtMeseFattFocusLost

private void txtAnnoFattFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAnnoFattFocusLost
// TODO add your handling code here:
    setNumber();
}//GEN-LAST:event_txtAnnoFattFocusLost

private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
// TODO add your handling code here:
    
    List<Spedizione> spedizioni = spedizioniInTabella;
//    int[] righeSelezionate = tblSpedizioni.getSelectedRows();
//    if (righeSelezionate.length == 0) {
//        spedizioni = spedizioniInTabella;
//
//    } else {
//        spedizioni = new ArrayList<Spedizione>();
//        for (int riga : righeSelezionate) {
//            spedizioni.add(spedizioniInTabella.get(getIndexSpedizioneAt(riga)));
//        }
//    }
       
    try {
        if (mnuIntervalloDate.isSelected()) {
            if (chkEmesse.isSelected() && chkNonFatturate.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni, dataIniziale, dataFinale, 
                        StampaSpedizioni.filtro.INTERVALLO_DATA, StampaSpedizioni.tipo.TUTTE).printAndOpen();
            
            } else if (!chkEmesse.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni, dataIniziale, dataFinale, 
                        StampaSpedizioni.filtro.INTERVALLO_DATA, StampaSpedizioni.tipo.NON_FATTURATE).printAndOpen();
                
            } else if (!chkNonFatturate.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni, dataIniziale, dataFinale, 
                        StampaSpedizioni.filtro.INTERVALLO_DATA, StampaSpedizioni.tipo.FATTURATE).printAndOpen();
                
            } else {
                return;
                
            }
        } else {
            if (chkEmesse.isSelected() && chkNonFatturate.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni,
                        StampaSpedizioni.filtro.TUTTE, StampaSpedizioni.tipo.TUTTE).printAndOpen();
            
            } else if (!chkEmesse.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni,
                        StampaSpedizioni.filtro.TUTTE, StampaSpedizioni.tipo.NON_FATTURATE).printAndOpen();
                
            } else if (!chkNonFatturate.isSelected()) {
                new StampaSpedizioni((Fornitore)cboFornitori.getSelectedItem(), spedizioni,
                        StampaSpedizioni.filtro.TUTTE, StampaSpedizioni.tipo.FATTURATE).printAndOpen();
                
            } else {
                return;
                
            }
        }
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }

}//GEN-LAST:event_mnuStampaActionPerformed

private void chkPagataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPagataActionPerformed
// TODO add your handling code here:
    if (chkPagata.isSelected()){
        Fattura fatt = new Fattura();
        fatt.setNumero(Integer.parseInt(txtNumFatt.getText()));
        fatt.setData(dataPresunta);
        fatt.setCliente((Fornitore)cboFornitori.getSelectedItem());
        Double totale = 0.0;
        if (chkForfait.isSelected())
            totale = Double.parseDouble(txtTotForfait.getText());
        else
            totale = Double.parseDouble(txtTotale.getText());
        fatt.setTotale(totale);
        String metodoPagamento = null;
        if (cboMetPag.getSelectedIndex() > 0) {
            metodoPagamento = (String) cboMetPag.getSelectedItem() + "-" + cboGiorni.getSelectedItem();
        } else {
            metodoPagamento = "Contante-0";
        }
        fatt.setMetPag(metodoPagamento);

        FrontController.open(new NotePagamento(vista, rootPaneCheckingEnabled, fatt)); 
    } else
        movimenti = null;
}//GEN-LAST:event_chkPagataActionPerformed

private void btnRegistroEmesseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroEmesseActionPerformed
// TODO add your handling code here:
    FrontController.open(new RegistroFattureEmesse());
}//GEN-LAST:event_btnRegistroEmesseActionPerformed

private void cboGiorniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiorniActionPerformed
// TODO add your handling code here:  
    String[] nuovaScadenza = DateUpdate.update(dataPresunta, Integer.parseInt((String) cboGiorni.getSelectedItem())).toString().split("-");
    txtGiornoScadenza.setText(nuovaScadenza[2]);
    txtMeseScadenza.setText(nuovaScadenza[1]);
    txtAnnoScadenza.setText(nuovaScadenza[0]);
}//GEN-LAST:event_cboGiorniActionPerformed

private void mnuStoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStoricoActionPerformed
// TODO add your handling code here:
    FrontController.open(new StoricoSpedizioni(this, true));
}//GEN-LAST:event_mnuStoricoActionPerformed

private void setNumber() {
    String anno = txtAnnoFatt.getText();
    String mese = txtMeseFatt.getText();
    String giorno = txtGiornoFatt.getText();
    
    if (anno.length() == 2)
        anno = "20" + anno;
    else if (anno.length() == 3)
        anno = "2" + anno;

    if (mese.length() == 1)
        mese = "0" + mese;

    if (giorno.length() == 1)
        giorno = "0" + giorno;
    
    dataPresunta = Date.valueOf(anno + "-" + mese + "-" + giorno);
    int textNumber = Integer.parseInt(txtNumFatt.getText());
    if (forced) {
        try {
            FrontController.checkTutt(dataPresunta, textNumber);
 
        } catch (CheckTuttException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    } else {
        int numCheck;
        try {
            numCheck = FrontController.checkTutt(dataPresunta, -1);
            txtNumFatt.setText(String.valueOf(numCheck));
            
        } catch (CheckTuttException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
    String[] nuovaScadenza = DateUpdate.update(dataPresunta, Integer.parseInt((String) cboGiorni.getSelectedItem())).toString().split("-");
    txtGiornoScadenza.setText(nuovaScadenza[2]);
    txtMeseScadenza.setText(nuovaScadenza[1]);
    txtAnnoScadenza.setText(nuovaScadenza[0]);

}

private void setRiepilogoFattura(List<Spedizione> spedizioni){
    if (!spedizioni.isEmpty()){
        pnlRiepilogo.setVisible(true);
        double importoTot = 0.0;
        double scontoTot = 0.0;
        double provvigione = 0.0;
        double ivaTot = 0.0;
        double imponibile = 0.0;
        double totale = 0.0;

        for (Spedizione sped : spedizioni){
            importoTot += sped.getImporto();
            scontoTot += (sped.getSconto() * sped.getImporto()) / 100.0;
            provvigione += sped.getProvvigione();
            ivaTot += sped.getIva();
        }
        
        imponibile = importoTot - scontoTot + provvigione;
        totale = imponibile + ivaTot;

        txtImpTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(importoTot)));
        txtImponibile.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(imponibile)));
        txtIva.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(ivaTot)));
        txtProvvigioneTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(provvigione)));
        txtScontoTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(scontoTot)));
        txtTotale.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totale)));

        //Inserisce una data presunta della fattura
        java.util.Date utilDate = new java.util.Date();
        String today = (new java.sql.Date(utilDate.getTime())).toString();
        String[] splitted = today.split("\\-");
        String anno = splitted[0];
        String mese = splitted[1];
        String giorno = splitted[2];
        dataPresunta = Date.valueOf(anno + "-" + mese + "-" + giorno);
        txtAnnoFatt.setText(anno);
        txtMeseFatt.setText(mese);
        txtGiornoFatt.setText(giorno);
        try {
            txtNumFatt.setText(String.valueOf(FrontController.checkTutt(dataPresunta, -1)));
            
        } catch (CheckTuttException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
        //txtNumFatt.setText(String.valueOf(FrontController.getNumber(Fattura.class, dataPresunta)));
        txtAnnoScadenza.setText(anno);
        txtMeseScadenza.setText(mese);
        txtGiornoScadenza.setText(giorno);

    } else
        pnlRiepilogo.setVisible(false);
}

private int getIndexSpedizioneAt(int i) {
    Spedizione sped = new Spedizione();
    sped.setNumSpedizione((String) tblSpedizioni.getValueAt(i, NUMERO));
    String[] data = ((String) tblSpedizioni.getValueAt(i, DATA_CARICO)).split("/");
    Date dataFatt = Date.valueOf(data[2] + "-" + data[1] + "-" + data[0]);
    sped.setDataCarico(dataFatt);

    return spedizioniInTabella.indexOf(sped);
}

void unCheckPagate(){
    chkPagata.setSelected(false);
}

int getIndexSpedizione(Spedizione sped) {
    return spedizioniInTabella.indexOf((Spedizione)sped);
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnElimina;
    private javax.swing.JButton btnEmetti;
    private javax.swing.JButton btnModifica;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnRegistroEmesse;
    private javax.swing.JComboBox cboFornitori;
    private javax.swing.JComboBox cboGiorni;
    private javax.swing.JComboBox cboMetPag;
    private javax.swing.JCheckBox chkEmesse;
    private javax.swing.JCheckBox chkForfait;
    private javax.swing.JCheckBox chkNonFatturate;
    private javax.swing.JCheckBox chkPagata;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblImpForfait;
    private javax.swing.JLabel lblImponibileForfait;
    private javax.swing.JLabel lblIvaForfait;
    private javax.swing.JLabel lblPercIvaForfait;
    private javax.swing.JLabel lblScontoForfait;
    private javax.swing.JLabel lblTotForfait;
    private javax.swing.JMenuItem mnuCambiaCliente;
    private javax.swing.JMenu mnuFiltra;
    private javax.swing.JCheckBoxMenuItem mnuIntervalloDate;
    private javax.swing.JCheckBoxMenuItem mnuSelezionate;
    private javax.swing.JMenu mnuSped;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JMenuItem mnuStorico;
    private javax.swing.JCheckBoxMenuItem mnuTutti;
    private javax.swing.JPanel pnlForfait;
    private javax.swing.JPanel pnlRiepilogo;
    private javax.swing.JTable tblSpedizioni;
    private javax.swing.JTextField txtAnnoFatt;
    private javax.swing.JTextField txtAnnoScadenza;
    private javax.swing.JTextField txtGiornoFatt;
    private javax.swing.JTextField txtGiornoScadenza;
    private javax.swing.JTextField txtImpForfait;
    private javax.swing.JTextField txtImpTot;
    private javax.swing.JTextField txtImponibile;
    private javax.swing.JTextField txtImponibileForfait;
    private javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtIvaForfait;
    private javax.swing.JTextField txtMeseFatt;
    private javax.swing.JTextField txtMeseScadenza;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtNumFatt;
    private javax.swing.JTextField txtPercIvaForfait;
    private javax.swing.JTextField txtProvvigioneTot;
    private javax.swing.JTextField txtScontoForfait;
    private javax.swing.JTextField txtScontoTot;
    private javax.swing.JTextField txtTotForfait;
    private javax.swing.JTextField txtTotale;
    // End of variables declaration//GEN-END:variables

    private int id_fornitore; //Il fornitore selezionato, ovvero colui di cui si stanno visualizzando le spedizioni
    private boolean forced = false; //indica se il numero della fattura è stato forzato o meno
    private Date dataPresunta; //indica la data del giorno, presunta per la fatturazione
    private Spedizioni vista;
    
    //Serve mantenere i riferimenti alle date, per mantenere sincronizzati le ricerche con il filtro delle fatture emesse
    private Date dataIniziale;
    private Date dataFinale;
    private List<Spedizione> spedizioniInTabella;
    private List<Spedizione> spedizioniDaFatt = new LinkedList<Spedizione>();
    
    //Lista temporanea di movimenti
    public List<Movimento> movimenti = new LinkedList<Movimento>();
    
    private final boolean[] modificaCelle = new boolean[] {
            true, true, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, false 
    };
    
    private final boolean[] nonModificareCelle = new boolean[] {
            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false 
    };
    
    //Le seguenti costanti indicano i numeri di colonna dei campi
    private final int BOLLE = 0;
    private final int STATO = 1;
    private final int NUMERO = 2;
    private final int DATA_CARICO = 3;
    private final int DATA_DOCUMENTO = 4;
    private final int DESCRIZIONE = 5;
    private final int UM = 6;
    private final int QTA = 7;
    private final int TRAZ = 8;
    private final int DISTRIB = 9;
    private final int IMPORTO = 10;
    private final int IMPONIBILE = 11;
    private final int VAL_MERCE = 12;
    private final int PROVVIGIONE = 13;
    private final int NOTE = 14;
    private final int RIENTRATA = 15;
    private final int MEZZO = 16;
    private final int FATTURA = 17;
    //private final int SCONTO = 15;
    //private final int IVA = 16;
    //private final int PROVVIGIONE = 17;
    //private final int TOTALE = 18;
    
    private static final int MAX_LENGTH_GIORNO = 2;
    private static final int MAX_LENGTH_MESE = 2;
    private static final int MAX_LENGTH_ANNO = 4;
    private static final int MAX_LENGTH_PERCIVA = 2;
    private static final int MAX_LENGTH_DESCRIZIONE = 54;
}