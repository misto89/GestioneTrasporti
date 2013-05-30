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
import contabilizzazione.BilancioMensile;
import contabilizzazione.SaldoContabilitaMensile;
import contabilizzazione.SaldoIvaMensile;
import controllo.FrontController;
import entita.Entity;
import entita.Fattura;
import entita.Fornitore;
import java.awt.Color;
import java.io.IOException;
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import libs.DoubleFormatter;
import libs.Utility;
import stampa.StampaBilancioFatture;
import stampa.StampaMovimentazioneMensile;


/**
 *
 * @author Michele
 */
public class Contabilita extends javax.swing.JFrame {
    
    private class ContabilitaTableModel extends DefaultTableModel {
        
        private final boolean[] CAN_EDIT;
        private final Class[] types;
        
        public ContabilitaTableModel(Object[][] righe, String[] colonne, Class[] tps, boolean[] edit) {
            super(righe, colonne);
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
    public Contabilita() {
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlAnno);
        color.changeColor(pnlCliente);
        color.changeColor(pnlPagate);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAnno = new javax.swing.JPanel();
        cboAnno = new javax.swing.JComboBox();
        pnlPagate = new javax.swing.JPanel();
        optTutte = new javax.swing.JRadioButton();
        optPagate = new javax.swing.JRadioButton();
        optNonPagate = new javax.swing.JRadioButton();
        pnlCliente = new javax.swing.JPanel();
        cboCliente = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblContEmesse = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTotNeg = new javax.swing.JTextField();
        txtTotPos = new javax.swing.JTextField();
        txtTotSaldo = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        mnuIntervalloDate = new javax.swing.JCheckBoxMenuItem();
        mnuProspetto = new javax.swing.JMenu();
        mnuStampa = new javax.swing.JMenuItem();
        mnuIVa = new javax.swing.JMenu();
        mnuProspettoIva = new javax.swing.JMenuItem();
        mnuBilancio = new javax.swing.JMenu();
        mnuBilancioFattureEmesse = new javax.swing.JMenuItem();
        mnuBilancioFattureAcquisto = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Contabilità");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlAnno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Anno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        cboAnno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAnnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAnnoLayout = new javax.swing.GroupLayout(pnlAnno);
        pnlAnno.setLayout(pnlAnnoLayout);
        pnlAnnoLayout.setHorizontalGroup(
            pnlAnnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAnnoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboAnno, 0, 115, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAnnoLayout.setVerticalGroup(
            pnlAnnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnnoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(cboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPagate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pagate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        optTutte.setSelected(true);
        optTutte.setText("Tutte");
        optTutte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTutteActionPerformed(evt);
            }
        });

        optPagate.setText("Pagate");
        optPagate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPagateActionPerformed(evt);
            }
        });

        optNonPagate.setText("Non Pagate");
        optNonPagate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optNonPagateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPagateLayout = new javax.swing.GroupLayout(pnlPagate);
        pnlPagate.setLayout(pnlPagateLayout);
        pnlPagateLayout.setHorizontalGroup(
            pnlPagateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPagateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(optTutte)
                    .addComponent(optPagate)
                    .addComponent(optNonPagate))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlPagateLayout.setVerticalGroup(
            pnlPagateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPagateLayout.createSequentialGroup()
                .addComponent(optTutte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optPagate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optNonPagate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tutti i clienti" }));
        cboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlClienteLayout = new javax.swing.GroupLayout(pnlCliente);
        pnlCliente.setLayout(pnlClienteLayout);
        pnlClienteLayout.setHorizontalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboCliente, 0, 477, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlClienteLayout.setVerticalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblContEmesse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblContEmesse.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblContEmesse);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("TOTALI");

        txtTotNeg.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotNeg.setEnabled(false);

        txtTotPos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotPos.setEnabled(false);

        txtTotSaldo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotSaldo.setEnabled(false);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filtra.png"))); // NOI18N
        jMenu3.setText("Filtra");

        mnuIntervalloDate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        mnuIntervalloDate.setText("Per intervallo date");
        mnuIntervalloDate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/intervallodate.png"))); // NOI18N
        mnuIntervalloDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuIntervalloDateActionPerformed(evt);
            }
        });
        jMenu3.add(mnuIntervalloDate);

        jMenuBar1.add(jMenu3);

        mnuProspetto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prospetto.png"))); // NOI18N
        mnuProspetto.setText("Prospetto");

        mnuStampa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnuStampa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stampa.png"))); // NOI18N
        mnuStampa.setText("Stampa");
        mnuStampa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuStampaActionPerformed(evt);
            }
        });
        mnuProspetto.add(mnuStampa);

        jMenuBar1.add(mnuProspetto);

        mnuIVa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filtropiva.png"))); // NOI18N
        mnuIVa.setText("IVA");

        mnuProspettoIva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuProspettoIva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prospettoiva.png"))); // NOI18N
        mnuProspettoIva.setText("Prospetto IVA mensile");
        mnuProspettoIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuProspettoIvaActionPerformed(evt);
            }
        });
        mnuIVa.add(mnuProspettoIva);

        jMenuBar1.add(mnuIVa);

        mnuBilancio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prelievo.png"))); // NOI18N
        mnuBilancio.setText("Bilancio");

        mnuBilancioFattureEmesse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        mnuBilancioFattureEmesse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/emettifattura.png"))); // NOI18N
        mnuBilancioFattureEmesse.setText("Fatture Emesse");
        mnuBilancioFattureEmesse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBilancioFattureEmesseActionPerformed(evt);
            }
        });
        mnuBilancio.add(mnuBilancioFattureEmesse);

        mnuBilancioFattureAcquisto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuBilancioFattureAcquisto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prospettoiva.png"))); // NOI18N
        mnuBilancioFattureAcquisto.setText("Fatture Acquisto");
        mnuBilancioFattureAcquisto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBilancioFattureAcquistoActionPerformed(evt);
            }
        });
        mnuBilancio.add(mnuBilancioFattureAcquisto);

        jMenuBar1.add(mnuBilancio);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPagate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotPos, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtTotNeg, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(txtTotSaldo))))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPagate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAnno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotNeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnlAnno, pnlCliente, pnlPagate});

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void optTutteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTutteActionPerformed
// TODO add your handling code here:
    optNonPagate.setSelected(false);
    optPagate.setSelected(false);
    optTutte.setSelected(true);
    setTable();
}//GEN-LAST:event_optTutteActionPerformed

