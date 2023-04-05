package ajastin;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Ajastin-luokka, joka huolehtii profiileista.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" profiilistoon.
 *
 * @author Eetu Alanen
 * @version 1.0, 01.03.2023
 * @version 1.1, 15.3.2023
 */
public class Ajastin {
    private Profiilit profiilit = new Profiilit();
    private Pelit pelit = new Pelit();
    
    
    
    /**
     * Lisätään uusi peli ajastimeen
     * @param peli listtävä peli
     */
    public void lisaa(Peli pel) throws SailoException {
        pelit.lisaa(pel);
    }

    
    
    
    /**
     * Haetaan kaikki profiili pelit
     * @param profiili jolle pelejä haetaan
     * @return tietorakenne jossa viiteet löydetteyihin peleihin
     */
    public List<Peli> annaPelit(Profiili profiili) throws SailoException {
        return pelit.annaPelit(profiili.getTunnusNro());
    }







    /**
     * Poistaa profiilistosta ja peleistä ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako profiilia poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }


    /**
     * Lisää Ajastimeen uuden profiilin
     * @param profiili lisättävä profiili
     * @throws SailoException jos lisäystä ei voida tehdä
     */
    public void lisaa(Profiili Profiili) throws SailoException {
        profiilit.lisaa(Profiili);
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Profiili> etsi(String hakuehto, int k) throws SailoException { 
        return profiilit.etsi(hakuehto, k); 
    } 



    /**
     * Lukee ajastinmen tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        profiilit = new Profiilit(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        pelit = new Pelit();

        setTiedosto(nimi);
        profiilit.lueTiedostosta();
        pelit.lueTiedostosta();

    }


    /**
     * Tallettaa ajastimen tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            profiilit.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            pelit.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }


    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        profiilit.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        pelit.setTiedostonPerusNimi(hakemistonNimi + "pelit");
    }


    /**
     * Testiohjelma ajastimesta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Ajastin ajastin = new Ajastin();

        try {
            // ajastin.lueTiedostosta("kelmit");

            Profiili matti1 = new Profiili(), matti2 = new Profiili();
            matti1.rekisteroi();
            matti1.annaTiedot();
            matti2.rekisteroi();
            matti2.annaTiedot();

            ajastin.lisaa(matti1);
            ajastin.lisaa(matti2);
            
            int id1 = matti1.getTunnusNro();
            int id2 = matti2.getTunnusNro();
            Peli dota11 = new Peli(id1); dota11.lisaaPeli(id1); ajastin.lisaa(dota11);
            Peli dota12 = new Peli(id1); dota12.lisaaPeli(id1); ajastin.lisaa(dota12);
            Peli dota21 = new Peli(id2); dota21.lisaaPeli(id2); ajastin.lisaa(dota21);
            Peli dota22 = new Peli(id2); dota22.lisaaPeli(id2); ajastin.lisaa(dota22);
            Peli dota23 = new Peli(id2); dota23.lisaaPeli(id2); ajastin.lisaa(dota23);


            System.out.println("============= ajastimen testi =================");
            Collection<Profiili> profiilit = ajastin.etsi("", -1);
            int i = 0;
            for (Profiili profiili: profiilit) {
                System.out.println("Jäsen paikassa: " + i);
                profiili.tulosta(System.out);
                List<Peli> loytyneet = ajastin.annaPelit(profiili);
                for (Peli peli : loytyneet)
                    peli.tulosta(System.out);
                i++;
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
