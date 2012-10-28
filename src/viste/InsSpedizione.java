/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJDialog.java
 *
 * Created on 1-mag-2012, 22.18.26
 */
package viste;

import com.itextpdf.text.DocumentException;
import controllo.FrontController;
import eccezioni.EccezioneChiaveDuplicata;
import eccezioni.EccezioneValoreCampoTroppoLungo;
import entita.Entity;
import entita.Fattura;
import entita.Fornitore;
import entita.Mezzo;
import entita.Movimento;
import entita.Spedizione;
import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import libs.DoubleFormatter;

/**
 *
 * @author Michele
 */
public class InsSpedizione extends javax.swing.JDialog {
    
    /** Creates new form NewJDialog */
    public InsSpedizione(java.awt.Frame parent, boolean modal, Fornitore fornitore) {
        super(parent, modal);
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlDatiSpedizione);
        color.changeColor(pnlGenerale);
        color.changeColor(pnlNote);
        color.changeColor(pnlPrezzo);
        color.changeColor(pnlTotale);
        blockTexts();
        setFormatDouble();
        id_fornitore = fornitore.getCod();
        this.fornitore = fornitore;
        setTitle("Inserimento Spedizioni: " + fornitore);
        txtPercIva.setText(String.valueOf(PERC_IVA));
//        java.util.Date utilDate = new java.util.Date();
//        String today = (new java.sql.Date(utilDate.getTime())).toString();
//        String[] splitted = today.split("\\-");
//        String anno = splitted[0];
//        String mese = splitted[1];
//        String giorno = splitted[2];
//        dataPresunta = Date.valueOf(anno + "-" + mese + "-" + giorno);
//        txtNumSpedizione.setText(String.valueOf(FrontController.getNumber(Spedizione.class, dataPresunta)));
        this.parent = (Spedizioni) parent;
        List<Entity> mezzi = FrontController.getAnagrafe(Mezzo.class);
        popolaSelect(mezzi); 
        modifica = false;
        String[] unitMis = FrontController.getUnitaMisura();
        for (String um : unitMis) {
            cboUm.addItem(um);
        }
    }
    
    public InsSpedizione(java.awt.Frame parent, boolean modal, Fornitore fornitore, Spedizione spedizione, int rigaTabellaSpedizioni) {
        super(parent, modal);
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlDatiSpedizione);
        color.changeColor(pnlGenerale);
        color.changeColor(pnlNote);
        color.changeColor(pnlPrezzo);
        color.changeColor(pnlTotale);
        blockTexts();
        setFormatDouble();
        id_fornitore = fornitore.getCod();
        setTitle("Modifica Spedizione #" + spedizione.getNumSpedizione() + ", " + spedizione.getDataCarico().toString().substring(0, 4) + " - " + fornitore);
        this.parent = (Spedizioni) parent;
        List<Entity> mezzi = FrontController.getAnagrafe(Mezzo.class);
        popolaSelect(mezzi);
        btnMemorizza.setText("Conferma");
        btnNuovo.setEnabled(false);
        btnEmetti.setVisible(false);
        modifica = true;
        this.rigaTabellaSpedizioni = rigaTabellaSpedizioni;
        String[] unitMis = FrontController.getUnitaMisura();
        for (String um : unitMis) {
            cboUm.addItem(um);
        }
                
        txtNumSpedizione.setText(spedizione.getNumSpedizione());
        txtBolle.setText(spedizione.getStringaBolle());
        
        String[] data = spedizione.getDataCarico().toString().split("-");
        txtAnnoCarico.setText(data[0]);
        txtMeseCarico.setText(data[1]);
        txtGiornoCarico.setText(data[2]);
        
        Date dataDocumento = spedizione.getDataDocumento();
        if (dataDocumento != null) {
            data = spedizione.getDataDocumento().toString().split("-");
            txtAnnoDocumento.setText(data[0]);
            txtMeseDocumento.setText(data[1]);
            txtGiornoDocumento.setText(data[2]);
        }
        
        String descr = spedizione.getDescrizione();
        if (descr != null)
            txtDescrizione.setText(descr);
        
        if (spedizione.getMezzo() != null)
            cboMezzo.setSelectedItem(new Mezzo(null, spedizione.getMezzo(),null));
        else
            cboMezzo.setSelectedIndex(0);
        
        String um = spedizione.getUm();
        if (um != null)
            cboUm.setSelectedItem(um);
        else
            cboUm.setSelectedIndex(0);
        
        double importo = DoubleFormatter.roundTwoDecimals(spedizione.getImporto());
        double traz = DoubleFormatter.roundTwoDecimals(spedizione.getTraz());
        double distrib = DoubleFormatter.roundTwoDecimals(spedizione.getDistrib());
        int percSconto = spedizione.getSconto();
        double impSconto = DoubleFormatter.roundTwoDecimals(importo * percSconto / 100);
        double impScontato = DoubleFormatter.roundTwoDecimals(importo - impSconto);
        double valoreMerce = DoubleFormatter.roundTwoDecimals(spedizione.getValoreMerce());
        double impProvv = DoubleFormatter.roundTwoDecimals(spedizione.getProvvigione());
        double imponibile = DoubleFormatter.roundTwoDecimals(impScontato + impProvv);
        int percIva = spedizione.getPercIva();
        double iva = DoubleFormatter.roundTwoDecimals(spedizione.getIva());
        double percProvv = spedizione.getPercProvv();
        double totale = DoubleFormatter.roundTwoDecimals(spedizione.getTotale());
        char tipo = spedizione.getStato();
        
        txtQuantita.setText(Integer.toString(spedizione.getQta()));
        txtImporto.setText(Double.toString(importo));
        txtTrazione.setText(String.valueOf(traz));
        txtDistribuzione.setText(String.valueOf(distrib));
        txtPercSconto.setText(String.valueOf(percSconto));
        txtImpSconto.setText(String.valueOf(impSconto));
        txtImpScontato.setText(String.valueOf(imponibile));
        txtPercIva.setText(String.valueOf(percIva));
        txtImpIva.setText(String.valueOf(iva));
        txtPercProv.setText(String.valueOf(percProvv));
        txtImpProv.setText(String.valueOf(impProvv));
        txtTotale.setText(String.valueOf(totale));
        txtValMerce.setText(String.valueOf(valoreMerce));
        txtImponibile.setText(String.valueOf(imponibile));
        Integer numFattura = spedizione.getNumFattura();
        
        String note = spedizione.getNote();
        if (note != null)
            txtNote.setText(note);
        
        txtAnnoCarico.setEnabled(false);
        txtMeseCarico.setEnabled(false);
        txtGiornoCarico.setEnabled(false);
        chkRientrata.setSelected(spedizione.getRientrata());
        
        if (tipo == 'C')
            optConsegna.setSelected(true);
        else
            optRitiro.setSelected(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGenerale = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNumSpedizione = new javax.swing.JTextField();
        txtGiornoCarico = new javax.swing.JTextField();
        cboMezzo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtMeseCarico = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAnnoCarico = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiornoDocumento = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMeseDocumento = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtAnnoDocumento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBolle = new javax.swing.JTextField();
        txtDescrizione = new javax.swing.JTextField();
        optConsegna = new javax.swing.JRadioButton();
        optRitiro = new javax.swing.JRadioButton();
        pnlDatiSpedizione = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtQuantita = new javax.swing.JTextField();
        cboUm = new javax.swing.JComboBox();
        pnlTotale = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txtPercIva = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtImpIva = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtImpProv = new javax.swing.JTextField();
        txtPercProv = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtTotale = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtValMerce = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txtImponibile = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        pnlPrezzo = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtTrazione = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtDistribuzione = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtPercSconto = new javax.swing.JTextField();
        txtImporto = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtImpSconto = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtImpScontato = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        pnlNote = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        chkRientrata = new javax.swing.JCheckBox();
        btnNuovo = new javax.swing.JButton();
        btnMemorizza = new javax.swing.JButton();
        btnEmetti = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        pnlGenerale.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Generale", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel19.setText("Data documento");

        jLabel18.setText("Descrizione");

        jLabel17.setText("Mezzo");

        jLabel2.setText("Numero");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel9.setText("Data carico");

        txtNumSpedizione.setEnabled(false);

        txtGiornoCarico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiornoCaricoFocusLost(evt);
            }
        });

        cboMezzo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleziona" }));
        cboMezzo.setNextFocusableComponent(cboUm);

        jLabel10.setText("gg");

        jLabel11.setText("mm");

        txtMeseCarico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMeseCaricoFocusLost(evt);
            }
        });

        jLabel12.setText("aaaa");

        txtAnnoCarico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAnnoCaricoFocusLost(evt);
            }
        });

        jLabel13.setText("gg");

        jLabel14.setText("mm");

        jLabel20.setText("aaaa");

        jLabel3.setText("Bolle");

        txtBolle.setNextFocusableComponent(txtDescrizione);

        txtDescrizione.setNextFocusableComponent(cboMezzo);

        optConsegna.setText("Consegna");
        optConsegna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optConsegnaActionPerformed(evt);
            }
        });

        optRitiro.setText("Ritiro");
        optRitiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optRitiroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlGeneraleLayout = new javax.swing.GroupLayout(pnlGenerale);
        pnlGenerale.setLayout(pnlGeneraleLayout);
        pnlGeneraleLayout.setHorizontalGroup(
            pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneraleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGeneraleLayout.createSequentialGroup()
                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlGeneraleLayout.createSequentialGroup()
                                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlGeneraleLayout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addGap(30, 30, 30))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlGeneraleLayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDescrizione, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGeneraleLayout.createSequentialGroup()
                                                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtNumSpedizione, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtBolle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(optConsegna)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(optRitiro)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel19)
                                                    .addComponent(jLabel9))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(pnlGeneraleLayout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtGiornoCarico, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel11)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtMeseCarico, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel12)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtAnnoCarico, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(pnlGeneraleLayout.createSequentialGroup()
                                                        .addComponent(jLabel13)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtGiornoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtMeseDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel20)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtAnnoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addComponent(cboMezzo, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(20, 20, 20)))
                        .addGap(21, 21, 21))
                    .addComponent(jLabel17)))
        );
        pnlGeneraleLayout.setVerticalGroup(
            pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneraleLayout.createSequentialGroup()
                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGeneraleLayout.createSequentialGroup()
                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNumSpedizione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtBolle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(optConsegna)
                            .addComponent(optRitiro)))
                    .addGroup(pnlGeneraleLayout.createSequentialGroup()
                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiornoCarico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtMeseCarico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtAnnoCarico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9))
                        .addGap(14, 14, 14)
                        .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtGiornoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(txtMeseDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txtAnnoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))))
                .addGap(33, 33, 33)
                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtDescrizione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlGeneraleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboMezzo)
                    .addComponent(jLabel17))
                .addGap(24, 24, 24))
        );

        pnlDatiSpedizione.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dati Spedizione", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel15.setText("UM");

        jLabel16.setText("Quantità");

        txtQuantita.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtQuantita.setNextFocusableComponent(txtTrazione);
        txtQuantita.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantitaFocusLost(evt);
            }
        });

        cboUm.setNextFocusableComponent(txtQuantita);

        javax.swing.GroupLayout pnlDatiSpedizioneLayout = new javax.swing.GroupLayout(pnlDatiSpedizione);
        pnlDatiSpedizione.setLayout(pnlDatiSpedizioneLayout);
        pnlDatiSpedizioneLayout.setHorizontalGroup(
            pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatiSpedizioneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(65, 65, 65)
                .addGroup(pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboUm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtQuantita, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addGap(53, 53, 53))
        );
        pnlDatiSpedizioneLayout.setVerticalGroup(
            pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatiSpedizioneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(pnlDatiSpedizioneLayout.createSequentialGroup()
                        .addComponent(cboUm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatiSpedizioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtQuantita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTotale.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Totale Spedizione", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel33.setText("% IVA");

        txtPercIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPercIva.setText("21");
        txtPercIva.setNextFocusableComponent(txtNote);
        txtPercIva.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPercIvaFocusLost(evt);
            }
        });

        jLabel34.setText("Importo IVA");

        txtImpIva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpIva.setEnabled(false);
        txtImpIva.setFocusable(false);

        jLabel35.setText("Imp. Provvigione");

        jLabel36.setText("% Provvigione");

        txtImpProv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpProv.setNextFocusableComponent(txtPercIva);

        txtPercProv.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPercProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPercProvFocusLost(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel38.setText("Totale finale");

        txtTotale.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtTotale.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotale.setEnabled(false);
        txtTotale.setFocusable(false);

        jLabel40.setText("€");

        jLabel41.setText("%");

        jLabel43.setText("€");

        jLabel44.setText("€");

        jLabel37.setText("%");

        jLabel42.setText("Valore Merce");

        txtValMerce.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValMerce.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValMerceFocusLost(evt);
            }
        });

        jLabel45.setText("€");

        jLabel46.setText("Imponibile");

        txtImponibile.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImponibile.setEnabled(false);
        txtImponibile.setFocusable(false);

        jLabel47.setText("€");

        javax.swing.GroupLayout pnlTotaleLayout = new javax.swing.GroupLayout(pnlTotale);
        pnlTotale.setLayout(pnlTotaleLayout);
        pnlTotaleLayout.setHorizontalGroup(
            pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotaleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTotaleLayout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotale, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addGroup(pnlTotaleLayout.createSequentialGroup()
                        .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel35)
                            .addComponent(jLabel42))
                        .addGap(18, 18, 18)
                        .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTotaleLayout.createSequentialGroup()
                                .addComponent(txtValMerce, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTotaleLayout.createSequentialGroup()
                                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPercProv, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(txtImpProv, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotaleLayout.createSequentialGroup()
                        .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTotaleLayout.createSequentialGroup()
                                .addComponent(txtImponibile, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlTotaleLayout.createSequentialGroup()
                                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtImpIva)
                                    .addComponent(txtPercIva, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        pnlTotaleLayout.setVerticalGroup(
            pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTotaleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txtValMerce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtPercProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtImpProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(txtImponibile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel37)
                    .addComponent(txtPercIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtImpIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addGap(38, 38, 38)
                .addGroup(pnlTotaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)))
        );

        pnlPrezzo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Prezzo Spedizione", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel21.setText("Trazione");

        txtTrazione.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTrazione.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTrazioneFocusLost(evt);
            }
        });

        jLabel22.setText("Distribuzione");

        txtDistribuzione.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDistribuzione.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDistribuzioneFocusLost(evt);
            }
        });

        jLabel23.setText("% Sconto");

        jLabel24.setText("Importo");

        txtPercSconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPercSconto.setNextFocusableComponent(txtValMerce);
        txtPercSconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPercScontoFocusLost(evt);
            }
        });

        txtImporto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImporto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtImportoFocusLost(evt);
            }
        });

        jLabel25.setText("Sconto");

        txtImpSconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpSconto.setEnabled(false);
        txtImpSconto.setFocusable(false);

        jLabel26.setText("Importo Scontato");

        txtImpScontato.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtImpScontato.setEnabled(false);
        txtImpScontato.setFocusable(false);

        jLabel27.setText("€");

        jLabel28.setText("€");

        jLabel29.setText("€");

        jLabel30.setText("€");

        jLabel31.setText("€");

        jLabel32.setText("%");

        javax.swing.GroupLayout pnlPrezzoLayout = new javax.swing.GroupLayout(pnlPrezzo);
        pnlPrezzo.setLayout(pnlPrezzoLayout);
        pnlPrezzoLayout.setHorizontalGroup(
            pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrezzoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(jLabel25)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addGap(22, 22, 22)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtImpScontato, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtTrazione, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtDistribuzione, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtImporto, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtPercSconto, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addComponent(txtImpSconto, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrezzoLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlPrezzoLayout.createSequentialGroup()
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPrezzoLayout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlPrezzoLayout.createSequentialGroup()
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
                                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPrezzoLayout.createSequentialGroup()
                                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                                        .addGap(27, 27, 27))
                                    .addGroup(pnlPrezzoLayout.createSequentialGroup()
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())))))))
        );
        pnlPrezzoLayout.setVerticalGroup(
            pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrezzoLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtTrazione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtDistribuzione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtImporto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtPercSconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtImpSconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPrezzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtImpScontato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)))
        );

        pnlNote.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Note", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        jLabel39.setText("Note");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);

        chkRientrata.setText("Bolla/e Rientrata/e");

        javax.swing.GroupLayout pnlNoteLayout = new javax.swing.GroupLayout(pnlNote);
        pnlNote.setLayout(pnlNoteLayout);
        pnlNoteLayout.setHorizontalGroup(
            pnlNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNoteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkRientrata)
                    .addGroup(pnlNoteLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNoteLayout.setVerticalGroup(
            pnlNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNoteLayout.createSequentialGroup()
                .addGroup(pnlNoteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNoteLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel39))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkRientrata)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        btnNuovo.setBackground(new java.awt.Color(255, 255, 255));
        btnNuovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pulisci.png"))); // NOI18N
        btnNuovo.setToolTipText("Cancella i valori inseriti");
        btnNuovo.setLabel("Pulisci campi");
        btnNuovo.setMargin(new java.awt.Insets(2, 0, 2, 14));
        btnNuovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuovoActionPerformed(evt);
            }
        });

        btnMemorizza.setBackground(new java.awt.Color(255, 255, 255));
        btnMemorizza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        btnMemorizza.setText("Memorizza");
        btnMemorizza.setToolTipText("Memorizza la spedizione");
        btnMemorizza.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnMemorizza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMemorizzaActionPerformed(evt);
            }
        });

        btnEmetti.setBackground(new java.awt.Color(255, 255, 255));
        btnEmetti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/emettifattura.png"))); // NOI18N
        btnEmetti.setText("Emetti Fattura");
        btnEmetti.setToolTipText("Emetti fattura immediatamente");
        btnEmetti.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnEmetti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmettiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlGenerale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlPrezzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlDatiSpedizione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlTotale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnNuovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnMemorizza, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEmetti))
                            .addComponent(pnlNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlGenerale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnMemorizza)
                            .addComponent(btnEmetti))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuovo))
                    .addComponent(pnlTotale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatiSpedizione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnlPrezzo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void setFormatDouble() {
    txtTrazione.setDocument(new JTextFieldFormatDouble());
    txtDistribuzione.setDocument(new JTextFieldFormatDouble());
    txtImporto.setDocument(new JTextFieldFormatDouble());
    txtValMerce.setDocument(new JTextFieldFormatDouble());
    txtImpProv.setDocument(new JTextFieldFormatDouble());
    txtPercProv.setDocument(new JTextFieldFormatDouble());
}
    
private void txtAnnoCaricoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAnnoCaricoFocusLost
// TODO add your handling code here:
    txtAnnoDocumento.setText(txtAnnoCarico.getText());
}//GEN-LAST:event_txtAnnoCaricoFocusLost