private void optPagateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPagateActionPerformed
// TODO add your handling code here:
    optNonPagate.setSelected(false);
    optTutte.setSelected(false);
    optPagate.setSelected(true);
    setTable();
}//GEN-LAST:event_optPagateActionPerformed

private void optNonPagateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optNonPagateActionPerformed
// TODO add your handling code here:
    optPagate.setSelected(false);
    optTutte.setSelected(false);
    optNonPagate.setSelected(true);
    setTable();
}//GEN-LAST:event_optNonPagateActionPerformed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    List<Entity> fornitori = FrontController.getAnagrafe(Fornitore.class);
    List<Integer> anni = FrontController.getAnniEsercizio(Fattura.tipo.ALL);
    popolaSelect(fornitori);
    popolaSelect(anni);   
    String anno = String.valueOf(cboAnno.getSelectedItem());
    dataIniziale = Date.valueOf(anno + "-01-01");
    dataFinale = Date.valueOf(anno + "-12-31");
    setTable();
}//GEN-LAST:event_formWindowOpened

/*
 * Prende come parametri d'ingresso due sql.Date e compila un array di String contenente tutti i mesi nel periodo passato come parametro.
 * I mesi da inserire nell'array sono nella forma "anno-mese";
 */
private void setAnniMesi(Date iniziale, Date finale) {
    String dataI = iniziale.toString();
    String dataF = finale.toString();
    int annoI = Integer.parseInt(dataI.substring(0,4));
    int meseI = Integer.parseInt(dataI.substring(5,7));
    int annoF = Integer.parseInt(dataF.substring(0,4));
    int meseF = Integer.parseInt(dataF.substring(5,7));
    
    //Valutazione del periodo inserito, a cavallo fra due o più anni, o nello stesso anno.
    if (annoI == annoF) {
        int diff = meseF - meseI;  //es. 12-1
        anniMesi = new String[diff+1];
        if (String.valueOf(meseI).length() == 1) //Se l'int del mese iniziale è nella forma 1, 2, 3.... 9
            anniMesi[0] = annoI + "-0" + String.valueOf(meseI);
        else 
            anniMesi[0] = annoI + "-" + String.valueOf(meseI);
        
        int j = 1;
        for (int i = meseI+1; i < meseF; i++) {
            String mese = String.valueOf(i);
            if (mese.length() == 1)
                mese = "0" + i;
            anniMesi[j] = annoI + "-" + mese;
            j++;
        }
        if (diff > 0) {
            if (String.valueOf(meseF).length() == 1)
                anniMesi[diff] = annoI + "-0" + meseF;
            else
                anniMesi[diff] = annoI + "-" + meseF;
        }
    } else { //se le date sono a cavallo fra 2 o più anni
        int diffAnni = annoF - annoI;
        int mesiTot = (12-meseI + 1) + (12 * (diffAnni -1)) + (meseF);
        anniMesi = new String[mesiTot];
        
        //inserimento nell'array dell'anno e mese iniziale
        if (String.valueOf(meseI).length() == 1)
            anniMesi[0] = annoI + "-0" + meseI;
        else 
            anniMesi[0] = annoI + "-" + meseI;
        
        int j = 1;
        int anno = annoI;
        //ciclo che inserisce nell'array di anniMesi tutti i valori dei mesi tranne quelli dell'ultimo anno del periodo di riferimento
        for (int i = 0; i < diffAnni; i++) {
            for (int h = meseI + 1; h <= 12; h++){
                if (String.valueOf(h).length() == 1) 
                    anniMesi[j] = anno + "-0" + h;
                else
                    anniMesi[j] = anno + "-" + h;                
                j++;
            }
            //porto l'anno pari all'anno successivo in caso di ulteriore iterata del ciclo (diffAnni > 1)
            anno = anno + 1;
            //riporto il mese iniziale a 1 in caso di ulteriore iterata
            meseI = 0;
        }
        //ciclo che inserisce nell'array anniMesi le occorrenze dell'ultimo anno del periodo inserito
        for (int i = 1; i < meseF; i++) {
            if (String.valueOf(i).length() == 1) 
                    anniMesi[j] = annoF + "-0" + i;
                else
                    anniMesi[j] = annoF + "-" + i;                
                j++;
        }
        if (String.valueOf(meseF).length() == 1)
            anniMesi[mesiTot-1] = annoF + "-0" + meseF;
        else
            anniMesi[mesiTot-1] = annoF + "-" + meseF;             
    }
} 

