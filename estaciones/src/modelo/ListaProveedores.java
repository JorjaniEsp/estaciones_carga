/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.*;

/**
 *
 * @author josse
 */

public class ListaProveedores {
    private List<Proveedor> proveedores = new ArrayList<>();

    public void agregar(Proveedor p) {
        proveedores.add(p);
    }

    public Proveedor buscarPorCedulaJuridica(String cedulaJuridica) {
        for (Proveedor p : proveedores) {
            if (p.getCedulaJuridica().equals(cedulaJuridica)) return p;
        }
        return null;
    }
}
