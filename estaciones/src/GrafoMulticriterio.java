public class GrafoMulticriterio {

    private Estacion[][] matrizAdyacencia; // pesos entre nodos
    private Estacion[]   nodos;            // estaciones registradas
    private int          cantidadNodos;
    private int          maxNodos;

    public GrafoMulticriterio(int maxNodos) {
        this.maxNodos         = maxNodos;
        this.nodos            = new Estacion[maxNodos];
        this.matrizAdyacencia = new Estacion[maxNodos][maxNodos]; // solo para saber si hay arista
        this.cantidadNodos    = 0;
        // Usamos una matriz separada para los pesos
        this.pesos            = new Arista[maxNodos][maxNodos];
    }

    private Arista[][] pesos; // pesos reales de cada arista

    // ── Construcción ─────────────────────────────────────────────────────────

    public void agregarEstacion(Estacion estacion) {
        if (cantidadNodos < maxNodos) {
            nodos[cantidadNodos++] = estacion;
        }
    }

    /** Ruta bidireccional entre dos estaciones por índice. */
    public void agregarRuta(int origen, int destino,
                             double km, double horas, double precio) {
        Arista a = new Arista(km, horas, precio);
        pesos[origen][destino] = a;
        pesos[destino][origen] = a;
    }

    // ── Dijkstra ──────────────────────────────────────────────────────────────

    /**
     * Calcula la estación óptima desde el nodo origen.
     * Filtra por conector compatible y estado activo (estado == 1).
     *
     * @param origen           índice del nodo de partida
     * @param criterio         "KM" | "TIEMPO" | "PRECIO"
     * @param conectorRequerido conector del vehículo del usuario
     * @return índice del nodo ganador, o -1 si no hay ninguno compatible.
     */
    public int calcularRutaOptima(int origen, String criterio, String conectorRequerido) {

        double[]  dist     = new double[cantidadNodos];
        boolean[] visitado = new boolean[cantidadNodos];
        int[]     previo   = new int[cantidadNodos];

        // Inicializar
        for (int i = 0; i < cantidadNodos; i++) {
            dist[i]     = Double.MAX_VALUE;
            visitado[i] = false;
            previo[i]   = -1;
        }
        dist[origen] = 0.0;

        // Proceso Dijkstra
        for (int iter = 0; iter < cantidadNodos - 1; iter++) {

            int u = nodoMinimo(dist, visitado);
            if (u == -1) break;

            // Nodo inactivo: lo marcamos visitado y lo saltamos
            // (el origen se procesa siempre porque dist[origen]=0)
            if (u != origen && nodos[u].getEstado() == 0) {
                visitado[u] = true;
                continue;
            }

            visitado[u] = true;

            for (int v = 0; v < cantidadNodos; v++) {
                if (!visitado[v] && pesos[u][v] != null) {
                    double peso       = getPeso(pesos[u][v], criterio);
                    double nueva      = dist[u] + peso;
                    if (dist[u] != Double.MAX_VALUE && nueva < dist[v]) {
                        dist[v]   = nueva;
                        previo[v] = u;
                    }
                }
            }
        }

        // Elegir ganador: compatible, activa, menor costo
        int    ganador  = -1;
        double minCosto = Double.MAX_VALUE;

        for (int i = 0; i < cantidadNodos; i++) {
            if (i == origen)                        continue;
            if (dist[i] == Double.MAX_VALUE)        continue;
            
            // Asumiendo que agregas getEstado() a Estacion (1 = activo, 0 = inactivo)
            if (nodos[i].getEstado() == 0)          continue;

            // NUEVA LÓGICA DE COMPATIBILIDAD (Iterar sobre el arreglo)
            boolean compatible = false;
            String[] conectores = nodos[i].getConectoresSoportados();
            if (conectores != null) {
                for (int k = 0; k < conectores.length; k++) {
                    if (conectores[k].equals(conectorRequerido) || conectores[k].equals("UNIVERSAL")) {
                        compatible = true;
                        break;
                    }
                }
            }

            if (compatible && dist[i] < minCosto) {
                minCosto = dist[i];
                ganador  = i;
            }
        }

        // Imprimir resultado en consola
        imprimirResultado(ganador, dist, previo, origen, criterio, conectorRequerido);

        return ganador;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private int nodoMinimo(double[] dist, boolean[] visitado) {
        double min = Double.MAX_VALUE;
        int    idx = -1;
        for (int i = 0; i < cantidadNodos; i++) {
            if (!visitado[i] && dist[i] <= min) { min = dist[i]; idx = i; }
        }
        return idx;
    }

    private double getPeso(Arista a, String criterio) {
        switch (criterio.toUpperCase()) {
            case "TIEMPO": return a.getTiempoHoras();
            case "PRECIO": return a.getPrecio();
            default:       return a.getDistanciaKm(); // KM
        }
    }

    private String reconstruirCamino(int[] previo, int destino) {
        int[] camino = new int[cantidadNodos];
        int   n      = 0;
        int   actual = destino;
        while (actual != -1) { camino[n++] = actual; actual = previo[actual]; }

        String r = "";
        for (int i = n - 1; i >= 0; i--) {
            r += nodos[camino[i]].getUbicacion(); // CAMBIO AQUÍ
            if (i > 0) r += " -> ";
        }
        return r;
    }

    private void imprimirResultado(int ganador, double[] dist, int[] previo,
                                    int origen, String criterio, String conector) {
        System.out.println("\n[Dijkstra | " + criterio + " | Conector: " + conector + "]");
        System.out.println("Origen: " + nodos[origen].getUbicacion()); // CAMBIO AQUÍ
        if (ganador == -1) {
            System.out.println(">> Sin resultado: no hay estaciones compatibles.");
            return;
        }
        String unidad = criterio.equals("KM") ? " km"
                      : criterio.equals("TIEMPO") ? " h" : " colones";
        System.out.println(">> Estacion optima : " + nodos[ganador].getUbicacion()); // CAMBIO AQUÍ
        System.out.println(">> Costo           : " + dist[ganador] + unidad);
        System.out.println(">> Tarifa          : " + nodos[ganador].getTarifaPorKwh() + "/kWh"); // Asegúrate de tener getTarifaPorKwh() en Estacion
        System.out.println(">> Ruta            : " + reconstruirCamino(previo, ganador));
    }

    // ── Acceso para las vistas ────────────────────────────────────────────────

    public Estacion getEstacion(int indice) {
        if (indice < 0 || indice >= cantidadNodos) return null;
        return nodos[indice];
    }

    public int getCantidadNodos() { return cantidadNodos; }
}
