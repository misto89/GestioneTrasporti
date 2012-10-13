/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;


/**
 *
 * @author Michele
 */
public class EccezioneCredenzialiErrate extends RuntimeException {

    public EccezioneCredenzialiErrate() {
    }

    public EccezioneCredenzialiErrate(String message) {
        super(message);
    }

    
}
