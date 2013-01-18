/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stampa;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import entita.Fattura;
import entita.Fornitore;
import entita.Spedizione;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michele
 */
public class StampaFattura extends StampaDocumento {
        
    private class PageManager extends PdfPageEventHelper {
                               
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            super.onEndPage(writer, document);
//            addSegue(document.getPageNumber());

        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            super.onStartPage(writer, document);
            
            if (!doc.isOpen())
                return;
            
            int numPage = document.getPageNumber();
            try {
                initIntestazione(numPage);
                drawRectangle(writer.getDirectContent(), X, Y_NOT_LAST, WIDTH, HEIGHT_NOT_LAST, false);
                if (numPage == 1) {
                    PdfPTable intestSped = new PdfPTable(1);
                    intestSped.setWidthPercentage(100);
                    intestSped.setSpacingBefore(0);
                    PdfPCell intC = new PdfPCell(new Phrase("ECCOVI FATTURA PER TRASPORTO/I EFFETTUATO/I PER VOSTRO CONTO", FONT_GRANDE_NORMALE));
                    intC.setBorder(BORDER_LEFT_RIGHT);
                    intC.setFixedHeight(20);
                    intestSped.addCell(intC);
                    doc.add(intestSped);
                }
                doc.add(initTableSped());
            } catch (DocumentException ex) {
                Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void drawRectangle(PdfContentByte canvas, float x, float y, float width, float height, boolean toClose) {
            canvas.moveTo(x, y);
            canvas.lineTo(x, y + height);
            canvas.moveTo(x + width, y);
            canvas.lineTo(x + width, y + height);

            if (toClose) {
                canvas.moveTo(x, y);
                canvas.lineTo(x + width, y);
            }

    //        canvas.lineTo(x + width, y);
    //        canvas.lineTo(x + width, y + height);
    //        canvas.lineTo(x, y + height);
    //        canvas.rectangle(x, y, width, height);
            canvas.closePath();

        }
        
//        private void addSegue(int numPagina) {
//            //Aggiunge la scritta SEGUE PAGINA N
//            PdfPTable rigaSegue = new PdfPTable(1);
//            PdfPCell riga = new PdfPCell(new Phrase("Segue pagina numero "+ String.valueOf(numPagina + 1), FONT_GRANDE_NORMALE)); 
//            riga.setBorder(PdfPCell.NO_BORDER);
//            riga.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
//            rigaSegue.addCell(riga);
//            rigaSegue.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
//            rigaSegue.setTotalWidth(523);
//            rigaSegue.writeSelectedRows(0, -1, 36, 56, writer.getDirectContent());
//        }
        
        private PdfPTable initTableSped() {
            PdfPTable table = initColsSped();

            PdfPCell[] intestazioneSpedizioni = {
                new PdfPCell(new Phrase("RIF. DOC.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("DATA DOC.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("DESCRIZIONE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("U.M", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("QTA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("PRZ.UN", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IMPORTO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SC.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IVA", FONT_GRANDE_BOLD)),
            };

            for (int i = 0; i < intestazioneSpedizioni.length; i++) {
                if (i == 0)         
                    intestazioneSpedizioni[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                else
                    intestazioneSpedizioni[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

                table.addCell(intestazioneSpedizioni[i]);

            }

            return table;
        }
        
        private void initIntestazione(int numPagina) throws DocumentException {
            //Paragraph vuoto per allineamento verticale

            if (allegato) {

                try {
                    //Inserimento di una immagine
                    Image img = Image.getInstance("src/img/carta_intestata_completa.jpg");
                    img.setAbsolutePosition(0, 0);
                    doc.add(img);

                } catch (BadElementException ex) {
                    Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Instestazione------------------------------------------------
            String indirizzo = cliente.getIndirizzoLeg();
            String piva = cliente.getPiva();
            String codfisc = cliente.getCodfiscale();
            String cap = cliente.getCapLeg();
            String citta = cliente.getCittaLeg();
            String prov = cliente.getProvLeg();
            String titolare = cliente.getTitolare();
            String nome;
            try {
                nome = cliente.getNome().toUpperCase();

            } catch (NullPointerException e) {
                nome = "";
            }

            if (indirizzo == null)
                indirizzo = "";
            if (piva == null) 
                piva = "";
            if (codfisc == null)
                codfisc = "";
            if (cap == null)
                cap = "";
            if (citta == null)
                citta = "";
            if (prov == null)
                prov = "";
            if (titolare == null)
                titolare = "";

            Phrase phDitta = new Phrase("Spett.le\n", FONT_GRANDE_NORMALE);
            Phrase phNome =  new Phrase(nome + "\n", FONT_BIG_BOLD);
            Phrase phTitolare = null;
            if (!(titolare.equalsIgnoreCase("")))
                 phTitolare = new Phrase("Di " + titolare + "\n", FONT_PICCOLO_NORMALE);
            else
                phTitolare = new Phrase("\n", FONT_PICCOLO_NORMALE);

            Phrase phIndirizzo = new Phrase(indirizzo + "\n" + cap + " " + citta + " " + prov + "\n" , FONT_GRANDE_NORMALE);
            Phrase phPartita = null;
             if (!(piva.equalsIgnoreCase("")))
                phPartita = new Phrase("PARTITA IVA " + piva + "\n", FONT_PICCOLO_NORMALE);
             else
                 phPartita = new Phrase("\n", FONT_PICCOLO_NORMALE);
             Phrase phCodFiscale = null;
             if (!(codfisc.equalsIgnoreCase("")))
                phCodFiscale = new Phrase("CODICE FISCALE " + codfisc ,FONT_PICCOLO_NORMALE);
             else
                 phCodFiscale = new Phrase("\n", FONT_PICCOLO_NORMALE);

             ArrayList<Phrase> intestazione = new ArrayList<Phrase>();
             intestazione.add(phDitta);
             intestazione.add(phNome);
             if (phTitolare != null) 
                 intestazione.add(phTitolare);
             intestazione.add(phIndirizzo);
             if (phPartita != null) 
                intestazione.add(phPartita);
             if (phCodFiscale != null)
                 intestazione.add(phCodFiscale);

            final int INDENT_LEFT = 280;
            Paragraph intestCliente = new Paragraph();
            intestCliente.addAll(intestazione);
            intestCliente.setIndentationLeft(INDENT_LEFT);
            intestCliente.setSpacingBefore(10);

            doc.add(intestCliente);
            //-----------------------------------------------------------------

            //tabella informazioni---------------------------------------------------------
            final String NEW_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);

            String numero =  String.valueOf(fattura.getNumero());
            String year = fattura.getData().toString().substring(0,4);
            if (numero.length() == 1)
                numero = "0" + numero;
            
            //Inserimento della notazione Num/AnnoFattura
            numero += "/" + year;

            String[] metPagam = fattura.getMetPag().split("-");
            String metodoPagam = "";
            if (metPagam[1].equals("0"))
                metodoPagam = metPagam[0];
            else
                metodoPagam = metPagam[0] + " a " + metPagam[1] + " gg";

            PdfPCell[] info = {
                new PdfPCell(new Phrase("TIPO DOCUMENTO", FONT_PICCOLO_BOLD)),
                new PdfPCell(new Phrase("NUMERO", FONT_PICCOLO_BOLD)),
                new PdfPCell(new Phrase("DATA", FONT_PICCOLO_BOLD)),
                new PdfPCell(new Phrase("MODALITA' DI PAGAMENTO", FONT_PICCOLO_BOLD)),
                new PdfPCell(new Phrase("PAGINA", FONT_PICCOLO_BOLD)),
                new PdfPCell(new Phrase("Fattura", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(numero, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(sdf.format(fattura.getData()), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(metodoPagam, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(String.valueOf(numPagina), FONT_GRANDE_NORMALE))    
            };         

            final int NUM_COLS_INTESTAZIONE = 5;
            PdfPTable table = new PdfPTable(NUM_COLS_INTESTAZIONE);
            table.setSpacingBefore(30);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setWidthPercentage(100);

            int[] widths = {40, 40, 40, 40, 25};
            try {
                table.setWidths(widths);
            } catch (DocumentException ex) {
                Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < info.length; i++) {

                //Imposta i bordi del riepilogo finale come rettangoli unici attorno ai dati
                if ((i >= 0) && (i <= (info.length/2)-1))
                    info[i].setBorder(BORDER_TOP_RIGHT_LEFT);
                else
                    info[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);

                info[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                table.addCell(info[i]);
            }

            doc.add(table);
        }
    }
    
    private Fornitore cliente;
    private Fattura fattura;
    private boolean allegato;
    private List<Spedizione> spedizioni;
    
    private final float X; //valore x del rettangolo da disegnare
//    private final float Y_LAST; //valore y del rettangolo per l'ultima pagina del pdf (quella con i totali)
    private final float Y_NOT_LAST; //valore y del rettangolo per tutte le pagine prima dell'ultima
    private final float WIDTH; //larghezza del rettangolo
//    private final float HEIGHT_LAST; //altezza del rettangolo per l'ultima pagina
    private final float HEIGHT_NOT_LAST; //altezza del rettangolo per tutte le pagina prima dell'ultima
    
    private static final String FILENAME = "fattura.pdf";
    
    public StampaFattura(Fattura fattura, Fornitore cliente, boolean allegato) throws DocumentException, IOException {
        super(FILENAME);
        this.fattura = fattura;
        this.cliente = cliente;
        this.allegato = allegato; 
        spedizioni = fattura.getSpedizioni();
        
        X = 36;
//        Y_LAST = 102;
        WIDTH = 523;
        Y_NOT_LAST = 50; //60
        
        /*
         * Se la fattura deve essere inviata per email, quindi deve contenere l'immagine della carta intestata,
         * i valori dell'altezza cambiano perchÃ© l'immagine inserita fa spostare tutto leggermente piÃ¹ in basso.
         * E' necessario quindi adattare l'altezza allo spostamento.
         */
        if (!allegato) {
//            HEIGHT_LAST = 562;
            HEIGHT_NOT_LAST = 604;
        } else {
//            HEIGHT_LAST = 552;
            HEIGHT_NOT_LAST = 594;
        }
        
        doc.setMargins(doc.leftMargin(), doc.rightMargin(), doc.topMargin(), doc.bottomMargin() + 15);
        writer.setPageEvent(new PageManager());
    }
          
    private void setTableSped(PdfPTable table) {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        
        for (Spedizione sped: spedizioni) {
            String descrizione = (sped.getDescrizione() != null) ? sped.getDescrizione() : "";
            if (sped.getValoreMerce() != 0) {
                descrizione += "\nVal.Merce â‚¬ " + String.format("%1$,.2f", sped.getValoreMerce());
            } else if (sped.getProvvigione() != 0)
                descrizione += "\nSpese Contrassegno â‚¬ " + String.format("%1$,.2f", sped.getProvvigione());

            //Controlli generici sulla presenza o meno dei valori, se i valori non sono presenti (pari a 0) non appaiono nella fattura
            String importo = doubleToString(roundTwoDecimals(sped.getImporto()));
            String przUnitario = doubleToString(roundTwoDecimals(sped.getDistrib1() + sped.getTraz1()));
            if (sped.getDistrib2() != 0.00 || sped.getTraz2() != 0.00)
                przUnitario += "\n" + doubleToString(roundTwoDecimals(sped.getDistrib2() + sped.getTraz2()));

            String qta = null;
            int qta1Int = 0;
            double qta1Double = 0.00;
            int qta2Int = 0;
            double qta2Double = 0.00;

            if(sped.getQta1() == 0.00){
               qta = "";
            } else {
                qta1Double = sped.getQta1();
                qta1Int = (int) qta1Double;
                if ( (qta1Double - qta1Int) == 0 )
                    qta = String.valueOf(qta1Int);
                else
                    qta = String.format("%1$,.1f", qta1Double);
            }

            if(sped.getQta2() != 0.00) {
                qta += "\n";
                qta2Double = sped.getQta2();
                qta2Int = (int) qta2Double;
                if ( (qta2Double - qta2Int) == 0 )
                    qta += String.valueOf(qta2Int);
                else
                    qta += String.format("%1$,.1f", qta2Double);
            }

            String um = (sped.getUm2() != null) ? sped.getUm1() + "\n" + sped.getUm2() : sped.getUm1();

            if(sped.getImporto() == 0){
                importo = "";
            }
            if(sped.getDistrib1() + sped.getTraz1() == 0){
                przUnitario = "";
            }            

            Date dataDocumento = sped.getDataDocumento();
            String dataDoc = "";
            if (dataDocumento != null)
                dataDoc = sdf.format(dataDocumento);

            String sconto = String.valueOf(sped.getSconto() + " %");
            if (sped.getSconto() == 0)
            {
                sconto = "";
            }

            PdfPCell[] rigaSped = {
                new PdfPCell(new Phrase(sped.getStringaBolle(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataDoc, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(descrizione, FONT_PICCOLO_NORMALE)),
                new PdfPCell(new Phrase(um, FONT_PICCOLO_NORMALE)),
                new PdfPCell(new Phrase(qta, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(przUnitario, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(importo, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(sconto, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(String.valueOf(sped.getPercIva() + " %"), FONT_GRANDE_NORMALE)),
            };

            rigaSped[0].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[2].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[4].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[5].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[6].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[7].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[8].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            for (int i = 0; i < rigaSped.length; i++) {
                rigaSped[i].setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
                rigaSped[i].setBorder(NO_BORDER);    
                table.addCell(rigaSped[i]);
            }

        }
    }
    
    private void setTableSpedForfait(PdfPTable table) {
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        
        //stampa la fattura eliminando i riferimenti a quantitÃ , prezzi e totali delle spedizioni
        for (Spedizione sped: spedizioni) {
            String descrizione = (sped.getDescrizione() != null) ? sped.getDescrizione() : "";
            if (sped.getValoreMerce() != 0) {
                descrizione += "\nVal.Merce â‚¬ " + String.format("%1$,.2f", sped.getValoreMerce());
            } else if (sped.getProvvigione() != 0)
                descrizione += "\nSpese Contrassegno â‚¬ " + String.format("%1$,.2f", sped.getProvvigione());

            Date dataDocumento = sped.getDataDocumento();
            String dataDoc = "";
            if (dataDocumento != null)
                dataDoc = sdf.format(dataDocumento);

            PdfPCell[] rigaSped = {
                new PdfPCell(new Phrase(sped.getStringaBolle(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataDoc, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(descrizione, FONT_PICCOLO_NORMALE)),
                new PdfPCell(new Phrase("", FONT_PICCOLO_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                //new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
            };
            rigaSped[0].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[2].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[4].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[5].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[6].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[7].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[8].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            rigaSped[0].setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);              
            rigaSped[1].setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            for (int i = 0; i < rigaSped.length; i++) {
                rigaSped[i].setBorder(NO_BORDER);
                table.addCell(rigaSped[i]);
            }

        }
    }
    
    @Override
    protected void stampa() throws DocumentException, IOException {
        
        doc.open();
       
        PdfPTable table = initColsSped();
      
        if (!fattura.getForfait()){
            setTableSped(table);
            doc.add(table);
        } else {
            setTableSpedForfait(table);
            doc.add(table);
            
            final int NUM_COLS_FORFAIT = 8;
            PdfPTable rigaForfait = new PdfPTable(NUM_COLS_FORFAIT);
            int[] widths = {115, 25, 20, 25, 45, 60, 20, 23};
            rigaForfait.setWidths(widths);
            rigaForfait.setWidthPercentage(100);

            PdfPCell[] riga = {
                new PdfPCell(new Phrase("Per importo totale di:", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),           
                new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(fattura.getImporto())), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase(fattura.getPercIva() + " %", FONT_GRANDE_NORMALE)),
            };

            riga[0].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            riga[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            riga[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            riga[3].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            riga[4].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            riga[5].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            riga[6].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            riga[7].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            for (int i = 0; i < riga.length; i++) {
                riga[i].setBorder(NO_BORDER);
                rigaForfait.addCell(riga[i]);
            }
            rigaForfait.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            rigaForfait.setSpacingBefore(10);

            doc.add(rigaForfait);
        }

        //Riepilogo finale totali------------------------------------------------------------------
        final int NUM_COLS_TOTALI = 6;
        final int TOTAL_WIDTH = 523;
        table = new PdfPTable(NUM_COLS_TOTALI);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setTotalWidth(TOTAL_WIDTH);
        //table.setWidthPercentage(100);
        table.setWidths(new int[] {15, 15, 15, 14, 14, 17});
        
        PdfPCell[] totali = {
            new PdfPCell(new Phrase("IMPORTO", FONT_PICCOLO_NORMALE)),
            new PdfPCell(new Phrase("SCONTO", FONT_PICCOLO_NORMALE)),
            new PdfPCell(new Phrase("PROVV. ASSEGNI", FONT_PICCOLO_NORMALE)),
            new PdfPCell(new Phrase("IMPONIBILE", FONT_PICCOLO_NORMALE)),
            new PdfPCell(new Phrase("IVA", FONT_PICCOLO_NORMALE)),
            new PdfPCell(new Phrase("TOTALE", FONT_PICCOLO_BOLD)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getImporto()))), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getSconto()))), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getProvvigione()))), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getImponibile()))), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getIva()))), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(String.valueOf(doubleToString(roundTwoDecimals(fattura.getTotale()))), FONT_GRANDE_BOLD))            
        };
               
        for (int i = 0; i < totali.length; i++) {
            //Imposta i bordi del riepilogo finale come rettangoli unici attorno ai dati
            if ((i >= 0) && (i <= (totali.length/2)-1))
                totali[i].setBorder(BORDER_TOP_RIGHT_LEFT);
            else
                totali[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);
            
            totali[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(totali[i]);
        }

        PdfPTable rigaNote = new PdfPTable(1);
        PdfPCell riga; 
        if (fattura.getNote() != null)
            riga = new PdfPCell(new Phrase("NOTE: " + fattura.getNote(), FONT_GRANDE_NORMALE)); 
        else
            riga = new PdfPCell(new Phrase("NOTE: ", FONT_GRANDE_NORMALE)); 
        
        riga.setBorder(BORDER_TOP);
        rigaNote.addCell(riga);
        rigaNote.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        rigaNote.setTotalWidth(523);
        rigaNote.writeSelectedRows(0, -1, 36, table.getTotalHeight() + 70, writer.getDirectContent());
       
        table.writeSelectedRows(0, -1, 36, table.getTotalHeight() + 50, writer.getDirectContent());
        //-----------------------------------------------------------------------
   
        doc.close();
    }
    
    private PdfPTable initColsSped() {
        final int NUM_COLS_TABLE_SPED = 9;
        final int[] WIDTHS_TABLE_SPED = {35, 40, 83, 15, 20, 34, 48, 20, 20};
    
        PdfPTable table = new PdfPTable(NUM_COLS_TABLE_SPED);
        table.setSpacingBefore(0);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        try {
            table.setWidths(WIDTHS_TABLE_SPED);
        } catch (DocumentException ex) {
            Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setWidthPercentage(100);
        
        return table;
    }
        
}
