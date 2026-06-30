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

public class ControladorRutas {
    private Dijkstra dijkstra = new Dijkstra();

    // recorre las estaciones candidatas y se queda con la de menor costo
    public ResultadoRuta buscarMejorEstacion(Estaciones estaciones, Estacion origen, Criterio criterio, TipoConector conector) {
        GrafoEstaciones grafo = estaciones.getGrafo();
        ResultadoRuta mejor = null;

        for (Estacion candidata : grafo.getEstaciones()) {
            if (candidata.getId().equals(origen.getId())) {
                continue;
            }
            if (candidata.getEstado() != EstadoEstacion.ACTIVA) {
                continue;
            }
            if (!candidata.soportaConector(conector)) {
                continue;
            }

            ResultadoRuta resultado = dijkstra.calcular(grafo, origen, candidata, criterio, conector);

            if (mejor == null || resultado.getCostoTotal() < mejor.getCostoTotal()) {
                mejor = resultado;
            }
        }
        return mejor;
    }
}
