/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RegistroFattureEmesse.java
 *
 * Created on 30-mag-2012, 20.17.33
 */
package viste;

import com.itextpdf.text.DocumentException;
import controllo.FrontController;
import entita.Entity;
import entita.Fattura;
import entita.Fornitore;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import libs.DoubleFormatter;
import stampa.StampaScadenziario;

/**
 *
 * @author Michele
 */
public class Scadenziario extends javax.swing.JFrame {
    
    private class FattureTableModel extends DefaultTableModel {
        
        //private final String[] COLONNE;
        private final boolean[] CAN_EDIT;
        private final Class[] types;
        
        public FattureTableModel(Object[][] righe, String[] colonne, Class[] tps, boolean[] edit) {
            super(righe, colonne);
            //COLONNE = colonne;
            CAN_EDIT = edit;
            types = tps;
        }
 
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return CAN_EDIT[columnIndex];
        }
        
        @Override
        public Class getColumnClass(int columnIndex) {
            return this.types[columnIndex];
        }
        
    }

    /** Creates new form RegistroFattureEmesse */
    public Scadenziario() {
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlAcquisto);
        color.changeColor(pnlCliente);
        color.changeColor(pnlEmesse);
        color.changeColor(pnlScadenza);
//        color.changeColor(tblScadenzeEmesse.getParent());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCliente = new javax.swing.JPanel();
        cboFornCliente = new javax.swing.JComboBox();
        pnlEmesse = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblScadenzeEmesse = new javax.swing.JTable();
        pnlAcquisto = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblScadenzeAcquisto = new javax.swing.JTable();
        pnlScadenza = new javax.swing.JPanel();
        cboPeriodi = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuCliente = new javax.swing.JMenu();
        mnuSollecito = new javax.swing.JMenuItem();
        mnuProspetto = new javax.swing.JMenu();
        mnuStampa = new javax.swing.JMenu();
        mnuEmesse = new javax.swing.JMenuItem();
        mnuAcquisto = new javax.swing.JMenuItem();
        mnuEntrambe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Scadenziario");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente/Fornitore", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        cboFornCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tutti" }));
        cboFornCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFornClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlClienteLayout = new javax.swing.GroupLayout(pnlCliente);
        pnlCliente.setLayout(pnlClienteLayout);
        pnlClienteLayout.setHorizontalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboFornCliente, 0, 340, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlClienteLayout.setVerticalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addComponent(cboFornCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlEmesse.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fatture emesse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tblScadenzeEmesse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblScadenzeEmesse.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblScadenzeEmesse);

        javax.swing.GroupLayout pnlEmesseLayout = new javax.swing.GroupLayout(pnlEmesse);
        pnlEmesse.setLayout(pnlEmesseLayout);
        pnlEmesseLayout.setHorizontalGroup(
            pnlEmesseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmesseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlEmesseLayout.setVerticalGroup(
            pnlEmesseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmesseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlAcquisto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Fatture Acquisto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        tblScadenzeAcquisto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblScadenzeAcquisto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(tblScadenzeAcquisto);

        javax.swing.GroupLayout pnlAcquistoLayout = new javax.swing.GroupLayout(pnlAcquisto);
        pnlAcquisto.setLayout(pnlAcquistoLayout);
        pnlAcquistoLayout.setHorizontalGroup(
            pnlAcquistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcquistoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAcquistoLayout.setVerticalGroup(
            pnlAcquistoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcquistoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pnlScadenza.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "In scadenza", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        cboPeriodi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tutte", "Oggi", "Scegli data" }));
        cboPeriodi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlScadenzaLayout = new javax.swing.GroupLayout(pnlScadenza);
        pnlScadenza.setLayout(pnlScadenzaLayout);
        pnlScadenzaLayout.setHorizontalGroup(
            pnlScadenzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScadenzaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboPeriodi, 0, 383, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlScadenzaLayout.setVerticalGroup(
            pnlScadenzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlScadenzaLayout.createSequentialGroup()
                .addComponent(cboPeriodi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnuCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cambiacliente.png"))); // NOI18N
        mnuCliente.setText("Cliente");
        mnuCliente.setEnabled(false);

        mnuSollecito.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        mnuSollecito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/emailsend.png"))); // NOI18N
        mnuSollecito.setText("Invia sollecito");
        mnuSollecito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSollecitoActionPerformed(evt);
            }
        });
        mnuCliente.add(mnuSollecito);

        jMenuBar1.add(mnuCliente);

        mnuProspetto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prospetto.png"))); // NOI18N
        mnuProspetto.setText("Prospetto");

        mnuStampa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stampa.png"))); // NOI18N
        mnuStampa.setText("Stampa");

        mnuEmesse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mnuEmesse.setText("Fatture emesse");
        mnuEmesse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEmesseActionPerformed(evt);
            }
        });
        mnuStampa.add(mnuEmesse);

        mnuAcquisto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuAcquisto.setText("Fatture acquisto");
        mnuAcquisto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAcquistoActionPerformed(evt);
            }
        });
        mnuStampa.add(mnuAcquisto);

        mnuEntrambe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        mnuEntrambe.setText("Entrambe");
        mnuEntrambe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEntrambeActionPerformed(evt);
            }
        });
        mnuStampa.add(mnuEntrambe);

        mnuProspetto.add(mnuStampa);

        jMenuBar1.add(mnuProspetto);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEmesse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlAcquisto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(pnlEmesse, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlAcquisto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnlAcquisto, pnlEmesse});

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    List<Entity> fornitori = FrontController.getAnagrafe(Fornitore.class);
    popolaSelect(fornitori);
    setTableEmesse();
    setTableAcquisto();
    setPanelBorderTitle();
}//GEN-LAST:event_formWindowOpened

