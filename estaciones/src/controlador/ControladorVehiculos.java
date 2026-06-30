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
public class ControladorVehiculos {

    public void agregarVehiculo(Usuario usuario, Vehiculo vehiculo) {
        usuario.agregarVehiculo(vehiculo);
    }

    public void seleccionar(Usuario usuario, Vehiculo vehiculo) {
        usuario.seleccionarVehiculo(vehiculo);
    }
}