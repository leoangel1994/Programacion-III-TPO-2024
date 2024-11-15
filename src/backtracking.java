import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;

public class backtracking {
    static class Nodo {
        int[] estado; // Representa el estado actual del nodo
        int resultado; // Representa el valor del resultado asociado al nodo
    }

    static int[] backtracking(int[][] D, int[] CFP) {
        // Cola de prioridad para almacenar los nodos vivos, ordenados por su resultado
        PriorityQueue<Nodo> vivos = new PriorityQueue<>(Comparator.comparingInt(n -> n.resultado));
        Nodo n1 = crearNodoRaiz(D, CFP); // Crear el nodo raíz con el estado inicial
        vivos.add(n1); // Agregar el nodo raíz a la lista de nodos vivos
        Nodo mejorSolucion = null; // Inicializar la mejor solución como null

        while (!vivos.isEmpty()) { // Mientras haya nodos vivos
            Nodo nodo = vivos.poll(); // Obtener y eliminar el primer nodo de la lista de nodos vivos
            List<Nodo> hijos = generarHijos(nodo, D, CFP); // Generar los hijos del nodo actual

            for (Nodo h : hijos) { // Para cada hijo generado
                if (esSolucion(h)) { // Si el hijo es una solución
                    if (esMejorSolucion(h, mejorSolucion)) { // Si el hijo es una mejor solución que la actual
                        mejorSolucion = h; // Actualizar la mejor solución
                    }
                } else {
                    vivos.add(h); // Si no es una solución, agregar el hijo a la lista de nodos vivos
                }
            }
        }
        return mejorSolucion.estado; // Retornar el estado de la mejor solución encontrada
    }

    static Nodo crearNodoRaiz(int[][] D, int[] CFP) {
        Nodo nodoRaiz = new Nodo(); // Crear un nuevo nodo
        nodoRaiz.estado = new int[D.length]; // Inicializar el estado del nodo con la misma longitud que D
        Arrays.fill(nodoRaiz.estado, Integer.MAX_VALUE); // Llenar el estado con valores infinitos (Integer.MAX_VALUE)
        nodoRaiz.resultado = 0; // Inicializar el resultado del nodo como 0
        return nodoRaiz; // Retornar el nodo raíz
    }

    static List<Nodo> generarHijos(Nodo nodo, int[][] D, int[] CFP) {
        List<Nodo> hijos = new ArrayList<>(); // Crear una lista para almacenar los hijos generados

        for (int i = 0; i < 2; i++) { // Generar dos hijos para cada nodo (uno con 0 y otro con 1)
            Nodo hijo = new Nodo(); // Crear un nuevo nodo hijo
            hijo.estado = Arrays.copyOf(nodo.estado, nodo.estado.length); // Copiar el estado del nodo padre al hijo

            // Encontrar el primer valor Integer.MAX_VALUE en el estado y reemplazarlo con i
            for (int j = 0; j < hijo.estado.length; j++) {
                if (hijo.estado[j] == Integer.MAX_VALUE) {
                    hijo.estado[j] = i;
                    break;
                }
            }

            // Si todos los centros están cerrados, abrir el primer centro
            if (Arrays.stream(hijo.estado).allMatch(e -> e == 0)) {
                hijo.estado[0] = 1;
            }

            // Calcular el valor de 'resultado' para el hijo
            /* Calcula el resultado para cada hijo asignando a cada cliente el centro más cercano que está abierto, 
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
            hijos.add(hijo); // Agregar el hijo a la lista de hijos
        }

        return hijos; // Retornar la lista de hijos generados
    }

    static boolean esSolucion(Nodo nodo) {
        boolean centroAbierto = false; // Variable para verificar si hay al menos un centro abierto
        for (int i = 0; i < nodo.estado.length; i++) {
            if (nodo.estado[i] == 1) {
                centroAbierto = true; // Encontrar al menos un centro abierto
            }
            if (nodo.estado[i] == Integer.MAX_VALUE) {
                return false; // Si hay algún valor infinito, no es una solución válida
            }
        }
        return centroAbierto; // Retornar verdadero si hay al menos un centro abierto
    }

    static boolean esMejorSolucion(Nodo h, Nodo mejorSolucion) {
        if (mejorSolucion == null) {
            return true; // Si no hay mejor solución actual, cualquier solución es mejor
        }
        return h.resultado < mejorSolucion.resultado; // Comparar los resultados para determinar la mejor solución
    }
}