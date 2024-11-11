package adt.implementation;

import Estructuras.Nodo;
import Estructuras.NodoClaveMultiple;
import Interfaces.ConjuntoTDA;
import Interfaces.DiccionarioMultipleTDA;

public class DiccionarioMultipleLD implements DiccionarioMultipleTDA{
	
	NodoClaveMultiple origen;

	public void inicializarDiccionario() {
		origen = null;
	}

	public void agregar(int clave, int valor) {
		NodoClaveMultiple nc = clave2NodoClaveMultpile(clave);
		if (nc == null){
			nc = new NodoClaveMultiple();
			nc.clave = clave;
			nc.sig = origen;
			origen = nc.sig;
		}
		
		Nodo nv = nc.valores;
		while (nv!=null && nv.info!=valor){
			nv = nv.sig;
		}
		if (nv == null){
			nv = new Nodo();
			nv.info=valor;
			nv.sig = nc.valores;
			nc.valores = nv;
		}
	}

	private NodoClaveMultiple clave2NodoClaveMultpile(int clave) {
		NodoClaveMultiple aux = origen;
		while (aux.clave!=clave && aux != null)
			aux = aux.sig;
		return aux;
	}

	public void eliminar(int clave) {
		if (origen.clave == clave)
			origen = origen.sig;
		else{
			NodoClaveMultiple aux = origen;
			while (origen.sig != null && origen.sig.clave!=clave){
				origen = origen.sig;
			}
			if (origen.sig != null)
				origen.sig = origen.sig.sig;
		}
	}

	public void eliminarValor(int clave, int valor) {
		if (origen != null){
			if (origen.clave == clave){
				eliminarValorDeNodo (origen, valor);
				if (origen.valores == null){
					origen = origen.sig;
				}
			}
			else{
				NodoClaveMultiple aux = origen;
				while (aux.sig != null && aux.sig.clave != clave){
					aux = aux.sig;
				}
				if (aux.sig != null){
					eliminarValorDeNodo(aux.sig, valor);
					if (aux.sig.valores == null){
						aux.sig = aux.sig.sig;
					}
				}
			}
		}
	}

	private void eliminarValorDeNodo(NodoClaveMultiple nc, int valor) {
		if (nc.valores.info == valor){
			nc.valores = nc.valores.sig;
		}
		else{
			Nodo nv = nc.valores;
			while (nv.sig != null && nv.sig.info != valor)
				nv = nv.sig;
			if (nv.sig != null){
				nv.sig = nv.sig.sig;
			}
		}
	}

	public ConjuntoTDA recuperar(int clave) {
		ConjuntoTDA valores = new ConjuntoLD(); valores.inicializarConjunto();
		NodoClaveMultiple nc = clave2NodoClaveMultpile(clave);
		if (nc != null){
			Nodo nv = nc.valores;
			while (nv != null){
				valores.agregar(nv.info);
				nv = nv.sig;
			}
		}
		return valores;
	}

	public ConjuntoTDA claves() {
		ConjuntoTDA claves = new ConjuntoLD(); claves.inicializarConjunto();
		NodoClaveMultiple aux = origen;
		while (aux != null){
			claves.agregar(aux.clave);
			aux=aux.sig;
		}
		return claves;
	}

}
