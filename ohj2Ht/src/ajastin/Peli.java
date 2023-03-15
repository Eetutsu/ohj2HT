package ajastin;

import java.io.OutputStream;
import java.io.PrintStream;

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
     * Testiohjelma Pelille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Peli pel = new Peli();
        pel.lisaaPeli(2);
        pel.tulosta(System.out);
    }

}

