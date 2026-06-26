public class Vehiculo {
    private String placa;
    private String marcaModelo;
    private String tipoConector; // "GBT", "CHAdeMO", "TIPO_1", "UNIVERSAL"

    public Vehiculo(String placa, String marcaModelo, String tipoConector) {
        this.placa         = placa;
        this.marcaModelo   = marcaModelo;
        this.tipoConector  = tipoConector;
    }

    public String getPlaca()             { return placa; }
    public void   setPlaca(String s)     { this.placa = s; }

    public String getMarcaModelo()          { return marcaModelo; }
    public void   setMarcaModelo(String s)  { this.marcaModelo = s; }

    public String getTipoConector()         { return tipoConector; }
    public void   setTipoConector(String s) { this.tipoConector = s; }
}
