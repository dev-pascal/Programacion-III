package prog3.grafos.utiles.juego;

public class Sitio {
	private String nombre;
	private boolean esDragon;
	private boolean esPrincesa;
	public Sitio () {
	}
	public Sitio (String nombre, boolean esDragon, boolean esPrincesa) {
		this.nombre=nombre;
		this.esDragon=esDragon;
		this.esPrincesa=esPrincesa;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	public boolean esDragon () {
		return this.esDragon;
	}
	public void setDragon (boolean esDragon) {
		this.esDragon=esDragon;
	}
	public boolean esPrincesa () {
		return this.esPrincesa;
	}
	public void setPrincesa (boolean esPrincesa) {
		this.esPrincesa=esPrincesa;
	}
}
