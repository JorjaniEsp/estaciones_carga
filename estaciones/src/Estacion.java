public class Estacion {
    private String idEstacion;
    private String nombreUbicacion; // Ej: "Limón Centro", "Guápiles"
    private String conectorSoportado; 
    private double tarifaPorKwh;
    private int estado; // 1 = Activa, 0 = Inactiva/Mantenimiento

    public Estacion(String idEstacion, String nombreUbicacion, String conectorSoportado, double tarifaPorKwh) {
        this.idEstacion = idEstacion;
        this.nombreUbicacion = nombreUbicacion;
        this.conectorSoportado = conectorSoportado;
        this.tarifaPorKwh = tarifaPorKwh;
        this.estado = 1; // Por defecto nace activa
    }

    // Getters y Setters
    public String getIdEstacion() { return idEstacion; }
    public void setIdEstacion(String idEstacion) { this.idEstacion = idEstacion; }

    public String getNombreUbicacion() { return nombreUbicacion; }
    public void setNombreUbicacion(String nombreUbicacion) { this.nombreUbicacion = nombreUbicacion; }

    public String getConectorSoportado() { return conectorSoportado; }
    public void setConectorSoportado(String conectorSoportado) { this.conectorSoportado = conectorSoportado; }

    public double getTarifaPorKwh() { return tarifaPorKwh; }
    public void setTarifaPorKwh(double tarifaPorKwh) { this.tarifaPorKwh = tarifaPorKwh; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}