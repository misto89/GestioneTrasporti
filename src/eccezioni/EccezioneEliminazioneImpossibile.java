/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneEliminazioneImpossibile extends RuntimeException {

    public EccezioneEliminazioneImpossibile() {
    }

    public EccezioneEliminazioneImpossibile(String message) {
        super(message);
    }
    
}
