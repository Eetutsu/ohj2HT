package Ht;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Eetu Alanen
 * @version 1.0, 30.1.2023
 * @version 1.1, 1.3.2023
 */

      // TODOpackage Ht;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class HarjoitustyoGUIController implements Initializable{
	
    @FXML private ScrollPane panelProfiili;
    @FXML private ListChooser<Profiili> chooserProfiilit;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private StringGrid<Peli> tablePelit;
    @FXML private TextField editNimi; 
    @FXML private TextField editNickname; 


    
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
	}
	
	
    @FXML private void handleHakuehto() {
        if ( profiiliKohdalla != null )
            hae(profiiliKohdalla.getTunnusNro());

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
        Dialogs.showMessageDialog("Vielä ei osata poistaa jäsentä");
    }

    
    @FXML
    void pelinLisays(MouseEvent event) {
    	uusiPeli();
    }
    
    
    @FXML private void handleMuokkaaHarrastus() {
        ModalController.showModal(HarjoitustyoGUIController.class.getResource("HarrastusDialogView.fxml"), "Harrastus", null, "");
    }
    
    
    @FXML private void handlePoistaHarrastus() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa harrastusta");
    }
    
    
    @FXML private void handleTietoja() {
        // Dialogs.showMessageDialog("Ei osata vielä tietoja");
        ModalController.showModal(HarjoitustyoGUIController.class.getResource("AboutView.fxml"), "Kerho", null, "");
    }

    
    

    

    @FXML private void handleTulosta() {
        TulostusController.tulosta(null);
        TulostusController tulostusCtrl = TulostusController.tulosta(null);  
    } 


	
    @FXML
    void aloitaAjastin(MouseEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä ajastaa");
    }

    


    


    

    
    
    @FXML
    void etsi() {
        String hakukentta = cbKentat.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
    }
    
    
    @FXML private void handleTallenna() {
        tallenna();
    }


    @FXML
    void annaNimi() {
    	Dialogs.showMessageDialog("Ei osata vielä antaa nimeä");
    }

    @FXML
    void annaTunnit() {
    	Dialogs.showMessageDialog("Ei osata vielä tunteja");
    }

    @FXML
    void peliOk(MouseEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä tallentaa");
    }



	

	    
	    

	    


	    

	    




	//=========================================================================================== 

	private String ajastinnimi = "peliAjastin";
    private Ajastin ajastin;
    private Profiili profiiliKohdalla;
    private TextArea areaProfiili = new TextArea();
    private TextField edits[]; 

    
    
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
        
        edits = new TextField[]{editNimi, editNickname}; 
        
    }
    
    
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
     * Tekee uuden tyhjän pelin editointia varten 
     * @throws SailoException 
     */ 
    public void uusiPeli(){ 
        if ( profiiliKohdalla == null ) return; 
        Peli pel = new Peli(); 
        pel.rekisteroi(); 
        pel.lisaaPeli(profiiliKohdalla.getTunnusNro()); 
        try {
            ajastin.lisaa(pel);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        } 
        hae(profiiliKohdalla.getTunnusNro());   

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
        
        ProfiiliDialogController.naytaProfiili(edits, profiiliKohdalla); 
        naytaPelit(profiiliKohdalla);


        }
    
    
    
    private void naytaPelit(Profiili profiili) {
        tablePelit.clear();
        if ( profiili == null ) return;
        
        try {
            List<Peli> pelit = ajastin.annaPelit(profiili);
            if ( pelit.size() == 0 ) return;
            for (Peli har: pelit)
                naytaPeli(har);
        } catch (SailoException e) {
            // naytaVirhe(e.getMessage());
        } 

		
	}


	private void naytaPeli(Peli har) {
        String[] rivi = har.toString().split("\\|"); // TODO: huono ja tilapäinen ratkaisu
        tablePelit.add(har,rivi[2],rivi[3]);

		
	}

	
    private void muokkaa() {
        ProfiiliDialogController.kysyProfiili(null, profiiliKohdalla);
    }

	

	/**
     * Hakee profiilien tiedot listaan
     * @param jnro profiilin numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        int k = cbKentat.getSelectionModel().getSelectedIndex();
        String ehto = hakuehto.getText(); 
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s)", k, ehto));
        else
            naytaVirhe(null);

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



}

   




 