private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
// TODO add your handling code here:
    try {
        codFornCliente = ((Fornitore) cboCliente.getSelectedItem()).getCod();
        
    } catch (ClassCastException e) {
        codFornCliente = -1;
        
    }
    
    setTable();
    
}//GEN-LAST:event_cboClienteActionPerformed

private void cboAnnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAnnoActionPerformed
// TODO add your handling code here:
    String anno = String.valueOf(cboAnno.getSelectedItem());
    dataIniziale = Date.valueOf(anno + "-01-01");
    dataFinale = Date.valueOf(anno + "-12-31");
    mnuIntervalloDate.setSelected(false);
    setTable();
}//GEN-LAST:event_cboAnnoActionPerformed

private void mnuIntervalloDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuIntervalloDateActionPerformed
// TODO add your handling code here:
    // TODO add your handling code here:
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
                      
            try {
                dataIniziale = Utility.dateValueOf(anno, mese, giorno, "data iniziale");
                okDate = true;
                
            } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
                JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Formato errato", JOptionPane.ERROR_MESSAGE);
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
        
        if (!(dataF.isEmpty())) { //La data inserita non è la stringa vuota
            if (!checkData(dataF)) //Il formato della data inserita dall'utente, non è gg/mm/aaaa
                JOptionPane.showMessageDialog(rootPane, "Inserisci la data nel formato gg/mm/aaaa", "Formato errato", JOptionPane.ERROR_MESSAGE);
            
            else { //Il formato è corretto
                String splitted[] = dataF.split("\\/");
                String giorno = splitted[0];
                String mese = splitted[1];
                String anno = splitted[2];
                               
                try {
                    dataFinale = Utility.dateValueOf(anno, mese, giorno, "data finale");
                    okDate = true;
                    
                } catch (IllegalArgumentException e) { //Il valore inserito per la data non è valido, perché non esiste. Per esempio si inserisce 13 come mese
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Formato errato", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else //L'utente non ha inserito alcuna data, quindi viene considerata la data odierna come data finale
            okDate = true;
   }
   if (okDate){
       setTable();
   }
}//GEN-LAST:event_mnuIntervalloDateActionPerformed

private void mnuProspettoIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuProspettoIvaActionPerformed
// TODO add your handling code here:
    FrontController.open(new ProspettoIva(this, rootPaneCheckingEnabled));
}//GEN-LAST:event_mnuProspettoIvaActionPerformed

