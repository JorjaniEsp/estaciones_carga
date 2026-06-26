public class ListaVehiculos {
    private Vehiculo[] lista;
    private int        cantidad;
    private int        tamMaximo;

    public ListaVehiculos(int tam) {
        this.tamMaximo = tam;
        this.lista     = new Vehiculo[tam];
        this.cantidad  = 0;
    }

    // Agrega y devuelve el índice asignado
    public int agregarVehiculo(Vehiculo nuevo) {
        if (cantidad < tamMaximo) {
            lista[cantidad] = nuevo;
            return cantidad++;
        }
        return -1;
    }

    public Vehiculo consultarVehiculoXPlaca(String placa) {
        for (int i = 0; i < cantidad; i++) {
            if (lista[i].getPlaca().equals(placa)) return lista[i];
        }
        return null;
    }

    public Vehiculo getVehiculo(int pos) { return lista[pos]; }
    public int      getCantidad()        { return cantidad; }
}
