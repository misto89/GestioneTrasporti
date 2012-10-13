/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneConnesioneNonRiuscita extends RuntimeException {

    public EccezioneConnesioneNonRiuscita() {
    }

    public EccezioneConnesioneNonRiuscita(String message) {
        super(message);
    }
    
    
}
