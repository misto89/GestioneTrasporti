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
import contabilizzazione.SaldoCassaMensile;
import controllo.FrontController;
import entita.Entity;
import entita.Fattura;
import entita.Fornitore;
import java.awt.event.MouseEvent;
import movimentazionecontante.Versamento;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
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
import movimentazionecontante.MovimentazioneContante;
import movimentazionecontante.Prelievo;
import stampa.StampaCassa;
import stampa.StampaCassa.RiepilogoCassa;

/**
 *
 * @author Michele
 */
public class Cassa extends javax.swing.JFrame {
    
    private class CassaTableModel extends DefaultTableModel {
        
        private final boolean[] CAN_EDIT;
        private final Class[] types;
        
        public CassaTableModel(Object[][] righe, String[] colonne, Class[] tps, boolean[] edit) {
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

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            super.setValueAt(aValue, row, column);
            
            MovimentazioneContante movimento = movimentiContante.get(row);
            
            int id = movimento.getId();
            String banca = ((String) tblMovimenti.getValueAt(row, BANCA));
            double importo = 0.00;
            
            try {
                importo = ((Double) tblMovimenti.getValueAt(row, IMPORTO));
            } catch (NullPointerException e) {
                setValueAt(movimento.getImporto(), row, column);
                return;
            }
                        
            if (column == IMPORTO) {
                Double totVersamenti = Double.parseDouble(txtVersamentiTot.getText());
                Double totPrelievi = Double.parseDouble(txtPrelievi.getText());
                Double attivoContante = Double.parseDouble(txtAttivoContante.getText());
                Double passivoContante = Double.parseDouble(txtPassivoContante.getText());
                                
                String tipo = ((String) tblMovimenti.getValueAt(row, TIPO));
                
                if (tipo.equalsIgnoreCase(Versamento.class.getSimpleName())) {
                    totVersamenti -= movimento.getImporto();
                    totVersamenti += (Double) aValue;
                    txtVersamentiTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totVersamenti)));
                } else {
                    totPrelievi -= movimento.getImporto();
                    totPrelievi += (Double) aValue;
                    txtPrelievi.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totPrelievi)));
                }
                
                Double netto = attivoContante + totPrelievi - passivoContante - totVersamenti;
                txtCassaNetto.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(netto)));
                
            }   
            
            movimento.setBanca(banca);
            movimento.setImporto(importo);
            
            if (!FrontController.updateMovimentazioneContante(movimento))
                JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la modifica!" , "Errore", JOptionPane.ERROR_MESSAGE);
            
            movimentiContante.set(row, movimento);
        }
        
    }

    /** Creates new form RegistroFattureEmesse */
    public Cassa() {
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlAnno);
        color.changeColor(pnlCliente);
        color.changeColor(pnlCassa);
        color.changeColor(pnlEntrate);
        color.changeColor(pnlUscite);
        color.changeColor(pnlMovimenti);
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
        pnlCliente = new javax.swing.JPanel();
        cboCliente = new javax.swing.JComboBox();
        pnlCassa = new javax.swing.JPanel();
        txtAttivoContante = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtVersamentiTot = new javax.swing.JTextField();
        txtCassaNetto = new javax.swing.JTextField();
        txtPrelievi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtPassivoContante = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnVersamento = new javax.swing.JButton();
        btnPrelievo = new javax.swing.JButton();
        pnlEntrate = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCassaAttiva = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtTotAssegniEntrate = new javax.swing.JTextField();
        txtTotBonificoEntrate = new javax.swing.JTextField();
        txtTotRibaEntrate = new javax.swing.JTextField();
        txtTotContanteEntrate = new javax.swing.JTextField();
        txtTotAccreditoEntrate = new javax.swing.JTextField();
        pnlUscite = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCassaPassiva = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtTotContanteUscite = new javax.swing.JTextField();
        txtTotAssegniUscite = new javax.swing.JTextField();
        txtTotBonificoUscite = new javax.swing.JTextField();
        txtTotRibaUscite = new javax.swing.JTextField();
        txtTotAccreditoUscite = new javax.swing.JTextField();
        pnlMovimenti = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMovimenti = new javax.swing.JTable();
        btnEliminaMovimento = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        mnuIntervalloDate = new javax.swing.JCheckBoxMenuItem();
        mnuProspetto = new javax.swing.JMenu();
        mnuStampa = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cassa");
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
            .addGroup(pnlAnnoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAnnoLayout.setVerticalGroup(
            pnlAnnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnnoLayout.createSequentialGroup()
                .addComponent(cboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente/Fornitore", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tutti" }));
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
                .addComponent(cboCliente, 0, 533, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlClienteLayout.setVerticalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCassa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Riepilogo Cassa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlCassa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAttivoContante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAttivoContante.setEnabled(false);
        pnlCassa.add(txtAttivoContante, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 28, 120, 30));

        jLabel4.setText("Prelievi");
        pnlCassa.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, 21));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Netto in cassa");
        pnlCassa.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 21));

        jLabel5.setText("Versamenti");
        pnlCassa.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 21));

        txtVersamentiTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtVersamentiTot.setEnabled(false);
        pnlCassa.add(txtVersamentiTot, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 120, 30));

        txtCassaNetto.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtCassaNetto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCassaNetto.setEnabled(false);
        pnlCassa.add(txtCassaNetto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 144, 120, 30));

        txtPrelievi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPrelievi.setEnabled(false);
        pnlCassa.add(txtPrelievi, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 83, 120, 30));

        jLabel1.setText("Attivo Contante");
        pnlCassa.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 21));

        txtPassivoContante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPassivoContante.setEnabled(false);
        pnlCassa.add(txtPassivoContante, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 56, 120, 30));

        jLabel3.setText("Passivo Contante");
        pnlCassa.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, 21));

        btnVersamento.setBackground(new java.awt.Color(255, 255, 255));
        btnVersamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/versamento.png"))); // NOI18N
        btnVersamento.setText("Effettua Versamento");
        btnVersamento.setToolTipText("Registra un nuovo versamento di contante");
        btnVersamento.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnVersamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVersamentoActionPerformed(evt);
            }
        });
        pnlCassa.add(btnVersamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 139, 180, 40));

        btnPrelievo.setBackground(new java.awt.Color(255, 255, 255));
        btnPrelievo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prelievo.png"))); // NOI18N
        btnPrelievo.setText("Effettua Prelievo");
        btnPrelievo.setToolTipText("Effettua un prelievo");
        btnPrelievo.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnPrelievo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrelievoActionPerformed(evt);
            }
        });
        pnlCassa.add(btnPrelievo, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 185, 180, -1));

        pnlEntrate.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Entrate", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlEntrate.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCassaAttiva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCassaAttiva.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblCassaAttiva);

        pnlEntrate.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, 640, 226));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("TOTALI");
        pnlEntrate.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 275, 58, -1));

        txtTotAssegniEntrate.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotAssegniEntrate.setEnabled(false);
        pnlEntrate.add(txtTotAssegniEntrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 110, -1));

        txtTotBonificoEntrate.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotBonificoEntrate.setEnabled(false);
        pnlEntrate.add(txtTotBonificoEntrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 110, -1));

        txtTotRibaEntrate.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotRibaEntrate.setEnabled(false);
        pnlEntrate.add(txtTotRibaEntrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 100, -1));

        txtTotContanteEntrate.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotContanteEntrate.setEnabled(false);
        pnlEntrate.add(txtTotContanteEntrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 110, -1));

        txtTotAccreditoEntrate.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotAccreditoEntrate.setEnabled(false);
        pnlEntrate.add(txtTotAccreditoEntrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 110, -1));

        pnlUscite.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Uscite", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlUscite.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblCassaPassiva.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCassaPassiva.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(tblCassaPassiva);

        pnlUscite.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, 640, 226));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("TOTALI");
        pnlUscite.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 275, 58, -1));

        txtTotContanteUscite.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotContanteUscite.setEnabled(false);
        pnlUscite.add(txtTotContanteUscite, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 110, -1));

        txtTotAssegniUscite.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotAssegniUscite.setEnabled(false);
        pnlUscite.add(txtTotAssegniUscite, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 110, -1));

        txtTotBonificoUscite.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotBonificoUscite.setEnabled(false);
        pnlUscite.add(txtTotBonificoUscite, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 110, -1));

        txtTotRibaUscite.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotRibaUscite.setEnabled(false);
        pnlUscite.add(txtTotRibaUscite, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 100, -1));

        txtTotAccreditoUscite.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotAccreditoUscite.setEnabled(false);
        pnlUscite.add(txtTotAccreditoUscite, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 110, -1));

        pnlMovimenti.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Riepilogo Movimenti Contante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        pnlMovimenti.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblMovimenti.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblMovimenti.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(tblMovimenti);

        pnlMovimenti.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 28, 640, 200));

        btnEliminaMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancella.png"))); // NOI18N
        btnEliminaMovimento.setText("Elimina movimento");
        btnEliminaMovimento.setEnabled(false);
        btnEliminaMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaMovimentoActionPerformed(evt);
            }
        });
        pnlMovimenti.add(btnEliminaMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 190, 40));

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

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEntrate, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlCassa, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlUscite, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMovimenti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(645, 645, 645))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pnlCassa, pnlEntrate, pnlMovimenti, pnlUscite});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlEntrate, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlCassa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlUscite, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMovimenti, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnlAnno, pnlCliente});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnlEntrate, pnlUscite});

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    List<Entity> fornitori = FrontController.getAnagrafe(Fornitore.class);
    List<Integer> anni = FrontController.getAnniEsercizio(Fattura.tipo.ALL);
    popolaSelect(fornitori);
    popolaSelect(anni);
    String anno = String.valueOf(cboAnno.getSelectedItem());
    dataIniziale = Date.valueOf(anno + "-01-01");
    dataFinale = Date.valueOf(anno + "-12-31");
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

