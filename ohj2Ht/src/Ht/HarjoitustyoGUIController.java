package Ht;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import ajastin.Ajastin;
import ajastin.Profiili;
import ajastin.SailoException;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
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

    
    private String ajastinnimi = "kelmit";

    

    @FXML private void handleTulosta() {
        TulostusController.tulosta(null);
        TulostusController tulostusCtrl = TulostusController.tulosta(null);  
    } 


	
    @FXML
    void aloitaAjastin(MouseEvent event) {
    	Dialogs.showMessageDialog("Ei osata vielä ajastaa");
    }

    
    @FXML
    void lisaaProfiili() {
        uusiProfiili();
    }

    
    @FXML
    void pelinLisays(MouseEvent event) {
    	ModalController.showModal(HarjoitustyoGUIController.class.getResource("UusiPeli.fxml"), "Peli", null, "");
    }

    
    @FXML
    void suljeOhjelma(MouseEvent event) {
    	tallenna();
    	Platform.exit();
    }
    
    
    @FXML
    void etsi() {
    	Dialogs.showMessageDialog("Ei osata vielä etsiä");
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


	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
	}
	
	
	 @FXML private void handleHakuehto() {
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



	//=========================================================================================== 

    private Ajastin ajastin;
    private Profiili profiiliKohdalla;
    private TextArea areaProfiili = new TextArea();
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa profiilin tiedot.
     * Alustetaan myös profiililistan kuuntelija 
     */
    protected void alusta() {
        panelProfiili.setContent(areaProfiili);
        areaProfiili.setFont(new Font("Courier New", 12));
        panelProfiili.setFitToHeight(true);
        
        chooserProfiilit.clear();
        chooserProfiilit.addSelectionListener(e -> naytaProfiili());
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
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        ajastinnimi = nimi;
        setTitle("Ajastin - " + ajastinnimi);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
        // if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }

    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */


    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
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

        areaProfiili.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaProfiili)) {
            profiiliKohdalla.tulosta(os);
        }
    }
    
    
    /**
     * Hakee profiilien tiedot listaan
     * @param jnro profiilin numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int jnro) {
        chooserProfiilit.clear();

        int index = 0;
        for (int i = 0; i < ajastin.getProfiilit(); i++) {
            Profiili profiili = ajastin.annaProfiili(i);
            if (profiili.getTunnusNro() == jnro) index = i;
            chooserProfiilit.add(profiili.getNimi(), profiili);
        }
        chooserProfiilit.setSelectedIndex(index);
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
    }	
}

   




 
