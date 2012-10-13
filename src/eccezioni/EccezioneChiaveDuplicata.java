/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneChiaveDuplicata extends RuntimeException {

    public EccezioneChiaveDuplicata() {
    }
    
    public EccezioneChiaveDuplicata(String message) {
        super(message);
    }
    
}
