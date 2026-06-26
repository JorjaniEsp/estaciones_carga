public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String numeroTarjeta; // Para cobrar multas o recargas
    
    // El "Garaje" del usuario usando la estructura circular del profesor
    private ListaSimpleC misVehiculosIds; 

    public Usuario(String cedula, String nombreCompleto, String correo, String numeroTarjeta) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.numeroTarjeta = numeroTarjeta;
        
        // Inicializamos la lista circular para que el usuario guarde hasta 20 carros
        this.misVehiculosIds = new ListaSimpleC(); 
    }

    // Método para agregar un carro al garaje
    public void registrarVehiculoEnGaraje(int idVehiculoEnListaGeneral) {
        this.misVehiculosIds.agregarElemento(idVehiculoEnListaGeneral);
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public ListaSimpleC getMisVehiculosIds() { return misVehiculosIds; }
}