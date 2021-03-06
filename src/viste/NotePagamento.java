/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NotePagamento.java
 *
 * Created on 21-giu-2012, 22.15.11
 */
package viste;

import controllo.FrontController;
import entita.Fattura;
import entita.Movimento;
import entita.NotaCredito;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import libs.DoubleFormatter;
import libs.Utility;

/**
 *
 * @author Michele
 */
public class NotePagamento extends javax.swing.JDialog {
    private static final String REG_EMESSE = RegistroFattureEmesse.class.getSimpleName();
    private static final String REG_ACQUISTO = RegistroFattureAcquisto.class.getSimpleName();
    private static final String INS_FATT_ACQUISTO = InsFatturaAcquisto.class.getSimpleName();
    private static final String SPEDIZIONI = Spedizioni.class.getSimpleName();
    private static final String PAGAMENTO_FATT = PagamentoFattura.class.getSimpleName();
    
    /** Creates new form NotePagamento */
    public NotePagamento(java.awt.Window parent, boolean modal, Fattura fattura) {       
        super((Frame)parent, modal);
        this.fattura = fattura;
        this.metodiPagamento = FrontController.getMetodiPagamento();
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnl);

        txtMetodi = new javax.swing.JTextField[] {
            null, txtContante, txtBonifico, txtAccredito, txtAssegno, txtRiba
        };
        
        this.parent = parent;
        setTitle(getTitle() + ": totale fattura " + fattura.getTotale());
        
        if (fattura instanceof NotaCredito) {
            for (int i = 1; i < txtMetodi.length; i++)
                txtMetodi[i].setEnabled(false);
            
        }
    }
   
    //Costruttore per la modifica live dei metodi di pagamento dalla dialog insFatturaAcquisto
    public NotePagamento(javax.swing.JDialog parent, boolean modal, Fattura fattura) {
        super(parent, modal);
        this.fattura = fattura;
        this.metodiPagamento = FrontController.getMetodiPagamento();
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnl);
        
        txtMetodi = new javax.swing.JTextField[] {
            null, txtContante, txtBonifico, txtAccredito, txtAssegno, txtRiba
        };
        
        this.parent = parent;
        setTitle(getTitle() + ": totale fattura " + fattura.getTotale());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl = new javax.swing.JPanel();
        lblContante = new javax.swing.JLabel();
        lblBonifico = new javax.swing.JLabel();
        lblAssegno = new javax.swing.JLabel();
        lblRiba = new javax.swing.JLabel();
        txtContante = new javax.swing.JTextField();
        txtBonifico = new javax.swing.JTextField();
        txtAssegno = new javax.swing.JTextField();
        txtRiba = new javax.swing.JTextField();
        lblBonifico1 = new javax.swing.JLabel();
        txtAccredito = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnConferma = new javax.swing.JButton();
        btnAnnulla = new javax.swing.JButton();
        lblRiba1 = new javax.swing.JLabel();
        txtTot = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtAnnoPagamento = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtGiornoPagamento = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMesePagamento = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Modalità di pagamento");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnl.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        lblContante.setText("Contante");

        lblBonifico.setText("Bonifico Bancario");

        lblAssegno.setText("Assegno Bancario");

        lblRiba.setText("RIBA");

        txtContante.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtContante.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtContanteFocusLost(evt);
            }
        });

        txtBonifico.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBonifico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBonificoFocusLost(evt);
            }
        });

        txtAssegno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAssegno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAssegnoFocusLost(evt);
            }
        });

        txtRiba.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRiba.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRibaFocusLost(evt);
            }
        });

        lblBonifico1.setText("Accredito c/c");

        txtAccredito.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAccredito.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAccreditoFocusLost(evt);
            }
        });

        jLabel1.setText("€");

        jLabel2.setText("€");

        jLabel3.setText("€");

        jLabel4.setText("€");

        jLabel5.setText("€");

        javax.swing.GroupLayout pnlLayout = new javax.swing.GroupLayout(pnl);
        pnl.setLayout(pnlLayout);
        pnlLayout.setHorizontalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContante)
                    .addComponent(lblRiba)
                    .addComponent(lblAssegno)
                    .addComponent(lblBonifico)
                    .addComponent(lblBonifico1))
                .addGap(55, 55, 55)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtRiba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtAssegno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtBonifico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtContante, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtAccredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pnlLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblAssegno, lblBonifico, lblContante, lblRiba});

        pnlLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtAccredito, txtAssegno, txtBonifico, txtContante, txtRiba});

        pnlLayout.setVerticalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContante)
                    .addComponent(txtContante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBonifico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBonifico)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAccredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBonifico1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssegno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAssegno)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRiba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRiba)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, txtContante});

        pnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, txtBonifico});

        pnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, txtAccredito});

        pnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel4, txtAssegno});

        pnlLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, txtRiba});

        btnConferma.setBackground(new java.awt.Color(255, 255, 255));
        btnConferma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.png"))); // NOI18N
        btnConferma.setText("Conferma");
        btnConferma.setToolTipText("Conferma");
        btnConferma.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnConferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfermaActionPerformed(evt);
            }
        });

        btnAnnulla.setBackground(new java.awt.Color(255, 255, 255));
        btnAnnulla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/annulla.png"))); // NOI18N
        btnAnnulla.setText("Annulla");
        btnAnnulla.setToolTipText("Annulla");
        btnAnnulla.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnullaActionPerformed(evt);
            }
        });

        lblRiba1.setText("Totale inserito");

        txtTot.setEditable(false);
        txtTot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTot.setForeground(new java.awt.Color(0, 255, 0));
        txtTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setText("€");

        jLabel11.setText("Data Pagamento");

        jLabel13.setText("gg");

        txtAnnoPagamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel12.setText("mm");

        txtGiornoPagamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel14.setText("aaaa");

        txtMesePagamento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConferma)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRiba1)
                            .addComponent(jLabel11))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtGiornoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMesePagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAnnoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAnnulla, btnConferma});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRiba1)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtAnnoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtGiornoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtMesePagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConferma)
                    .addComponent(btnAnnulla))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel6, txtTot});

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    
    for (int i = 1; i < txtMetodi.length; i++)
        txtMetodi[i].setDocument(new JTextFieldFormatDouble());
    
    txtTot.setDocument(new JTextFieldFormatDouble());
    
    if (fattura.getNotePag() == null) {
        String metPag = (fattura.getMetPag().split("-"))[0];
        double totale = DoubleFormatter.roundTwoDecimals(fattura.getTotale());
    
        if (metPag.equals("Rimessa diretta"))
            txtContante.setText(String.valueOf(totale));
        else {        
            for (int i = 1; i < txtMetodi.length; i++)
                if (metPag.equals(metodiPagamento[i])) {
                    txtMetodi[i].setText(String.valueOf(totale));
                    break;
                }
        }
        
    } else {
        List<Movimento> movimenti = fattura.getMovimenti();
        for (Movimento movimento : movimenti) {
            for (int i = 1; i < txtMetodi.length; i++)
                if (movimento.getMetPag().equals(metodiPagamento[i])) {
                    txtMetodi[i].setText(String.valueOf(movimento.getValore()));
                    break;
                }
        }
    }
    parentClassName = parent.getClass().getSimpleName(); 
    checkInsertedTotal();
    txtGiornoPagamento.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
    txtMesePagamento.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
    txtAnnoPagamento.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
    
    Date data = null;
    if (fattura.getDataPagamento() == null) {
        data = fattura.getDataScadenza();
    } else
        data = fattura.getDataPagamento();
    
    String[] dataStr = data.toString().split("-");
    txtGiornoPagamento.setText(dataStr[2]);
    txtMesePagamento.setText(dataStr[1]);
    txtAnnoPagamento.setText(dataStr[0]);
}//GEN-LAST:event_formWindowOpened

