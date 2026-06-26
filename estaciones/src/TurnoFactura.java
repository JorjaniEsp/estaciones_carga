public class TurnoFactura {
    private String fechaHora;
    private String idEstacion;
    private int tiempoConsumidoMinutos;
    private double montoCobrado;

    public TurnoFactura(String fechaHora, String idEstacion, int tiempoConsumidoMinutos, double montoCobrado) {
        this.fechaHora = fechaHora;
        this.idEstacion = idEstacion;
        this.tiempoConsumidoMinutos = tiempoConsumidoMinutos;
        this.montoCobrado = montoCobrado;
    }

    // Getters
    public String getFechaHora() { return fechaHora; }
    public String getIdEstacion() { return idEstacion; }
    public int getTiempoConsumidoMinutos() { return tiempoConsumidoMinutos; }
    public double getMontoCobrado() { return montoCobrado; }

    // Setters
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    public void setIdEstacion(String idEstacion) { this.idEstacion = idEstacion; }
    public void setTiempoConsumidoMinutos(int tiempoConsumidoMinutos) { this.tiempoConsumidoMinutos = tiempoConsumidoMinutos; }
    public void setMontoCobrado(double montoCobrado) { this.montoCobrado = montoCobrado; }
    
    @Override
    public String toString() {
        return "Fecha: " + fechaHora + " | Estación: " + idEstacion + 
               " | Minutos: " + tiempoConsumidoMinutos + " | Total: ₡" + montoCobrado;
    }
}