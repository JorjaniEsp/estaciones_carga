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

public class ControladorAgendamiento {

    public Reserva reservar(Usuario usuario, Estacion estacion, String hora, int tiempoBloqueMin) {
        if (!usuario.tieneVehiculo()) return null;

        Reserva reserva = new Reserva(usuario, estacion, hora, tiempoBloqueMin);
        estacion.encolar(reserva);
        return reserva;
    }

    public void confirmarLlegada(Reserva reserva, Estacion estacion) {
        reserva.confirmarLlegada();
        estacion.ocuparConector();
    }

    // se llama cuando pasaron los 10 minutos de gracia sin confirmar
    public TurnoFactura procesarAusencia(Reserva reserva, double montoMulta) {
        if (reserva.isConfirmada()) return null;

        Estacion estacion = reserva.getEstacion();
        estacion.atenderSiguiente();

        TurnoFactura multa = new TurnoFactura(reserva.getHoraReservada(), estacion.getId(),
                                               0, montoMulta, TipoTransaccion.MULTA);
        reserva.getUsuario().agregarFactura(multa);
        return multa;
    }

    // cobro por bloque completo, llegue a tiempo o dentro del margen de gracia
    public TurnoFactura finalizarCarga(Reserva reserva, String fechaHora) {
        Estacion estacion = reserva.getEstacion();
        double monto = estacion.getTarifa() * reserva.getTiempoBloqueMin();

        TurnoFactura factura = new TurnoFactura(fechaHora, estacion.getId(),
                                                 reserva.getTiempoBloqueMin(), monto, TipoTransaccion.CARGA);
        reserva.getUsuario().agregarFactura(factura);
        estacion.liberarConector();
        return factura;
    }
}
