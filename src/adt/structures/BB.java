package adt.structures;

import java.util.PriorityQueue;

public class BB {
        static Nodo CrearNodoRaiz(int[] c, int[] CFP) {
        // implementar lógica para crear nodo raíz
    }
    static Nodo primero(PriorityQueue<Nodo> vivos) {
        // implementar lógica para obtener el primer nodo
    }
    static Nodo[] generarHijos(Nodo nodo, int[] c, int[] CFP) {
        // implementar lógica para generar hijos
    }
    static boolean NoPodar(Nodo h, int cota) {
        // implementar lógica para determinar si podar o no
    }
    static boolean esSolucion(Nodo h) {
        // implementar lógica para determinar si un nodo es una solución
    }
    static boolean esMejorSolucion(Nodo h, Nodo mejorSolucion) {
        // implementar lógica para determinar si un nodo es una mejor solución
    }
    static int actualizar(int cota, Nodo h) {
        // implementar lógica para actualizar la cota
    }
}
