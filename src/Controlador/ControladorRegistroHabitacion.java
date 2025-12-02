package Controlador;

import DAO.HabitacionDAO;
import DAO.HotelDAO;
import Modelo.Habitacion;
import Modelo.Hotel;
import Vista.VistaRegistroHabitacion;
import Vista.VistaRegistrar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorRegistroHabitacion implements ActionListener {
    private VistaRegistroHabitacion vista;
    private VistaRegistrar vistaRegistrar;
    private HabitacionDAO habitacionDAO;
    private HotelDAO hotelDAO;
    
    public ControladorRegistroHabitacion(VistaRegistroHabitacion vista, VistaRegistrar vistaRegistrar) {
        this.vista = vista;
        this.vistaRegistrar = vistaRegistrar;
        this.habitacionDAO = new HabitacionDAO();
        this.hotelDAO = new HotelDAO();
        
        this.vista.getBotonguardarhabitacion().addActionListener(this);
        this.vista.getBotonlimpiarhabitacion().addActionListener(this);
        this.vista.getBotonvolver_registrohabitacion().addActionListener(this);
        
        cargarHotelesComboBox();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonguardarhabitacion()) {
            guardarHabitacion();
        } else if (e.getSource() == vista.getBotonlimpiarhabitacion()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBotonvolver_registrohabitacion()) {
            volver();
        }
    }
    
    private void cargarHotelesComboBox() {
        vista.getComboboxidhotel().removeAllItems();
        List<Hotel> hoteles = hotelDAO.listarTodos();
        
        for (Hotel hotel : hoteles) {
            vista.getComboboxidhotel().addItem(hotel.getIdHotel() + " - " + hotel.getNombre());
        }
    }
    
    private void guardarHabitacion() {
        try {
            // Validar campos
            if (vista.getTxtidhabitacion().getText().trim().isEmpty() ||
                vista.getTxtnumerohabitacion().getText().trim().isEmpty() ||
                vista.getTxttipohabitacion().getText().trim().isEmpty() ||
                vista.getComboboxidhotel().getSelectedItem() == null) {
                
                JOptionPane.showMessageDialog(vista, 
                    "Todos los campos son obligatorios", 
                    "Error de validación", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Extraer ID del hotel del ComboBox
            String hotelSeleccionado = vista.getComboboxidhotel().getSelectedItem().toString();
            int idHotel = Integer.parseInt(hotelSeleccionado.split(" - ")[0]);
            Hotel hotel = hotelDAO.buscarPorId(idHotel);
            
            // Crear objeto Habitacion
            Habitacion habitacion = new Habitacion();
            habitacion.setIdHabitacion(Integer.parseInt(vista.getTxtidhabitacion().getText().trim()));
            habitacion.setIdHotel(idHotel);
            habitacion.setNombreHotel(hotel.getNombre());
            habitacion.setNumeroHabitacion(Integer.parseInt(vista.getTxtnumerohabitacion().getText().trim()));
            habitacion.setTipoHabitacion(vista.getTxttipohabitacion().getText().trim());
            
            // Guardar en base de datos
            if (habitacionDAO.insertar(habitacion)) {
                JOptionPane.showMessageDialog(vista, 
                    "Habitación registrada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al registrar la habitación", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "Los IDs y número de habitación deben ser números válidos", 
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
        vista.getTxtidhabitacion().setText("");
        vista.getTxtnumerohabitacion().setText("");
        vista.getTxttipohabitacion().setText("");
        vista.getTxtidhabitacion().requestFocus();
    }
    
    private void volver() {
        vistaRegistrar.setVisible(true);
        vista.dispose();
    }
}
