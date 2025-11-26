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
public class Hotel {
    private String nombreHotel;
    private int idHotel;
    private String direccion;
    private String telefono;
    private int anioInauguracion;
    private int categoria;

    public Hotel() {
    }

    public Hotel(String nombreHotel, int idHotel, String direccion, String telefono, int anioInauguracion, int categoria) {
        this.nombreHotel = nombreHotel;
        this.idHotel = idHotel;
        this.direccion = direccion;
        this.telefono = telefono;
        this.anioInauguracion = anioInauguracion;
        this.categoria = categoria;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }
    
    public int getIdHotel(){
        return idHotel;
    }
    
    public void setIdHotel(int idHotel){
        this.idHotel = idHotel;
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

    public int getAnioInauguracion() {
        return anioInauguracion;
    }

    public void setAnioInauguracion(int anioInauguracion) {
        this.anioInauguracion = anioInauguracion;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
    public int getAntiguedad(){
        return LocalDate.now().getYear()- anioInauguracion;
    }
    
    @Override
    public String toString(){
        return "Hotel{" + 
                "Nombre: " + nombreHotel +
                ", ID Hotel: " + idHotel +
                ", direccion: " + direccion +
                ", telefono: " + telefono +
                ", año de inauguracion: " + anioInauguracion +
                ", categoria: " + categoria +
                "}";
    }
}
