/*
 * ConfigManager.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the properties files
 *
 * @author pierpaolo
 */
public class ConfigManager {

    private static Properties properties;
    private static String confString;

    /** Creates a new instance of ConfigManager */
    public ConfigManager() {
    }

    //TODO: Stefano: evoluzione configurazione con Properties file
    //non capisco l'utilit? dello statemetn static...metto un brackPoint se non mi fermo mai in modo utile lo elimino!
    static {
        if (properties == null) {
            init();
        }
    }

    private static void init() {
//        String so = System.getProperty("os.name");
        //if (so.contains("Windows"))
            //confString = System.getProperty("user.home") + "\\confConn_PugliaTrasporti.properties";
        //else
            //confString = System.getProperty("user.home") + "/confConn_PugliaTrasporti.properties";
        
        confString = "conf.properties";
        
        try {
            properties = new Properties();
            File f = new File(confString);
            if (f.exists()) {
                properties.load(new FileInputStream(confString));
                //System.out.println(
                //		"Configuration file loaded: " + f.getAbsolutePath());
                System.out.println("Configuration file loaded: "
                        + f.getAbsolutePath());
            } else {
                //Log.getLog().info(
                //		"Configuration file not found: " + f.getAbsolutePath()
                //				+ " - Please check!");
                System.out.println("Configuration file not found: "
                        + f.getAbsolutePath());
            }
        } catch (FileNotFoundException ex) {
            // LOG
            ex.printStackTrace();
        } catch (IOException ex) {
            // LOG
            ex.printStackTrace();
        }
    }

    static boolean hasFileConf() {
        return new File(confString).exists();
    }
    
    /**
     * Sets the String of the configuration file and initializes the
     * ConfigManager
     *
     * @param conf
     *            the String representing the configuration file
     */
    public static void setConfString(String conf) {
        if (conf != null && !conf.equals(confString)) {
            confString = conf;
            init();
        }
    }

    /*
     * static { try { properties = new Properties(); properties.load(new
     * FileInputStream(confString)); } catch (FileNotFoundException ex) { //LOG
     * ex.printStackTrace(); } catch (IOException ex) { //LOG
     * ex.printStackTrace(); } }
     */
    /**
     * Gets the value of the field "name" into the properties file
     *
     * @param name
     *            the name of the field
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    /**
     * Gets the value of the categories field and puts that into a String Array
     *
     * @return an Array containing the values of the categories
     */
    public static String[] getCategories() {
        return ConfigManager.getProperty("categories").split(";");
    }

    /**
     * Sets the value of the field "name" with the value specified and puts it
     * into the properties file
     *
     * @param name
     *            the name of the field
     * @param value
     *            the value of the field
     *
     * In case of setting categories or slots, the value must be separated with
     * a semi-comma.
     *
     * Example: Art;Drama;Family;Cartoon
     */
    public static void setProperty(String name, String value) {
        // String fileName = "./src/di/uniba/itr/conf/itr.properties";
        String fileName = confString;
        properties.setProperty(name, value);
        try {
            properties.store(new FileOutputStream(fileName), null);
        } catch (IOException e) {
            System.out.println(e);
            //TODO:add printStackTrace: Aggiunto per tracciare l'errore in modo pi? evidente
            System.out.println("ATTENZIONE: printStackTrace aggiunto da Stefano, controllare che l'eccezione non sia fisiologica.");
            e.printStackTrace();
        }
    }

    //TODO: Stefano: evoluzione configurazione con Properties file
    public static void setConfProperties(Properties conf) {
        if (conf != null) {
            properties = conf;
        }
    }

    public static String getAllPreferences() {
        FileInputStream fis = null;
        String pref = new String();
        try {

            File f = new File(confString);
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String element = br.readLine();
            while (element != null) {
                pref = pref + element + "\n";
                element = br.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pref;
    }

    public static String getStopWordsList() {
        FileInputStream fis = null;
        String pref = new String();
        try {

            File f = new File("stopwords");
            fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String element = br.readLine();
            while (element != null) {
                pref = pref + element + "\n";
                element = br.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pref;
    }

    public static void updatePreferences(String text) {
        FileOutputStream fos = null;
        try {
            //System.out.println("QUI");
            //System.out.println(id);

            //if(new File(id).delete())System.out.println("d");
            //if(new File(id).mkdir())System.out.println("e");
            File f = new File(confString);
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f, true);
            PrintStream ps = new PrintStream(fos);

            ps.print(text);

            // Catching delle eccezioni
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
            }
        }
    }

    public static void updateStopwords(String text) {
        FileOutputStream fos = null;
        try {
            //System.out.println("QUI");
            //System.out.println(id);

            //if(new File(id).delete())System.out.println("d");
            //if(new File(id).mkdir())System.out.println("e");
            File f = new File("stopwords");
            f.delete();
            f.createNewFile();
            fos = new FileOutputStream(f, true);
            PrintStream ps = new PrintStream(fos);

            ps.print(text);

            // Catching delle eccezioni
        } catch (IOException ex) {
            Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
            }
        }
    }
}
