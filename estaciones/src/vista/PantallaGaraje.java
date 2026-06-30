/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import modelo.*;
import controlador.ControladorVehiculos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PantallaGaraje extends JPanel {

    private VentanaPrincipal app;
    private ControladorVehiculos controlador;
    private Sesion sesion;

    private DefaultTableModel modeloTabla;
    private JTable tabla;
    private JButton botonAgregar;
    private JButton botonBuscar;

    public PantallaGaraje(VentanaPrincipal app, ControladorVehiculos controlador, Sesion sesion) {
        this.app = app;
        this.controlador = controlador;
        this.sesion = sesion;

        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{"Placa", "Marca", "Modelo", "Conector"}, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        botonAgregar = new JButton("+ Agregar vehículo");
        botonAgregar.addActionListener(e -> abrirFormularioVehiculo());
        panelBotones.add(botonAgregar);

        botonBuscar = new JButton("Buscar Carga para este Vehículo");
        botonBuscar.addActionListener(e -> buscarCarga());
        panelBotones.add(botonBuscar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    public void actualizar() {
        Usuario usuario = sesion.getUsuarioActivo();
        modeloTabla.setRowCount(0);

        for (Vehiculo v : usuario.getVehiculos()) {
            modeloTabla.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo(), v.getTipoConector()});
        }

        botonBuscar.setEnabled(usuario.tieneVehiculo());
    }

    private void abrirFormularioVehiculo() {
        JTextField campoPlaca = new JTextField();
        JTextField campoMarca = new JTextField();
        JTextField campoModelo = new JTextField();
        JComboBox<TipoConector> campoConector = new JComboBox<>(TipoConector.values());

        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));
        formulario.add(new JLabel("Placa:"));
        formulario.add(campoPlaca);
        formulario.add(new JLabel("Marca:"));
        formulario.add(campoMarca);
        formulario.add(new JLabel("Modelo:"));
        formulario.add(campoModelo);
        formulario.add(new JLabel("Conector:"));
        formulario.add(campoConector);

        int opcion = JOptionPane.showConfirmDialog(this, formulario, "Agregar vehículo", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            Vehiculo nuevo = new Vehiculo(campoPlaca.getText(), campoMarca.getText(), campoModelo.getText(),
                                           0, "", (TipoConector) campoConector.getSelectedItem());
            controlador.agregarVehiculo(sesion.getUsuarioActivo(), nuevo);
            actualizar();
        }
    }

    private void buscarCarga() {
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un vehículo primero");
            return;
        }

        Usuario usuario = sesion.getUsuarioActivo();
        Vehiculo seleccionado = usuario.getVehiculos().get(filaSeleccionada);
        controlador.seleccionar(usuario, seleccionado);

        app.navegarA("busqueda");
    }
}