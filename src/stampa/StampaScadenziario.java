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
import entita.Fattura;
import entita.Fornitore;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaScadenziario extends StampaDocumento {

    private static final String FILENAME = "scadenziario_fatture_";
    
    private List<Fattura> elencoFatture1;
    private List<Fattura> elencoFatture2;
    private Fattura.tipo tipo;
    private Fornitore forn_cliente;
    private String scadenza;
    
    /*
     * La stringa nome serve per disambiguare il nome del file, dato che questa classe sarà utilizzata
     * per lo scadenziaro, per il registro delle fatture emesse e il registro delle fatture d'acquisto.
     */
    public StampaScadenziario(Fornitore forn_cliente, List<Fattura> fatture, Fattura.tipo tipo, String scadenza) throws DocumentException {
        super(FILENAME + tipo + ".pdf");
        elencoFatture1 = fatture;
        this.forn_cliente = forn_cliente;
        this.tipo = tipo;
        this.scadenza = scadenza;
              
    }

    public StampaScadenziario(Fornitore forn_cliente, List<Fattura> emesse, List<Fattura> acquisto, Fattura.tipo tipo, String scadenza) throws DocumentException {
        this(forn_cliente, emesse, tipo, scadenza);
        this.elencoFatture2 = acquisto;
    }
    
        
    @Override
    protected void stampa() throws DocumentException, IOException {
        doc.open();
        if (tipo == Fattura.tipo.ACQ)
            stampaAcquisto(elencoFatture1);
        else if (tipo == Fattura.tipo.VEN)
            stampaEmesse();
        else
            stampaTutte();
        doc.close();
    }
    
    private void initIntestazione(PdfPTable table) throws DocumentException {
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        
        String fornCliente = null;
        if (forn_cliente == null)
            fornCliente = "Tutti";
        else
            fornCliente = forn_cliente.toString();        

        PdfPCell[] intestazione = {
                    new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("IN SCADENZA", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(scadenza, FONT_GRANDE_NORMALE)),
        };

        int[] widths = {80, 160};
        table.setWidths(widths);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
        
        doc.add(table);
        
    }
    
    private void stampaEmesse() throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("SCADENZIARIO FATTURE EMESSE"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        initIntestazione(new PdfPTable(2));
        
        table = new PdfPTable(6);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        int[] widths = {60, 20, 40, 60, 40, 40};
        table.setWidths(widths);
        
        PdfPCell[] intestazione = {
                new PdfPCell(new Phrase("CLIENTE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("NUMERO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("DATA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("MOD. PAGAM.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCADENZA", FONT_GRANDE_BOLD)),
        };
        
        for (PdfPCell cella : intestazione) {
            cella.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cella);
        }
        
        for (int j = 0; j < elencoFatture1.size(); j++) {
                        
            PdfPCell[] riga = new PdfPCell[] {
                    new PdfPCell(new Phrase(elencoFatture1.get(j).getCliente().getNome(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(String.valueOf(elencoFatture1.get(j).getNumero()), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture1.get(j).getFormattedData(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture1.get(j).getMetPag().replace("-", " a ") + " gg", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(elencoFatture1.get(j).getTotale())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture1.get(j).getFormattedDataScadenza(), FONT_GRANDE_NORMALE))
            };
            
            for (int i = 0; i < riga.length; i++) {
                if (i == 4)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                else if (i == 1 || i == 2 || i == 5)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                else
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                
                if (j == elencoFatture1.size()-1)
                    riga[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);
                else
                    riga[i].setBorder(BORDER_LEFT_RIGHT);
                
                table.addCell(riga[i]);
                                
            }
        }
        
        doc.add(table);
        
    }
    
    private void stampaAcquisto(List<Fattura> elencoFatture) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("SCADENZIARIO FATTURE ACQUISTO"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        initIntestazione(new PdfPTable(2));
        
        table = new PdfPTable(7);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        int[] widths = {60, 30, 25, 40, 60, 40, 40};
        table.setWidths(widths);
        
        PdfPCell[] intestazione = {
                new PdfPCell(new Phrase("FORNITORE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TIPO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("NUMERO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("DATA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("MOD. PAGAM.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCADENZA", FONT_GRANDE_BOLD)),
        };
        
        for (PdfPCell cella : intestazione) {
            cella.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cella);
        }
        
        for (int j = 0; j < elencoFatture.size(); j++) {
                        
            PdfPCell[] riga = new PdfPCell[] {
                    new PdfPCell(new Phrase(elencoFatture.get(j).getCliente().getNome(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture.get(j).getTipo(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(String.valueOf(elencoFatture.get(j).getNumero()), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture.get(j).getFormattedData(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture.get(j).getMetPag().replace("-", " a ") + " gg", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(elencoFatture.get(j).getTotale())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(elencoFatture.get(j).getFormattedDataScadenza(), FONT_GRANDE_NORMALE))
            };
            
            for (int i = 0; i < riga.length; i++) {
                if (i == 5)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                else if (i == 2 || i == 3 || i == 6)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                else
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                
                if (j == elencoFatture.size()-1)
                    riga[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);
                else
                    riga[i].setBorder(BORDER_LEFT_RIGHT);
                
                table.addCell(riga[i]);
                                
            }
        }
        
        doc.add(table);
    }
    
    private void stampaTutte() throws DocumentException {
        stampaEmesse();
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        PdfPCell titolo = new PdfPCell(new Phrase("\n"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        stampaAcquisto(elencoFatture2);
    }
}
