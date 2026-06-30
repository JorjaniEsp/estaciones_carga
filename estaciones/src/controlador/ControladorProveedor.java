/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.*;

/**
 *
 * @author josse
 */
public class ControladorProveedor {

    public void registrarEstacion(Proveedor proveedor, Estaciones estaciones, Estacion estacion) {
        proveedor.agregarEstacion(estacion);
        estaciones.agregar(estacion);
    }

    public void cambiarEstado(Estacion estacion, EstadoEstacion nuevoEstado) {
        estacion.setEstado(nuevoEstado);
    }

    public double calcularIngresos(ListaUsuarios usuarios, String idEstacion, String dia) {
        double total = 0;
        for (Usuario u : usuarios.getUsuarios()) {
            for (TurnoFactura factura : u.historialOrdenado()) {
                if (factura.getIdEstacion().equals(idEstacion) && factura.getFechaHora().startsWith(dia)) {
                    total = total + factura.getMontoCobrado();
                }
            }
        }
        return total;
    }

    public int contarClientes(ListaUsuarios usuarios, String idEstacion, String dia) {
        int total = 0;
        for (Usuario u : usuarios.getUsuarios()) {
            for (TurnoFactura factura : u.historialOrdenado()) {
                if (factura.getIdEstacion().equals(idEstacion) && factura.getFechaHora().startsWith(dia)) {
                    total = total + 1;
                }
            }
        }
        return total;
    }
}
