package Controlador;

import DAO.*;
import Modelo.*;
import Vista.VistaRegistrar;
import Vista.VistaRegistroReserva;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controlador para el registro de reservas
 * Implementa las reglas de negocio del sistema de hotel
 */
public class ControladorRegistroReserva implements ActionListener {
    private VistaRegistroReserva vista;
    private VistaRegistrar vistaAnterior;
    private ReservaDAO reservaDAO;
    private HuespedDAO huespedDAO;
    private AgenciaDAO agenciaDAO;
    private DateTimeFormatter dateFormatter;
    
    public ControladorRegistroReserva(VistaRegistroReserva vista, VistaRegistrar vistaAnterior) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.reservaDAO = new ReservaDAO();
        this.huespedDAO = new HuespedDAO();
        this.agenciaDAO = new AgenciaDAO();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        inicializarEventos();
        cargarTiposHabitacion();
    }
    
    private void inicializarEventos() {
        vista.getBotonguardarreserva().addActionListener(this);
        vista.getBotonlimpiarreserva().addActionListener(this);
        vista.getBotonvolver_resgistroreserva().addActionListener(this);
    }
    
    private void cargarTiposHabitacion() {
        vista.getComboboxtipohabitacion_registroreserva().removeAllItems();
        vista.getComboboxtipohabitacion_registroreserva().addItem("Individual");
        vista.getComboboxtipohabitacion_registroreserva().addItem("Doble");
        vista.getComboboxtipohabitacion_registroreserva().addItem("Suite");
        vista.getComboboxtipohabitacion_registroreserva().addItem("Familiar");
        vista.getComboboxtipohabitacion_registroreserva().addItem("Presidencial");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBotonguardarreserva()) {
            guardarReserva();
        } else if (e.getSource() == vista.getBotonlimpiarreserva()) {
            limpiarCampos();
        } else if (e.getSource() == vista.getBotonvolver_resgistroreserva()) {
            volver();
        }
    }
    
    /**
     * Guarda una nueva reserva en el sistema
     * Implementa las reglas de negocio #4, #5, #6, #7 y #8
     */
    private void guardarReserva() {
        try {
            // Validar campos obligatorios
            if (!validarCampos()) {
                return;
            }
            
            // Verificar que el huésped existe (Regla #4)
            int idHuesped = Integer.parseInt(vista.getTxtidhuesped_resgistroreserva().getText().trim());
            Huesped huesped = huespedDAO.buscarPorId(idHuesped);
            
            if (huesped == null) {
                JOptionPane.showMessageDialog(vista,
                    "El huésped con ID " + idHuesped + " no existe.\n" +
                    "Por favor, regístrelo primero desde el menú de registro.",
                    "Huésped no encontrado",
                    JOptionPane.WARNING_MESSAGE);
                vista.getTxtidhuesped_resgistroreserva().requestFocus();
                return;
            }
            
            // Crear objeto Reserva
            Reserva reserva = new Reserva();
            reserva.setIdReserva(Integer.parseInt(vista.getTxtidreserva().getText().trim()));
            reserva.setIdHuesped(idHuesped);
            
            // Verificar agencia si se proporcionó ID (Regla #5)
            String idAgenciaStr = vista.getTxtidagencia_registroreserva().getText().trim();
            if (!idAgenciaStr.isEmpty()) {
                int idAgencia = Integer.parseInt(idAgenciaStr);
                Agencia agencia = agenciaDAO.buscarPorId(idAgencia);
                
                if (agencia == null) {
                    int opcion = JOptionPane.showConfirmDialog(vista,
                        "La agencia con ID " + idAgencia + " no existe.\n" +
                        "¿Desea continuar sin agencia?",
                        "Agencia no encontrada",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (opcion == JOptionPane.NO_OPTION) {
                        vista.getTxtidagencia_registroreserva().requestFocus();
                        return;
                    }
                    reserva.setIdAgencia(null);
                } else {
                    reserva.setIdAgencia(idAgencia);
                }
            } else {
                reserva.setIdAgencia(null);
            }
            
            // Parsear y validar fechas (Regla #6)
            LocalDate fechaInicio = LocalDate.parse(
                vista.getTxtfechainicio_registroreserva().getText().trim(), 
                dateFormatter);
            LocalDate fechaFin = LocalDate.parse(
                vista.getTxtfechafin_registroreserva().getText().trim(), 
                dateFormatter);
            
            // Validar que fecha fin sea posterior a fecha inicio
            if (!fechaFin.isAfter(fechaInicio)) {
                JOptionPane.showMessageDialog(vista,
                    "La fecha de fin debe ser posterior a la fecha de inicio",
                    "Fechas inválidas",
                    JOptionPane.WARNING_MESSAGE);
                vista.getTxtfechainicio_registroreserva().requestFocus();
                return;
            }
            
            // Validar que las fechas no sean en el pasado
            if (fechaInicio.isBefore(LocalDate.now())) {
                int opcion = JOptionPane.showConfirmDialog(vista,
                    "La fecha de inicio es anterior a la fecha actual.\n" +
                    "¿Está seguro de continuar?",
                    "Advertencia de fecha",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (opcion == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            
            reserva.setFechaInicio(fechaInicio);
            reserva.setFechaFin(fechaFin);
            
            // Validar cantidad de personas y habitaciones (Regla #6)
            int cantidadPersonas = Integer.parseInt(vista.getTxtcatidadpersona().getText().trim());
            int cantidadHabitaciones = Integer.parseInt(vista.getTxtcantidadhabitaciones().getText().trim());
            
            if (cantidadPersonas <= 0) {
                JOptionPane.showMessageDialog(vista,
                    "La cantidad de personas debe ser mayor a 0",
                    "Cantidad inválida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (cantidadHabitaciones <= 0) {
                JOptionPane.showMessageDialog(vista,
                    "La cantidad de habitaciones debe ser mayor a 0",
                    "Cantidad inválida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            reserva.setCantidadPersonas(cantidadPersonas);
            reserva.setCantidadHabitaciones(cantidadHabitaciones);
            reserva.setTipoHabitacion((String) vista.getComboboxtipohabitacion_registroreserva().getSelectedItem());
            reserva.setDireccion(vista.getTxtdireccion_registroreserva().getText().trim());
            reserva.setTelefono(vista.getTxttelefono_registroreserva().getText().trim());
            reserva.setServiciosAdicionales(vista.getTxtserviciosadicionales().getText().trim());
            reserva.setIdRegistroLlegada(null);
            
            // Intentar guardar en la base de datos
            if (reservaDAO.insertar(reserva)) {
                // Mensaje de éxito con reglas de negocio (Reglas #7 y #8)
                String mensaje = "✓ Reserva registrada exitosamente\n\n" +
                    "═══════════════════════════════════════\n" +
                    "INFORMACIÓN IMPORTANTE:\n" +
                    "═══════════════════════════════════════\n\n" +
                    "PAGOS (Regla #7):\n" +
                    "• Debe pagar el 20% en las próximas 24 horas\n" +
                    "• Si no se paga, la reserva será CANCELADA\n" +
                    "• El 80% restante se paga al llegar al hotel\n\n" +
                    "REGISTRO DE LLEGADA (Regla #8):\n" +
                    "• Horario: 3:00 PM - 7:00 PM\n" +
                    "• Después de las 7:00 PM se cancela\n" +
                    "• Sin devolución de dinero\n\n" +
                    "═══════════════════════════════════════\n" +
                    "ID Reserva: " + reserva.getIdReserva() + "\n" +
                    "Fechas: " + fechaInicio + " al " + fechaFin + "\n" +
                    "═══════════════════════════════════════";
                
                JOptionPane.showMessageDialog(vista,
                    mensaje,
                    "Reserva Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
                
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vista,
                    "Error al registrar la reserva en la base de datos.\n" +
                    "Por favor, verifique los datos e intente nuevamente.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                "Error en formato de números.\n" +
                "Verifique que los campos numéricos contengan solo números.",
                "Error de formato",
                JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(vista,
                "Fecha inválida.\n" +
                "Use el formato: yyyy-MM-dd\n" +
                "Ejemplo: 2024-12-25",
                "Error de formato de fecha",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                "Error inesperado: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /**
     * Valida que todos los campos obligatorios estén llenos
     */
    private boolean validarCampos() {
        if (vista.getTxtidreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El ID de la reserva es obligatorio", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtidreserva().requestFocus();
            return false;
        }
        
        if (vista.getTxtidhuesped_resgistroreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El ID del huésped es obligatorio", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtidhuesped_resgistroreserva().requestFocus();
            return false;
        }
        
        if (vista.getTxtfechainicio_registroreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de inicio es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtfechainicio_registroreserva().requestFocus();
            return false;
        }
        
        if (vista.getTxtfechafin_registroreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de fin es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtfechafin_registroreserva().requestFocus();
            return false;
        }
        
        if (vista.getTxtcatidadpersona().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La cantidad de personas es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtcatidadpersona().requestFocus();
            return false;
        }
        
        if (vista.getTxtcantidadhabitaciones().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La cantidad de habitaciones es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtcantidadhabitaciones().requestFocus();
            return false;
        }
        
        if (vista.getTxtdireccion_registroreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "La dirección es obligatoria", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxtdireccion_registroreserva().requestFocus();
            return false;
        }
        
        if (vista.getTxttelefono_registroreserva().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, 
                "El teléfono es obligatorio", 
                "Campo vacío", 
                JOptionPane.WARNING_MESSAGE);
            vista.getTxttelefono_registroreserva().requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Limpia todos los campos del formulario
     */
    private void limpiarCampos() {
        vista.getTxtidreserva().setText("");
        vista.getTxtidhuesped_resgistroreserva().setText("");
        vista.getTxtidagencia_registroreserva().setText("");
        vista.getTxtfechainicio_registroreserva().setText("");
        vista.getTxtfechafin_registroreserva().setText("");
        vista.getTxtcatidadpersona().setText("");
        vista.getTxtcantidadhabitaciones().setText("");
        vista.getTxtdireccion_registroreserva().setText("");
        vista.getTxttelefono_registroreserva().setText("");
        vista.getTxtserviciosadicionales().setText("");
        vista.getComboboxtipohabitacion_registroreserva().setSelectedIndex(0);
        vista.getTxtidreserva().requestFocus();
    }
    
    /**
     * Vuelve a la vista de menú de registro
     */
    private void volver() {
        vista.setVisible(false);
        vistaAnterior.setVisible(true);
    }
}