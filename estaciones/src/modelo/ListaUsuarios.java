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

public class ListaUsuarios {
    private List<Usuario> usuarios = new ArrayList<>();

    public void agregar(Usuario u) {
        usuarios.add(u);
    }

    public Usuario buscarPorCedula(String cedula) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public boolean existe(String cedula) {
        return buscarPorCedula(cedula) != null;
    }
}