private void txtQuantitaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantitaFocusLost
// TODO add your handling code here:
    calcolaImporto();
    calcolaSconto();
    calcolaTotale();
}//GEN-LAST:event_txtQuantitaFocusLost

private void txtPercIvaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPercIvaFocusLost
// TODO add your handling code here:
    calcolaTotale();
}//GEN-LAST:event_txtPercIvaFocusLost

private void txtTrazioneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTrazioneFocusLost
// TODO add your handling code here:
    calcolaImporto();
    calcolaSconto();
    calcolaTotale();
}//GEN-LAST:event_txtTrazioneFocusLost

private void txtDistribuzioneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDistribuzioneFocusLost
// TODO add your handling code here:
    calcolaImporto();
    calcolaSconto();
    calcolaTotale();
}//GEN-LAST:event_txtDistribuzioneFocusLost

private void txtPercScontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPercScontoFocusLost
// TODO add your handling code here:
    calcolaSconto();
    calcolaTotale();
}//GEN-LAST:event_txtPercScontoFocusLost

private void btnNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuovoActionPerformed
// TODO add your handling code here:
    int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler azzerare i valori inseriti?", "Conferma azzeramento campi", JOptionPane.OK_CANCEL_OPTION);
    if (RESPONSE == JOptionPane.OK_OPTION) 
        pulisciText();
}//GEN-LAST:event_btnNuovoActionPerformed

