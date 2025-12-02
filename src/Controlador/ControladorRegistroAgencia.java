package Controlador;

import DAO.AgenciaDAO;
import Modelo.Agencia;
import Vista.VistaRegistroAgencia;
import Vista.VistaRegistrar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorRegistroAgencia implements ActionListener {
    private VistaRegistroAgencia vista;
    private VistaRegistrar vistaRegistrar;
    private AgenciaDAO agenciaDAO;
    
    public ControladorRegistroAgencia(VistaRegistroAgencia vista, VistaRegistrar vistaRegistrar) {
        this.vista = vista;
        this.vistaRegistrar = vistaRegistrar;
        this.agenciaDAO = new AgenciaDAO();
        
        this.vista.getBotonguardaragencia().addActionListener(this);
        this.vista.getBotonlimpiaragencia().addActionListener(this);
        this.vista.getBotonvolver_registroagencia().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonguardaragencia()) {
            guardarAgencia();
        } else if (e.getSource() == vista.getBotonlimpiaragencia()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBotonvolver_registroagencia()) {
            volver();
        }
    }
    
    private void guardarAgencia() {
        try {
            // Validar campos vacíos
            if (vista.getTxtidagencia().getText().trim().isEmpty() ||
                vista.getTxtnombreagencia().getText().trim().isEmpty() ||
                vista.getTxtdireccionagencia().getText().trim().isEmpty() ||
                vista.getTxttelefonoagencia().getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(vista, 
                    "Todos los campos son obligatorios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear objeto Agencia
            Agencia agencia = new Agencia();
            agencia.setIdAgencia(Integer.parseInt(vista.getTxtidagencia().getText().trim()));
            agencia.setNombre(vista.getTxtnombreagencia().getText().trim());
            agencia.setDireccion(vista.getTxtdireccionagencia().getText().trim());
            agencia.setTelefono(vista.getTxttelefonoagencia().getText().trim());
            
            // Guardar en base de datos
            if (agenciaDAO.insertar(agencia)) {
                JOptionPane.showMessageDialog(vista, 
                    "Agencia registrada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al registrar la agencia", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "El ID debe ser un número válido", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        vista.getTxtidagencia().setText("");
        vista.getTxtnombreagencia().setText("");
        vista.getTxtdireccionagencia().setText("");
        vista.getTxttelefonoagencia().setText("");
        vista.getTxtidagencia().requestFocus();
    }
    
    private void volver() {
        vistaRegistrar.setVisible(true);
        vista.dispose();
    }
}
