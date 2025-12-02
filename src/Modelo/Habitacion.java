package Modelo;

/**
 * Clase que representa una Habitación
 */
public class Habitacion {
    private int idHabitacion;
    private int idHotel;
    private String nombreHotel;
    private int numeroHabitacion;
    private String tipoHabitacion;
    private int idReserva;

    // Constructor vacío
    public Habitacion() {
    }

    // Constructor completo
    public Habitacion(int idHabitacion, int idHotel, String nombreHotel, 
                      int numeroHabitacion, String tipoHabitacion, int idReserva) {
        this.idHabitacion = idHabitacion;
        this.idHotel = idHotel;
        this.nombreHotel = nombreHotel;
        this.numeroHabitacion = numeroHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.idReserva = idReserva;
    }

    // Getters y Setters
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", idHotel=" + idHotel +
                ", nombreHotel='" + nombreHotel + '\'' +
                ", numeroHabitacion=" + numeroHabitacion +
                ", tipoHabitacion='" + tipoHabitacion + '\'' +
                ", idReserva=" + idReserva +
                '}';
    }
}