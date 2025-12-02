package DAO;

import Modelo.Agencia;
import Modelo.ConnectionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Agencia
 */
public class AgenciaDAO {
    
    public boolean insertar(Agencia agencia) {
        String sql = "INSERT INTO agencia (id_agencia, nombre) VALUES (?, ?)";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, agencia.getIdAgencia());
            pstmt.setString(2, agencia.getNombre());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar agencia: " + e.getMessage());
            return false;
        }
    }
    
    public Agencia buscarPorId(int idAgencia) {
        String sql = "SELECT * FROM agencia WHERE id_agencia = ?";
        Agencia agencia = null;
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idAgencia);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNombre(rs.getString("nombre"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar agencia: " + e.getMessage());
        }
        
        return agencia;
    }
    
    public List<Agencia> listarTodas() {
        String sql = "SELECT * FROM agencia";
        List<Agencia> agencias = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNombre(rs.getString("nombre"));
                agencias.add(agencia);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar agencias: " + e.getMessage());
        }
        
        return agencias;
    }
    
    public boolean actualizar(Agencia agencia) {
        String sql = "UPDATE agencia SET nombre = ? WHERE id_agencia = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, agencia.getNombre());
            pstmt.setInt(2, agencia.getIdAgencia());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar agencia: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean eliminar(int idAgencia) {
        String sql = "DELETE FROM agencia WHERE id_agencia = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idAgencia);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar agencia: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar por nombre
    public List<Agencia> buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM agencia WHERE nombre LIKE ?";
        List<Agencia> agencias = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Agencia agencia = new Agencia();
                agencia.setIdAgencia(rs.getInt("id_agencia"));
                agencia.setNombre(rs.getString("nombre"));
                agencias.add(agencia);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar agencias por nombre: " + e.getMessage());
        }
        
        return agencias;
    }
}