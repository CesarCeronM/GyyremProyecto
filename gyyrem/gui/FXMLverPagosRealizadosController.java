package gyyrem.gui;

import gyyrem.logic.dao.PagoDAO;
import gyyrem.logic.domain.Pago;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLverPagosRealizadosController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLverPagosRealizadosController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblPagos;

    @FXML
    private TableView<Pago> tblPagos;

    @FXML
    private TableColumn<Pago, String> colFecha;

    @FXML
    private TableColumn<Pago, String> colMonto;

    @FXML
    private TableColumn<Pago, String> colDescripcion;
    
    @FXML
    private TableColumn<Pago, String> colNombre;
    
    @FXML
    private Button btnRegresar;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private ObservableList<Pago> pagos;
    
    private PagoDAO pagoDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
    }
    
    private void configurarTabla(){
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colMonto.setCellValueFactory(new PropertyValueFactory("monto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombreMiembro"));
    }
    
    public void mostrarPagos(){
        pagos = FXCollections.observableArrayList();
        pagoDAO = new PagoDAO();
        List<Pago> pagosRegistrados= FXCollections.observableArrayList();
        
        if(tipoUsuario==2){
            colNombre.setVisible(false);
            pagosRegistrados= pagoDAO.obtenerListaPagos(idMiembro);
        }else{
            pagosRegistrados= pagoDAO.obtenerListaTodosPagos();
        }
        
        for (Pago pago : pagosRegistrados) {
            pagos.add(pago);
        }
        
        tblPagos.setItems(pagos);
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
}