private void checkInsertedTotal(){
    double tot = 0.00;
    for (int i = 1; i < txtMetodi.length; i++){
        double val = 0.00;
        if (!(txtMetodi[i].getText().equalsIgnoreCase("")))
           val = Double.parseDouble(txtMetodi[i].getText());
        tot += val;
    }
    txtTot.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(tot)));
    if (DoubleFormatter.roundTwoDecimals(tot) != fattura.getTotale())
        txtTot.setForeground(Color.red);
    else txtTot.setForeground(Color.green);
}

private void btnAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnullaActionPerformed
// TODO add your handling code here:
    if (parentClassName.equalsIgnoreCase(REG_ACQUISTO))
        ((RegistroFattureAcquisto)parent).setFatture();
    else if (parentClassName.equalsIgnoreCase(REG_EMESSE))
        ((RegistroFattureEmesse)parent).setFatture();
    else if (parentClassName.equalsIgnoreCase(SPEDIZIONI)){
        ((Spedizioni)parent).movimenti = null;
        ((Spedizioni)parent).unCheckPagate();
    }
    else if (parentClassName.equalsIgnoreCase(INS_FATT_ACQUISTO)){
        ((InsFatturaAcquisto)parent).movimenti = null;
        ((InsFatturaAcquisto)parent).unCheckPagate();
    }
    else if (parentClassName.equalsIgnoreCase(PAGAMENTO_FATT)){
        ((PagamentoFattura)parent).movimenti = null;
        ((PagamentoFattura)parent).unCheckPagate();
    }
 
    dispose();
}//GEN-LAST:event_btnAnnullaActionPerformed

