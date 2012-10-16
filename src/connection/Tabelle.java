/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author Michele
 */
class Tabelle {
    
    static final String FORNITORI = "fornitori";
    static final String MEZZI = "mezzi";
    static final String SPEDIZIONI = "spedizioni";
    static final String BOLLE = "bolle_spedizioni";
    static final String FATTURE = "fatture";
    static final String FATT_ACQUISTO = "fattureacq";
    static final String MOVIMENTI = "movimenti";
    static final String MOVIMENTICONTANTE = "movimcontante";
    
    //Rappresenta la tabella "fornitori" con i suoi campi
    static class Fornitori {
        public static final String COD = "cod";
        public static final String NOME = "nome";
        public static final String TITOLARE = "titolare";
        public static final String PIVA = "piva";
        public static final String CODFISCALE = "codfiscale";
        public static final String INDIRIZZO_LEGALE = "indirizzo_leg";
        public static final String TELEFONO1 = "telefono1";
        public static final String TELEFONO2 = "telefono2";
        public static final String FAX = "fax";
        public static final String EMAIL = "email";  
        public static final String CAP_LEGALE = "cap_leg";
        public static final String CITTA_LEGALE = "citta_leg";
        public static final String PROV_LEGALE = "provincia_leg";
        public static final String NAZIONE_LEGALE = "nazione_leg";
        public static final String BANCA = "banca";
        public static final String IBAN = "iban";
        public static final String NOME_REF_1 = "nome_ref_1";
        public static final String NOME_REF_2 = "nome_ref_2";
        public static final String EMAIL_REF_1 = "email_ref_1";
        public static final String EMAIL_REF_2 = "email_ref_2";
        public static final String TEL_REF_1 = "tel_ref_1";
        public static final String TEL_REF_2 = "tel_ref_2";
        public static final String ISCRIZIONE_ALBO = "iscrizione_albo";
        public static final String INDIRIZZO_OP = "indirizzo_op";
        public static final String CAP_OP = "cap_op";
        public static final String CITTA_OP = "citta_op";
        public static final String PROV_OP = "provincia_op";
        public static final String NAZIONE_OP = "nazione_op";
    }
    
    //Rappresenta la tabella "mezzi" con i suoi campi
    static class Mezzi {
        public static final String ID = "id";
        public static final String TARGA = "targa";
        public static final String MARCA = "marca";
        public static final String SCAD_BOLLO = "scad_bollo";
        public static final String SCAD_REVISIONE = "scad_revisione";
        public static final String SCAD_ATP = "scad_atp";
        public static final String SCAD_ASSICURAZIONE = "scad_assicurazione";
    }
    
    //Rappresenta la tabella "spedizioni" con i suoi campi
    static class Spedizioni {
        public static final String NUMERO = "numero";
        public static final String DATA_CARICO = "data_carico";
        public static final String DATA_DOCUMENTO = "data_documento";
        public static final String DESCRIZIONE = "descrizione";
        public static final String FORN_CLIENTE = "forn_cliente";
        public static final String MEZZO = "mezzo";
        public static final String UM = "um";
        public static final String QTA = "qta";
        public static final String TRAZ = "traz";
        public static final String DISTRIB = "distrib";
        public static final String IMPORTO = "importo";
        public static final String SCONTO = "sconto";
        public static final String PERC_IVA = "perciva";
        public static final String PERC_PROVV = "percprovvigione";
        public static final String IMPORTO_PROVV = "importoprovvigione";
        public static final String IVA = "iva";
        public static final String TOTALE = "totale";
        public static final String NOTE = "note";
        public static final String RIENTRATA = "rientrata";
        public static final String NUM_FATTURA = "fattura";
        public static final String DATA_FATTURA = "data_fattura";
        public static final String VALORE_MERCE = "valoremerce";
        public static final String IMPONIBILE = "imponibile";
        public static final String STATO = "stato";
    }
    
    //Rappresenta la tabella "bolle_fatture" con i suoi campi
    static class Bolle {
        public static final String SPEDIZIONE = "num_spedizione";
        public static final String DATA_SPEDIZIONE = "data_spedizione";
        public static final String BOLLA = "bolla";
    }
    
    //Rappresenta la tabella "fatture" con i suoi campi
   static class Fatture {
        public static final String NUMERO = "numero";	 	 	 	 	
	public static final String DATA = "data";
        public static final String METODO_PAGAMENTO = "metodopagamento";
	public static final String IMPORTO = "importo"; 	 	 	
	public static final String SCONTO = "sconto"; 	 	 	 	 	 	
	public static final String PROVVIGIONE = "provvigione";	 	 	 	 	 	
	public static final String IVA = "iva";		 	 	 	 	 	 	
	public static final String TOTALE = "totale";
        public static final String FORFAIT = "forfait";
        public static final String PAGATA = "pagata";
        public static final String NOTE = "note";
    }
    
    //Rappresenta la tabella "fatture acquisto" con i suoi campi
    static class FattureAcquisto {
        public static final String NUMERO = "numero";	 	 	 	 	
	public static final String DATA = "data";
        public static final String METODO_PAGAMENTO = "metodopagamento";
	public static final String IMPORTO = "importo"; 	 	 	
	public static final String SCONTO = "sconto"; 	 	 	 	 	 	
	public static final String IVA = "iva";		 	 	 	 	 	 	
	public static final String TOTALE = "totale";
        public static final String TIPO = "tipo";
        public static final String PAGATA = "pagata";
        public static final String NOTE = "note";
        public static final String FORNITORE = "fornitore";
    }
    
    static class Movimenti {
        public static final String ID = "id";
        public static final String NUM_DOC = "num_doc";
        public static final String DATA = "data";
        public static final String TIPO = "tipo";
        public static final String MET_PAG = "met_pag";
        public static final String VALORE = "valore";
        public static final String FORN_CLIENTE = "forncliente";
    }
    
    static class MovimentoContante {
        public static final String DATA = "data";
        public static final String BANCA = "banca";
        public static final String IMPORTO = "importo";
        public static final String TIPO = "tipo";
        public static final String NUMERO = "numero";
    }
}
