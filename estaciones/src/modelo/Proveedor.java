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

public class Proveedor {
    private String cedulaJuridica;
    private String nombreEmpresa;
    private String correo;
    private List<Estacion> estaciones = new ArrayList<>();

    public Proveedor(String cedulaJuridica, String nombreEmpresa, String correo) {
        this.cedulaJuridica = cedulaJuridica;
        this.nombreEmpresa = nombreEmpresa;
        this.correo = correo;
    }

    public void agregarEstacion(Estacion e) {
        estaciones.add(e);
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    public String getCedulaJuridica() {
        return cedulaJuridica;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
}