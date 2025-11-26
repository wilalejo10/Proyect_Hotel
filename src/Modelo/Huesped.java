/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class Huesped {
    private int idHuesped;
    private String nombreHuesped;
    private String direccion;
    private String telefono;
    private LocalDate fechaNacimiento;

    public Huesped() {
    }

    public Huesped(int idHuesped, String nombreHuesped, String direccion, String telefono, LocalDate fechaNacimiento) {
        this.idHuesped = idHuesped;
        this.nombreHuesped = nombreHuesped;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public String getNombreHuesped() {
        return nombreHuesped;
    }

    public void setNombreHuesped(String nombreHuesped) {
        this.nombreHuesped = nombreHuesped;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    @Override
    public String toString(){
        return "Huesped{" + 
                "ID Huesped: " + idHuesped +
                ", nombre: " + nombreHuesped +
                ", direccion: " + direccion +
                ", telefono: " + telefono +
                ", fecha de nacimiento: " + fechaNacimiento +
                "}";
    }
}
