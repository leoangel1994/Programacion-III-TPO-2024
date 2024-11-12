package adt.implementation;

import adt.structures.Nodo;
import adt.interfaces.ConjuntoTDA;

public class ConjuntoLD implements ConjuntoTDA{
	
	Nodo primero;
	

	public boolean pertenece(int x) {
		Nodo aux = primero;
		while (aux != null && aux.info!=x){
			aux = aux.sig;
		}
		return aux!=null;
	}

	public int elegir() {
		return primero.info;
	}

	public void inicializarConjunto() {
		primero = null;
	}

	public void sacar(int x) {
		if (!this.conjuntoVacio()){
			if (primero.info == x){
				primero = primero.sig;
			}
			else{
				Nodo aux = primero;
				while (aux.sig!=null && aux.sig.info!=x)
					aux = aux.sig;
				if (aux.sig != null)
					aux.sig = aux.sig.sig;
			}
		}
	}

	public void agregar(int x) {
		if (!this.pertenece(x)){
			Nodo nuevo = new Nodo();
			nuevo.info=x;
			nuevo.sig=primero;
			primero = nuevo;
		}
	}

	public boolean conjuntoVacio() {
		return primero==null;
	}

}
