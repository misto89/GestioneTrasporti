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
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Michele
 */
public class StampaAnagrafica extends StampaDocumento {

    private static final String FILENAME = "anagrafica.pdf";
    
    private List<Fornitore> fornitoriClienti;
    private Fornitore anagrafe;
    private boolean lista;
    
    public StampaAnagrafica(List<Fornitore> fornClienti) throws DocumentException {
        super(FILENAME);
        fornitoriClienti = fornClienti;
        doc.setPageSize(PageSize.A4.rotate());
        lista = true;
    }

    public StampaAnagrafica(Fornitore anagrafe) throws DocumentException {
        super(FILENAME);
        this.anagrafe = anagrafe;
        lista = false;
    }
        
    private void stampaLista() throws DocumentException, IOException {        
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("ANAGRAFICA FORNITORI/CLIENTI"));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(8);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        int[] widths = new int[] {40, 30, 20, 20, 20, 20, 35, 35};
        table.setWidths(widths);
        
        PdfPCell[] riga = new PdfPCell[] {
                new PdfPCell(new Phrase("NOME", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TITOLARE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("P. IVA", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("COD. FISCALE", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("TEL.", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("FAX", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("EMAIL", FONT_GRANDE_BOLD)),
                new PdfPCell(new Phrase("INDIRIZZO", FONT_GRANDE_BOLD)),
        };
        
        for (int i = 0; i < riga.length; i++) {
            riga[i].setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

//            if (i == 0)
//                tot[i].setBorder(BORDER_TOP_RIGHT_LEFT);
//            else
//                tot[i].setBorder(BORDER_TOP_RIGHT);
            
            table.addCell(riga[i]);
        }
        
        for (Fornitore anagrafe : fornitoriClienti) {
            riga = new PdfPCell[] {
                new PdfPCell(new Phrase(anagrafe.getNome(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getTitolare(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getPiva(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getCodfiscale(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getTelefono1(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getFax(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getEmail(), FONT_GRANDE_NORMALE)),
                new PdfPCell(new Phrase(anagrafe.getIndirizzoLeg() + ", " + anagrafe.getCittaLeg() + "(" + anagrafe.getProvLeg() + ")", FONT_GRANDE_NORMALE)),
            };
            
            for (PdfPCell cella : riga) {
                cella.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                table.addCell(cella);
            }
        }
        
        doc.add(table);

    }
    
    private void stampaSingolo() throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        
        PdfPCell titolo = new PdfPCell(new Phrase("ANAGRAFICA \"" + anagrafe + "\""));
        
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(1);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(30);
        table.setWidthPercentage(100);
        titolo = new PdfPCell(new Phrase("Dati generali"));
        titolo.setBorder(NO_BORDER);
        titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(titolo);
                
        doc.add(table);
        
        table = new PdfPTable(2);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.setSpacingBefore(10);
        table.setWidthPercentage(70);
               
        String nome = anagrafe.getNome();
        String piva = anagrafe.getPiva();
        String codfisc = anagrafe.getCodfiscale();
        String banca = anagrafe.getBanca();
        String iban = anagrafe.getIban();
        String tel1 = anagrafe.getTelefono1();
        String tel2 = anagrafe.getTelefono2();
        String fax = anagrafe.getFax();
        String email = anagrafe.getEmail();
        String titolare = anagrafe.getTitolare();
        String iscrizioneAlbo = anagrafe.getIscrizioneAlbo();
          
        PdfPCell[] tabella = {
                    new PdfPCell(new Phrase("Codice", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase(String.valueOf(anagrafe.getCod()), FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Nome", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((nome != null) ? nome : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Titolare", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((titolare != null) ? titolare : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Partita IVA", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((piva != null) ? piva : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Codice Fiscale", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((codfisc != null) ? codfisc : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Banca", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((banca != null) ? banca : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("IBAN", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((iban != null) ? iban : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Iscrizione all'albo", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((iscrizioneAlbo != null) ? iscrizioneAlbo : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("E-mail", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((email != null) ? email : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Telefono 1", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((tel1 != null) ? tel1 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Telefono 2", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((tel2 != null) ? tel2 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Fax", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((fax != null) ? fax : "-", FONT_GRANDE_NORMALE))
        };

        int[] widths = {25, 45};
        table.setWidths(widths);
        
        for (PdfPCell cella : tabella) {
//            cella.setBorder(BORDER_LEFT_RIGHT);
            cella.setFixedHeight(25);
            table.addCell(cella);
        }
        
        doc.add(table);
                        
        String indirizzoLeg = anagrafe.getIndirizzoLeg();
        String capLeg = anagrafe.getCapLeg();
        String cittaLeg = anagrafe.getCittaLeg();
        String provLeg = anagrafe.getProvLeg();
        String nazioneLeg = anagrafe.getNazioneLeg();
        
        if (indirizzoLeg != null || capLeg != null || cittaLeg != null || provLeg != null || nazioneLeg != null){
            
            table = new PdfPTable(1);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(30);
            table.setWidthPercentage(100);
            titolo = new PdfPCell(new Phrase("Sede legale"));
            titolo.setBorder(NO_BORDER);
            titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(titolo);
        
            doc.add(table);
        
            table = new PdfPTable(2);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(10);
            table.setWidthPercentage(70);

        
        
            tabella = new PdfPCell[] {
                    new PdfPCell(new Phrase("Indirizzo", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((indirizzoLeg != null) ? indirizzoLeg : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("CAP", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((capLeg != null) ? capLeg : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Città", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((cittaLeg != null) ? cittaLeg : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Provincia", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((provLeg != null) ? provLeg : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Nazione", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((nazioneLeg != null) ? nazioneLeg : "-", FONT_GRANDE_NORMALE))
            };

            widths = new int[] {25, 45};
            table.setWidths(widths);

            for (PdfPCell cella : tabella) {
    //            cella.setBorder(BORDER_LEFT_RIGHT);
                cella.setFixedHeight(25);
                table.addCell(cella);
            }

            doc.add(table);
        }
               
        String indirizzoOp = anagrafe.getIndirizzoOp();
        String capOp = anagrafe.getCapOp();
        String cittaOp = anagrafe.getCittaOp();
        String provOp = anagrafe.getProvOp();
        String nazioneOp = anagrafe.getNazioneOp();
        
        if (indirizzoOp != null || capOp != null || cittaOp != null || provOp != null || nazioneOp != null){
            
            table = new PdfPTable(1);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(30);
            table.setWidthPercentage(100);
            titolo = new PdfPCell(new Phrase("Sede operativa"));
            titolo.setBorder(NO_BORDER);
            titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(titolo);
        
            doc.add(table);

            table = new PdfPTable(2);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(10);
            table.setWidthPercentage(70);


            tabella = new PdfPCell[] {
                    new PdfPCell(new Phrase("Indirizzo", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((indirizzoOp != null) ? indirizzoOp : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("CAP", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((capOp != null) ? capOp : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Città", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((cittaOp != null) ? cittaOp : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Provincia", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((provOp != null) ? provOp : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Nazione", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((nazioneOp != null) ? nazioneOp : "-", FONT_GRANDE_NORMALE))
            };

            widths = new int[] {25, 45};
            table.setWidths(widths);

            for (PdfPCell cella : tabella) {
    //            cella.setBorder(BORDER_LEFT_RIGHT);
                cella.setFixedHeight(25);
                table.addCell(cella);
            }

            doc.add(table);
        }
                        
        String nomeRef1 = anagrafe.getNomeRef1();
        String emailRef1 = anagrafe.getEmailRef1();
        String telRef1 = anagrafe.getTelRef1();
        
        if (nomeRef1 != null || emailRef1 != null || telRef1 != null){
            
            table = new PdfPTable(1);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(30);
            table.setWidthPercentage(100);
            titolo = new PdfPCell(new Phrase("Referente 1"));
            titolo.setBorder(NO_BORDER);
            titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(titolo);
        
            doc.add(table);

            table = new PdfPTable(2);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(10);
            table.setWidthPercentage(70);



            tabella = new PdfPCell[] {
                    new PdfPCell(new Phrase("Nome", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((nomeRef1 != null) ? nomeRef1 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("E-mail", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((emailRef1 != null) ? emailRef1 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Telefono", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((telRef1 != null) ? telRef1 : "-", FONT_GRANDE_NORMALE))
            };

            widths = new int[] {25, 45};
            table.setWidths(widths);

            for (PdfPCell cella : tabella) {
    //            cella.setBorder(BORDER_LEFT_RIGHT);
                cella.setFixedHeight(25);
                table.addCell(cella);
            }

            doc.add(table);
        }
                 
        String nomeRef2 = anagrafe.getNomeRef2();
        String emailRef2 = anagrafe.getEmailRef2();
        String telRef2 = anagrafe.getTelRef2();
        
        if (nomeRef2 != null || emailRef2 != null || telRef2 != null){
            
            table = new PdfPTable(1);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(30);
            table.setWidthPercentage(100);
            titolo = new PdfPCell(new Phrase("Referente 2"));
            titolo.setBorder(NO_BORDER);
            titolo.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(titolo);
        
            doc.add(table);

            table = new PdfPTable(2);
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.setSpacingBefore(10);
            table.setWidthPercentage(70);



            tabella = new PdfPCell[] {
                    new PdfPCell(new Phrase("Nome", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((nomeRef2 != null) ? nomeRef2 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("E-mail", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((emailRef2 != null) ? emailRef2 : "-", FONT_GRANDE_NORMALE)),
                    new PdfPCell(new Phrase("Telefono", FONT_GRANDE_BOLD)),
                    new PdfPCell(new Phrase((telRef2 != null) ? telRef2 : "-", FONT_GRANDE_NORMALE))
            };

            widths = new int[] {25, 45};
            table.setWidths(widths);

            for (PdfPCell cella : tabella) {
    //            cella.setBorder(BORDER_LEFT_RIGHT);
                cella.setFixedHeight(25);
                table.addCell(cella);
            }

            doc.add(table);
        }

        
    }
    
    @Override
    protected void stampa() throws DocumentException, IOException {
        doc.open();
        if (lista)
            stampaLista();
        else
            stampaSingolo();
        doc.close();
    }
    
}