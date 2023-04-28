package Ht;

import java.awt.Desktop;
import java.lang.Object;
import org.apache.commons.lang3.time.StopWatch;

import static Ht.ProfiiliDialogController.getFieldId; 
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import ajastin.Ajastin;
import ajastin.Peli;
import ajastin.Profiili;
import ajastin.SailoException;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;

/**
 * @author Eetu Alanen
 * @version 1.0, 30.1.2023
 * @version 1.1, 1.3.2023
 */

      // TODOpackage Ht;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class HarjoitustyoGUIController implements Initializable{
	
    @FXML private ScrollPane panelProfiili;
    @FXML private ListChooser<Profiili> chooserProfiilit;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private StringGrid<Peli> tablePelit;
    @FXML private GridPane gridProfiili;
    @FXML private Label ajastinLabel;
    @FXML private ToggleButton ajastinNappi;
    @FXML private Label yhteensa;
    

   StopWatch s = new StopWatch();


    
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
	}
	
	
    @FXML private void hakuehto() {
    	hae(0);

    }
	
	
    @FXML
    void handleLopeta(MouseEvent event) {
    	tallenna();
    	Platform.exit();
    }
    
    
    @FXML
    void lisaaProfiili() {
        uusiProfiili();
    }

    
    @FXML private void handlePoistaProfiili() {
        poistaProfiili();
    }

    
    @FXML
    void pelinLisays(MouseEvent event) {
    	uusiPeli();
    }
    
    

    
    
    @FXML private void handlePoistaPeli() {
        poistaPeli();
    }
    

    

    @FXML private void handleTulosta() {
        TulostusController.tulosta(null);
        TulostusController tulostusCtrl = TulostusController.tulosta(null);  
    } 


	
    







	@FXML private void handleMuokkaaProfiili() {
        muokkaa(kentta);
    }

    
    @FXML
    void etsi() {
    	hae(0);
    }
	    




	//=========================================================================================== 

	private String ajastinnimi = "peliAjastin";
    private Ajastin ajastin;
    private Profiili profiiliKohdalla;
    private TextArea areaProfiili = new TextArea();
    private TextField edits[] = TietueDialogController.luoKentat(gridProfiili, new Profiili());; 
    private static Peli apupeli = new Peli(); 
    private static Profiili apuprofiili = new Profiili(); 


    int kentta = 0;

    /**
     * avaa ohjelman ensimmäisen kerran, jolloin luetaantiedosotn tiedot
     */
    
    protected void avaa() {
    	lueTiedosto(ajastinnimi);
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa profiilin tiedot.
     * Alustetaan myös profiililistan kuuntelija 
     */
    protected void alusta() {

        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
        
        cbKentat.clear(); 
        for (int k = apuprofiili.ekaKentta(); k < apuprofiili.getKenttia(); k++) 
            cbKentat.add(apuprofiili.getKysymys(k), null); 
        cbKentat.getSelectionModel().select(0); 
        
        edits = TietueDialogController.luoKentat(gridProfiili, apuprofiili);  

        for (TextField edit: edits)  
            if ( edit != null ) {  
                edit.setEditable(false);  
                edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));  
                edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);}); 
            }   
 
        int eka = apupeli.ekaKentta(); 
                 int lkm = apupeli.getKenttia(); 
                 String[] headings = new String[lkm-eka]; 
                 for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apupeli.getKysymys(k); 
                 tablePelit.initTable(headings); 
                 tablePelit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
                 tablePelit.setEditable(false); 
                 tablePelit.setPlaceholder(new Label("Ei vielä pelejä")); 
                  
                 // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
                 tablePelit.setColumnSortOrderNumber(1); 
                 tablePelit.setColumnSortOrderNumber(2); 
                 tablePelit.setColumnWidth(1, 60);
                 tablePelit.setColumnWidth(2, 60);
                 tablePelit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaPeli(); } );
                 tablePelit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaPeli();});
    }
    
    /**
     * Muokkaa peliä
     */

         private void muokkaaPeli() {
        	 int r = tablePelit.getRowNr();
        	 if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        	 Peli har = tablePelit.getObject();
        	 if ( har == null ) return;
        	 int k = tablePelit.getColumnNr()+har.ekaKentta();
        	 try {
        		 har = TietueDialogController.kysyTietue(null, har.clone(), k);
        		 if ( har == null ) return;
        		 ajastin.korvaaTaiLisaa(har); 
        		 naytaPelit(profiiliKohdalla);
        		 tablePelit.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        	 } catch (CloneNotSupportedException  e) { /* clone on tehty */
        	 } 
        	 catch (SailoException e) {
        		 Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        	 }
         }
	


    
    

   /**
    * Näyttää virheen
    * @param virhe
    */

	private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    
    
    /**
     * Poistetaan harrastustaulukosta valitulla kohdalla oleva harrastus. 
     */
    private void poistaPeli() {
        int rivi = tablePelit.getRowNr();
        if ( rivi < 0 ) return;
        Peli harrastus = tablePelit.getObject();
        if ( harrastus == null ) return;
        ajastin.poistaPeli(harrastus);
        naytaPelit(profiiliKohdalla);
        int harrastuksia = tablePelit.getItems().size(); 
        if ( rivi >= harrastuksia ) rivi = harrastuksia -1;
        tablePelit.getFocusModel().focus(rivi);
        tablePelit.getSelectionModel().select(rivi);
    }


    /*
     * Poistetaan listalta valittu jäsen
     */
    private void poistaProfiili() {
        Profiili jasen = profiiliKohdalla;
        if ( jasen == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko jäsen: " + jasen.getNimi(), "Kyllä", "Ei") )
            return;
        ajastin.poista(jasen);
        int index = chooserProfiilit.getSelectedIndex();
        hae(0);
        chooserProfiilit.setSelectedIndex(index);
    }

    
    
    
    
    /** 
     * Tekee uuden tyhjän pelin editointia varten 
     * @throws SailoException 
     */ 
     void uusiPeli(){ 
        if ( profiiliKohdalla == null ) return; 
        try {
        	Peli pel = new Peli(profiiliKohdalla.getTunnusNro()); 
        	pel = TietueDialogController.kysyTietue(null, pel, 0);
        	if (pel == null) return;
        	pel.rekisteroi();
        	ajastin.lisaa(pel);
        	naytaPelit(profiiliKohdalla);
        	tablePelit.selectRow(1000);
        	
        }
        catch (SailoException ex){
        	Dialogs.showMessageDialog("Lisääminen epäonnistui: " + ex.getMessage());
        }

    } 
    
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    protected String lueTiedosto(String nimi)  {
        ajastinnimi = nimi;
        setTitle("Ajastin - " + ajastinnimi);
        try {
            ajastin.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
        
    }



    
    /**
     * Tietojen tallennus
     */
 
        private String tallenna() {
            try {
                ajastin.tallenna();
                return null;
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
                return ex.getMessage();
            }

    }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }


    
    /**
     * Näyttää listasta valitun profiilin tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    protected void naytaProfiili() {
        profiiliKohdalla = chooserProfiilit.getSelectedObject();

        if (profiiliKohdalla == null) return;
        
        TietueDialogController.naytaTietue(edits, profiiliKohdalla); 
        naytaPelit(profiiliKohdalla);


        }
    
    
    /**
     * näyttää pelit ja niiden tiedot
     * @param profiili
     */
    private void naytaPelit(Profiili profiili) {
        tablePelit.clear();
        try {
			tunnitYht();
		} catch (SailoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if ( profiili == null ) return;
        
        try {
            List<Peli> pelit = ajastin.annaPelit(profiili);
            if ( pelit.size() == 0 ) return;
            for (Peli har: pelit)
                naytaPeli(har);
        } catch (SailoException e) {
             naytaVirhe(e.getMessage());
        } 

		
	}


    /**
     * näyttää valitun pelin
     * @param har
     */
    
	private void naytaPeli(Peli har) {
        String[] rivi = har.toString().split("\\|"); // TODO: huono ja tilapäinen ratkaisu
        tablePelit.add(har,rivi[2],rivi[3]);

		
	}

	
	/**
	 * Muokkaa valittua profiilia
	 * @param k
	 */
    private void muokkaa(int k) {
        if ( profiiliKohdalla == null ) return; 
        try { 
            Profiili jasen; 
            jasen = TietueDialogController.kysyTietue(null, profiiliKohdalla.clone(), k);   
            if ( jasen == null ) return; 
            ajastin.korvaaTaiLisaa(jasen); 
            hae(jasen.getTunnusNro()); 
        } catch (CloneNotSupportedException e) { 
            // 
        } catch (SailoException e) { 
            Dialogs.showMessageDialog(e.getMessage()); 
        } 
    }     
    



	

	/**
     * Hakee profiilien tiedot listaan
     * @param jnro profiilin numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnr) {
    	 int jnro = jnr; // jnro jäsenen numero, joka aktivoidaan haun jälkeen 
        String ehto = hakuehto.getText(); 
        if (jnro > 0 || ehto.length() > 0) {
        	Profiili kohdalla = profiiliKohdalla;
        	if (kohdalla != null) jnro = kohdalla.getTunnusNro();
        }
            
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apuprofiili.ekaKentta(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 

        chooserProfiilit.clear();

        int index = 0;
        Collection<Profiili> profiilit;
        try {
            profiilit = ajastin.etsi(ehto, k);
            int i = 0;
            for (Profiili profiili:profiilit) {
                if (profiili.getTunnusNro() == jnro) index = i;
                chooserProfiilit.add(profiili.getNimi(), profiili);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage());

        chooserProfiilit.setSelectedIndex(index);
        }
    }
    
    
    /**
     * Luo uuden profiilin jota aletaan editoimaan 
     */
    protected void uusiProfiili() {
        Profiili uusi = new Profiili();
        uusi = TietueDialogController.kysyTietue(null, uusi, 1);
        uusi.rekisteroi();
        uusi.annaTiedot();
        try {
            ajastin.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    }
    

    /**
     * @param ajastin Ajastin jota käytetään tässä käyttöliittymässä
     */
    public void setAjastin(Ajastin ajastin) {
        this.ajastin = ajastin;
        
        naytaProfiili();
        
    }



    
    
    /**
     * Tulostaa profiilin tiedot
     * @param os tietovirta johon tulostetaan
     * @param profiili tulostettava profiili
     */
    public void tulosta(PrintStream os, final Profiili profiili) {
        os.println("----------------------------------------------");
        profiili.tulosta(os);
        os.println("----------------------------------------------");
        try {
            List<Peli> pelit = ajastin.annaPelit(profiili);
            for (Peli pel :pelit) 
                pel.tulosta(os);     
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Harrastusten hakemisessa ongelmia! " + ex.getMessage());
        }    

    }
    

    /**
     * laskee yhteen tunnit
     * @return
     * @throws SailoException
     */
    public void tunnitYht() throws SailoException {
    	double t = 0;
    	String a;
		List<Peli> p = new ArrayList(ajastin.annaPelit(profiiliKohdalla));
    	for (int i = 0; i<p.size(); i++) {
    		t = t + p.get(i).getTunnit();
    	}
    	
    	if (Double.toString(t) == "") a = "0";
    	else a = Double.toString(t);
    	yhteensa.setText("Yhteensä tunteja : " + a);
    	}
    

}

   




 
