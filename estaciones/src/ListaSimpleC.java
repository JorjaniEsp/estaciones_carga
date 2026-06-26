/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josse
 */
public class ListaSimpleC {
  // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  // LISTA SIMPLE CIRCULAR  
  // Listas encadenadas , 
  // de manera cíclica  
  // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   private int TOP; // Máximo 
   private int Apunt[ ]; 
   private int INFO [ ]; 
   private int PTR;
   private PilaV DISP;
   
   public ListaSimpleC(){
     TOP   = 20;
     INFO  = new int[TOP];
     Apunt = new int[TOP];
     PTR   = 0;
     DISP  = new PilaV(TOP-1);
     inicializarPila();
   }
   
   public ListaSimpleC(int max){
     TOP = max+1;
     INFO = new int[TOP];
     Apunt = new int[TOP];
     PTR = 0;
     DISP = new PilaV(TOP-1);
     inicializarPila();
   }
   
   public void inicializarPila(){
     //DISP.getTop();
     for(int i=1; i<TOP; i++){
       DISP.ingresarElemento(i);
     }
   }
   
   public String desplegarPila(){
     return DISP.toString();
   }
   
   public void agregarElemento(int elemento){
     //saca un elemento de la pila y disminuye el apunt en 1
     int X = DISP.eliminarElemento();
     if(PTR==0){
       PTR = X;
     }
     INFO[X]   =elemento;
     Apunt[X]  =Apunt[PTR];
     Apunt[PTR]=X;
     PTR       =X;
   }
   
   public int eliminarElemento(){
     int  X       = PTR; 
     int elemento = INFO[X];
     INFO[X]      = 0;
     PTR          = Apunt[X];   
     Apunt[X]     = 0;
     Apunt[Apunt[PTR]]=PTR;
     //devuelve un elemento a la pila y aumenta el apunt en 1
     DISP.ingresarElemento(X);
     return elemento;
   }
   
   public int localizarElemento(int elemento){
     int R = PTR;
     int Enodo = 0;
     for(int i=R; i<TOP; i++){
       if(INFO[i]==elemento){
         Enodo=i;
         R=Apunt[i];
       }
     }
     return Enodo;
   }
//   public void  unirListas( int PTR1 , int PTR2){
//   // unir lista con la lista de la clase 
//     int R = apunt(PTR1); 
//     apunt[PTR1] = apunt[PTR2] ; 
//     apunt[PTR2] = R; 
//     PTR2 = 0; 
//   }
//   public void invertirListas ( int PTR){
//   
//   }
   
   public String toString(){
     String salida = "";
     for(int i=1; i<TOP; i++){
       salida+=i+" ";
       salida+="["+INFO[i]+"]";
       salida+="["+Apunt[i]+"]";
       if(i==PTR){
         salida+="<--- PTR \n";
       }else{
         salida+="\n";
       }
     }
     salida+="Estado de la pila\n";
     salida+=DISP.toString();
     return salida;
   }
   
   public String toStringI(){
     String salida = "";
     for(int i=TOP-1; i>0; i--){//invertido
       salida+=i+" ";
       salida+="["+INFO[i]+"]";
       
       salida+="["+Apunt[i]+"]";
       if(i==PTR){
         salida+="<--- PTR \n";
       }else{
         salida+="\n";
       }
     }
     salida+="PTR: "+PTR+"\n";
     salida+="Estado de la pila\n";
     salida+=DISP.toString();
     return salida;
   }
   
   // Getters para que la interfaz gráfica pueda leer la lista circular
    public int getPTR() { return PTR; }
    public int[] getINFO() { return INFO; }
    public int[] getApunt() { return Apunt; }
}
