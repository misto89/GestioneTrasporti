/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Main.java
 *
 * Created on 14-apr-2012, 17.14.55
 */
package viste;

import controllo.FrontController;
import eccezioni.EccezioneConnesioneNonRiuscita;
import eccezioni.EccezioneUtenteNonValido;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Andle
 */
public class Main extends javax.swing.JFrame {

    /** Creates new form Main */
    public Main() {
        initComponents();
        ColorManager color = new ColorManager();
        color.changeColor(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFatturazione = new javax.swing.JButton();
        btnAnagFornitoriClienti = new javax.swing.JButton();
        btnMezzi = new javax.swing.JButton();
        btnFattureEmesse = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txtContabilita = new javax.swing.JButton();
        txtCassa = new javax.swing.JButton();
        btnScadenziario = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        mnuStrumenti = new javax.swing.JMenu();
        mnuTest = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mnuBackup = new javax.swing.JMenuItem();
        mnuRestore = new javax.swing.JMenuItem();
        mnuEliminaFileTemp = new javax.swing.JMenuItem();
        mnuInt = new javax.swing.JMenu();
        mnuInfo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestione trasporti");
        setName("main"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnFatturazione.setBackground(new java.awt.Color(255, 255, 255));
        btnFatturazione.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/spedizioni.png"))); // NOI18N
        btnFatturazione.setText("Spedizioni");
        btnFatturazione.setIconTextGap(1);
        btnFatturazione.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnFatturazione.setMaximumSize(new java.awt.Dimension(161, 23));
        btnFatturazione.setMinimumSize(new java.awt.Dimension(161, 23));
        btnFatturazione.setPreferredSize(new java.awt.Dimension(161, 23));
        btnFatturazione.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFatturazioneActionPerformed(evt);
            }
        });

        btnAnagFornitoriClienti.setBackground(new java.awt.Color(255, 255, 255));
        btnAnagFornitoriClienti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fornclienti.png"))); // NOI18N
        btnAnagFornitoriClienti.setText("Anagrafica Fornitori/Clienti");
        btnAnagFornitoriClienti.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAnagFornitoriClienti.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        btnAnagFornitoriClienti.setIconTextGap(3);
        btnAnagFornitoriClienti.setMargin(new java.awt.Insets(2, -3, 2, 14));
        btnAnagFornitoriClienti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnagFornitoriClientiActionPerformed(evt);
            }
        });

        btnMezzi.setBackground(new java.awt.Color(255, 255, 255));
        btnMezzi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/automezzi.png"))); // NOI18N
        btnMezzi.setText("Anagrafica Automezzi");
        btnMezzi.setMaximumSize(new java.awt.Dimension(161, 23));
        btnMezzi.setMinimumSize(new java.awt.Dimension(161, 23));
        btnMezzi.setPreferredSize(new java.awt.Dimension(161, 23));
        btnMezzi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMezziActionPerformed(evt);
            }
        });

        btnFattureEmesse.setBackground(new java.awt.Color(255, 255, 255));
        btnFattureEmesse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/regfattemesse.png"))); // NOI18N
        btnFattureEmesse.setText("Registro Fatture emesse");
        btnFattureEmesse.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnFattureEmesse.setMaximumSize(new java.awt.Dimension(161, 23));
        btnFattureEmesse.setMinimumSize(new java.awt.Dimension(161, 23));
        btnFattureEmesse.setPreferredSize(new java.awt.Dimension(161, 23));
        btnFattureEmesse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFattureEmesseActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/registrofattacquisto.png"))); // NOI18N
        jButton1.setText("Registro Fatture acquisto");
        jButton1.setMargin(new java.awt.Insets(2, -10, 2, 14));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtContabilita.setBackground(new java.awt.Color(255, 255, 255));
        txtContabilita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contabilit.png"))); // NOI18N
        txtContabilita.setText("Contabilità");
        txtContabilita.setMargin(new java.awt.Insets(2, -10, 2, 14));
        txtContabilita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContabilitaActionPerformed(evt);
            }
        });

        txtCassa.setBackground(new java.awt.Color(255, 255, 255));
        txtCassa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cassa.png"))); // NOI18N
        txtCassa.setText("Cassa");
        txtCassa.setMargin(new java.awt.Insets(2, -10, 2, 14));
        txtCassa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCassaActionPerformed(evt);
            }
        });

        btnScadenziario.setBackground(new java.awt.Color(255, 255, 255));
        btnScadenziario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/scadenziario.png"))); // NOI18N
        btnScadenziario.setText("Scadenziario");
        btnScadenziario.setMargin(new java.awt.Insets(2, -10, 2, 14));
        btnScadenziario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScadenziarioActionPerformed(evt);
            }
        });

        mnuStrumenti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/strumenti.png"))); // NOI18N
        mnuStrumenti.setText("Strumenti");

        mnuTest.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnuTest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/databasetest.png"))); // NOI18N
        mnuTest.setText("Test connessione database");
        mnuTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTestActionPerformed(evt);
            }
        });
        mnuStrumenti.add(mnuTest);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/database.png"))); // NOI18N
        jMenu1.setText("Database");

        mnuBackup.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        mnuBackup.setText("Backup");
        mnuBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBackupActionPerformed(evt);
            }
        });
        jMenu1.add(mnuBackup);

        mnuRestore.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuRestore.setText("Ripristino");
        mnuRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRestoreActionPerformed(evt);
            }
        });
        jMenu1.add(mnuRestore);

        mnuStrumenti.add(jMenu1);

        mnuEliminaFileTemp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        mnuEliminaFileTemp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancella.png"))); // NOI18N
        mnuEliminaFileTemp.setText("Elimina file temporanei");
        mnuEliminaFileTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEliminaFileTempActionPerformed(evt);
            }
        });
        mnuStrumenti.add(mnuEliminaFileTemp);

        barraMenu.add(mnuStrumenti);

        mnuInt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/info.png"))); // NOI18N
        mnuInt.setText("?");

        mnuInfo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mnuInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/informazioni.png"))); // NOI18N
        mnuInfo.setText("Informazioni");
        mnuInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInfoActionPerformed(evt);
            }
        });
        mnuInt.add(mnuInfo);

        barraMenu.add(mnuInt);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMezzi, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(btnAnagFornitoriClienti, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(btnFatturazione, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtContabilita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFattureEmesse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCassa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnScadenziario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAnagFornitoriClienti, btnFatturazione, btnFattureEmesse, btnMezzi, btnScadenziario, jButton1, txtCassa, txtContabilita});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFattureEmesse, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCassa, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnScadenziario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAnagFornitoriClienti, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnMezzi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFatturazione, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContabilita, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnMezzi, jButton1});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAnagFornitoriClienti, btnFattureEmesse});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnFatturazione, txtCassa, txtContabilita});

        btnAnagFornitoriClienti.getAccessibleContext().setAccessibleDescription("Anagrafica Fornitori/Clienti");

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-750)/2, (screenSize.height-256)/2, 750, 256);
    }// </editor-fold>//GEN-END:initComponents

