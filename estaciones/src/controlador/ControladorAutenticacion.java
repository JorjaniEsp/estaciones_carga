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

// controlador/ControladorAutenticacion.java
public class ControladorAutenticacion {

    public Usuario registrarUsuario(ListaUsuarios usuarios, String cedula, String nombre, String correo) {
        if (usuarios.existe(cedula)) return null;
        Usuario nuevo = new Usuario(cedula, nombre, correo);
        usuarios.agregar(nuevo);
        return nuevo;
    }

    public Usuario login(ListaUsuarios usuarios, String cedula) {
        return usuarios.buscarPorCedula(cedula);
    }

    public Proveedor registrarProveedor(ListaProveedores proveedores, String cedulaJuridica, String nombreEmpresa, String correo) {
        Proveedor nuevo = new Proveedor(cedulaJuridica, nombreEmpresa, correo);
        proveedores.agregar(nuevo);
        return nuevo;
    }
}
