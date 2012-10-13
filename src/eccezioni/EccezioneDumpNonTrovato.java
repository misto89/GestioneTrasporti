/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eccezioni;

/**
 *
 * @author Michele
 */
public class EccezioneDumpNonTrovato extends RuntimeException {

    public EccezioneDumpNonTrovato() {
    }

    public EccezioneDumpNonTrovato(String message) {
        super(message);
    }
    
}
