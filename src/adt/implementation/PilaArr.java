package adt.implementation;

import Interfaces.PilaTDA;

public class PilaArr implements PilaTDA{

	int[] pila;
	int indice;
	
	public void inicializarPila() {
		indice = 0;
		pila = new int[100];
	}

	public void apilar(int x) {
		pila[indice]=x;
		indice++;
	}

	public int tope() {
		return pila[indice-1];
	}

	public void desapilar() {
		indice--;
	}

	public boolean pilaVacia() {
		return indice == 0;
	}

}
