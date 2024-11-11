package adt.implementation;

import Estructuras.NodoABB;
import Interfaces.ABBTDA;

public class ABB implements ABBTDA{

	NodoABB raiz;
	
	public int raiz() {
		return raiz.info;
	}

	public ABBTDA hijoIzq() {
		return raiz.hijoIzq;
	}

	public ABBTDA hijoDer() {
		return raiz.hijoDer;
	}

	public boolean arbolVacio() {
		return raiz == null;
	}

	public void inicializarAbb() {
		raiz = null;
	}

	public void agregarElem(int x) {
		if (this.arbolVacio()){
			raiz = new NodoABB();
			raiz.info=x;
			raiz.hijoDer = new ABB();
			raiz.hijoDer.inicializarAbb();
			raiz.hijoIzq = new ABB();
			raiz.hijoIzq.inicializarAbb();
		}
		else if (raiz.info > x){
			raiz.hijoIzq.agregarElem(x);
		}
		else if (raiz.info < x){
			raiz.hijoDer.agregarElem(x);
		}
	}

	public void eliminarElem(int x) {
		if (raiz != null){
			if (raiz.info == x && raiz.hijoDer.arbolVacio() && raiz.hijoIzq.arbolVacio()){
				raiz = null;
			}
			else if (raiz.info == x && !raiz.hijoIzq.arbolVacio()){
				raiz.info = mayor (raiz.hijoIzq);
				raiz.hijoIzq.eliminarElem(raiz.info);
			}
			else if (raiz.info == x && raiz.hijoIzq.arbolVacio()){
				raiz.info = menor (raiz.hijoDer);
				raiz.hijoDer.eliminarElem(raiz.info);
			}
			else if (raiz.info > x)
				raiz.hijoIzq.eliminarElem(x);
			else
				raiz.hijoDer.eliminarElem(x);
		}
	}

	private int menor(ABBTDA a) {
		if (a.hijoIzq().arbolVacio())
			return a.raiz();
		else
			return menor (a.hijoIzq());
	}

	private int mayor(ABBTDA a) {
		if (a.hijoDer().arbolVacio())
			return a.raiz();
		else
			return mayor(a.hijoDer());
	}

}
