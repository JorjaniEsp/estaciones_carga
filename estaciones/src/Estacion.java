public class Estacion {
    private String idEstacion;
    private String ubicacion;
    private String[] conectoresSoportados; // Ej: {"GBT", "CHAdeMO"}
    
    // Atributos requeridos por el Grafo
    private int estado; // 1 = Activo, 0 = Inactivo/Mantenimiento
    private double tarifaPorKwh; 
    
    // Estructuras de control de flujo (Múltiples colas)
    private Cola colaRapida;
    private Cola colaLenta;

    public Estacion(String idEstacion, String ubicacion, String[] conectoresSoportados, int estado, double tarifaPorKwh) {
        this.idEstacion = idEstacion;
        this.ubicacion = ubicacion;
        this.conectoresSoportados = conectoresSoportados;
        this.estado = estado;
        this.tarifaPorKwh = tarifaPorKwh;
        
        // Inicializamos las colas cíclicas
        this.colaRapida = new Cola(10); // Capacidad para carga rápida
        this.colaLenta = new Cola(15);  // Capacidad para carga estándar
    }

    // Getters y Setters básicos
    public String getIdEstacion() { return idEstacion; }
    public void setIdEstacion(String idEstacion) { this.idEstacion = idEstacion; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public String[] getConectoresSoportados() { return conectoresSoportados; }
    public void setConectoresSoportados(String[] conectoresSoportados) { this.conectoresSoportados = conectoresSoportados; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public double getTarifaPorKwh() { return tarifaPorKwh; }
    public void setTarifaPorKwh(double tarifaPorKwh) { this.tarifaPorKwh = tarifaPorKwh; }

    // Getters de estructuras
    public Cola getColaRapida() { return colaRapida; }
    public Cola getColaLenta() { return colaLenta; }

    // Método para encolar un vehículo que llega (se guarda su turno/reserva)
    public void agendarVehiculo(TurnoFactura turno, boolean esCargaRapida) {
        if(esCargaRapida) {
            colaRapida.agregarElemento(turno);
        } else {
            colaLenta.agregarElemento(turno);
        }
    }
    
    // Método para atender al siguiente vehículo (desencolar)
    public TurnoFactura atenderSiguienteVehiculo(boolean esCargaRapida) {
        if(esCargaRapida && !colaRapida.colaVacia()) {
            return colaRapida.eliminarElemento();
        } else if (!esCargaRapida && !colaLenta.colaVacia()) {
            return colaLenta.eliminarElemento();
        }
        return null; // La cola está vacía
    }
}