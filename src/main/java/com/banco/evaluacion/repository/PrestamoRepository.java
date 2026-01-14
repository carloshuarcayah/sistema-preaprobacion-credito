package com.banco.evaluacion.repository;

import com.banco.evaluacion.config.DataBaseConfig;
import com.banco.evaluacion.model.Cliente;
import com.banco.evaluacion.model.EstadoPrestamo;
import com.banco.evaluacion.model.Prestamo;
import com.banco.evaluacion.model.TipoPrestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepository {
    public void guardar(Prestamo prestamo, int id_cliente, String mensaje) throws SQLException {
        String sql = "insert into historial_prestamos(cliente_id,tipo_prestamo,monto_solicitado,plazo_meses,estado, mensaje_detalle,fecha) values(?,?,?,?,?,?,?)";

        try(Connection conn = DataBaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id_cliente);
            pstmt.setString(2,prestamo.tipoPrestamo().name());
            pstmt.setDouble(3,prestamo.monto());
            pstmt.setInt(4,prestamo.plazoMeses());
            pstmt.setString(5,prestamo.estado().name());
            pstmt.setString(6,mensaje);
            pstmt.setObject(7,LocalDateTime.now());
            pstmt.executeUpdate();
            System.out.println("Se guardo el prestamo exitosamente.");
        }catch (SQLException e) {
            throw new SQLException("No se pudo guardar el prestamo.");
        }
    }

    public void actualizarEstado(int id, EstadoPrestamo estado, String mensaje) throws SQLException {
        String sql = "UPDATE historial_prestamos SET estado = ?, mensaje_detalle = ? WHERE id = ?";
        try(Connection conn = DataBaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, estado.name());
            pstmt.setString(2,mensaje);
            pstmt.setInt(3,id);

            pstmt.executeUpdate();
        }
        catch(SQLException e){
            throw new SQLException("Error al intentar conectarse a historial_prestamos: "+e.getMessage());
        }
    }

    public Optional<List<Prestamo>> obtenerPendientes() throws SQLException {
        String sql = "select * from historial_prestamos where estado='PENDIENTE'";
        try(Connection conn = DataBaseConfig.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            List<Prestamo> prestamos = new ArrayList<>();
            while(rs.next()){
                prestamos.add(
                        new Prestamo(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getDouble(4),
                                rs.getInt(8),
                                TipoPrestamo.valueOf(rs.getString(3)),
                                EstadoPrestamo.valueOf(rs.getString(5))
                ));
            }
            if(!prestamos.isEmpty())
            return Optional.of(prestamos);
            else return Optional.empty();
        }catch(SQLException e){
            throw new SQLException("No se pudo conectar con la tabla historial_prestamo");
        }

    }
}
