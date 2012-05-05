package com.touriXta;
import android.graphics.Canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Sevicis extends Overlay 
{
	static Bitmap icono_sevici = null;
	int mapWidth;
	int mapHeight;
	
	GeoPoint[] sevicis;
	GeoPoint sevici_heliopolis;
	GeoPoint sevici_etsii;
	GeoPoint sevici_arqui;
	GeoPoint sevici_trafico;
	GeoPoint sevici_fatima;
	GeoPoint sevici_puente;
	GeoPoint sevici_cigarreras;
	GeoPoint sevici_vvalle;
	GeoPoint sevici_pol;
	Context context;
	private GeoPoint posicion;
public Sevicis(Context context,MapView mapa)
{
	sevici_heliopolis = new GeoPoint((int)(37.356486*1E6),(int)(-5.986489*1E6));
	sevici_etsii = new GeoPoint((int)(37.358397*1E6),(int)(-5.986436*1E6));
	sevici_arqui = new GeoPoint((int)(37.362742*1E6),(int)(-5.986233*1E6));
	sevici_trafico = new GeoPoint((int)(37.363844*1E6),(int)(-5.984764*1E6));
	sevici_fatima = new GeoPoint((int)(37.369139*1E6),(int)(-5.988042*1E6));
	sevici_puente = new GeoPoint((int)(37.373983*1E6),(int)(-5.990839*1E6));
	sevici_cigarreras = new GeoPoint((int)(37.374794*1E6),(int)(-5.993725*1E6));
	sevici_vvalle = new GeoPoint((int)(37.374811*1E6),(int)(-5.997656*1E6));
	sevici_pol = new GeoPoint((int)(37.374711*1E6),(int)(-6.002067*1E6));
	if(icono_sevici == null)icono_sevici=BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_sevici);
	this.sevicis = new GeoPoint[]{sevici_heliopolis,sevici_etsii,sevici_arqui,sevici_trafico,sevici_fatima,sevici_puente,sevici_cigarreras,sevici_vvalle,sevici_pol};
	this.context = context;
	
}

	public void draw(Canvas canvas,MapView mapView,boolean shadow)
	{
		GeoPoint posicion;
		int zoom = mapView.getZoomLevel();
		Paint p1 = new Paint();
		for(int i = 0;i<sevicis.length;i++)
		{
			this.posicion = sevicis[i];
			Point pt = mapView.getProjection().toPixels(this.posicion, null);
				canvas.drawBitmap(icono_sevici,pt.x-20,pt.y-20, p1);
			

	}
			super.draw(canvas, mapView, shadow);
	}

}
