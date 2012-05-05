package com.touriXta;

import java.util.ArrayList;
import java.util.Date;

import com.google.android.maps.GeoPoint;

public class recorrido_inf_pol 
{
	ArrayList<GeoPoint> puntos;
	//ArrayList<Date> tiempos;
	String nombre;
	String codigo;
	
	final GeoPoint ReinaMercedes = new GeoPoint((int)(37.358088*1E6),(int)(-5.987817*1E6));
	final GeoPoint Remedios = new GeoPoint((int)(37.3766939*1E6),(int)(-6.0033106*1E6));
	final GeoPoint Cartuja = new GeoPoint((int)(37.411819*1E6),(int)(-6.000947*1E6));
	public recorrido_inf_pol(String nombre)
	{
		puntos = new ArrayList<GeoPoint>();
		//tiempos = new ArrayList<Date>();
		this.codigo = nombre;
		this.nombre = "rec_inf_pol_bus";
		/*Reina Mercedes ETSII*/
		puntos.add(new GeoPoint((int)(37.358088*1E6),(int)(-5.987817*1E6)));
		//tiempos.add(new Date(110,3,4,4,30));
		/*Remedios Poli*/
		puntos.add(new GeoPoint((int)(37.3766939*1E6),(int)(-6.0033106*1E6)));
		//tiempos.add(new Date(110,3,4,5,00));
		puntos.add(Cartuja);
	}
	public ArrayList<GeoPoint> getPuntos()
	{
		return puntos;
	}
//	public ArrayList<Date> getTiempos()
//	{
//		return tiempos;
//	}
}
