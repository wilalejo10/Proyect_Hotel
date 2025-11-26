/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author USER
 */
public class Reserva {
    private int idReserva;
    private int idHuesped;
    private String direccion;
    private String telefono;
    private int idAgencia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadPersonas;
    private String tipoHabitacion;
    private int cantidadHabitaciones;
    private String serviciosAdicionales;

    public Reserva() {
    }

    public Reserva(int idReserva, int idHuesped, String direccion, String telefono, int idAgencia, LocalDate fechaInicio, LocalDate fechaFin, int cantidadPersonas, String tipoHabitacion, int cantidadHabitaciones, String serviciosAdicionales) {
        this.idReserva = idReserva;
        this.idHuesped = idHuesped;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idAgencia = idAgencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.tipoHabitacion = tipoHabitacion;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(String nombreHuesped) {
        this.idHuesped = idHuesped;
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

    public int getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(int idAgencia) {
        this.idAgencia = idAgencia;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public String getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(String serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }
    
    @Override
    public String toString(){
        return "Reserva{" + 
                "ID Reserva: " + idReserva +
                ", ID Huesped: " + idHuesped +
                ", direccion: " + direccion +
                ", telefono: " + telefono +
                ", ID agencia: " + idAgencia +
                ", fecha inicio: " + fechaInicio +
                ", fecha fin: " + fechaFin +
                ", cantidad de personas: " + cantidadPersonas +
                ", tipo de habitaciones: " + tipoHabitacion +
                ", cantidad de habitaciones: " + cantidadHabitaciones +
                ", Servicios adicionales: " + serviciosAdicionales +
                "}";
    }
}
