package Controlador;

import DAO.HuespedDAO;
import Modelo.Huesped;
import Vista.VistaConsulta;
import Vista.VistaConsultaHuespedes;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Controlador para la consulta, modificación y eliminación de huéspedes
 */
public class ControladorConsultaHuespedes implements ActionListener {
    private VistaConsultaHuespedes vista;
    private VistaConsulta vistaAnterior;
    private HuespedDAO huespedDAO;
    private Huesped huespedActual;
    
    public ControladorConsultaHuespedes(VistaConsultaHuespedes vista, VistaConsulta vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.huespedDAO = new HuespedDAO();
        inicializarEventos();
        cargarTodosLosHuespedes();
    }
    
    private void inicializarEventos() {
        vista.getBotonconsultarhuesped_consultahuesped().addActionListener(this);
        vista.getBotonmodificarhuesped_consultahuesped().addActionListener(this);
        vista.getBotoneliminarhuesped_consultahuesped().addActionListener(this);
        vista.getBotonvolver_consultahusped().addActionListener(this);
        
        // Agregar listener para doble clic en la tabla
        vista.getTablaconsultahuesped().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    seleccionarHuespedDeTabla();
                }
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonconsultarhuesped_consultahuesped()) {
            consultarHuesped();
        } else if (e.getSource() == vista.getBotonmodificarhuesped_consultahuesped()) {
            modificarHuesped();
        } else if (e.getSource() == vista.getBotoneliminarhuesped_consultahuesped()) {
            eliminarHuesped();
        } else if (e.getSource() == vista.getBotonvolver_consultahusped()) {
            volver();
        }
    }
    
    /**
     * Consulta un huésped por ID
     * Si el campo está vacío, muestra todos los huéspedes
     */
    private void consultarHuesped() {
        String idStr = vista.getTxtconsulta_idhuesped().getText().trim();
        
        if (idStr.isEmpty()) {
            cargarTodosLosHuespedes();
            JOptionPane.showMessageDialog(vista, 
                "Mostrando todos los huéspedes", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            huespedActual = huespedDAO.buscarPorId(id);
            
            if (huespedActual != null) {
                // Mostrar datos en los campos
                vista.getTxtconsulta_nombrehuesped().setText(huespedActual.getNombre());
                vista.getTxtconsulta_direccionhuesped().setText(huespedActual.getDireccion());
                vista.getTxtconsulta_telefonohuesped().setText(huespedActual.getTelefono());
                vista.getTxtconsulta_fechadenacimiento().setText(
                    huespedActual.getFechaNacimiento() != null ? 
                    huespedActual.getFechaNacimiento().toString() : "");
                
                // Mostrar en la tabla
                List<Huesped> lista = new java.util.ArrayList<>();
                lista.add(huespedActual);
                cargarHuespedesEnTabla(lista);
                
                JOptionPane.showMessageDialog(vista, 
                    "Huésped encontrado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                limpiarCampos();
                limpiarTabla();
                JOptionPane.showMessageDialog(vista, 
                    "No se encontró ningún huésped con el ID: " + id, 
                    "No encontrado", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "El ID debe ser un número válido", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
            vista.getTxtconsulta_idhuesped().requestFocus();
        }
    }
    
    /**
     * Modifica los datos del huésped seleccionado
     */
    private void modificarHuesped() {
        if (huespedActual == null) {
            JOptionPane.showMessageDialog(vista, 
                "Primero debe consultar un huésped para poder modificarlo", 
                "No hay huésped seleccionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validar que los campos no estén vacíos
            String nombre = vista.getTxtconsulta_nombrehuesped().getText().trim();
            String direccion = vista.getTxtconsulta_direccionhuesped().getText().trim();
            String telefono = vista.getTxtconsulta_telefonohuesped().getText().trim();
            
            if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                    "Todos los campos son obligatorios", 
                    "Campos vacíos", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Actualizar los datos del huésped
            huespedActual.setNombre(nombre);
            huespedActual.setDireccion(direccion);
            huespedActual.setTelefono(telefono);
            
            // Intentar actualizar en la base de datos
            if (huespedDAO.actualizar(huespedActual)) {
                JOptionPane.showMessageDialog(vista, 
                    "Huésped actualizado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodosLosHuespedes();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al actualizar el huésped en la base de datos", 
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
    
    /**
     * Elimina el huésped seleccionado después de confirmar
     */
    private void eliminarHuesped() {
        if (huespedActual == null) {
            JOptionPane.showMessageDialog(vista, 
                "Primero debe consultar un huésped para poder eliminarlo", 
                "No hay huésped seleccionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar el huésped?\n\n" +
            "Nombre: " + huespedActual.getNombre() + "\n" +
            "ID: " + huespedActual.getIdHuesped() + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (huespedDAO.eliminar(huespedActual.getIdHuesped())) {
                JOptionPane.showMessageDialog(vista, 
                    "Huésped eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                huespedActual = null;
                cargarTodosLosHuespedes();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al eliminar el huésped.\n" +
                    "Es posible que tenga reservas asociadas.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Carga todos los huéspedes de la base de datos en la tabla
     */
    private void cargarTodosLosHuespedes() {
        try {
            List<Huesped> huespedes = huespedDAO.listarTodos();
            cargarHuespedesEnTabla(huespedes);
            
            if (huespedes.isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                    "No hay huéspedes registrados en el sistema", 
                    "Sin registros", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar huéspedes: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga una lista de huéspedes en la tabla
     * @param huespedes Lista de huéspedes a mostrar
     */
    private void cargarHuespedesEnTabla(List<Huesped> huespedes) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Fecha Nacimiento");
        
        for (Huesped h : huespedes) {
            modelo.addRow(new Object[]{
                h.getIdHuesped(), 
                h.getNombre(), 
                h.getDireccion(), 
                h.getTelefono(),
                h.getFechaNacimiento() != null ? h.getFechaNacimiento().toString() : "N/A"
            });
        }
        
        vista.getTablaconsultahuesped().setModel(modelo);
        
        // Ajustar ancho de columnas
        if (vista.getTablaconsultahuesped().getColumnModel().getColumnCount() > 0) {
            vista.getTablaconsultahuesped().getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            vista.getTablaconsultahuesped().getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
            vista.getTablaconsultahuesped().getColumnModel().getColumn(2).setPreferredWidth(200); // Dirección
            vista.getTablaconsultahuesped().getColumnModel().getColumn(3).setPreferredWidth(100); // Teléfono
            vista.getTablaconsultahuesped().getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha
        }
    }
    
    /**
     * Selecciona un huésped de la tabla al hacer doble clic
     */
    private void seleccionarHuespedDeTabla() {
        int filaSeleccionada = vista.getTablaconsultahuesped().getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            try {
                int idHuesped = (int) vista.getTablaconsultahuesped().getValueAt(filaSeleccionada, 0);
                huespedActual = huespedDAO.buscarPorId(idHuesped);
                
                if (huespedActual != null) {
                    vista.getTxtconsulta_idhuesped().setText(String.valueOf(huespedActual.getIdHuesped()));
                    vista.getTxtconsulta_nombrehuesped().setText(huespedActual.getNombre());
                    vista.getTxtconsulta_direccionhuesped().setText(huespedActual.getDireccion());
                    vista.getTxtconsulta_telefonohuesped().setText(huespedActual.getTelefono());
                    vista.getTxtconsulta_fechadenacimiento().setText(
                        huespedActual.getFechaNacimiento() != null ? 
                        huespedActual.getFechaNacimiento().toString() : "");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, 
                    "Error al seleccionar huésped: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Limpia todos los campos de texto
     */
    private void limpiarCampos() {
        vista.getTxtconsulta_idhuesped().setText("");
        vista.getTxtconsulta_nombrehuesped().setText("");
        vista.getTxtconsulta_direccionhuesped().setText("");
        vista.getTxtconsulta_telefonohuesped().setText("");
        vista.getTxtconsulta_fechadenacimiento().setText("");
        huespedActual = null;
    }
    
    /**
     * Limpia la tabla
     */
    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaconsultahuesped().getModel();
        modelo.setRowCount(0);
    }
    
    /**
     * Vuelve a la vista anterior (menú de consultas)
     */
    private void volver() {
        limpiarCampos();
        limpiarTabla();
        huespedActual = null;
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}