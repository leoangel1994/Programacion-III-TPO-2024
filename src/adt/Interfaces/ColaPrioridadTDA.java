package adt.Interfaces;

public interface ColaPrioridadTDA {

	public void inicializarCola();
	
	public int primero();
	
	public void desacolar();
	
	public void acolarPrioridad (int x, int prioridad);
	
	public boolean colaVacia();
	
	public int prioridad();
}
