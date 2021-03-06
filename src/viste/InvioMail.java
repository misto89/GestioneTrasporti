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
import entita.NotaCredito;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        
        fattura = f;        
        cliente = f.getCliente();

        setColorAndEmailList();                
        
        String entitaStampa = "";
        
        if (fattura instanceof NotaCredito){
            entitaStampa = "nota credito";
            testo = "Spett.le <span style='font-weight:bold'>" + cliente.getNome()+ "</span>,<br />in allegato nota di credito per storno.<br /><br />";
        } else {
            entitaStampa = "fattura";
            testo = "Spett.le <span style='font-weight:bold'>" + cliente.getNome()+ "</span>,<br />in allegato fattura per la/e spedizione/i effettuata/e per vostro conto. <br /><br />";
        }
        
        testo += "Cordialmente, <br />Franco Rotunno & Figli s.r.l.";
        
        setTitle(getTitle() + " a " + cliente);
        txtOggetto.setText("Invio " + entitaStampa + " numero " + fattura.getNumero() + " in data " + fattura.getFormattedData());               
        txtTesto.setText(testo);
        prgBar.setVisible(false);
    }
    
    public InvioMail(java.awt.Frame parent, boolean modal, List<Fattura> fatture) {
        super(parent, modal);
        initComponents();
                
        cliente = fatture.get(0).getCliente();
       
        setColorAndEmailList();
        Double totale = 0.0;
        setTitle(getTitle() + " a " + cliente);
        txtOggetto.setText("Sollecito di pagamento");

        String stringaFatt = "<table>"+
                                "<tr>"+
                                   "<th>NUMERO</th>"+
                                   "<th>DATA</th>"+
                                   "<th>TOTALE</th>"+
                                "</tr>";
        for (Fattura fatt : fatture) {          
            
            //stringaFatt += "NUMERO:\t" + fatt.getNumero() + "\nDATA:\t" + fatt.getFormattedData() + "\nIMPONIBILE:\t€ " + fatt.getImponibile() + 
                   // "\nIVA:\t€ " + fatt.getIva() + "\nTOTALE:</p>\t€ " + fatt.getTotale() + "\nSCADENZA:\t" + fatt.getFormattedDataScadenza() + "<br /><br />";
            //Fatture in formato table html
            stringaFatt += "<tr>"+
                            "<td>"+fatt.getNumero()+"</td>"+
                            "<td>"+fatt.getFormattedData()+"</td>"+
                            "<td style='border:1px solid black;text-align:right'>&euro;   "+String.format("%1$,.2f", fatt.getTotale())+"</td>"+
                          "</tr>";
            totale += fatt.getTotale();
        }
        if (fatture.size() > 1) {
            stringaFatt += "<tr id='tot'>" + 
                                "<td colspan='2' style='text-align:left'>Totale Complessivo</td>" +
                                "<td style='text-align:right'>&euro;   "+String.format("%1$,.2f", totale) + "</td>" +
                           "</tr>";
        }
        
        stringaFatt += "</table><br />";
        testo = "Spett.le <span style='font-weight:bold'>" + cliente.getNome()+ "</span>"+
                            ",<br />dal controllo della vostra partita contabile, risultano scadute le fatture di seguito elencate:<br /><br />"+
                            stringaFatt +
                            "Vi preghiamo di voler cortesemente provvedere al saldo tramite bonifico bancario:<br /><br />"+
                            "<span style='font-weight:bold'>COORDINATE BANCARIE:<br />"+
                            "IBAN: IT84O0538541471000001449453</span><br /><br />" +
                            "Nel caso aveste nel frattempo già provveduto, vogliate ritenere nullo il presente sollecito.<br /><br />"+
                            "Distinti saluti,<br />"+
                            "Franco Rotunno & Figli S.r.l.";
                        
        txtTesto.setText(testo);
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
        txtTesto = new javax.swing.JEditorPane();
        btnInvia = new javax.swing.JButton();
        prgBar = new javax.swing.JProgressBar();
        pnlConoscenza = new javax.swing.JPanel();
        txtConoscenza = new javax.swing.JTextField();

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
                .addContainerGap(519, Short.MAX_VALUE))
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
                .addComponent(txtOggetto, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
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

        txtTesto.setContentType("text/html");
        jScrollPane1.setViewportView(txtTesto);

        javax.swing.GroupLayout pnlTestoLayout = new javax.swing.GroupLayout(pnlTesto);
        pnlTesto.setLayout(pnlTestoLayout);
        pnlTestoLayout.setHorizontalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTestoLayout.setVerticalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
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

        pnlConoscenza.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlConoscenzaLayout = new javax.swing.GroupLayout(pnlConoscenza);
        pnlConoscenza.setLayout(pnlConoscenzaLayout);
        pnlConoscenzaLayout.setHorizontalGroup(
            pnlConoscenzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConoscenzaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtConoscenza, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlConoscenzaLayout.setVerticalGroup(
            pnlConoscenzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConoscenzaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtConoscenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlOggetto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDest, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnInvia)
                        .addGap(18, 18, 18)
                        .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))
                    .addComponent(pnlConoscenza, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlDest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlConoscenza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlOggetto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlTesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(prgBar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnInvia))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void setColorAndEmailList() {
    ColorManager color = new ColorManager();
    color.changeColor(pnlDest);
    color.changeColor(pnlOggetto);
    color.changeColor(pnlTesto);
    color.changeColor(pnlConoscenza);

    String email = cliente.getEmail();
    String emailRef1 = cliente.getEmailRef1();
    String emailRef2 = cliente.getEmailRef2();

    String nomeRef1 = cliente.getNomeRef1();
    String nomeRef2 = cliente.getNomeRef2();

    if (email == null) {
        email = "Nessuna e-mail aziendale presente";
        chkAzienda.setEnabled(false);
    } else {
        email = "E-mail aziendale: " + email;
    }

    chkAzienda.setText(email);

    if (nomeRef1 == null) {
        nomeRef1 = "Referente 1";
    }

    if (emailRef1 == null) {
        emailRef1 = "Nessuna e-mail presente";
        chkRef1.setEnabled(false);
    }

    chkRef1.setText(nomeRef1 + ": " + emailRef1);

    if (nomeRef2 == null) {
        nomeRef2 = "Referente 2";
    }

    if (emailRef2 == null) {
        emailRef2 = "Nessuna e-mail presente";
        chkRef2.setEnabled(false);
    }

    chkRef2.setText(nomeRef2 + ": " + emailRef2);

}    
    
