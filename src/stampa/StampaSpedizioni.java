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
        
        double importoTotale = 0.00;
        double provvigioneTotale = 0.00;
        double imponibileTotale = 0.00;
        
        for (Spedizione spedizione : spedizioni) {
            importoTotale += spedizione.getImporto();
            provvigioneTotale += spedizione.getProvvigione();
            imponibileTotale += spedizione.getImponibile();
            
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
                
                if (spedizione.getQta2() != 0) {
                    qtaDouble = spedizione.getQta2();
                    qtaInt = (int) qtaDouble;
                    if ( (qtaDouble - qtaInt) == 0 )
                        qta += "\n" + String.valueOf(qtaInt);
                    else
                        qta += "\n" + String.format("%1$,.1f", qtaDouble);
                }
                
            String um = spedizione.getUm1();
            if (spedizione.getUm2() != null) 
                um += "\n" + spedizione.getUm2();
            
            String traz = doubleToString(roundTwoDecimals(spedizione.getTraz1()));
            if (spedizione.getTraz2() != 0) {
                traz += "\n" + doubleToString(roundTwoDecimals(spedizione.getTraz2()));
            }
            
            String distrib = doubleToString(roundTwoDecimals(spedizione.getDistrib1()));
            if (spedizione.getDistrib2() != 0) {
                distrib += "\n" + doubleToString(roundTwoDecimals(spedizione.getDistrib2()));
            }
            
            PdfPCell[] rigaSped = {
                new PdfPCell(new Phrase(spedizione.getStringaBolle(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(statoStr, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataCarico, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(dataDoc, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(spedizione.getDescrizione(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(um, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(qta, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(traz, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(distrib, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(spedizione.getImporto())), FONT_GRANDE_NORMALE))
            };
            
            rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[6].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            rigaSped[7].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[8].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[9].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            for (PdfPCell cella : rigaSped) {
                //cella.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
                cella.setBorder(com.itextpdf.text.Rectangle.ALIGN_CENTER);
                table.addCell(cella);
            }
        }
        
        PdfPCell[] rigaImporto = {
                new PdfPCell(new Phrase("IMPORTO TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(DoubleFormatter.doubleToString(importoTotale), FONT_GRANDE_BOLD))
        };
        
        PdfPCell[] rigaProvv = {
                new PdfPCell(new Phrase("SPESE CONTRASSEGNO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(DoubleFormatter.doubleToString(provvigioneTotale), FONT_GRANDE_BOLD))
        };
        
        PdfPCell[] rigaImponibile = {
                new PdfPCell(new Phrase("IMPONIBILE\n\n", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(DoubleFormatter.doubleToString(imponibileTotale), FONT_GRANDE_BOLD))
        };
        
        for (int i = 0; i < rigaImporto.length; i++) {
            if (i == rigaImporto.length-1)
                rigaImporto[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            rigaImporto[i].setBorder(BORDER_TOP_AND_BOTTOM);
            rigaImporto[i].setBorderWidthTop(1.5f);
            table.addCell(rigaImporto[i]);
        }
        
        for (int i = 0; i < rigaProvv.length; i++) {
            if (i == rigaProvv.length-1)
                rigaProvv[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            rigaProvv[i].setBorder(BORDER_TOP_AND_BOTTOM);
            table.addCell(rigaProvv[i]);
        }
        
        for (int i = 0; i < rigaImponibile.length; i++) {
            if (i == rigaImponibile.length-1)
                rigaImponibile[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            rigaImponibile[i].setBorder(BORDER_TOP_AND_BOTTOM);
            table.addCell(rigaImponibile[i]);
        }
        
        doc.add(table);
        
        doc.close();
    }
    
}