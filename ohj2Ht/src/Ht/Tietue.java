package Ht;


/**
 * Rajapinta tietueelle johon voidaan taulukon avulla rakentaa 
 * "attribuutit".
 * @author vesal
 * @version Mar 23, 2012
 * @example
 */
public interface Tietue {

    
    /**
     * @return tietueen kenttien lukumäärä
     * @example
     * <pre name="test">
     *   #import ajastin.Profiili;
     *   Profiili har = new Profiili();
     *   har.getKenttia() === 3;
     * </pre>
     */
    public abstract int getKenttia();


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     * @example
     * <pre name="test">
     *   Profiili har = new Profiili();
     *   har.ekaKentta() === 1;
     * </pre>
     */
    public abstract int ekaKentta();


    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     * @example
     * <pre name="test">
     *   Profiili har = new Profiili();
     *   har.getKysymys(2) === "nickName";
     * </pre>
     */
    public abstract String getKysymys(int k);


    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Profiili har = new Profiili();
     *   har.parse("   2   |  Eetu Alanen  |   Tsuppi ");
     *   har.anna(0) === "2";   
     *   har.anna(1) === "Eetu Alanen";   
     *   har.anna(2) === "Tsuppi";    
     * </pre>
     */
    public abstract String anna(int k);

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     */
    public abstract String aseta(int k, String s);


    /**
     * Tehdään identtinen klooni tietueesta
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Profiili har = new Profiili();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Object kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     *   kopio instanceof Profiili === true;
     * </pre>
     */
    public abstract Tietue clone() throws CloneNotSupportedException;


    /**
     * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tietue tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Profiili Profiili = new Profiili();
     *   Profiili.parse("   0   |  Eetu Alanen  |   Tsuppi  ");
     * </pre>
     */
    @Override
    public abstract String toString();

}