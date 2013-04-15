/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entita;

public class Fornitore implements Entity {

    private Integer cod;
    private String nome;
    private String titolare;
    private String piva;
    private String codfiscale;
    private String indirizzoLeg;
    private String telefono1;
    private String telefono2;
    private String fax;
    private String email;
    private String capLeg;
    private String cittaLeg;
    private String provLeg;
    private String nazioneLeg;
    private String banca;
    private String iban;
    private String nomeRef1;
    private String nomeRef2;
    private String emailRef1;
    private String emailRef2;
    private String telRef1;
    private String telRef2;
    private String iscrizioneAlbo; 
    private String indirizzoOp;
    private String capOp;
    private String cittaOp;
    private String provOp;
    private String nazioneOp;
    
    public static final int NUM_CAMPI = 16;
    
    //Tipi di ricerche per i fornitori
    public static final int RIC_NOME = 1;
    public static final int RIC_PIVA = 2;
    public static final int RIC_CODFISC = 3;

    public Fornitore() {
    }

    public Fornitore(Integer cod) {
        this.cod = cod;
    }

    public Fornitore(Integer cod, String nome, String titolare, String piva, String codfiscale, String indirizzoLeg, String telefono1, String telefono2,
            String fax, String email, String capLeg, String cittaLeg, String provLeg, String nazioneLeg, String banca, String iban,
            String iscrizioneAlbo, String indirizzoOp, String cittaOp, String capOp, String provOp, String nazioneOp) {
        
        this.cod = cod;
        this.nome = nome;
        this.titolare = titolare;
        this.piva = piva;
        this.codfiscale = codfiscale;
        this.indirizzoLeg = indirizzoLeg;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fax = fax;
        this.email = email;
        this.capLeg = capLeg;
        this.cittaLeg = cittaLeg;
        this.provLeg = provLeg;
        this.nazioneLeg = nazioneLeg;
        this.banca = banca;
        this.iban = iban;
        this.iscrizioneAlbo = iscrizioneAlbo;
        this.indirizzoOp = indirizzoOp;
        this.cittaOp = cittaOp;
        this.capOp = capOp;
        this.cittaOp = cittaOp;
        this.provOp = provOp;
        this.nazioneOp = nazioneOp;
    }
    
    public Fornitore(Integer cod, String nome, String titolare, String piva, String codfiscale, String indirizzoLeg, String telefono1, String telefono2,
            String fax, String email, String capLeg, String cittaLeg, String provLeg, String nazioneLeg, String banca, String iban, 
            String iscrizioneAlbo, String indirizzoOp, String cittaOp, String capOp, String provOp, String nazioneOp,  String nomeRef1, String nomeRef2, String emailRef1,
            String emailRef2, String telRef1, String telRef2) {
        
        this(cod, nome, titolare, piva, codfiscale, indirizzoLeg, telefono1, telefono2, fax, email, capLeg, cittaLeg, provLeg, nazioneLeg, banca, iban, iscrizioneAlbo, 
                indirizzoOp, cittaOp, capOp, provOp, nazioneOp);
     
        this.nomeRef1 = nomeRef1;
        this.nomeRef2 = nomeRef2;
        this.emailRef1 = emailRef1;
        this.emailRef2 = emailRef2;
        this.telRef1 = telRef1;
        this.telRef2 = telRef2;
         
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getTitolare() {
        return titolare;
    }

    public void setTitolare(String titolare) {
        this.titolare = titolare;
    }

    public String getPiva() {
        return piva;
    }

    public void setPiva(String piva) {
        this.piva = piva;
    }

    public String getCodfiscale() {
        return codfiscale;
    }

    public void setCodfiscale(String codfiscale) {
        this.codfiscale = codfiscale;
    }

    public String getIndirizzoLeg() {
        return indirizzoLeg;
    }

    public void setIndirizzoLeg(String indirizzo) {
        this.indirizzoLeg = indirizzo;
    }
    
    public String getIndirizzoOp() {
        return indirizzoOp;
    }

    public void setIndirizzoOp(String indirizzo) {
        this.indirizzoOp = indirizzo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono) {
        this.telefono1 = telefono;
    }
    
    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono) {
        this.telefono2 = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getCapLeg() {
        return capLeg;
    }

    public void setCapLeg(String cap) {
        this.capLeg = cap;
    }
    
    public String getCittaLeg() {
        return cittaLeg;
    }

    public void setCittaLeg(String citta) {
        this.cittaLeg = citta;
    }
    
    public String getProvLeg() {
        return provLeg;
    }

    public void setProvLeg(String prov) {
        this.provLeg = prov;
    }
    
    public String getNazioneLeg() {
        return nazioneLeg;
    }

    public void setNazioneLeg(String nazione) {
        this.nazioneLeg = nazione;
    }
    
    public String getCapOp() {
        return capOp;
    }

    public void setCapOp(String cap) {
        this.capOp = cap;
    }
    
    public String getCittaOp() {
        return cittaOp;
    }

    public void setCittaOp(String citta) {
        this.cittaOp = citta;
    }
    
    public String getProvOp() {
        return provOp;
    }

    public void setProvOp(String prov) {
        this.provOp = prov;
    }
    
    public String getNazioneOp() {
        return nazioneOp;
    }

    public void setNazioneOp(String nazione) {
        this.nazioneOp = nazione;
    }
    
    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }
    
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getNomeRef1() {
        return nomeRef1;
    }

