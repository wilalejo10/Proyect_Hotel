package DAO;

import Modelo.Habitacion;
import Modelo.ConnectionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Habitacion
 */
public class HabitacionDAO {
    
    // CREATE
    public boolean insertar(Habitacion habitacion) {
        String sql = "INSERT INTO habitacion (id_habitacion, id_hotel, nombre_hotel, " +
                     "id_reserva, tipo_habitacion) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, habitacion.getIdHabitacion());
            pstmt.setInt(2, habitacion.getIdHotel());
            pstmt.setString(3, habitacion.getNombreHotel());
            pstmt.setInt(4, habitacion.getIdReserva());
            pstmt.setString(5, habitacion.getTipoHabitacion());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar habitación: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Buscar por ID
    public Habitacion buscarPorId(int idHabitacion) {
        String sql = "SELECT * FROM habitacion WHERE id_habitacion = ?";
        Habitacion habitacion = null;
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHabitacion);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("id_habitacion"));
                habitacion.setIdHotel(rs.getInt("id_hotel"));
                habitacion.setNombreHotel(rs.getString("nombre_hotel"));
                habitacion.setIdReserva(rs.getInt("id_reserva"));
                habitacion.setTipoHabitacion(rs.getString("tipo_habitacion"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar habitación: " + e.getMessage());
        }
        
        return habitacion;
    }
    
    // READ - Listar todas
    public List<Habitacion> listarTodas() {
        String sql = "SELECT * FROM habitacion";
        List<Habitacion> habitaciones = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("id_habitacion"));
                habitacion.setIdHotel(rs.getInt("id_hotel"));
                habitacion.setNombreHotel(rs.getString("nombre_hotel"));
                habitacion.setIdReserva(rs.getInt("id_reserva"));
                habitacion.setTipoHabitacion(rs.getString("tipo_habitacion"));
                habitaciones.add(habitacion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // READ - Listar por hotel
    public List<Habitacion> listarPorHotel(int idHotel) {
        String sql = "SELECT * FROM habitacion WHERE id_hotel = ?";
        List<Habitacion> habitaciones = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHotel);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("id_habitacion"));
                habitacion.setIdHotel(rs.getInt("id_hotel"));
                habitacion.setNombreHotel(rs.getString("nombre_hotel"));
                habitacion.setIdReserva(rs.getInt("id_reserva"));
                habitacion.setTipoHabitacion(rs.getString("tipo_habitacion"));
                habitaciones.add(habitacion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones por hotel: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // UPDATE
    public boolean actualizar(Habitacion habitacion) {
        String sql = "UPDATE habitacion SET id_hotel = ?, nombre_hotel = ?, " +
                     "id_reserva = ?, tipo_habitacion = ? WHERE id_habitacion = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, habitacion.getIdHotel());
            pstmt.setString(2, habitacion.getNombreHotel());
            pstmt.setInt(3, habitacion.getIdReserva());
            pstmt.setString(4, habitacion.getTipoHabitacion());
            pstmt.setInt(5, habitacion.getIdHabitacion());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar habitación: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean eliminar(int idHabitacion) {
        String sql = "DELETE FROM habitacion WHERE id_habitacion = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHabitacion);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar habitación: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar habitaciones disponibles por tipo
    public List<Habitacion> buscarDisponiblesPorTipo(String tipo) {
        String sql = "SELECT * FROM habitacion WHERE tipo_habitacion = ? AND id_reserva IS NULL";
        List<Habitacion> habitaciones = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipo);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setIdHabitacion(rs.getInt("id_habitacion"));
                habitacion.setIdHotel(rs.getInt("id_hotel"));
                habitacion.setNombreHotel(rs.getString("nombre_hotel"));
                habitacion.setIdReserva(rs.getInt("id_reserva"));
                habitacion.setTipoHabitacion(rs.getString("tipo_habitacion"));
                habitaciones.add(habitacion);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar habitaciones disponibles: " + e.getMessage());
        }
        
        return habitaciones;
    }
}