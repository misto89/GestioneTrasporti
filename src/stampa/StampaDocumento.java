/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stampa;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.DoubleFormatter;

/**
 *
 * @author Michele
 */
public abstract class StampaDocumento {
    
    private String filename;
    
    private static final String TMP_FOLDER = "tmp";
    private static final int DEFAULT_FONT_SIZE = 10;
    private static final String DEFAULT_FONT = FontFactory.COURIER;
    
    protected static final com.itextpdf.text.Font FONT_GRANDE_NORMALE = com.itextpdf.text.FontFactory.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE);
    protected static final com.itextpdf.text.Font FONT_BIG_BOLD = com.itextpdf.text.FontFactory.getFont(DEFAULT_FONT, 11, com.itextpdf.text.Font.BOLD);
    protected static final com.itextpdf.text.Font FONT_GRANDE_BOLD =  com.itextpdf.text.FontFactory.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE, com.itextpdf.text.Font.BOLD);
    protected static final com.itextpdf.text.Font FONT_PICCOLO_NORMALE = com.itextpdf.text.FontFactory.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE-2);
    protected static final com.itextpdf.text.Font FONT_PICCOLO_BOLD =  com.itextpdf.text.FontFactory.getFont(DEFAULT_FONT, DEFAULT_FONT_SIZE-2, com.itextpdf.text.Font.BOLD);

    protected static final int NO_BORDER = 0;
    protected static final int BORDER_LEFT_RIGHT = 12;
    protected static final int BORDER_TOP_LEFT = 5;
    protected static final int BORDER_TOP = 1;
    protected static final int BORDER_TOP_RIGHT = 9;
    protected static final int BORDER_BOTTOM_LEFT = 6;
    protected static final int BORDER_BOTTOM = 2;
    protected static final int BORDER_BOTTOM_RIGHT = 10;
    protected static final int BORDER_RIGHT = 8;
    protected static final int BORDER_LEFT = 20;
    protected static final int BORDER_TOP_RIGHT_LEFT = 13;
    protected static final int BORDER_BOTTOM_RIGHT_LEFT = 14;
    protected static final int BORDER_TOP_AND_BOTTOM = 3;
    
    protected Document doc;
    protected PdfWriter writer;

    public StampaDocumento(String nomeFile) throws DocumentException {
                
        File tmp = new File(TMP_FOLDER);
        if (!tmp.exists())
            tmp.mkdir();
        
        doc = new Document();
        filename = nomeFile;
        
        writer = null;
        try {
            writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(TMP_FOLDER, nomeFile)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StampaFattura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      
    private void apri() throws IOException {
        File myFile = new File(TMP_FOLDER, filename);
        Desktop.getDesktop().open(myFile);
    }
    
    public void printAndOpen() throws DocumentException, IOException {
        stampa();
        apri();
    }
    
    public File printAndGet() throws DocumentException, IOException {
        stampa();
        return new File(TMP_FOLDER, filename);
    }
    
    protected abstract void stampa() throws DocumentException, IOException;
    
    /*
     * Arrotonda a due cifre decimali il valore del double ricevuto come parametro
    */
    protected double roundTwoDecimals(double d) {
        return DoubleFormatter.roundTwoDecimals(d);
    }
    
    protected String doubleToString(double d) {
        return DoubleFormatter.doubleToString(d);
    }
}