    public void setNomeRef1(String nome) {
        this.nomeRef1 = nome;
    }
    
    public String getNomeRef2() {
        return nomeRef2;
    }

    public void setNomeRef2(String nome) {
        this.nomeRef2 = nome;
    }
    
    public String getEmailRef1() {
        return emailRef1;
    }

    public void setEmailRef1(String email) {
        this.emailRef1 = email;
    }
    
    public String getEmailRef2() {
        return emailRef2;
    }

    public void setEmailRef2(String email) {
        this.emailRef2 = email;
    }
    
    public String getTelRef1() {
        return telRef1;
    }

    public void setTelRef1(String tel) {
        this.telRef1 = tel;
    }
    
    public String getTelRef2() {
        return telRef2;
    }

    public void setTelRef2(String tel) {
        this.telRef2 = tel;
    }
    
    public void setIscrizioneAlbo(String iscrizioneAlbo) {
        this.iscrizioneAlbo = iscrizioneAlbo;
    }

    public String getIscrizioneAlbo() {
        return iscrizioneAlbo;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cod fields are not set
        if (!(object instanceof Fornitore)) {
            return false;
        }
        Fornitore other = (Fornitore) object;
        if ((this.cod == null && other.cod != null) || (this.cod != null && !this.cod.equals(other.cod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "entita.Fornitori[ cod=" + cod + " ]";

        if (nome != null) {
            if (piva != null){
                if (titolare != null)                    
                    return (this.nome).toUpperCase() + " di " + (this.titolare).toUpperCase() + " - " + this.piva;
                else
                    return (this.nome).toUpperCase() + " - " + this.piva;
            }                
            else {
                if (titolare != null)                    
                    return (this.nome).toUpperCase() + " di " + (this.titolare).toUpperCase() + " - " + this.codfiscale;
                else
                    return (this.nome).toUpperCase() + " - " + this.codfiscale;
            }
            
        } else {
            if (piva != null){
                 if (titolare != null)
                     return " Di " + (this.titolare).toUpperCase() + " - " + this.piva;                
                else
                     return this.piva;
            } else{ 
                if (titolare != null)
                     return " Di " + (this.titolare).toUpperCase() + " - " + this.codfiscale;                
                else
                     return this.codfiscale;
            }
        }
        
    }
    
    public Object[] toArray(){
        Object[] arrayForn = {this.cod, this.nome, this.titolare, this.piva, this.codfiscale, this.banca, this.iban, this.telefono1,
                this.telefono2, this.fax, this.email, this.indirizzoLeg, this.capLeg, this.cittaLeg, this.provLeg, this.nazioneLeg
        };
        return arrayForn;
    }
    
}