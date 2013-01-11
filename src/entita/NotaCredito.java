/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Michele
 */
public class NotaCredito extends Fattura {

    private List<DescrizioniNotaCredito> descrizioni;    
    private Integer id;
    
    public NotaCredito(int num, Date data, Fornitore cliente, String metPag, double imponibile, double iva, double tot, List<DescrizioniNotaCredito> descrizioni, boolean pagata, String note,
            Date dataPagamento, Date dataScadenza) {
        
        numero = num;
        this.data = data;
        this.cliente = cliente;
        metodoPagamento = metPag;
        this.imponibile = imponibile;
        ivaTot = iva;
        totale = tot;
        this.note = note;
        this.descrizioni = descrizioni;
        this.pagata = pagata;
        this.dataPagamento = dataPagamento;  
        this.dataScadenza = dataScadenza;
    }
    
    public List<DescrizioniNotaCredito> getDescrizioni() {
        return descrizioni;
    }
    
    public void setDescrizioni(List<DescrizioniNotaCredito> descrizioni) {
        this.descrizioni = descrizioni;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
