package adt.implementation;

import adt.interfaces.ConjuntoTDA;
import adt.interfaces.GrafoTDA;

public class GrafoMA implements GrafoTDA{
	
	int[][] mAdy;
	int[] etiquetas;
	int cantNodos;
	static int N = 100;

	public void inicializarGrafo() {
		mAdy = new int[N][N];
		etiquetas = new int [N];
		cantNodos = 0;
	}

	public void agregarVertice(int v) {
		etiquetas[cantNodos] = v;
		for (int i = 0; i<=cantNodos ; i++){
			mAdy[cantNodos][i] = 0;
			mAdy[i][cantNodos] = 0;
		}
		cantNodos++;
	}

	public void eliminarVertice(int v) {
		int ind = vert2ind(v);
		for (int i=0; i<cantNodos; i++){
			mAdy[ind][i]=0;
		}
		for (int i=0; i<cantNodos; i++){
			mAdy[i][ind]=0;
		}
		etiquetas[ind] = etiquetas[cantNodos-1];
		cantNodos--;
	}

	public ConjuntoTDA vertices() {
		ConjuntoTDA vertices = new ConjuntoLD(); vertices.inicializarConjunto();
		for (int i = 0; i<cantNodos; i++){
			vertices.agregar(etiquetas[i]);
		}
		return vertices;
	}

	public void agregarArista(int vo, int vd, int peso) {
		int o = vert2ind (vo);
		int d = vert2ind (vd);
		mAdy[o][d] = peso;
	}

	private int vert2ind(int vo) {
		int pos = cantNodos-1;
		while (pos>=0 && etiquetas[pos]!=vo)
			pos--;
		return pos;
	}

	public void eliminarArista(int vo, int vd) {
		int o = vert2ind (vo);
		int d = vert2ind (vd);
		mAdy[o][d]=0;
	}

	public int pesoArista(int vo, int vd) {
		int o = vert2ind (vo);
		int d = vert2ind (vd);
		return mAdy[o][d];
	}

	public boolean existeArista(int vo, int vd) {
		int o = vert2ind (vo);
		int d = vert2ind (vd);
		return mAdy[o][d]!=0;
	}

}
