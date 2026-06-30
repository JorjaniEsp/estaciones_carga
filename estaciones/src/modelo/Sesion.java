/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */

public class Sesion {
    private Usuario usuarioActivo;
    private Proveedor proveedorActivo;

    public void iniciarSesionUsuario(Usuario u) {
        usuarioActivo = u;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public void iniciarSesionProveedor(Proveedor p) {
        proveedorActivo = p;
    }

    public Proveedor getProveedorActivo() {
        return proveedorActivo;
    }
}
