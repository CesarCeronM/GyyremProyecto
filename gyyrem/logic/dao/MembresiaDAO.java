package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Membresia;
import gyyrem.logic.interfaces.IMembresia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class MembresiaDAO implements IMembresia {
    private Connection conexion;
    private static final Logger log = Logger.getLogger(MembresiaDAO.class);
    
    public int registrarMembresia(Membresia membresia){
        int resultado=-1;
        ResultSet grupoResultado=null;
        
        String declaracion = "INSERT INTO membresia(fechaInicio, fechaFin, estado, tipo, " +
                            "costo, idMiembro) VALUES(?, ?, 'deuda', ?, ?, ?);";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion,
                                                                    Statement.RETURN_GENERATED_KEYS);
            declaracionPreparada.setString(1, membresia.getFechaInicio());
            declaracionPreparada.setString(2, membresia.getFechaFin());
            declaracionPreparada.setString(3, membresia.getTipo());
            declaracionPreparada.setInt(4, membresia.getCosto());
            declaracionPreparada.setInt(5, membresia.getIdMiembro());

            resultado= declaracionPreparada.executeUpdate();
            grupoResultado=declaracionPreparada.getGeneratedKeys();
            if (grupoResultado.next()) { 
                resultado = grupoResultado.getInt(1); 
            }
            
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al registrar una membresia: " + excepcion);
            resultado= -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " 
                                    + exepcion);
                }
            }
        }
        return resultado;
    };
    
    public int expirarMembresia(int idMiembro){
        int resultado=-1;
        String declaracion = "UPDATE membresia SET estado = 'expirada' " +
                            "WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idMiembro);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de una membresia: " 
                            + excepcion);
            resultado= -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " 
                                    + exepcion);
                }
            }
        }
        return resultado;
    };
    
    public int pagarMembresia(int idMiembro){
        int resultado=-1;
        String declaracion = "UPDATE membresia SET estado = 'vigente' " +
                            "WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idMiembro);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de una membresia: " 
                            + excepcion);
            resultado= -1;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " 
                                    + exepcion);
                }
            }
        }
        return resultado;
    };
    
    public Membresia membresiaEstaPagada(int idMiembro){
        Membresia membresia= null;
        ResultSet grupoResultado = null;
        String consulta = "SELECT * FROM membresia WHERE idMiembro = ? AND estado = 'deuda';";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idMiembro);
            grupoResultado = declaracionPreparada.executeQuery();

            if (grupoResultado != null && grupoResultado.next()) {
                membresia = new Membresia(
                                        grupoResultado.getInt("idMembresia"),
                                        grupoResultado.getString("fechaInicio"),
                                        grupoResultado.getString("fechaFin"),
                                        grupoResultado.getString("tipo"),
                                        grupoResultado.getString("estado"),
                                        grupoResultado.getInt("costo"),
                                        grupoResultado.getInt("idMiembro"));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar una membresia: " + excepcion);
            membresia = null;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error(" Fallo al cerrar la conexion a la base de datos: " 
                                    + exepcion);
                }
            }
        }
        return membresia;
    };
}
