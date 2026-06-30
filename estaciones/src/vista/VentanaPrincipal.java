/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// vista/VentanaPrincipal.java
package vista;

import modelo.*;
import controlador.*;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal {

    private JFrame frame;
    private CardLayout layout;
    private JPanel panelCentral;
    private JLabel etiquetaPantalla;

    private Sesion sesion = new Sesion();
    private ListaUsuarios listaUsuarios = new ListaUsuarios();
    private ListaProveedores listaProveedores = new ListaProveedores();
    private Estaciones estaciones = new Estaciones();

    private ControladorAutenticacion controladorAutenticacion = new ControladorAutenticacion();
    private ControladorVehiculos controladorVehiculos = new ControladorVehiculos();
    private ControladorRutas controladorRutas = new ControladorRutas();
    private ControladorAgendamiento controladorAgendamiento = new ControladorAgendamiento();
    private ControladorHistorial controladorHistorial = new ControladorHistorial();

    private PantallaLogin pantallaLogin;
    private PantallaGaraje pantallaGaraje;

    public VentanaPrincipal() {
        frame = new JFrame("ChargeCR");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        frame.setLocationRelativeTo(null);

        etiquetaPantalla = new JLabel("Iniciar Sesión");
        etiquetaPantalla.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaPantalla.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        layout = new CardLayout();
        panelCentral = new JPanel(layout);

        pantallaLogin = new PantallaLogin(this, listaUsuarios, listaProveedores, controladorAutenticacion, sesion);
        pantallaGaraje = new PantallaGaraje(this, controladorVehiculos, sesion);

        panelCentral.add(pantallaLogin, "login");
        panelCentral.add(pantallaGaraje, "garaje");

        frame.add(etiquetaPantalla, BorderLayout.NORTH);
        frame.add(panelCentral, BorderLayout.CENTER);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void navegarA(String nombrePantalla) {
        if (nombrePantalla.equals("login")) {
            etiquetaPantalla.setText("Iniciar Sesión");
        }
        if (nombrePantalla.equals("garaje")) {
            pantallaGaraje.actualizar();
            etiquetaPantalla.setText("Mi Garaje");
        }
        layout.show(panelCentral, nombrePantalla);
    }
}
