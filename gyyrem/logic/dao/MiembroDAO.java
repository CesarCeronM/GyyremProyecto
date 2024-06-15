package gyyrem.logic.dao;

import gyyrem.dataaccess.DatabaseManager;
import gyyrem.logic.domain.Miembro;
import gyyrem.logic.interfaces.IMiembro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MiembroDAO implements IMiembro{
    private Connection conexion;
    private static final Logger log = Logger.getLogger(ClaseDAO.class);
    
    public int existeMiembroInicial(String correo){
        int miembro = -1;
        String consulta = "SELECT idMiembro FROM miembro " +
                        "WHERE correo = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setString(1, correo);
            
            ResultSet grupoResultado = declaracionPreparada.executeQuery();
            if (grupoResultado.next()) {
                miembro = grupoResultado.getInt("idMiembro");
            }
        }catch (SQLException excepcion) {
            log.error(" Excepcion SQL al comprobar si existe un miembro: " 
                            + excepcion);
            miembro = -1;
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
        
        return miembro;
    };
    
    public int existeMiembroInicialT(String telefono){
        int miembro = -1;
        String consulta = "SELECT idMiembro FROM miembro " +
                        "WHERE telefono = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setString(1, telefono);
            
            ResultSet grupoResultado = declaracionPreparada.executeQuery();
            if (grupoResultado.next()) {
                miembro = grupoResultado.getInt("idMiembro");
            }
        }catch (SQLException excepcion) {
            log.error(" Excepcion SQL al comprobar si existe un miembro: " 
                            + excepcion);
            miembro = -1;
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
        
        return miembro;
    };
    
    public int existeMiembro(String correo, String contraseña){
        int miembro = -1;
        String consulta = "SELECT idMiembro FROM miembro " +
                        "WHERE correo = ? AND contraseña = sha2(?,256) AND estado = 'activo';";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setString(1, correo);
            declaracionPreparada.setString(2, contraseña);
            
            ResultSet grupoResultado = declaracionPreparada.executeQuery();
            if (grupoResultado.next()) {
                miembro = grupoResultado.getInt("idMiembro");
            }
        }catch (SQLException excepcion) {
            log.error(" Excepcion SQL al comprobar si existe un miembro: " 
                            + excepcion);
            miembro = -1;
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
        
        return miembro;
    };
    
    public int registrarMiembro(Miembro miembro){
        int resultado=-1;
        ResultSet grupoResultado=null;
        
        String declaracion = "INSERT INTO miembro(nombre, apellidoPaterno, apellidoMaterno, " +
                            "fechaNacimiento, telefono, correo, contraseña, diaDePago, estado) " +
                            "VALUES(?, ?, ?, ?, ?, ?, sha2(?,256), ?, 'activo');";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion,
                                                                    Statement.RETURN_GENERATED_KEYS);
            declaracionPreparada.setString(1, miembro.getNombre());
            declaracionPreparada.setString(2, miembro.getApellidoPaterno());
            declaracionPreparada.setString(3, miembro.getApellidoMaterno());
            declaracionPreparada.setString(4, miembro.getFechaDeNacimiento());
            declaracionPreparada.setString(5, miembro.getTelefono());
            declaracionPreparada.setString(6, miembro.getCorreo());
            declaracionPreparada.setString(7, miembro.getContrasenia());
            declaracionPreparada.setInt(8, miembro.getDiaDePago());

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
    
    public int modificarMiembro(int idMiembro, Miembro miembroNuevo){
        int resultado=-1;
        String declaracion = "UPDATE miembro SET nombre = ?, apellidoPaterno = ?, " +
                            "apellidoMaterno = ?, fechaNacimiento = ?, telefono = ?, " +
                            "correo = ?, contraseña = sha2(?, 256) , diaDePago = ? WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setString(1, miembroNuevo.getNombre());
            declaracionPreparada.setString(2, miembroNuevo.getApellidoPaterno());
            declaracionPreparada.setString(3, miembroNuevo.getApellidoMaterno());
            declaracionPreparada.setString(4, miembroNuevo.getFechaDeNacimiento());
            declaracionPreparada.setString(5, miembroNuevo.getTelefono());
            declaracionPreparada.setString(6, miembroNuevo.getCorreo());
            declaracionPreparada.setString(7, miembroNuevo.getContrasenia());
            declaracionPreparada.setInt(8, miembroNuevo.getDiaDePago());
            declaracionPreparada.setInt(9, idMiembro);

            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar un miembro: " + excepcion);
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
    
    public int suspenderMiembro(int idMiembro){
        int resultado=-1;
        String declaracion = "UPDATE miembro SET estado = 'suspendido' " +
                            "WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idMiembro);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de un miembro: " 
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
    
    public int activarMiembro(int idMiembro){
        int resultado=-1;
        String declaracion = "UPDATE miembro SET estado = 'activo' " +
                            "WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
        
            PreparedStatement declaracionPreparada = conexion.prepareStatement(declaracion);
            declaracionPreparada.setInt(1, idMiembro);
            
            resultado= declaracionPreparada.executeUpdate();
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al modificar el atributo estado de un miembro: " 
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
    
    public List<Miembro> obtenerListaMiembro(){
        List<Miembro> listaMiembros = new ArrayList<>();
        PreparedStatement declaracion = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            String consulta = "SELECT * FROM miembro WHERE estado='activo' AND idMiembro > 1;";
            
            declaracion = conexion.prepareStatement(consulta);
            grupoResultado = declaracion.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaMiembros.add(new Miembro(
                                        grupoResultado.getInt("idMiembro"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("apellidoPaterno"),
                                        grupoResultado.getString("apellidoMaterno"),
                                        grupoResultado.getString("fechaNacimiento"),
                                        grupoResultado.getString("telefono"),
                                        grupoResultado.getString("correo"),
                                        grupoResultado.getString("contraseña"),
                                        grupoResultado.getInt("diaDePago"),
                                        grupoResultado.getString("estado")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de miembros: " 
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
        
        return listaMiembros;
    };
    
    public List<Miembro> obtenerListaMiembrosSuspendidos(){
        List<Miembro> listaMiembros = new ArrayList<>();
        PreparedStatement declaracion = null;
        ResultSet grupoResultado = null;

        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            String consulta = "SELECT * FROM miembro WHERE estado= 'suspendido' AND idMiembro > 1;";
            
            declaracion = conexion.prepareStatement(consulta);
            grupoResultado = declaracion.executeQuery();

            while (grupoResultado != null && grupoResultado.next()) {
                listaMiembros.add(new Miembro(
                                        grupoResultado.getInt("idMiembro"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("apellidoPaterno"),
                                        grupoResultado.getString("apellidoMaterno"),
                                        grupoResultado.getString("fechaNacimiento"),
                                        grupoResultado.getString("telefono"),
                                        grupoResultado.getString("correo"),
                                        grupoResultado.getString("contraseña"),
                                        grupoResultado.getInt("diaDePago"),
                                        grupoResultado.getString("estado")
                ));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar la lista de miembros: " 
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
        
        return listaMiembros;
    };
    
    public Miembro obtenerInformacionMiembro(int idMiembro){
        Miembro miembro= null;
        ResultSet grupoResultado = null;
        String consulta = "SELECT * FROM miembro WHERE idMiembro = ?;";
        
        try{
            if (conexion == null || conexion.isClosed()) {
                conexion = new DatabaseManager().getConnectionAdministrador();
            }
            
            PreparedStatement declaracionPreparada = conexion.prepareStatement(consulta);
            declaracionPreparada.setInt(1, idMiembro);
            grupoResultado = declaracionPreparada.executeQuery();

            if (grupoResultado != null && grupoResultado.next()) {
                miembro = new Miembro(
                                        grupoResultado.getInt("idMiembro"),
                                        grupoResultado.getString("nombre"),
                                        grupoResultado.getString("apellidoPaterno"),
                                        grupoResultado.getString("apellidoMaterno"),
                                        grupoResultado.getString("fechaNacimiento"),
                                        grupoResultado.getString("telefono"),
                                        grupoResultado.getString("correo"),
                                        grupoResultado.getString("contraseña"),
                                        grupoResultado.getInt("diaDePago"),
                                        grupoResultado.getString("estado"));
            }
        } catch (SQLException excepcion) {
            log.error(" Excepcion SQL al consultar un miembro: " + excepcion);
            miembro = null;
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
        return miembro;
    };   
}
