package adt.implementation;

import adt.structures.NodoClave;
import adt.interfaces.ConjuntoTDA;
import adt.interfaces.DiccionarioSimpleTDA;

public class DiccionarioSimpleLD implements DiccionarioSimpleTDA {

	NodoClave origen;
	
	public void inicializarDiccionario() {
		origen = null;
	}

	public void agregar(int clave, int valor) {
		NodoClave nuevo = clave2NodoClave(clave);
		if (nuevo == null){
			nuevo = new NodoClave();
			nuevo.clave = clave;
			nuevo.sig = origen;
			origen = nuevo;
		}
		nuevo.valor=valor;
	}
	
	private NodoClave clave2NodoClave (int clave){
		NodoClave aux = origen;
		while (aux != null && aux.clave!=clave){
			aux = aux.sig;
		}
		return aux;
	}

	public void eliminar(int clave) {
		if (origen != null){
			if (origen.clave == clave){
				origen = origen.sig;
			}
			else{
				NodoClave aux = origen;
				while (aux.sig != null && aux.sig.clave != clave)
					aux = aux.sig;
				if (aux.sig != null)
					aux.sig = aux.sig.sig;
			}
		}
	}

	public int recuperar(int clave) {
		NodoClave nodo = clave2NodoClave(clave);
		return nodo.valor;
	}

	public ConjuntoTDA claves() {
		ConjuntoTDA claves = new ConjuntoLD();claves.inicializarConjunto();
		NodoClave aux = origen;
		while (aux!=null){
			claves.agregar(aux.clave);
			aux=aux.sig;
		}
		return claves;
	}

}
