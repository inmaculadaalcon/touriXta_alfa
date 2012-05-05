package com.touriXta;

import java.util.List;

public class Dato
{
	private String ciudad;
	private List<Monumento> monumentos;
	private List<Escuela> escuelas;
	private List<Estadio> estadios;
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public List<Monumento> getMonumentos() {
		return monumentos;
	}
	public void setMonumentos(List<Monumento> monumentos) {
		this.monumentos = monumentos;
	}
	public List<Escuela> getEscuelas() {
		return escuelas;
	}
	public void setEscuelas(List<Escuela> escuelas) {
		this.escuelas = escuelas;
	}
	public List<Estadio> getEstadios() {
		return estadios;
	}
	public void setEstadios(List<Estadio> estadios) {
		this.estadios = estadios;
	}
	
	
}
