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
import contabilizzazione.SaldoCassaMensile;
import entita.Fornitore;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import movimentazionecontante.MovimentazioneContante;
import movimentazionecontante.Versamento;

/**
 *
 * @author Michele
 */
public class StampaCassa extends StampaDocumento {

    public static class RiepilogoCassa {

        public double attivoContante;
        public double passivoContante;
        public double totVersamenti;
        public double totPrelievi;
        public double netto;    
        
        public RiepilogoCassa(double attivoContante, double passivoContante, double totVersamenti, double totPrelievi, double netto) {
            this.attivoContante = attivoContante;
            this.passivoContante = passivoContante;
            this.totVersamenti = totVersamenti;
            this.totPrelievi = totPrelievi;
            this.netto = netto;
        }
                
    }
    
    private static final String FILENAME = "cassa.pdf";

    public static enum filtro {
        ANNO_PRECISO, INTERVALLO_DATA
    }
    
    private Fornitore forn_cliente;
    private List<MovimentazioneContante> movimentiContante;
    private List<SaldoCassaMensile> attivo;
    private List<SaldoCassaMensile> passivo;
    private RiepilogoCassa riepilogoCassa;
    private Date dataIniziale;
    private Date dataFinale;
    private int anno;
    private filtro filtroS;
    private String[] totaliAttivo;
    private String[] totaliPassivo;

    private StampaCassa(List<MovimentazioneContante> movimentiContante, List<SaldoCassaMensile> attivo, List<SaldoCassaMensile> passivo, 
            RiepilogoCassa riepilogoCassa, String[] totaliAttivo, String[] totaliPassivo) throws DocumentException {
        
        super(FILENAME);
        this.movimentiContante = movimentiContante;
        this.attivo = attivo;
        this.passivo = passivo;
        this.riepilogoCassa = riepilogoCassa;
        this.totaliAttivo = totaliAttivo;
        this.totaliPassivo = totaliPassivo;
    }
        
    public StampaCassa(int anno, List<MovimentazioneContante> movimentiContante, List<SaldoCassaMensile> attivo, List<SaldoCassaMensile> passivo,
            RiepilogoCassa riepilogoCassa, String[] totaliAttivo, String[] totaliPassivo) throws DocumentException {
        
        this(movimentiContante, attivo, passivo, riepilogoCassa, totaliAttivo, totaliPassivo);
        this.anno = anno;         
        this.filtroS = filtro.ANNO_PRECISO;
    }
    
    public StampaCassa(Fornitore forn_cliente, int anno, List<MovimentazioneContante> movimentiContante, List<SaldoCassaMensile> attivo, List<SaldoCassaMensile> passivo,
            RiepilogoCassa riepilogoCassa, String[] totaliAttivo, String[] totaliPassivo) throws DocumentException {
        
        this(anno, movimentiContante, attivo, passivo, riepilogoCassa, totaliAttivo, totaliPassivo);
        this.forn_cliente = forn_cliente;
    }
    
    public StampaCassa(Date dataI, Date dataF, List<MovimentazioneContante> movimentiContante, List<SaldoCassaMensile> attivo, 
            List<SaldoCassaMensile> passivo, RiepilogoCassa riepilogoCassa, String[] totaliAttivo, String[] totaliPassivo) throws DocumentException {
        
        this(movimentiContante, attivo, passivo, riepilogoCassa, totaliAttivo, totaliPassivo);
        this.dataFinale = dataF;
        this.dataIniziale = dataI;
        this.filtroS = filtro.INTERVALLO_DATA;
    }
    
    public StampaCassa(Fornitore forn_cliente, Date dataI, Date dataF, List<MovimentazioneContante> movimentiContante,
            List<SaldoCassaMensile> attivo, List<SaldoCassaMensile> passivo, RiepilogoCassa riepilogoCassa, String[] totaliAttivo, String[] totaliPassivo) throws DocumentException {
        
        this(dataI, dataF, movimentiContante, attivo, passivo, riepilogoCassa,totaliAttivo, totaliPassivo);
        this.forn_cliente = forn_cliente;
    }
    
    private void initTableEU(String tipo, List<SaldoCassaMensile> lista, String[] totali) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        PdfPCell titolo = new PdfPCell(new Phrase(tipo));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(6);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        int[] widths = {49, 38, 38, 38, 39, 38};
        table.setWidths(widths);
        