private void btnVersamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVersamentoActionPerformed
// TODO add your handling code here:
    FrontController.open(new InsMovimentoContante(this, rootPaneCheckingEnabled, MovimentazioneContante.VERSAMENTO));
}//GEN-LAST:event_btnVersamentoActionPerformed

private void btnPrelievoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrelievoActionPerformed
// TODO add your handling code here:
     FrontController.open(new InsMovimentoContante(this, rootPaneCheckingEnabled, MovimentazioneContante.PRELIEVO));
}//GEN-LAST:event_btnPrelievoActionPerformed

private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
// TODO add your handling code here:
    try {
        
        boolean forn_clienteSelezionato = cboCliente.getSelectedIndex() > 0;
        
        double attivoContante = Double.parseDouble(txtAttivoContante.getText());
        double passivoContante = Double.parseDouble(txtPassivoContante.getText());
        double versamenti = Double.parseDouble(txtVersamentiTot.getText());
        double prelievi = Double.parseDouble(txtPrelievi.getText());
        double netto = Double.parseDouble(txtCassaNetto.getText());
        
        RiepilogoCassa cassa = new RiepilogoCassa(attivoContante, passivoContante, versamenti, prelievi, netto);
        
        String[] totaliAttivo = {
            txtTotContanteEntrate.getText(),
            txtTotAssegniEntrate.getText(),
            txtTotBonificoEntrate.getText(),
            txtTotAccreditoEntrate.getText(),
            txtTotRibaEntrate.getText()
            
        };
        
        String[] totaliPassivo = {
            txtTotContanteUscite.getText(),
            txtTotAssegniUscite.getText(),
            txtTotBonificoUscite.getText(),
            txtTotAccreditoUscite.getText(),
            txtTotRibaUscite.getText()
    
        };
        
        if (mnuIntervalloDate.isSelected() && forn_clienteSelezionato) {
            new StampaCassa((Fornitore)cboCliente.getSelectedItem(), dataIniziale, dataFinale, movimentiContante, attivo, passivo, cassa, totaliAttivo, totaliPassivo).printAndOpen();
   
        } else if (mnuIntervalloDate.isSelected() && !forn_clienteSelezionato) {
            new StampaCassa(dataIniziale, dataFinale, movimentiContante, attivo, passivo, cassa, totaliAttivo, totaliPassivo).printAndOpen();
            
        } else if (!mnuIntervalloDate.isSelected() && forn_clienteSelezionato) {
            new StampaCassa((Fornitore)cboCliente.getSelectedItem(), (Integer) cboAnno.getSelectedItem(), movimentiContante, attivo, passivo, cassa, totaliAttivo, totaliPassivo).printAndOpen();
            
        } else if (!mnuIntervalloDate.isSelected() && !forn_clienteSelezionato) {
            new StampaCassa((Integer) cboAnno.getSelectedItem(), movimentiContante, attivo, passivo, cassa, totaliAttivo, totaliPassivo).printAndOpen();
            
        }
        
    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
    
}//GEN-LAST:event_mnuStampaActionPerformed

