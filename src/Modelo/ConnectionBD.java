/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author USER
 */
public class ConnectionBD {
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";
    
    public static Connection getConnectionDb() throws SQLException {
        System.out.println("Conectado...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
