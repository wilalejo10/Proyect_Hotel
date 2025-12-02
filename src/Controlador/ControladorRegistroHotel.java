package Controlador;

import DAO.HotelDAO;
import Modelo.Hotel;
import Vista.VistaRegistroHotel;
import Vista.VistaRegistrar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorRegistroHotel implements ActionListener {
    private VistaRegistroHotel vista;
    private VistaRegistrar vistaRegistrar;
    private HotelDAO hotelDAO;
    
    public ControladorRegistroHotel(VistaRegistroHotel vista, VistaRegistrar vistaRegistrar) {
        this.vista = vista;
        this.vistaRegistrar = vistaRegistrar;
        this.hotelDAO = new HotelDAO();
        
        this.vista.getBotonguardarhotel().addActionListener(this);
        this.vista.getBotonlimpiarhotel().addActionListener(this);
        this.vista.getBotonvolver_registrohotel().addActionListener(this);
        
        cargarCategoriasComboBox();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonguardarhotel()) {
            guardarHotel();
        } else if (e.getSource() == vista.getBotonlimpiarhotel()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBotonvolver_registrohotel()) {
            volver();
        }
    }
    
    private void cargarCategoriasComboBox() {
        vista.getComboboxcategoria().removeAllItems();
        vista.getComboboxcategoria().addItem("1 Estrella");
        vista.getComboboxcategoria().addItem("2 Estrellas");
        vista.getComboboxcategoria().addItem("3 Estrellas");
        vista.getComboboxcategoria().addItem("4 Estrellas");
        vista.getComboboxcategoria().addItem("5 Estrellas");
    }
    
    private void guardarHotel() {
        try {
            // Validar campos
            if (vista.getTxtnombrehotel().getText().trim().isEmpty() ||
                vista.getTxtdireccionhotel().getText().trim().isEmpty() ||
                vista.getTxttelefonohotel().getText().trim().isEmpty() ||
                vista.getTxtanioinauguracion().getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(vista, 
                    "Todos los campos son obligatorios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear objeto Hotel
            Hotel hotel = new Hotel();
            hotel.setNombre(vista.getTxtnombrehotel().getText().trim());
            hotel.setDireccion(vista.getTxtdireccionhotel().getText().trim());
            hotel.setTelefono(vista.getTxttelefonohotel().getText().trim());
            hotel.setAnioInauguracion(Integer.parseInt(vista.getTxtanioinauguracion().getText().trim()));
            hotel.setCategoria(vista.getComboboxcategoria().getSelectedIndex() + 1);
            
            // Guardar en base de datos
            if (hotelDAO.insertar(hotel)) {
                JOptionPane.showMessageDialog(vista, 
                    "Hotel registrado exitosamente\nAntigüedad: " + hotel.calcularAntiguedad() + " años", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al registrar el hotel", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "El año de inauguración debe ser un número válido", 
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
        vista.getTxtnombrehotel().setText("");
        vista.getTxtdireccionhotel().setText("");
        vista.getTxttelefonohotel().setText("");
        vista.getTxtanioinauguracion().setText("");
        vista.getComboboxcategoria().setSelectedIndex(0);
        vista.getTxtnombrehotel().requestFocus();
    }
    
    private void volver() {
        vistaRegistrar.setVisible(true);
        vista.dispose();
    }
}
