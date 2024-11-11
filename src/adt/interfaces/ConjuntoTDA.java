package adt.interfaces;

public interface ConjuntoTDA {
	
	public boolean pertenece (int x); //conjunto inicalizado
	
	public int elegir(); // conjunto incializado y no vacio
	
	public void inicializarConjunto();
	
	public void sacar(int x); // conjunto inicializado
	
	public void agregar (int x); // conjutno incializado
	
	public boolean conjuntoVacio(); // conjunto inicializado.

}
