package gyyrem.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connectionAdministrador;
    private Connection connectionCoordinador;
    
    private final String DATABASE_NAME = "jdbc:mysql://localhost:3306/gimnasio";
    
    private final String DATABASE_ADMINISTRADOR = "AdministradorGyyrem";
    private final String DATABASE_PASSWORD_ADMINISTRADOR = "3ncP4IbZ";
    
    private final String DATABASE_COORDINADOR = "UsuarioGyyrem";
    private final String DATABASE_PASSWORD_COORDINADOR = "Gdm70xsM";
    
    
    public Connection getConnectionAdministrador(){
        connectAdministrador();
        return connectionAdministrador;
    }

    private void connectAdministrador(){
        try {
            connectionAdministrador = DriverManager.getConnection(DATABASE_NAME, DATABASE_ADMINISTRADOR,
                    DATABASE_PASSWORD_ADMINISTRADOR);
        } catch (SQLException exepcion) {
            
        }
    }
    
    public Connection getConnectionCoordinador(){
        connectCoordinador();
        return connectionCoordinador;
    }

    private void connectCoordinador(){
        try {
            connectionCoordinador = DriverManager.getConnection(DATABASE_NAME, DATABASE_COORDINADOR,
                    DATABASE_PASSWORD_COORDINADOR);
        } catch (SQLException exepcion) {
            
        }
    }
}