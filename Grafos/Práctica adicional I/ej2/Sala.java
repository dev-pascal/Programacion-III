package prog3.grafos.utiles.juego;

public class Sala {
	private String nombre;
	private int minutos;
	public Sala () {}
	public Sala (String nombre, int minutos) {
		this.nombre=nombre;
		this.minutos=minutos;
	}
	public String getNombre () {
		return this.nombre;
	}
	public int getMin () {
		return this.minutos;
	}
}