private void btnEliminaMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaMovimentoActionPerformed
// TODO add your handling code here:    
    
    final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'eliminazione del movimento?", "Conferma eliminazione", JOptionPane.OK_CANCEL_OPTION);
    if (RESPONSE == JOptionPane.OK_OPTION) {
        if (!FrontController.deleteMovimentazioneContante(movimentiContante.get(tblMovimenti.getSelectedRow())))
            JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'eliminazione!", "Errore", JOptionPane.ERROR_MESSAGE);
        else {
            setTable();
            JOptionPane.showMessageDialog(this, "Movimento eliminato con successo!", "Eliminazione avvenuta", JOptionPane.INFORMATION_MESSAGE);
        }
            
    }
       
}//GEN-LAST:event_btnEliminaMovimentoActionPerformed

private boolean checkData(String data) {
    Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
    Matcher match = pattern.matcher(data);
    return match.matches();
}

void setTable() {
       
    setAnniMesi(dataIniziale, dataFinale);
    List<SaldoCassaMensile> cassaAttiva = FrontController.getMovimentazioneCassaMensile(anniMesi, codFornCliente, Fattura.tipo.VEN);
    List<SaldoCassaMensile> cassaPassiva = FrontController.getMovimentazioneCassaMensile(anniMesi, codFornCliente, Fattura.tipo.ACQ);
    List<MovimentazioneContante> movimenti = FrontController.getCashMovements(anniMesi);
    attivo = cassaAttiva;
    passivo = cassaPassiva;
    movimentiContante = movimenti;
    
    //Definizione del netto in cassa
//    double attivoAssegni = 0.00;
    double attivoContante = 0.00;
    double totPrelievi = 0.00;
    double passivoContante = 0.00;
    double totVers = 0.00;
    double nettoCassa = 0.00;
    
    for (SaldoCassaMensile s : cassaAttiva){
//        attivoAssegni += s.getAssegni();
        attivoContante += s.getContanti();
    }
    
    for (SaldoCassaMensile s : cassaPassiva){
        passivoContante += s.getContanti();
    }
    for (MovimentazioneContante m : movimenti){
        if (m instanceof Versamento)
            totVers += m.getImporto();
        else if (m instanceof Prelievo)
            totPrelievi += m.getImporto();
    }
    nettoCassa = attivoContante + totPrelievi - passivoContante - totVers;
    
    //txtAttivoAssegni.setText(String.valueOf(roundTwoDecimals(attivoAssegni)));
    txtAttivoContante.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(attivoContante)));
    txtPrelievi.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totPrelievi)));
    txtPassivoContante.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(passivoContante)));
    txtVersamentiTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totVers)));
    txtCassaNetto.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(nettoCassa)));
    
    Object[] cassaAtt = cassaAttiva.toArray();
    Object[] cassaPass = cassaPassiva.toArray();
    Object[] movim = movimenti.toArray();
    Object[][] arrayAttiva = new Object[cassaAtt.length][SaldoCassaMensile.NUM_CAMPI];
    Object[][] arrayPassiva = new Object[cassaPass.length][SaldoCassaMensile.NUM_CAMPI];
    Object[][] arrayMov = new Object[movim.length][MovimentazioneContante.NUM_CAMPI];
    
    int cont = 0;
    double totContante = 0.00;
    double totAssegni = 0.00;
    double totBonifico = 0.00;
    double totRiba = 0.00;
    double totAccredito = 0.00;
    for (SaldoCassaMensile saldo : cassaAttiva) {
        arrayAttiva[cont++] = saldo.toArray();
        totContante += saldo.getContanti();
        totAssegni += saldo.getAssegni();
        totBonifico += saldo.getBonifico();
        totRiba += saldo.getRiba();
        totAccredito += saldo.getAccredito();
    }
    
    txtTotAssegniEntrate.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totAssegni)));
    txtTotContanteEntrate.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totContante)));
    txtTotBonificoEntrate.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totBonifico)));
    txtTotRibaEntrate.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totRiba)));
    txtTotAccreditoEntrate.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totAccredito)));
    
    txtTotAssegniEntrate.setHorizontalAlignment(JTextField.RIGHT);
    txtTotContanteEntrate.setHorizontalAlignment(JTextField.RIGHT);
    txtTotBonificoEntrate.setHorizontalAlignment(JTextField.RIGHT);
    txtTotRibaEntrate.setHorizontalAlignment(JTextField.RIGHT);
    txtTotAccreditoEntrate.setHorizontalAlignment(JTextField.RIGHT);
        
    cont = 0;
    totContante = 0.00;
    totAssegni = 0.00;
    totBonifico = 0.00;
    totRiba = 0.00;
    totAccredito = 0.00;
    for (SaldoCassaMensile saldo : cassaPassiva) {
        arrayPassiva[cont++] = saldo.toArray();
        totContante += saldo.getContanti();
        totAssegni += saldo.getAssegni();
        totBonifico += saldo.getBonifico();
        totRiba += saldo.getRiba();
        totAccredito += saldo.getAccredito();
    }
    
    txtTotAssegniUscite.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totAssegni)));
    txtTotContanteUscite.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totContante)));
    txtTotBonificoUscite.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totBonifico)));
    txtTotRibaUscite.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totRiba)));
    txtTotAccreditoUscite.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totAccredito)));
    
    txtTotAssegniUscite.setHorizontalAlignment(JTextField.RIGHT);
    txtTotContanteUscite.setHorizontalAlignment(JTextField.RIGHT);
    txtTotBonificoUscite.setHorizontalAlignment(JTextField.RIGHT);
    txtTotRibaUscite.setHorizontalAlignment(JTextField.RIGHT);
    txtTotAccreditoUscite.setHorizontalAlignment(JTextField.RIGHT);
    
    cont = 0;
    for (MovimentazioneContante m : movimenti){
        arrayMov[cont++] = m.toArray();
    }
    
    final String[] COLONNE_MOV = {
        "DATA", "BANCA", "IMPORTO", "TIPO"
    };
    
    final String[] COLONNE = {
        "PERIODO", "CONTANTE", "ASSEGNI", "BONIFICO", "ACCREDITO C/C" , "RIBA"
    };
    
    Class[] types = {String.class, Double.class, Double.class, Double.class, Double.class, Double.class};
    
    TableModel tm = new CassaTableModel(arrayAttiva, COLONNE, types, new boolean[] {
        false, false, false, false, false, false
    });    
       
    tblCassaAttiva.setModel(tm);
    TableRowSorter sorter = new TableRowSorter(tm);
    sorter.setSortable(PERIODO, false);
    tblCassaAttiva.setRowSorter(sorter);
       
    tm = new CassaTableModel(arrayPassiva, COLONNE, types, new boolean[] {
        false, false, false, false, false, false
    });    
    
    tblCassaPassiva.setModel(tm);
    sorter = new TableRowSorter(tm);
    sorter.setSortable(PERIODO, false);
    tblCassaPassiva.setRowSorter(sorter);
    
    boolean[] resizable = {
        false, false, false, false, false, false
    };
    
    types = new Class[] {Object.class, String.class, Double.class, String.class};
    
    tm = new CassaTableModel(arrayMov, COLONNE_MOV, types, new boolean[] {false, true, true, false});
    tblMovimenti.setModel(tm);
