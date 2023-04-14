package Ht;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ajastin.Ajastin;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;



/**
 * @author eetuoa
 * @version 30.1.2023
 *
 */
public class HarjoitustyoMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("HarjoitustyoGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final HarjoitustyoGUIController harjoitustyoCtrl = (HarjoitustyoGUIController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("harjoitustyo.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Harjoitustyo");
            
            primaryStage.setOnCloseRequest((event) ->{
            	if(!harjoitustyoCtrl.voikoSulkea() ) event.consume();
            });
            Ajastin ajastin = new Ajastin();
            harjoitustyoCtrl.setAjastin(ajastin);  
            ajastin.lueTiedostosta("peliAjastin");
            
            primaryStage.show();
         
     } catch(Exception e) {
        e.printStackTrace();
    }
    
   }
    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}
