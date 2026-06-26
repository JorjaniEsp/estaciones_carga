public class Estacion {
    private String idEstacion;
    private String nombreUbicacion;
    private String conectorSoportado;
    private double tarifaPorKwh;
    private int estado; // 1 = Activa, 0 = Inactiva/Mantenimiento

    public Estacion(String idEstacion, String nombreUbicacion,
                    String conectorSoportado, double tarifaPorKwh) {
        this.idEstacion       = idEstacion;
        this.nombreUbicacion  = nombreUbicacion;
        this.conectorSoportado= conectorSoportado;
        this.tarifaPorKwh     = tarifaPorKwh;
        this.estado           = 1; // nace activa
    }

    public String getIdEstacion()         { return idEstacion; }
    public void   setIdEstacion(String s) { this.idEstacion = s; }

    public String getNombreUbicacion()         { return nombreUbicacion; }
    public void   setNombreUbicacion(String s) { this.nombreUbicacion = s; }

    public String getConectorSoportado()         { return conectorSoportado; }
    public void   setConectorSoportado(String s) { this.conectorSoportado = s; }

    public double getTarifaPorKwh()        { return tarifaPorKwh; }
    public void   setTarifaPorKwh(double d){ this.tarifaPorKwh = d; }

    public int  getEstado()      { return estado; }
    public void setEstado(int e) { this.estado = e; }
}
