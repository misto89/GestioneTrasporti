/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viste;

import controllo.FrontController;
import entita.DescrizioniNotaCredito;
import entita.Entity;
import entita.Fornitore;
import entita.NotaCredito;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import libs.DoubleFormatter;
import libs.Utility;

/**
 *
 * @author Michele
 */
public class InsNotaCredito extends javax.swing.JFrame {

    private class DescrizioniTableModel extends DefaultTableModel {
        
        private final boolean[] CAN_EDIT;
        private final Class[] types;
        
        public DescrizioniTableModel(Object[][] righe, String[] colonne, Class[] tps, boolean[] edit) {
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
            
            DescrizioniNotaCredito descrizione = descrizioni.get(row);
            switch (column) {
                case DESCRIZIONE:
                    descrizione.setDescrizione((String)aValue);
                    break;
                    
                case IMPORTO: 
                        double oldImporto = descrizione.getImporto();
                        double newImporto =  (Double) aValue;
                        double oldImportoTotal = Double.parseDouble(txtImportoTot.getText());
                                      
                        double oldIva = descrizione.getIva();        
                        double newIva = (newImporto * descrizione.getPercIva()) / 100;
                        double oldIvaTotal = Double.parseDouble(txtIvaTot.getText());
                        
                        double oldTotalSingleDesc = (Double) tblDescrizioni.getValueAt(row, TOTALE);                                                
                        double newTotalSingleDesc = newImporto + newIva;   
                        double oldTotal = Double.parseDouble(txtTotale.getText());
                        
                        descrizione.setImporto(newImporto);                        
                        descrizione.setIva(newIva);
                        
                        tblDescrizioni.setValueAt(newIva, row, IVA);
                        tblDescrizioni.setValueAt(newTotalSingleDesc, row, TOTALE);
                        
                        txtImportoTot.setText(String.format("%.2f", oldImportoTotal - oldImporto + newImporto));
                        txtIvaTot.setText(String.format("%.2f", oldIvaTotal - oldIva + newIva));
                        txtTotale.setText(String.format("%.2f", oldTotal - oldTotalSingleDesc + newTotalSingleDesc));
                        
                    break;
                    
                case PERCIVA: 
                        double importo = descrizione.getImporto();                   
                    
                        int newPerc = (Integer) aValue;
     
                        oldIva = descrizione.getIva();      
                        newIva = (importo * newPerc) / 100;
                        oldIvaTotal = Double.parseDouble(txtIvaTot.getText());
                        
                        oldTotalSingleDesc = (Double) tblDescrizioni.getValueAt(row, TOTALE);                                                
                        newTotalSingleDesc = importo + newIva;   
                        oldTotal = Double.parseDouble(txtTotale.getText());
                        
                        descrizione.setPercIva(newPerc);                   
                        descrizione.setIva(newIva);
                        
                        tblDescrizioni.setValueAt(newIva, row, IVA);
                        tblDescrizioni.setValueAt(newTotalSingleDesc, row, TOTALE);

                        txtIvaTot.setText(String.format("%.2f", oldIvaTotal - oldIva + newIva));
                        txtTotale.setText(String.format("%.2f", oldTotal - oldTotalSingleDesc + newTotalSingleDesc));
                        
                    break;
//                case IVA: break;
//                case TOTALE: break;
                        
            }
        }
              
    }
    
    /**
     * Creates new form RegistroPrimaNota
     */
    public InsNotaCredito() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuova = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtGiorno = new javax.swing.JTextField();
        cboCliente = new javax.swing.JComboBox();
        txtNote = new javax.swing.JTextField();
        txtAnno = new javax.swing.JTextField();
        cboMetPag = new javax.swing.JComboBox();
        btnElimina = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDescrizioni = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cboGiorni = new javax.swing.JComboBox();
        txtMese = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtImportoTot = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtIvaTot = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtTotale = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtGiornoScadenza = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtMeseScadenza = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtAnnoScadenza = new javax.swing.JTextField();
        btnMemorizza = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inserimento nota credito");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));