private void btnConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfermaActionPerformed
// TODO add your handling code here:
    double[] valori = new double[txtMetodi.length -1];
    double somma = 0.00;
    for (int i = 0; i < txtMetodi.length -1; i++) {
        String valore = txtMetodi[i + 1].getText();
        if (!valore.isEmpty()) {
            try {
                valori[i] = DoubleFormatter.roundTwoDecimals(Double.parseDouble(valore));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valore errato per il metodo " + metodiPagamento[i + 1] + "!", "Formato errato", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        } else {
            valori[i] = 0.00;
        }
        
        somma += valori[i];
    }
        
    if (DoubleFormatter.roundTwoDecimals(somma) != fattura.getTotale()) {
        JOptionPane.showMessageDialog(this, "La somma di tutti i valori inseriti non coincide con il totale della fattura!\nSi prega di ricontrollare i valori inseriti",
                "Errore", JOptionPane.ERROR_MESSAGE);
        
    } else {
        List<Movimento> movimenti = new LinkedList<Movimento>();
        Fattura.tipo tipo;
        if (parentClassName.equalsIgnoreCase(REG_EMESSE) || parentClassName.equalsIgnoreCase(SPEDIZIONI) || parentClassName.equalsIgnoreCase(PAGAMENTO_FATT)) {
            if (fattura instanceof NotaCredito)
                tipo = Fattura.tipo.VNC;
            else
                tipo = Fattura.tipo.VEN;
        } else
            tipo = Fattura.tipo.ACQ;
        
        
        for (int i = 0; i < valori.length; i++)
            if (valori[i] != 0.00)
                movimenti.add(new Movimento(fattura.getNumero(), fattura.getData(), tipo.toString(), metodiPagamento[i + 1], valori[i], fattura.getCliente().getCod()));
        
         String anno = txtAnnoPagamento.getText();
         String mese = txtMesePagamento.getText();
         String giorno = txtGiornoPagamento.getText(); 
       
         Date dataPagam = null;
         try {
             dataPagam = Utility.dateValueOf(anno, mese, giorno, "data pagamento");
         } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Formato errato", JOptionPane.ERROR_MESSAGE);
             return;
         }
         
         if (parentClassName.equalsIgnoreCase(INS_FATT_ACQUISTO)){
            ((InsFatturaAcquisto)parent).movimenti = movimenti; 
            ((InsFatturaAcquisto)parent).dataPagamento = dataPagam; 
         } else if (parentClassName.equalsIgnoreCase(SPEDIZIONI)){
            ((Spedizioni)parent).movimenti = movimenti;
            ((Spedizioni)parent).dataPagamento = dataPagam;
         } else if (parentClassName.equalsIgnoreCase(PAGAMENTO_FATT)){
             ((PagamentoFattura)parent).movimenti = movimenti;
             ((PagamentoFattura)parent).dataPagamento = dataPagam;
         } else {
             
             fattura.setDataPagamento(dataPagam);
             FrontController.updatePagataFattura(tipo, fattura, true, movimenti);
        
             if (tipo == Fattura.tipo.VEN || tipo == Fattura.tipo.VNC) {
                ((RegistroFattureEmesse)parent).setFatture();
            
             } else {
                ((RegistroFattureAcquisto)parent).setFatture();
             }
         }
        
        dispose();
    }
    
    
}//GEN-LAST:event_btnConfermaActionPerformed

private void txtContanteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtContanteFocusLost
// TODO add your handling code here:
    checkInsertedTotal();
}//GEN-LAST:event_txtContanteFocusLost

private void txtBonificoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBonificoFocusLost
// TODO add your handling code here:
    checkInsertedTotal();
}//GEN-LAST:event_txtBonificoFocusLost

private void txtAssegnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAssegnoFocusLost
// TODO add your handling code here:
    checkInsertedTotal();
}//GEN-LAST:event_txtAssegnoFocusLost

private void txtRibaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRibaFocusLost
// TODO add your handling code here:
    checkInsertedTotal();
}//GEN-LAST:event_txtRibaFocusLost

private void txtAccreditoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAccreditoFocusLost
// TODO add your handling code here:
    checkInsertedTotal();
}//GEN-LAST:event_txtAccreditoFocusLost

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
// TODO add your handling code here:
    btnAnnullaActionPerformed(null);
}//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnnulla;
    private javax.swing.JButton btnConferma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblAssegno;
    private javax.swing.JLabel lblBonifico;
    private javax.swing.JLabel lblBonifico1;
    private javax.swing.JLabel lblContante;
    private javax.swing.JLabel lblRiba;
    private javax.swing.JLabel lblRiba1;
    private javax.swing.JPanel pnl;
    private javax.swing.JTextField txtAccredito;
    private javax.swing.JTextField txtAnnoPagamento;
    private javax.swing.JTextField txtAssegno;
    private javax.swing.JTextField txtBonifico;
    private javax.swing.JTextField txtContante;
    private javax.swing.JTextField txtGiornoPagamento;
    private javax.swing.JTextField txtMesePagamento;
    private javax.swing.JTextField txtRiba;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables

    private Fattura fattura;
    private javax.swing.JTextField txtMetodi[];
    private Window parent;
    private String parentClassName;
//    private JFrame spedizioniParent;
//    private JDialog dialogParent;
//    private boolean calledByDialog = false;
//    private boolean calledBySpedizioni = false;
    private String[] metodiPagamento;
    private static final int MAX_LENGTH_GIORNO = 2;
    private static final int MAX_LENGTH_MESE = 2;
    private static final int MAX_LENGTH_ANNO = 4;
}