/*
 * Verifica che il campo per l'inserimento delle bolle contenga una serie di interi
 * separati da un un '-'
 */
private boolean checkBolle(String bolle) {
//    Pattern pattern = Pattern.compile("[1-9]([0-9])*(-[1-9]([0-9])*)*");
//    Pattern pattern = Pattern.compile("(\\d){1,}(/[a-z]{1,}){0,1}(-(\\d){1,}(/[a-z]{1,}){0,1})*");
//    Matcher match = pattern.matcher(bolle);
//    return match.matches();
    
    return true;
      
}

private Spedizione creaSpedizioneDaInserire() {
    Date dataCarico = null;
    String anno = txtAnnoCarico.getText();
    String mese = txtMeseCarico.getText();
    String giorno = txtGiornoCarico.getText();
    
    if (anno.length() == 2)
        anno = "20" + anno;
    
    if (mese.length() == 1)
        mese = "0" + mese;
    
    if (giorno.length() == 1) 
        giorno = "0" + giorno;
    
    if (anno.isEmpty() || mese.isEmpty() || giorno.isEmpty()) { //Un o più campi fra gg, mm e aaaa non sono stati inseriti
        JOptionPane.showMessageDialog(null, "Inserire la data carico nel formato gg mm aaaa", 
            "Campo obbligatorio mancante", JOptionPane.ERROR_MESSAGE);
        return null;
    }
        
    /*
     * Se la data non è inserita nel formato corretto, mostra un messaggio di errore.
     * Per formato corretto si intende che il giorno non superi 31, o che il mese non
     * sia superiore a 12, oppure che sia stato inserito il giorno corretto in base al 
     * mese inserito. (es. il 31/11 non esiste)
     */
    try {
        dataCarico = Date.valueOf(anno + "-" + mese + "-" + giorno);
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(null, "Valore inserito per la data carico non valido! Inserire la data nel formato gg/mm/aaaa", 
            "Formato errato", JOptionPane.ERROR_MESSAGE);
            return null;
    }
    
    Date dataDocumento = null;
    anno = txtAnnoDocumento.getText();
    mese = txtMeseDocumento.getText();
    giorno = txtGiornoDocumento.getText();
    
    if (anno.length() == 2)
        anno = "20" + anno;
    
    if (mese.length() == 1)
        mese = "0" + mese;
    
    if (giorno.length() == 1) 
        giorno = "0" + giorno;
    
    if (!anno.isEmpty() && !mese.isEmpty() && !giorno.isEmpty()) { //Sono stati inseriti tutti i campi relativi alla data in questione
        //Controlla la data, come nel caso della data precedente
        try {
            dataDocumento = Date.valueOf(anno + "-" + mese + "-" + giorno);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Valore inserito per la data documento non valido! Inserire la data nel formato gg/mm/aaaa", 
                "Formato errato", JOptionPane.ERROR_MESSAGE);
                return null;
        }
    } else if (anno.isEmpty() && mese.isEmpty() && giorno.isEmpty()) {
        
    } else { //Un o più campi fra gg, mm e aaaa non sono stati inseriti
        JOptionPane.showMessageDialog(null, "Valore inserito per la data documento non valido! Inserire la data nel formato gg/mm/aaaa", 
                "Formato errato", JOptionPane.ERROR_MESSAGE);
                return null;
    }
    
    //Acquisisce la stringa delle bolle, e se non vuota, controlla che rispetti il formato corretto (cioè col '-')
    String stringaBolle = txtBolle.getText();
    if (!stringaBolle.isEmpty())
        if (!checkBolle(stringaBolle)) {
            JOptionPane.showMessageDialog(null, "Valore inserito per le bolle non valido! Inserire i numeri delle bolle separati da un -", 
                "Formato errato", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    
    //Le la stringa delle bolle non è vuota, isola ciascuna bolla assegnandola ad un elemento diverso di un array
    String[] bolle  = null;
    if (stringaBolle != null)
        if (!stringaBolle.isEmpty())
            bolle = stringaBolle.split("-");
            
    String descrizione = txtDescrizione.getText();
    
    /*
     * Per tutti i campi che seguono, si cerca di ricavare il corrispettivo valore dalle text. 
     * Se viene generata una eccezione, vuol dire che il campo è rimasto vuoto, quindi si 
     * imposta il valore delle corrispettive variabili al valore di default per il tipo del valore.
     */
    
    double distrib = 0.00;
    try {
        distrib = (Double.parseDouble(txtDistribuzione.getText()));
    } catch (NumberFormatException e) {}
    
    double iva = 0.00;
    try {
        iva = (Double.parseDouble(txtImpIva.getText()));
    } catch (NumberFormatException e) {}
    
    double impProv = 0.00;
    try {
        impProv = (Double.parseDouble(txtImpProv.getText()));
    } catch (NumberFormatException e) {}
       
    double importo = 0.00;
    try {
        importo = (Double.parseDouble(txtImporto.getText()));
    } catch (NumberFormatException e) {}
    
    String note = txtNote.getText();
    
    int percIva = PERC_IVA;
    try {
        percIva = Integer.parseInt(txtPercIva.getText());
    } catch (NumberFormatException e) {}
    
    double percProvv = 0.0;
    try {
        percProvv = Double.parseDouble(txtPercProv.getText());
    } catch (NumberFormatException e) {}
    
    int sconto = 0;
    try {
        sconto = Integer.parseInt(txtPercSconto.getText());
    } catch (NumberFormatException e) {}
    
    int quantita = 0;
    try {
        quantita = Integer.parseInt(txtQuantita.getText());
    } catch (NumberFormatException e) {}
    
    double totale = 0.00;
    try {
        totale = (Double.parseDouble(txtTotale.getText()));
    } catch (NumberFormatException e) {}
    
    double trazione = 0.00;
    try {
        trazione = (Double.parseDouble(txtTrazione.getText()));
    } catch (NumberFormatException e) {}
    
    String  um = (String) cboUm.getSelectedItem();
    
    //Acquisisce il mezzo selezionato. Se non è stato selezionato alcun mezzo, viene lanciata e gestita una eccezione.      
    Mezzo mezzo = null;
    String targa;
    try {
        mezzo = (Mezzo) cboMezzo.getSelectedItem();
        targa = mezzo.getTarga();
    } catch (ClassCastException e) {
        targa = null;
    }  
    
    boolean rientrata = chkRientrata.isSelected();
    
    double valMerce = 0.00;
    try {
        valMerce = Double.parseDouble(txtValMerce.getText());
    } catch (NumberFormatException e) {}
    
    double imponibile = 0.00;
    try {
        imponibile = Double.parseDouble(txtImponibile.getText());
    } catch (NumberFormatException e) {}
    
    if (!modifica)
        txtNumSpedizione.setText(String.valueOf(FrontController.getNumber(Spedizione.class, dataCarico)));
    
    String numero = txtNumSpedizione.getText();
    
    char stato;
    
    if (!optConsegna.isSelected() && !optRitiro.isSelected()) {
        JOptionPane.showMessageDialog(this, "Indicare se la spedizione è una Consegna o un Ritiro", "Campo mancante", JOptionPane.ERROR_MESSAGE);
        return null;
    }
    
    if (optConsegna.isSelected())
        stato = 'C';
    else
        stato = 'R';
    
    Spedizione sped = new Spedizione(numero, id_fornitore, dataCarico, dataDocumento, descrizione, targa,
            um, quantita, trazione, distrib, importo, sconto, percIva, iva, 
            percProvv, impProv, totale, note, rientrata, null, null, valMerce, imponibile, stato);
    
    spedDaFatturare = sped;
    
    if (bolle != null) {
        for (String bolla : bolle)
            sped.addBolla(bolla);
    }
    
    return sped;
}

private void btnMemorizzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMemorizzaActionPerformed
// TODO add your handling code here:
    
    Spedizione sped = creaSpedizioneDaInserire();
    if (sped != null) {
        if (!modifica) {
            final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'inserimento?", "Conferma inserimento", JOptionPane.OK_CANCEL_OPTION);
            if (RESPONSE == JOptionPane.OK_OPTION) {
                try {
                    if (FrontController.insert(sped)) {
                        parent.ricaricaTabella(); //Aggiorna la tabella del frame di visualizzazione delle spedizioni, in modo da visualizzare la riga appena inserita
                        pulisciText();
                        JOptionPane.showMessageDialog(null, "Inserimento eseguito con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                        spedizioneInserita = true;                    
                    } else {
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'inserimento", "Errore" ,JOptionPane.ERROR_MESSAGE);
                    }

                } catch (EccezioneChiaveDuplicata e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                } catch (EccezioneValoreCampoTroppoLungo e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }

            }

        } else {
            final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con la modifica?", "Conferma modifica", JOptionPane.OK_CANCEL_OPTION);
            if (RESPONSE == JOptionPane.OK_OPTION) {
                try {
                    if (FrontController.update(sped)) {
                        parent.ricaricaTabella(rigaTabellaSpedizioni, sped); //Aggiorna la tabella del frame di visualizzazione delle spedizioni, in modo da visualizzare la riga appena inserita
                        JOptionPane.showMessageDialog(null, "Modifica eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                        dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'inserimento", "Errore" ,JOptionPane.ERROR_MESSAGE);
                    }
                } catch (EccezioneValoreCampoTroppoLungo e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }

}//GEN-LAST:event_btnMemorizzaActionPerformed

private void txtPercProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPercProvFocusLost
// TODO add your handling code here:
    calcolaProvvigione();
    calcolaTotale();
}//GEN-LAST:event_txtPercProvFocusLost

private void txtImportoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtImportoFocusLost
// TODO add your handling code here:
    calcolaSconto();
    calcolaTotale();
}//GEN-LAST:event_txtImportoFocusLost

void continuaEmissione(int numero, Date dataFattura, String metodoPagamento, boolean pagata, boolean forfait, String note, List<Movimento> movimenti,
        Date dataScadenza) {
           
    Double importo = 0.00;
    try {
        importo = Double.parseDouble(txtImporto.getText());
    } catch (NumberFormatException e) {}
    
    Double provvigione = 0.00;
    try {
        provvigione = Double.parseDouble(txtImpProv.getText());
    } catch (NumberFormatException e) {}
    
    Double sconto = 0.00;
    try {
        sconto = Double.parseDouble(txtImpSconto.getText());

    } catch (NumberFormatException e) {}
    
    Double ivaTot = 0.00;
    try {
        
        ivaTot = Double.parseDouble(txtImpIva.getText());
  
    } catch (NumberFormatException e) {}
    
    Double totale = 0.00;
    try {
        totale = Double.parseDouble(txtTotale.getText());
    } catch (NumberFormatException e) {}
    
    List<Spedizione> speds = new LinkedList<Spedizione>();
    speds.add(spedDaFatturare);

    Fattura fatt = new Fattura(numero, dataFattura, metodoPagamento, importo, provvigione, sconto, ivaTot, totale, speds, forfait, pagata, note,
            dataScadenza);
    
    fatt.setCliente(fornitore);
    try {
        if (FrontController.insert(fatt)) {
            if (pagata) {
                
                for (Movimento m : movimenti){
                    m.setFornCliente(id_fornitore);
                    m.setData(dataFattura);
                    m.setNumDoc(numero);
                                
                }
                
                FrontController.updatePagataFattura(Fattura.tipo.VEN, fatt, pagata, movimenti);
            }
            
            JOptionPane.showMessageDialog(null, "Fatturazione eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
            pulisciText();
            parent.ricaricaTabella();
            modifica = false;
            txtAnnoCarico.setEnabled(true);
            txtMeseCarico.setEnabled(true);
            txtGiornoCarico.setEnabled(true);
            btnMemorizza.setText("Memorizza");
            spedizioneInserita = false;
            //Stampa della fattura e visualizzazione a video
            try {
                new stampa.StampaFattura(fatt, fornitore, false).printAndOpen();   

            } catch (DocumentException ex) {
                Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la fatturazione", "Errore" ,JOptionPane.ERROR_MESSAGE);
        }
    } catch (EccezioneValoreCampoTroppoLungo e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
    }
}


private void btnEmettiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmettiActionPerformed
// TODO add your handling code here:
    class AvviaRichiestaMetodoPagamento extends Thread {

        private PagamentoFattura pagamento;

        public AvviaRichiestaMetodoPagamento(PagamentoFattura pagamento) {
            this.pagamento = pagamento;
        }

        @Override
        public void run() {
            super.run();
            FrontController.open(pagamento);

        }

    }
    
    if (!spedizioneInserita) {   
    
        final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'inserimento e la fatturazione della spedizione?", "Conferma", JOptionPane.OK_CANCEL_OPTION);
        if (RESPONSE == JOptionPane.OK_OPTION) {
            Spedizione sped = creaSpedizioneDaInserire();
            spedDaFatturare = sped;
            if (sped != null) {
                try {
                    if (FrontController.insert(sped)) {
                        parent.ricaricaTabella(); //Aggiorna la tabella del frame di visualizzazione delle spedizioni, in modo da visualizzare la riga appena inserita
                        //pulisciText();
                        spedizioneInserita = true;
                        modifica = true;
                        txtAnnoCarico.setEnabled(false);
                        txtMeseCarico.setEnabled(false);
                        txtGiornoCarico.setEnabled(false);
                        btnMemorizza.setText("Modifica");
                        new AvviaRichiestaMetodoPagamento(new PagamentoFattura(this, rootPaneCheckingEnabled)).start();
                    } else {
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'inserimento", "Errore" ,JOptionPane.ERROR_MESSAGE);
                    }

                } catch (EccezioneChiaveDuplicata e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                } catch (EccezioneValoreCampoTroppoLungo e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        
    } else {
        final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con la fatturazione della spedizione?", "Conferma", JOptionPane.OK_CANCEL_OPTION);
        if (RESPONSE == JOptionPane.OK_OPTION) {
            Spedizione sped = creaSpedizioneDaInserire();
            spedDaFatturare = sped;
            if (sped != null) {
                try {
                    if (FrontController.update(sped)) {
                        parent.ricaricaTabella(parent.getIndexSpedizione(sped), sped); //Aggiorna la tabella del frame di visualizzazione delle spedizioni, in modo da visualizzare la riga appena inserita
                        new AvviaRichiestaMetodoPagamento(new PagamentoFattura(this, rootPaneCheckingEnabled)).start();
                    } else {
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'inserimento", "Errore" ,JOptionPane.ERROR_MESSAGE);
                    }
                } catch (EccezioneValoreCampoTroppoLungo e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }               
                
            }
            
        }
    }
    
}//GEN-LAST:event_btnEmettiActionPerformed

private void txtGiornoCaricoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiornoCaricoFocusLost
// TODO add your handling code here:
    txtGiornoDocumento.setText(txtGiornoCarico.getText());
}//GEN-LAST:event_txtGiornoCaricoFocusLost

private void txtMeseCaricoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMeseCaricoFocusLost
// TODO add your handling code here:
    txtMeseDocumento.setText(txtMeseCarico.getText());
}//GEN-LAST:event_txtMeseCaricoFocusLost

private void blockTexts() {
    txtGiornoCarico.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
    txtMeseCarico.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
    txtAnnoCarico.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
    txtGiornoDocumento.setDocument(new JTextFieldLimit(MAX_LENGTH_GIORNO));
    txtMeseDocumento.setDocument(new JTextFieldLimit(MAX_LENGTH_MESE));
    txtAnnoDocumento.setDocument(new JTextFieldLimit(MAX_LENGTH_ANNO));
    txtPercIva.setDocument(new JTextFieldLimit(MAX_LENGTH_PERC));
    txtPercSconto.setDocument(new JTextFieldLimit(MAX_LENGTH_PERC));
    txtDescrizione.setDocument(new JTextFieldLimit(MAX_LENGTH_DESCRIZIONE));
}

private void optConsegnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optConsegnaActionPerformed
// TODO add your handling code here:
    optRitiro.setSelected(false);
}//GEN-LAST:event_optConsegnaActionPerformed

private void optRitiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optRitiroActionPerformed
// TODO add your handling code here:
    optConsegna.setSelected(false);
}//GEN-LAST:event_optRitiroActionPerformed

private void txtValMerceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMerceFocusLost
// TODO add your handling code here:
    calcolaProvvigione();
    calcolaTotale();
}//GEN-LAST:event_txtValMerceFocusLost

private void popolaSelect(List items) {
    cboMezzo.addItem(new Mezzo(null, "Vettore", null));
    for (Object mezzo : items)
        cboMezzo.addItem((Mezzo)mezzo);
}
    
private void calcolaImporto(){
    double traz = 0.0;
    double distrib = 0.0;
    int qta = 0;
    if (!(txtTrazione.getText().equals("")))
        try {
            traz = Double.parseDouble(txtTrazione.getText());
        } catch (NumberFormatException e) {}
        
    
    if (!(txtDistribuzione.getText().equals("")))
        try {
            distrib = Double.parseDouble(txtDistribuzione.getText());
        } catch (NumberFormatException e) {}
        
    
    if (!(txtQuantita.getText().equals("")))
        try {
            qta = Integer.parseInt(txtQuantita.getText());
        } catch (NumberFormatException e) {}
    
    double importo = (qta * traz) + (qta * distrib);
    txtImporto.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(importo)));
}

