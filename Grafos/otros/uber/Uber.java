public class Uber {
	private String nombre;
	private int reputacion;
	private int combustible;
	public Uber () {
	}
	public Uber(String nombre, int reputacion, int combustible) {
		this.nombre=nombre;
		this.reputacion=reputacion;
		this.combustible=combustible;
	}
	public String getNombre () {
		return this.nombre;
	}
	public void setNombre (String nombre) {
		this.nombre=nombre;
	}
	public int getReputacion () {
		return this.reputacion;
	}
	public void setReputacion (int reputacion) {
		this.reputacion=reputacion;
	}
	public int getCombustible () {
		return this.combustible;
	}
	public void setCombustible (int combustible) {
		this.combustible=combustible;
	}
}