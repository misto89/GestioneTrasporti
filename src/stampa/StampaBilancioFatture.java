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
import contabilizzazione.BilancioMensile;
import entita.Fornitore;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaBilancioFatture extends StampaDocumento {

//    public static enum filtro {
//
//        ANNO_PRECISO, INTERVALLO_DATA
//    }
//
//    public static enum tipo {
//
//        PAGATE, NON_PAGATE, TUTTE
//    }
    private Fornitore forn_cliente;
    private List<BilancioMensile> bilancio;
//    private Date dataIniziale;
//    private Date dataFinale;
    private int anno;
//    private filtro filtroS;
//    private tipo tipoS;
    private String[] totali;
    private String type;
    private static final String FILENAME = "bilancio_fatture";

    public StampaBilancioFatture(int anno, List bilancio, String type, String[] totali) throws DocumentException {
        super(FILENAME + "_" + type + ".pdf");
        this.anno = anno;
        this.bilancio = bilancio;
//        this.tipoS = tipo;
        this.totali = totali;
//        this.filtroS = filtro.ANNO_PRECISO;
        this.type = type;
    }

    public StampaBilancioFatture(Fornitore forn_cliente, int anno, List bilancio, String type, String[] totali) throws DocumentException {
        this(anno, bilancio, type, totali);
        this.forn_cliente = forn_cliente;
    }

//    public StampaBilancioFatture(Date dataI, Date dataF, List saldi, tipo tipo, String[] totali) throws DocumentException {
//        super(FILENAME);
//        this.saldi = saldi;
//        this.tipoS = tipo;
//        this.dataIniziale = dataI;
//        this.dataFinale = dataF;
//        this.totali = totali;
//        this.filtroS = filtro.INTERVALLO_DATA;
//    }
//
//    public StampaBilancioFatture(Fornitore forn_cliente, Date dataI, Date dataF, List saldi, tipo tipo, String[] totali) throws DocumentException {
//        this(dataI, dataF, saldi, tipo, totali);
//        this.forn_cliente = forn_cliente;
//    }

    @Override
    protected void stampa() throws DocumentException, IOException {

        doc.open();

        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

        PdfPCell titolo = new PdfPCell(new Phrase("PROSPETTO BILANCIO MENSILE - FATTURE " + type.toUpperCase()));
       

        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);

        doc.add(table);

        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);

        String fornCliente;
        if (forn_cliente == null) {
            fornCliente = "Tutti";
        } else {
            fornCliente = forn_cliente.toString();
        }

        PdfPCell[] intestazione = new PdfPCell[]{
                new PdfPCell(new Phrase("CLIENTE/FORNITORE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase(fornCliente, FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase("ANNO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase(String.valueOf(anno), FONT_GRANDE_NORMALE))
        };
        
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
        widths = new int[]{40, 40, 40, 40};
        table.setWidths(widths);

        if (type.equals("emesse")) {
            intestazione = new PdfPCell[]{
                new PdfPCell(new Phrase("PERIODO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE INCASSATO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE DA INCASSARE", FONT_GRANDE_BOLD))
            };
        } else {
            intestazione = new PdfPCell[]{
                new PdfPCell(new Phrase("PERIODO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE PAGATO", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TOTALE DA PAGARE", FONT_GRANDE_BOLD))
            };
        }

        intestazione[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[1].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[2].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        intestazione[3].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//        intestazione[4].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        for (PdfPCell cella : intestazione) {
//            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }

        for (BilancioMensile b : bilancio) {

            PdfPCell[] riga = new PdfPCell[]{
                new PdfPCell(new Phrase(b.getMeseAnnoRif(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(b.getTotale())), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(b.getTotaleLiquidato())), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(doubleToString(roundTwoDecimals(b.getTotaleDaLiquidare())), FONT_GRANDE_NORMALE))
            };

            riga[0].setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            riga[1].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            riga[2].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            riga[3].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
//            riga[4].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

            for (PdfPCell cella : riga) {
//                cella.setBorder(com.itextpdf.text.Rectangle.ALIGN_CENTER);
                table.addCell(cella);
            }
        }

        for (int i = 0; i < table.getNumberOfColumns(); i++) {

            PdfPCell cella = new PdfPCell(new Phrase("\n"));
            cella.setBorder(NO_BORDER);
            table.addCell(cella);
        }


        PdfPCell[] rigaTot = {
            new PdfPCell(new Phrase("TOTALI", FONT_BIG_BOLD)),
            new PdfPCell(new Phrase(totali[0], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[1], FONT_GRANDE_BOLD)),
            new PdfPCell(new Phrase(totali[2], FONT_GRANDE_BOLD)),
//            new PdfPCell(new Phrase(totali[3], FONT_GRANDE_BOLD))
        };

        rigaTot[0].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        rigaTot[1].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        rigaTot[2].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        rigaTot[3].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
//        rigaTot[4].setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        for (PdfPCell cella : rigaTot) {
            table.addCell(cella);
        }

        doc.add(table);

        doc.close();

    }
}