package Controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para el menú de consultas
 * Gestiona la navegación entre las diferentes vistas de consulta
 */
public class ControladorConsulta implements ActionListener {
    private VistaConsulta vistaConsulta;
    private VistaPrincipal vistaPrincipal;
    
    // Vistas de consulta
    private VistaConsultaAgencias vistaConsultaAgencias;
    private VistaConsultaHoteles vistaConsultaHoteles;
    private VistaConsultaHabitaciones vistaConsultaHabitaciones;
    private VistaConsultaHuespedes vistaConsultaHuespedes;
    private VistaConsultaReservas vistaConsultaReservas;
    
    public ControladorConsulta(VistaConsulta vistaConsulta, VistaPrincipal vistaPrincipal) {
        this.vistaConsulta = vistaConsulta;
        this.vistaPrincipal = vistaPrincipal;
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        vistaConsulta.getBotonconsultaagencias().addActionListener(this);
        vistaConsulta.getBotonconsultahoteles().addActionListener(this);
        vistaConsulta.getBotonconsultahabitaciones().addActionListener(this);
        vistaConsulta.getBotonconsultahuespedes().addActionListener(this);
        vistaConsulta.getBotonconsultareservas().addActionListener(this);
        vistaConsulta.getBotonregresomenu().addActionListener(this);
        
        // Agregar listener para el botón de volver (si existe en la vista)
        // Este listener permite regresar al menú principal
        vistaConsulta.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                volverMenuPrincipal();
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaConsulta.getBotonconsultaagencias()) {
            abrirConsultaAgencias();
        } else if (e.getSource() == vistaConsulta.getBotonconsultahoteles()) {
            abrirConsultaHoteles();
        } else if (e.getSource() == vistaConsulta.getBotonconsultahabitaciones()) {
            abrirConsultaHabitaciones();
        } else if (e.getSource() == vistaConsulta.getBotonconsultahuespedes()) {
            abrirConsultaHuespedes();
        } else if (e.getSource() == vistaConsulta.getBotonconsultareservas()) {
            abrirConsultaReservas();
        } else if (e.getSource() == vistaConsulta.getBotonregresomenu()){
            volverMenuPrincipal();
        }
    }
    
    /**
     * Abre la vista de consulta de agencias
     * Crea la vista y su controlador si no existe
     */
    private void abrirConsultaAgencias() {
        try {
            if (vistaConsultaAgencias == null) {
                vistaConsultaAgencias = new VistaConsultaAgencias();
                new ControladorConsultaAgencias(vistaConsultaAgencias, vistaConsulta);
            }
            vistaConsulta.setVisible(false);
            vistaConsultaAgencias.setVisible(true);
            vistaConsultaAgencias.setLocationRelativeTo(null); // Centrar ventana
        } catch (Exception ex) {
            System.err.println("Error al abrir consulta de agencias: " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(vistaConsulta,
                "Error al abrir la ventana de consulta de agencias",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Abre la vista de consulta de hoteles
     * Crea la vista y su controlador si no existe
     */
    private void abrirConsultaHoteles() {
        try {
            if (vistaConsultaHoteles == null) {
                vistaConsultaHoteles = new VistaConsultaHoteles();
                new ControladorConsultaHoteles(vistaConsultaHoteles, vistaConsulta);
            }
            vistaConsulta.setVisible(false);
            vistaConsultaHoteles.setVisible(true);
            vistaConsultaHoteles.setLocationRelativeTo(null); // Centrar ventana
        } catch (Exception ex) {
            System.err.println("Error al abrir consulta de hoteles: " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(vistaConsulta,
                "Error al abrir la ventana de consulta de hoteles",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Abre la vista de consulta de habitaciones
     * Crea la vista y su controlador si no existe
     */
    private void abrirConsultaHabitaciones() {
        try {
            if (vistaConsultaHabitaciones == null) {
                vistaConsultaHabitaciones = new VistaConsultaHabitaciones();
                new ControladorConsultaHabitaciones(vistaConsultaHabitaciones, vistaConsulta);
            }
            vistaConsulta.setVisible(false);
            vistaConsultaHabitaciones.setVisible(true);
            vistaConsultaHabitaciones.setLocationRelativeTo(null); // Centrar ventana
        } catch (Exception ex) {
            System.err.println("Error al abrir consulta de habitaciones: " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(vistaConsulta,
                "Error al abrir la ventana de consulta de habitaciones",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Abre la vista de consulta de huéspedes
     * Crea la vista y su controlador si no existe
     */
    private void abrirConsultaHuespedes() {
        try {
            if (vistaConsultaHuespedes == null) {
                vistaConsultaHuespedes = new VistaConsultaHuespedes();
                new ControladorConsultaHuespedes(vistaConsultaHuespedes, vistaConsulta);
            }
            vistaConsulta.setVisible(false);
            vistaConsultaHuespedes.setVisible(true);
            vistaConsultaHuespedes.setLocationRelativeTo(null); // Centrar ventana
        } catch (Exception ex) {
            System.err.println("Error al abrir consulta de huéspedes: " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(vistaConsulta,
                "Error al abrir la ventana de consulta de huéspedes",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Abre la vista de consulta de reservas
     * Crea la vista y su controlador si no existe
     */
    private void abrirConsultaReservas() {
        try {
            if (vistaConsultaReservas == null) {
                vistaConsultaReservas = new VistaConsultaReservas();
                new ControladorConsultaReservas(vistaConsultaReservas, vistaConsulta);
            }
            vistaConsulta.setVisible(false);
            vistaConsultaReservas.setVisible(true);
            vistaConsultaReservas.setLocationRelativeTo(null); // Centrar ventana
        } catch (Exception ex) {
            System.err.println("Error al abrir consulta de reservas: " + ex.getMessage());
            javax.swing.JOptionPane.showMessageDialog(vistaConsulta,
                "Error al abrir la ventana de consulta de reservas",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Vuelve al menú principal
     */
    private void volverMenuPrincipal() {
        vistaConsulta.setVisible(false);
        if (vistaPrincipal != null) {
            vistaPrincipal.setVisible(true);
        }
    }
    
    /**
     * Método público para cerrar todas las vistas de consulta
     * Útil cuando se cierra la aplicación o se cambia de módulo
     */
    public void cerrarTodasLasVistas() {
        if (vistaConsultaAgencias != null) {
            vistaConsultaAgencias.dispose();
            vistaConsultaAgencias = null;
        }
        if (vistaConsultaHoteles != null) {
            vistaConsultaHoteles.dispose();
            vistaConsultaHoteles = null;
        }
        if (vistaConsultaHabitaciones != null) {
            vistaConsultaHabitaciones.dispose();
            vistaConsultaHabitaciones = null;
        }
        if (vistaConsultaHuespedes != null) {
            vistaConsultaHuespedes.dispose();
            vistaConsultaHuespedes = null;
        }
        if (vistaConsultaReservas != null) {
            vistaConsultaReservas.dispose();
            vistaConsultaReservas = null;
        }
    }
    
    /**
     * Obtiene la vista principal de consultas
     * @return VistaConsulta
     */
    public VistaConsulta getVistaConsulta() {
        return vistaConsulta;
    }
}