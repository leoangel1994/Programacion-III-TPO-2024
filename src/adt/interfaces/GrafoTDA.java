package adt.interfaces;

public interface GrafoTDA {
	
	public void inicializarGrafo();
	
	public void agregarVertice(int v); // grafo inicializado y no exista el nodo
	
	public void eliminarVertice(int v); // grafo inicializado y exista el nodo
	
	public ConjuntoTDA vertices(); // grafo inicializado
	
	public void agregarArista(int vo, int vd, int peso); //grafo inicializado, no existe arista pero si ambos nodos.
	
	public void eliminarArista (int vo, int vd); // grafo inicializado y existe la arista.
	
	public int pesoArista (int vo, int vd); // grafo inicializado y existen los nodos.
	
	public boolean existeArista (int vo, int vd); // grafo inicializado y existen los nodos.

}
