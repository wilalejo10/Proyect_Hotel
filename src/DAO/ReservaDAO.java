package DAO;

import Modelo.Reserva;
import Modelo.ConnectionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Reserva
 */
public class ReservaDAO {
    
    // CREATE
    public boolean insertar(Reserva reserva) {
        String sql = "INSERT INTO reserva (id_reserva, id_huesped, id_agencia, " +
                     "fecha_inicio, fecha_fin, cant_persona, cant_habitacion, id_registro_llegada) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reserva.getIdReserva());
            pstmt.setInt(2, reserva.getIdHuesped());
            
            if (reserva.getIdAgencia() != null) {
                pstmt.setInt(3, reserva.getIdAgencia());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            
            pstmt.setDate(4, Date.valueOf(reserva.getFechaInicio()));
            pstmt.setDate(5, Date.valueOf(reserva.getFechaFin()));
            pstmt.setInt(6, reserva.getCantidadPersonas());
            pstmt.setInt(7, reserva.getCantidadHabitaciones());
            
            if (reserva.getIdRegistroLlegada() != null) {
                pstmt.setInt(8, reserva.getIdRegistroLlegada());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            
            int filasAfectadas = pstmt.executeUpdate();
            
            // Insertar tipo de habitación
            if (filasAfectadas > 0 && reserva.getTipoHabitacion() != null) {
                insertarTipoHabitacion(reserva.getIdReserva(), reserva.getTipoHabitacion(), conn);
            }
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
            return false;
        }
    }
    
    // Método auxiliar para insertar tipo de habitación
    private void insertarTipoHabitacion(int idReserva, String tipoHabitacion, Connection conn) 
            throws SQLException {
        // Insertar en tipo_habitacion
        String sql1 = "INSERT INTO tipo_habitacion (tipo, id_reserva) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.setString(1, tipoHabitacion);
            pstmt.setInt(2, idReserva);
            pstmt.executeUpdate();
        }
        
        // Insertar en tipo_habitacion_reserva
        String sql2 = "INSERT INTO tipo_habitacion_reserva (id_reserva, tipo_habitacion) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, idReserva);
            pstmt.setString(2, tipoHabitacion);
            pstmt.executeUpdate();
        }
    }
    
    // READ - Buscar por ID
    public Reserva buscarPorId(int idReserva) {
        String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
        Reserva reserva = null;
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idReserva);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setIdHuesped(rs.getInt("id_huesped"));
                
                int idAgencia = rs.getInt("id_agencia");
                reserva.setIdAgencia(rs.wasNull() ? null : idAgencia);
                
                reserva.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                reserva.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                reserva.setCantidadPersonas(rs.getInt("cant_persona"));
                reserva.setCantidadHabitaciones(rs.getInt("cant_habitacion"));
                
                int idRegistro = rs.getInt("id_registro_llegada");
                reserva.setIdRegistroLlegada(rs.wasNull() ? null : idRegistro);
                
                // Obtener tipo de habitación
                String tipo = obtenerTipoHabitacion(idReserva, conn);
                reserva.setTipoHabitacion(tipo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar reserva: " + e.getMessage());
        }
        
        return reserva;
    }
    
    // Método auxiliar para obtener tipo de habitación
    private String obtenerTipoHabitacion(int idReserva, Connection conn) throws SQLException {
        String sql = "SELECT tipo FROM tipo_habitacion WHERE id_reserva = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idReserva);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            }
        }
        return null;
    }
    
    // READ - Listar todas
    public List<Reserva> listarTodas() {
        String sql = "SELECT * FROM reserva";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setIdHuesped(rs.getInt("id_huesped"));
                
                int idAgencia = rs.getInt("id_agencia");
                reserva.setIdAgencia(rs.wasNull() ? null : idAgencia);
                
                reserva.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                reserva.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                reserva.setCantidadPersonas(rs.getInt("cant_persona"));
                reserva.setCantidadHabitaciones(rs.getInt("cant_habitacion"));
                
                int idRegistro = rs.getInt("id_registro_llegada");
                reserva.setIdRegistroLlegada(rs.wasNull() ? null : idRegistro);
                
                String tipo = obtenerTipoHabitacion(reserva.getIdReserva(), conn);
                reserva.setTipoHabitacion(tipo);
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar reservas: " + e.getMessage());
        }
        
        return reservas;
    }
    
    // UPDATE
    public boolean actualizar(Reserva reserva) {
        String sql = "UPDATE reserva SET id_huesped = ?, id_agencia = ?, " +
                     "fecha_inicio = ?, fecha_fin = ?, cant_persona = ?, " +
                     "cant_habitacion = ?, id_registro_llegada = ? WHERE id_reserva = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reserva.getIdHuesped());
            
            if (reserva.getIdAgencia() != null) {
                pstmt.setInt(2, reserva.getIdAgencia());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setDate(3, Date.valueOf(reserva.getFechaInicio()));
            pstmt.setDate(4, Date.valueOf(reserva.getFechaFin()));
            pstmt.setInt(5, reserva.getCantidadPersonas());
            pstmt.setInt(6, reserva.getCantidadHabitaciones());
            
            if (reserva.getIdRegistroLlegada() != null) {
                pstmt.setInt(7, reserva.getIdRegistroLlegada());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            
            pstmt.setInt(8, reserva.getIdReserva());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar reserva: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean eliminar(int idReserva) {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idReserva);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar reserva: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar reservas por huésped
    public List<Reserva> buscarPorHuesped(int idHuesped) {
        String sql = "SELECT * FROM reserva WHERE id_huesped = ?";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHuesped);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setIdHuesped(rs.getInt("id_huesped"));
                
                int idAgencia = rs.getInt("id_agencia");
                reserva.setIdAgencia(rs.wasNull() ? null : idAgencia);
                
                reserva.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                reserva.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                reserva.setCantidadPersonas(rs.getInt("cant_persona"));
                reserva.setCantidadHabitaciones(rs.getInt("cant_habitacion"));
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar reservas por huésped: " + e.getMessage());
        }
        
        return reservas;
    }
    
    // Buscar reservas activas (fechas futuras)
    public List<Reserva> buscarReservasActivas() {
        String sql = "SELECT * FROM reserva WHERE fecha_inicio >= CURDATE()";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setIdHuesped(rs.getInt("id_huesped"));
                
                int idAgencia = rs.getInt("id_agencia");
                reserva.setIdAgencia(rs.wasNull() ? null : idAgencia);
                
                reserva.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                reserva.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                reserva.setCantidadPersonas(rs.getInt("cant_persona"));
                reserva.setCantidadHabitaciones(rs.getInt("cant_habitacion"));
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar reservas activas: " + e.getMessage());
        }
        
        return reservas;
    }
}