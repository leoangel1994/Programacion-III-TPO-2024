package adt.interfaces;

public interface ABBTDA {

	public int raiz (); // arbol inicializado y no vacio
	
	public ABBTDA hijoIzq (); // arbol inicializado y no vacio
	
	public ABBTDA hijoDer (); // arbol inicializado y no vacio
	
	public boolean arbolVacio(); // arbol inicializado
	
	public void inicializarAbb();
	
	public void agregarElem (int x); // arbol inicializado 
	
	public void eliminarElem (int x); // arbol inicializado 
	
}
