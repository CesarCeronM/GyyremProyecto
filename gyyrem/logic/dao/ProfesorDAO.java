package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Profesor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProfesorDAO {
    private Connection conexion;
    private static final Logger log = Logger.getLogger(ProfesorDAO.class);

    public List<Profesor> obtenerListaProfesores() {
        List<Profesor> listaProfesores = new ArrayList<>();
        PreparedStatement declaracion = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            String consulta = "SELECT * FROM profesor;"; 
            declaracion = conexion.prepareStatement(consulta);
            grupoResultado = declaracion.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaProfesores.add(new Profesor(
                                        grupoResultado.getInt("idProfesor"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("apellidoPaterno"),
                                        grupoResultado.getString("apellidoMaterno")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de profesores: " + excepcion);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + exepcion);
                }
            }
        }

        return listaProfesores;
    }

    public Profesor obtenerInformacionProfesor(int idProfesor) {
        Profesor profesor = null;
        ResultSet grupoResultado = null;
        String consulta = "SELECT * FROM profesor WHERE idProfesor = ?;";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idProfesor);
            grupoResultado = declaracionPreparada.executeQuery();

            if (grupoResultado != null && grupoResultado.next()) {
                profesor = new Profesor(
                                        grupoResultado.getInt("idProfesor"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("apellidoPaterno"),
                                        grupoResultado.getString("apellidoMaterno")
                );
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la información del profesor: " + excepcion);
            profesor = null;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + exepcion);
                }
            }
        }
        return profesor;
    }
}