private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
// TODO add your handling code here:  
    
    try {
        
        boolean forn_clienteSelezionato = cboCliente.getSelectedIndex() > 0;
        
        StampaMovimentazioneMensile.tipo tipo;
        if (optTutte.isSelected())
            tipo = StampaMovimentazioneMensile.tipo.TUTTE;
        else if (optNonPagate.isSelected())
            tipo = StampaMovimentazioneMensile.tipo.NON_PAGATE;
        else
            tipo = StampaMovimentazioneMensile.tipo.PAGATE;
        
        String[] totali = {
            txtTotPos.getText(),
            txtTotNeg.getText(),
            txtTotSaldo.getText()
        };
        
        
        if (mnuIntervalloDate.isSelected() && forn_clienteSelezionato) {
            new StampaMovimentazioneMensile((Fornitore)cboCliente.getSelectedItem(), dataIniziale, dataFinale, listaSaldi, tipo, totali).printAndOpen();
   
        } else if (mnuIntervalloDate.isSelected() && !forn_clienteSelezionato) {
            new StampaMovimentazioneMensile(dataIniziale, dataFinale, listaSaldi, tipo, totali).printAndOpen();
            
        } else if (!mnuIntervalloDate.isSelected() && forn_clienteSelezionato) {
            new StampaMovimentazioneMensile((Fornitore)cboCliente.getSelectedItem(), (Integer) cboAnno.getSelectedItem(), listaSaldi, tipo, totali).printAndOpen();
            
        } else if (!mnuIntervalloDate.isSelected() && !forn_clienteSelezionato) {
            new StampaMovimentazioneMensile((Integer) cboAnno.getSelectedItem(), listaSaldi, tipo, totali).printAndOpen();
            
        }
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_mnuStampaActionPerformed

    private void mnuBilancioFattureEmesseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBilancioFattureEmesseActionPerformed
        FrontController.open(new ProspettoBilancio(this, rootPaneCheckingEnabled, "emesse"));
    }//GEN-LAST:event_mnuBilancioFattureEmesseActionPerformed

    private void mnuBilancioFattureAcquistoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBilancioFattureAcquistoActionPerformed
        FrontController.open(new ProspettoBilancio(this, rootPaneCheckingEnabled, "acquisto"));
    }//GEN-LAST:event_mnuBilancioFattureAcquistoActionPerformed

private String doubleToString(double d) {
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
    String string = currencyFormatter.format(d);
    return string;
}

private void setTable() {
    
    Fattura.pagata tipo;
    if (optTutte.isSelected())
        tipo = Fattura.pagata.ALL;
    else if (optNonPagate.isSelected())
        tipo = Fattura.pagata.NP;
    else
        tipo = Fattura.pagata.P;
    
    setAnniMesi(dataIniziale, dataFinale);
    List<SaldoContabilitaMensile> contabilita = FrontController.getContabilitaFatture(anniMesi, tipo, codFornCliente);
    listaSaldi = contabilita;
    
    Object[] contab = contabilita.toArray();
    Object[][] arrayContabilita = new Object[contab.length][SaldoContabilitaMensile.NUM_CAMPI];
    int cont = 0;
    double totPos = 0.00;
    double totNeg = 0.00;
    double totSaldo = 0.00;
    
    for (SaldoContabilitaMensile saldo : contabilita) {
        SaldoContabilitaMensile s = ((SaldoContabilitaMensile) saldo);
        arrayContabilita[cont++] = s.toArray();
        totPos += s.getTotPos();
        totNeg += s.getTotNeg();
        totSaldo += s.getSaldo();
    }

    txtTotNeg.setHorizontalAlignment(JTextField.RIGHT);
    txtTotPos.setHorizontalAlignment(JTextField.RIGHT);
    txtTotSaldo.setHorizontalAlignment(JTextField.RIGHT);
    txtTotNeg.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totNeg)));
    txtTotPos.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totPos)));
    txtTotSaldo.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totSaldo)));
    
    final String[] COLONNE = {
        "PERIODO", "ENTRATE", "USCITE", "SALDO"
    };
    
    Class[] types = {String.class, Double.class, Double.class, Double.class};
    
    TableModel tm = new ContabilitaTableModel(arrayContabilita, COLONNE, types, new boolean[] {
        false, false, false, false
    });    
   
    tblContEmesse.setModel(tm);
    TableRowSorter sorter = new TableRowSorter(tm);
    sorter.setSortable(PERIODO, false);
    tblContEmesse.setRowSorter(sorter);
    
    boolean[] resizable = {
        false, false, false, false
    };
    
