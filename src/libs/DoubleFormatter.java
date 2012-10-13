/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Michele
 */
public class DoubleFormatter extends DefaultTableCellRenderer {
    
    DecimalFormat numberFormatter;
    Double d;
        
    public void setValue(Object value) {
        if (numberFormatter == null) {
            numberFormatter = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.ITALY);
        }

        setHorizontalAlignment(JLabel.RIGHT);
        d = (Double) value;

        setText((value == null) ? "" :  numberFormatter.format(d));
    }
    
    public static double roundTwoDecimals(double d) {
        return Math.rint(d * Math.pow(10,2)) / Math.pow(10,2);
    }
    
    public static String doubleToString(double d) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String string = currencyFormatter.format(d);
        return string;
    }
    
    public static double doubleValue(String d) {
        String[] value = d.split(" ");
        value[1] = value[1].replaceAll("\\.", "");
        value[1] = value[1].replace(',', '.');
        
        double doubleValue = Double.parseDouble(value[1]);
        if (value[0].equals("-€"))
            doubleValue *= -1;
        
        return doubleValue;
    }
}
