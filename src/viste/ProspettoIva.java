/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InsFatturaAcquisto.java
 *
 * Created on 6-giu-2012, 20.16.59
 */
package viste;

import contabilizzazione.SaldoIvaMensile;
import controllo.FrontController;
import java.awt.Color;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import libs.DoubleFormatter;

/**
 *
 * @author Andle
 */
public class ProspettoIva extends javax.swing.JDialog {

    /** Creates new form ProspettoIva */
    public ProspettoIva(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setTitle("Prospetto calcolo IVA mensile");
        this.parent = (Contabilita) parent;
        setTable();
    }

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
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSaldiIvaMensili = new javax.swing.JTable();
        btnOk = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtIvaCredito = new javax.swing.JTextField();
        txtTotIvaDebito = new javax.swing.JTextField();
        txtTotSaldo = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuProspetto = new javax.swing.JMenu();
        mnuStampa = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tblSaldiIvaMensili.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSaldiIvaMensili.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblSaldiIvaMensili);

        btnOk.setBackground(new java.awt.Color(255, 255, 255));
        btnOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.png"))); // NOI18N
        btnOk.setText("Ok");
        btnOk.setToolTipText("Chiude la finestra attiva");
        btnOk.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("TOTALI");

        txtIvaCredito.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtIvaCredito.setEnabled(false);

        txtTotIvaDebito.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotIvaDebito.setEnabled(false);

        txtTotSaldo.setFont(new java.awt.Font("Tahoma", 1, 11));
        txtTotSaldo.setEnabled(false);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                    .addComponent(btnOk)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIvaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotIvaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtTotSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotIvaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIvaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
}//GEN-LAST:event_formWindowOpened

private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btnOkActionPerformed

private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
// TODO add your handling code here:
    parent.stampaProspettoIva(prospetto, new String[] {
        txtIvaCredito.getText(),
        txtTotIvaDebito.getText(),
        txtTotSaldo.getText()
    });
}//GEN-LAST:event_mnuStampaActionPerformed

private void setTable() {
    
    List<SaldoIvaMensile> contabilita = FrontController.getSaldoIvaMensile(this.parent.anniMesi);
    prospetto = contabilita;
    
    Object[] contab = contabilita.toArray();
    Object[][] arrayContabilita = new Object[contab.length][SaldoIvaMensile.NUM_CAMPI];
    int cont = 0;
    double ivaCredito = 0.00;
    double ivaDebito = 0.00;
    double totSaldo = 0.00;
    
    for (SaldoIvaMensile saldo : contabilita) {
        SaldoIvaMensile s = ((SaldoIvaMensile) saldo);
        arrayContabilita[cont++] = s.toArray();
        ivaCredito += s.getIvaCredito();
        ivaDebito += s.getIvaDebito();
        totSaldo += s.getSaldoIva();
    }

    txtTotIvaDebito.setHorizontalAlignment(JTextField.RIGHT);
    txtIvaCredito.setHorizontalAlignment(JTextField.RIGHT);
    txtTotSaldo.setHorizontalAlignment(JTextField.RIGHT);
    txtTotIvaDebito.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(ivaDebito)));
    txtIvaCredito.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(ivaCredito)));
    txtTotSaldo.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totSaldo)));
    
    final String[] COLONNE = {
        "PERIODO", "IVA CREDITO", "IVA DEBITO", "SALDO IVA"
    };
    
    Class[] types = {String.class, Double.class, Double.class, Double.class};
    
    TableModel tm = new ContabilitaTableModel(arrayContabilita, COLONNE, types, new boolean[] {
        false, false, false, false
    });    
   
    tblSaldiIvaMensili.setModel(tm);
    TableRowSorter sorter = new TableRowSorter(tm);
    sorter.setSortable(PERIODO, false);
    tblSaldiIvaMensili.setRowSorter(sorter);
    
    boolean[] resizable = {
        false, false, false, false
    };
    
//    int[] width = {
//        300, 100, 90, 170
//    };
    
    tblSaldiIvaMensili.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
        
    //Imposta la larghezza dei singoli campi
    //tblContEmesse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    for (int i = 0; i < COLONNE.length; i++) {
        TableColumn colonna = tblSaldiIvaMensili.getColumnModel().getColumn(i);
        colonna.setResizable(resizable[i]);
        
        
        if (i == CREDITO || i == DEBITO || i == SALDO)
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
    
    tblSaldiIvaMensili.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
}
    private Contabilita parent;
    
    private static final int PERIODO = 0;
    private static final int CREDITO = 1;
    private static final int DEBITO = 2;
    private static final int SALDO = 3;
    
    private List<SaldoIvaMensile> prospetto;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JTable tblSaldiIvaMensili;
    private javax.swing.JTextField txtIvaCredito;
    private javax.swing.JTextField txtTotIvaDebito;
    private javax.swing.JTextField txtTotSaldo;
    // End of variables declaration//GEN-END:variables
}