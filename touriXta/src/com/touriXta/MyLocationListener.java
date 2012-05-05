package com.touriXta;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener{
	private Context context;
	private LocationManager locationManager;
	private MapController mapController;
	
	public MyLocationListener(MapController mapController,LocationManager locationManager,Context context)
	{
		this.mapController = mapController;
		this.locationManager = locationManager;
		this.context = context;
	}
	
	public void onLocationChanged(Location loc)
	{
		Toast.makeText(context,"onLocationChanged", Toast.LENGTH_SHORT).show();
		
		if(loc != null )
		{
			Toast.makeText(context,"onLocationChanged: Lat:"+loc.getLatitude()+" Lng:"+loc.getLongitude(), Toast.LENGTH_SHORT).show();
			GeoPoint point = new GeoPoint((int)(loc.getLatitude()*1E6),(int)(loc.getLongitude()*1E6));
			mapController.animateTo(point);
			mapController.setZoom(20);
		
		}else{
			Toast.makeText(context, "location changed to null value",Toast.LENGTH_SHORT).show();
		}
		
		locationManager.removeUpdates((LocationListener) this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0,new MyLocationListener(mapController,locationManager,context));
		
	}
	
	public void onProviderDisabled(String str)
	{
		Toast.makeText(context, "Provider Disabled",Toast.LENGTH_SHORT).show();
	}
	
	public void onProviderEnabled(String str)
	{
		Toast.makeText(context, "Provider Enabled",Toast.LENGTH_SHORT).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(context, "Status on "+provider+" is " +getStatusMessage(status), Toast.LENGTH_SHORT).show();
		
	}
	
	public String getStatusMessage(int status)
	{
		if(status == 1)
		{
			return "conecting";
		}else if(status == 2)
		{
			return "connected";
		}else
			return "unknown";
	}
}