//    tblMovimenti.setRowSorter(new TableRowSorter(tm) {
//
//        class DateComparator implements Comparator {
//
//            @Override
//            public int compare(Object o1, Object o2) {
//                String[] data1_str = ((String) o1).split("/");
//                String[] data2_str = ((String) o2).split("/");
//
//                Date data1 = Date.valueOf(data1_str[2] + "-" + data1_str[1] + "-" + data1_str[0]);
//                Date data2 = Date.valueOf(data2_str[2] + "-" + data2_str[1] + "-" + data2_str[0]);
//
//                return data1.compareTo(data2);
//            }
//            
//        }
//        
//        @Override
//        public void sort() {
//            setComparator(DATA, new DateComparator());
//            super.sort();
//        }
//        
//    });
    
    tblMovimenti.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                btnEliminaMovimento.setEnabled(true);
            }
        
    });
    
    boolean[] resizableMov = {
        false, false, false, false
    };
    
    for (int i = 0; i < COLONNE_MOV.length; i++) {
        TableColumn colonnaVers = tblCassaAttiva.getColumnModel().getColumn(i);
        colonnaVers.setResizable(resizableMov[i]);
        if (i == IMPORTO) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.RIGHT);
            colonnaVers.setCellRenderer(renderer);
        }
            
    }
    
