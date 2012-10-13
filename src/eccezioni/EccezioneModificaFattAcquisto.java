/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneModificaFattAcquisto extends RuntimeException {

    public EccezioneModificaFattAcquisto() {
    }

    public EccezioneModificaFattAcquisto(String message) {
        super(message);
    }
   
}
