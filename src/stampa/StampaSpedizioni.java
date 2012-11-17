/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stampa;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import entita.Fornitore;
import entita.Spedizione;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import libs.DoubleFormatter;

/**
 *
 * @author Michele
 */
public final class StampaSpedizioni extends StampaDocumento {

    public static enum filtro {
        TUTTE, INTERVALLO_DATA
    }
    
    public static enum tipo {
        FATTURATE, NON_FATTURATE, TUTTE
    }
    
    private Fornitore cliente;
    private List<Spedizione> spedizioni;
    private Date dataIniziale;
    private Date dataFinale;
    private filtro filtroS;
    private tipo tipoS;
    
    private static final String FILENAME = "spedizioni.pdf";
        
    public StampaSpedizioni(Fornitore cliente, List<Spedizione> spedizioni, filtro filtro, tipo tipo) throws DocumentException, IOException {
        super(FILENAME);
        this.filtroS = filtro;
        this.cliente = cliente;
        this.tipoS = tipo;
        this.spedizioni = spedizioni;
        this.doc.setPageSize(PageSize.A4.rotate());

    }
    
    public StampaSpedizioni(Fornitore cliente, List<Spedizione> spedizioni, Date dataI, Date dataF, filtro filtro, tipo tipo) throws DocumentException, IOException {
        super(FILENAME);
        this.filtroS = filtro;
        this.cliente = cliente;
        this.tipoS = tipo;
        this.spedizioni = spedizioni;
        this.doc.setPageSize(PageSize.A4.rotate());
        dataIniziale = dataI;
        dataFinale = dataF;
        
    }
    
    @Override
    protected void stampa() throws DocumentException, IOException {
        
        doc.open();
        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        PdfPCell titolo = new PdfPCell(new Phrase("PROSPETTO SPEDIZIONI"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        
        String filtroSp = null;
        if  (filtroS == filtro.INTERVALLO_DATA) {
            final String NEW_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
            filtroSp = "Dal " + sdf.format(dataIniziale) + " al " + sdf.format(dataFinale);
        }
        
        String tipoSp;
        if (tipoS == tipo.TUTTE)
            tipoSp = "Fatturate e Non fatturate";
        else if  (tipoS == tipo.FATTURATE)
            tipoSp = "Fatturate";
        else
            tipoSp = "Non fatturate";
        
        PdfPCell[] intestazione;
        if (filtroS == filtro.INTERVALLO_DATA) {
            intestazione = new PdfPCell[] {
                    new PdfPCell(new Phrase("CLIENTE", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(cliente.toString(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("SPEDIZIONI", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(tipoSp, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(filtroSp, FONT_GRANDE_NORMALE)),
            };
            
        } else {
            intestazione = new PdfPCell[] {
                    new PdfPCell(new Phrase("CLIENTE", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(cliente.toString(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("SPEDIZIONI", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(tipoSp, FONT_GRANDE_NORMALE))
            };
        } 
        
        int[] widths = {40, 160};
        table.setWidths(widths);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cella);
        }
        
        doc.add(table);
        
        table = new PdfPTable(10);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        widths = new int[] {40, 30, 40, 40, 70, 20, 20, 40, 40, 45};
        table.setWidths(widths);
        
        intestazione = new PdfPCell[] {
            new PdfPCell(new Phrase("RIF. DOC.", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("STATO", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("DATA CARICO", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("DATA DOC.", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("DESCRIZIONE", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("U.M.", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("QTA", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("TRAZ.", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("DISTRIB.", FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("IMPORTO", FONT_GRANDE_BOLD))
            
        };
        
        intestazione[1].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        intestazione[6].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[7].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        intestazione[8].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        intestazione[9].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
            table.addCell(cella);
        }
        
        double totale = 0.00;
        
        for (Spedizione spedizione : spedizioni) {
            totale += spedizione.getImporto();
            final String NEW_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
            String dataCarico = sdf.format(spedizione.getDataCarico());
            String dataDoc = sdf.format(spedizione.getDataDocumento()); 
            String statoStr;
            char stato = spedizione.getStato();
            if (stato == 'C')
                statoStr = "Consegna";
            else
                statoStr = "Ritiro";
            
            String qta = null;
                int qtaInt = 0;
                double qtaDouble = 0.00;
                
                if(spedizione.getQta1() == 0.00){
                   qta = "";
                } else {
                    qtaDouble = spedizione.getQta1();
                    qtaInt = (int) qtaDouble;
                    if ( (qtaDouble - qtaInt) == 0 )
                        qta = String.valueOf(qtaInt);
                    else
                        qta = String.format("%1$,.1f", qtaDouble);
                }
                
            PdfPCell[] rigaSped = {
                new PdfPCell(new Phrase(spedizione.getStringaBolle(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(statoStr, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataCarico, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataDoc, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(spedizione.getDescrizione(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(spedizione.getUm1(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(qta, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(spedizione.getTraz1())), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(spedizione.getDistrib1())), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(spedizione.getImporto())), FONT_GRANDE_NORMALE))
            };
            
            rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[6].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[7].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[8].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[9].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            for (PdfPCell cella : rigaSped) {
                cella.setBorder(com.itextpdf.text.Rectangle.ALIGN_CENTER);
                table.addCell(cella);
            }
        }

        PdfPCell[] riga = {
                new PdfPCell(new Phrase("IMPORTO TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(DoubleFormatter.doubleToString(totale), FONT_GRANDE_BOLD))
        };
        
        for (int i = 0; i < riga.length; i++) {
            if (i == riga.length-1)
                riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            riga[i].setBorder(BORDER_TOP_AND_BOTTOM);
            table.addCell(riga[i]);
        }
        
        doc.add(table);
        
        doc.close();
    }
    
}