public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String numeroTarjeta;
    
    // Estructuras integradas
    private ListaSimpleC misVehiculosIds; // El garaje (lista enlazada)
    private Pila historialFacturas;       // NUEVO: Historial LIFO de transacciones

    public Usuario(String cedula, String nombreCompleto, String correo, String numeroTarjeta) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.numeroTarjeta = numeroTarjeta;
        
        // Inicializamos las estructuras
        this.misVehiculosIds = new ListaSimpleC(); 
        this.historialFacturas = new Pila(20); // Capacidad base del historial
    }

    // Getters y Setters básicos
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
    
    // Getters de estructuras
    public ListaSimpleC getMisVehiculosIds() { return misVehiculosIds; }
    public Pila getHistorialFacturas() { return historialFacturas; }

    // Método para apilar una nueva factura/penalidad
    public void registrarFactura(TurnoFactura nuevaFactura) {
        historialFacturas.ckIngresarElemento(nuevaFactura);
    }
}
