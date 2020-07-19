package prog3.grafos.utiles.parcial;

public class Ciudad {
	private String nombre;
	private int fase; 
	public Ciudad () {}
	public Ciudad (String nombre, int fase) {
		this.nombre=nombre;
		this.fase=fase;
	}
	public int getFase () {
		return this.fase;
	}
	public void setFase (int fase) {
		this.fase=fase;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre (String nombre) {
		this.nombre=nombre;
	}
}
