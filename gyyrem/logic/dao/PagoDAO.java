package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Pago;
import gyyrem.logic.interfaces.IPago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PagoDAO implements IPago{
    private Connection conexion;
    private static final Logger log = Logger.getLogger(PagoDAO.class);
    
    public int registrarPago(Pago pago) {
        int resultado = -1;
        ResultSet grupoResultado = null;

        String declaracion = "INSERT INTO pago (monto, fecha, descripcion, idMiembro) VALUES (?, ?, ?, ?);";
        
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion, PreparedStatement.RETURN_GENERATED_KEYS);
            declaracionPreparada.setInt(1, pago.getMonto());
            declaracionPreparada.setString(2, pago.getFecha());
            declaracionPreparada.setString(3, pago.getDescripcion());
            declaracionPreparada.setInt(4, pago.getIdMiembro());

            resultado = declaracionPreparada.executeUpdate();
            grupoResultado = declaracionPreparada.getGeneratedKeys();
            if (grupoResultado.next()) { 
                resultado = grupoResultado.getInt(1); 
            }

        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al registrar un pago: " + excepcion);
            resultado = -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + excepcion);
                }
            }
        }
        return resultado;
    }

    public List<Pago> obtenerListaPagos(int idMiembro) {
        List<Pago> listaPagos = new ArrayList<>();
        PreparedStatement declaracionPreparada = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            String consulta = "SELECT * FROM pago WHERE idMiembro= ?;";
            declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idMiembro);
            
            grupoResultado = declaracionPreparada.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaPagos.add(new Pago(
                    grupoResultado.getInt("idPago"),
                    grupoResultado.getInt("monto"),
                    grupoResultado.getString("fecha"),
                    grupoResultado.getString("descripcion"),
                    grupoResultado.getInt("idMiembro")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de pagos: " + excepcion);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + excepcion);
                }
            }
        }

        return listaPagos;
    }
    
    public List<Pago> obtenerListaTodosPagos() {
        List<Pago> listaPagos = new ArrayList<>();
        PreparedStatement declaracionPreparada = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            String consulta = "SELECT p.idPago, p.monto, p.fecha, p.descripcion, p.idMiembro, m.nombre " +
                          "FROM pago p " +
                          "JOIN miembro m ON p.idMiembro = m.idMiembro;";
            declaracionPreparada = conexion.prepareStatement(consulta);
            
            grupoResultado = declaracionPreparada.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaPagos.add(new Pago(
                    grupoResultado.getInt("idPago"),
                    grupoResultado.getInt("monto"),
                    grupoResultado.getString("fecha"),
                    grupoResultado.getString("descripcion"),
                    grupoResultado.getInt("idMiembro"),
                    grupoResultado.getString("nombre")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de pagos: " + excepcion);
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + excepcion);
                }
            }
        }

        return listaPagos;
    }

    public Pago obtenerInformacionPago(int idPago) {
        Pago pago = null;
        ResultSet grupoResultado = null;
        String consulta = "SELECT * FROM pago WHERE idPago = ?;";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idPago);
            grupoResultado = declaracionPreparada.executeQuery();

            if (grupoResultado != null && grupoResultado.next()) {
                pago = new Pago(
                    grupoResultado.getInt("idPago"),
                    grupoResultado.getInt("monto"),
                    grupoResultado.getString("fecha"),
                    grupoResultado.getString("descripcion"),
                    grupoResultado.getInt("idMiembro")
                );
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la informacion de un pago: " + excepcion);
            pago = null;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException excepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " + excepcion);
                }
            }
        }
        return pago;
    }
}
