/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josse
 */

public class Reserva {
    private Usuario usuario;
    private Estacion estacion;
    private String horaReservada;
    private int tiempoBloqueMin;
    private boolean confirmada;

    public Reserva(Usuario usuario, Estacion estacion, String horaReservada, int tiempoBloqueMin) {
        this.usuario = usuario;
        this.estacion = estacion;
        this.horaReservada = horaReservada;
        this.tiempoBloqueMin = tiempoBloqueMin;
        this.confirmada = false;
    }

    public void confirmarLlegada() {
        confirmada = true;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public String getHoraReservada() {
        return horaReservada;
    }

    public int getTiempoBloqueMin() {
        return tiempoBloqueMin;
    }
}
