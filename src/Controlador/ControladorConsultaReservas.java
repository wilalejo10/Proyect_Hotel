package Controlador;

import DAO.ReservaDAO;
import Modelo.Reserva;
import Vista.VistaConsulta;
import Vista.VistaConsultaReservas;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador para la consulta, modificación y eliminación de reservas
 */
public class ControladorConsultaReservas implements ActionListener {
    private VistaConsultaReservas vista;
    private VistaConsulta vistaAnterior;
    private ReservaDAO reservaDAO;
    private Reserva reservaActual;
    private DateTimeFormatter dateFormatter;
    
    public ControladorConsultaReservas(VistaConsultaReservas vista, VistaConsulta vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.reservaDAO = new ReservaDAO();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        inicializarEventos();
        cargarTiposHabitacion();
        cargarTodasLasReservas();
    }
    
    private void inicializarEventos() {
        vista.getBotonconsultareserva_consultareseva().addActionListener(this);
        vista.getBotonmodificarreserva_consultareseva().addActionListener(this);
        vista.getBotoneliminarreserva_consultareseva().addActionListener(this);
        vista.getBotonvolver_consultareseva().addActionListener(this);
        
        // Agregar listener para doble clic en la tabla
        vista.getTablaconsultareserva().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    seleccionarReservaDeTabla();
                }
            }
        });
    }
    
    private void cargarTiposHabitacion() {
        vista.getComboxconsulta_tipodehabitacion().removeAllItems();
        vista.getComboxconsulta_tipodehabitacion().addItem("Individual");
        vista.getComboxconsulta_tipodehabitacion().addItem("Doble");
        vista.getComboxconsulta_tipodehabitacion().addItem("Suite");
        vista.getComboxconsulta_tipodehabitacion().addItem("Familiar");
        vista.getComboxconsulta_tipodehabitacion().addItem("Presidencial");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonconsultareserva_consultareseva()) {
            consultarReserva();
        } else if (e.getSource() == vista.getBotonmodificarreserva_consultareseva()) {
            modificarReserva();
        } else if (e.getSource() == vista.getBotoneliminarreserva_consultareseva()) {
            eliminarReserva();
        } else if (e.getSource() == vista.getBotonvolver_consultareseva()) {
            volver();
        }
    }
    
    /**
     * Consulta una reserva por ID
     * Si el campo está vacío, muestra todas las reservas
     */
    private void consultarReserva() {
        String idStr = vista.getTxtconsulta_idreserva().getText().trim();
        
        if (idStr.isEmpty()) {
            cargarTodasLasReservas();
            JOptionPane.showMessageDialog(vista, 
                "Mostrando todas las reservas", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(idStr);
            reservaActual = reservaDAO.buscarPorId(id);
            
            if (reservaActual != null) {
                // Cargar datos en los campos
                vista.getTxtconsulta_idhuesped().setText(String.valueOf(reservaActual.getIdHuesped()));
                vista.getTxtconsulta_idagencia().setText(
                    reservaActual.getIdAgencia() != null ? String.valueOf(reservaActual.getIdAgencia()) : "");
                vista.getTxtconsulta_fechainicio().setText(reservaActual.getFechaInicio().toString());
                vista.getTxtconsulta_fechafin().setText(reservaActual.getFechaFin().toString());
                vista.getTxtconsulta_cantidadpersonasreserva().setText(String.valueOf(reservaActual.getCantidadPersonas()));
                vista.getTxtconsulta_cantidaddehabitaciones().setText(String.valueOf(reservaActual.getCantidadHabitaciones()));
                vista.getTxtconsulta_direccionreserva().setText(
                    reservaActual.getDireccion() != null ? reservaActual.getDireccion() : "");
                vista.getTxtconsulta_telefonoreserva().setText(
                    reservaActual.getTelefono() != null ? reservaActual.getTelefono() : "");
                vista.getTxtconsulta_serviciosadicionales().setText(
                    reservaActual.getServiciosAdicionales() != null ? reservaActual.getServiciosAdicionales() : "");
                
                if (reservaActual.getTipoHabitacion() != null) {
                    vista.getComboxconsulta_tipodehabitacion().setSelectedItem(reservaActual.getTipoHabitacion());
                }
                
                // Mostrar en la tabla
                List<Reserva> lista = new java.util.ArrayList<>();
                lista.add(reservaActual);
                cargarReservasEnTabla(lista);
                
                JOptionPane.showMessageDialog(vista, 
                    "Reserva encontrada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                limpiarCampos();
                limpiarTabla();
                JOptionPane.showMessageDialog(vista, 
                    "No se encontró ninguna reserva con el ID: " + id, 
                    "No encontrado", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "El ID debe ser un número válido", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
            vista.getTxtconsulta_idreserva().requestFocus();
        }
    }
    
    /**
     * Modifica los datos de la reserva seleccionada
     */
    private void modificarReserva() {
        if (reservaActual == null) {
            JOptionPane.showMessageDialog(vista, 
                "Primero debe consultar una reserva para poder modificarla", 
                "No hay reserva seleccionada", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validar campos obligatorios
            if (!validarCamposObligatorios()) {
                return;
            }
            
            // Actualizar datos de la reserva
            reservaActual.setIdHuesped(Integer.parseInt(vista.getTxtconsulta_idhuesped().getText().trim()));
            
            String idAgenciaStr = vista.getTxtconsulta_idagencia().getText().trim();
            reservaActual.setIdAgencia(idAgenciaStr.isEmpty() ? null : Integer.parseInt(idAgenciaStr));
            
            // Parsear y validar fechas
            LocalDate fechaInicio = LocalDate.parse(vista.getTxtconsulta_fechainicio().getText().trim(), dateFormatter);
            LocalDate fechaFin = LocalDate.parse(vista.getTxtconsulta_fechafin().getText().trim(), dateFormatter);
            
            if (!fechaFin.isAfter(fechaInicio)) {
                JOptionPane.showMessageDialog(vista,
                    "La fecha de fin debe ser posterior a la fecha de inicio",
                    "Fechas inválidas",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            reservaActual.setFechaInicio(fechaInicio);
            reservaActual.setFechaFin(fechaFin);
            reservaActual.setCantidadPersonas(Integer.parseInt(vista.getTxtconsulta_cantidadpersonasreserva().getText().trim()));
            reservaActual.setCantidadHabitaciones(Integer.parseInt(vista.getTxtconsulta_cantidaddehabitaciones().getText().trim()));
            reservaActual.setTipoHabitacion((String) vista.getComboxconsulta_tipodehabitacion().getSelectedItem());
            reservaActual.setDireccion(vista.getTxtconsulta_direccionreserva().getText().trim());
            reservaActual.setTelefono(vista.getTxtconsulta_telefonoreserva().getText().trim());
            reservaActual.setServiciosAdicionales(vista.getTxtconsulta_serviciosadicionales().getText().trim());
            
            // Intentar actualizar en la base de datos
            if (reservaDAO.actualizar(reservaActual)) {
                JOptionPane.showMessageDialog(vista, 
                    "Reserva actualizada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarTodasLasReservas();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al actualizar la reserva en la base de datos", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error en formato de números. Verifique los campos numéricos.", 
                "Error de formato", 
                JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista, 
                "Fecha inválida. Use el formato: yyyy-MM-dd (ejemplo: 2024-12-25)", 
                "Error de formato de fecha", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error al modificar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Valida que los campos obligatorios no estén vacíos
     */
    private boolean validarCamposObligatorios() {
        if (vista.getTxtconsulta_idhuesped().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El ID del huésped es obligatorio", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_fechainicio().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de inicio es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_fechafin().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de fin es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_cantidadpersonasreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La cantidad de personas es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_cantidaddehabitaciones().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La cantidad de habitaciones es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_direccionreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La dirección es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (vista.getTxtconsulta_telefonoreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El teléfono es obligatorio", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Elimina la reserva seleccionada después de confirmar
     */
    private void eliminarReserva() {
        if (reservaActual == null) {
            JOptionPane.showMessageDialog(vista, 
                "Primero debe consultar una reserva para poder eliminarla", 
                "No hay reserva seleccionada", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar la reserva?\n\n" +
            "ID Reserva: " + reservaActual.getIdReserva() + "\n" +
            "ID Huésped: " + reservaActual.getIdHuesped() + "\n" +
            "Fechas: " + reservaActual.getFechaInicio() + " al " + reservaActual.getFechaFin() + "\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (reservaDAO.eliminar(reservaActual.getIdReserva())) {
                JOptionPane.showMessageDialog(vista, 
                    "Reserva eliminada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                reservaActual = null;
                cargarTodasLasReservas();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al eliminar la reserva.\n" +
                    "Puede que tenga registros de llegada asociados.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Carga todas las reservas de la base de datos en la tabla
     */
    private void cargarTodasLasReservas() {
        try {
            List<Reserva> reservas = reservaDAO.listarTodas();
            cargarReservasEnTabla(reservas);
            
            if (reservas.isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                    "No hay reservas registradas en el sistema", 
                    "Sin registros", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar reservas: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga una lista de reservas en la tabla
     * @param reservas Lista de reservas a mostrar
     */
    private void cargarReservasEnTabla(List<Reserva> reservas) {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        modelo.addColumn("ID Reserva");
        modelo.addColumn("ID Huésped");
        modelo.addColumn("ID Agencia");
        modelo.addColumn("Fecha Inicio");
        modelo.addColumn("Fecha Fin");
        modelo.addColumn("Personas");
        modelo.addColumn("Habitaciones");
        modelo.addColumn("Tipo");
        
        for (Reserva r : reservas) {
            modelo.addRow(new Object[]{
                r.getIdReserva(),
                r.getIdHuesped(),
                r.getIdAgencia() != null ? r.getIdAgencia() : "N/A",
                r.getFechaInicio(),
                r.getFechaFin(),
                r.getCantidadPersonas(),
                r.getCantidadHabitaciones(),
                r.getTipoHabitacion() != null ? r.getTipoHabitacion() : "N/A"
            });
        }
        
        vista.getTablaconsultareserva().setModel(modelo);
        
        // Ajustar ancho de columnas
        if (vista.getTablaconsultareserva().getColumnModel().getColumnCount() > 0) {
            vista.getTablaconsultareserva().getColumnModel().getColumn(0).setPreferredWidth(80);  // ID Reserva
            vista.getTablaconsultareserva().getColumnModel().getColumn(1).setPreferredWidth(80);  // ID Huésped
            vista.getTablaconsultareserva().getColumnModel().getColumn(2).setPreferredWidth(80);  // ID Agencia
            vista.getTablaconsultareserva().getColumnModel().getColumn(3).setPreferredWidth(100); // Fecha Inicio
            vista.getTablaconsultareserva().getColumnModel().getColumn(4).setPreferredWidth(100); // Fecha Fin
            vista.getTablaconsultareserva().getColumnModel().getColumn(5).setPreferredWidth(70);  // Personas
            vista.getTablaconsultareserva().getColumnModel().getColumn(6).setPreferredWidth(90);  // Habitaciones
            vista.getTablaconsultareserva().getColumnModel().getColumn(7).setPreferredWidth(100); // Tipo
        }
    }
    
    /**
     * Selecciona una reserva de la tabla al hacer doble clic
     */
    private void seleccionarReservaDeTabla() {
        int filaSeleccionada = vista.getTablaconsultareserva().getSelectedRow();
        
        if (filaSeleccionada >= 0) {
            try {
                int idReserva = (int) vista.getTablaconsultareserva().getValueAt(filaSeleccionada, 0);
                reservaActual = reservaDAO.buscarPorId(idReserva);
                
                if (reservaActual != null) {
                    vista.getTxtconsulta_idreserva().setText(String.valueOf(reservaActual.getIdReserva()));
                    vista.getTxtconsulta_idhuesped().setText(String.valueOf(reservaActual.getIdHuesped()));
                    vista.getTxtconsulta_idagencia().setText(
                        reservaActual.getIdAgencia() != null ? String.valueOf(reservaActual.getIdAgencia()) : "");
                    vista.getTxtconsulta_fechainicio().setText(reservaActual.getFechaInicio().toString());
                    vista.getTxtconsulta_fechafin().setText(reservaActual.getFechaFin().toString());
                    vista.getTxtconsulta_cantidadpersonasreserva().setText(String.valueOf(reservaActual.getCantidadPersonas()));
                    vista.getTxtconsulta_cantidaddehabitaciones().setText(String.valueOf(reservaActual.getCantidadHabitaciones()));
                    vista.getTxtconsulta_direccionreserva().setText(
                        reservaActual.getDireccion() != null ? reservaActual.getDireccion() : "");
                    vista.getTxtconsulta_telefonoreserva().setText(
                        reservaActual.getTelefono() != null ? reservaActual.getTelefono() : "");
                    vista.getTxtconsulta_serviciosadicionales().setText(
                        reservaActual.getServiciosAdicionales() != null ? reservaActual.getServiciosAdicionales() : "");
                    
                    if (reservaActual.getTipoHabitacion() != null) {
                        vista.getComboxconsulta_tipodehabitacion().setSelectedItem(reservaActual.getTipoHabitacion());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, 
                    "Error al seleccionar reserva: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Limpia todos los campos de texto
     */
    private void limpiarCampos() {
        vista.getTxtconsulta_idreserva().setText("");
        vista.getTxtconsulta_idhuesped().setText("");
        vista.getTxtconsulta_idagencia().setText("");
        vista.getTxtconsulta_fechainicio().setText("");
        vista.getTxtconsulta_fechafin().setText("");
        vista.getTxtconsulta_cantidadpersonasreserva().setText("");
        vista.getTxtconsulta_cantidaddehabitaciones().setText("");
        vista.getTxtconsulta_direccionreserva().setText("");
        vista.getTxtconsulta_telefonoreserva().setText("");
        vista.getTxtconsulta_serviciosadicionales().setText("");
        vista.getComboxconsulta_tipodehabitacion().setSelectedIndex(0);
        reservaActual = null;
    }
    
    /**
     * Limpia la tabla
     */
    private void limpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaconsultareserva().getModel();
        modelo.setRowCount(0);
    }
    
    /**
     * Vuelve a la vista anterior (menú de consultas)
     */
    private void volver() {
        limpiarCampos();
        limpiarTabla();
        reservaActual = null;
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}