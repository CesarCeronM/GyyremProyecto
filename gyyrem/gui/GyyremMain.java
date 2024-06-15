package gyyrem.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.log4j.Logger;

public class GyyremMain extends Application {
    private static final Logger log = Logger.getLogger(GyyremMain.class);
    
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane raiz = FXMLLoader.load(getClass().getResource("FXMLinicioDeSesion.fxml"));
        
            Scene escena = new Scene(raiz);
        
            primaryStage.setScene(escena);
            primaryStage.show();
        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana inicial: " + excepcion);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}