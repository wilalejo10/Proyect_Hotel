package Controlador;

import DAO.HabitacionDAO;
import Modelo.Habitacion;
import Vista.VistaConsulta;
import Vista.VistaConsultaHabitaciones;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ControladorConsultaHabitaciones implements ActionListener {
    private VistaConsultaHabitaciones vista;
    private VistaConsulta vistaAnterior;
    private HabitacionDAO habitacionDAO;
    private Habitacion habitacionActual;
    
    public ControladorConsultaHabitaciones(VistaConsultaHabitaciones vista, VistaConsulta vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.habitacionDAO = new HabitacionDAO();
        inicializarEventos();
        cargarTodasLasHabitaciones();
    }
    
    private void inicializarEventos() {
        vista.getBotonconsultarhabitacion_consultahabitacion().addActionListener(this);
        vista.getBotonmodificarhabitacion_consultahabitacion().addActionListener(this);
        vista.getBotonaliminarhabitacion_consultahabitacion().addActionListener(this);
        vista.getBontonvolver_consultahabitacion().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonconsultarhabitacion_consultahabitacion()) {
            consultarHabitacion();
        } else if (e.getSource() == vista.getBotonmodificarhabitacion_consultahabitacion()) {
            modificarHabitacion();
        } else if (e.getSource() == vista.getBotonaliminarhabitacion_consultahabitacion()) {
            eliminarHabitacion();
        } else if (e.getSource() == vista.getBontonvolver_consultahabitacion()) {
            volver();
        }
    }
    
    private void consultarHabitacion() {
        String idStr = vista.getTxtconsultaidhabitacion().getText().trim();
        
        if (idStr.isEmpty()) {
            cargarTodasLasHabitaciones();
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            habitacionActual = habitacionDAO.buscarPorId(id);
            
            if (habitacionActual != null) {
                vista.getTxtconsultaidhotel().setText(String.valueOf(habitacionActual.getIdHotel()));
                vista.getTxtconsultanumerodehabitacion().setText(String.valueOf(habitacionActual.getNumeroHabitacion()));
                vista.getTxtconsultatipohabitacion().setText(habitacionActual.getTipoHabitacion());
                
                List<Habitacion> lista = new java.util.ArrayList<>();
                lista.add(habitacionActual);
                cargarHabitacionesEnTabla(lista);
                
                JOptionPane.showMessageDialog(vista, "Habitación encontrada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                limpiarCampos();
                JOptionPane.showMessageDialog(vista, "No se encontró la habitación", "No encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarHabitacion() {
        if (habitacionActual == null) {
            JOptionPane.showMessageDialog(vista, "Primero consulte una habitación", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            habitacionActual.setIdHotel(Integer.parseInt(vista.getTxtconsultaidhotel().getText().trim()));
            habitacionActual.setNumeroHabitacion(Integer.parseInt(vista.getTxtconsultanumerodehabitacion().getText().trim()));
            habitacionActual.setTipoHabitacion(vista.getTxtconsultatipohabitacion().getText().trim());
            
            if (habitacionDAO.actualizar(habitacionActual)) {
                JOptionPane.showMessageDialog(vista, "Habitación actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTodasLasHabitaciones();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarHabitacion() {
        if (habitacionActual == null) {
            JOptionPane.showMessageDialog(vista, "Primero consulte una habitación", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Eliminar habitación " + habitacionActual.getNumeroHabitacion() + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (habitacionDAO.eliminar(habitacionActual.getIdHabitacion())) {
                JOptionPane.showMessageDialog(vista, "Habitación eliminada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                habitacionActual = null;
                cargarTodasLasHabitaciones();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarTodasLasHabitaciones() {
        List<Habitacion> habitaciones = habitacionDAO.listarTodas();
        cargarHabitacionesEnTabla(habitaciones);
    }
    
    private void cargarHabitacionesEnTabla(List<Habitacion> habitaciones) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("ID Hotel");
        modelo.addColumn("Número");
        modelo.addColumn("Tipo");
        
        for (Habitacion h : habitaciones) {
            modelo.addRow(new Object[]{h.getIdHabitacion(), h.getIdHotel(), h.getNumeroHabitacion(), h.getTipoHabitacion()});
        }
        
        vista.getTablaconsultahabitacion().setModel(modelo);
    }
    
    private void limpiarCampos() {
        vista.getTxtconsultaidhabitacion().setText("");
        vista.getTxtconsultaidhotel().setText("");
        vista.getTxtconsultanumerodehabitacion().setText("");
        vista.getTxtconsultatipohabitacion().setText("");
    }
    
    private void volver() {
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}