private void btnInviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviaActionPerformed
// TODO add your handling code here:
    //Replace dei tag html in modo da aggiungere lo stile voluto
    testo = txtTesto.getText();
    testo = testo.replaceAll("<table>", "<table style='border:1px solid black;border-collapse:collapse;font-weight:bold'>");
    testo = testo.replaceAll("<th>", "<th style='font-weight:bold;background-color:blue;color:white;border:1px solid black;width:120px'>");
    testo = testo.replaceAll("<td>", "<td style='border:1px solid black;text-align:center'>");
    testo = testo.replaceAll("<tr id=\"tot\">","<tr style='border-top:2px solid black'>");
    
    boolean checked = false;
    List<String> to = new LinkedList<String>();
    String cc = null;
    
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
    
    if (!txtConoscenza.getText().isEmpty())
        if (checkEmail(txtConoscenza.getText()))
            cc = txtConoscenza.getText();
        else {
            JOptionPane.showMessageDialog(this, "Campo Cc: formato mail non valido", "Email non valida", JOptionPane.ERROR_MESSAGE);
            txtConoscenza.requestFocus();
            return;
        }
    
    if (!checked){
        JOptionPane.showMessageDialog(this, "Selezionare almeno un destinatario!", "Destinatario mancante", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    if (fattura != null) {
        try {
            if (fattura instanceof NotaCredito)
                new MailProgressBarManager(prgBar, to, cc, txtOggetto.getText(), testo, new stampa.StampaNotaCredito((NotaCredito) fattura, cliente, true).printAndGet(), this).actionPerformed(null);
            else
                new MailProgressBarManager(prgBar, to, cc, txtOggetto.getText(), testo, new stampa.StampaFattura(fattura, cliente, true).printAndGet(), this).actionPerformed(null);
            
        } catch (DocumentException ex) {
            Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InvioMail.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    } else {
        new MailProgressBarManager(prgBar, to, cc, txtOggetto.getText(), testo, this).actionPerformed(null);
        
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

private boolean checkEmail(String email) {
    Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
    Matcher match = pattern.matcher(email);
    return match.matches();
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvia;
    private javax.swing.JCheckBox chkAzienda;
    private javax.swing.JCheckBox chkRef1;
    private javax.swing.JCheckBox chkRef2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlConoscenza;
    private javax.swing.JPanel pnlDest;
    private javax.swing.JPanel pnlOggetto;
    private javax.swing.JPanel pnlOggetto1;
    private javax.swing.JPanel pnlOggetto2;
    private javax.swing.JPanel pnlTesto;
    private javax.swing.JProgressBar prgBar;
    private javax.swing.JTextField txtConoscenza;
    private javax.swing.JTextField txtConoscenza1;
    private javax.swing.JTextField txtConoscenza2;
    private javax.swing.JTextField txtOggetto;
    private javax.swing.JEditorPane txtTesto;
    // End of variables declaration//GEN-END:variables
    
    private Fattura fattura;
    private Fornitore cliente;
    private String testo;
    
}

class MailProgressBarManager implements ActionListener, PropertyChangeListener {

    private JProgressBar progressBar;
    private Task task;
    private List<String> to;
    private String cc;
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
                    if (FrontController.sendMail(to, cc, subject, text, allegato))
                        success = true;
                    else
                        success = false;
                } else {
                    if (FrontController.sendMail(to, cc, subject, text))
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

    public MailProgressBarManager(JProgressBar bar, List<String> to, String cc, String subject, String text, File allegato, InvioMail dialog) {    
        this(bar, to, cc, subject, text, dialog);
        this.allegato = allegato;
    }
    
    public MailProgressBarManager(JProgressBar bar, List<String> to, String cc, String subject, String text, InvioMail dialog) {
        progressBar = bar;
        this.to = to;
        this.cc = cc;
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