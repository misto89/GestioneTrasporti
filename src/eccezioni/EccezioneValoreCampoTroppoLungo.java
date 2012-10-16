/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneValoreCampoTroppoLungo extends RuntimeException {

    public EccezioneValoreCampoTroppoLungo() {
    }

    public EccezioneValoreCampoTroppoLungo(String message) {
        super(message);
    }
    
}