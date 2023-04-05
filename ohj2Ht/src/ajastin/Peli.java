package ajastin;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/*
 * @author Eetu Alanen
 * @version 1.0 15.3.2023
 */

public class Peli {
	
    private int tunnusNro;
    private int profiiliNro;
    private String ala;
    private int tunnit;

    private static int seuraavaNro = 1;


    /**
     * Alustetaan harrastus.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Peli() {
        // Vielä ei tarvita mitään
    }


    /**
     * Alustetaan tietyn jäsenen harrastus.  
     * @param jasenNro jäsenen viitenumero 
     */
    public Peli(int profiiliNro) {
        this.profiiliNro = profiiliNro;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Harrastukselle.
     * Aloitusvuosi arvotaan, jotta kahdella harrastuksella ei olisi
     * samoja tietoja.
     * @param nro viite henkilöön, jonka harrastuksesta on kyse
     */
    public void lisaaPeli(int nro) {
        profiiliNro = nro;
        ala = "Dota";
        tunnit = ajastin.Profiili.rand(0, 60);
    }


    /**
     * Tulostetaan harrastuksen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(ala + " " +  " " + tunnit);
    }


    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa pelille seuraavan rekisterinumeron.
     * @return pelin uusi tunnus_nro
     * @example
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }


    /**
     * Palautetaan pelin oma id
     * @return pelin id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }


    /**
     * Palautetaan mille profiilille peli kuuluu
     * @return profiilin id
     */
    public int getProfiiliNro() {
        return profiiliNro;
    }

    /**
     * Testiohjelma Harrastukselle.
     * @param args ei käytössä
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    /**
     * Palauttaa pelin tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return peli tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Peli peli = new Peli();
     *   harrastus.parse("   2   |  2  |   Dota 2  | 1949  ");
     *   harrastus.toString()    === "2|10|Dota 2|1949|";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + profiiliNro + "|" + ala + "|" + tunnit;
    }
    
    
    /**
     * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harrastuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getJasenNro() === 10;
     *   harrastus.toString()    === "2|10|Kalastus|1949|22";
     *   
     *   harrastus.rekisteroi();
     *   int n = harrastus.getTunnusNro();
     *   harrastus.parse(""+(n+20));
     *   harrastus.rekisteroi();
     *   harrastus.getTunnusNro() === n+20+1;
     *   harrastus.toString()     === "" + (n+20+1) + "|10|Kalastus|1949|22";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        profiiliNro = Mjonot.erota(sb, '|', profiiliNro);
        ala = Mjonot.erota(sb, '|', ala);
        tunnit = Mjonot.erota(sb, '|', tunnit);
    }

    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return tunnusNro;
    }


    /**
     * Testiohjelma Pelille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Peli pel = new Peli();
        pel.lisaaPeli(2);
        pel.tulosta(System.out);
    }

}

