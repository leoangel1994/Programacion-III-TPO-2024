package adt.implementation;

import Estructuras.Elemento;
import Interfaces.ColaPrioridadTDA;

public class ColaPrioridadArr implements ColaPrioridadTDA{

	Elemento[] cola;
	int indice;
			
	public void inicializarCola() {
		indice = 0;
		cola = new Elemento[100];
	}

	public int primero() {
		return cola[indice-1].info;
	}

	public void desacolar() {
		cola[indice-1] = null;
		indice--;
	}

	public void acolarPrioridad(int x, int prioridad) {
		int i = indice;
		Elemento nuevo = new Elemento();
		nuevo.info=x;
		nuevo.prioridad=prioridad;
		while (i>0 && cola[i-1].prioridad>=nuevo.prioridad){
			cola[i] = cola[i-1];
			i--;
		}
		cola[i]=nuevo;
	}

	public boolean colaVacia() {
		return indice == 0;
	}

	public int prioridad() {
		return cola[indice-1].prioridad;
	}
	

}
