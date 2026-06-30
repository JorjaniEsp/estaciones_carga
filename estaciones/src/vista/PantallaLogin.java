/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// vista/PantallaLogin.java
package vista;

import modelo.*;
import controlador.ControladorAutenticacion;

import javax.swing.*;
import java.awt.*;

public class PantallaLogin extends JPanel {

    private VentanaPrincipal app;
    private ListaUsuarios listaUsuarios;
    private ListaProveedores listaProveedores;
    private ControladorAutenticacion controlador;
    private Sesion sesion;

    private JRadioButton opcionUsuario;
    private JRadioButton opcionProveedor;

    private JTextField campoCedula;
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JTextField campoTarjeta;
    private JTextField campoVencimiento;
    private JTextField campoCvv;

    public PantallaLogin(VentanaPrincipal app, ListaUsuarios listaUsuarios, ListaProveedores listaProveedores,
                          ControladorAutenticacion controlador, Sesion sesion) {
        this.app = app;
        this.listaUsuarios = listaUsuarios;
        this.listaProveedores = listaProveedores;
        this.controlador = controlador;
        this.sesion = sesion;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(construirPanelRol(), BorderLayout.NORTH);
        add(construirPanelFormulario(), BorderLayout.CENTER);
        add(construirPanelBotones(), BorderLayout.SOUTH);

        actualizarCamposSegunRol();
    }

    private JPanel construirPanelRol() {
        opcionUsuario = new JRadioButton("Soy conductor", true);
        opcionProveedor = new JRadioButton("Soy proveedor / empresa");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(opcionUsuario);
        grupo.add(opcionProveedor);

        opcionUsuario.addActionListener(e -> actualizarCamposSegunRol());
        opcionProveedor.addActionListener(e -> actualizarCamposSegunRol());

        JPanel panel = new JPanel();
        panel.add(opcionUsuario);
        panel.add(opcionProveedor);
        return panel;
    }

    private JPanel construirPanelFormulario() {
        JPanel formulario = new JPanel(new GridLayout(6, 2, 8, 8));

        formulario.add(new JLabel("Cédula:"));
        campoCedula = new JTextField();
        formulario.add(campoCedula);

        formulario.add(new JLabel("Nombre / Empresa (solo registro):"));
        campoNombre = new JTextField();
        formulario.add(campoNombre);

        formulario.add(new JLabel("Correo (solo registro):"));
        campoCorreo = new JTextField();
        formulario.add(campoCorreo);

        formulario.add(new JLabel("Número de tarjeta (solo conductor):"));
        campoTarjeta = new JTextField();
        formulario.add(campoTarjeta);

        formulario.add(new JLabel("Vencimiento (solo conductor):"));
        campoVencimiento = new JTextField();
        formulario.add(campoVencimiento);

        formulario.add(new JLabel("CVV (solo conductor):"));
        campoCvv = new JTextField();
        formulario.add(campoCvv);

        // ancla el formulario arriba en vez de estirarlo a toda la ventana
        JPanel envoltorio = new JPanel(new BorderLayout());
        envoltorio.add(formulario, BorderLayout.NORTH);
        return envoltorio;
    }

    private JPanel construirPanelBotones() {
        JButton botonIngresar = new JButton("Ingresar");
        botonIngresar.addActionListener(e -> ingresar());

        JButton botonRegistrar = new JButton("Registrarse");
        botonRegistrar.addActionListener(e -> registrar());

        JPanel panel = new JPanel();
        panel.add(botonIngresar);
        panel.add(botonRegistrar);
        return panel;
    }

    private void actualizarCamposSegunRol() {
        boolean esConductor = opcionUsuario.isSelected();
        campoTarjeta.setEnabled(esConductor);
        campoVencimiento.setEnabled(esConductor);
        campoCvv.setEnabled(esConductor);
    }

    private boolean cedulaValida() {
        return campoCedula.getText() != null && !campoCedula.getText().trim().isEmpty();
    }

    private void ingresar() {
        if (!cedulaValida()) {
            JOptionPane.showMessageDialog(this, "Ingresá una cédula");
            return;
        }

        String cedula = campoCedula.getText().trim();

        if (opcionProveedor.isSelected()) {
            Proveedor proveedor = listaProveedores.buscarPorCedulaJuridica(cedula);
            if (proveedor == null) {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
                return;
            }
            sesion.iniciarSesionProveedor(proveedor);
            app.navegarA("estacionesProveedor");
            return;
        }

        Usuario usuario = controlador.login(listaUsuarios, cedula);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado");
            return;
        }

        sesion.iniciarSesionUsuario(usuario);
        app.navegarA("garaje");
    }

    private void registrar() {
        if (!cedulaValida() || campoNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cédula y nombre son obligatorios");
            return;
        }

        String cedula = campoCedula.getText().trim();
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();

        if (opcionProveedor.isSelected()) {
            Proveedor proveedor = controlador.registrarProveedor(listaProveedores, cedula, nombre, correo);
            sesion.iniciarSesionProveedor(proveedor);
            app.navegarA("estacionesProveedor");
            return;
        }

        Usuario usuario = controlador.registrarUsuario(listaUsuarios, cedula, nombre, correo);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Esa cédula ya está registrada");
            return;
        }

        usuario.setDatosPago(campoTarjeta.getText(), campoVencimiento.getText(), campoCvv.getText());
        sesion.iniciarSesionUsuario(usuario);
        app.navegarA("garaje");
    }
}