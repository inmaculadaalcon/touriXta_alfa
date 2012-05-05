package com.touriXta;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import org.xml.sax.HandlerBase;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class actividadMonumento extends MapActivity{
	/*Creamos las variables a usar*/
	MapView mMapa;
	MapController mControlMapa;
	MonumentoOverlay monumento;
	GeoPoint animateto;
	GeoPoint ultimaPosicion;
	MyLocationOverlay mylocation;
	List<Estadio> list;
	List<Monumento> list2;
	List<Escuela> list3;
	Context context;
	String s;
	String s2;
	String s3;
	Texto_monumentos tm;
	Button btSilencia;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Le damos una vista para mostrar el mapa
		setContentView(com.touriXta.R.layout.mapa_recorrido);
		/*Establecemos los parámetros para el mapa*/
		mMapa =(MapView)findViewById(com.touriXta.R.id.mapview);
		
		mControlMapa = mMapa.getController();
		
		mMapa.setBuiltInZoomControls(true);
		
		mMapa.setSatellite(false);
		
		mControlMapa.setZoom(19);
		/*Obtenemos el MyLocationOverlay*/
		mylocation = new MyLocationOverlay(this,mMapa);
		mylocation.enableMyLocation();	
		
		mylocation.runOnFirstFix(new Runnable()
		{
				public void run()
				{
					ultimaPosicion = mylocation.getMyLocation();
					mControlMapa.animateTo(ultimaPosicion);
				}
		});
		/*Para añadir los overlay de monumento en el mapa tenemos que crearlos a partir de la clase 
		 * MonumentoOverlay*/
		monumento = new MonumentoOverlay(this);
		/*Añadimos estos elementos en el mapa además de incluir nuestra localización*/
		mMapa.getOverlays().add(monumento);
		mMapa.getOverlays().add(mylocation);
		
		Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
		
		int latitud = new Integer(getIntent().getExtras().getInt("latitud"));
		int longitud = new Integer(getIntent().getExtras().getInt("longitud"));
		
		animateto = new GeoPoint((int)(latitud),(int)(longitud));
		
		mMapa.getController().animateTo(animateto);			
		try {
			InputStream is_estadio= getResources().openRawResource(R.raw.estadios);
			
			XmlParser xmlParser = new XmlParser(is_estadio);
			list = xmlParser.parse();
			s = list.get(1).getDescripcion();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			InputStream is_monumento = getResources().openRawResource(R.raw.monumentos);		
			XmlParse2 xmlParser2 = new XmlParse2(is_monumento);
			list2 = xmlParser2.parse();
			s2 = list2.get(1).getName();
		
		}catch(MalformedURLException e)
		{
			e.printStackTrace();
		}catch(NotFoundException e)
		{
			e.printStackTrace();
		}
		try
		{
			XmlParse3 xmlParser3 = new XmlParse3();
			InputStream is_escuela = getResources().openRawResource(R.raw.escuelas);
			list3 = xmlParser3.parse(is_escuela);
			s3 = list3.get(0).getName();
	
		}catch(MalformedURLException e)
		{
			e.printStackTrace();
		}catch(NotFoundException e)
		{
			e.printStackTrace();
		}
		
		
	}

	//Añadimos el menu, que en este caso tendrá dos botones Vista Callejero o Satélite, este menú lo mantendremos en todo el mapa
	public boolean onCreateOptionsMenu(Menu menu)
	{	
		boolean result = super.onCreateOptionsMenu(menu);
		
			menu.add(0, 1, 0,"Vista Callejero").setIcon(android.R.drawable.ic_menu_mapmode);
	
			menu.add(0, 0, 0,"Vista Satelite").setIcon(android.R.drawable.ic_menu_mapmode);
			
			menu.add(0,2,0,"Silenciar").setIcon(android.R.drawable.ic_lock_silent_mode_off);
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
	public boolean isRouteDisplayed()
	{
		return false;
	}
}
