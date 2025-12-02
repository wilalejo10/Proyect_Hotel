package Controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador principal que maneja la navegación entre vistas
 */
public class ControladorPrincipal implements ActionListener {
    private VistaPrincipal vistaPrincipal;
    private VistaConsulta vistaConsulta;
    private VistaRegistrar vistaRegistrar;
    
    public ControladorPrincipal(VistaPrincipal vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        inicializarEventos();
    }
    
    private void inicializarEventos() {
        vistaPrincipal.getBontonconsultar().addActionListener(this);
        vistaPrincipal.getBontonregitrar().addActionListener(this);
        vistaPrincipal.getBotonsalida().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.getBontonconsultar()) {
            abrirVistaConsulta();
        } else if (e.getSource() == vistaPrincipal.getBontonregitrar()) {
            abrirVistaRegistrar();
        } else if (e.getSource() == vistaPrincipal.getBotonsalida()) {
            System.exit(0);
        }
    }
    
    private void abrirVistaConsulta() {
        if (vistaConsulta == null) {
            vistaConsulta = new VistaConsulta();
            new ControladorConsulta(vistaConsulta, vistaPrincipal);
        }
        vistaPrincipal.setVisible(false);
        vistaConsulta.setVisible(true);
    }
    
    private void abrirVistaRegistrar() {
        if (vistaRegistrar == null) {
            vistaRegistrar = new VistaRegistrar();
            new ControladorRegistrar(vistaRegistrar, vistaPrincipal);
        }
        vistaPrincipal.setVisible(false);
        vistaRegistrar.setVisible(true);
    }
}