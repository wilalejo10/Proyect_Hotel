package Modelo;

/**
 * Clase que representa un Hotel
 */
public class Hotel {
    private int idHotel;
    private String nombre;
    private String direccion;
    private String telefono;
    private int anioInauguracion;
    private int categoria;

    // Constructor vacío
    public Hotel() {
    }

    // Constructor completo
    public Hotel(int idHotel, String nombre, String direccion, String telefono, 
                 int anioInauguracion, int categoria) {
        this.idHotel = idHotel;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.anioInauguracion = anioInauguracion;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    // Método para calcular antigüedad
    public int calcularAntiguedad() {
        return java.time.Year.now().getValue() - this.anioInauguracion;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "idHotel=" + idHotel +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", anioInauguracion=" + anioInauguracion +
                ", categoria=" + categoria +
                ", antiguedad=" + calcularAntiguedad() +
                '}';
    }
}