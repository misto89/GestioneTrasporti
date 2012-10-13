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
import contabilizzazione.MovimentazioneMensile;
import contabilizzazione.SaldoContabilitaMensile;
import contabilizzazione.SaldoIvaMensile;
import entita.Fornitore;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaMovimentazioneMensile extends StampaDocumento {

    public static enum filtro {
        ANNO_PRECISO, INTERVALLO_DATA
    }
    
    public static enum tipo {
        PAGATE, NON_PAGATE, TUTTE
    }
    
    private Fornitore forn_cliente;
    private List<MovimentazioneMensile> saldi;
    private Date dataIniziale;
    private Date dataFinale;
    private int anno;
    private filtro filtroS;
    private tipo tipoS;
    private double[] totali;
    
    private static final String FILENAME = "movimentazioni.pdf";

    public StampaMovimentazioneMensile(int anno, List saldi, tipo tipo, double[] totali) throws DocumentException {
        super(FILENAME);
        this.anno = anno;
        this.saldi = saldi;
        this.tipoS = tipo;
        this.totali = totali;
        this.filtroS = filtro.ANNO_PRECISO;
    }

    public StampaMovimentazioneMensile(Fornitore forn_cliente, int anno, List saldi, tipo tipo, double[] totali) throws DocumentException {
        this(anno, saldi, tipo, totali);
        this.forn_cliente = forn_cliente;
    }
    
    public StampaMovimentazioneMensile(Date dataI, Date dataF, List saldi, tipo tipo, double[] totali) throws DocumentException {
        super(FILENAME);
        this.saldi = saldi;
        this.tipoS = tipo;
        this.dataIniziale = dataI;
        this.dataFinale = dataF;
        this.totali = totali;
        this.filtroS = filtro.INTERVALLO_DATA;
    }

    public StampaMovimentazioneMensile(Fornitore forn_cliente, Date dataI, Date dataF, List saldi, tipo tipo, double[] totali) throws DocumentException {
        this(dataI, dataF, saldi, tipo, totali);
        this.forn_cliente = forn_cliente;
    }
               
    @Override
    protected void stampa() throws DocumentException, IOException {
        
        Class subClass = saldi.get(0).getClass();

        doc.open();
        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo;
        if (subClass == SaldoContabilitaMensile.class)
            titolo = new PdfPCell(new Phrase("PROSPETTO CONTABILITA'"));
        else
            titolo = new PdfPCell(new Phrase("PROSPETTO IVA MENSILE"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        
        String filtroSa = null;
        if  (filtroS == filtro.INTERVALLO_DATA) {
            final String NEW_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
            filtroSa = "Dal " + sdf.format(dataIniziale) + " al " + sdf.format(dataFinale);
        }
        
        PdfPCell[] intestazione;
        if (subClass == SaldoContabilitaMensile.class) {
        
            String tipoSa;
            if (tipoS == tipo.TUTTE)
                tipoSa = "Pagate e Non pagate";
            else if  (tipoS == tipo.PAGATE)
                tipoSa = "Pagate";
            else
                tipoSa = "Non pagate";

            String fornCliente = null;
            if (forn_cliente == null)
                fornCliente = "Tutti";
            else
                fornCliente = forn_cliente.toString();        

            if (filtroS == filtro.INTERVALLO_DATA) {
                intestazione = new PdfPCell[] {
                        new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                        new PdfPCell(new Phrase("LISTA VALORI", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(tipoSa, FONT_GRANDE_NORMALE)),
                        new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(filtroSa, FONT_GRANDE_NORMALE)),
                };

            } else {
                intestazione = new PdfPCell[] {
                        new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                        new PdfPCell(new Phrase("LISTA VALORI", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(tipoSa, FONT_GRANDE_NORMALE)),
                        new PdfPCell(new Phrase("ANNO", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(String.valueOf(anno), FONT_GRANDE_NORMALE)),
                };
            } 
            
        } else {
            
            if (filtroS == filtro.INTERVALLO_DATA) {
                intestazione = new PdfPCell[] {
                        new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(filtroSa, FONT_GRANDE_NORMALE))
                };

            } else {
                intestazione = new PdfPCell[] {
                        new PdfPCell(new Phrase("ANNO", FONT_GRANDE_BOLD)),
                        new PdfPCell(new Phrase(String.valueOf(anno), FONT_GRANDE_NORMALE))
                };
            } 
        }
        
        int[] widths = {80, 160};
        table.setWidths(widths);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
        
        doc.add(table);
        
        table = new PdfPTable(4);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        widths = new int[] {40, 40, 40, 40};
        table.setWidths(widths);
        
        if (subClass == SaldoContabilitaMensile.class)
            intestazione = new PdfPCell[] {
                new PdfPCell(new Phrase("PERIODO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("ENTRATE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("USCITE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SALDO", FONT_GRANDE_BOLD))

            };
        else
            intestazione = new PdfPCell[] {
                new PdfPCell(new Phrase("PERIODO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IVA CREDITO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IVA DEBITO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("SALDO IVA", FONT_GRANDE_BOLD))

            };
        
        intestazione[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        
        for (PdfPCell cella : intestazione) {
//            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
        
        for (MovimentazioneMensile s : saldi) {      
            
            PdfPCell[] rigaSped;
            
            if (subClass == SaldoContabilitaMensile.class) {
                SaldoContabilitaMensile saldo = (SaldoContabilitaMensile) s;
                rigaSped = new PdfPCell[] {
                    new PdfPCell(new Phrase(saldo.getMeseAnno(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getTotPos())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getTotNeg())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getSaldo())), FONT_GRANDE_NORMALE))
                };
                
            } else {
                SaldoIvaMensile saldo = (SaldoIvaMensile) s;
                rigaSped = new PdfPCell[] {
                    new PdfPCell(new Phrase(saldo.getMeseAnno(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getIvaCredito())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getIvaDebito())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(saldo.getSaldoIva())), FONT_GRANDE_NORMALE))
                };
            }
            
            rigaSped[0].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[2].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            rigaSped[3].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            
            for (PdfPCell cella : rigaSped) {
//                cella.setBorder(com.itextpdf.text.Rectangle.ALIGN_CENTER);
                table.addCell(cella);
            }
        }
          
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            
            PdfPCell cella = new PdfPCell(new Phrase("\n"));
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
            
                
        PdfPCell[] rigaSped = {
            new PdfPCell(new Phrase("TOTALI", FONT_BIG_BOLD)),
            new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(totali[0])), FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(totali[1])), FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase("€ " + doubleToString(roundTwoDecimals(totali[2])), FONT_GRANDE_BOLD))
        };

        rigaSped[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        rigaSped[1].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        rigaSped[2].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        rigaSped[3].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        for (PdfPCell cella : rigaSped) {
            table.addCell(cella);
        }
        
        doc.add(table);
        
        doc.close();
        
    }
    
}
