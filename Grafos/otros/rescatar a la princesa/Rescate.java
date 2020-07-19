package prog3.grafos.utiles.juego;

import prog3.listagenerica.ListaGenerica;
import prog3.listagenerica.ListaGenericaEnlazada;

public class Rescate {
	private int cantPrincesas;
	private ListaGenerica<ListaGenerica<Sitio>> caminosAccesibles; 
	public Rescate () {
		this.caminosAccesibles= new ListaGenericaEnlazada<ListaGenerica<Sitio>>();
	}
	public Rescate (ListaGenerica<ListaGenerica<Sitio>> caminosAccesibles, int cantPrincesas) {
		this.setCamino(caminosAccesibles);
		this.cantPrincesas=cantPrincesas;
	}
	public void setCamino (ListaGenerica<ListaGenerica<Sitio>> caminosAccesibles) {
		if (this.caminosAccesibles!=null) {
			while (!this.caminosAccesibles.esVacia())
				this.caminosAccesibles.eliminarEn(0);
			caminosAccesibles.comenzar();
			while (!caminosAccesibles.fin())
				this.caminosAccesibles.agregarFinal(caminosAccesibles.proximo());
		}
	}
	public ListaGenerica<ListaGenerica<Sitio>> getCamino () {
		return this.caminosAccesibles;
	}
	public void setCant (int cantPrincesas) {
		this.cantPrincesas=cantPrincesas;
	}
	public int getCant () {
		return this.cantPrincesas;
	}
}
