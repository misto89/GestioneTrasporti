/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viste;

import contabilizzazione.BilancioMensile;
import controllo.FrontController;
import entita.Entity;
import entita.Fattura;
import entita.Fornitore;
import java.awt.Color;
import java.sql.Date;
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
 * @author Andrea
 */
public class ProspettoBilancio extends javax.swing.JDialog {

    private class BilancioTableModel extends DefaultTableModel {

        private final boolean[] CAN_EDIT;
        private final Class[] types;

        public BilancioTableModel(Object[][] righe, String[] colonne, Class[] tps, boolean[] edit) {
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

    /**
     * Creates new form ProspettoBilancio
     */
    public ProspettoBilancio(java.awt.Frame parent, boolean modal, String type) {
        super(parent, modal);
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlAnno);
        color.changeColor(pnlCliente);
        setTitle("Prospetto bilancio fatture " + type);
        this.type = type;
        this.parent = (Contabilita) parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAnno = new javax.swing.JPanel();
        cboAnno = new javax.swing.JComboBox();
        pnlCliente = new javax.swing.JPanel();
        cboCliente = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBilancio = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTotale = new javax.swing.JTextField();
        txtTotLiquidato = new javax.swing.JTextField();
        txtTotNonLiquidato = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();
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
                .addContainerGap()
                .addComponent(cboAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

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
                .addComponent(cboCliente, 0, 617, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlClienteLayout.setVerticalGroup(
            pnlClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblBilancio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblBilancio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tblBilancio);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("TOTALI");

        txtTotale.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotale.setEnabled(false);

        txtTotLiquidato.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotLiquidato.setEnabled(false);

        txtTotNonLiquidato.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtTotNonLiquidato.setEnabled(false);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnOk))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotLiquidato, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotNonLiquidato, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlAnno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pnlCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTotNonLiquidato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotLiquidato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlAnno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(319, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboAnnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAnnoActionPerformed
        // TODO add your handling code here:
        String anno = String.valueOf(cboAnno.getSelectedItem());
        dataIniziale = Date.valueOf(anno + "-01-01");
        dataFinale = Date.valueOf(anno + "-12-31");
        setTable();
    }//GEN-LAST:event_cboAnnoActionPerformed

    private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
        // TODO add your handling code here:
        try {
            codFornCliente = ((Fornitore) cboCliente.getSelectedItem()).getCod();

        } catch (ClassCastException e) {
            codFornCliente = -1;
        }

        setTable();

    }//GEN-LAST:event_cboClienteActionPerformed

    /*
     * Prende come parametri d'ingresso due sql.Date e compila un array di String contenente tutti i mesi nel periodo passato come parametro.
     * I mesi da inserire nell'array sono nella forma "anno-mese";
     */
    private void setAnniMesi(Date iniziale, Date finale) {
        String dataI = iniziale.toString();
        String dataF = finale.toString();
        int annoI = Integer.parseInt(dataI.substring(0, 4));
        int meseI = Integer.parseInt(dataI.substring(5, 7));
        int annoF = Integer.parseInt(dataF.substring(0, 4));
        int meseF = Integer.parseInt(dataF.substring(5, 7));

        //Valutazione del periodo inserito, a cavallo fra due o più anni, o nello stesso anno.
        if (annoI == annoF) {
            int diff = meseF - meseI;  //es. 12-1
            anniMesi = new String[diff + 1];
            if (String.valueOf(meseI).length() == 1) //Se l'int del mese iniziale è nella forma 1, 2, 3.... 9
            {
                anniMesi[0] = annoI + "-0" + String.valueOf(meseI);
            } else {
                anniMesi[0] = annoI + "-" + String.valueOf(meseI);
            }

            int j = 1;
            for (int i = meseI + 1; i < meseF; i++) {
                String mese = String.valueOf(i);
                if (mese.length() == 1) {
                    mese = "0" + i;
                }
                anniMesi[j] = annoI + "-" + mese;
                j++;
            }
            if (diff > 0) {
                if (String.valueOf(meseF).length() == 1) {
                    anniMesi[diff] = annoI + "-0" + meseF;
                } else {
                    anniMesi[diff] = annoI + "-" + meseF;
                }
            }
        } else { //se le date sono a cavallo fra 2 o più anni
            int diffAnni = annoF - annoI;
            int mesiTot = (12 - meseI + 1) + (12 * (diffAnni - 1)) + (meseF);
            anniMesi = new String[mesiTot];

            //inserimento nell'array dell'anno e mese iniziale
            if (String.valueOf(meseI).length() == 1) {
                anniMesi[0] = annoI + "-0" + meseI;
            } else {
                anniMesi[0] = annoI + "-" + meseI;
            }

            int j = 1;
            int anno = annoI;
            //ciclo che inserisce nell'array di anniMesi tutti i valori dei mesi tranne quelli dell'ultimo anno del periodo di riferimento
            for (int i = 0; i < diffAnni; i++) {
                for (int h = meseI + 1; h <= 12; h++) {
                    if (String.valueOf(h).length() == 1) {
                        anniMesi[j] = anno + "-0" + h;
                    } else {
                        anniMesi[j] = anno + "-" + h;
                    }
                    j++;
                }
                //porto l'anno pari all'anno successivo in caso di ulteriore iterata del ciclo (diffAnni > 1)
                anno = anno + 1;
                //riporto il mese iniziale a 1 in caso di ulteriore iterata
                meseI = 0;
            }
            //ciclo che inserisce nell'array anniMesi le occorrenze dell'ultimo anno del periodo inserito
            for (int i = 1; i < meseF; i++) {
                if (String.valueOf(i).length() == 1) {
                    anniMesi[j] = annoF + "-0" + i;
                } else {
                    anniMesi[j] = annoF + "-" + i;
                }
                j++;
            }
            if (String.valueOf(meseF).length() == 1) {
                anniMesi[mesiTot - 1] = annoF + "-0" + meseF;
            } else {
                anniMesi[mesiTot - 1] = annoF + "-" + meseF;
            }
        }
    }

