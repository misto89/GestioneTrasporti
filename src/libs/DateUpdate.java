/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.sql.Date;

/**
 *
 * @author Michele
 */
public abstract class DateUpdate {
    
    private static class Data {
        public int day;
        public int month;
        public int year;

        public Data(int anno, int mese, int giorno) {
            day = giorno;
            month = mese;
            year = anno;
        }

        @Override
        public String toString() {
            String mese = Integer.toString(month);
            String giorno = Integer.toString(day);
            
            if (mese.length() == 1)
                mese = "0" + mese;
            
            if (giorno.length() == 1)
                giorno = "0" + giorno;
            
            return year + "-" + mese + "-" + giorno;
        }
                
    }
    
    //Funzione che calcola la data di domani
    private static Data dateUpdate(Data today) {
    
        Data tomorrow;
      
        if (today.day != numberOfDays(today)) {           
            tomorrow = new Data(today.year, today.month, today.day + 1);

        } else if (today.month == 12) { //ultimo dell'anno
            tomorrow = new Data(today.year + 1, 1, 1);

        } else { //ultimo del mese
            tomorrow = new Data(today.year, today.month + 1, 1);
        }

        return tomorrow;
    }
    
    //Funzione per trovare il numero di giorni in un mese
    private static int numberOfDays(Data d) {
        int days;
        final int[] dayPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear(d) && d.month == 2)
            days = 29;
        else
            days = dayPerMonth[d.month - 1];

        return days;
    }
    
    //Funzione che determina se l'anno è bisestile
    private static boolean isLeapYear(Data d) {
        boolean flag;

        if ( (d.year % 4 == 0 && d.year % 100 != 0) || d.year % 400 == 0 )
            flag = true;
        else
            flag = false;

        return flag;
    }
    
    public static Date update(Date date, int days) {
        if (days == 0)
            return date;
        
        String[] data = date.toString().split("-");
        int[] dataInt = new int[data.length];
        for (int i = 0; i < data.length; i++)
            dataInt[i] = Integer.parseInt(data[i]);
        
        Data dataF = new Data(dataInt[0], dataInt[1], dataInt[2]);
        for (int i = 1; i <= days; i++)
            dataF = dateUpdate(dataF);
        
        return Date.valueOf(dataF.toString());
    }
}
