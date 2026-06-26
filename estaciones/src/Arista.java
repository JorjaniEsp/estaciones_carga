public class Arista {
    private double distanciaKm;
    private double tiempoHoras;
    private double precio;

    public Arista(double distanciaKm, double tiempoHoras, double precio) {
        this.distanciaKm = distanciaKm;
        this.tiempoHoras = tiempoHoras;
        this.precio = precio;
    }

    public double getDistanciaKm() { return distanciaKm; }
    public double getTiempoHoras() { return tiempoHoras; }
    public double getPrecio() { return precio; }
}