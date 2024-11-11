package adt.interfaces;

public interface ColaTDA {
	
	public void inicializarCola();
	
	public void acolar(int x); // pila inicializada.
	
	public void desacolar (); // pila inicializada y no vacia.
	
	public int primero(); // pila inicializada y no vacia.
	
	public boolean colaVacia(); // pila inicializada.

}
