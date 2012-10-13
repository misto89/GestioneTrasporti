/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InvioMail.java
 *
 * Created on 29-giu-2012, 19.41.58
 */
package viste;

import com.itextpdf.text.DocumentException;
import controllo.FrontController;
import eccezioni.EccezioneCredenzialiErrate;
import entita.Fattura;
import entita.Fornitore;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Michele
 */
public class InvioMail extends javax.swing.JDialog {
    
    /** Creates new form InvioMail */
    public InvioMail(java.awt.Frame parent, boolean modal, Fattura f) {
        super(parent, modal);
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlDest);
        color.changeColor(pnlOggetto);
        color.changeColor(pnlTesto);
        
        fattura = f;
        cliente = f.getCliente();
        chkAzienda.setText("Email aziendale: " + cliente.getEmail());
        chkRef1.setText(cliente.getNomeRef1() + ": " + cliente.getEmailRef1());
        chkRef2.setText(cliente.getNomeRef2() + ": " + cliente.getEmailRef2());
        setTitle(getTitle() + " a " + cliente);
        txtOggetto.setText("Invio fattura numero " + fattura.getNumero() + " in data " + fattura.getFormattedData());
        txtTesto.setText("Spett.le " + cliente.getNome() + ",\nin allegato fattura per la/e spedizione/i effettuata/e per vostro conto. \n\n" +
                "Cordialmente, \nFranco Rotunno & Figli s.r.l.");

        prgBar.setVisible(false);
    }
    
        public InvioMail(java.awt.Frame parent, boolean modal, List<Fattura> fatture) {
        super(parent, modal);
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(pnlDest);
        color.changeColor(pnlOggetto);
        color.changeColor(pnlTesto);
        
        cliente = fatture.get(0).getCliente();
        chkAzienda.setText("Email aziendale: " + cliente.getEmail());
        chkRef1.setText(cliente.getNomeRef1() + ": " + cliente.getEmailRef1());
        chkRef2.setText(cliente.getNomeRef2() + ": " + cliente.getEmailRef2());
        setTitle(getTitle() + " a " + cliente);
        txtOggetto.setText("Sollecito per fatture non pagate");

        String stringaFatt = "";
        for (Fattura fatt : fatture) {          
            
            stringaFatt += "NUMERO:\t" + fatt.getNumero() + "\nDATA:\t" + fatt.getFormattedData() + "\nIMPONIBILE:\t€ " + fatt.getImponibile() + 
                    "\nIVA:\t€ " + fatt.getIva() + "\nTOTALE:\t€ " + fatt.getTotale() + "\nSCADENZA:\t" + fatt.getFormattedDataScadenza() + "\n\n";
        }
        
        txtTesto.setText("Spett.le " + cliente.getNome() + ",\n\nla invitiamo al pagamento della/e seguente/i fattura/e:\n\n" + stringaFatt + 
                "Cordialmente, \nFranco Rotunno & Figli s.r.l.");
        
        prgBar.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDest = new javax.swing.JPanel();
        chkRef2 = new javax.swing.JCheckBox();
        chkRef1 = new javax.swing.JCheckBox();
        chkAzienda = new javax.swing.JCheckBox();
        pnlOggetto = new javax.swing.JPanel();
        txtOggetto = new javax.swing.JTextField();
        pnlTesto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTesto = new javax.swing.JTextArea();
        btnInvia = new javax.swing.JButton();
        prgBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invio mail");
        setResizable(false);

        pnlDest.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Destinatari", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlDestLayout = new javax.swing.GroupLayout(pnlDest);
        pnlDest.setLayout(pnlDestLayout);
        pnlDestLayout.setHorizontalGroup(
            pnlDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkRef2)
                    .addComponent(chkRef1)
                    .addComponent(chkAzienda))
                .addContainerGap(463, Short.MAX_VALUE))
        );
        pnlDestLayout.setVerticalGroup(
            pnlDestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkAzienda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkRef1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkRef2)
                .addContainerGap())
        );

        pnlOggetto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Oggetto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlOggettoLayout = new javax.swing.GroupLayout(pnlOggetto);
        pnlOggetto.setLayout(pnlOggettoLayout);
        pnlOggettoLayout.setHorizontalGroup(
            pnlOggettoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOggettoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtOggetto, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlOggettoLayout.setVerticalGroup(
            pnlOggettoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOggettoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtOggetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlTesto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Testo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtTesto.setColumns(20);
        txtTesto.setRows(5);
        jScrollPane1.setViewportView(txtTesto);

        javax.swing.GroupLayout pnlTestoLayout = new javax.swing.GroupLayout(pnlTesto);
        pnlTesto.setLayout(pnlTestoLayout);
        pnlTestoLayout.setHorizontalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTestoLayout.setVerticalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnInvia.setBackground(new java.awt.Color(255, 255, 255));
        btnInvia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.png"))); // NOI18N
        btnInvia.setText("Invia Mail");
        btnInvia.setToolTipText("Invia l'email");
        btnInvia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlOggetto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnInvia)
                        .addGap(18, 18, 18)
                        .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
                    .addComponent(pnlTesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOggetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnInvia))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnInviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviaActionPerformed
