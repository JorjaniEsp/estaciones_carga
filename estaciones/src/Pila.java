


public class Pila {
        
    private int pila[];
    private int TOP;
    private int apunt;
    
    public Pila(){
        pila = new int [21];
        TOP = 20;
        apunt = 0;
        
    }
    
    public Pila(int tamaño){
        pila = new int [tamaño+1];
        TOP = tamaño;
        apunt = 0;
        
    }

public boolean pilaLlena() {
    return apunt == TOP;
}

public boolean pilaVacia() {
    return apunt == 0;
}

public void ingresarElemento(int y) {
    apunt++;
    pila[apunt] = y;
}
    public int eliminarElemento() {
    return pila[apunt--];
}

public int getTop() {
    return TOP;
}

public String ckIngresarElemento() {

    if (!pilaLlena()) {
        return "Pila normal, elemento sera ingresado";
    } else {
        return "Pila LLENA, elemento no ingresará";
    }
}
public String ckIngresarElemento(int y) {

    String sal;

    if (!pilaLlena()) {
        ingresarElemento(y);
        sal = "Pila normal, elemento ingresado";
    } else {
        sal = "Pila LLENA, elemento no ingresará";
    }

    return sal;
}
    public String ckEliminarElemento() {

    String sal;

    if (!pilaVacia()) {
        eliminarElemento();
        sal = "Elemento eliminado";
    } else {
        sal = "Pila VACIA, no se puede eliminar";
    }

    return sal;
}

    @Override
    public String toString()
  {
    String salida="\n";
    for(int i=pila.length-1;i>0;i--){
      salida+=i+"["+pila[i]+"]";
      if(i==pila.length-1){
        salida+="<==TOP";
      }
      if(i==apunt){
        salida+="<==Apunt";
      }
        salida+="\n";
    }
    salida+="Apunt="+apunt+"\n";
    return salida;
  }//fin del metodo toString
    

}
