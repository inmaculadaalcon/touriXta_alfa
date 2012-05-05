package com.touriXta;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


public class Escuelas_pol_inf extends MapActivity {
	//Context context;
	boolean ing_tec_ind,fac_der,esc_ing;
	//Variable de nuestro mapa
	static MapView mMapa ;
	//Control del Mapa
	private MapController mControlMapa;
	//Recorrido
	private recorrido_inf_pol rec;
	//Overlay de los tiempos-
	 TiemposOverlay tiempos; 
	 GeoPoint animateto;
	 Context context;
	 boolean esp;
	 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(com.touriXta.R.layout.mapa_recorrido);
		mMapa = (MapView)findViewById(com.touriXta.R.id.mapview);
		mControlMapa = mMapa.getController();
		mMapa.setBuiltInZoomControls(true);
		mMapa.setSatellite(false);
		mControlMapa.setZoom(18);             	      
	    //Creamos un recorrido.
        rec = new recorrido_inf_pol("rec_pol_inf_bus");
    	context = getApplicationContext();
        /*E
    	 * l overlay de tiempos...*/        
        tiempos = new TiemposOverlay(this,rec, mMapa.getWidth(), mMapa.getHeight());
	    mMapa.getOverlays().add(tiempos);
        //Centramos el mapa en el lugar donde queremos ir, en este caso la politécnica
    	mControlMapa.animateTo(rec.ReinaMercedes);
    	
	}
	//Añadimos el menu, que en este caso tendrá dos botones Vista Callejero o Satélite, este menú lo mantendremos en todo el mapa
	public boolean onCreateOptionsMenu(Menu menu)
	{	
		boolean result = super.onCreateOptionsMenu(menu);
		
			menu.add(0, 1, 0,"Vista Callejero").setIcon(com.touriXta.R.drawable.icon);
	
			menu.add(0, 0, 0,"Vista Satelite").setIcon(com.touriXta.R.drawable.icon);
			
		/*	menu.add(0,2,0,"Autobuses").setIcon(com.touriXta.R.drawable.icon);*/
		return result;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		switch (item.getItemId())
		{
			//Si se ha pulsado el botón de cambiar mapa
			case 0:
			//	String s = list.get(1).getName();
//				System.out.println(s);
//				for(Escuela e:list3){
//				Toast.makeText(this,e.getName().trim(), Toast.LENGTH_SHORT).show();
				//}
				mMapa.setSatellite(true);
				mMapa.postInvalidate();
			return true;
			//Si se ha pulsado el botón de cambiar mapa
			case 1:
				mMapa.setSatellite(false);
				mMapa.postInvalidate();
				return true;
			/*Prueba para el caso de activar la funcionalidad de las horas de los autobuses*/
		/*	case 2:
				Intent actividadBus = new Intent(actividadMonumento.this,bus.class);
	        	actividadBus.putExtra("esp", true);
	    		startActivity(actividadBus);    
	    		return true;*/
		}	
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	 	
}
