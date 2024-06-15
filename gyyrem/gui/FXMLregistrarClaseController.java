package gyyrem.gui;

import gyyrem.logic.dao.ClaseDAO;
import gyyrem.logic.domain.Clase;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

public class FXMLregistrarClaseController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLregistrarClaseController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblRegistrarClase;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCosto;

    @FXML
    private Label lblProfesor;

    @FXML
    private Label lblCupo;

    @FXML
    private Label lblHorario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtCupo;

    @FXML
    private TextField txtHorario;
    
    @FXML
    private DatePicker dpFecha;

    @FXML
    private ChoiceBox<String> cmbProfesor;

    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private CheckBox chkLunes;
    
    @FXML
    private CheckBox chkMartes;
    
    @FXML
    private CheckBox chkMiercoles;
    
    @FXML
    private CheckBox chkJueves;
    
    @FXML
    private CheckBox chkViernes;
    
    private int tipoUsuario=0;
    
    private int idMiembro=0;
    
    private ClaseDAO claseDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProfesor.getItems().addAll("Juan", "Maria", "Carlos", "Ana", "Jose");
        claseDAO = new ClaseDAO();
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
        String costo = txtCosto.getText();
        String cupo = txtCupo.getText();
        String horario = txtHorario.getText();
        String profesor = cmbProfesor.getValue();
        LocalDate fecha = dpFecha.getValue();
        
        if (nombre.isEmpty() || nombre.length() > 20) {
            mostrarMensaje("El campo nombre no puede estar vacío o tener más de 20 caracteres.");
        } else if (costo.isEmpty() || costo.length() > 4) {
            mostrarMensaje("El campo costo no puede estar vacío o tener más de 4 caracteres.");
        } else if (cupo.isEmpty()) {
            mostrarMensaje("El campo cupo no puede estar vacío.");
        } else if (horario.isEmpty() || horario.length() > 20) {
            mostrarMensaje("El campo hora no puede estar vacío.");
        } else if (!nombre.matches("[a-zA-ZÀ-ÿ0-9\\u00f1\\u00d1 ]+") || !nombre.matches("^[^\\s].*")) {
            mostrarMensaje("El campo nombre contiene caracteres no válidos.");
        } else if (!costo.matches("[0-9]+")) {
            mostrarMensaje("El campo costo solo puede contener números.");
        } else if (!cupo.matches("[0-9]+")) {
            mostrarMensaje("El campo cupo solo puede contener números.");
        } else if (fecha == null) {
            mostrarMensaje("Selecciona una fecha.");
        } else if (fecha.compareTo(LocalDate.now()) < 0) {
            mostrarMensaje("La fecha tiene que ser mayor a la de hoy.");
        } else if (!horario.matches("^([01]\\d|2[0-3]):([0-5]\\d)-([01]\\d|2[0-3]):([0-5]\\d)$") || !horario.matches("^[^\\s].*")) {
            mostrarMensaje("Usa un formato (HH:MM-HH:MM) en un horario de 24 horas para la hora.");
        } else if (profesor == null) {
            mostrarMensaje("Selecciona un profesor.");
        } else if (!chkLunes.isSelected() && !chkMartes.isSelected() && !chkMiercoles.isSelected() && !chkJueves.isSelected() && !chkViernes.isSelected()) {
            mostrarMensaje("Selecciona al menos un día.");
        } else {
            registrarClase();
        }
    };
    
    private void registrarClase() {
        String nombre = txtNombre.getText();
        String costo = txtCosto.getText();
        String cupo = txtCupo.getText();
        String horario = txtHorario.getText();
        String profesor = cmbProfesor.getValue();
        LocalDate fecha = dpFecha.getValue();
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);

        int costoInt = Integer.parseInt(costo);
        int cupoInt = Integer.parseInt(cupo);
        int idProfesor = 0;
        if (cupoInt > 30) {
            mostrarMensaje("El campo cupo no puede ser mayor a 30");
            return;
        }
        if(profesor.equals("Juan")) {
            idProfesor = 1;
        } else if (profesor.equals("Maria")) {
            idProfesor = 2;
        } else if (profesor.equals("Carlos")) {
            idProfesor = 3;
        } else if (profesor.equals("Ana")) {
            idProfesor = 4;
        } else if (profesor.equals("Jose")) {
            idProfesor = 5;
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaString = fecha.format(formato);

        StringBuilder dias = new StringBuilder();
        if (chkLunes.isSelected()) {
            dias.append("Lunes, ");
        }
        if (chkMartes.isSelected()) {
            dias.append("Martes, ");
        }
        if (chkMiercoles.isSelected()) {
            dias.append("Miércoles, ");
        }
        if (chkJueves.isSelected()) {
            dias.append("Jueves, ");
        }
        if (chkViernes.isSelected()) {
            dias.append("Viernes, ");
        }

        if (dias.length() > 0) {
            dias.setLength(dias.length() - 2);
        }

        String horarios = "A partir del " + fechaString + ", los días " + dias.toString() + " en el horario de: " + horario;

        Clase clase = new Clase(0, cupoInt, nombre, horarios, "disponible", costoInt, idProfesor);
        
        if (claseDAO.existeClase(dias.toString(), horario, idProfesor)) {
            mostrarMensaje("Ya existe una clase con el mismo profesor y horario en los mismos días.");
        } else {
            claseDAO.registrarClase(clase);
            confirmacion.setTitle("Confirmacion");
            confirmacion.setHeaderText("Registro exitoso");
            confirmacion.setContentText("Clase registrada correctamente");
            confirmacion.showAndWait();
            regresarVentanaMenu();
        }
    };
    
    private int obtenerIdProfesor(String profesor) {
        switch (profesor) {
            case "Juan": return 1;
            case "Maria": return 2;
            case "Carlos": return 3;
            case "Ana": return 4;
            case "Jose": return 5;
            default: return 0;
        }
    }
    
    @FXML
    private void regresarVentanaMenu(){
        try {
            FXMLLoader inicializador = new FXMLLoader(getClass().getResource("FXMLmenu.fxml"));
            Parent raiz = inicializador.load();
                        
            FXMLmenuController menuController = inicializador.getController();
            menuController.recibirTipoUsuario(tipoUsuario,idMiembro);
                        
            Scene escena = cmbProfesor.getScene();

            Pane panelPadre = (Pane) escena.getRoot();

            panelPadre.getChildren().clear();
            panelPadre.getChildren().add(raiz);

        } catch (IOException excepcion) {
            log.error(" Error al abrir la ventana menu: " + excepcion);
        }
    }
}
