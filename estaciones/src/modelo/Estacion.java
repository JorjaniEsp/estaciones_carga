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
public class Estacion {

    private String id;
    private String ubicacion;
    private List<TipoConector> conectores = new ArrayList<>();
    private TipoCarga tipoCarga;
    private double tarifa;
    private EstadoEstacion estado;
    private List<Arista> rutas = new ArrayList<>();
    private Queue<Reserva> cola = new ArrayDeque<>();
    private int capacidadConectores;
    private int conectoresEnUso = 0;

    public Estacion(String id, String ubicacion, TipoCarga tipoCarga, double tarifa, int capacidadConectores) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.tipoCarga = tipoCarga;
        this.tarifa = tarifa;
        this.capacidadConectores = capacidadConectores;
        this.estado = EstadoEstacion.ACTIVA;
    }

    public void encolar(Reserva r) {
        cola.add(r);
    }

    public Reserva atenderSiguiente() {
        return cola.poll();
    }

    public boolean hayEspacioLibre() {
        return conectoresEnUso < capacidadConectores;
    }

    public void ocuparConector() {
        conectoresEnUso = conectoresEnUso + 1;
    }

    public void liberarConector() {
        conectoresEnUso = conectoresEnUso - 1;
    }

    public void agregarConector(TipoConector tipo) {
        conectores.add(tipo);
    }

    public boolean soportaConector(TipoConector tipo) {
        return conectores.contains(tipo);
    }

    public void agregarRuta(Arista arista) {
        rutas.add(arista);
    }

    public List<Arista> getRutas() {
        return rutas;
    }

    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public double getTarifa() {
        return tarifa;
    }

    public EstadoEstacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoEstacion estado) {
        this.estado = estado;
    }
    
    public int cantidadEnCola() {
        return cola.size();
    }
}