//    int[] width = {
//        300, 100, 90, 170
//    };
    
    tblContEmesse.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    //tblContEmesse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    for (int i = 0; i < COLONNE.length; i++) {
        TableColumn colonna = tblContEmesse.getColumnModel().getColumn(i);
        colonna.setResizable(resizable[i]);
        
        if (i == EMESSE || i == ACQUISTO || i == SALDO)
            colonna.setCellRenderer(new DoubleFormatter());
        else {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setBackground(Color.lightGray);
            colonna.setCellRenderer(renderer);
        }
 
        //colonna.setPreferredWidth(width[i]);
    }
       
//    int numRows = tblContEmesse.getRowCount();
//    for (int row = 0; row < numRows; row++)
//        if ( (Double) tblContEmesse.getValueAt(row, SALDO) < 0)
//            for (int col = PERIODO; col < SALDO; col++)
//               tblContEmesse.getCellRenderer(row, col).getTableCellRendererComponent(tblContEmesse, 
//                        tblContEmesse.getValueAt(row, col), tblContEmesse.isCellSelected(row, col), false, row, col).setBackground(Color.red);
    
    tblContEmesse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}

private boolean checkData(String data) {
    Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
    Matcher match = pattern.matcher(data);
    return match.matches();
}

/*
 * Popola la select dei fornitori.
 */
private void popolaSelect(List items) {
    for (Object item : items)
        if (item instanceof Integer)
            cboAnno.addItem((Integer)item);
        else
            cboCliente.addItem((Fornitore) item);
}

void stampaProspettoBilancio(List<BilancioMensile> prospetto, String[] totali, String type, int anno, Fornitore fornCliente) {
    try {       
        if (fornCliente == null)
            new StampaBilancioFatture(anno, prospetto, type, totali).printAndOpen();
        else
            new StampaBilancioFatture(fornCliente, anno, prospetto, type, totali).printAndOpen();

    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}

void stampaProspettoIva(List<SaldoIvaMensile> prospettoIva, String[] totali) {
    try {       
        
        if (mnuIntervalloDate.isSelected()) {
            new StampaMovimentazioneMensile(dataIniziale, dataFinale, prospettoIva, null, totali).printAndOpen();
            
        } else {
            new StampaMovimentazioneMensile((Integer) cboAnno.getSelectedItem(), prospettoIva, null, totali).printAndOpen();
            
        }
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboAnno;
    private javax.swing.JComboBox cboCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mnuBilancio;
    private javax.swing.JMenuItem mnuBilancioFattureAcquisto;
    private javax.swing.JMenuItem mnuBilancioFattureEmesse;
    private javax.swing.JMenu mnuIVa;
    private javax.swing.JCheckBoxMenuItem mnuIntervalloDate;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuProspettoIva;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JRadioButton optNonPagate;
    private javax.swing.JRadioButton optPagate;
    private javax.swing.JRadioButton optTutte;
    private javax.swing.JPanel pnlAnno;
    private javax.swing.JPanel pnlCliente;
    private javax.swing.JPanel pnlPagate;
    private javax.swing.JTable tblContEmesse;
    private javax.swing.JTextField txtTotNeg;
    private javax.swing.JTextField txtTotPos;
    private javax.swing.JTextField txtTotSaldo;
    // End of variables declaration//GEN-END:variables
    
    //private List<Fattura> fattureInTabella;
    Date dataIniziale;
    Date dataFinale;
    public String[] anniMesi;
    private int codFornCliente = -1;
    private List listaSaldi;
    
    private static final int PERIODO = 0;
    private static final int EMESSE = 1;
    private static final int ACQUISTO = 2;
    private static final int SALDO = 3;
}