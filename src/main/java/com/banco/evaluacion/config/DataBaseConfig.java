package com.banco.evaluacion.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/banco_prueba";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    public static Connection getConnection() throws SQLException {
        try{
            return DriverManager.getConnection(URL,USER,PASS);
        }catch(SQLException e){
            throw new SQLException("No se pudo conectar a la base de datos: "+e.getMessage());
        }
    }
}
