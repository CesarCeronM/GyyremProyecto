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


public class FXMLmodificarClaseController implements Initializable {
    private static final Logger log = Logger.getLogger(FXMLmodificarClaseController.class);
    
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblModificarClase;

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
    private DatePicker dpFecha;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtCupo;

    @FXML
    private TextField txtHorario;

    @FXML
    private ChoiceBox<String> cmbProfesor;

    @FXML
    private Button btnModificar;

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
    
    private Clase claseSeleccionada;
    
    private ClaseDAO claseDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbProfesor.getItems().addAll("Juan", "Maria", "Carlos", "Ana", "Jose");
        claseDAO = new ClaseDAO();
    }    
    
    public void recibirTipoUsuario(int tipoUsuario, int idMiembro, Clase clase) {
        this.tipoUsuario = tipoUsuario;
        this.idMiembro = idMiembro;
        this.claseSeleccionada = clase;

        txtNombre.setText(claseSeleccionada.getNombre());
        txtCosto.setText(String.valueOf(claseSeleccionada.getCosto()));
        txtCupo.setText(String.valueOf(claseSeleccionada.getCupo()));

        String horario = claseSeleccionada.getHorario();
        if (horario != null && horario.contains("en el horario de: ")) {
            String[] partes = horario.split("en el horario de: ");
            if (partes.length > 1) {
                txtHorario.setText(partes[1]);
            }
        }

        String fechaParte = claseSeleccionada.getHorario().split(", los días ")[0].replace("A partir del ", "").trim();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(fechaParte, formato);
        dpFecha.setValue(fecha);

        String diasParte = claseSeleccionada.getHorario().split(", los días ")[1].split(" en el horario de: ")[0].trim();
        chkLunes.setSelected(diasParte.contains("Lunes"));
        chkMartes.setSelected(diasParte.contains("Martes"));
        chkMiercoles.setSelected(diasParte.contains("Miércoles"));
        chkJueves.setSelected(diasParte.contains("Jueves"));
        chkViernes.setSelected(diasParte.contains("Viernes"));

        cmbProfesor.setValue(getProfesorNameById(claseSeleccionada.getIdProfesor()));
    }

    private String getProfesorNameById(int idProfesor) {
        return switch (idProfesor) {
            case 1 -> "Juan";
            case 2 -> "Maria";
            case 3 -> "Carlos";
            case 4 -> "Ana";
            case 5 -> "Jose";
            default -> null;
        };
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
            modificarClase();
        }
    };
    
    private void modificarClase() {
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
        if (profesor.equals("Juan")) {
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
            dias.setLength(dias.length() - 2); // Remove last comma and space
        }

        String horarios = "A partir del " + fechaString + ", los días " + dias.toString() + " en el horario de: " + horario;

        Clase clase = new Clase(0, cupoInt, nombre, horarios, "disponible", costoInt, idProfesor);
        
        if (claseDAO.existeClase(dias.toString(), horario, idProfesor)) {
            mostrarMensaje("Ya existe una clase con el mismo profesor y horario en los mismos días.");
        } else {
            claseDAO.modificarClase(claseSeleccionada.getIdClase(), clase);
            confirmacion.setTitle("Confirmacion");
            confirmacion.setHeaderText("Modificación exitosa");
            confirmacion.setContentText("Clase modificada correctamente");
            confirmacion.showAndWait();
            regresarVentanaMenu();
        }
    };
    
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
