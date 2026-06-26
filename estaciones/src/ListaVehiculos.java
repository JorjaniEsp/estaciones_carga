public class ListaVehiculos {
    private Vehiculo listaVehiculos[];
    private int aVehiculo;
    private int tamMaximo;

    public ListaVehiculos(int tam) {
        this.tamMaximo = tam;
        this.listaVehiculos = new Vehiculo[tamMaximo];
        this.aVehiculo = 0;
    }

    public void agregarVehiculo(Vehiculo nuevo) {
        if (aVehiculo < tamMaximo) {
            listaVehiculos[aVehiculo++] = nuevo;
        }
    }

    public Vehiculo consultarVehiculoXPlaca(String placaBuscar) {
        for (int i = 0; i < aVehiculo; i++) {
            if (listaVehiculos[i].getPlaca().equals(placaBuscar)) {
                return listaVehiculos[i];
            }
        }
        return null;
    }

    public int getAVehiculo() { return aVehiculo; }
    public Vehiculo getVehiculo(int pos) { return listaVehiculos[pos]; }
}