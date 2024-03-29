package ajastin;

import java.io.OutputStream;
import java.io.PrintStream;

import Ht.Tietue;
import fi.jyu.mit.ohj2.Mjonot;

/*
 * @author Eetu Alanen
 * @version 1.0 15.3.2023
 */

public class Peli implements Cloneable, Tietue {
	
    private int tunnusNro;
    private int profiiliNro;
    private String ala;
    private double tunnit;
    

    private static int seuraavaNro = 1;


    /**
     * Alustetaan peli.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Peli() {
        // Vielä ei tarvita mitään
    }


    /**
     * Alustetaan tietyn jäsenen peli.  
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
        out.println(ala + " "  + tunnit );
    }

    public double getTunnit() {
    	return tunnit;
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
     *   peli.parse("2|2|Dota 2|1949");
     *   peli.toString()    === "2|2|Dota 2|1949";
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
     *   Peli peli = new Peli();
     *   peli.parse("2|2|Dota|1949");
     *   peli.getProfiiliNro() === 2;
     *   peli.toString()    === "2|2|Dota|1949";
     *   
     *   peli.rekisteroi();
     *   int n = peli.getTunnusNro();
     *   peli.parse(""+(n+20));
     *   peli.rekisteroi();
     *   peli.getTunnusNro() === n+20+1;
     *   peli.toString()     === "" + (n+20+1) + "|2|Dota|1949";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        profiiliNro = Mjonot.erota(sb, '|', profiiliNro);
        ala = Mjonot.erota(sb, '|', ala);
        tunnit = Mjonot.erota(sb, '|', tunnit);
            }

    /**
     * Onko kaksi peliä samat
     */
    
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    
    
    /**
     * Palauetetaan tunnusNro
     */

    @Override
    public int hashCode() {
        return tunnusNro;
    }

    /**
     * ensimmäisen kentän numero
     */
    
    
    
	public int ekaKentta() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * Montako kentää
	 */

	public int getKenttia() {
		// TODO Auto-generated method stub
		return 2;
	}

	/**
	 * Annetaan kysyttävät tiedot
	 */

	public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Peli";
        case 1: return "Tunnit";
        default: return "Äääliö";
        }
	}

	/**
	 * annetaan pyydetyt tiedot
	 */

	public String anna(int k) {
        switch ( k ) {
        case 0: return "" + ala;
        case 1: return "" + tunnit;
        default: return "Äääliö";
        }
	}

	/**
	 * Kloonataan peli
	 */
	
    @Override
    public Peli clone() throws CloneNotSupportedException {
        Peli uusi;
        uusi = (Peli) super.clone();
        return uusi;
    }
	
/**
 * Asetetaan pelin tiedot	
 */

	@Override
	public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            ala = tjono;
            return null;
        case 1:
        	if(tjono.isEmpty()) tunnit = 0;
        	else tunnit = Double.parseDouble(tjono);
            return null;
        default: 
        	return "ÄÄliö";
        }
	}

    
    
    /**
     * Testiohjelma Pelille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Peli pel = new Peli();

        pel.lisaaPeli(2);
        System.out.println(pel.toString());
        pel.tulosta(System.out);
    }




}

