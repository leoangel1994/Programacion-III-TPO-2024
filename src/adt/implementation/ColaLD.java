package adt.implementation;

import Estructuras.Nodo;
import Interfaces.ColaTDA;

public class ColaLD implements ColaTDA{
	
	private Nodo primero;
	private Nodo ultimo;

	public void inicializarCola() {
		primero = null;
		ultimo = null;
	}

	public void acolar(int x) {
		Nodo aux = new Nodo();
		aux.info = x;
		aux.sig = null;
		if (ultimo != null){
			ultimo.sig = aux;
		}
		ultimo = aux;
		if (primero == null){
			primero = ultimo;
		}		
	}

	public void desacolar() {
		primero = primero.sig;
		if (primero == null){
			ultimo = null;
		}
	}

	public int primero() {
		return primero.info;
	}

	public boolean colaVacia() {
		return primero==null;
	}

}
