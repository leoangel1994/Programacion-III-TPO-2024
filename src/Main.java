import adt.structures.Nodo;
import adt.interfaces.ConjuntoTDA;
import adt.implementation.GrafoLA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

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

        // Implementar Dijkstra para encontrar el costo mínimo de transporte
        for (int i = 0; i < cl; i++) {
            Map<Integer, Integer> distancias = dijkstra(grafoD, i);
            for (int j = 0; j < cd; j++) {
                D[j][i] = distancias.getOrDefault(j + 50, Integer.MAX_VALUE);
            }
        }

        // Optimización para determinar qué centros de distribución construir
        int[] CDconst = new int[cd];
        Arrays.fill(CDconst, 0);
        int[] mejorSolucion = new int[cd];
        Arrays.fill(mejorSolucion, 0);
        int[] mejorAsignacion = new int[cl];
        Arrays.fill(mejorAsignacion, -1);
        int costoMinimo = Integer.MAX_VALUE;

        backtracking(0, CDconst, mejorSolucion, mejorAsignacion, D, CFP, CDP, vpa, 0, costoMinimo);

        // Mostrar resultados
        for (int i = 0; i < cd; i++) {
            if (mejorSolucion[i] == 1) {
                System.out.println("Centro de distribución " + i + " construido.");
            }
        }
        for (int i = 0; i < cl; i++) {
            System.out.println("Cliente " + i + " asignado al centro de distribución " + mejorAsignacion[i]);
        }
    }

    // Implementación del algoritmo de Dijkstra
    public static Map<Integer, Integer> dijkstra(GrafoLA grafo, int start) {
        Map<Integer, Integer> dist = new HashMap<>();
        PriorityQueue<Ruta> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.peso));
        pq.add(new Ruta(start, start, 0));
        dist.put(start, 0);

        while (!pq.isEmpty()) {
            Ruta current = pq.poll();
            int currentNode = current.destino;

            ConjuntoTDA adyacentes = grafo.adyacentes(currentNode);
            while (!adyacentes.conjuntoVacio()) {
                int vecino = adyacentes.elegir();
                adyacentes.sacar(vecino);
                int newDist = dist.get(currentNode) + grafo.pesoArista(currentNode, vecino);
                if (newDist < dist.getOrDefault(vecino, Integer.MAX_VALUE)) {
                    dist.put(vecino, newDist);
                    pq.add(new Ruta(currentNode, vecino, newDist));
                }
            }
        }
        return dist;
    }

    // Implementación del algoritmo de backtracking
    public static void backtracking(int nivel, int[] CDconst, int[] mejorSolucion, int[] mejorAsignacion, int[][] D, int[] CFP, int[] CDP, int[] vpa, int costoActual, int costoMinimo) {
        if (nivel == CDconst.length) {
            int costoTotal = calcularCostoTotal(CDconst, D, CFP, CDP, vpa);
            if (costoTotal < costoMinimo) {
                costoMinimo = costoTotal;
                System.arraycopy(CDconst, 0, mejorSolucion, 0, CDconst.length);
                asignarClientes(CDconst, mejorAsignacion, D, vpa);
            }
            return;
        }

        // No construir el centro de distribución en el nivel actual
        CDconst[nivel] = 0;
        backtracking(nivel + 1, CDconst, mejorSolucion, mejorAsignacion, D, CFP, CDP, vpa, costoActual, costoMinimo);

        // Construir el centro de distribución en el nivel actual
        CDconst[nivel] = 1;
        backtracking(nivel + 1, CDconst, mejorSolucion, mejorAsignacion, D, CFP, CDP, vpa, costoActual + CFP[nivel], costoMinimo);
    }

    public static int calcularCostoTotal(int[] CDconst, int[][] D, int[] CFP, int[] CDP, int[] vpa) {
        int costoTotal = 0;
        for (int i = 0; i < CDconst.length; i++) {
            if (CDconst[i] == 1) {
                costoTotal += CFP[i];
            }
        }
        for (int i = 0; i < vpa.length; i++) {
            int mejorCosto = Integer.MAX_VALUE;
            for (int j = 0; j < CDconst.length; j++) {
                if (CDconst[j] == 1) {
                    int costo = D[j][i] * vpa[i] + CDP[j] * vpa[i];
                    if (costo < mejorCosto) {
                        mejorCosto = costo;
                    }
                }
            }
            costoTotal += mejorCosto;
        }
        return costoTotal;
    }

    public static void asignarClientes(int[] CDconst, int[] mejorAsignacion, int[][] D, int[] vpa) {
        for (int i = 0; i < vpa.length; i++) {
            int mejorCosto = Integer.MAX_VALUE;
            int mejorCentro = -1;
            for (int j = 0; j < CDconst.length; j++) {
                if (CDconst[j] == 1) {
                    int costo = D[j][i] * vpa[i];
                    if (costo < mejorCosto) {
                        mejorCosto = costo;
                        mejorCentro = j;
                    }
                }
            }
            mejorAsignacion[i] = mejorCentro;
        }
    }
}