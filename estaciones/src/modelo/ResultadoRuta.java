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

public class ResultadoRuta {
    private List<Estacion> camino = new ArrayList<>();
    private double costoTotal;
    private Criterio criterioUsado;

    public ResultadoRuta(List<Estacion> camino, double costoTotal, Criterio criterioUsado) {
        this.camino = camino;
        this.costoTotal = costoTotal;
        this.criterioUsado = criterioUsado;
    }

    public Estacion estacionGanadora() {
        if (camino.isEmpty()) return null;
        return camino.get(camino.size() - 1);
    }

    public List<Estacion> getCamino() {
        return camino;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public Criterio getCriterioUsado() {
        return criterioUsado;
    }
}
