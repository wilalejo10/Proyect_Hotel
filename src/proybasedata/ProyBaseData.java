package proybasedata;

import Controlador.ControladorPrincipal;
import Vista.VistaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

/**
 * Clase principal del Sistema de Gestión de Hotel
 * @author jnosp
 */
public class ProyBaseData {

    /**
     * Método principal que inicia la aplicación
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Mostrar mensaje de inicio en consola
                mostrarBannerInicio();
                
                // Crear la vista principal
                VistaPrincipal vistaPrincipal = new VistaPrincipal();
                
                // Crear el controlador principal
                ControladorPrincipal controladorPrincipal = new ControladorPrincipal(vistaPrincipal);
                
                // Configurar la ventana
                vistaPrincipal.setLocationRelativeTo(null); // Centrar en pantalla
                vistaPrincipal.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                vistaPrincipal.setVisible(true);
                
                System.out.println("✓ Aplicación iniciada correctamente");
                System.out.println("✓ Ventana principal visible\n");
                
            } catch (Exception e) {
                System.err.println("ERROR CRÍTICO al iniciar la aplicación:");
                System.err.println(e.getMessage());
                e.printStackTrace();
                
                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar la aplicación:\n" + e.getMessage() +
                    "\n\nPor favor, verifique:\n" +
                    "1. Que la base de datos esté corriendo\n" +
                    "2. Que las credenciales sean correctas\n" +
                    "3. Que todas las clases estén compiladas",
                    "Error de Inicio",
                    JOptionPane.ERROR_MESSAGE);
                
                System.exit(1);
            }
        });
    }
    
    /**
     * Muestra un banner de bienvenida en la consola
     */
    private static void mostrarBannerInicio() {
        System.out.println("\n╔═══════════════════════════════════════════════════╗");
        System.out.println("║                                                   ║");
        System.out.println("║      SISTEMA DE GESTIÓN DE HOTEL                  ║");
        System.out.println("║                                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("┌───────────────────────────────────────────────────┐");
        System.out.println("│ Versión: 1.0                                      │");
        System.out.println("│ Base de datos: MySQL - hotel                      │");
        System.out.println("│ Autor: jnosp                                      │");
        System.out.println("└───────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("Iniciando aplicación...");
        System.out.println();
    }
}