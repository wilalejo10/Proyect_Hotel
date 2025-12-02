package DAO;

import Modelo.Hotel;
import Modelo.ConnectionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar operaciones CRUD de Hotel
 */
public class HotelDAO {
    
    // CREATE
    public boolean insertar(Hotel hotel) {
        String sql = "INSERT INTO hotel (id_hotel, nombre, direccion, telefono, " +
                     "anio_inauguracion, categoria) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hotel.getIdHotel());
            pstmt.setString(2, hotel.getNombre());
            pstmt.setString(3, hotel.getDireccion());
            pstmt.setString(4, hotel.getTelefono());
            pstmt.setInt(5, hotel.getAnioInauguracion());
            pstmt.setInt(6, hotel.getCategoria());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar hotel: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Buscar por ID
    public Hotel buscarPorId(int idHotel) {
        String sql = "SELECT * FROM hotel WHERE id_hotel = ?";
        Hotel hotel = null;
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHotel);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                hotel = new Hotel();
                hotel.setIdHotel(rs.getInt("id_hotel"));
                hotel.setNombre(rs.getString("nombre"));
                hotel.setDireccion(rs.getString("direccion"));
                hotel.setTelefono(rs.getString("telefono"));
                hotel.setAnioInauguracion(rs.getInt("anio_inauguracion"));
                hotel.setCategoria(rs.getInt("categoria"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar hotel: " + e.getMessage());
        }
        
        return hotel;
    }
    
    // READ - Listar todos
    public List<Hotel> listarTodos() {
        String sql = "SELECT * FROM hotel";
        List<Hotel> hoteles = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setIdHotel(rs.getInt("id_hotel"));
                hotel.setNombre(rs.getString("nombre"));
                hotel.setDireccion(rs.getString("direccion"));
                hotel.setTelefono(rs.getString("telefono"));
                hotel.setAnioInauguracion(rs.getInt("anio_inauguracion"));
                hotel.setCategoria(rs.getInt("categoria"));
                hoteles.add(hotel);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar hoteles: " + e.getMessage());
        }
        
        return hoteles;
    }
    
    // UPDATE
    public boolean actualizar(Hotel hotel) {
        String sql = "UPDATE hotel SET nombre = ?, direccion = ?, telefono = ?, " +
                     "anio_inauguracion = ?, categoria = ? WHERE id_hotel = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, hotel.getNombre());
            pstmt.setString(2, hotel.getDireccion());
            pstmt.setString(3, hotel.getTelefono());
            pstmt.setInt(4, hotel.getAnioInauguracion());
            pstmt.setInt(5, hotel.getCategoria());
            pstmt.setInt(6, hotel.getIdHotel());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar hotel: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE
    public boolean eliminar(int idHotel) {
        String sql = "DELETE FROM hotel WHERE id_hotel = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHotel);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar hotel: " + e.getMessage());
            return false;
        }
    }
    
    // Aumentar categoría
    public boolean aumentarCategoria(int idHotel) {
        String sql = "UPDATE hotel SET categoria = categoria + 1 WHERE id_hotel = ?";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHotel);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Registrar cambio en categoria_hotel
                registrarCambioCategoria(idHotel, conn);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al aumentar categoría: " + e.getMessage());
        }
        
        return false;
    }
    
    // Disminuir categoría
    public boolean disminuirCategoria(int idHotel) {
        String sql = "UPDATE hotel SET categoria = categoria - 1 WHERE id_hotel = ? AND categoria > 1";
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idHotel);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Registrar cambio en categoria_hotel
                registrarCambioCategoria(idHotel, conn);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al disminuir categoría: " + e.getMessage());
        }
        
        return false;
    }
    
    // Método privado para registrar cambios de categoría
    private void registrarCambioCategoria(int idHotel, Connection conn) throws SQLException {
        Hotel hotel = buscarPorId(idHotel);
        if (hotel != null) {
            String sql = "INSERT INTO categoria_hotel (id_categoria, nombre_hotel, " +
                        "nombre_categoria, historico_categoria) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, hotel.getCategoria());
                pstmt.setString(2, hotel.getNombre());
                pstmt.setString(3, "Categoría " + hotel.getCategoria() + " estrellas");
                pstmt.setString(4, "Cambio realizado: " + new Date(System.currentTimeMillis()));
                pstmt.executeUpdate();
            }
        }
    }
    
    // Buscar por nombre
    public List<Hotel> buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM hotel WHERE nombre LIKE ?";
        List<Hotel> hoteles = new ArrayList<>();
        
        try (Connection conn = ConnectionBD.getConnectionDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setIdHotel(rs.getInt("id_hotel"));
                hotel.setNombre(rs.getString("nombre"));
                hotel.setDireccion(rs.getString("direccion"));
                hotel.setTelefono(rs.getString("telefono"));
                hotel.setAnioInauguracion(rs.getInt("anio_inauguracion"));
                hotel.setCategoria(rs.getInt("categoria"));
                hoteles.add(hotel);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar hoteles por nombre: " + e.getMessage());
        }
        
        return hoteles;
    }
}