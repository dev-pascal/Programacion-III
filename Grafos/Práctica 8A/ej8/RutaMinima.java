package prog3.grafos.utiles;

import prog3.listagenerica.ListaGenerica;

public class RutaMinima {
	private ListaGenerica<String> camino;
	private boolean unicoBoleto;
	
	public RutaMinima () {
	}
	
	public void setCamino (ListaGenerica<String> camino) {
		while (!this.camino.esVacia())
			this.camino.eliminarEn(0);
		camino.comenzar();
		while (!camino.fin())
			this.camino.agregarFinal(camino.proximo());
	}
	
	public RutaMinima (ListaGenerica<String> camino, boolean unicoBoleto) {
		this.setCamino(camino);
		this.unicoBoleto=unicoBoleto;
	}
	
	public ListaGenerica<String> getCamino () {
		return this.camino;
	}
	
	public boolean getBoleto () {
		return this.unicoBoleto;
	}
	
	public void setBoleto (boolean unicoBoleto) {
		this.unicoBoleto=unicoBoleto;
	}
}