        btnNuova.setBackground(new java.awt.Color(255, 255, 255));
        btnNuova.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modifica.png"))); // NOI18N
        btnNuova.setText("Nuova riga");
        btnNuova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuovaActionPerformed(evt);
            }
        });

        jLabel14.setText("Giorni");

        txtGiorno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGiorno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiornoFocusLost(evt);
            }
        });

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleziona un cliente" }));
        cboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClienteActionPerformed(evt);
            }
        });

        txtAnno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAnno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAnnoFocusLost(evt);
            }
        });

        btnElimina.setBackground(new java.awt.Color(255, 255, 255));
        btnElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancella.png"))); // NOI18N
        btnElimina.setText("Elimina riga");
        btnElimina.setEnabled(false);
        btnElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaActionPerformed(evt);
            }
        });

        jLabel12.setText("aaaa");

        jLabel10.setText("gg");

        jLabel13.setText("Pagamento");

        tblDescrizioni.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblDescrizioni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDescrizioniMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDescrizioni);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Data");

        jLabel18.setText("Note");

        cboGiorni.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85", "90", "95", "100", "105", "110", "115", "120" }));
        cboGiorni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGiorniActionPerformed(evt);
            }
        });

        txtMese.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMese.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMeseFocusLost(evt);
            }
        });

        jLabel11.setText("mm");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Totali", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Importo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        txtImportoTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImportoTot.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtImportoTot, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtImportoTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Iva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        txtIvaTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIvaTot.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIvaTot, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIvaTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Totale", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        txtTotale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotale.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel19.setText("Scadenza");

        jLabel20.setText("gg");

        txtGiornoScadenza.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel21.setText("mm");

        txtMeseScadenza.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel22.setText("aaaa");

        txtAnnoScadenza.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMese, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAnno, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNote)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(cboMetPag, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cboGiorni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(42, 42, 42)
                                                .addComponent(jLabel19)
                                                .addGap(20, 20, 20)
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtGiornoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtMeseScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtAnnoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addGap(0, 146, Short.MAX_VALUE))
                            .addComponent(cboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuova, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiorno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtMese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNuova)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnElimina))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(txtGiornoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(txtMeseScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)
                        .addComponent(txtAnnoScadenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(cboGiorni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(cboMetPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnMemorizza.setBackground(new java.awt.Color(255, 255, 255));
        btnMemorizza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnMemorizza.setText("Memorizza");
        btnMemorizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemorizzaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMemorizza, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btnMemorizza, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
        // TODO add your handling code here:        
        if (cboCliente.getSelectedIndex() > 0) {
            cliente = (Fornitore) cboCliente.getSelectedItem();
        }                  
    }//GEN-LAST:event_cboClienteActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        ColorManager color = new ColorManager();
        color.changeColor(jPanel1);
        color.changeColor(jPanel2);
        color.changeColor(jPanel3);
        color.changeColor(jPanel4);
        color.changeColor(jPanel5);
        
        descrizioni = new LinkedList<DescrizioniNotaCredito>();
        
        List<Entity> clienti = FrontController.getAnagrafe(Fornitore.class);
        popolaSelect(clienti);
        cboCliente.setSelectedIndex(0);
        String[] metPagam = FrontController.getMetodiPagamento();
        for (int i = 0; i < metPagam.length-1; i++)
            cboMetPag.addItem((String) metPagam[i]);

        cboMetPag.setSelectedIndex(1);

        txtNote.setText(null);
        
        txtGiorno.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
        txtMese.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
        txtAnno.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
        
        txtImportoTot.setDocument(new JTextFieldFormatDouble());
        txtIvaTot.setDocument(new JTextFieldFormatDouble());
        txtTotale.setDocument(new JTextFieldFormatDouble());
        
        txtImportoTot.setText(String.valueOf(0.0));
        txtIvaTot.setText(String.valueOf(0));
        txtTotale.setText(String.valueOf(0.0));
        
        String[] todayDate = Utility.todayDate();
        
        txtAnno.setText(todayDate[0]);
        txtMese.setText(todayDate[1]);
        txtGiorno.setText(todayDate[2]);
        
        cboGiorni.setSelectedIndex(0);
        
        tm = new DescrizioniTableModel(
                null, 
                new String[] {"DESCRIZIONE", "IMPORTO", "% IVA", "IVA", "TOTALE"}, 
                new Class[] {String.class, Double.class, Integer.class, Double.class, Double.class}, 
                new boolean[] {true, true, true, false, false}
        );
        
        tblDescrizioni.setModel(tm);
        int[] widths = {100, 10, 10, 10, 10};
        boolean[] resizable = {true, true, true, true, true};
        
        for (int i = DESCRIZIONE; i <= TOTALE; i++) {
            tblDescrizioni.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);   
            tblDescrizioni.getColumnModel().getColumn(i).setResizable(resizable[i]);   
            if (i == IMPORTO || i == IVA || i == TOTALE)
                tblDescrizioni.getColumnModel().getColumn(i).setCellRenderer(new DoubleFormatter());
        }
        
        tblDescrizioni.getTableHeader().setReorderingAllowed(false);
        tblDescrizioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnNuovaActionPerformed(null);
    }//GEN-LAST:event_formWindowOpened

    private void btnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaActionPerformed
        // TODO add your handling code here:
        int numRiga = tblDescrizioni.getSelectedRow();
        tm = (DescrizioniTableModel) tblDescrizioni.getModel();
        tm.removeRow(numRiga); 
        DescrizioniNotaCredito descrizione = descrizioni.remove(numRiga);
        double importo = Double.parseDouble(txtImportoTot.getText()) - descrizione.getImporto();
        double iva = Double.parseDouble(txtIvaTot.getText()) - descrizione.getIva();
        txtImportoTot.setText(String.format("%.2f", importo));
        txtIvaTot.setText(String.format("%.2f", iva));
        txtTotale.setText(String.format("%.2f", importo + iva));
        btnElimina.setEnabled(false); 
    }//GEN-LAST:event_btnEliminaActionPerformed

    private void btnNuovaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuovaActionPerformed
        // TODO add your handling code here:
        tm = (DescrizioniTableModel) tblDescrizioni.getModel();
        tm.insertRow(tm.getRowCount(), newRow);
        descrizioni.add(new DescrizioniNotaCredito((String)newRow[0], (Double)newRow[1], (Integer)newRow[2], (Double)newRow[3]));
    }//GEN-LAST:event_btnNuovaActionPerformed

    private void tblDescrizioniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDescrizioniMouseClicked
        // TODO add your handling code here:
        if (tblDescrizioni.getSelectedRow() != -1)
            btnElimina.setEnabled(true);
    }//GEN-LAST:event_tblDescrizioniMouseClicked

    private void btnMemorizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemorizzaActionPerformed
        // TODO add your handling code here:
        if (cboCliente.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(this, "Selezionare un cliente", "Attenzione", JOptionPane.WARNING_MESSAGE);            
            return;
        }
        Date data = null;
        try{        
            data = Utility.dateValueOf(txtAnno.getText(), txtMese.getText(),txtGiorno.getText(), "data");            
        } catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Formato data errato", JOptionPane.ERROR_MESSAGE);            
            return;
        }
        
        if (descrizioni.size() == 0) {
            JOptionPane.showMessageDialog(this, "Inserire almeno una descrizione", "Descrizione obbligatoria", JOptionPane.ERROR_MESSAGE);            
            return;
        }
        
        int numero = FrontController.getNumber(NotaCredito.class, data);
        
        String metodoPagamento = "Contante-0";        
        if (cboMetPag.getSelectedIndex() > 0)
            metodoPagamento = String.valueOf(cboMetPag.getSelectedItem()) + "-" + String.valueOf(cboGiorni.getSelectedItem());
        
        double imponibile = - Double.parseDouble(txtImportoTot.getText());
        double iva = - Double.parseDouble(txtIvaTot.getText());
        double totale = - Double.parseDouble(txtTotale.getText());
        String note = txtNote.getText();
        
        Date dataScadenza = null;
        try{        
            dataScadenza = Utility.dateValueOf(txtAnnoScadenza.getText(), txtMeseScadenza.getText(),txtGiornoScadenza.getText(), "data scadenza");            
        } catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Formato data errato", JOptionPane.ERROR_MESSAGE);            
            return;
        }
        
        NotaCredito notaCredito = new NotaCredito(numero, data, cliente, metodoPagamento, imponibile, iva, totale, descrizioni, false, note, null, dataScadenza);
        if (FrontController.insertNotaCredito(notaCredito)) {
            JOptionPane.showMessageDialog(this, "Nota credito inserita correttamente", "", JOptionPane.INFORMATION_MESSAGE);
            formWindowOpened(null);
        } else {
            JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'inserimento", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnMemorizzaActionPerformed

    private void txtGiornoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiornoFocusLost
        // TODO add your handling code here:
        setDataScadenza();
    }//GEN-LAST:event_txtGiornoFocusLost

    private void txtMeseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMeseFocusLost
        // TODO add your handling code here:
        setDataScadenza();
    }//GEN-LAST:event_txtMeseFocusLost

    private void txtAnnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAnnoFocusLost
        // TODO add your handling code here:
        setDataScadenza();        
    }//GEN-LAST:event_txtAnnoFocusLost

    private void cboGiorniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGiorniActionPerformed
        // TODO add your handling code here:        
        setDataScadenza();
    }//GEN-LAST:event_cboGiorniActionPerformed

    protected void popolaSelect(List items) {
        for (Object item : items)
            cboCliente.addItem((Fornitore) item);

    }
   
    private void setDataScadenza() {
        
        String giorno = txtGiorno.getText();
        String mese = txtMese.getText();
        String anno = txtAnno.getText();
//JOptionPane.showMessageDialog(null, giorno + "-" + mese + "-" + anno + ":" + Integer.parseInt((String) cboGiorni.getSelectedItem()));
        try {
            String[] nuovaScadenza = Utility.setDataScadenza(giorno, mese, anno, Integer.parseInt((String) cboGiorni.getSelectedItem()));
            txtGiornoScadenza.setText(nuovaScadenza[2]);
            txtMeseScadenza.setText(nuovaScadenza[1]);
            txtAnnoScadenza.setText(nuovaScadenza[0]);
        } catch (IllegalArgumentException e) {}
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnElimina;
    private javax.swing.JButton btnMemorizza;
    private javax.swing.JButton btnNuova;
    private javax.swing.JComboBox cboCliente;
    private javax.swing.JComboBox cboGiorni;
    private javax.swing.JComboBox cboMetPag;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDescrizioni;
    private javax.swing.JTextField txtAnno;
    private javax.swing.JTextField txtAnnoScadenza;
    private javax.swing.JTextField txtGiorno;
    private javax.swing.JTextField txtGiornoScadenza;
    private javax.swing.JTextField txtImportoTot;
    private javax.swing.JTextField txtIvaTot;
    private javax.swing.JTextField txtMese;
    private javax.swing.JTextField txtMeseScadenza;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtTotale;
    // End of variables declaration//GEN-END:variables

    private static final int MAX_LENGTH_GIORNO = 2;
    private static final int MAX_LENGTH_MESE = 2;
    private static final int MAX_LENGTH_ANNO = 4;
    private static final int MAX_LENGTH_PERCIVA = 2;    
    private static final Integer PERC_IVA = 21;
    
    private final int DESCRIZIONE = 0;
    private final int IMPORTO = 1;
    private final int PERCIVA = 2;
    private final int IVA = 3;
    private final int TOTALE = 4;
    
    private Fornitore cliente = null;
    private Object[] newRow = {null, 0.0, PERC_IVA, 0.0, 0.0};
    private DescrizioniTableModel tm = null; 
    private List<DescrizioniNotaCredito> descrizioni = null;
}
