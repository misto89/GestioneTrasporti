/*
 * DbManager.java
 *
 * Created on 14 October 2007, 15:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class manages the connection to the database
 * @author Bruno Carla, Bux Massimo, Casucci Andrea
 */
class DbManager {
    
    /** Creates and returns the connection to the db
     * @return the Connection created
     */
    public Connection CreateConnection() {
        ConfigManager config = new ConfigManager();
        String host = ConfigManager.getProperty("host");
        String db = ConfigManager.getProperty("db");
        String user = ConfigManager.getProperty("user");
        String pwd = ConfigManager.getProperty("password");
        String driver = ConfigManager.getProperty("dbdriver");
        
        Connection conn = null;
        try{
            Class.forName(driver);
            
            conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?user=" + user + "&password=" + pwd);
            System.out.println("Connection to database " + db + " successfully executed");
        }
        
        catch (Exception e){
            System.out.println("ERROR. Connection to database " + db + " not executed!");
            //Log.getLog().info(e);
        }
        
        return conn;
    }
}
