public class Respuesta {
	private String nombreLibreria;
	private ListaGenerica<Lugar> caminoMinimo;
	public Respuesta () {
		this.caminoMinimo=new ListaGenericaEnlazada<>();
	}
	public Respuesta (String nombreLibreria, ListaGenerica<Lugar> caminoMinimo) {
		this.nombreLibreria=nombreLibreria;
		this.setCamino(caminoMinimo);
	}
	public void setCamino (ListaGenerica<Lugar> caminoMinimo) {
		if (this.caminoMinimo!=null) {
			while (!this.caminoMinimo.esVacia()) {
				this.caminoMinimo.eliminarEn(0);
			}	
			caminoMinimo.comenzar();
			while (!caminoMinimo.fin()) {
				this.caminoMinimo.agregarFinal(caminoMinimo.proximo());
			}
		}
		else this.caminoMinimo=caminoMinimo;
	}
	public ListaGenerica<Lugar> getCamino () {
		return this.caminoMinimo;
	}
	public String getNombre () {
		return this.nombreLibreria;
	}
	public void setNombre (String nombreLibreria) {
		this.nombreLibreria=nombreLibreria;
	}
}