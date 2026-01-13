package com.banco.evaluacion.repository;

import com.banco.evaluacion.config.DataBaseConfig;
import com.banco.evaluacion.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepository {
    public int guardar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre,edad,sueldo) values (?,?,?) returning id";

        try(Connection conn = DataBaseConfig.getConnection();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,cliente.nombre());
            pstmt.setInt(2,cliente.edad());
            pstmt.setDouble(3,cliente.sueldoNeto());

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return rs.getInt(1);
                }
            }
        }
        catch(SQLException e){
            throw new SQLException("No se pudo guardar al cliente");
        }
        throw new SQLException("No se pudo guardar al cliente");
    }
}
