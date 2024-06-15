package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Clase;
import gyyrem.logic.interfaces.IClase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ClaseDAO implements IClase{
    private Connection conexion;
    private static final Logger log = Logger.getLogger(ClaseDAO.class);
    
    public boolean existeClase(String dias, String horario, int idProfesor) {
        boolean existe = false;
        String consulta = "SELECT * FROM clase WHERE idProfesor = ? AND horario LIKE ?;";

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }

            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idProfesor);
            declaracionPreparada.setString(2, "%" + horario + "%");
            ResultSet grupoResultado = declaracionPreparada.executeQuery();

            while (grupoResultado.next()) {
                String diasExistente = grupoResultado.getString("horario").split(", los días ")[1].split(" en el horario de: ")[0].trim();
                if (diasSolapados(dias, diasExistente)) {
                    existe = true;
                    break;
                }
            }
        } catch (SQLException excepcion) {
            log.error("Excepción SQL al comprobar si existe una clase: " + excepcion);
            return false;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException exepcion) {
                    log.error("Fallo al cerrar la conexión a la base de datos: " + exepcion);
                }
            }
        }

        return existe;
    }

    private boolean diasSolapados(String dias1, String dias2) {
        String[] diasArray1 = dias1.split(", ");
        String[] diasArray2 = dias2.split(", ");
        for (String dia1 : diasArray1) {
            for (String dia2 : diasArray2) {
                if (dia1.equals(dia2)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int registrarClase(Clase clase){
        int resultado=-1;
        ResultSet grupoResultado=null;
        
        String declaracion = "INSERT INTO clase(nombre, horario, cupo, estado, " +
                            "costo, idProfesor) VALUES(?, ?, ?, 'disponible', ?, ?);";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion,
                                                                    Statement.RETURN_GENERATED_KEYS);
            declaracionPreparada.setString(1, clase.getNombre());
            declaracionPreparada.setString(2, clase.getHorario());
            declaracionPreparada.setInt(3, clase.getCupo());
            declaracionPreparada.setInt(4, clase.getCosto());
            declaracionPreparada.setInt(5, clase.getIdProfesor());

            resultado= declaracionPreparada.executeUpdate();
            grupoResultado=declaracionPreparada.getGeneratedKeys();
            if (grupoResultado.next()) { 
                resultado = grupoResultado.getInt(1); 
            }
            
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al registrar una clase: " + excepcion);
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
    
    public int modificarClase(int idClase, Clase claseNueva){
        int resultado=-1;
        String declaracion = "UPDATE clase SET nombre = ?, horario = ?, " +
                            "cupo = ?, costo = ?, idProfesor = ? WHERE idClase = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setString(1, claseNueva.getNombre());
            declaracionPreparada.setString(2, claseNueva.getHorario());
            declaracionPreparada.setInt(3, claseNueva.getCupo());
            declaracionPreparada.setInt(4, claseNueva.getCosto());
            declaracionPreparada.setInt(5, claseNueva.getIdProfesor());
            declaracionPreparada.setInt(6, idClase);

            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar una clase: " + excepcion);
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
    
    public int cambiarEstadoALlena(int idClase){
        int resultado=-1;
        String declaracion = "UPDATE clase SET estado = 'llena' " +
                            "WHERE idClase = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idClase);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de una clase: " 
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
    
    public int cambiarEstadoADisponible(int idClase){
        int resultado=-1;
        String declaracion = "UPDATE clase SET estado = 'disponible' " +
                            "WHERE idClase = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idClase);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de una clase: " 
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
    
    public List<Clase> obtenerListaClasesDisponibles(){
        List<Clase> listaClases = new ArrayList<>();
        PreparedStatement declaracion = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            String consulta = "SELECT * FROM clase WHERE estado='disponible';";
            
            declaracion = conexion.prepareStatement(consulta);
            grupoResultado = declaracion.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaClases.add(new Clase(
                                        grupoResultado.getInt("idClase"),
                                        grupoResultado.getInt("cupo"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("horario"),
                                        grupoResultado.getString("estado"),
                                        grupoResultado.getInt("costo"),
                                        grupoResultado.getInt("idProfesor")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de clases: " 
                            + excepcion);
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
        
        return listaClases;
    };
    
    public Clase obtenerInformacionClase(int idClase){
        Clase clase= null;
        ResultSet grupoResultado = null;
        String consulta = "SELECT * FROM clase WHERE idClase = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idClase);
            grupoResultado = declaracionPreparada.executeQuery();

            if (grupoResultado != null) {
                clase = new Clase(
                                        grupoResultado.getInt("idClase"),
                                        grupoResultado.getInt("cupo"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("horario"),
                                        grupoResultado.getString("estado"),
                                        grupoResultado.getInt("costo"),
                                        grupoResultado.getInt("idProfesor")
                );
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar una clase: " + excepcion);
            clase = null;
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
        return clase;
    };
}
