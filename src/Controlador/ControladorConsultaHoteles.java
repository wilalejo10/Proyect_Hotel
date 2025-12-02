package Controlador;

import DAO.HotelDAO;
import Modelo.Hotel;
import Vista.VistaConsulta;
import Vista.VistaConsultaHoteles;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la consulta, modificación y eliminación de hoteles
 * Incluye funcionalidad de cambio de categoría (Regla #2)
 */
public class ControladorConsultaHoteles implements ActionListener {
    private VistaConsultaHoteles vista;
    private VistaConsulta vistaAnterior;
    private HotelDAO hotelDAO;
    private Hotel hotelActual;
    
    public ControladorConsultaHoteles(VistaConsultaHoteles vista, VistaConsulta vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.hotelDAO = new HotelDAO();
        inicializarEventos();
        cargarTodosLosHoteles();
    }
    
    private void inicializarEventos() {
        vista.getBotonconsultarhotel_consultahotel().addActionListener(this);
        vista.getBotonmodificarhotel_consultahotel().addActionListener(this);
        vista.getBotoneliminarhotel_consultahotel().addActionListener(this);
        vista.getBontonvolver_consultahotel().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonconsultarhotel_consultahotel()) {
            consultarHotel();
        } else if (e.getSource() == vista.getBotonmodificarhotel_consultahotel()) {
            modificarHotel();
        } else if (e.getSource() == vista.getBotoneliminarhotel_consultahotel()) {
            eliminarHotel();
        } else if (e.getSource() == vista.getBontonvolver_consultahotel()) {
            volver();
        }
    }
    
    private void consultarHotel() {
        String nombre = vista.getTxtconsulta_nombrehotel().getText().trim();
        
        if (nombre.isEmpty()) {
            // Si no hay nombre, mostrar todos
            cargarTodosLosHoteles();
            return;
        }
        
        // Buscar por nombre
        List<Hotel> hoteles = hotelDAO.buscarPorNombre(nombre);
        
        if (!hoteles.isEmpty()) {
            cargarHotelesEnTabla(hoteles);
            JOptionPane.showMessageDialog(vista,
                "Se encontraron " + hoteles.size() + " hotel(es)",
                "Resultados",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            limpiarTabla();
            JOptionPane.showMessageDialog(vista,
                "No se encontraron hoteles con ese nombre",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void modificarHotel() {
        if (hotelActual == null) {
            JOptionPane.showMessageDialog(vista,
                "Seleccione un hotel de la tabla haciendo doble clic",
                "No hay hotel seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Mostrar opciones de modificación
        String[] opciones = {
            "Modificar datos básicos",
            "Aumentar categoría",
            "Disminuir categoría",
            "Cancelar"
        };
        
        int opcion = JOptionPane.showOptionDialog(vista,
            "Seleccione el tipo de modificación:",
            "Modificar Hotel",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);
        
        switch (opcion) {
            case 0:
                modificarDatosBasicos();
                break;
            case 1:
                aumentarCategoria();
                break;
            case 2:
                disminuirCategoria();
                break;
        }
    }
    
    private void modificarDatosBasicos() {
        try {
            // Actualizar datos del hotel
            hotelActual.setNombre(vista.getTxtconsulta_nombrehotel().getText().trim());
            hotelActual.setDireccion(vista.getTxtconsulta_direccionhotel().getText().trim());
            hotelActual.setTelefono(vista.getTxtconsulta_telefonohotel().getText().trim());
            hotelActual.setAnioInauguracion(
                Integer.parseInt(vista.getTxtconsulta_añodeinauguracion().getText().trim()));
            
            // Validar campos
            if (hotelActual.getNombre().isEmpty() || 
                hotelActual.getDireccion().isEmpty() || 
                hotelActual.getTelefono().isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Todos los campos son obligatorios",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Actualizar en la base de datos
            if (hotelDAO.actualizar(hotelActual)) {
                JOptionPane.showMessageDialog(vista,
                    "Hotel actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodosLosHoteles();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al actualizar el hotel",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "El año de inauguración debe ser un número válido",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Aumenta la categoría del hotel (Regla de negocio #2)
     */
    private void aumentarCategoria() {
        if (hotelActual.getCategoria() >= 5) {
            JOptionPane.showMessageDialog(vista,
                "El hotel ya tiene la categoría máxima (5 estrellas)",
                "Límite alcanzado",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Aumentar la categoría del hotel de " + hotelActual.getCategoria() + 
            " a " + (hotelActual.getCategoria() + 1) + " estrellas?\n" +
            "Este cambio quedará registrado con fecha.",
            "Confirmar aumento",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (hotelDAO.aumentarCategoria(hotelActual.getIdHotel())) {
                JOptionPane.showMessageDialog(vista,
                    "Categoría aumentada exitosamente\n" +
                    "Nueva categoría: " + (hotelActual.getCategoria() + 1) + " estrellas",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                hotelActual = hotelDAO.buscarPorId(hotelActual.getIdHotel());
                cargarTodosLosHoteles();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al aumentar la categoría",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Disminuye la categoría del hotel (Regla de negocio #2)
     */
    private void disminuirCategoria() {
        if (hotelActual.getCategoria() <= 1) {
            JOptionPane.showMessageDialog(vista,
                "El hotel ya tiene la categoría mínima (1 estrella)",
                "Límite alcanzado",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Disminuir la categoría del hotel de " + hotelActual.getCategoria() + 
            " a " + (hotelActual.getCategoria() - 1) + " estrellas?\n" +
            "Este cambio quedará registrado con fecha.",
            "Confirmar disminución",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (hotelDAO.disminuirCategoria(hotelActual.getIdHotel())) {
                JOptionPane.showMessageDialog(vista,
                    "Categoría disminuida exitosamente\n" +
                    "Nueva categoría: " + (hotelActual.getCategoria() - 1) + " estrellas",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                hotelActual = hotelDAO.buscarPorId(hotelActual.getIdHotel());
                cargarTodosLosHoteles();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al disminuir la categoría",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void eliminarHotel() {
        if (hotelActual == null) {
            JOptionPane.showMessageDialog(vista,
                "Seleccione un hotel de la tabla",
                "No hay hotel seleccionado",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar el hotel:\n" +
            hotelActual.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (hotelDAO.eliminar(hotelActual.getIdHotel())) {
                JOptionPane.showMessageDialog(vista,
                    "Hotel eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                hotelActual = null;
                cargarTodosLosHoteles();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al eliminar el hotel.\n" +
                    "Puede que tenga habitaciones o reservas asociadas.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarTodosLosHoteles() {
        List<Hotel> hoteles = hotelDAO.listarTodos();
        cargarHotelesEnTabla(hoteles);
    }
    
    private void cargarHotelesEnTabla(List<Hotel> hoteles) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Año Inauguración");
        modelo.addColumn("Categoría");
        modelo.addColumn("Antigüedad");
        
        for (Hotel hotel : hoteles) {
            Object[] fila = {
                hotel.getIdHotel(),
                hotel.getNombre(),
                hotel.getDireccion(),
                hotel.getTelefono(),
                hotel.getAnioInauguracion(),
                hotel.getCategoria() + " estrellas",
                hotel.calcularAntiguedad() + " años"
            };
            modelo.addRow(fila);
        }
        
        vista.getTablaconsultarhoteles().setModel(modelo);
        
        // Agregar listener para selección
        vista.getTablaconsultarhoteles().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    seleccionarHotelDeTabla();
                }
            }
        });
    }
    
    private void seleccionarHotelDeTabla() {
        int fila = vista.getTablaconsultarhoteles().getSelectedRow();
        if (fila >= 0) {
            int idHotel = (int) vista.getTablaconsultarhoteles().getValueAt(fila, 0);
            hotelActual = hotelDAO.buscarPorId(idHotel);
            
            if (hotelActual != null) {
                vista.getTxtconsulta_nombrehotel().setText(hotelActual.getNombre());
                vista.getTxtconsulta_direccionhotel().setText(hotelActual.getDireccion());
                vista.getTxtconsulta_telefonohotel().setText(hotelActual.getTelefono());
                vista.getTxtconsulta_añodeinauguracion().setText(
                    String.valueOf(hotelActual.getAnioInauguracion()));
            }
        }
    }
    
    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaconsultarhoteles().getModel();
        modelo.setRowCount(0);
    }
    
    private void limpiarCampos() {
        vista.getTxtconsulta_nombrehotel().setText("");
        vista.getTxtconsulta_direccionhotel().setText("");
        vista.getTxtconsulta_telefonohotel().setText("");
        vista.getTxtconsulta_añodeinauguracion().setText("");
    }
    
    private void volver() {
        limpiarCampos();
        limpiarTabla();
        hotelActual = null;
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}