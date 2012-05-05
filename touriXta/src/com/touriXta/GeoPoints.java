package com.touriXta;

import com.google.android.maps.GeoPoint;


public class GeoPoints 
{
	public String name;
	public int latitude;
	public int longitude;
	
	public GeoPoints(String name,int latitude,int longitude)
	{
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	
	}
	private GeoPoint creaGeoPoint()
	{
		GeoPoint gp = new GeoPoint((int)(latitude),(int)(longitude));
		return gp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	
	
}
