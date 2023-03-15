package ajastin;

import java.util.*;

/*
 * @author Eetu Alanen
 * @version 1.0 15.3.2023
 */

public class Pelit implements Iterable<Peli>{
	
    private String                      tiedostonNimi = "";

    /** peleistä taulukko */
    private final Collection<Peli> alkiot        = new ArrayList<Peli>();
    
    
    /**
     * Pelien alustaminen
     */
    public Pelit() {
    	
    }
    
    
    /**
     * Lisää uuden pelin tietorakenteeseen.  Ottaa pelin omistukseensa.
     * @param dota1 lisättävä peli.  Huom tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Peli dota1) {
        alkiot.add(dota1);
    }
    
    
    /**
     * Lukee profiilien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".pel";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }



    /**
     * Tallentaa profiilien tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }

    
    /**
     * Palauttaa ajastimen pelien lukumäärän
     * @return pelien lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    
    /**
     * Iteraattori kaikkien pelien läpikäymiseen
     * @return peli-iteraattori

     */
    @Override
    public Iterator<Peli> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Haetaan kaikki profiili pelit
     * @param tunnusnro profiilin tunnusnumero jolle pelejä haetaan
     * @return tietorakenne jossa viiteet löydetteyihin peleihin
     */
    public List<Peli> annaPelit(int tunnusnro) {
        List<Peli> loydetyt = new ArrayList<Peli>();
        for (Peli pel : alkiot)
            if (pel.getProfiiliNro() == tunnusnro) loydetyt.add(pel);
        return loydetyt;
    }


    /**
     * Testiohjelma harrastuksille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pelit pelit = new Pelit();
        Peli dota1 = new Peli();
        dota1.lisaaPeli(2);
        Peli dota2 = new Peli();
        dota2.lisaaPeli(1);
        Peli dota3 = new Peli();
        dota3.lisaaPeli(2);
        Peli dota4 = new Peli();
        dota4.lisaaPeli(2);

        pelit.lisaa(dota1);
        pelit.lisaa(dota2);
        pelit.lisaa(dota3);
        pelit.lisaa(dota2);
        pelit.lisaa(dota4);

        System.out.println("============= Pelit testi =================");

        List<Peli> Pelit2 = pelit.annaPelit(2);

        for (Peli pel : Pelit2) {
            System.out.print(pel.getProfiiliNro() + " ");
            pel.tulosta(System.out);
        }

    }


}
