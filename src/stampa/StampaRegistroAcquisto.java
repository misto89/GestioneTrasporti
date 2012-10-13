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
import entita.Fattura;
import entita.Fornitore;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaRegistroAcquisto extends StampaDocumento {

    private static final String FILENAME = "registro_fatture_acquisto.pdf";
    
    private Integer anno;
    private Date dataIniziale;
    private Date dataFinale;
    private Object[] riepilogo;
    private Fattura.pagata filtroPagate;
    private Fattura.scaduta filtroScadute;
    private Fornitore fornitore;
    private String tipo;
    private List<String> mesi;
    private List<Fattura> fatture;
    
    private StampaRegistroAcquisto(Fattura.pagata filtroPagate, Fattura.scaduta filtroScadute, Fornitore cliente, Object[] riepilogo, List<String> mesi, List<Fattura> fatture, String tipo) throws DocumentException {
        super(FILENAME);
        this.filtroPagate = filtroPagate;
        this.filtroScadute = filtroScadute;
        this.fornitore = cliente;
        this.riepilogo = riepilogo;
        this.mesi = mesi;
        this.fatture = fatture;
        if (tipo.equals("all"))
            this.tipo = "Tutte";
        else
            this.tipo = tipo;
        doc.setPageSize(PageSize.A4.rotate());
    }
    
    public StampaRegistroAcquisto(int anno, Fattura.pagata filtroPagate, Fattura.scaduta filtroScadute, Fornitore cliente, Object[] riepilogo, List<String> mesi, List<Fattura> fatture, String tipo) throws DocumentException {
        this(filtroPagate, filtroScadute, cliente, riepilogo, mesi, fatture, tipo);
        this.anno = anno;
    }
    
    public StampaRegistroAcquisto(Date dataI, Date dataF, Fattura.pagata filtroPagate, Fattura.scaduta filtroScadute, Fornitore cliente, Object[] riepilogo, List<String> mesi, List<Fattura> fatture, String tipo) throws DocumentException {
        this(filtroPagate, filtroScadute, cliente, riepilogo, mesi, fatture, tipo);
        this.dataIniziale = dataI;
        this.dataFinale = dataF;
    }
    
    @Override
    protected void stampa() throws DocumentException, IOException {
        doc.open();
        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("REGISTRO FATTURE ACQUISTO"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        
        String fornStringa = null;
        if (fornitore == null)
            fornStringa = "Tutti";
        else
            fornStringa = fornitore.toString();        

        String strPagate = null;
        if (filtroPagate == Fattura.pagata.P)
            strPagate = "Pagate";
        else if (filtroPagate == Fattura.pagata.NP)
            strPagate = "Non pagate";
        else
            strPagate = "Pagate e Non pagate";
        
        String strScadute = null;
        if (filtroScadute == Fattura.scaduta.S)
            strScadute = "Scadute";
        else if (filtroScadute == Fattura.scaduta.NS)
            strScadute = "Non scadute";
        else
            strScadute = "Scadute e Non scadute";
        
        String strTitolo = null;
        String strPeriodo = null;
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        if (anno == null) {
            strTitolo = "Intervallo";
            strPeriodo = "Dal " + sdf.format(dataIniziale) + " al " + sdf.format(dataFinale);
            
        } else {
            strTitolo = "Anno";
            strPeriodo = anno.toString();
        }
        
        String titMesi = "Mesi di riferimento";
        String strMesi = "";
        if (mesi.size() == 12) {
            strMesi = "Tutti";
            
        } else if (mesi.isEmpty()) {
            strMesi = "-";
        } else {
            for (String mese : mesi) {
//                titMesi += "\n";
                strMesi += mese + "\n";
            }
        }
                
        PdfPCell[] intestazione = {
                    new PdfPCell(new Phrase("Fornitore", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(fornStringa, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Tipo", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(tipo, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Pagamento", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(strPagate, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Scadenza", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(strScadute, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(strTitolo, FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(strPeriodo, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(titMesi, FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(strMesi, FONT_GRANDE_NORMALE))
        };

        int[] widths = {80, 160};
        table.setWidths(widths);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
        
        doc.add(table);
        
        table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        titolo = new PdfPCell(new Phrase("Totali"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(6);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        widths = new int[] {40, 40, 40, 40, 40, 40};
        table.setWidths(widths);
        
        PdfPCell[] tot = new PdfPCell[] {
                new PdfPCell(new Phrase("NUM. FATTURE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOT. IMPONIBILE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOT. IVA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOT. FATTURE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOT. PAGATE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOT. NON PAGATE", FONT_GRANDE_BOLD))
        };
        
        for (int i = 0; i < tot.length; i++) {
            tot[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

            if (i == 0)
                tot[i].setBorder(BORDER_TOP_RIGHT_LEFT);
            else
                tot[i].setBorder(BORDER_TOP_RIGHT);
            
            table.addCell(tot[i]);
        }
        
        tot = new PdfPCell[] {
            new PdfPCell(new Phrase(String.valueOf((Integer)riepilogo[0]), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(doubleToString((Double)riepilogo[1]), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(doubleToString((Double)riepilogo[2]), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(doubleToString((Double)riepilogo[3]), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(doubleToString((Double)riepilogo[4]), FONT_GRANDE_NORMALE)),
            new PdfPCell(new Phrase(doubleToString((Double)riepilogo[5]), FONT_GRANDE_NORMALE))
        };
        
        for (int i = 0; i < tot.length; i++) {
            if (i == 0) {
                tot[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                tot[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);
                
            } else {
                tot[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                tot[i].setBorder(BORDER_BOTTOM_RIGHT);
            }
            
            table.addCell(tot[i]);
        }
        
        doc.add(table);
        
        table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        titolo = new PdfPCell(new Phrase("Fatture"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(9);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        widths = new int[] {60, 50, 20, 35, 55, 20, 35, 35, 35};
        table.setWidths(widths);
        
        intestazione = new PdfPCell[] {
                new PdfPCell(new Phrase("FORNITORE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TIPO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("NUMERO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("DATA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("MOD. PAGAM.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("PAGATA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IVA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SCADENZA", FONT_GRANDE_BOLD)),
        };
        
        for (PdfPCell cella : intestazione) {
            cella.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cella);
        }
        
        for (int j = 0; j < fatture.size(); j++) {
                        
            String pagata = null;
            if (fatture.get(j).getPagata())
                pagata = "S";
            else
                pagata = "N";
            
            PdfPCell[] riga = new PdfPCell[] {
                    new PdfPCell(new Phrase(fatture.get(j).getCliente().getNome(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(fatture.get(j).getTipo(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(String.valueOf(fatture.get(j).getNumero()), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(fatture.get(j).getFormattedData(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(fatture.get(j).getMetPag().replace("-", " a ") + " gg", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(pagata, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(fatture.get(j).getIva())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(fatture.get(j).getTotale())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(fatture.get(j).getFormattedDataScadenza(), FONT_GRANDE_NORMALE))
            };
            
            for (int i = 0; i < riga.length; i++) {
                if (i == 6 || i == 7)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                else if (i == 2 || i == 3 || i == 5 || i == 8)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                else
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                
                if (j == fatture.size()-1)
                    riga[i].setBorder(BORDER_BOTTOM_RIGHT_LEFT);
                else
                    riga[i].setBorder(BORDER_LEFT_RIGHT);
                
                table.addCell(riga[i]);
                                
            }
        }
        
        doc.add(table);
        
        doc.close();
    }
    
}
