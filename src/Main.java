import adt.structures.Nodo;
import adt.interfaces.ConjuntoTDA;
import adt.implementation.GrafoLA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class Main{

    static GrafoLA leerRutas() {
        GrafoLA grafoD = new GrafoLA();
        HashSet<Integer> nodos = new HashSet<>();
        ArrayList<int[]> aristas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("rutas.txt"));
            // Saltar la primera línea
            reader.readLine();
            // Leer las líneas 2 a 157
            for (int i = 0; i < 156; i++) {
                String line = reader.readLine();
                if (line != null) {
                    if (line.contains("#")) {
                        line = line.substring(0, line.indexOf("#")).trim();
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        int origen = Integer.parseInt(parts[0]);
                        int destino = Integer.parseInt(parts[1]);
                        int peso = Integer.parseInt(parts[2]);
                        nodos.add(origen);
                        nodos.add(destino);
                        aristas.add(new int[]{origen, destino, peso});
                    }
                }
            }
            reader.close();
            for (int nodo : nodos) {
                grafoD.agregarVertice(nodo);
            }
            for (int[] arista : aristas) {
                grafoD.agregarArista(arista[0], arista[1], arista[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grafoD;
    }
    static void imprimir(GrafoLA grafo) {
        ConjuntoTDA vertices = grafo.vertices();
        while (!vertices.conjuntoVacio()) {
            int vo = vertices.elegir();
            vertices.sacar(vo);
            ConjuntoTDA otrosVertices = grafo.vertices();
            while (!otrosVertices.conjuntoVacio()) {
                int vd = otrosVertices.elegir();
                otrosVertices.sacar(vd);
                if (grafo.existeArista(vo, vd)) {
                    int peso = grafo.pesoArista(vo, vd);
                    System.out.println("Origen: " + vo + ", Destino: " + vd + ", Peso: " + peso);
                }
            }
        }
    
    }
    //-----------------------------------------------------------------------
    
    static class Resultado {
        int cl;
        int cd;
        int[] vpa;
        int[] CDP;
        int[] CFP;

        Resultado(int cl, int cd, int[] vpa, int[] CDP, int[] CFP) {
            this.cl = cl;
            this.cd = cd;
            this.vpa = vpa;
            this.CDP = CDP;
            this.CFP = CFP;
        }
    }
    
    public static Resultado leerLineas() {
        int cl = 0;
        int cd = 0;
        int[] vpa = new int[50];
        int[] CDP = new int[8];
        int[] CFP = new int[8];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("clientesycentros.txt"));
            String line = reader.readLine();
            if (line != null && line.contains("#")) {
                line = line.substring(0, line.indexOf("#")).trim();
            }
            cl = Integer.parseInt(line);
            line = reader.readLine();
            if (line != null && line.contains("#")) {
                line = line.substring(0, line.indexOf("#")).trim();
            }
            cd = Integer.parseInt(line);
             // Leer las líneas 3 a 10

             for (int i = 0; i < 8; i++) {
                line = reader.readLine();
                if (line != null) {
                    if (line.contains("#")) {
                        line = line.substring(0, line.indexOf("#")).trim();
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        int position = Integer.parseInt(parts[0]);
                        CDP[position] = Integer.parseInt(parts[1]);
                        CFP[position] = Integer.parseInt(parts[2]);
                    }
                }
            }
            // Leer las líneas 11 a 60
            for (int i = 0; i < 50; i++) {
                line = reader.readLine();
                if (line != null) {
                    if (line.contains("#")) {
                        line = line.substring(0, line.indexOf("#")).trim();
                    }
                    String[] parts = line.split(",");
                    int position = Integer.parseInt(parts[0]);
                    int value = Integer.parseInt(parts[1]);
                    vpa[position] = value;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Resultado(cl, cd, vpa, CDP, CFP);
    }

        public static Nodo minim(int[] CFP, int[] c) {
        Nodo n1 = CrearNodoRaiz(c, CFP);
        vivos.add(n1);
        int cota = n1.cotaSup;
        Nodo mejorSolucion = null;
        while (!vivos.isEmpty()) {
            Nodo nodo = primero(vivos);
            vivos.remove(nodo);
            Nodo[] hijos = generarHijos(nodo, c, CFP);
            for (Nodo h : hijos) {
                if (NoPodar(h, cota)) {
                    if (esSolucion(h)) {
                        if (esMejorSolucion(h, mejorSolucion)) {
                            mejorSolucion = h;
                            cota = actualizar(cota, h);
                        } else {
                            vivos.add(h);
                            cota = actualizar(cota, h);
                        }
                    }
                }
            }
        }
        return mejorSolucion;
    }
    
    /* public static minim (CFP[],int C[]){
        n1=CrearNodoRaiz(c,CFP);
        vivos=CrearColaPrioridad();
        vivos.agregar(n1,n1.cotainf);
        cota=n1.cotaSup;
        mejorSolucion=null;
        while(vivos!= 0){
            nodo=primero(vivos);
            vivos.sacar(nodo);
            hijos=generarHijos(nodo,c,CFP);
            for(int h =0;h<hijos;h++){
                if(NoPodar(h,cota)){
                    if(esSolucion(h)){
                        if(esMejorSolucion(h,mejorSolucion)){
                            mejorSolucion=h;
                            cota=actualizar(cota,h);
                    }else{
                        vivo.agregar(h,h.cotainf);
                        cota=actualizar(cota,h);
                    }
                    }
                }
            }
        }
        return mejorSolucion.estado;
    } */
    
    
    public static void main(String[] args) {
        Resultado resultado = leerLineas();
        GrafoLA grafoD = leerRutas();
        imprimir(grafoD);
        
        int cl = resultado.cl;
        int cd = resultado.cd;
        int[] vpa = resultado.vpa;
        int[] CDP = resultado.CDP;
        int[] CFP = resultado.CFP;
        int[][] D = new int[cd][cl];
        
        System.out.println("Resultado: cl = " + cl + ", cd = " + cd);
        for (int i = 0; i < vpa.length; i++) {
            System.out.println("Posición: " + i + ", Valor: " + vpa[i]);
        }
        for (int i = 0; i < CDP.length; i++) {
            System.out.println("CDP Posición: " + i + ", Valor: " + CDP[i]);
        }
        for (int i = 0; i < CFP.length; i++) {
            System.out.println("CFP Posición: " + i + ", Valor: " + CFP[i]);
        }
        

        

        for(int i=0; i<cl;i++){
            R=Dijkstra(G,i);
            for(int j=0; j<cd;j++){
                D[j][i]=R.pesoArista(i+50,j);
            }

        }
        int[] CDconst= new int [100];
        //CDconst=minim(D,CFP);

        int[][] CICD = new int[cd][cl];
        for(int i=0;i<cl;i++){
            int mejorSol=99999;
            int Pos=-1;
            for(int j=0;j<cd;j++){
                if(CDconst[j]==1){
                    if (D[j][i]<mejorSol){
                        mejorSol=D[j][i];
                        CICD[j][i]=1;
                        if (Pos!=-1)
                            CICD[Pos][i]=0;
                        Pos=j;
                    }else{
                        CICD[j][i]=0;
                    }
                }else{
                    CICD[j][i]=0;
                }
            }
        }
    }
}