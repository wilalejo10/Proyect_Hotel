package Modelo;

import java.time.LocalDate;

/**
 * Clase que representa una Reserva
 */
public class Reserva {
    private int idReserva;
    private int idHuesped;
    private Integer idAgencia; // Puede ser null si no es reserva de agencia
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int cantidadPersonas;
    private int cantidadHabitaciones;
    private String tipoHabitacion;
    private String direccion;
    private String telefono;
    private String serviciosAdicionales;
    private Integer idRegistroLlegada; // Puede ser null hasta que llegue

    // Constructor vacío
    public Reserva() {
    }

    // Constructor completo
    public Reserva(int idReserva, int idHuesped, Integer idAgencia, 
                   LocalDate fechaInicio, LocalDate fechaFin, 
                   int cantidadPersonas, int cantidadHabitaciones,
                   String tipoHabitacion, String direccion, String telefono,
                   String serviciosAdicionales, Integer idRegistroLlegada) {
        this.idReserva = idReserva;
        this.idHuesped = idHuesped;
        this.idAgencia = idAgencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadPersonas = cantidadPersonas;
        this.cantidadHabitaciones = cantidadHabitaciones;
        this.tipoHabitacion = tipoHabitacion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.serviciosAdicionales = serviciosAdicionales;
        this.idRegistroLlegada = idRegistroLlegada;
    }

    // Getters y Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdHuesped() {
        return idHuesped;
    }

    public void setIdHuesped(int idHuesped) {
        this.idHuesped = idHuesped;
    }

    public Integer getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(Integer idAgencia) {
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

    public int getCantidadHabitaciones() {
        return cantidadHabitaciones;
    }

    public void setCantidadHabitaciones(int cantidadHabitaciones) {
        this.cantidadHabitaciones = cantidadHabitaciones;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
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

    public String getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(String serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public Integer getIdRegistroLlegada() {
        return idRegistroLlegada;
    }

    public void setIdRegistroLlegada(Integer idRegistroLlegada) {
        this.idRegistroLlegada = idRegistroLlegada;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idHuesped=" + idHuesped +
                ", idAgencia=" + idAgencia +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", cantidadPersonas=" + cantidadPersonas +
                ", cantidadHabitaciones=" + cantidadHabitaciones +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", serviciosAdicionales='" + serviciosAdicionales + '\'' +
                ", idRegistroLlegada=" + idRegistroLlegada +
                '}';
    }
}