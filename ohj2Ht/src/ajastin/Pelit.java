package ajastin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*
 * @author Eetu Alanen
 * @version 1.0 15.3.2023
 */

public class Pelit implements Iterable<Peli>{
	
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "pelit";


    /** peleistä taulukko */
    private final Collection<Peli> alkiot = new ArrayList<Peli>();
    
    
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
        muutettu = true;
    }
    
    
    /**
     * Lukee profiilien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Peli pel = new Peli();
                pel.parse(rivi); // voisi olla virhekäsittely
                lisaa(pel);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * Tallentaa jäsenistön tiedostoon.  
     * TODO Kesken.
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }



    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Peli pel : this) {
                fo.println(pel.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
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
