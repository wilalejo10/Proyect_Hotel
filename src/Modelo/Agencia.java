/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author USER
 */
public class Agencia {
    private int idAgencia;
    private String nombreA;
    private String telefono;
    private String direccion;

    public Agencia() {
    }

    public Agencia(int idAgencia, String nombreA, String telefono, String direccion) {
        this.idAgencia = idAgencia;
        this.nombreA = nombreA;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(int idAgencia) {
        this.idAgencia = idAgencia;
    }

    public String getNombreA() {
        return nombreA;
    }

    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    @Override
    public String toString(){
        return "Agencia{" + 
                "ID Agencia: " + idAgencia +
                ", nombre: " + nombreA +
                ", telefono: " + telefono +
                ", direccion: " + direccion +
                "}";
    }
}