//Modifica a runtime del borderTitle
private void setPanelBorderTitle(){
    Double totFattAcquisto = 0.0;
    Double totFattEmesse = 0.0; 
    DoubleFormatter.roundTwoDecimals(totFattAcquisto);
    DoubleFormatter.roundTwoDecimals(totFattEmesse);
    //Prendo il totale delle fatture emesse e d'acquisto nelle tabelle
    for (Fattura f: fattureEmesse)
        totFattEmesse += f.getTotale();
    for (Fattura f: fattureAcquisto)
        totFattAcquisto += f.getTotale();
    
    Border borderEmesse = new TitledBorder("Fatture emesse - TOTALE: € " + String.format("%1$,.2f", totFattEmesse));
    Border borderAcquisto = new TitledBorder("Fatture acquisto  - TOTALE: € " + String.format("%1$,.2f", totFattAcquisto));
    pnlEmesse.setBorder(borderEmesse);
    pnlAcquisto.setBorder(borderAcquisto);
}

private void cboFornClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFornClienteActionPerformed
// TODO add your handling code here: 
    setTableEmesse();
    setTableAcquisto();
    setPanelBorderTitle();
}//GEN-LAST:event_cboFornClienteActionPerformed

private void cboPeriodiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodiActionPerformed
    if (cboPeriodi.getSelectedIndex() == SCELTA) {
        boolean okDate = false;
        /*
         * Il ciclo viene ripetuto fino a quando viene inserita una data nel formato corretto, oppure
         * fino a quando l'utente non decide di annullare l'operazione di filtraggio.
        */
        while (!okDate) {
            String dataInput;
            try {
                /*
                 * Il ciclo viene ripetuto fino a quando l'utente continua a cliccare ok sull'input senza
                 * inserire alcun valore.
                 */
                while ((dataInput = JOptionPane.showInputDialog(rootPane, "Inserisci la data","Inserimento data finale di scadenza", JOptionPane.QUESTION_MESSAGE)).isEmpty());

            } catch (NullPointerException e) { //L'utente ha premuto annulla sull'input dialog
                return;
            }

            if (!checkData(dataInput)) //Il formato della data inserita dall'utente, non è gg/mm/aaaa
                JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato gg/mm/aaaa", "Formato errato", JOptionPane.ERROR_MESSAGE);

            else { //Il formato è corretto
                String splitted[] = dataInput.split("\\/");
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
                    dataScelta = Date.valueOf(anno + "-" + mese + "-" + giorno);
                    okDate = true;

                } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
                    JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato corretto", "Valore errato", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
    setTableEmesse();
    setTableAcquisto();
    setPanelBorderTitle();
}//GEN-LAST:event_cboPeriodiActionPerformed

private boolean checkData(String data) {
    Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
    Matcher match = pattern.matcher(data);
    return match.matches();
      
}

private void mnuSollecitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSollecitoActionPerformed
// TODO add your handling code here:
    
    if (tblScadenzeEmesse.getSelectedRowCount() == 0)
        FrontController.open(new InvioMail(this, rootPaneCheckingEnabled, fattureEmesse));
    else {
        int[] selectedRows = tblScadenzeEmesse.getSelectedRows();
        List<Fattura> fattureSelezionate = new ArrayList<Fattura>();
        for (int row : selectedRows)
            fattureSelezionate.add(fattureEmesse.get(row));
        
        FrontController.open(new InvioMail(this, rootPaneCheckingEnabled, fattureSelezionate));
    }
}//GEN-LAST:event_mnuSollecitoActionPerformed

