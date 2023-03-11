package ajastin;

/**
 * Kerhon jäsenistö joka osaa mm. lisätä uuden jäsenen
 *
 * @author Eetu Alanen
 * @version 1.0, 1.3.2023 
 */
public class Profiilit {
    private int MAX_Profiileja = 5;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Profiili alkiot[] = new Profiili[MAX_Profiileja];


    /**
     * Oletusmuodostaja
     */
    public Profiilit() {
        // Attribuuttien oma alustus riittää
    }


    /**
     * Lisää uuden profiilin tietorakenteeseen.  Ottaa profiilin omistukseensa.
     * @param Profiili lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Profiilit Profiilit = new Profiilit();
     * Profiili matti1 = new Profiili(), matti2 = new Profiili();
     * Profiilit.getLkm() === 0;
     * Profiilit.lisaa(matti1); Profiilit.getLkm() === 1;
     * Profiilit.lisaa(matti2); Profiilit.getLkm() === 2;
     * Profiilit.lisaa(matti1); Profiilit.getLkm() === 3;
     * Profiilit.anna(0) === matti1;
     * Profiilit.anna(1) === matti2;
     * Profiilit.anna(2) === matti1;
     * Profiilit.anna(1) == matti1 === false;
     * Profiilit.anna(1) == matti2 === true;
     * Profiilit.anna(3) === matti1; #THROWS IndexOutOfBoundsException 
     * Profiilit.lisaa(matti1); Profiilit.getLkm() === 4;
     * Profiilit.lisaa(matti1); Profiilit.getLkm() === 5;
     * Profiilit.lisaa(matti1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Profiili profiili) throws SailoException {
        if (lkm >= alkiot.length) uusiTaulukko();
        alkiot[lkm] = profiili;
        lkm++;
    }
    
    
    /**
     * Kun profiilien max määrä täyttyy, luodaan uusi Profiili taulukko
     */
    
    public void uusiTaulukko() {
    	MAX_Profiileja = MAX_Profiileja*2;
    	Profiili temp[] = new Profiili[MAX_Profiileja];
    	int pituus = alkiot.length;
    	for (int i = 0; i<pituus; i++) {
    		temp[i] = alkiot[i];
    	}
    	
    	alkiot = temp;
    	
    }


    /**
     * Palauttaa viitteen i:teen profiiliin.
     * @param i monennenko profiilin viite halutaan
     * @return viite profiiliin, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Profiili anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee profiili kannan tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }


    /**
     * Tallentaa profiili kannan tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }


    /**
     * Palauttaa ajastimen profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }


    /**
     * Testiohjelma profiili kannalle
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Profiilit Profiilit = new Profiilit();

        Profiili matti = new Profiili(), matti2 = new Profiili(), matti3 = new Profiili();
        matti.rekisteroi();
        matti.annaTiedot();
        matti2.rekisteroi();
        matti2.annaTiedot();
        matti3.rekisteroi();
        matti3.annaTiedot();

        try {
            Profiilit.lisaa(matti);
            Profiilit.lisaa(matti2);
            Profiilit.lisaa(matti3);

            System.out.println("============= Jäsenet testi =================");

            for (int i = 0; i < Profiilit.getLkm(); i++) {
                Profiili Profiili = Profiilit.anna(i);
                System.out.println("Jäsen nro: " + i);
                Profiili.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
