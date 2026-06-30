/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */

public class Arista {
    private Estacion destino;
    private double distanciaKm;
    private double tiempoMin;
    private double precioColones;

    public Arista(Estacion destino, double distanciaKm, double tiempoMin, double precioColones) {
        this.destino = destino;
        this.distanciaKm = distanciaKm;
        this.tiempoMin = tiempoMin;
        this.precioColones = precioColones;
    }

    public double costoSegun(Criterio criterio) {
        if (criterio == Criterio.DISTANCIA) return distanciaKm;
        if (criterio == Criterio.TIEMPO) return tiempoMin;
        return precioColones;
    }

    public Estacion getDestino() {
        return destino;
    }
}
