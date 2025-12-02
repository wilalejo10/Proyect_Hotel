package Controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para manejar el menú de registro y direccionar al flujo correcto
 */
public class ControladorRegistrar implements ActionListener {
    private VistaRegistrar vistaRegistrar;
    private VistaPrincipal vistaPrincipal;
    
    // Vistas de registro
    private VistaRegistroAgencia vistaRegistroAgencia;
    private VistaRegistroHotel vistaRegistroHotel;
    private VistaRegistroHabitacion vistaRegistroHabitacion;
    private VistaRegistroHuesped vistaRegistroHuesped;
    private VistaRegistroReserva vistaRegistroReserva;
    
    public ControladorRegistrar(VistaRegistrar vistaRegistrar, VistaPrincipal vistaPrincipal) {
        this.vistaRegistrar = vistaRegistrar;
        this.vistaPrincipal = vistaPrincipal;
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        vistaRegistrar.getBotonregistraragencia().addActionListener(this);
        vistaRegistrar.getBotonregistrarhotel().addActionListener(this);
        vistaRegistrar.getBotonregistrarhabitacion().addActionListener(this);
        vistaRegistrar.getBotonregistrarhuesped().addActionListener(this);
        vistaRegistrar.getBotonregistrarreservacion().addActionListener(this);
        vistaRegistrar.getBotonregresomenu_resgistro().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaRegistrar.getBotonregistraragencia()) {
            abrirRegistroAgencia();
        } else if (e.getSource() == vistaRegistrar.getBotonregistrarhotel()) {
            abrirRegistroHotel();
        } else if (e.getSource() == vistaRegistrar.getBotonregistrarhabitacion()) {
            abrirRegistroHabitacion();
        } else if (e.getSource() == vistaRegistrar.getBotonregistrarhuesped()) {
            abrirRegistroHuesped();
        } else if (e.getSource() == vistaRegistrar.getBotonregistrarreservacion()) {
            abrirRegistroReserva();
        } else if (e.getSource() == vistaRegistrar.getBotonregresomenu_resgistro()) {
            regresarMenuPrincipal();
        }
    }
    
    private void abrirRegistroAgencia() {
        if (vistaRegistroAgencia == null) {
            vistaRegistroAgencia = new VistaRegistroAgencia();
            new ControladorRegistroAgencia(vistaRegistroAgencia, vistaRegistrar);
        }
        vistaRegistrar.setVisible(false);
        vistaRegistroAgencia.setVisible(true);
    }
    
    private void abrirRegistroHotel() {
        if (vistaRegistroHotel == null) {
            vistaRegistroHotel = new VistaRegistroHotel();
            new ControladorRegistroHotel(vistaRegistroHotel, vistaRegistrar);
        }
        vistaRegistrar.setVisible(false);
        vistaRegistroHotel.setVisible(true);
    }
    
    private void abrirRegistroHabitacion() {
        if (vistaRegistroHabitacion == null) {
            vistaRegistroHabitacion = new VistaRegistroHabitacion();
            new ControladorRegistroHabitacion(vistaRegistroHabitacion, vistaRegistrar);
        }
        vistaRegistrar.setVisible(false);
        vistaRegistroHabitacion.setVisible(true);
    }
    
    private void abrirRegistroHuesped() {
        if (vistaRegistroHuesped == null) {
            vistaRegistroHuesped = new VistaRegistroHuesped();
            new ControladorRegistroHuesped(vistaRegistroHuesped, vistaRegistrar);
        }
        vistaRegistrar.setVisible(false);
        vistaRegistroHuesped.setVisible(true);
    }
    
    private void abrirRegistroReserva() {
        if (vistaRegistroReserva == null) {
            vistaRegistroReserva = new VistaRegistroReserva();
            new ControladorRegistroReserva(vistaRegistroReserva, vistaRegistrar);
        }
        vistaRegistrar.setVisible(false);
        vistaRegistroReserva.setVisible(true);
    }
    
    private void regresarMenuPrincipal() {
        vistaRegistrar.setVisible(false);
        vistaPrincipal.setVisible(true);
    }
}