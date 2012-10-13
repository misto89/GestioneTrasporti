/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneUtenteNonValido extends RuntimeException {

    public EccezioneUtenteNonValido() {
    }

    public EccezioneUtenteNonValido(String message) {
        super(message);
    }
    
}
