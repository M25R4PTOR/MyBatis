package com;

public class Campo {

	private Integer id;

	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Campo(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Campo() {
		super();
	}

	@Override
	public String toString() {
		return "Campo [id=" + id + ", nombre=" + nombre + "]";
	}

}