// TODO add your handling code here:
    boolean checked = false;
    List<String> to = new LinkedList<String>();
    
    if (chkAzienda.isSelected()){
        checked = true;
        to.add(cliente.getEmail());
    }
    
    if (chkRef1.isSelected()){
        checked = true;
        to.add(cliente.getEmailRef1());
    }
    
    if (chkRef2.isSelected()){
        checked = true;
        to.add(cliente.getEmailRef2());
    }
    
    if (!checked){
        JOptionPane.showMessageDialog(this, "Selezionare almeno un destinatario!", "Destinatario mancante", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (fattura != null) {
        try {
            new MailProgressBarManager(prgBar, to, txtOggetto.getText(), txtTesto.getText(), new stampa.StampaFattura(fattura, cliente, true).printAndGet(), this).actionPerformed(null);
        } catch (DocumentException ex) {
            Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    } else {
        new MailProgressBarManager(prgBar, to, txtOggetto.getText(), txtTesto.getText(), this).actionPerformed(null);
        
    }
           
}//GEN-LAST:event_btnInviaActionPerformed

void showResultSendingMail(boolean success, String msg) {
    if (success) {
        JOptionPane.showMessageDialog(this, "E-mail inviata con successo!", "Esito invio", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    } else if (msg == null)
        JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'invio della mail", "Errore", JOptionPane.ERROR_MESSAGE);
    else
        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvia;
    private javax.swing.JCheckBox chkAzienda;
    private javax.swing.JCheckBox chkRef1;
    private javax.swing.JCheckBox chkRef2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlDest;
    private javax.swing.JPanel pnlOggetto;
    private javax.swing.JPanel pnlTesto;
    private javax.swing.JProgressBar prgBar;
    private javax.swing.JTextField txtOggetto;
    private javax.swing.JTextArea txtTesto;
    // End of variables declaration//GEN-END:variables
    
    private Fattura fattura;
    private Fornitore cliente;
    
}

class MailProgressBarManager implements ActionListener, PropertyChangeListener {

    private JProgressBar progressBar;
    private Task task;
    private List<String> to;
    private String subject;
    private String text;
    private File allegato;
    private boolean done = false;
    private InvioMail dialogMail;
    private boolean success;
    private String msg = null;
    
    class Task extends SwingWorker<Void, Void> {
        
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            
            try {
                if (allegato != null) {
                    if (FrontController.sendMail(to, subject, text, allegato))
                        success = true;
                    else
                        success = false;
                } else {
                    if (FrontController.sendMail(to, subject, text))
                        success = true;
                    else
                        success = false;
                }  

            } catch (AddressException ex) {
                Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
                success = false;

            } catch (MessagingException ex) {
                Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
                success = false;
                
            } catch (EccezioneCredenzialiErrate e) {
                msg = e.getMessage();
                success = false;                
            }
            
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            done = true;
            Toolkit.getDefaultToolkit().beep();
            progressBar.setCursor(null);
            progressBar.setValue(progressBar.getMinimum());
            progressBar.setIndeterminate(false);
            progressBar.setString(null);
            progressBar.setValue(0);
            setProgress(0);
            progressBar.setVisible(false);
            dialogMail.showResultSendingMail(success, msg);
        }
    }

    public MailProgressBarManager(JProgressBar bar, List<String> to, String subject, String text, File allegato, InvioMail dialog) {    
        this(bar, to, subject, text, dialog);
        this.allegato = allegato;
    }
    
    public MailProgressBarManager(JProgressBar bar, List<String> to, String subject, String text, InvioMail dialog) {
        progressBar = bar;
        this.to = to;
        this.subject = subject;
        this.text = text;
        dialogMail = dialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
        progressBar.setVisible(true);
        progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!done) {
            int progress = task.getProgress();
            if (progress == 0) {
                progressBar.setIndeterminate(true);

            } else {
                progressBar.setIndeterminate(false); 
                progressBar.setString(null);
                progressBar.setValue(progress);

            }
        }
    }
    
}
