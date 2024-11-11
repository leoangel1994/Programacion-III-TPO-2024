package adt.implementation;

import Interfaces.ColaTDA;

public class ColaArr implements ColaTDA{

	int[] cola;
	int indice;
	
	public void inicializarCola() {
		indice = 0;
		cola = new int[100];
	}

	public void acolar(int x) {
		cola[indice] = x;
		indice++;
	}

	public void desacolar() {
		for (int i=0;i<indice-1;i++){
			cola[i] = cola[i+1];
		}
		indice --;
	}

	public int primero() {
		return cola[0];
	}

	public boolean colaVacia() {
		return indice == 0;
	}

}
