package prog3.grafos.utiles;

public class Sitio {
	private String nombre;
	private boolean tieneMafia;
	public Sitio() {}
	public Sitio(String nombre, boolean tieneMafia) {
		this.nombre=nombre;
		this.tieneMafia=tieneMafia;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre (String nombre) {
		this.nombre=nombre;
	}
	public boolean getMafia () {
		return this.tieneMafia;
	}
	public void setMafia (boolean tieneMafia) {
		this.tieneMafia=tieneMafia;
	}
}