private void calcolaSconto(){
    int sconto = 0;
    double importo = 0.0;
    if (!(txtPercSconto.getText().equals(""))) {
        try {
            sconto = Integer.parseInt(txtPercSconto.getText());
        } catch (NumberFormatException e) {}
        
        if (!(txtImporto.getText().equals("")))
            try {
                importo = Double.parseDouble(txtImporto.getText());
            } catch (NumberFormatException e) {}
    }
    
    double importoSconto = (importo * sconto) / 100.0;
    double impScontato = importo - importoSconto;
    txtImpScontato.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(impScontato)));
    txtImpSconto.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(importoSconto)));
}

private void calcolaProvvigione() {
    double valMerce = 0.0;
    double percProv = 0;
    
    if (!(txtValMerce.getText().equals(""))) {
        try {
            valMerce = Double.parseDouble(txtValMerce.getText());
        } catch (NumberFormatException e) {}
        
         if (!(txtPercProv.getText().equals(""))) 
             try {
                 percProv = Double.parseDouble(txtPercProv.getText());
             } catch (NumberFormatException e) {}      
    }
    
    double provvigione = (valMerce * percProv) / 100.0;
    txtImpProv.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(provvigione)));
}

private void calcolaTotale(){
    int percIva = PERC_IVA;
    try {
        percIva = Integer.parseInt(txtPercIva.getText());
    } catch (NumberFormatException e) {}
    
    double importo = 0.0;
    
    if (!(txtImpScontato.getText().equals("")) && (!((Double.parseDouble(txtImpScontato.getText())) == 0.0)))
        try {
            importo = Double.parseDouble(txtImpScontato.getText());
        } catch (NumberFormatException e) {}
    
    else {
        try {
            importo = Double.parseDouble(txtImporto.getText());
        } catch (NumberFormatException e) {}
        
        txtImpScontato.setText(Double.toString(DoubleFormatter.roundTwoDecimals(importo)));
        txtPercSconto.setText("0");
        txtImpSconto.setText("0.0");
    }
    
    String provvigione = txtImpProv.getText();
    
    double imponibile;
    if (provvigione.isEmpty())
        imponibile = importo;
    else
        try {
            imponibile = importo + Double.parseDouble(provvigione);
        } catch (NumberFormatException e) {
            imponibile = importo;
        }
    
    double iva = (imponibile * percIva) / 100.0;
    double totale = imponibile + iva;
    txtImponibile.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(imponibile)));
    txtImpIva.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(iva)));
    txtTotale.setText(String.valueOf(DoubleFormatter.roundTwoDecimals(totale)));
}

