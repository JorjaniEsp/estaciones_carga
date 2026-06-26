public class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String numeroTarjeta;

    // Garaje: guarda índices de catalogoVehiculos[]
    private ListaSimpleC misVehiculosIds;

    public Usuario(String cedula, String nombreCompleto,
                   String correo, String numeroTarjeta) {
        this.cedula          = cedula;
        this.nombreCompleto  = nombreCompleto;
        this.correo          = correo;
        this.numeroTarjeta   = numeroTarjeta;
        this.misVehiculosIds = new ListaSimpleC();
    }

    /** Agrega el índice del vehículo en catalogoVehiculos[] al garaje. */
    public void registrarVehiculoEnGaraje(int idVehiculoEnListaGeneral) {
        this.misVehiculosIds.agregarElemento(idVehiculoEnListaGeneral);
    }

    public String       getCedula()           { return cedula; }
    public void         setCedula(String s)   { this.cedula = s; }

    public String       getNombreCompleto()         { return nombreCompleto; }
    public void         setNombreCompleto(String s) { this.nombreCompleto = s; }

    public String       getCorreo()           { return correo; }
    public void         setCorreo(String s)   { this.correo = s; }

    public String       getNumeroTarjeta()          { return numeroTarjeta; }
    public void         setNumeroTarjeta(String s)  { this.numeroTarjeta = s; }

    public ListaSimpleC getMisVehiculosIds()   { return misVehiculosIds; }
}
