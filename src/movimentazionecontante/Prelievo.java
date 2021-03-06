/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movimentazionecontante;

import java.sql.Date;
import java.text.SimpleDateFormat;
import libs.DoubleFormatter;

/**
 *
 * @author Andle
 */
public class Prelievo extends MovimentazioneContante {
            
    public Prelievo(Integer id, Date dataVersamento, String banca, double importo){
        super(id, dataVersamento, banca, importo);
    }

    public Object[] toArray(){
        final String NEW_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        return new Object[] {sdf.format(dataMovimento), this.banca, DoubleFormatter.roundTwoDecimals(importo), "Prelievo"};
    }
}