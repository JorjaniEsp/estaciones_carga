public class GrafoMulticriterio {
    private Estacion[] nodos;
    private Arista[][] matrizAdyacencia;
    private int cantidadNodos;

    public GrafoMulticriterio(int maxNodos) {
        nodos = new Estacion[maxNodos];
        matrizAdyacencia = new Arista[maxNodos][maxNodos];
        cantidadNodos = 0;
    }

    public void agregarEstacion(Estacion estacion) {
        if (cantidadNodos < nodos.length) {
            nodos[cantidadNodos] = estacion;
            cantidadNodos++;
        }
    }

    public void agregarRuta(int indiceOrigen, int indiceDestino, double km, double horas, double precio) {
        Arista nuevaRuta = new Arista(km, horas, precio);
        matrizAdyacencia[indiceOrigen][indiceDestino] = nuevaRuta;
        matrizAdyacencia[indiceDestino][indiceOrigen] = nuevaRuta; // Es bidireccional
    }

    // El núcleo del proyecto
    public void calcularRutaOptima(int origen, String criterio, String conectorRequerido) {
        double[] distancias = new double[cantidadNodos];
        boolean[] visitados = new boolean[cantidadNodos];
        int[] previos = new int[cantidadNodos];

        // Inicializar distancias al infinito
        for (int i = 0; i < cantidadNodos; i++) {
            distancias[i] = Double.MAX_VALUE;
            visitados[i] = false;
            previos[i] = -1;
        }

        distancias[origen] = 0;

        for (int i = 0; i < cantidadNodos - 1; i++) {
            int u = buscarNodoMinimo(distancias, visitados);
            if (u == -1) break; // Nodos inalcanzables
            
            visitados[u] = true;

            for (int v = 0; v < cantidadNodos; v++) {
                if (!visitados[v] && matrizAdyacencia[u][v] != null) {
                    
                    // FILTRO DE NEGOCIO: ¿La estación destino tiene el conector que ocupo?
                    // (Ignoramos el filtro si 'v' es un punto de paso que no es el destino final, 
                    // pero para simplificar, si no es compatible, penalizamos la parada)
                    if (!nodos[v].getConectorSoportado().equals(conectorRequerido) && !nodos[v].getConectorSoportado().equals("UNIVERSAL")) {
                        continue; // Saltamos esta estación porque no nos sirve
                    }

                    // Extraer el peso según el criterio del usuario
                    double pesoArista = obtenerPesoPorCriterio(matrizAdyacencia[u][v], criterio);

                    if (distancias[u] != Double.MAX_VALUE && distancias[u] + pesoArista < distancias[v]) {
                        distancias[v] = distancias[u] + pesoArista;
                        previos[v] = u;
                    }
                }
            }
        }
        
        imprimirResultados(distancias, origen, criterio);
    }

    private int buscarNodoMinimo(double[] distancias, boolean[] visitados) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < cantidadNodos; i++) {
            if (!visitados[i] && distancias[i] <= min) {
                min = distancias[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private double obtenerPesoPorCriterio(Arista arista, String criterio) {
        switch (criterio.toUpperCase()) {
            case "TIEMPO": return arista.getTiempoHoras();
            case "PRECIO": return arista.getPrecio();
            case "KM": default: return arista.getDistanciaKm();
        }
    }

    private void imprimirResultados(double[] distancias, int origen, String criterio) {
        System.out.println("--- Resultados desde: " + nodos[origen].getNombre() + " (Prioridad: " + criterio + ") ---");
        for (int i = 0; i < cantidadNodos; i++) {
            if (i != origen) {
                if (distancias[i] == Double.MAX_VALUE) {
                    System.out.println("Hacia " + nodos[i].getNombre() + ": Inalcanzable o Incompatible");
                } else {
                    System.out.println("Hacia " + nodos[i].getNombre() + ": " + distancias[i] + " unidades");
                }
            }
        }
    }
}
