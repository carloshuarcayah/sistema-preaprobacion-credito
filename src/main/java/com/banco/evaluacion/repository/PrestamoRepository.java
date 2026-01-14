package com.banco.evaluacion.repository;

import com.banco.evaluacion.config.DataBaseConfig;
import com.banco.evaluacion.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PrestamoRepository {
    public void guardar(Prestamo prestamo, int id_cliente, String mensaje) throws SQLException {
        String sql = "insert into historial_prestamos(cliente_id,tipo_prestamo,monto_solicitado,estado, mensaje_detalle,fecha) values(?,?,?,?,?,?)";

        try(Connection conn = DataBaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id_cliente);
            pstmt.setString(2,prestamo.tipoPrestamo().name());
            pstmt.setDouble(3,prestamo.monto());
            pstmt.setString(4,prestamo.estado().name());
            pstmt.setString(5,mensaje);
            pstmt.setObject(6,LocalDateTime.now());
            pstmt.executeUpdate();
            System.out.println("Se guardo el prestamo exitosamente.");
        }catch (SQLException e) {
            throw new SQLException("No se pudo guardar el prestamo.");
        }
    }
}
