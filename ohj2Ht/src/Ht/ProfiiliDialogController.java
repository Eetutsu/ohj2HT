package Ht;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ajastin.Profiili;

/**
 * Kysytään jäsenen tiedot luomalla sille uusi dialogi
 * 
 * @author vesal
 * @version 11.1.2016
 *
 */
public class ProfiiliDialogController implements ModalControllerInterface<Profiili>,Initializable  {

    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;    
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }
    
    @FXML private void handleOK() {
        ModalController.closeStage(labelVirhe);
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

// ========================================================    
    private Profiili profiiliKohdalla;
    private TextField edits[];
   

    /**
     * Tyhjentään tekstikentät 
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        edits = new TextField[]{editNimi, editHetu, editKatuosoite, editPostinumero};
    }
    
    
    @Override
    public void setDefault(Profiili oletus) {
        profiiliKohdalla = oletus;
        naytaProfiili(edits, profiiliKohdalla);
    }

    
    @Override
    public Profiili getResult() {
        return profiiliKohdalla;
    }
    
    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }
    
    
    /**
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param jasen näytettävä jäsen
     */
    public static void naytaProfiili(TextField[] edits, Profiili profiili) {
        if (profiili == null) return;
        edits[0].setText(profiili.getNimi());
        edits[1].setText(profiili.getNickname());
    }
    
    
    /**
     * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * TODO: korjattava toimimaan
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null jos painetaan Cancel, muuten täytetty tietue
     */
    public static Profiili kysyProfiili(Stage modalityStage, Profiili oletus) {
        return ModalController.<Profiili, ProfiiliDialogController>showModal(
                    ProfiiliDialogController.class.getResource("UusiProfiili.fxml"),
                    "Ajastin",
                    modalityStage, oletus, null 
                );
    }

	

}
