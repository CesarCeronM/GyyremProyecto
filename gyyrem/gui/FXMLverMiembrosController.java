package gyyrem.gui;

import gyyrem.logic.dao.MiembroDAO;
import gyyrem.logic.domain.Miembro;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLverMiembrosController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLverMiembrosController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblMiembros;

    @FXML
    private TableView<Miembro> tblMiembros;

    @FXML
    private TableColumn<Miembro, String> colNombre;

    @FXML
    private TableColumn<Miembro, String> colApellidoPaterno;

    @FXML
    private TableColumn<Miembro, String> colApellidoMaterno;

    @FXML
    private TableColumn<Miembro, String> colTelefono;

    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnSuspender;

    @FXML
    private Button btnRegresar;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private ObservableList<Miembro> miembros;
    
    private MiembroDAO miembroDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
    }
    
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void configurarTabla(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }
    
    void mostrarMiembros(){
        miembros = FXCollections.observableArrayList();
        miembroDAO = new MiembroDAO();
        
        List<Miembro> miembrosRegistrados= FXCollections.observableArrayList();
        miembrosRegistrados= miembroDAO.obtenerListaMiembro();
        
        for (Miembro miembro : miembrosRegistrados) {
            miembros.add(miembro);
        }
        
        tblMiembros.setItems(miembros);
    }
    
    @FXML
    private void suspenderMiembro(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        Miembro miembroSeleccionado = tblMiembros.getSelectionModel().getSelectedItem();
        if(miembroSeleccionado==null){
            mostrarMensaje("Selecciona un miembro en la tabla");
            return;
        }
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("Suspender miembro");
        confirmacion.setContentText("¿Está seguro de desea suspender a este miembro?");

        Optional<ButtonType> resultadoVentana = confirmacion.showAndWait();
        if (resultadoVentana.isPresent() && resultadoVentana.get() == ButtonType.OK) {
            miembroDAO.suspenderMiembro(miembroSeleccionado.getIdMiembro());
            confirmacion.getButtonTypes().setAll(new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE));
            confirmacion.setTitle("Confirmacion");
            confirmacion.setHeaderText("Suspencion exitosa");
            confirmacion.setContentText("Miembro suspendido correctamente");
            confirmacion.showAndWait();
            regresarVentanaMenu();
        }
    }
    
    @FXML
    private void regresarVentanaMenu(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmenuController menuController = inicializador.getController();
            menuController.recibirTipoUsuario(tipoUsuario,idMiembro);
                        
            Scene escena = btnRegresar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
    
    @FXML
    private void modificarMiembro(){
        Miembro miembroSeleccionado = tblMiembros.getSelectionModel().getSelectedItem();
        if(miembroSeleccionado==null){
            mostrarMensaje("Selecciona un miembro en la tabla");
            return;
        }
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmodificarMiembro.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmodificarMiembroController modificarMiembro = inicializador.getController();
            modificarMiembro.recibirTipoUsuario(tipoUsuario,miembroSeleccionado.getIdMiembro(), miembroSeleccionado);
                        
            Scene escena = btnRegresar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
}
