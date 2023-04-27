package ajastin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Kerhon jäsenistö joka osaa mm. lisätä uuden jäsenen
 *
 * @author Eetu Alanen
 * @version 1.0, 1.3.2023 
 */
public class Profiilit implements Iterable<Profiili> {
    private int MAX_Profiileja = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
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
        muutettu = true;

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
    protected Profiili anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);

        return alkiot[i];
    }


    /**
     * Lukee profiili kannan tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Profiili profiili = new Profiili();
                profiili.parse(rivi); // voisi olla virhekäsittely
                lisaa(profiili);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }



    /**
     * Tallentaa profiili kannan tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Profiili profiili : this) {
                fo.println(profiili.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    
    /**
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    
    /**
     * Luokka jäsenten iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * jasenet.lisaa(aku1); 
     * jasenet.lisaa(aku2); 
     * jasenet.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Jasen jasen:jasenet)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+jasen.getTunnusNro());           
     * 
     * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Jasen>  i=jasenet.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Jasen jasen = i.next();
     *   ids.append(" "+jasen.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Jasen>  i=jasenet.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class ProfiilitIterator implements Iterator<Profiili> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Profiili next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Profiili> iterator() {
        return new ProfiilitIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Jasenet jasenet = new Jasenet(); 
     *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
     *   // TODO: toistaiseksi palauttaa kaikki jäsenet 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Profiili> etsi(String hakuehto, int k) { 
        Collection<Profiili> loytyneet = new ArrayList<Profiili>(); 
        for (Profiili profiili : this) { 
            loytyneet.add(profiili);  
        } 
        return loytyneet; 
    }
    


    /**
     * Palauttaa ajastimen profiilien lukumäärän
     * @return profiilien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }

    
    
	public void korvaaTaiLisaa(Profiili jasen) throws SailoException {
        int id = jasen.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = jasen;
                muutettu = true;
                return;
            }
        }
        lisaa(jasen);
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
        try {
			Profiilit.tallenna();
		} catch (SailoException e) {
			e.printStackTrace();
		}
    }
	



		
	}
    


