/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josse
 */

public class GrafoEstaciones {
    private List<Estacion> estaciones = new ArrayList<>();

    public void agregar(Estacion e) {
        estaciones.add(e);
    }

    public Estacion buscarPorId(String id) {
        for (Estacion e : estaciones) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    // rutas bidireccionales: agrega la arista en ambos sentidos
    public void conectar(Estacion a, Estacion b, double km, double min, double precio) {
        a.agregarRuta(new Arista(b, km, min, precio));
        b.agregarRuta(new Arista(a, km, min, precio));
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }
}