import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;

public class backtracking {
    static class Nodo {
        int[] estado;
        int resultado;
    }

    static int[] backtracking(int[][] D, int[] CFP) {
        PriorityQueue<Nodo> vivos = new PriorityQueue<>(Comparator.comparingInt(n -> n.resultado));
        Nodo n1 = crearNodoRaiz(D, CFP);
        vivos.add(n1);
        Nodo mejorSolucion = null;
        while (!vivos.isEmpty()) {
            Nodo nodo = vivos.poll();
            List<Nodo> hijos = generarHijos(nodo, D, CFP);
            for (Nodo h : hijos) {
                if (esSolucion(h)) {
                    if (esMejorSolucion(h, mejorSolucion)) {
                        mejorSolucion = h;
                    }
                } else {
                    vivos.add(h);
                }
            }
        }
        return mejorSolucion.estado;
    }

    static Nodo crearNodoRaiz(int[][] D, int[] CFP) {
        Nodo nodoRaiz = new Nodo();
        nodoRaiz.estado = new int[D.length]; // Estado con la misma longitud que D
        Arrays.fill(nodoRaiz.estado, Integer.MAX_VALUE); // Llenar el estado con infinitos
        nodoRaiz.resultado = 0; // Resultado es 0
        return nodoRaiz;
    }
    static List<Nodo> generarHijos(Nodo nodo, int[][] D, int[] CFP) {
        List<Nodo> hijos = new ArrayList<>();
    
        for (int i = 0; i < 2; i++) {
            Nodo hijo = new Nodo();
            hijo.estado = Arrays.copyOf(nodo.estado, nodo.estado.length);
            // Encuentra el primer valor Integer.MAX_VALUE en el estado y reemplázalo con i
            for (int j = 0; j < hijo.estado.length; j++) {
                if (hijo.estado[j] == Integer.MAX_VALUE) {
                    hijo.estado[j] = i;
                    break;
                }
            }
            // Si todos los centros están cerrados, abre el primer centro
            if (Arrays.stream(hijo.estado).allMatch(e -> e == 0)) {
                hijo.estado[0] = 1;
            }
            // Calcular el valor de 'resultado' para el hijo
            /*Calcula el resultado para cada hijo asignando a cada cliente el centro más cercano que está abierto, 
            sumando las distancias a estos centros y luego sumando el costo fijo de cada centro que está abierto. */
            hijo.resultado = 0;
            for (int cliente = 0; cliente < D[0].length; cliente++) {
                int distanciaMinima = Integer.MAX_VALUE;
                for (int centro = 0; centro < hijo.estado.length; centro++) {
                    if (hijo.estado[centro] == 1 && D[centro][cliente] < distanciaMinima) {
                        distanciaMinima = D[centro][cliente];
                    }
                }
                hijo.resultado += distanciaMinima;
            }
            for (int centro = 0; centro < hijo.estado.length; centro++) {
                if (hijo.estado[centro] == 1) {
                    hijo.resultado += CFP[centro];
                }
            }
            hijos.add(hijo);
        }

        return hijos;
    }

    static boolean esSolucion(Nodo nodo) {
        boolean centroAbierto = false;
        for (int i = 0; i < nodo.estado.length; i++) {
            if (nodo.estado[i] == 1) {
                centroAbierto = true;
            }
            if (nodo.estado[i] == Integer.MAX_VALUE) {
                return false;
            }
        }
        return centroAbierto;
    }

    static boolean esMejorSolucion(Nodo h, Nodo mejorSolucion) {
        if (mejorSolucion == null) {
            return true;
        }
        return h.resultado < mejorSolucion.resultado;
    }
}