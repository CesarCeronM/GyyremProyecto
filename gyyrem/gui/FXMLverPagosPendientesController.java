package gyyrem.gui;

import gyyrem.logic.dao.MembresiaDAO;
import gyyrem.logic.dao.PagoDAO;
import gyyrem.logic.dao.ReservaDAO;
import gyyrem.logic.domain.Clase;
import gyyrem.logic.domain.Membresia;
import gyyrem.logic.domain.Pago;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLverPagosPendientesController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLmenuController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblPagos;

    @FXML
    private TableView<Clase> tblClases;

    @FXML
    private TableColumn<Clase, String> colNombre;

    @FXML
    private TableColumn<Clase, String> colCostoClase;

    @FXML
    private TableColumn<Clase, String> colHorario;

    @FXML
    private TableView<Membresia> tblMembresia;

    @FXML
    private TableColumn<Membresia, String> colTipoMembresia;

    @FXML
    private TableColumn<Membresia, String> colCostoMembresia;

    @FXML
    private TableColumn<Membresia, String> colFechaDeFin;

    @FXML
    private Button btnPagar;

    @FXML
    private Button btnRegresar;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private ObservableList<Clase> reserva;
    
    private ReservaDAO reservaDAO;
    
    private ObservableList<Membresia> membresia;
    
    private MembresiaDAO membresiaDAO;
    
    private PagoDAO pagoDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTablaClases();
        configurarTablaMembresia();
    }    
    
    private void mostrarMensaje(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    private void configurarTablaClases(){
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCostoClase.setCellValueFactory(new PropertyValueFactory("costo"));
        colHorario.setCellValueFactory(new PropertyValueFactory("horario"));
    }
    
    private void configurarTablaMembresia(){
        colTipoMembresia.setCellValueFactory(new PropertyValueFactory("tipo"));
        colCostoMembresia.setCellValueFactory(new PropertyValueFactory("costo"));
        colFechaDeFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
    }
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro){
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
    }
    
    public void mostrarClases(){
        reserva = FXCollections.observableArrayList();
        reservaDAO = new ReservaDAO();
        
        List<Clase> clasesReservadas= FXCollections.observableArrayList();
        clasesReservadas = reservaDAO.obtenerClasesReservadasPorMiembro(idMiembro);
        
        for (Clase clase : clasesReservadas) {
            reserva.add(clase);
        }
        
        tblClases.setItems(reserva);
    }
    
    public void mostrarMembresia(){
        membresia = FXCollections.observableArrayList();
        membresiaDAO = new MembresiaDAO();
        
        Membresia membresiaRegistrada= membresiaDAO.membresiaEstaPagada(idMiembro);
        
        if(membresiaRegistrada==null){
            tblMembresia.setVisible(false);
        }else{
            membresia.add(membresiaRegistrada);
        
            tblMembresia.setItems(membresia);
        }
        
    }
    
    public void pagar(){
        Clase claseSeleccionada = tblClases.getSelectionModel().getSelectedItem();
        Membresia membresiaSeleccionada = tblMembresia.getSelectionModel().getSelectedItem();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        
        pagoDAO = new PagoDAO();
        if(claseSeleccionada == null && membresiaSeleccionada == null){
            mostrarMensaje("Selecciona un elemento a pagar");
        }else{
            if(claseSeleccionada != null && membresiaSeleccionada != null){
                mostrarMensaje("Selecciona solo un elemento a pagar");
            }else{
                LocalDate fechaActual = LocalDate.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaActualString = fechaActual.format(formato);
                
                if(claseSeleccionada != null && membresiaSeleccionada == null){
                    Pago pago = new Pago(0,claseSeleccionada.getCosto(),fechaActualString,"Pago de clase " + claseSeleccionada.getNombre(), idMiembro);
                    reservaDAO.pagarReserva(claseSeleccionada.getIdClase(), idMiembro);
                    pagoDAO.registrarPago(pago);
                }else{
                    if(claseSeleccionada == null && membresiaSeleccionada != null){
                        Pago pago = new Pago(0,membresiaSeleccionada.getCosto(),fechaActualString,"Pago de membresia " + membresiaSeleccionada.getTipo(), idMiembro);
                        membresiaDAO.pagarMembresia(idMiembro);
                        pagoDAO.registrarPago(pago);
                    }
                }
                confirmacion.setTitle("Confirmacion");
                confirmacion.setHeaderText("Pago exitoso");
                confirmacion.setContentText("Pago registrado correctamente");
                confirmacion.showAndWait();
                regresarVentanaMenu();
            }
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
}
