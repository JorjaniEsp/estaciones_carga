public class ListaUsuarios {
    private Usuario listaUsuarios[];
    private int aUsuario;
    private int tamMaximo;

    public ListaUsuarios(int tam) {
        this.tamMaximo = tam;
        this.listaUsuarios = new Usuario[tamMaximo];
        this.aUsuario = 0;
    }

    public void agregarUsuario(Usuario nuevo) {
        if (aUsuario < tamMaximo) {
            listaUsuarios[aUsuario++] = nuevo;
        } else {
            System.out.println("Error: Lista de usuarios llena.");
        }
    }

    public Usuario consultarUsuarioXCedula(String cedulaBuscar) {
        for (int i = 0; i < aUsuario; i++) {
            if (listaUsuarios[i].getCedula().equals(cedulaBuscar)) {
                return listaUsuarios[i];
            }
        }
        return null;
    }

    public int getAUsuario() { return aUsuario; }
    public Usuario getUsuario(int pos) { return listaUsuarios[pos]; }
}