private void mnuInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInfoActionPerformed
// TODO add your handling code here:
    FrontController.open(new Info(this, false));
}//GEN-LAST:event_mnuInfoActionPerformed

private void btnFatturazioneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFatturazioneActionPerformed
// TODO add your handling code here:
    FrontController.open(new Spedizioni());
}//GEN-LAST:event_btnFatturazioneActionPerformed

private void btnAnagFornitoriClientiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnagFornitoriClientiActionPerformed
// TODO add your handling code here:
    FrontController.open(new Fornitori_Clienti());
}//GEN-LAST:event_btnAnagFornitoriClientiActionPerformed

private void btnMezziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMezziActionPerformed
// TODO add your handling code here:
    FrontController.open(new Mezzi());
}//GEN-LAST:event_btnMezziActionPerformed

private void mnuTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTestActionPerformed
// TODO add your handling code here:
    try {
        FrontController.test();            
        JOptionPane.showMessageDialog(null, "Test di connessione eseguito con successo!", "Connessione riuscita", JOptionPane.INFORMATION_MESSAGE);
    } catch (EccezioneConnesioneNonRiuscita e) {
        JOptionPane.showMessageDialog(null, "Test di connessione fallito!\n" + e.getMessage(), "Connessione non riuscita", JOptionPane.ERROR_MESSAGE);
    }

}//GEN-LAST:event_mnuTestActionPerformed

private void btnFattureEmesseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFattureEmesseActionPerformed
// TODO add your handling code here:
    FrontController.open(new RegistroFattureEmesse());
}//GEN-LAST:event_btnFattureEmesseActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
    FrontController.open(new RegistroFattureAcquisto());
}//GEN-LAST:event_jButton1ActionPerformed

private void txtContabilitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContabilitaActionPerformed
// TODO add your handling code here:
    FrontController.open(new Contabilita());
}//GEN-LAST:event_txtContabilitaActionPerformed

private void txtCassaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCassaActionPerformed
// TODO add your handling code here:
    FrontController.open(new Cassa());
}//GEN-LAST:event_txtCassaActionPerformed

private void btnScadenziarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScadenziarioActionPerformed
// TODO add your handling code here:
    FrontController.open(new Scadenziario());
}//GEN-LAST:event_btnScadenziarioActionPerformed

