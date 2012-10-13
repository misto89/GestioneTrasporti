/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.io.File;

/**
 *
 * @author Michele
 */
public class Cleaner {

    public boolean cleanTempFiles() {
        File tmp = new File("tmp");
        if (tmp != null) {
            File[] files = tmp.listFiles();
            if (files != null) {
                for (File file : files) 
                    if (!file.delete())
                        return false;

                if (!tmp.delete())
                    return false;
            }
        }
        
        File log = new File("log");
        File[] dirs = log.listFiles();
        for (File dir : dirs) {
            for (File file : dir.listFiles())
                if (!file.delete())
                    continue;
            
            if (!dir.delete())
                continue;
        }
        
        return true;
    }
    
}
