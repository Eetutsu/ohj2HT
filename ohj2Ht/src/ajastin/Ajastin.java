package ajastin;

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
    private final Profiilit profiilit = new Profiilit();
    private final Pelit pelit = new Pelit();
    
    
    
    /**
     * Lisätään uusi peli ajastimeen
     * @param peli listtävä peli
     */
    public void lisaa(Peli pel) {
    	pelit.lisaa(pel);
    }
    
    
    
    /**
     * Haetaan kaikki profiili pelit
     * @param profiili jolle pelejä haetaan
     * @return tietorakenne jossa viiteet löydetteyihin peleihin
     */
    public List<Peli> annaPelit(Profiili profiili) {
        return pelit.annaPelit(profiili.getTunnusNro());
    }



    /**
     * Palautaa Ajastimen profiilit
     * @return profiilien määrä
     */
    public int getProfiilit() {
        return profiilit.getLkm();
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
     * Palauttaa i:n profiilin
     * @param i monesko profiili palautetaan
     * @return viite i:teen profiiliin
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Profiili annaProfiili(int i) throws IndexOutOfBoundsException {
        return profiilit.anna(i);
    }


    /**
     * Lukee ajastinmen tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        profiilit.lueTiedostosta(nimi);
        pelit.lueTiedostosta(nimi);

    }


    /**
     * Tallettaa ajastimen tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        profiilit.talleta();
        pelit.talleta();

        // TODO: yritä tallettaa toinen vaikka toinen epäonnistuisi
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

            for (int i = 0; i < ajastin.getProfiilit(); i++) {
                Profiili profiili = ajastin.annaProfiili(i);
                System.out.println("Jäsen paikassa: " + i);
                profiili.tulosta(System.out);
                List<Peli> loytyneet = ajastin.annaPelit(profiili);
                for (Peli peli : loytyneet)
                    peli.tulosta(System.out);

            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
