package gyyrem.gui;

import gyyrem.logic.dao.MembresiaDAO;
import gyyrem.logic.dao.MiembroDAO;
import gyyrem.logic.domain.Membresia;
import gyyrem.logic.domain.Miembro;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLregistrarMiembroController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLregistrarMiembroController.class);
    
    @FXML
    private Label lblTitulo;
    
    @FXML
    private Label lblMembresia;

    @FXML
    private Label lblRegistrarMiembro;

    @FXML
    private Label lblNombre;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtApellidoMaterno;

    @FXML
    private Label lblFechaNacimiento;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblApellidoMaterno;

    @FXML
    private Label lblApellidoPaterno;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lblCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private Label lblConstraseña;

    @FXML
    private TextField txtContraseña;
    
    @FXML
    private ChoiceBox<String> cmbMembresia;

    @FXML
    private DatePicker dpFechaNacimiento;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private MiembroDAO miembroDAO;
    
    private MembresiaDAO membresiaDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbMembresia.getItems().addAll("Mensual", "Trimestral", "Semestral", "Anual");
        miembroDAO = new MiembroDAO();
        membresiaDAO = new MembresiaDAO();
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
    
    @FXML
    private void validarCampos() {
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();
        String membresia = cmbMembresia.getValue();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        
        if (correo.isEmpty() || correo.length() > 40) {
            mostrarMensaje("El campo correo no puede estar vacío o tener más de 40 caracteres.");
        } else if (!correo.matches("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}")) {
            mostrarMensaje("El campo correo contiene caracteres no válidos.");
        } else if (nombre.isEmpty() || nombre.length() > 20) {
            mostrarMensaje("El campo nombre no puede estar vacío o tener más de 20 caracteres.");
        } else if (!nombre.matches("[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+") || !nombre.matches("^[^\\s].*")) {
            mostrarMensaje("El campo nombre contiene caracteres no válidos.");
        } else if (apellidoPaterno.isEmpty() || apellidoPaterno.length() > 20) {
            mostrarMensaje("El campo apellido paterno no puede estar vacío o tener más de 20 caracteres.");
        } else if (!apellidoPaterno.matches("[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+") || !apellidoPaterno.matches("^[^\\s].*")) {
            mostrarMensaje("El campo apellido paterno contiene caracteres no válidos.");
        } else if (apellidoMaterno.isEmpty() || apellidoMaterno.length() > 20) {
            mostrarMensaje("El campo apellido materno no puede estar vacío o tener más de 20 caracteres.");
        } else if (!apellidoMaterno.matches("[a-zA-ZÀ-ÿ\\u00f1\\u00d1 ]+") || !apellidoMaterno.matches("^[^\\s].*")) {
            mostrarMensaje("El campo apellido materno contiene caracteres no válidos.");
        } else if (telefono.isEmpty() || telefono.length() != 10 || !telefono.matches("[0-9]+")) {
            mostrarMensaje("El campo telefono debe tener 10 números.");
        } else if (fechaNacimiento== null) {
            mostrarMensaje("Selecciona una fecha de nacimiento.");
        } else if (fechaNacimiento.compareTo(LocalDate.now()) >= 0) {
            mostrarMensaje("La fecha de nacimiento tiene que ser menor a la actual.");
        } else if (contraseña.length() < 8 ||
                   !contraseña.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[.@#$%^&+=!\\-\\_]).+$")) {
            mostrarMensaje("El campo contraseña debe ser de longitud mayor a 8 y contener al menos un dígito, una mayúscula y un carácter especial.");
        } else if (membresia == null) {
            mostrarMensaje("Selecciona una membresía.");
        } else {
            registrarMiembro();
        }
    };
    
    private void registrarMiembro() {
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();
        String contraseña = txtContraseña.getText();
        String membresia = cmbMembresia.getValue();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        LocalDate fechaFin=null;
        int costo=0;
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        
        if(miembroDAO.existeMiembroInicial(correo)>0){
            mostrarMensaje("Correo ya registrado");
            return;
        }
        
        if(miembroDAO.existeMiembroInicialT(telefono)>0){
            mostrarMensaje("Telefono ya registrado");
            return;
        }
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaNacimientoString = fechaNacimiento.format(formato);
        
        int diaDePago = LocalDate.now().getDayOfMonth();
        
        Miembro miembro = new Miembro(0, nombre, apellidoPaterno, apellidoMaterno, 
                                fechaNacimientoString, telefono, correo, contraseña, 
                                diaDePago, "disponible");
        
        int idMiembroRegistrado = miembroDAO.registrarMiembro(miembro);
        
        LocalDate fechaInicio = LocalDate.now();
        
        if(membresia=="Mensual"){
            fechaFin = fechaInicio.plusMonths(1);
            costo=200;
        }
        if(membresia=="Trimestral"){
            fechaFin = fechaInicio.plusMonths(3);
            costo=600;
        }
        if(membresia=="Semestral"){
            fechaFin = fechaInicio.plusMonths(6);
            costo=1200;
        }
        if(membresia=="Anual"){
            fechaFin = fechaInicio.plusMonths(12);
            costo=2400;
        }
        
        String fechaInicioString = fechaInicio.format(formato);
        String fechaFinString = fechaFin.format(formato);
        
        Membresia membresiaNueva = new Membresia(0,fechaInicioString, fechaFinString, membresia, "vigente", costo, idMiembroRegistrado);
        membresiaDAO.registrarMembresia(membresiaNueva);
        
        confirmacion.setTitle("Confirmacion");
        confirmacion.setHeaderText("Registro exitoso");
        confirmacion.setContentText("Miembro registrado correctamente");
        confirmacion.showAndWait();
        regresarVentanaMenu();
    };
    
    @FXML
    private void regresarVentanaMenu(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmenuController menuController = inicializador.getController();
            menuController.recibirTipoUsuario(tipoUsuario,idMiembro);
                        
            Scene escena = btnRegistrar.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
}
