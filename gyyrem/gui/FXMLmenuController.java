package gyyrem.gui;

import gyyrem.logic.dao.MiembroDAO;
import gyyrem.logic.domain.Miembro;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLmenuController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLmenuController.class);
    
    @FXML
    private Button btnCerrarSesion;
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblBienvenido;

    @FXML
    private Label lblNombreGimnasio;

    @FXML
    private Label lblInstrucciones;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuMiembros;

    @FXML
    private MenuItem menuItemRegistrarMiembro;

    @FXML
    private MenuItem menuItemModificarMiembro;

    @FXML
    private MenuItem menuItemActivarMiembro;

    @FXML
    private MenuItem menuItemVerMiembros;

    @FXML
    private MenuItem menuItemRegistrarMembresia;

    @FXML
    private Menu menuClases;

    @FXML
    private MenuItem menuItemRegistrarClase;

    @FXML
    private MenuItem menuItemModificarClase;

    @FXML
    private MenuItem menuItemVerClases;

    @FXML
    private Menu menuPagos;

    @FXML
    private MenuItem menuItemPagosPendientes;

    @FXML
    private MenuItem menuItemPagosRealizados;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private MiembroDAO miembroDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        miembroDAO = new MiembroDAO();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
        if(tipoUsuario==1){
            menuItemModificarMiembro.setVisible(false);
            menuItemVerClases.setVisible(false);
            menuItemPagosPendientes.setVisible(false);
        }
        if(tipoUsuario==2){
            menuItemRegistrarMiembro.setVisible(false);
            menuItemVerMiembros.setVisible(false);
            menuItemActivarMiembro.setVisible(false);
            menuItemRegistrarClase.setVisible(false);
            menuItemModificarClase.setVisible(false);
        }
    }
    
    @FXML
    private void registrarClase(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLregistrarClase.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLregistrarClaseController registrarClaseController = inicializador.getController();
            registrarClaseController.recibirTipoUsuario(tipoUsuario,idMiembro);
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana registrar clase: " + excepcion);
        }
    }
    
    @FXML
    private void verClases(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLverClases.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLverClasesController verClasesController = inicializador.getController();
            verClasesController.recibirTipoUsuario(tipoUsuario,idMiembro);
            verClasesController.mostrarProfesores();
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver clases: " + excepcion);
        }
    }
    
    @FXML
    private void registrarMiembro(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLregistrarMiembro.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLregistrarMiembroController registrarMiembroController = inicializador.getController();
            registrarMiembroController.recibirTipoUsuario(tipoUsuario,idMiembro);
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver registrar miembro: " + excepcion);
        }
    }
    
    @FXML
    private void verMiembros(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLverMiembros.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLverMiembrosController verMiembrosController = inicializador.getController();
            verMiembrosController.recibirTipoUsuario(tipoUsuario,idMiembro);
            verMiembrosController.mostrarMiembros();
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver miembros: " + excepcion);
        }
    }
    
    @FXML
    private void modificarMiembro(){
        Miembro miembroSeleccionado = miembroDAO.obtenerInformacionMiembro(idMiembro);
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmodificarMiembro.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmodificarMiembroController modificarMiembro = inicializador.getController();
            modificarMiembro.recibirTipoUsuario(tipoUsuario,miembroSeleccionado.getIdMiembro(), miembroSeleccionado);
                        
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana modificar miembro: " + excepcion);
        }
    }
    
    @FXML
    private void verMiembrosSuspendidos(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLverMiembrosSuspendidos.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLverMiembrosSuspendidosController verMiembrosSuspendidosController = inicializador.getController();
            verMiembrosSuspendidosController.recibirTipoUsuario(tipoUsuario,idMiembro);
            verMiembrosSuspendidosController.mostrarMiembros();
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver miembros suspendidos: " + excepcion);
        }
    }
    
    @FXML
    private void verPagosRealizados(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLverPagosRealizados.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLverPagosRealizadosController verPagosRealizadosController = inicializador.getController();
            verPagosRealizadosController.recibirTipoUsuario(tipoUsuario,idMiembro);
            verPagosRealizadosController.mostrarPagos();
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver pagos realizados: " + excepcion);
        }
    }
    
    @FXML
    private void verPagosPendientes(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLverPagosPendientes.fxml"));
            Parent raiz = inicializador.load();
            
            FXMLverPagosPendientesController verPagosPendientesController = inicializador.getController();
            verPagosPendientesController.recibirTipoUsuario(tipoUsuario,idMiembro);
            verPagosPendientesController.mostrarClases();
            verPagosPendientesController.mostrarMembresia();
            
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana ver pagos pendientes: " + excepcion);
        }
    }
    
    @FXML
    private void cerrarSesion(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLinicioDeSesion.fxml"));
            Parent raiz = inicializador.load();
                        
            Scene escena = menuBar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana inicio de sesion: " + excepcion);
        }
    }
}
