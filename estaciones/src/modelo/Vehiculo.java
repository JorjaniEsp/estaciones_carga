/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */
// modelo/Vehiculo.java
public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private String numeroSerie;
    private TipoConector tipoConector;

    public Vehiculo(String placa, String marca, String modelo, int anio,
                     String numeroSerie, TipoConector tipoConector) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.numeroSerie = numeroSerie;
        this.tipoConector = tipoConector;
    }

    public String getPlaca() {
        return placa;
    }

    public TipoConector getTipoConector() {
        return tipoConector;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }
}
