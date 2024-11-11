package adt.interfaces;

public interface PilaTDA {
	
	public void inicializarPila ();
	
	public void apilar (int x); //la pila debe estar inicializada
	
	public int tope(); // la pila debe estar inicializada y no vacia
	
	public void desapilar (); // la pila debe estar inicializada y no vacia
	
	public boolean pilaVacia(); // la pila debe estar incializada

}
