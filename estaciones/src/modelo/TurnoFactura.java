/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */

public class TurnoFactura {
    private String fechaHora;
    private String idEstacion;
    private int tiempoConsumidoMin;
    private double montoCobrado;
    private TipoTransaccion tipo;

    public TurnoFactura(String fechaHora, String idEstacion, int tiempoConsumidoMin,
                         double montoCobrado, TipoTransaccion tipo) {
        this.fechaHora = fechaHora;
        this.idEstacion = idEstacion;
        this.tiempoConsumidoMin = tiempoConsumidoMin;
        this.montoCobrado = montoCobrado;
        this.tipo = tipo;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public String getIdEstacion() {
        return idEstacion;
    }
}