private void mnuBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBackupActionPerformed
// TODO add your handling code here:
    final int RESPONSE = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare un backup del database?", "Conferma backup", JOptionPane.OK_CANCEL_OPTION);
    if (RESPONSE == JOptionPane.OK_OPTION) {
        try {
            FrontController.test();            
                try {
                    String file = FrontController.backup();
                    JOptionPane.showMessageDialog(this, "Backup del database eseguito con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                    Desktop.getDesktop().open(new File(file));
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "File non trovato", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EccezioneUtenteNonValido e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Privilegi insufficienti", JOptionPane.ERROR_MESSAGE);
                }

        } catch (EccezioneConnesioneNonRiuscita e) {
            JOptionPane.showMessageDialog(null, "Impossibile collegarsi al database!\n" + e.getMessage(), "Connessione non riuscita", JOptionPane.ERROR_MESSAGE);
        }
    }
}//GEN-LAST:event_mnuBackupActionPerformed

private void mnuRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRestoreActionPerformed
// TODO add your handling code here:   
    FrontController.open(new FileChooser(this, rootPaneCheckingEnabled));
}//GEN-LAST:event_mnuRestoreActionPerformed

private void mnuEliminaFileTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEliminaFileTempActionPerformed
// TODO add your handling code here:
    if (FrontController.deleteTempFiles())
        JOptionPane.showMessageDialog(this, "Pulizia effettuata con successo!", "Esito", JOptionPane.INFORMATION_MESSAGE);
    else
        JOptionPane.showMessageDialog(this, "Si è verificato un errore durante l'eliminazione!", "Esito", JOptionPane.ERROR_MESSAGE);
}//GEN-LAST:event_mnuEliminaFileTempActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        final long HOURS_24 = 86400000; //24 ore (in millisecondi)
        
        File[] dumpFiles;
        try {
            dumpFiles = FrontController.getAllDumpFiles();
            if (dumpFiles == null)
                return;
            
            Arrays.sort(dumpFiles, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    File f1 = (File) o1;
                    File f2 = (File) o2;
                    
                    //Ordina dal più recente al meno recente
                    return new Long(f2.lastModified()).compareTo(new Long(f1.lastModified()));
                }
                
            });
            
            File lastDump = dumpFiles[0]; //Dump più recente            
            java.util.Date now = new java.util.Date(); //Tempo corrente   
            long timePassed = now.getTime() - lastDump.lastModified(); //Tempo trascorso fra l'ultimo dump al timestamp corrente (in millisecondi)
            
            if (timePassed > HOURS_24) { //Se sono trascorse più di 24 ore, effettua un dump automatico
                try {
                    FrontController.test();            
                    try {
                        FrontController.backup();
                    } 
                    catch (FileNotFoundException e) {} 
                    catch (IOException ex) {} 
                    catch (EccezioneUtenteNonValido e) {}

                } catch (EccezioneConnesioneNonRiuscita e) {}
            }
            
        } catch (EccezioneUtenteNonValido e) {}
    }//GEN-LAST:event_formWindowOpened

private static void redirectOutErr() throws FileNotFoundException {
    File dirLog = new File("log");
    if (!dirLog.exists())
        dirLog.mkdir();
    
    java.util.Date date = new java.util.Date();
    String timeStamp = new Timestamp(date.getTime()).toString();
    timeStamp = timeStamp.replaceAll(" ", "");
    timeStamp = timeStamp.replaceAll("-", "");
    timeStamp = timeStamp.replaceAll(":", "");
    timeStamp = timeStamp.replaceAll("\\.", "");  

    File current = new File(dirLog, timeStamp);
    current.mkdir();

    PrintStream out = new PrintStream(new FileOutputStream(new File(current, "out.txt")));
    PrintStream err = new PrintStream(new FileOutputStream(new File(current, "err.txt")));

    System.setOut(out);
    System.setErr(err);
   
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws FileNotFoundException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        redirectOutErr();
        
        try {
            FrontController.test();
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    Main main = new Main();
                    main.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("img/icon.png")).getImage());
                    main.setVisible(true);
                }
            
            });

        } catch (EccezioneConnesioneNonRiuscita e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\nImpossibile avviare il programma.", "Avvio non riuscito", JOptionPane.ERROR_MESSAGE);
        }        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnAnagFornitoriClienti;
    private javax.swing.JButton btnFatturazione;
    private javax.swing.JButton btnFattureEmesse;
    private javax.swing.JButton btnMezzi;
    private javax.swing.JButton btnScadenziario;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem mnuBackup;
    private javax.swing.JMenuItem mnuEliminaFileTemp;
    private javax.swing.JMenuItem mnuInfo;
    private javax.swing.JMenu mnuInt;
    private javax.swing.JMenuItem mnuRestore;
    private javax.swing.JMenu mnuStrumenti;
    private javax.swing.JMenuItem mnuTest;
    private javax.swing.JButton txtCassa;
    private javax.swing.JButton txtContabilita;
    // End of variables declaration//GEN-END:variables
}