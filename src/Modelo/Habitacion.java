/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author USER
 */
public class Habitacion {
    private int idHabitacion;
    private String tipoH;
    private int numHabitacion;
    private int idHotel;

    public Habitacion() {
    }

    public Habitacion(int idHabitacion, String tipoH, int numHabitacion, int idHotel) {
        this.idHabitacion = idHabitacion;
        this.tipoH = tipoH;
        this.numHabitacion = numHabitacion;
        this.idHotel = idHotel;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getTipoH() {
        return tipoH;
    }

    public void setTipoH(String tipoH) {
        this.tipoH = tipoH;
    }

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }
    
    @Override
    public String toString(){
        return "Habitacion{" + 
                "ID Habitacion: " + idHabitacion +
                ", tipo: " + tipoH +
                ", numero de habitacion: " + numHabitacion +
                ", ID Hotel: " + idHotel +
                "}";
    }
}
