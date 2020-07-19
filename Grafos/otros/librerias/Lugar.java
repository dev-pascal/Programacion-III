public class Lugar {
	private String nombre;
	private boolean esLibreria;
	public Lugar () {
	}
	public Lugar (String nombre, boolean esLibreria) {
		this.nombre=nombre;
		this.esLibreria=esLibreria;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre (String nombre) {
		this.nombre=nombre;
	}
	public boolean esLibreria() {
		return this.esLibreria;	
	}
	public void setLibreria (boolean esLibreria) {
		this.esLibreria=esLibreria;
	}
}
