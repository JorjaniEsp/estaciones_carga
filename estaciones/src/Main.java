public class Main {

    // =========================================================================
    //  MEMORIA GLOBAL
    // =========================================================================
    public static GrafoMulticriterio mapaGlobal        = new GrafoMulticriterio(10);
    public static ListaUsuarios      catalogoUsuarios   = new ListaUsuarios(100);
    public static ListaEstaciones    catalogoEstaciones = new ListaEstaciones(20);
    public static ListaVehiculos     catalogoVehiculos  = new ListaVehiculos(50);

    // Sesión activa (se escribe desde las pantallas)
    public static Usuario usuarioActivo         = null;  // quién está logueado
    public static String  conectorSeleccionado  = null;  // elegido en pantalla Garaje
    public static int     indexEstacionObjetivo = -1;    // resultado de Dijkstra

    // =========================================================================
    //  MAIN
    // =========================================================================
    public static void main(String[] args) {
        inicializarDatosPrueba();

        // Prueba de fuego en consola
        System.out.println("\n========== PRUEBA DIJKSTRA ==========");
        mapaGlobal.calcularRutaOptima(0, "KM",     "CHAdeMO");
        mapaGlobal.calcularRutaOptima(0, "TIEMPO", "CHAdeMO");
        mapaGlobal.calcularRutaOptima(0, "PRECIO", "GBT");
        mapaGlobal.calcularRutaOptima(3, "KM",     "UNIVERSAL");

        // TODO: arrancar UI
        // new PantallaLogin().setVisible(true);
    }

    // =========================================================================
    //  DATOS DE PRUEBA — Provincia de Limón
    // =========================================================================
    public static void inicializarDatosPrueba() {

        // ── Estaciones ────────────────────────────────────────────────────────
        // IMPORTANTE: el orden aquí = índice en el grafo
        Estacion limon     = new Estacion("0", "Limon Centro",  "UNIVERSAL", 99.7);
        Estacion sanClem   = new Estacion("1", "San Clemente",  "CHAdeMO",  110.0);
        Estacion jabillo   = new Estacion("2", "El Jabillo",    "GBT",      105.0);
        Estacion guapiles  = new Estacion("3", "Guapiles",      "UNIVERSAL", 99.7);
        Estacion siquirres = new Estacion("4", "Siquirres",     "CHAdeMO",   90.0);
        Estacion cahuita   = new Estacion("5", "Cahuita",       "TIPO_1",   108.0);
        cahuita.setEstado(0); // EN MANTENIMIENTO -> Dijkstra la descarta

        // Registrar en catálogo Y en grafo (mismo orden obligatorio)
        catalogoEstaciones.agregarEstacion(limon);     mapaGlobal.agregarEstacion(limon);     // idx 0
        catalogoEstaciones.agregarEstacion(sanClem);   mapaGlobal.agregarEstacion(sanClem);   // idx 1
        catalogoEstaciones.agregarEstacion(jabillo);   mapaGlobal.agregarEstacion(jabillo);   // idx 2
        catalogoEstaciones.agregarEstacion(guapiles);  mapaGlobal.agregarEstacion(guapiles);  // idx 3
        catalogoEstaciones.agregarEstacion(siquirres); mapaGlobal.agregarEstacion(siquirres); // idx 4
        catalogoEstaciones.agregarEstacion(cahuita);   mapaGlobal.agregarEstacion(cahuita);   // idx 5

        // ── Rutas (km, horas, precio peaje) ──────────────────────────────────
        mapaGlobal.agregarRuta(0, 1,  15.0, 0.30,     0.0); // Limon <-> San Clemente
        mapaGlobal.agregarRuta(1, 2,   8.0, 0.15,     0.0); // San Clemente <-> El Jabillo
        mapaGlobal.agregarRuta(0, 3,  98.0, 1.50, 1500.0);  // Limon <-> Guapiles (Ruta 32)
        mapaGlobal.agregarRuta(2, 3,  85.0, 1.20, 1000.0);  // El Jabillo <-> Guapiles
        mapaGlobal.agregarRuta(1, 4,  25.0, 0.40,   200.0); // San Clemente <-> Siquirres
        mapaGlobal.agregarRuta(4, 3,  60.0, 0.90,   800.0); // Siquirres <-> Guapiles
        mapaGlobal.agregarRuta(0, 5,  45.0, 0.70,     0.0); // Limon <-> Cahuita (inactiva)

        // ── Usuarios ─────────────────────────────────────────────────────────
        Usuario ana    = new Usuario("101110111", "Ana Mora Solis",
                                     "ana@ucr.ac.cr",     "4111-1111-1111-1111");
        Usuario carlos = new Usuario("205660234", "Carlos Vargas Jimenez",
                                     "carlos@gmail.com",  "5500-0000-0000-0004");
        catalogoUsuarios.agregarUsuario(ana);    // índice 0
        catalogoUsuarios.agregarUsuario(carlos); // índice 1

        // ── Vehículos en catálogo general ─────────────────────────────────────
        Vehiculo v0 = new Vehiculo("LMN-001", "Hyundai Ioniq 5", "UNIVERSAL");
        Vehiculo v1 = new Vehiculo("SJB-440", "BYD Atto 3",      "GBT");
        Vehiculo v2 = new Vehiculo("CRZ-881", "Nissan Leaf",      "CHAdeMO");
        catalogoVehiculos.agregarVehiculo(v0); // índice 0
        catalogoVehiculos.agregarVehiculo(v1); // índice 1
        catalogoVehiculos.agregarVehiculo(v2); // índice 2

        // ── Garaje personal de cada usuario ───────────────────────────────────
        ana.registrarVehiculoEnGaraje(0);    // Ioniq 5
        ana.registrarVehiculoEnGaraje(1);    // BYD Atto 3
        carlos.registrarVehiculoEnGaraje(2); // Nissan Leaf

        System.out.println("Datos de prueba cargados.");
    }
}
