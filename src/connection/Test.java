/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import eccezioni.EccezioneConnesioneNonRiuscita;
import java.sql.Connection;

/**
 *
 * @author Michele
 */
class Test {

    public Test() {
        if (!ConfigManager.hasFileConf()) {
            throw new EccezioneConnesioneNonRiuscita("File di configurazione non trovato.");
            
        } else {
            Connection conn = new DbManager().CreateConnection();
            if (conn == null) {
                String error = "Connessione al database non riuscita.\n";
                error += "Le possibili cause potrebbero essere:\n";
                error += "\t- Driver di connessione non valido;\n";
                error += "\t- Server MySQL non attivo;\n";
                error += "\t- Uno o più parametri di connessione non validi.\n";
                throw new EccezioneConnesioneNonRiuscita(error);
            }
        }
    }
    
}
