import java.io.*;
import java.util.*;
import adt.Implementations.GrafoLA;
import adt.Interfaces.ConjuntoTDA;

public class main {
    static class Resultado {
        int cl; // Cantidad de clientes
        int cd; // Cantidad de centros de distribución
        int[] vpa; // Vector de posiciones de los clientes
        int[] CDP; // Costos de distribución por centro
        int[] CFP; // Costos fijos por centro

        Resultado(int cl, int cd, int[] vpa, int[] CDP, int[] CFP) {
            this.cl = cl;
            this.cd = cd;
            this.vpa = vpa;
            this.CDP = CDP;
            this.CFP = CFP;
        }
    }

    // Método para leer los datos de los clientes y centros desde un archivo
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

    // Método para leer las rutas desde un archivo y construir el grafo
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

    // Método para imprimir el grafo
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

    // Implementación del algoritmo de Dijkstra para encontrar las distancias más cortas
    public static int[] dijkstra(GrafoLA grafo, int origen) {
        ConjuntoTDA vertices = grafo.vertices();
        HashSet<Integer> noVisitados = new HashSet<>();
        while (!vertices.conjuntoVacio()) {
            int v = vertices.elegir();
            vertices.sacar(v);
            noVisitados.add(v);
        }

        int numVertices = noVisitados.size();
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[origen] = 0;

        while (!noVisitados.isEmpty()) {
            int verticeActual = -1;
            int distanciaMinima = Integer.MAX_VALUE;

            for (int v : noVisitados) {
                if (distancias[v] < distanciaMinima) {
                    verticeActual = v;
                    distanciaMinima = distancias[v];
                }
            }

            noVisitados.remove(verticeActual);

            ConjuntoTDA todosVertices = grafo.vertices();
            while (!todosVertices.conjuntoVacio()) {
                int v = todosVertices.elegir();
                todosVertices.sacar(v);
                if (noVisitados.contains(v) && grafo.existeArista(verticeActual, v)) {
                    int nuevaDistancia = distancias[verticeActual] + grafo.pesoArista(verticeActual, v);
                    if (nuevaDistancia < distancias[v]) {
                        distancias[v] = nuevaDistancia;
                    }
                }
            }
        }

        return distancias;
    }

    public static void main(String[] args) {
        // Leer datos de clientes y centros
        Resultado resultado = leerLineas();
        int cl = resultado.cl;
        int cd = resultado.cd;
        int[] vpa = resultado.vpa;
        int[] CDP = resultado.CDP;
        int[] CFP = resultado.CFP;

        // Leer rutas y construir el grafo
        GrafoLA grafoD = leerRutas();
        int[][] D = new int[cd][cl];

        // Calcular las distancias mínimas desde cada centro a cada cliente
        for (int i = 0; i < cd; i++) {
            int[] R = dijkstra(grafoD, i + 50);
            for (int j = 0; j < cl; j++) {
                D[i][j] = R[j];
            }
        }

        // Calcular los costos de distribución
        for (int i = 0; i < cl; i++) {
            for (int j = 0; j < cd; j++) {
                D[j][i] = (D[j][i] * vpa[i]) + (CDP[j] * vpa[i]);
            }
        }

        // Obtener el estado de los centros utilizando backtracking
        int[] estado = backtracking.backtracking(D, CFP);
        System.out.println("Estado de los centros: " + Arrays.toString(estado));

        // Calcular el costo total
        int totalCost = 0;
        for (int cliente = 0; cliente < cl; cliente++) {
            int distanciaMinima = Integer.MAX_VALUE;
            for (int centro = 0; centro < cd; centro++) {
                if (estado[centro] == 1 && D[centro][cliente] < distanciaMinima) {
                    distanciaMinima = D[centro][cliente];
                }
            }
            totalCost += distanciaMinima;
        }
        for (int centro = 0; centro < cd; centro++) {
            if (estado[centro] == 1) {
                totalCost += CFP[centro];
            }
        }

        System.out.println("Costo total: " + totalCost);
    }
}