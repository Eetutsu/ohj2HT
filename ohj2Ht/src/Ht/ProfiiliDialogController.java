package Ht;


import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ajastin.Profiili;
import javafx.scene.control.ScrollPane;

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
    @FXML private ScrollPane panelProfiili;
    @FXML private GridPane gridProfiili;
    


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
    private static Profiili apuprofiili = new Profiili(); // Jäsen jolta voidaan kysellä tietoja.
    private TextField[] edits;
    private int kentta = 0;
    

    /**
     * Luodaan GridPaneen jäsenen tiedot
     * @param gridJasen mihin tiedot luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridJasen) {
        gridJasen.getChildren().clear();
        TextField[] edits = new TextField[apuprofiili.getKenttia()];
        
        for (int i=0, k = apuprofiili.ekaKentta(); k < apuprofiili.getKenttia(); k++, i++) {
            Label label = new Label(apuprofiili.getKysymys(k));
            gridJasen.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridJasen.add(edit, 1, i);
        }
        return edits;
    }
    

   

    /**
     * Tyhjentään tekstikentät 
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
    	for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 

    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        edits = luoKentat(gridProfiili);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosProfiiliin((TextField)(e.getSource())));
        

    }
    
    

    
    
    
    @Override
    public void setDefault(Profiili oletus) {
        profiiliKohdalla = oletus;
        naytaProfiili(edits, profiiliKohdalla);
    }
    
    
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }


    private void setKentta(int kentta) {
        this.kentta = kentta;
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
        kentta = Math.max(apuprofiili.ekaKentta(), Math.min(kentta, apuprofiili.getKenttia()-1));
        edits[kentta].requestFocus();

    }
    
    
    /**
     * Näytetään jäsenen tiedot TextField komponentteihin
     * @param edits taulukko jossa tekstikenttiä
     * @param jasen näytettävä jäsen
     */
    public static void naytaProfiili(TextField[] edits, Profiili jasen) {
        if (jasen == null) return;
        for (int k = jasen.ekaKentta(); k < jasen.getKenttia(); k++) {
            edits[k].setText(jasen.anna(k));
        }
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

    
    protected void kasitteleMuutosProfiiliin(TextField edit) {
        if (profiiliKohdalla == null) return;
        int k = getFieldId(edit,apuprofiili.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = profiiliKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
        
        

        


    

        
        public static Profiili kysyProfiili(Stage modalityStage, Profiili oletus, int kentta) {
            return ModalController.<Profiili, ProfiiliDialogController>showModal(
                        ProfiiliDialogController.class.getResource("UusiProfiili.fxml"),
                        "Ajastin",
                        modalityStage, oletus,
                        ctrl -> ctrl.setKentta(kentta) 
                    );
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