        PdfPCell[] intestazione = new PdfPCell[] {
                new PdfPCell(new Phrase("PERIODO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("CONTANTE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("ASSEGNI", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("BONIFICO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("ACCREDITO C/C", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("RIBA", FONT_GRANDE_BOLD))
        };
        
        for (PdfPCell cella : intestazione) {
//            cella.setBorder(NO_BORDER);
            cella.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cella);
        }
        
        for (SaldoCassaMensile movimento : lista) {
            PdfPCell[] riga = new PdfPCell[] {
                    new PdfPCell(new Phrase(movimento.getMeseAnnoRif(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getContanti())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getAssegni())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getBonifico())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getAccredito())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getRiba())), FONT_GRANDE_NORMALE))
            };
            
            for (int i = 0; i < riga.length; i++) {
                if (i == 0)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                else
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                
                table.addCell(riga[i]);
            }
        }
        
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            
            PdfPCell cella = new PdfPCell(new Phrase("\n"));
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
          
        PdfPCell[] riga = {
            new PdfPCell(new Phrase("TOTALI", FONT_BIG_BOLD)),
            new PdfPCell(new Phrase(totali[0], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[1], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[2], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[3], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[4], FONT_GRANDE_BOLD))
        };
        
        for (int i = 0; i < riga.length; i++) {
            if (i == 0)
                riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            else
                riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

            table.addCell(riga[i]);
        }
        
        doc.add(table);
    }
    
    @Override
    protected void stampa() throws DocumentException, IOException {
        doc.open();
        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("PROSPETTO CASSA"));
        
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
        
        String fornCliente = null;
        if (forn_cliente == null)
            fornCliente = "Tutti";
        else
            fornCliente = forn_cliente.toString();        

        PdfPCell[] intestazione;
        if (filtroS == filtro.INTERVALLO_DATA) {
            intestazione = new PdfPCell[] {
                    new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(filtroSa, FONT_GRANDE_NORMALE)),
            };

        } else {
            intestazione = new PdfPCell[] {
                    new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("ANNO", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(String.valueOf(anno), FONT_GRANDE_NORMALE)),
            };
        } 
        
        int[] widths = {80, 160};
        table.setWidths(widths);
        
        for (PdfPCell cella : intestazione) {
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }
        
        doc.add(table);
        
        initTableEU("Entrate", attivo, totaliAttivo);
//        doc.newPage();
        initTableEU("Uscite", passivo, totaliPassivo);
        
        table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        titolo = new PdfPCell(new Phrase("Riepilogo movimenti contante"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(4);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        widths = new int[] {40, 40, 40, 40};
        table.setWidths(widths);
        
        intestazione = new PdfPCell[] {
                new PdfPCell(new Phrase("DATA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("BANCA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("IMPORTO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TIPO", FONT_GRANDE_BOLD))
        };
        
        for (PdfPCell cella : intestazione) {
//            cella.setBorder(NO_BORDER);
            cella.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            table.addCell(cella);
        }
        
        for (MovimentazioneContante movimento : movimentiContante) {
            
            String tipo;
            if (movimento instanceof Versamento)
                tipo = "Versamento";
            else
                tipo = "Prelievo";
            
            PdfPCell[] riga = new PdfPCell[] {
                    new PdfPCell(new Phrase(movimento.getFormattedDataMovimento(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(movimento.getBanca(), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(movimento.getImporto())), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase(tipo, FONT_GRANDE_NORMALE))
            };
            
            for (int i = 0; i < riga.length; i++) {
                if (i == 2)
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                else
                    riga[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                
                table.addCell(riga[i]);
            }
        }
        
        doc.add(table);
        
        table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        titolo = new PdfPCell(new Phrase("Riepilogo cassa"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(100);
        widths = new int[] {40, 40};
        table.setWidths(widths);
        
        PdfPCell[] riepilogo = new PdfPCell[] {
                new PdfPCell(new Phrase("Attivo contante", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(riepilogoCassa.attivoContante)), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("Passivo contante", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(riepilogoCassa.passivoContante)), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("Prelievi", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(riepilogoCassa.totPrelievi)), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("Versamenti", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(riepilogoCassa.totVersamenti)), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("\n", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("\n", FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("Netto in cassa", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(riepilogoCassa.netto)), FONT_GRANDE_BOLD))
        };
        
        for (int i = 0; i < riepilogo.length; i++) {

            if (i == 8 || i == 9)
                riepilogo[i].setBorder(NO_BORDER);
            else {
                if (i % 2 == 0)
                    riepilogo[i].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                else
                    riepilogo[i].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            }
            
            table.addCell(riepilogo[i]);
        }
        
        doc.add(table);
        
        doc.close();
    }
    
}
