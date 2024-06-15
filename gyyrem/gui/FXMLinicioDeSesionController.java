package gyyrem.gui;

import gyyrem.logic.dao.MiembroDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLinicioDeSesionController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLinicioDeSesionController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblIniciarSesion;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtContraseña;

    @FXML
    private Button btnIniciarSesion;
    
    private MiembroDAO miembroDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        miembroDAO = new MiembroDAO();
    }    
    
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    @FXML
    void iniciarSesion() {
        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();
        int usuario = 0;

        if (!correo.isEmpty() && !contraseña.isEmpty() && correo.length() <= 20) {
            if (correo.matches("[[a-zA-Z0-9.@\u00f1\u00d1]+]+")) {
                int miembroExiste = miembroDAO.existeMiembro(correo, contraseña);
                if (miembroExiste>0) {
                    usuario=2;
                    if (correo.equalsIgnoreCase("Administrador")){
                        usuario=1;
                    }
                    try {
                        FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
                        Parent raiz = inicializador.load();
                        
                        FXMLmenuController menuController = inicializador.getController();
                        menuController.recibirTipoUsuario(usuario,miembroExiste);
                        
                        Scene escena = lblCorreo.getScene();

                        Pane panelPadre = (Pane) escena.getRoot();

                        panelPadre.getChildren().clear();
                        panelPadre.getChildren().add(raiz);

                    } catch (IOException excepcion) {
                        log.error(" Error al abrir la ventana menu: " + excepcion);
                    }
                } else {
                    mostrarMensaje("Cuenta o contraseña incorrectos");
                }
            } else {
                mostrarMensaje("Campos con caracteres invalidos");
            }
        } else {
            mostrarMensaje("Campos con longitud incorrecta");
        }
    }
}
