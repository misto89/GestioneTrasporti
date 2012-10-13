/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneEntitaNonValida extends RuntimeException {

    public EccezioneEntitaNonValida() {
    }

    public EccezioneEntitaNonValida(String message) {
        super(message);
    }
    
    
}
