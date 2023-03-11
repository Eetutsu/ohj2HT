package ajastin;


/**
 * Ajastin-luokka, joka huolehtii profiileista.  Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" profiilistoon.
 *
 * @author Eetu Alanen
 * @version 1.0, 01.03.2023
 */
public class Ajastin {
    private final Profiilit profiilit = new Profiilit();


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
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Ajastin ajastin = new Ajastin();
     * Profiili matti1 = new Profiili(), matti2 = new Profiili();
     * matti1.rekisteroi(); matti2.rekisteroi();
     * ajastin.getProfiiliia() === 0;
     * ajastin.lisaa(matti1); ajastin.getProfiiliia() === 1;
     * ajastin.lisaa(matti2); ajastin.getProfiiliia() === 2;
     * ajastin.lisaa(matti1); ajastin.getProfiiliia() === 3;
     * ajastin.getProfiiliia() === 3;
     * ajastin.annaProfiili(0) === matti1;
     * ajastin.annaProfiili(1) === matti2;
     * ajastin.annaProfiili(2) === matti1;
     * ajastin.annaProfiili(3) === matti1; #THROWS IndexOutOfBoundsException 
     * ajastin.lisaa(matti1); ajastin.getProfiiliia() === 4;
     * ajastin.lisaa(matti1); ajastin.getProfiiliia() === 5;
     * ajastin.lisaa(matti1);            #THROWS SailoException
     * </pre>
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
    }


    /**
     * Tallettaa ajastimen tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        profiilit.talleta();
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

            System.out.println("============= ajastimen testi =================");

            for (int i = 0; i < ajastin.getProfiilit(); i++) {
                Profiili Profiili = ajastin.annaProfiili(i);
                System.out.println("Jäsen paikassa: " + i);
                Profiili.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
