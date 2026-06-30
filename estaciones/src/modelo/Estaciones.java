/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */

public class Estaciones {
    private GrafoEstaciones grafo = new GrafoEstaciones();

    public void agregar(Estacion e) {
        grafo.agregar(e);
    }

    public Estacion buscarPorId(String id) {
        return grafo.buscarPorId(id);
    }

    public GrafoEstaciones getGrafo() {
        return grafo;
    }
}