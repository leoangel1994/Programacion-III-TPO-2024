package adt.implementation;

import adt.structures.NodoPrioridad;
import adt.interfaces.ColaPrioridadTDA;

public class ColaPrioridadLD implements ColaPrioridadTDA{
	
	NodoPrioridad mayorPrioridad;

	public void inicializarCola() {
		mayorPrioridad = null;
	}

	public int primero() {
		return mayorPrioridad.elemento.info;
	}

	public void desacolar() {
		mayorPrioridad = mayorPrioridad.sig;
	}

	public void acolarPrioridad(int x, int prioridad) {
		NodoPrioridad nuevo = new NodoPrioridad();
		nuevo.elemento.info=x;
		nuevo.elemento.prioridad=prioridad;
		if (mayorPrioridad!=null){
			if (mayorPrioridad.elemento.prioridad<prioridad){
				nuevo.sig=mayorPrioridad;
				mayorPrioridad = nuevo;
			}
			else{
				NodoPrioridad aux = mayorPrioridad;
				while (aux.sig != null && prioridad <= aux.sig.elemento.prioridad){
					aux = aux.sig;
				}
				nuevo.sig = aux.sig;
				aux.sig = nuevo;
			}
		}
		else{
			mayorPrioridad = nuevo;
			nuevo.sig=null;
		}
	}

	public boolean colaVacia() {
		return mayorPrioridad == null;
	}

	public int prioridad() {
		return mayorPrioridad.elemento.prioridad;
	}

}
