/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Mezzi.java
 *
 * Created on 16-apr-2012, 11.00.06
 */
package viste;

import com.itextpdf.text.DocumentException;
import controllo.FrontController;
import eccezioni.EccezioneChiaveDuplicata;
import entita.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import stampa.StampaMezzi;

/**
 *
 * @author Andle
 */
public class Mezzi extends javax.swing.JFrame {

    /** Creates new form Mezzi */
    public Mezzi() {
        initComponents();
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
        tblMezzi = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblTarga = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        btnNuovo = new javax.swing.JButton();
        btnSalva = new javax.swing.JButton();
        btnElimina = new javax.swing.JButton();
        lblTarga1 = new javax.swing.JLabel();
        txtTarga = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuFiltra = new javax.swing.JMenu();
        mnuTutti = new javax.swing.JCheckBoxMenuItem();
        mnuTarga = new javax.swing.JCheckBoxMenuItem();
        mnuProspetto = new javax.swing.JMenu();
        mnuStampa = new javax.swing.JMenuItem();

        setTitle("Anagrafica automezzi propri");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tblMezzi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblMezzi);

        lblTarga.setText("Marca");
        lblTarga.setToolTipText("");

        btnNuovo.setText("Nuovo");
        btnNuovo.setToolTipText("Inserisci un nuovo fornitore");
        btnNuovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuovoActionPerformed(evt);
            }
        });

        btnSalva.setText("Salva");
        btnSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvaActionPerformed(evt);
            }
        });

        btnElimina.setText("Elimina");
        btnElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaActionPerformed(evt);
            }
        });

        lblTarga1.setText("Targa");
        lblTarga1.setToolTipText("");

        mnuFiltra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filtra.png"))); // NOI18N
        mnuFiltra.setText("Filtra");

        mnuTutti.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        mnuTutti.setSelected(true);
        mnuTutti.setText("Mostra tutti");
        mnuTutti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mostratutto.png"))); // NOI18N
        mnuTutti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTuttiActionPerformed(evt);
            }
        });
        mnuFiltra.add(mnuTutti);

        mnuTarga.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnuTarga.setSelected(true);
        mnuTarga.setText("Per Targa");
        mnuTarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filtrotarga.png"))); // NOI18N
        mnuTarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuTargaActionPerformed(evt);
            }
        });
        mnuFiltra.add(mnuTarga);

        jMenuBar1.add(mnuFiltra);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuovo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalva, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTarga1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTarga, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMarca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTarga1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTarga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuovo)
                    .addComponent(btnSalva)
                    .addComponent(btnElimina))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
 * Questo metodo costruisce e popola una tabella con i valori presenti nel parametro. 
 */
private void popolaTabella(List mezzi) {
    Object[] arMezzi = mezzi.toArray();
    Object[][] arrayMezzi = new Object[arMezzi.length][Mezzo.NUM_CAMPI];
    mezziInTabella = new LinkedList<Mezzo>();
    int cont = 0;
    
    for (Object mezzo : arMezzi) {
        arrayMezzi[cont] = ((Mezzo) mezzo).toArray();
        mezziInTabella.add((Mezzo) mezzo);
        cont++;
    }
    
    DefaultTableModel model = new javax.swing.table.DefaultTableModel(
        arrayMezzi,
            
        new String [] {
                "TARGA", "MARCA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            Class[] types = {String.class, String.class};
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            
            
    };
    
    /*
     * Crea la tabella
     */
    tblMezzi.setModel(model);
//    tblMezzi.setRowSorter(new TableRowSorter(model));
    
    //Le seguenti costanti indicano i numeri di colonna dei campi
    final int TARGA = 0;
    final int MARCA = 1;

    jScrollPane1.setViewportView(tblMezzi);
    tblMezzi.getColumnModel().getColumn(TARGA).setResizable(false);
    tblMezzi.getColumnModel().getColumn(MARCA).setResizable(false);

    tblMezzi.getTableHeader().setReorderingAllowed(false); //Fa in modo che l'utente non possa modificare l'ordine delle colonne
    tblMezzi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    /*
     * Aggiunge un evento alla tabella. In particolare, al click su una cella della tabella,
     * visualizza i valori corrispondenti ai campi della riga selezionata nelle rispettive text.
     */
    tblMezzi.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent me) {
            //Estrae dalla tabella i valori dei relativi campi
            String targa = (String) tblMezzi.getValueAt(tblMezzi.getSelectedRow(), TARGA);
            String marca = (String) tblMezzi.getValueAt(tblMezzi.getSelectedRow(), MARCA);

            //Imposta le text con i valori estratti dalla tabella
            txtTarga.setText(targa);
            txtMarca.setText(marca);

        }
    });
}  
    