//    int[] width = {
//        300, 100, 90, 170
//    };
    
    tblCassaAttiva.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
    tblCassaPassiva.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    //tblContEmesse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    for (int i = 0; i < COLONNE.length; i++) {
        TableColumn colonnaAttivo = tblCassaAttiva.getColumnModel().getColumn(i);
        TableColumn colonnaPassivo = tblCassaPassiva.getColumnModel().getColumn(i);
        colonnaAttivo.setResizable(resizable[i]);
        colonnaPassivo.setResizable(resizable[i]);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        
        if (i != PERIODO)
            renderer.setHorizontalAlignment(JLabel.RIGHT);
        else
            renderer.setBackground(Color.lightGray);
        
        colonnaAttivo.setCellRenderer(renderer);
        colonnaPassivo.setCellRenderer(renderer);
        
        //colonna.setPreferredWidth(width[i]);
    }
          
    tblCassaAttiva.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tblCassaPassiva.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    for (int i = CONTANTE; i <= RIBA; i++) {
        tblCassaAttiva.getColumnModel().getColumn(i).setCellRenderer(new DoubleFormatter());
        tblCassaPassiva.getColumnModel().getColumn(i).setCellRenderer(new DoubleFormatter());
    }
    
    tblMovimenti.getColumnModel().getColumn(IMPORTO).setCellRenderer(new DoubleFormatter());
    btnEliminaMovimento.setEnabled(false);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminaMovimento;
    private javax.swing.JButton btnPrelievo;
    private javax.swing.JButton btnVersamento;
    private javax.swing.JComboBox cboAnno;
    private javax.swing.JComboBox cboCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBoxMenuItem mnuIntervalloDate;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JPanel pnlAnno;
    private javax.swing.JPanel pnlCassa;
    private javax.swing.JPanel pnlCliente;
    private javax.swing.JPanel pnlEntrate;
    private javax.swing.JPanel pnlMovimenti;
    private javax.swing.JPanel pnlUscite;
    private javax.swing.JTable tblCassaAttiva;
    private javax.swing.JTable tblCassaPassiva;
    private javax.swing.JTable tblMovimenti;
    private javax.swing.JTextField txtAttivoContante;
    private javax.swing.JTextField txtCassaNetto;
    private javax.swing.JTextField txtPassivoContante;
    private javax.swing.JTextField txtPrelievi;
    private javax.swing.JTextField txtTotAccreditoEntrate;
    private javax.swing.JTextField txtTotAccreditoUscite;
    private javax.swing.JTextField txtTotAssegniEntrate;
    private javax.swing.JTextField txtTotAssegniUscite;
    private javax.swing.JTextField txtTotBonificoEntrate;
    private javax.swing.JTextField txtTotBonificoUscite;
    private javax.swing.JTextField txtTotContanteEntrate;
    private javax.swing.JTextField txtTotContanteUscite;
    private javax.swing.JTextField txtTotRibaEntrate;
    private javax.swing.JTextField txtTotRibaUscite;
    private javax.swing.JTextField txtVersamentiTot;
    // End of variables declaration//GEN-END:variables
    
    Date dataIniziale;
    Date dataFinale;
    private String[] anniMesi;
    private int codFornCliente = -1;
    private List<MovimentazioneContante> movimentiContante; 
    private List<SaldoCassaMensile> attivo;
    private List<SaldoCassaMensile> passivo;
    
    private static final int PERIODO = 0;
    private static final int CONTANTE = 1;
    private static final int ASSEGNI = 2;
    private static final int BONIFICO = 3;
    private static final int ACCREDITO = 4;
    private static final int RIBA = 5;
    
    private static final int DATA = 0;
    private static final int BANCA = 1;
    private static final int IMPORTO = 2;
    private static final int TIPO = 3;
}