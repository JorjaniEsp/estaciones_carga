
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("=== INICIANDO SISTEMA ChargeCR ===");
        
        // 1. Inicializar Catálogos Maestros y Estructuras
        ListaUsuarios catalogoUsuarios = new ListaUsuarios(100);
        ListaVehiculos catalogoVehiculos = new ListaVehiculos(200);
        
        // 2. Crear Usuarios y Vehículos de prueba
        Usuario u1 = new Usuario("101110111", "Carlos Mendoza", "carlos@mail.com", "4000-1234-5678-9010");
        catalogoUsuarios.agregarUsuario(u1);
        
        Vehiculo v1 = new Vehiculo("BCC-123", "Nissan Leaf", "CHAdeMO");
        catalogoVehiculos.agregarVehiculo(v1);
        
        // Ligar el vehículo al garaje del usuario (ListaSimpleC)
        u1.getMisVehiculosIds().agregarElemento(0);
        // 3. Inicializar el Cerebro: Grafo Multicriterio (Capacidad para 15 nodos)
        GrafoMulticriterio mapaEstaciones = new GrafoMulticriterio(15);

        // 4. Crear las Estaciones reales
        // Firma: (ID, Ubicación, Conectores, Estado (1=Activo), TarifaPorKwh)
        Estacion e0 = new Estacion("EST-00", "Limón Centro", new String[]{"GBT", "CHAdeMO", "TIPO_1"}, 1, 150.0);
        Estacion e1 = new Estacion("EST-01", "Siquirres", new String[]{"UNIVERSAL"}, 1, 140.0);
        Estacion e2 = new Estacion("EST-02", "Guápiles", new String[]{"TIPO_1", "TIPO_2"}, 1, 145.0);
        Estacion e3 = new Estacion("EST-03", "Bananito", new String[]{"CHAdeMO", "TIPO_2"}, 1, 160.0);
        Estacion e4 = new Estacion("EST-04", "Penshurt", new String[]{"GBT", "TIPO_2"}, 1, 155.0);
        Estacion e5 = new Estacion("EST-05", "La Guaria (Valle La Estrella)", new String[]{"UNIVERSAL"}, 1, 150.0);
        Estacion e6 = new Estacion("EST-06", "Cahuita", new String[]{"TIPO_1", "CHAdeMO"}, 1, 165.0);
        Estacion e7 = new Estacion("EST-07", "Puerto Viejo", new String[]{"GBT", "TIPO_2", "UNIVERSAL"}, 1, 170.0);

        // Agregar nodos al grafo
        mapaEstaciones.agregarEstacion(e0); // Índice 0
        mapaEstaciones.agregarEstacion(e1); // Índice 1
        mapaEstaciones.agregarEstacion(e2); // Índice 2
        mapaEstaciones.agregarEstacion(e3); // Índice 3
        mapaEstaciones.agregarEstacion(e4); // Índice 4
        mapaEstaciones.agregarEstacion(e5); // Índice 5
        mapaEstaciones.agregarEstacion(e6); // Índice 6
        mapaEstaciones.agregarEstacion(e7); // Índice 7

        // 5. Configurar las Aristas (Rutas reales aproximadas)
        // Firma: (Origen, Destino, Km, Horas, Costo/Peaje)
        // Corredor Ruta 32
        mapaEstaciones.agregarRuta(0, 1, 58.0, 1.0, 1000);  // Limón Centro <-> Siquirres
        mapaEstaciones.agregarRuta(1, 2, 40.0, 0.6, 500);   // Siquirres <-> Guápiles

        // Corredor Ruta 36 (Caribe Sur)
        mapaEstaciones.agregarRuta(0, 3, 22.0, 0.35, 0);    // Limón Centro <-> Bananito
        mapaEstaciones.agregarRuta(3, 4, 15.0, 0.25, 0);    // Bananito <-> Penshurt

        // Bifurcación en Penshurt (Hacia Valle La Estrella o hacia la costa)
        mapaEstaciones.agregarRuta(4, 5, 20.0, 0.4, 0);     // Penshurt <-> La Guaria
        mapaEstaciones.agregarRuta(4, 6, 12.0, 0.2, 0);     // Penshurt <-> Cahuita

        // Continuación Caribe Sur
        mapaEstaciones.agregarRuta(6, 7, 16.0, 0.3, 0);     // Cahuita <-> Puerto Viejo

        System.out.println("Mapa y Rutas configuradas exitosamente con " + mapaEstaciones.getCantidadNodos() + " estaciones.");

        // 6. Prueba Rápida del Algoritmo
        System.out.println("\n--- PRUEBA DE DIJKSTRA ---");
        // El usuario está en Guápiles (Índice 2) y busca el conector "CHAdeMO" más cercano en KM
        mapaEstaciones.calcularRutaOptima(2, "KM", "CHAdeMO");

        // El usuario está en Penshurt (Índice 4) y busca la estación "UNIVERSAL" más rápida en TIEMPO
        mapaEstaciones.calcularRutaOptima(4, "TIEMPO", "UNIVERSAL");

        // 7. Lanzar Interfaz Gráfica
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal app = new VentanaPrincipal(catalogoUsuarios, catalogoVehiculos, mapaEstaciones);
            app.setVisible(true);
        });
    }
}