private void mnuEmesseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEmesseActionPerformed
// TODO add your handling code here:
    
    Fornitore forn_cliente = null;
    if (cboFornCliente.getSelectedIndex() > 0)
        forn_cliente = (Fornitore) cboFornCliente.getSelectedItem();
    
    try {
        if (!fattureEmesse.isEmpty())
            new StampaScadenziario(forn_cliente, fattureEmesse, Fattura.tipo.VEN, (String) cboPeriodi.getSelectedItem()).printAndOpen();
        else
            JOptionPane.showMessageDialog(this, "Nessuna fattura da stampare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
    
}//GEN-LAST:event_mnuEmesseActionPerformed

private void mnuAcquistoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAcquistoActionPerformed
// TODO add your handling code here:
    
    Fornitore forn_cliente = null;
    if (cboFornCliente.getSelectedIndex() > 0)
        forn_cliente = (Fornitore) cboFornCliente.getSelectedItem();
    
    try {
        if (!fattureAcquisto.isEmpty())
            new StampaScadenziario(forn_cliente, fattureAcquisto, Fattura.tipo.ACQ, (String) cboPeriodi.getSelectedItem()).printAndOpen();
        else
            JOptionPane.showMessageDialog(this, "Nessuna fattura da stampare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_mnuAcquistoActionPerformed

private void mnuEntrambeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEntrambeActionPerformed
// TODO add your handling code here:
    
    Fornitore forn_cliente = null;
    if (cboFornCliente.getSelectedIndex() > 0)
        forn_cliente = (Fornitore) cboFornCliente.getSelectedItem();
    
    try {
        if (!fattureEmesse.isEmpty() && !fattureAcquisto.isEmpty())
            new StampaScadenziario(forn_cliente, fattureEmesse, fattureAcquisto, Fattura.tipo.ALL, (String) cboPeriodi.getSelectedItem()).printAndOpen();
        else if (fattureEmesse.isEmpty() && fattureAcquisto.isEmpty())
            JOptionPane.showMessageDialog(this, "Nessuna fattura da stampare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        else if (fattureAcquisto.isEmpty())
            JOptionPane.showMessageDialog(this, "Nessuna fattura di acquisto da stampare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, "Nessuna fattura emessa da stampare", "Attenzione", JOptionPane.WARNING_MESSAGE);
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_mnuEntrambeActionPerformed
 
void setTableEmesse() {
    
    Fornitore forn_cliente = null;
    if (cboFornCliente.getSelectedIndex() == 0)
        forn_cliente = new Fornitore(null);  
    else
        forn_cliente = (Fornitore) cboFornCliente.getSelectedItem();
    
    //Richiama le fatture emesse pagate di un certo fornitore all'interno di un intervallo date globale (dall'inizio dell'esercizio alla data odierna)
    List<Fattura> fattureEmesseProvvisorie = FrontController.getFatture(forn_cliente, Fattura.tipo.VEN);
    List<Fattura> fattureEmesse = new LinkedList<Fattura>();
        
    for (Fattura fattura : fattureEmesseProvvisorie){
        Date scadenza = fattura.getDataScadenza();
        java.util.Date utilDate = new java.util.Date();
        String today = (new java.sql.Date(utilDate.getTime())).toString();
        String[] splitted = today.split("\\-");
        String anno = splitted[0];
        String mese = splitted[1];
        String giorno = splitted[2];
        Date dataOdierna = Date.valueOf(anno + "-" + mese + "-" + giorno);
        if (cboPeriodi.getSelectedIndex() == SEMPRE) {
            if (!fattura.getPagata())
                fattureEmesse.add(fattura);
            
        } else if(cboPeriodi.getSelectedIndex() == OGGI) {
            if (scadenza.compareTo(dataOdierna) == 0)
                fattureEmesse.add(fattura);
            
        } else if(cboPeriodi.getSelectedIndex() == SCELTA) {
            //Date inMese = Date.valueOf(anno + "-" + mese + "-01");
            //Date fMese = Date.valueOf(anno + "-" + mese + "-31");
            if (scadenza.compareTo(dataScelta) <= 0)
                fattureEmesse.add(fattura);
        }
    }
    
    if (forn_cliente.getCod() != null && !fattureEmesse.isEmpty())
        mnuCliente.setEnabled(true);
    else
        mnuCliente.setEnabled(false);
    
    this.fattureEmesse = fattureEmesse;
    
    Object[] arFatt = fattureEmesse.toArray();
    Object[][] arrayFatt = new Object[arFatt.length][Fattura.NUM_CAMPI_EMESSE];
    int contFatt = 0;
    for (Object fatt : arFatt) {
        arrayFatt[contFatt++] = ((Fattura) fatt).toArray();        
    }
    
    final String[] COLONNE = {
        "CLIENTE", "NUM. FATT.", "DATA", "IMPONIBILE", "IVA", "TOTALE", "MOD. PAGAMENTO", "PAGATA", 
        "SCADENZA", "NOTE PAGAM.", "NOTE"
    };
    
    Class[] types = { String.class, Integer.class, Object.class,
                    Double.class, Double.class, Double.class, String.class, Character.class, Object.class, String.class, String.class };
    
    TableModel tm = new FattureTableModel(arrayFatt, COLONNE, types, new boolean[] {
        false, false, false, false, false, false, false, false, false, false, false
    });    
        
    tblScadenzeEmesse.setModel(tm);
    tblScadenzeEmesse.setRowSorter(new TableRowSorter(tm) {

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
            setComparator(DATA_VEN, new DateComparator());
            setComparator(SCADENZA_VEN, new DateComparator());
            super.sort();

        }
        
    });
    
    boolean[] resizable = {
        true, false, false, false, false, false, false, false, false, true, true
    };
    
    int[] width = {
        300, 100, 90, 125, 125, 125, 170, 70, 125, 300, 300
    };
    
    tblScadenzeEmesse.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    tblScadenzeEmesse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    for (int i = 0; i < COLONNE.length; i++) {
        TableColumn colonna = tblScadenzeEmesse.getColumnModel().getColumn(i);
        colonna.setResizable(resizable[i]);
        colonna.setPreferredWidth(width[i]);
        if (i == IMPONIBILE_VEN || i == IVA_VEN || i == TOTALE_VEN)
            colonna.setCellRenderer(new DoubleFormatter());
    }
    
    tblScadenzeEmesse.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    
    tblScadenzeEmesse.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mnuCliente.setEnabled(true);
            }
        
    });
}

void setTableAcquisto() {
    
    Fornitore forn_cliente = null;
    if (cboFornCliente.getSelectedIndex() == 0)
        forn_cliente = new Fornitore(null);  
    else
        forn_cliente = (Fornitore) cboFornCliente.getSelectedItem();
    
    //Richiama le fatture emesse pagate di un certo fornitore all'interno di un intervallo date globale (dall'inizio dell'esercizio alla data odierna)
    List<Fattura> fattureEmesseAcquisto = FrontController.getFatture(forn_cliente, Fattura.tipo.ACQ);
    List<Fattura> fattureAcquisto = new LinkedList<Fattura>();
    
    for (Fattura fattura : fattureEmesseAcquisto){
        Date scadenza = fattura.getDataScadenza();
        java.util.Date utilDate = new java.util.Date();
        String today = (new java.sql.Date(utilDate.getTime())).toString();
        String[] splitted = today.split("\\-");
        String anno = splitted[0];
        String mese = splitted[1];
        String giorno = splitted[2];
        Date dataOdierna = Date.valueOf(anno + "-" + mese + "-" + giorno);
        if (cboPeriodi.getSelectedIndex() == SEMPRE) {
            if (!fattura.getPagata())
                fattureAcquisto.add(fattura);
            
        } else if(cboPeriodi.getSelectedIndex() == OGGI) {
            if (scadenza.compareTo(dataOdierna) == 0)
                fattureAcquisto.add(fattura);
            
        } else if(cboPeriodi.getSelectedIndex() == SCELTA) {
            if (scadenza.compareTo(dataScelta) <= 0)
                fattureAcquisto.add(fattura);
        }
    }
    
    this.fattureAcquisto = fattureAcquisto;
    
    Object[] arFatt = fattureAcquisto.toArray();
    Object[][] arrayFatt = new Object[arFatt.length][Fattura.NUM_CAMPI_ACQUISTO];
    int contFatt = 0;
    for (Object fatt : arFatt) {
        arrayFatt[contFatt++] = ((Fattura) fatt).fattAcquistoToArray();        
    }
    
    final String[] COLONNE = {
        "FORNITORE", "TIPO", "NUM. DOC", "DATA", "IMPONIBILE", "IVA", 
        "TOTALE", "MOD. PAGAMENTO", "PAGATA",  "SCADENZA", "NOTE PAGAM.", "NOTE" 
    };
    
    Class[] types = { String.class, String.class, Integer.class,
                    Object.class, Double.class, Double.class, Double.class, String.class, Character.class, Object.class, String.class, String.class };
    
    TableModel tm = new FattureTableModel(arrayFatt, COLONNE, types, new boolean[] {
        false, false, false, false, false, false, false, false, false, false, false, false
    });
    
    tblScadenzeAcquisto.setModel(tm);
    
    tblScadenzeAcquisto.setRowSorter(new TableRowSorter(tm) {

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
            setComparator(DATA_ACQ, new DateComparator());
            setComparator(SCADENZA_ACQ, new DateComparator());
            super.sort();

        }
        
    });
    
    boolean[] resizable = {
        true, true, false, false, false, false, false, false, false, false, true, true
    };
    
    int[] width = {
        300, 90, 100, 90, 125, 125, 125, 170, 70, 125, 300, 300
    };
    
    tblScadenzeAcquisto.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    tblScadenzeAcquisto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    for (int i = 0; i < COLONNE.length; i++) {
        TableColumn colonna = tblScadenzeAcquisto.getColumnModel().getColumn(i);
        colonna.setResizable(resizable[i]);
        colonna.setPreferredWidth(width[i]);
        if (i== IMPONIBILE_ACQ || i == IVA_ACQ || i == TOTALE_ACQ)
            colonna.setCellRenderer(new DoubleFormatter());
            //colonna.setCellRenderer(new DefaultTableCellRenderer());
    }
    
    tblScadenzeAcquisto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
}


/*
 * Popola la select dei fornitori.
 */
private void popolaSelect(List items) {
    for (Object item : items)
        cboFornCliente.addItem((Fornitore) item);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboFornCliente;
    private javax.swing.JComboBox cboPeriodi;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem mnuAcquisto;
    private javax.swing.JMenu mnuCliente;
    private javax.swing.JMenuItem mnuEmesse;
    private javax.swing.JMenuItem mnuEntrambe;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuSollecito;
    private javax.swing.JMenu mnuStampa;
    private javax.swing.JPanel pnlAcquisto;
    private javax.swing.JPanel pnlCliente;
    private javax.swing.JPanel pnlEmesse;
    private javax.swing.JPanel pnlScadenza;
    private javax.swing.JTable tblScadenzeAcquisto;
    private javax.swing.JTable tblScadenzeEmesse;
    // End of variables declaration//GEN-END:variables
    
    private static final int SEMPRE = 0;
    private static final int OGGI = 1;
    private static final int SCELTA = 2;
    
    private static final int CLIENTE = 0;
    private static final int NUM_VEN = 1;
    private static final int DATA_VEN = 2;
    private static final int IMPONIBILE_VEN = 3;
    private static final int IVA_VEN = 4;
    private static final int TOTALE_VEN = 5;
    private static final int MOD_PAG_VEN = 6;
    private static final int PAGATA_VEN = 7;
    private static final int SCADENZA_VEN = 8;
    private static final int NOTE_PAG_VEN = 9;
    private static final int NOTE_VEN = 10;
    
    private static final int FORNITORE = 0;
    private static final int TIPO = 1;
    private static final int NUM_ACQ = 2;
    private static final int DATA_ACQ = 3;
    private static final int IMPONIBILE_ACQ = 4;
    private static final int IVA_ACQ = 5;
    private static final int TOTALE_ACQ = 6;
    private static final int MOD_PAG_ACQ = 7;
    private static final int PAGATA_ACQ = 8;
    private static final int SCADENZA_ACQ = 9;
    private static final int NOTE_PAG_ACQ = 10;
    private static final int NOTE_ACQ = 11;
    
    private List<Fattura> fattureEmesse;
    private List<Fattura> fattureAcquisto;
    
    private Date dataScelta = null;
}
