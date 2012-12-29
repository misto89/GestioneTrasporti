/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import eccezioni.EccezioneDumpNonTrovato;
import eccezioni.EccezioneUtenteNonValido;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;

/**
 *
 * @author Michele
 */
public class Database {
    
    private Runtime runtime = Runtime.getRuntime();
    private String user;
    private String db;
    
    private static final String FILE_NAME = "_gestione_trasporti.sql";
    public static final String DIR_NAME = ConfigManager.getProperty("dumpdir");
        
    public Database() {
        user = ConfigManager.getProperty("user");
        if (!user.equals("server"))
            throw new EccezioneUtenteNonValido("Utente non autorizzato ad eseguire l'operazione richiesta!\nL'operazione può essere eseguita solo sul server.");
        
        db = ConfigManager.getProperty("db");
                
    }
        
    public String backup() throws IOException {
        
        File directory = new File(DIR_NAME);
        if (!directory.exists())
            directory.mkdir();
                       
        java.util.Date date = new java.util.Date();
        String timeStamp = new Timestamp(date.getTime()).toString();
        timeStamp = timeStamp.replaceAll(" ", "");
        timeStamp = timeStamp.replaceAll("-", "");
        timeStamp = timeStamp.replaceAll(":", "");
        timeStamp = timeStamp.replaceAll("\\.", "");

        String path = new File(directory, timeStamp + FILE_NAME).getAbsolutePath();
        
        Process mysql = runtime.exec("mysqldump " + db + " --database -u " + user);
        BufferedReader reader = new BufferedReader(new InputStreamReader(mysql.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            
        String linea = null;
        do {
            linea = reader.readLine(); 
            if (linea != null) {
                writer.write(linea + '\n');
                writer.flush();
            }

        } while(linea != null);
        
        writer.close();
        reader.close();
        mysql.destroy();
        
        return new File(DIR_NAME).getAbsolutePath();

    }
    
    public void restore(File file) throws IOException {
        
        if (!file.exists())
            throw new EccezioneDumpNonTrovato("Nessun file di backup trovato!");
        
        Process mysql = runtime.exec("mysql -u " + user);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(mysql.getOutputStream()));
        
        writer.write("source " + file.getAbsolutePath());
        writer.flush();  
        writer.close();
        
    }
    
    public File[] getAllDumpFiles() {
        return new File(DIR_NAME).listFiles();
    }
    
}
