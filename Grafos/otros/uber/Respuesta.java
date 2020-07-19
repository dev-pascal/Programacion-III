public class Respuesta {
	private ListaGenerica<String> camino;
	private ListaGenerica<String> conductores;
	public Respuesta () {
		camino = new ListaGenericaEnlazada<String>();
		conductores = new ListaGenericaEnlazada<String>();
	}
	public void setLista (ListaGenerica<String> act, ListaGenerica<String> nue) {
		if (act!=null) {
			while (!act.esVacia()) {
				act.eliminarEn(0);
			}
			nue.comenzar();
			while (!nue.fin()) {
				act.agregarFinal(nue.proximo());
			}
		}
		else {
			act=nue;
		}
	}
	public Respuesta (ListaGenerica<String> camino, ListaGenerica<String> conductores) {
		this.setLista(this.camino, camino);
		this.setLista(this.conductores, conductores);				
	}
}