/**
  * Questo metodo, che corrisponde all'evento che si innesca con l'apertura del frame, recupera l'elenco di tutte le targhe e
  * popola una tabella con questo elenco.
  */
private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// TODO add your handling code here:
    mnuTarga.setState(false);
    mnuTutti.setState(true);
    pulisciText();
    List<Entity> mezzi = FrontController.getAnagrafe(Mezzo.class);
    popolaTabella(mezzi);
}//GEN-LAST:event_formWindowOpened

/**
 * Questo evento si verifica alla pressione del tasto Nuovo
 */
private void btnNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuovoActionPerformed
// TODO add your handling code here:
    pulisciText();
}//GEN-LAST:event_btnNuovoActionPerformed

/**
 * Questo evento si verifica alla pressione del tasto per il salvataggio.
 * @param evt 
 */
private void btnSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvaActionPerformed
// TODO add your handling code here:
    String targa = txtTarga.getText().toUpperCase();
    String marca = txtMarca.getText();
    
    Integer ID = null;
    try {
        ID = mezziInTabella.get(tblMezzi.getSelectedRow()).getId();
        
    } catch (IndexOutOfBoundsException e) {}
       
    
    /*
     * Vengono effettuati dei controlli sui valori inseriti, mostrando degli opportuni messaggi nel caso in cui
     * tali valori non rispettano il formato corretto.
     */
    boolean checkData=true;
    if (targa.isEmpty()){
        JOptionPane.showMessageDialog(null, "Inserire la targa!", "Campo obbligatorio mancante", JOptionPane.ERROR_MESSAGE);
        checkData=false;
        
    } else { 
        if (!targa.isEmpty() && targa.length() != 7){
            JOptionPane.showMessageDialog(null, "La Targa deve contenere 7 cifre!", "Campo obbligatorio errato", JOptionPane.ERROR_MESSAGE);
            checkData=false;
        } 
    }
    
    /*
     * Se i campi non contengono errori di formato, si cerca di inserire una nuova targa, o di aggiornare 
     * una targa esistente, rispettivamente se non è stata selezionata alcuna riga, o è stata selezionata una 
     * riga della tabella.
     */
    if (checkData){
        Mezzo mezzo = new Mezzo(ID, targa, marca);
        
        /*
         * Se l'id è != null vuol dire che è stata selezionata una riga dalla tabella, quindi si cerca di 
         * aggiornare il campo rispettivo, altrimenti vuol dire che non è stata selezionata alcuna riga. In 
         * questo caso si cerca di inserire una nuova riga.
         */
        if (ID != null) {
            final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con la modifica?", "Conferma modifica", JOptionPane.OK_CANCEL_OPTION);
            if (RESPONSE == JOptionPane.OK_OPTION) {
                try {
                    if (FrontController.update(mezzo))
                        JOptionPane.showMessageDialog(null, "Modifica eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la modifica", "Errore" ,JOptionPane.ERROR_MESSAGE);
                
                } catch (EccezioneChiaveDuplicata e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }
            }            
        
        } else {
            final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'inserimento?", "Conferma inserimento", JOptionPane.OK_CANCEL_OPTION);
            if (RESPONSE == JOptionPane.OK_OPTION) {
                try {
                    if (FrontController.insert(mezzo))
                        JOptionPane.showMessageDialog(null, "Inserimento eseguito con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'inserimento", "Errore" ,JOptionPane.ERROR_MESSAGE);
                
                } catch (EccezioneChiaveDuplicata e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore" ,JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        if (mnuTarga.isSelected())
            visualizzaTarga(txtTarga.getText());
        else
            formWindowOpened(null);
        
        pulisciText();
    }
}//GEN-LAST:event_btnSalvaActionPerformed

/**
 * Questo evento si verifica alla pressione del tasto di eliminazione.
 * @param evt 
 */
private void btnEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaActionPerformed
// TODO add your handling code here:
        /*
         * Se viene generata una eccezione, vuol dire che non è stata selezionata alcuna riga. Quindi è impossibile eliminare una riga
         */
        try {
            Integer ID = mezziInTabella.get(tblMezzi.getSelectedRow()).getId();
            
            /*
             * Viene mostrato un messagio di conferma eliminazione. Se l'utente accetta
             * si cerca di eliminare la targa corrispondente all'id selezionato.
             */
            final int RESPONSE = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler procedere con l'eliminazione?", "Conferma eliminazione", JOptionPane.OK_CANCEL_OPTION);
            if (RESPONSE == JOptionPane.OK_OPTION) {
                Mezzo mezzo = new Mezzo(ID);
                
                //Viene mostrato un messaggio a seconda dell'esito dell'operazione
                if (FrontController.delete(mezzo))
                    JOptionPane.showMessageDialog(null, "Eliminazione eseguita con successo!", "", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'eliminazione", "Errore" ,JOptionPane.ERROR_MESSAGE);
    
                if (mnuTarga.isSelected())
                    visualizzaTarga(txtMarca.getText());
                else
                    formWindowOpened(null);
                
                pulisciText();
            }
            
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Seleziona una riga da eliminare!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_btnEliminaActionPerformed

private void mnuTuttiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTuttiActionPerformed
// TODO add your handling code here:
    formWindowOpened(null);
}//GEN-LAST:event_mnuTuttiActionPerformed

private void visualizzaTarga(String targa) {
    if (targa != null) {
        Mezzo mezzo = FrontController.getMezzo(targa);
        if (mezzo != null) {
            LinkedList mezzi = new LinkedList();
            if (mezzo.getId() != null)
                mezzi.add(mezzo);
            
            popolaTabella(mezzi);
            mnuTarga.setState(true);
            mnuTutti.setState(false);
        } else {
            JOptionPane.showMessageDialog(null, "Si è verificato un errore imprevisto durante la ricerca", "Errore" ,JOptionPane.ERROR_MESSAGE);
            mnuTarga.setState(false);
        }
            
        
    } else {
        mnuTarga.setState(false);
    }
}

private void mnuTargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuTargaActionPerformed
// TODO add your handling code here:
    String targa = JOptionPane.showInputDialog(null, "Inserire la targa del mezzo da cercare:");
    visualizzaTarga(targa);
}//GEN-LAST:event_mnuTargaActionPerformed

private void mnuStampaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuStampaActionPerformed
// TODO add your handling code here:
    try {
        new StampaMezzi(mezziInTabella).printAndOpen();

    } catch (DocumentException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Spedizioni.class.getName()).log(Level.SEVERE, null, ex);
    }
}//GEN-LAST:event_mnuStampaActionPerformed

/*
 * Azzera le text
 */
private void pulisciText() {
    txtMarca.setText(null);
    txtTarga.setText(null);
    txtTarga.requestFocus();
}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnElimina;
    private javax.swing.JButton btnNuovo;
    private javax.swing.JButton btnSalva;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblTarga;
    private javax.swing.JLabel lblTarga1;
    private javax.swing.JMenu mnuFiltra;
    private javax.swing.JMenu mnuProspetto;
    private javax.swing.JMenuItem mnuStampa;
    private javax.swing.JCheckBoxMenuItem mnuTarga;
    private javax.swing.JCheckBoxMenuItem mnuTutti;
    private javax.swing.JTable tblMezzi;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtTarga;
    // End of variables declaration//GEN-END:variables

    private List<Mezzo> mezziInTabella;
}