double getTotale() {
    try {
        return Double.parseDouble(txtTotale.getText());
    } catch (NumberFormatException e) {
        return 0.0;
    }
}

Fornitore getCliente() {
    return fornitore;
}

private void pulisciText(){
    txtAnnoCarico.setText(null);
    txtAnnoDocumento.setText(null);
    txtDescrizione.setText(null);
    txtDistribuzione.setText(null);
    txtGiornoCarico.setText(null);
    txtGiornoDocumento.setText(null);
    txtImpIva.setText(null);
    txtImpProv.setText(null);
    txtImpSconto.setText(null);
    txtImpScontato.setText(null);
    txtImporto.setText(null);
    txtMeseCarico.setText(null);
    txtMeseDocumento.setText(null);
    txtNote.setText(null);
    //txtNumSpedizione.setText(String.valueOf(FrontController.getNumber(Spedizione.class, dataPresunta)));
    txtNumSpedizione.setText(null);
    //txtNFatt.setText(String.valueOf(FrontController.getNumber(Fattura.class, dataPresunta)));
    txtPercIva.setText(String.valueOf(PERC_IVA));
    txtPercProv.setText(null);
    txtQuantita.setText(null);
    txtTotale.setText(null);
    txtTrazione.setText(null);
    cboUm.setSelectedIndex(0);
    txtPercSconto.setText(null);
    txtBolle.setText(null);
    txtImponibile.setText(null);
    txtValMerce.setText(null);
    cboMezzo.setSelectedIndex(0);
    optConsegna.setSelected(false);
    optRitiro.setSelected(false);
    txtGiornoCarico.requestFocus();
}

    private boolean modifica; //se true vuol dire che si sta effettuando una modifica, altrimenti un inserimento
    private int id_fornitore; //Rappresenta il fornitore di cui si sta inserendo una spedizione
    private Spedizioni parent; //Indica il jframe delle spedizioni, dal quale è stata istanziata questa (this) jdialog
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmetti;
    private javax.swing.JButton btnMemorizza;
    private javax.swing.JButton btnNuovo;
    private javax.swing.JComboBox cboMezzo;
    private javax.swing.JComboBox cboUm;
    private javax.swing.JCheckBox chkRientrata;
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
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton optConsegna;
    private javax.swing.JRadioButton optRitiro;
    private javax.swing.JPanel pnlDatiSpedizione;
    private javax.swing.JPanel pnlGenerale;
    private javax.swing.JPanel pnlNote;
    private javax.swing.JPanel pnlPrezzo;
    private javax.swing.JPanel pnlTotale;
    private javax.swing.JTextField txtAnnoCarico;
    private javax.swing.JTextField txtAnnoDocumento;
    private javax.swing.JTextField txtBolle;
    private javax.swing.JTextField txtDescrizione;
    private javax.swing.JTextField txtDistribuzione;
    private javax.swing.JTextField txtGiornoCarico;
    private javax.swing.JTextField txtGiornoDocumento;
    private javax.swing.JTextField txtImpIva;
    private javax.swing.JTextField txtImpProv;
    private javax.swing.JTextField txtImpScontato;
    private javax.swing.JTextField txtImpSconto;
    private javax.swing.JTextField txtImponibile;
    private javax.swing.JTextField txtImporto;
    private javax.swing.JTextField txtMeseCarico;
    private javax.swing.JTextField txtMeseDocumento;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtNumSpedizione;
    private javax.swing.JTextField txtPercIva;
    private javax.swing.JTextField txtPercProv;
    private javax.swing.JTextField txtPercSconto;
    private javax.swing.JTextField txtQuantita;
    private javax.swing.JTextField txtTotale;
    private javax.swing.JTextField txtTrazione;
    private javax.swing.JTextField txtValMerce;
    // End of variables declaration//GEN-END:variables
    //private Date dataPresunta;
    private Spedizione spedDaFatturare;
    private boolean spedizioneInserita = false;
    private Fornitore fornitore;
    private int rigaTabellaSpedizioni; //Il numero della riga che sto modificando nella jtable delle spedizioni
    
    private static final int PERC_IVA = 21;
    
    private static final int MAX_LENGTH_GIORNO = 2;
    private static final int MAX_LENGTH_MESE = 2;
    private static final int MAX_LENGTH_ANNO = 4;
    private static final int MAX_LENGTH_PERC = 2;
    private static final int MAX_LENGTH_DESCRIZIONE = 54;
}