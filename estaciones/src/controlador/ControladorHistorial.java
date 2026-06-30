/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import modelo.*;

/**
 *
 * @author josse
 */

public class ControladorHistorial {

    public List<TurnoFactura> verHistorial(Usuario usuario) {
        return usuario.historialOrdenado();
    }
}
