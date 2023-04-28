package ajastin;

import java.io.*;

import Ht.Tietue;
import fi.jyu.mit.ohj2.Mjonot;



/**
 * Ajastimen jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
 *
 * @author Eetu Alanen
 * @version 1.0, 1.3.2023
 */
public class Profiili implements Cloneable, Tietue{
	private int        tunnusNro;
    private String     nimi           = "";
    private String     nickName           = "";


    private static int seuraavaNro    = 1;
    
    
    /**
     * Palauttaa jäsenen kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 3;
    }


    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return eknn kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }


    /**
     * Alustetaan jäsenen merkkijono-attribuuti tyhjiksi jonoiksi
     * ja tunnusnro = 0.
     */
    public Profiili() {
        // Toistaiseksi ei tarvita mitään
    }



    /**
     * @return jäsenen nimi
     * @example
     * <pre name="test">
     *   Profiili matti = new Profiili();
     *   matti.annaTiedot();
     *   matti.getNimi() =R= "Meikäläinen Matti .*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    public String getNickname() {
    	return nickName;
    }
    
    
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + nimi;
        case 2: return "" + nickName;
        default: return "Äääliö";
        }
    }
    
    
    
    @Override
    public Profiili clone() throws CloneNotSupportedException {
        Profiili uusi;
        uusi = (Profiili) super.clone();
        return uusi;
    }


    /**
    * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
    * @param k kuinka monennen kentän arvo asetetaan
    * @param jono jonoa joka asetetaan kentän arvoksi
    * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
    * @example
    * <pre name="test">
    *   Profiili profiili = new Profiili();
    *   profiili.aseta(1,"Ankka Aku") === null;
    *   profiili.aseta(2,"Drago") === null;
    * </pre>
    */

    
    
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            nickName = tjono;
            return null;
        default: 
        	return "ÄÄliö";
        }
    }

    
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "nimi";
        case 2: return "nickName";
        default: return "Äääliö";
        }
    }

    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle. 
     */
    public void annaTiedot() {
        nimi = "Meikäläinen Matti " + rand(1000, 9999);
        nickName = "Dargo";
    }



    
    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan yläraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
      double n = (yla-ala)*Math.random() + ala;
      return (int)Math.round(n);
    }

    

    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  "
                + nickName);
    }


    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return jäsenen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Profiili matti1 = new Profiili();
     *   matti1.getTunnusNro() === 0;
     *   matti1.rekisteroi();
     *   Profiili matti2 = new Profiili();
     *   matti2.rekisteroi();
     *   int n1 = matti1.getTunnusNro();
     *   int n2 = matti2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }


    /**
     * Palauttaa profiilin tunnusnumeron.
     * @return profiilin tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Profiili profiili = new Profiili();
     *   profiili.parse("3|Eetu Alanen| Tsuppi"); 
     * </pre>  
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();

    }

    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Profiili profiili = new Profiili();
     *   profiili.parse("   3  |  Eetu Alanen | Tsuppi    C");
     *   profiili.getTunnusNro() === 3;
     *
     *   profiili.rekisteroi();
     *   int n = profiili.getTunnusNro();
     *   profiili.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));

        


    }
    
    
    
    /**
     * Tutkii onko jäsenen tiedot samat kuin parametrina tuodun jäsenen tiedot
     * @param jasen jäsen johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Profiili p   = new Profiili();
     *   p.parse("   3  |  Eetu Alanen | Tsuppi     ");
     *   Profiili p2  = new Profiili();
     *   p2.parse("   3  |  Eetu Alanen | Tsuppi     ");
     *   Profiili  p3 = new Profiili();
     *   p3.parse("   3  |  Peetu Palanen|Tsubban    ");
     *   
     *   p.equals(p2) === true;
     *   p2.equals(p) === true;
     *   p.equals(p3) === false;
     *   p3.equals(p2) === false;
     * </pre>
     */
    public boolean equals(Profiili jasen) {
        if ( jasen == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(jasen.anna(k)) ) return false;
        return true;
    }


    /**
     * Ovatko kaksi profiilia samanlaiset
     */
    
    @Override
    public boolean equals(Object jasen) {
        if ( jasen instanceof Profiili ) return equals((Profiili)jasen);
        return false;

    }


    /**
     * Palautetaan tunnusNro
     */
    
    @Override
    public int hashCode() {
        return tunnusNro;
    }


    /**
     * Testiohjelma profiilille.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Profiili matti = new Profiili(), matti2 = new Profiili(), matti3 = new Profiili();
        matti.rekisteroi();
        matti2.rekisteroi();
        matti3.rekisteroi();
        matti.tulosta(System.out);
        matti.annaTiedot();
        matti.tulosta(System.out);

        matti2.annaTiedot();
        matti2.tulosta(System.out);

        matti2.annaTiedot();
        matti2.tulosta(System.out);
        
        matti3.annaTiedot();
        matti3.tulosta(System.out);
        }

}
