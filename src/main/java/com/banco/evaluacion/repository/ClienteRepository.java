package com.banco.evaluacion.repository;

import com.banco.evaluacion.config.DataBaseConfig;
import com.banco.evaluacion.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClienteRepository {
    public int guardar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre,edad,score_crediticio,sueldo,tiene_deuda) values (?,?,?,?,?) returning id";

        try(Connection conn = DataBaseConfig.getConnection();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,cliente.nombre());
            pstmt.setInt(2,cliente.edad());
            pstmt.setInt(3,cliente.historialCrediticio());
            pstmt.setDouble(4,cliente.sueldoNeto());
            pstmt.setBoolean(5, cliente.tieneDeuda());

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

    public Optional<Cliente> buscarPorId(int id) throws SQLException {
        String sql = "Select * from clientes where id=?";

        try(Connection conn = DataBaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                   Cliente cliente = new Cliente(
                           rs.getString("nombre"),
                           rs.getInt("edad"),
                           rs.getInt("score_crediticio"),
                           rs.getDouble("sueldo"),
                           rs.getBoolean("tiene_deuda")
                   );
                   return Optional.of(cliente);
                }
            }
        }
        return Optional.empty();
    }
}
