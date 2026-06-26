public class Cola {

    private TurnoFactura cola[];
    private int E;
    private int S;
    private int TOP;

    // constructor
    public Cola() {
        TOP = 20;
        cola = new TurnoFactura[TOP + 1];
        E = 0;
        S = 0;
    }
    
    public Cola(int tamaño) {
        TOP = tamaño;
        cola = new TurnoFactura[TOP + 1];
    }
    
    // condiciones
    public boolean colaVacia() {
        return E == S;
    }

    public boolean colaLlena() {
        return (E + 1) % (TOP + 1) == S;
    }

    public boolean eTOP() {
        return E == TOP;
    }

    public boolean sTOP() {
        return S == TOP;
    }

    public boolean soloUnElemento() {
        return E != S && (S + 1) % (TOP + 1) == E;
    }

    public void agregarElemento(TurnoFactura y) {
        if (colaVacia()) {
            S = 1;
            E = 1;
        } else {
            if (eTOP() && !colaLlena()) {
                E = 1;   // hacer cíclica la cola
            } else {
                E++;
            }
        }
        cola[E] = y;
    }

    public TurnoFactura eliminarElemento() {
        TurnoFactura y;

        if (soloUnElemento()) {
            y = cola[S];
            S = 0;
            E = 0;
            return y;
        }

        y = cola[S++];
        return y;
    }

    public String toString() {
        String salida = "cola\n";
        for (int i = cola.length - 1; i > 0; i--) {
            // El array imprimirá null si la posición está vacía, o el contenido de TurnoFactura si hay un objeto
            salida += i + "[" + cola[i] + "]";
            if (i == S) {
                salida += "<==S";
            }
            if (i == E) {
                salida += "<==E";
            }
            salida += "\n";
        }

        salida += "E=" + E + "\n";
        salida += "S=" + S + "\n";
        return salida;
    }
}