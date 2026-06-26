public class Vehiculo {
    private String placa;
    private String marcaModelo;
    private String tipoConector; // Ej: "GBT", "CHAdeMO", "TIPO_1", "UNIVERSAL"

    public Vehiculo(String placa, String marcaModelo, String tipoConector) {
        this.placa = placa;
        this.marcaModelo = marcaModelo;
        this.tipoConector = tipoConector;
    }

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarcaModelo() { return marcaModelo; }
    public void setMarcaModelo(String marcaModelo) { this.marcaModelo = marcaModelo; }

    public String getTipoConector() { return tipoConector; }
    public void setTipoConector(String tipoConector) { this.tipoConector = tipoConector; }
}