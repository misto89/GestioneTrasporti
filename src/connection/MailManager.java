/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author Michele
 */
public class MailManager {
    
    private String host;
    private String port;
    private String from;
    private String user;
    private String pass;

    public MailManager(String host, String port, String from, String user, String pass) {
        this.host = host;
        this.port = port;
        this.from = from;
        this.user = user;
        this.pass = pass;
    }
    
    public String getFrom() {
        return from;
    }

    public String getHost() {
        return host;
    }

    public String getPass() {
        return pass;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }
    
    public static MailManager getParams() {
        String host = ConfigManager.getProperty("smtp");
        String port = ConfigManager.getProperty("port");
        String from = ConfigManager.getProperty("from");
        String user = ConfigManager.getProperty("username");
        String pass = ConfigManager.getProperty("pass");
        
        return new MailManager(host, port, from, user, pass);
    }

}
