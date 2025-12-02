package Controlador;

import DAO.HuespedDAO;
import Modelo.Huesped;
import Vista.VistaRegistroHuesped;
import Vista.VistaRegistrar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

public class ControladorRegistroHuesped implements ActionListener {
    private VistaRegistroHuesped vista;
    private VistaRegistrar vistaRegistrar;
    private HuespedDAO huespedDAO;
    private DateTimeFormatter formatter;
    
    public ControladorRegistroHuesped(VistaRegistroHuesped vista, VistaRegistrar vistaRegistrar) {
        this.vista = vista;
        this.vistaRegistrar = vistaRegistrar;
        this.huespedDAO = new HuespedDAO();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        this.vista.getBotonguardarhuesped().addActionListener(this);
        this.vista.getBotonlimpiarhuesped().addActionListener(this);
        this.vista.getBotonvolver_resgistrohuesped().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonguardarhuesped()) {
            guardarHuesped();
        } else if (e.getSource() == vista.getBotonlimpiarhuesped()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBotonvolver_resgistrohuesped()) {
            volver();
        }
    }
    
    private void guardarHuesped() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            Huesped huesped = new Huesped();
            huesped.setIdHuesped(Integer.parseInt(vista.getTxtidhuesped_registrohuesped().getText().trim()));
            huesped.setNombre(vista.getTxtnombrehuesped_resgistrohuesped().getText().trim());
            huesped.setDireccion(vista.getTxtdireccionhuesped_registrohuesped().getText().trim());
            huesped.setTelefono(vista.getTxttelefonohuesped_registrohuesped().getText().trim());
            
            String fechaTexto = vista.getTxtfechantohuesped_registrohuesped().getText().trim();
            LocalDate fechaNacimiento = LocalDate.parse(fechaTexto, formatter);
            huesped.setFechaNacimiento(fechaNacimiento);
            
            if (huespedDAO.existe(huesped.getIdHuesped())) {
                JOptionPane.showMessageDialog(vista,
                    "El ID del huésped ya existe en la base de datos.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean exito = huespedDAO.insertar(huesped);
            
            if (exito) {
                JOptionPane.showMessageDialog(vista,
                    "Huésped registrado exitosamente.\n\nAhora puede continuar con el registro de reserva desde el menú principal.",
                    "Registro Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);
                
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al registrar el huésped. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista,
                "El ID debe ser un número válido.",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(vista,
                "La fecha debe estar en formato yyyy-MM-dd (ejemplo: 1990-05-15).",
                "Error de Formato de Fecha",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                "Error inesperado: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        if (vista.getTxtidhuesped_registrohuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Debe ingresar el ID del huésped.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtidhuesped_registrohuesped().requestFocus();
            return false;
        }
        
        if (vista.getTxtnombrehuesped_resgistrohuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Debe ingresar el nombre del huésped.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtnombrehuesped_resgistrohuesped().requestFocus();
            return false;
        }
        
        if (vista.getTxtdireccionhuesped_registrohuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Debe ingresar la dirección del huésped.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtdireccionhuesped_registrohuesped().requestFocus();
            return false;
        }
        
        if (vista.getTxttelefonohuesped_registrohuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Debe ingresar el teléfono del huésped.",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            vista.getTxttelefonohuesped_registrohuesped().requestFocus();
            return false;
        }
        
        if (vista.getTxtfechantohuesped_registrohuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "Debe ingresar la fecha de nacimiento (formato: yyyy-MM-dd).",
                "Campo Requerido",
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtfechantohuesped_registrohuesped().requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        vista.getTxtidhuesped_registrohuesped().setText("");
        vista.getTxtnombrehuesped_resgistrohuesped().setText("");
        vista.getTxtdireccionhuesped_registrohuesped().setText("");
        vista.getTxttelefonohuesped_registrohuesped().setText("");
        vista.getTxtfechantohuesped_registrohuesped().setText("");
        vista.getTxtidhuesped_registrohuesped().requestFocus();
    }
    
    private void volver() {
        vistaRegistrar.setVisible(true);
        vista.dispose();
    }
}