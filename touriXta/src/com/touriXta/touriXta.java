package com.touriXta;
 
import java.util.Locale;

import android.speech.tts.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;


import com.google.android.maps.MyLocationOverlay;	
import android.location.LocationListener;
import android.os.Bundle; 
import android.preference.PreferenceActivity;

import com.google.android.maps.MapActivity; 
import com.google.android.maps.MapController;
import com.google.android.maps.MapView; 
import com.google.android.maps.Overlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
public class touriXta extends MapActivity implements TextToSpeech.OnInitListener ,LocationListener 
{
	private settings sts;
	private MapView mMapa;
	private TextToSpeech tts;
	public MyLocationOverlay mylocation;
	private MapController mController;
	private GeoPoint ultimaPosicion;
	private actividadMonumento aM;
	private MonumentoOverlay mo;
	private PreferenceActivity pa;
	private Dialog d;
	private ListView list;
	private Context cnt;
	AlertDialog.Builder builder;
	ProgressDialog progressdialog;

	
   public void onCreate(Bundle savedInstanceState) 
    { 
	   /*Le damos la vista a la pantalla principal*/
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 
        cnt = this;
        /*Añadimos los botones principales para su uso*/
        final Button btSalir = (Button)findViewById(R.id.BotonSalir);
        final Button btEntrar = (Button)findViewById(R.id.BotonEntrar);
        
        /*Le damos funcionalidad al botón Salir*/
        btSalir.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        	/*Creamos el AlertDialog*/
        	  builder = new AlertDialog.Builder(cnt);
       	   	  /*Establecemos un mensaje*/
       	   	  builder.setMessage("¿Estás seguro de que quieres salir?");	
       	   	 /*Establecemos una función si en AlertDialog Le damos a Sí y a No*/
       	   	  builder.setCancelable(false)
       	   	  .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
       	   		  public void onClick(DialogInterface dialog, int id) {
       	   			  finish();
       	   		  }
       	   	  })
       	   	  .setNegativeButton("No", new DialogInterface.OnClickListener() {
       	   		  public void onClick(DialogInterface dialog, int id) {
       	   			  dialog.cancel();
       	   		  }
       	   	  });
       	   	  /*Se crea y se muestra*/
       	   	  builder.create();
       	   	  builder.show();
        	}
        });
        /*Le damos funcionalidad al botón Salir*/
        btEntrar.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		/*Establecemos un progressDialog para hacer una interfaz más llamativa*/
        		ProgressDialog progressdialog;
        	
        		progressdialog = new ProgressDialog(cnt);
        		progressdialog.setMessage("Cargando...");
        		progressdialog.show();
        		/*Creamos un Intent para que abra a la actividad relacionada*/
        		Intent actividadmapa= new Intent(touriXta.this,actividadMonumento.class);
        		actividadmapa.putExtra("esp", true);
        		startActivity(actividadmapa);    
        	}
        });
        /*Hacemos función de la clase TextToSpeech para que hable*/
        tts = new TextToSpeech(this,this);
     //   progressdialog.dismiss();
    }
   
  /*Creamos un menu donde podemos cambiar alguna configuración*/
   public boolean onCreateOptionsMenu(Menu menu)
   {
	   boolean result = super.onCreateOptionsMenu(menu);
	   menu.add(0, 0, 0,"Settings");
	   return result;
   }
   /*Le damos funcionalidad a los botones del menú, en este caso es uno sólo*/
   public boolean onOptionsItemSelected(MenuItem item)
   {
	   	switch(item.getItemId())
	   {
	   	case 0:
	   	/*La funcionalidad dada al botón de menú se traslada a la clase Preference*/
	   		Intent intent2 = new Intent(touriXta.this,Preference.class);
	   		startActivity(intent2);
	   }
	   return super.onOptionsItemSelected(item);
   }
   protected boolean isRouteDisplayed() 
    { 
        return false; 
    }
    public void onInit(int status)
    {
    	Locale loc = new Locale("es","","");
    	tts.setLanguage(loc);
    	tts.speak("¡Bienvenido a touriXta!", TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onLocationChanged(Location location) {
		Log.d(LOCATION_SERVICE, "Location received");
	}
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub	
	}
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub	
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}			 
}