    private void popolaSelect(List items) {
        for (Object item : items) {
            if (item instanceof Integer) {
                cboAnno.addItem((Integer) item);
            } else {
                cboCliente.addItem((Fornitore) item);
            }
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        List<Entity> fornitori = FrontController.getAnagrafe(Fornitore.class);
        List<Integer> anni = FrontController.getAnniEsercizio(Fattura.tipo.ALL);
        popolaSelect(fornitori);
        popolaSelect(anni);
        String anno = String.valueOf(cboAnno.getSelectedItem());
        dataIniziale = Date.valueOf(anno + "-01-01");
        dataFinale = Date.valueOf(anno + "-12-31");
        setTable();
    }//GEN-LAST:event_formWindowOpened

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
        parent.stampaProspettoBilancio(prospettoBilancio, new String[] {
            txtTotale.getText(),
            txtTotLiquidato.getText(),
            txtTotNonLiquidato.getText()
    }, type, (Integer) cboAnno.getSelectedItem(), codFornCliente == -1 ? null : (Fornitore) cboCliente.getSelectedItem());
    }//GEN-LAST:event_mnuStampaActionPerformed

    private void setTable() {

       setAnniMesi(dataIniziale, dataFinale);
       List<BilancioMensile> bilancioList = type.equals("emesse") ? FrontController.getBilancioEmesse(anniMesi, codFornCliente) : FrontController.getBilancioAcquisto(anniMesi, codFornCliente);
//    listaSaldi = contabilita;

       prospettoBilancio = bilancioList;
       
        Object[] bilancio = bilancioList.toArray();
        Object[][] arrayBilancio = new Object[bilancio.length][BilancioMensile.NUM_CAMPI];
        int cont = 0;
        double tot = 0.00;
        double totLiquidate = 0.00;
        double totNonLiquidate = 0.00;
//        double saldo = 0.00;

        for (BilancioMensile bil : bilancioList) {
            arrayBilancio[cont++] = bil.toArray();
            tot += bil.getTotale();
            totLiquidate += bil.getTotaleLiquidato();
            totNonLiquidate += bil.getTotaleDaLiquidare();
//            saldo += bil.getSaldo();
        }

        txtTotale.setHorizontalAlignment(JTextField.RIGHT);
        txtTotLiquidato.setHorizontalAlignment(JTextField.RIGHT);
        txtTotNonLiquidato.setHorizontalAlignment(JTextField.RIGHT);
//        txtSaldo.setHorizontalAlignment(JTextField.RIGHT);
        
        txtTotale.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(tot)));
        txtTotLiquidato.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totLiquidate)));
        txtTotNonLiquidato.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(totNonLiquidate)));
//        txtSaldo.setText(DoubleFormatter.doubleToString(DoubleFormatter.roundTwoDecimals(saldo)));

        String colLiquidato = type.equals("emesse") ? "TOTALE INCASSATO" : "TOTALE PAGATO";
        String colDaLiquidare = type.equals("emesse") ? "TOTALE DA INCASSARE" : "TOTALE DA PAGARE";

        final String[] COLONNE = {
            "PERIODO", "TOTALE", colLiquidato, colDaLiquidare,
        };

        Class[] types = {String.class, Double.class, Double.class, Double.class};

        TableModel tm = new BilancioTableModel(arrayBilancio, COLONNE, types, new boolean[]{
                    false, false, false, false
                });

        tblBilancio.setModel(tm);
        TableRowSorter sorter = new TableRowSorter(tm);
        sorter.setSortable(PERIODO, false);
        tblBilancio.setRowSorter(sorter);

        boolean[] resizable = {
            false, false, false, false
        };

//    int[] width = {
//        300, 100, 90, 170
//    };

        tblBilancio.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne

        //Imposta la larghezza dei singoli campi
        //tblContEmesse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < COLONNE.length; i++) {
            TableColumn colonna = tblBilancio.getColumnModel().getColumn(i);
            colonna.setResizable(resizable[i]);

            if (i == TOTALE || i == TOT_LIQUIDATE || i == TOT_DA_LIQUIDARE) {
                colonna.setCellRenderer(new DoubleFormatter());
            } else {
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

        tblBilancio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox cboAnno;
    private javax.swing.JComboBox cboCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JPanel pnlAnno;
    private javax.swing.JPanel pnlCliente;
    private javax.swing.JTable tblBilancio;
    private javax.swing.JTextField txtTotLiquidato;
    private javax.swing.JTextField txtTotNonLiquidato;
    private javax.swing.JTextField txtTotale;
    // End of variables declaration//GEN-END:variables
    private int codFornCliente = -1;
    Date dataIniziale;
    Date dataFinale;
    public String[] anniMesi;
    private static final int PERIODO = 0;
    private static final int TOTALE = 1;
    private static final int TOT_LIQUIDATE = 2;
    private static final int TOT_DA_LIQUIDARE = 3;
//    private static final int SALDO = 4;
    private String type = null;
    
    private Contabilita parent;
    private List<BilancioMensile> prospettoBilancio;
}
