package adt.interfaces;

public interface DiccionarioSimpleTDA {
	
	public void inicializarDiccionario();
	
	public void agregar (int clave, int valor); //siempre que este incializado.
	
	public void eliminar (int clave); // siempre que este inicializado.
	
	public int recuperar (int clave); // siempre que este inicializado y la clave exista en  el mismo.
	
	public ConjuntoTDA claves(); // siempre que este inicializado.

}
