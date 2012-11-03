/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stampa;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import entita.Mezzo;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaMezzi extends StampaDocumento {

    private static final String FILENAME = "mezzi.pdf";
    
    private List<Mezzo> mezzi;
    
    public StampaMezzi(List<Mezzo> mezzi) throws DocumentException {
        super(FILENAME);
        this.mezzi = mezzi;
    }

    @Override
    protected void stampa() throws DocumentException, IOException {
        doc.open();
        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("ANAGRAFICA AUTOMEZZI PROPRI"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(6);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        int[] widths = new int[] {15, 25, 20, 20, 20, 20};
        table.setWidths(widths);
        
        PdfPCell[] riga = new PdfPCell[] {
                new PdfPCell(new Phrase("TARGA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("MARCA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCAD. BOLLO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCAD. ATP", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCAD. ASSICURAZIONE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCAD. REVISIONE", FONT_GRANDE_BOLD)),
        };
        
        for (int i = 0; i < riga.length; i++) {
            riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);            
            table.addCell(riga[i]);
        }
        
        for (Mezzo mezzo : mezzi) {
            riga = new PdfPCell[] {
                new PdfPCell(new Phrase(mezzo.getTarga(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(mezzo.getMarca(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(mezzo.getFormattedScadBollo(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(mezzo.getFormattedScadAtp(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(mezzo.getFormattedScadAssicurazione(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(mezzo.getScadRevisione(), FONT_GRANDE_NORMALE)),
            };
            
            for (PdfPCell cella : riga) {
                cella.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cella);
            }
        }
        
        doc.add(table);
        
        doc.close();
    }
    
}