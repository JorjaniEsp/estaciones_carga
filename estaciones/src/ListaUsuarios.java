public class ListaUsuarios {
    private Usuario[] lista;
    private int       cantidad;
    private int       tamMaximo;

    public ListaUsuarios(int tam) {
        this.tamMaximo = tam;
        this.lista     = new Usuario[tam];
        this.cantidad  = 0;
    }

    // Agrega un usuario al final
    public void agregarUsuario(Usuario nuevo) {
        if (cantidad < tamMaximo) {
            lista[cantidad++] = nuevo;
        } else {
            System.out.println("Error: Lista de usuarios llena.");
        }
    }

    // Busca por cédula, devuelve el objeto o null
    public Usuario consultarUsuarioXCedula(String cedula) {
        for (int i = 0; i < cantidad; i++) {
            if (lista[i].getCedula().equals(cedula)) return lista[i];
        }
        return null;
    }

    // Devuelve el índice del usuario con esa cédula, o -1
    public int getIndicePorCedula(String cedula) {
        for (int i = 0; i < cantidad; i++) {
            if (lista[i].getCedula().equals(cedula)) return i;
        }
        return -1;
    }

    public Usuario getUsuario(int pos)  { return lista[pos]; }
    public int     getCantidad()        { return cantidad; }
}
