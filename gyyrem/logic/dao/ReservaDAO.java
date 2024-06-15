package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Clase;
import gyyrem.logic.domain.Reserva;
import gyyrem.logic.interfaces.IReserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ReservaDAO implements IReserva{
    private Connection conexion;
    private static final Logger log = Logger.getLogger(ReservaDAO.class);
    
    public boolean existeReserva(int idMiembro, int idClase) {
        boolean existe = false;
        String consulta = "SELECT COUNT(*) FROM reserva WHERE idMiembro = ? AND idClase = ?";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idMiembro);
            declaracionPreparada.setInt(2, idClase);
            ResultSet resultado = declaracionPreparada.executeQuery();

            if (resultado.next()) {
                existe = resultado.getInt(1) > 0;
            }
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al verificar la existencia de una reserva: " + excepcion);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + excepcion);
                }
            }
        }

        return existe;
    }
    
    public int reservarClase(Reserva reserva) {
        int resultado = -1;
        ResultSet grupoResultado = null;

        String declaracion = "INSERT INTO reserva(idMiembro, idClase, fecha, estado) " +
                             "VALUES(?, ?, ?, 'reservado');";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion, Statement.RETURN_GENERATED_KEYS);
            declaracionPreparada.setInt(1, reserva.getIdMiembro());
            declaracionPreparada.setInt(2, reserva.getIdClase());
            declaracionPreparada.setString(3, reserva.getFecha());

            resultado = declaracionPreparada.executeUpdate();
            grupoResultado = declaracionPreparada.getGeneratedKeys();
            if (grupoResultado.next()) { 
                resultado = grupoResultado.getInt(1); 
            }
            
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al reservar una clase: " + excepcion);
            resultado = -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + excepcion);
                }
            }
        }
        return resultado;
    }

    public int cancelarReserva(int idReserva) {
        int resultado = -1;
        String declaracion = "DELETE FROM reserva WHERE idReserva = ?;";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idReserva);

            resultado = declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al cancelar una reserva: " + excepcion);
            resultado = -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + excepcion);
                }
            }
        }
        return resultado;
    }

    public int pagarReserva(int idClase, int idMiembro) {
        int resultado = -1;
        String declaracion = "UPDATE reserva SET estado = 'pagado' WHERE idClase = ? AND idMiembro = ?;";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idClase);
            declaracionPreparada.setInt(2, idMiembro);

            resultado = declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al pagar una reserva: " + excepcion);
            resultado = -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + excepcion);
                }
            }
        }
        return resultado;
    }
    
    public List<Clase> obtenerClasesReservadasPorMiembro(int idMiembro) {
        List<Clase> clases = new ArrayList<>();
        String consulta = "SELECT c.idClase, c.nombre, c.horario, c.cupo, c.estado, c.costo, c.idProfesor " +
                          "FROM clase c " +
                          "JOIN reserva r ON c.idClase = r.idClase " +
                          "WHERE r.idMiembro = ? AND r.estado = 'reservado';";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idMiembro);
            ResultSet grupoResultado = declaracionPreparada.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                Clase clase = new Clase(
                    grupoResultado.getInt("idClase"),
                    grupoResultado.getInt("cupo"),
                    grupoResultado.getString("nombre"),
                    grupoResultado.getString("horario"),
                    grupoResultado.getString("estado"),
                    grupoResultado.getInt("costo"),
                    grupoResultado.getInt("idProfesor")
                );
                clases.add(clase);
            }
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al obtener las clases por miembro: " + excepcion);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + excepcion);
                }
            }
        }

        return clases;
    }
}
