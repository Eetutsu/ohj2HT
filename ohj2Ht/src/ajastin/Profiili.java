package ajastin;

import java.io.*;



/**
 * Ajastimen jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
 *
 * @author Eetu Alanen
 * @version 1.0, 1.3.2023
 */
public class Profiili {
	private int        tunnusNro;
    private String     nimi           = "";
    private String     nickName           = "";


    private static int seuraavaNro    = 1;


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