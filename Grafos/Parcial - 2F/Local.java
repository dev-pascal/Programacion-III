package prog3.grafos.utiles.parcial;

public class Local {
	private String nombre;
	private int cantPersonas;
	public Local() {}
	public Local (String nombre, int cantPersonas) {
		this.nombre=nombre;
		this.cantPersonas=cantPersonas;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre (String nombre) {
		this.nombre=nombre;
	}
	public int getPersonas () {
		return this.cantPersonas;
	}
	public void setPersonas (int cantPersonas) {
		this.cantPersonas=cantPersonas;
	}
}
