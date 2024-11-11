package adt.interfaces;

public interface DiccionarioMultipleTDA {
	
	public void inicializarDiccionario();
	
	public void agregar (int clave, int valor); // siempre que este inicializado.
	
	public void eliminar (int clave); // siempre que este inicializado.
	
	public void eliminarValor (int clave, int valor); // siempre que este inicializado.
	
	public ConjuntoTDA recuperar (int clave); // siempre que este inicializado.
	
	public ConjuntoTDA claves (); // siempre que este inicializado.

}
