/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author josse
 */
public class Usuario {

    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String fechaNacimiento;
    private String direccion;
    private String numeroTarjeta;
    private String vencimientoTarjeta;
    private String cvv;
    private Vehiculo vehiculoSeleccionado;
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private int vehiculoActual = 0;
    private List<TurnoFactura> historial = new ArrayList<>();

    public Usuario(String cedula, String nombreCompleto, String correo) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
    }

    public void seleccionarVehiculo(Vehiculo v) {
        vehiculoSeleccionado = v;
    }

    public Vehiculo vehiculoActual() {
        return vehiculoSeleccionado;
    }

    public void agregarVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }

    public boolean tieneVehiculo() {
        return !vehiculos.isEmpty();
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    // navegación circular: al llegar al final vuelve al inicio
    public Vehiculo siguienteVehiculo() {
        if (vehiculos.isEmpty()) {
            return null;
        }
        vehiculoActual = (vehiculoActual + 1) % vehiculos.size();
        return vehiculoActual();
    }

    public Vehiculo anteriorVehiculo() {
        if (vehiculos.isEmpty()) {
            return null;
        }
        vehiculoActual = (vehiculoActual - 1 + vehiculos.size()) % vehiculos.size();
        return vehiculoActual();
    }

    public void agregarFactura(TurnoFactura factura) {
        historial.add(factura);
    }

    // la más reciente primero
    public List<TurnoFactura> historialOrdenado() {
        List<TurnoFactura> copia = new ArrayList<>(historial);
        Collections.reverse(copia);
        return copia;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setDatosPago(String numeroTarjeta, String vencimiento, String cvv) {
        this.numeroTarjeta = numeroTarjeta;
        this.vencimientoTarjeta = vencimiento;
        this.cvv = cvv;
    }
}