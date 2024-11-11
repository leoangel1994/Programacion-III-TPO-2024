package adt.implementation;

import Estructuras.Nodo;
import Interfaces.PilaTDA;

public class PilaLD implements PilaTDA{

	private Nodo primero;
		
	
	public void inicializarPila() {
		primero = null;
	}

	public void apilar(int x) {
		Nodo aux = new Nodo();
		aux.info = x;
		aux.sig = primero;
		primero = aux;
	}

	public int tope() {
		return primero.info;
	}

	public void desapilar() {
		primero = primero.sig;
	}

	public boolean pilaVacia() {
		return (primero==null);
	}

}
