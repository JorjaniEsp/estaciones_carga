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

public class Dijkstra {

    public ResultadoRuta calcular(GrafoEstaciones grafo, Estacion origen, Estacion destino,
                                   Criterio criterio, TipoConector conectorRequerido) {

        List<Estacion> pendientes = new ArrayList<>();
        Map<String, Double> distancias = new HashMap<>();
        Map<String, Estacion> previos = new HashMap<>();

        for (Estacion e : grafo.getEstaciones()) {
            distancias.put(e.getId(), Double.MAX_VALUE);
            pendientes.add(e);
        }
        distancias.put(origen.getId(), 0.0);

        while (!pendientes.isEmpty()) {
            Estacion actual = sacarMasCercana(pendientes, distancias);

            for (Arista arista : actual.getRutas()) {
                Estacion vecino = arista.getDestino();

                if (!vecino.soportaConector(conectorRequerido)) continue;
                if (vecino.getEstado() == EstadoEstacion.EN_MANTENIMIENTO) continue;

                double nueva = distancias.get(actual.getId()) + arista.costoSegun(criterio);
                if (nueva < distancias.get(vecino.getId())) {
                    distancias.put(vecino.getId(), nueva);
                    previos.put(vecino.getId(), actual);
                }
            }
        }

        List<Estacion> camino = reconstruirCamino(previos, origen, destino);
        double costoTotal = distancias.get(destino.getId());

        return new ResultadoRuta(camino, costoTotal, criterio);
    }

    private Estacion sacarMasCercana(List<Estacion> pendientes, Map<String, Double> distancias) {
        Estacion masCercana = pendientes.get(0);
        for (Estacion e : pendientes) {
            if (distancias.get(e.getId()) < distancias.get(masCercana.getId())) {
                masCercana = e;
            }
        }
        pendientes.remove(masCercana);
        return masCercana;
    }

    private List<Estacion> reconstruirCamino(Map<String, Estacion> previos, Estacion origen, Estacion destino) {
        List<Estacion> camino = new ArrayList<>();
        Estacion actual = destino;

        while (actual != null && !actual.getId().equals(origen.getId())) {
            camino.add(0, actual);
            actual = previos.get(actual.getId());
        }

        if (actual != null) {
            camino.add(0, origen);
        }

        return camino;
    }
}
