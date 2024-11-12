package adt.implementation;

import adt.structures.NodoArista;
import adt.structures.NodoGrafo;
import adt.interfaces.ConjuntoTDA;
import adt.interfaces.GrafoTDA;

public class GrafoLA implements GrafoTDA{

	NodoGrafo origen;
	
	public void inicializarGrafo() {
		origen = null;
	}

	public void agregarVertice(int v) {
		NodoGrafo nuevo = new NodoGrafo();
		nuevo.nodo = v;
		nuevo.arista = null;
		nuevo.sigNodo = origen;
		origen = nuevo;
	}

	public void eliminarVertice(int v) {
		if (origen.nodo == v)
			origen = origen.sigNodo;
		else{
			NodoGrafo aux = origen;
			while (aux != null){
				eliminarArista(aux.nodo, v);
				if (aux.sigNodo != null && aux.sigNodo.nodo == v)
					aux.sigNodo = aux.sigNodo.sigNodo;
				aux = aux.sigNodo;
			}
		}
	}

	public ConjuntoTDA vertices() {
		ConjuntoTDA vertices = new ConjuntoLD(); vertices.inicializarConjunto();
		NodoGrafo aux = origen;
		while (aux != null){
			vertices.agregar(aux.nodo);
			aux = aux.sigNodo;
		}
		return vertices;
	}

	public void agregarArista(int vo, int vd, int peso) {
		NodoGrafo o = vert2nodo (vo);
		NodoGrafo d = vert2nodo (vd);
		NodoArista nuevo = new NodoArista();
		nuevo.peso = peso;
		nuevo.destino = d;
		nuevo.sigArista = o.arista;
		o.arista = nuevo;
	}

	private NodoGrafo vert2nodo(int vo) {
		NodoGrafo aux = origen;
		while (aux != null && aux.nodo != vo){
			aux = aux.sigNodo;
		}
		return aux;
	}

	public void eliminarArista(int vo, int vd) {
		NodoGrafo origen = vert2nodo(vo);
		NodoArista aux = origen.arista;
		if (aux.destino.nodo == vd){
			origen.arista = origen.arista.sigArista;
		}
		else{
			while (aux.sigArista.destino.nodo != vd)
				aux = aux.sigArista;
			aux.sigArista = aux.sigArista.sigArista;
		}
	}

	public int pesoArista(int vo, int vd) {
		NodoGrafo origen = vert2nodo(vo);
		NodoArista aux = origen.arista;
		while (aux.destino.nodo != vd){
			aux = aux.sigArista;
		}
		return aux.peso;
	}

	public boolean existeArista(int vo, int vd) {
		NodoGrafo origen = vert2nodo(vo);
		NodoArista aux = origen.arista;
		while (aux != null && aux.destino.nodo != vd)
			aux = aux.sigArista;
		return aux != null;
	}

}
