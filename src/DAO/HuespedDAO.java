package DAO;

import Modelo.Huesped;
import Modelo.ConnectionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Huesped
 */
public class HuespedDAO {
    
    // CREATE
    public boolean insertar(Huesped huesped) {
        String sql = "INSERT INTO huesped (id_huesped, nombre, direccion, telefono) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, huesped.getIdHuesped());
            pstmt.setString(2, huesped.getNombre());
            pstmt.setString(3, huesped.getDireccion());
            pstmt.setString(4, huesped.getTelefono());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar huésped: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Buscar por ID
    public Huesped buscarPorId(int idHuesped) {
        String sql = "SELECT * FROM huesped WHERE id_huesped = ?";
        Huesped huesped = null;
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHuesped);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                huesped = new Huesped();
                huesped.setIdHuesped(rs.getInt("id_huesped"));
                huesped.setNombre(rs.getString("nombre"));
                huesped.setDireccion(rs.getString("direccion"));
                huesped.setTelefono(rs.getString("telefono"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar huésped: " + e.getMessage());
        }
        
        return huesped;
    }
    
    // READ - Listar todos
    public List<Huesped> listarTodos() {
        String sql = "SELECT * FROM huesped";
        List<Huesped> huespedes = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Huesped huesped = new Huesped();
                huesped.setIdHuesped(rs.getInt("id_huesped"));
                huesped.setNombre(rs.getString("nombre"));
                huesped.setDireccion(rs.getString("direccion"));
                huesped.setTelefono(rs.getString("telefono"));
                huespedes.add(huesped);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar huéspedes: " + e.getMessage());
        }
        
        return huespedes;
    }
    
    // UPDATE
    public boolean actualizar(Huesped huesped) {
        String sql = "UPDATE huesped SET nombre = ?, direccion = ?, telefono = ? " +
                     "WHERE id_huesped = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, huesped.getNombre());
            pstmt.setString(2, huesped.getDireccion());
            pstmt.setString(3, huesped.getTelefono());
            pstmt.setInt(4, huesped.getIdHuesped());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar huésped: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean eliminar(int idHuesped) {
        String sql = "DELETE FROM huesped WHERE id_huesped = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHuesped);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar huésped: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar por nombre
    public List<Huesped> buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM huesped WHERE nombre LIKE ?";
        List<Huesped> huespedes = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Huesped huesped = new Huesped();
                huesped.setIdHuesped(rs.getInt("id_huesped"));
                huesped.setNombre(rs.getString("nombre"));
                huesped.setDireccion(rs.getString("direccion"));
                huesped.setTelefono(rs.getString("telefono"));
                huespedes.add(huesped);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar huéspedes por nombre: " + e.getMessage());
        }
        
        return huespedes;
    }
    
    // Verificar si existe
    public boolean existe(int idHuesped) {
        String sql = "SELECT COUNT(*) FROM huesped WHERE id_huesped = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHuesped);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de huésped: " + e.getMessage());
        }
        
        return false;
    }
}