package com.touriXta;

public class Estadio 
{
	private String name;
	private String descripcion_breve;
	private String descripcion;
	
	public Estadio(){}
	public Estadio(String name,String desc,String desc_breve)
	{
		this.name = name;
		this.descripcion = desc;
		this.descripcion_breve = desc_breve;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripcion_breve() {
		return descripcion_breve;
	}
	public void setDescripcion_breve(String descripcionBreve) {
		descripcion_breve = descripcionBreve;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
