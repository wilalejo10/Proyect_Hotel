package Controlador;

import DAO.AgenciaDAO;
import Modelo.Agencia;
import Vista.VistaConsulta;
import Vista.VistaConsultaAgencias;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para la consulta, modificación y eliminación de agencias
 */
public class ControladorConsultaAgencias implements ActionListener {
    private VistaConsultaAgencias vista;
    private VistaConsulta vistaAnterior;
    private AgenciaDAO agenciaDAO;
    private Agencia agenciaActual;
    
    public ControladorConsultaAgencias(VistaConsultaAgencias vista, VistaConsulta vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.agenciaDAO = new AgenciaDAO();
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        vista.getBotonconsultaagencia_consultaagencia().addActionListener(this);
        vista.getBotonmodificaragencias_consultaagencias().addActionListener(this);
        vista.getBotoneliminaragencias_consultaagencias().addActionListener(this);
        vista.getBontonvolver_consultaagencias().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonconsultaagencia_consultaagencia()) {
            consultarAgencia();
        } else if (e.getSource() == vista.getBotonmodificaragencias_consultaagencias()) {
            modificarAgencia();
        } else if (e.getSource() == vista.getBotoneliminaragencias_consultaagencias()) {
            eliminarAgencia();
        } else if (e.getSource() == vista.getBontonvolver_consultaagencias()) {
            volver();
        }
    }
    
    private void consultarAgencia() {
        String idStr = vista.getTxtconsultaidagencia().getText().trim();
        
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Ingrese el ID de la agencia a consultar",
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            agenciaActual = agenciaDAO.buscarPorId(id);
            
            if (agenciaActual != null) {
                // Mostrar datos en los campos
                vista.getTxtconsultanombreagencia().setText(agenciaActual.getNombre());
                vista.getTxtconsultadireccionagencia().setText(agenciaActual.getDireccion());
                vista.getTxtconsultatelefonoagencia().setText(agenciaActual.getTelefono());
                
                JOptionPane.showMessageDialog(vista,
                    "Agencia encontrada",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                limpiarCampos();
                JOptionPane.showMessageDialog(vista,
                    "No se encontró ninguna agencia con el ID: " + id,
                    "No encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "El ID debe ser un número válido",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarAgencia() {
        if (agenciaActual == null) {
            JOptionPane.showMessageDialog(vista,
                "Primero debe consultar una agencia",
                "No hay agencia seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Actualizar datos de la agencia
            agenciaActual.setNombre(vista.getTxtconsultanombreagencia().getText().trim());
            agenciaActual.setDireccion(vista.getTxtconsultadireccionagencia().getText().trim());
            agenciaActual.setTelefono(vista.getTxtconsultatelefonoagencia().getText().trim());
            
            // Validar campos
            if (agenciaActual.getNombre().isEmpty() || 
                agenciaActual.getDireccion().isEmpty() || 
                agenciaActual.getTelefono().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Todos los campos son obligatorios",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Actualizar en la base de datos
            if (agenciaDAO.actualizar(agenciaActual)) {
                JOptionPane.showMessageDialog(vista,
                    "Agencia actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al actualizar la agencia",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error al modificar: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarAgencia() {
        if (agenciaActual == null) {
            JOptionPane.showMessageDialog(vista,
                "Primero debe consultar una agencia",
                "No hay agencia seleccionada",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar la agencia:\n" +
            agenciaActual.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (agenciaDAO.eliminar(agenciaActual.getIdAgencia())) {
                JOptionPane.showMessageDialog(vista,
                    "Agencia eliminada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                agenciaActual = null;
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al eliminar la agencia.\n" +
                    "Puede que tenga reservas asociadas.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void limpiarCampos() {
        vista.getTxtconsultaidagencia().setText("");
        vista.getTxtconsultanombreagencia().setText("");
        vista.getTxtconsultadireccionagencia().setText("");
        vista.getTxtconsultatelefonoagencia().setText("");
    }
    
    private void volver() {
        limpiarCampos();
        agenciaActual = null;
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}