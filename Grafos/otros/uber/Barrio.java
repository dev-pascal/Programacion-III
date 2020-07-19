public class Barrio {
	private String nombre;
	private boolean tieneUber;
	private boolean tienePolicia;
	private Uber uber;
	public Barrio () {
	}
	public Barrio (String nombre, boolean tieneUber, boolean tienePolicia, Uber uber) {
		this.nombre=nombre;
		this.tieneUber=tieneUber;
		this.tienePolicia=tienePolicia;
		this.uber.setNombre(uber.getNombre());
		this.uber.setReputacion(uber.getReputacion());
		this.uber.setCombustible(uber.getCombustible());
	}
	public void setPolicia (boolean tienePolicia) {
		this.tienePolicia=tienePolicia;
	}
	public boolean tienePolicia () {
		return this.tienePolicia;
	}
	public void setUber (boolean tieneUber) {
		this.tieneUber=tieneUber;
	}
	public boolean getUber () {
		return this.tieneUber;
	}
	public void setUberObject (Uber uber) {
		this.uber.setNombre(uber.getNombre());
		this.uber.setReputacion(uber.getReputacion());
		this.uber.setCombustible(uber.getCombustible());
	}
	public Uber getUberObject () {
		return this.uber;
	}
}
