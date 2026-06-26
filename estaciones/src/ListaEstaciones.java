public class ListaEstaciones {
    private Estacion[] lista;
    private int        cantidad;
    private int        tamMaximo;

    public ListaEstaciones(int tam) {
        this.tamMaximo = tam;
        this.lista     = new Estacion[tam];
        this.cantidad  = 0;
    }

    // Agrega y devuelve el índice asignado (= posición en el grafo)
    public int agregarEstacion(Estacion nueva) {
        if (cantidad < tamMaximo) {
            lista[cantidad] = nueva;
            return cantidad++;
        }
        return -1;
    }

    public Estacion consultarEstacionXId(String id) {
        for (int i = 0; i < cantidad; i++) {
            if (lista[i].getIdEstacion().equals(id)) return lista[i];
        }
        return null;
    }

    public Estacion getEstacion(int pos) { return lista[pos]; }
    public int      getCantidad()        { return cantidad; }
}
