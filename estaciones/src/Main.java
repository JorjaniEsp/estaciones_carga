public class Main {
    
    // 1. MEMORIA GLOBAL: Accesible desde cualquier pantalla de la interfaz
    public static GrafoMulticriterio mapaGlobal = new GrafoMulticriterio(10);
    
    // Asumiendo que adaptaste la ListaContactos a ListaUsuarios y ListaEstaciones
    public static ListaUsuarios catalogoUsuarios = new ListaUsuarios(100);
    public static ListaEstaciones catalogoEstaciones = new ListaEstaciones(20);
    public static ListaVehiculos catalogoVehiculos = new ListaVehiculos(50); // Catálogo general de carros

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema ChargeCR...");
        
        // 2. Cargar los datos de prueba
        inicializarDatosPrueba();
        
        // 3. PRUEBA DE FUEGO (Consola): Verificar que el Cerebro funciona
        System.out.println("\n--- PRUEBA DEL ALGORITMO DIJKSTRA ---");
        System.out.println("Un conductor en Limón Centro con conector CHAdeMO busca la ruta más CORTA:");
        // 0 = Limón Centro. Criterio = "KM". Conector = "CHAdeMO"
        mapaGlobal.calcularRutaOptima(0, "KM", "CHAdeMO");

        System.out.println("\nEl mismo conductor busca la ruta más RÁPIDA:");
        mapaGlobal.calcularRutaOptima(0, "TIEMPO", "CHAdeMO");
        
        // Aquí iría el código para arrancar tu interfaz gráfica (ej. new PantallaLogin().setVisible(true); )
    }

    private static void inicializarDatosPrueba() {
        // --- A. CREAR ESTACIONES (Nodos) ---
        // (ID/Índice en matriz, Nombre, Conector, Tarifa kWh)
        Estacion limon = new Estacion("0", "Limón Centro", "UNIVERSAL", 99.7);
        Estacion sanClemente = new Estacion("1", "San Clemente", "CHAdeMO", 110.0);
        Estacion elJabillo = new Estacion("2", "El Jabillo", "GBT", 105.0);
        Estacion guapiles = new Estacion("3", "Guápiles", "UNIVERSAL", 99.7);
        
        catalogoEstaciones.agregarEstacion(limon);
        catalogoEstaciones.agregarEstacion(sanClemente);
        catalogoEstaciones.agregarEstacion(elJabillo);
        catalogoEstaciones.agregarEstacion(guapiles);
        
        // Alimentar el Grafo con los nodos
        mapaGlobal.agregarEstacion(limon);
        mapaGlobal.agregarEstacion(sanClemente);
        mapaGlobal.agregarEstacion(elJabillo);
        mapaGlobal.agregarEstacion(guapiles);

        // --- B. CREAR RUTAS (Aristas Multicriterio) ---
        // (Origen, Destino, Km, Horas, Precio Peajes)
        mapaGlobal.agregarRuta(0, 1, 15.0, 0.3, 0.0); // Limón a San Clemente
        mapaGlobal.agregarRuta(1, 2, 8.0, 0.15, 0.0); // San Clemente a El Jabillo
        mapaGlobal.agregarRuta(0, 3, 98.0, 1.5, 1500.0); // Limón a Guápiles (Ruta 32)
        mapaGlobal.agregarRuta(2, 3, 85.0, 1.2, 1000.0); // El Jabillo a Guápiles

        // --- C. CREAR USUARIOS Y VEHÍCULOS DE PRUEBA ---
        Usuario admin = new Usuario("111", "Usuario Prueba", "admin@ucr.ac.cr", "4000-1234-5678-9010");
        catalogoUsuarios.agregarUsuario(admin);
        
        // Creamos carros en el catálogo general (Asignamos ID simulados 0 y 1)
        Vehiculo carro1 = new Vehiculo("XYZ-123", "Nissan Leaf", "CHAdeMO");
        Vehiculo carro2 = new Vehiculo("BYD-999", "BYD Dolphin", "GBT");
        catalogoVehiculos.agregarVehiculo(carro1);
        catalogoVehiculos.agregarVehiculo(carro2);
        
        // El usuario mete esos carros a su garaje personal (ListaSimpleC)
        admin.registrarVehiculoEnGaraje(0); // Mete el carro1
        admin.registrarVehiculoEnGaraje(1); // Mete el carro2
        
        System.out.println("Datos de prueba inicializados correctamente.");
    }
}