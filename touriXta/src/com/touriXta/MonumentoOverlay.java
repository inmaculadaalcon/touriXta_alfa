package com.touriXta;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.touriXta.R.drawable;

public class MonumentoOverlay extends Overlay
{
	static Bitmap icono = null;
	GeoPoint[] monumentos;
	String[] nombres;
	GeoPoint posicion;
	static final int offsetX = 18;
	static final int offsetY = 20;
	int a;
	AlertDialog.Builder d2;
	Bitmap icono32;
	Bitmap icono16;
	Bitmap icono_etsii;
	Bitmap icono_etsii32;
	Bitmap icono_betis;
	Bitmap icono_sevillafc;
	Bitmap icono_politecnica;
	Bitmap icono_catedral;
	Bitmap icono_esi;
	Bitmap icono_sanesteban;
	Bitmap icono_sanmarcos;
	Context context;
	GeoPoint campoDelBetis;
	GeoPoint catedral;
	GeoPoint etsii;
	GeoPoint estadioSevilla;
	GeoPoint escuelaPolitecnica;
	GeoPoint escuelaIngenieros;
	GeoPoint iglesia_SanEsteban;
	GeoPoint iglesia_SanMarcos;
	int mapWidth;
	int mapHeight;
	Texto_monumentos h;
	List<Dato> l;
	fromXmlToString fxts;
	//Constructor de MonumentoOverlay
	public MonumentoOverlay(Context ctx)
	{
		fxts = new fromXmlToString(ctx);
		//Puntos en el mapa de los monumentos con sus coordenadas correspondientes
		catedral = new GeoPoint((int)(37.385793223391524*1E6),(int)(-5.992934703826904*1E6));
		iglesia_SanEsteban = new GeoPoint((int)(37.389706*1E6),(int)(-5.986289*1E6));
		iglesia_SanMarcos = new GeoPoint((int)(37.396347*1E6),(int)(-5.987881*1E6));
		estadioSevilla = new GeoPoint((int)(37.384064*1E6),(int)(-5.9705*1E6)) ;
		campoDelBetis = new GeoPoint((int)(37.3570083*1E6),(int)(-5.9820389*1E6));
		etsii = new GeoPoint((int)(37.358088*1E6),(int)(-5.987817*1E6));
		escuelaPolitecnica = new GeoPoint((int)(37.3766939*1E6),(int)(-6.0033106*1E6));
		escuelaIngenieros = new GeoPoint((int)(37.411819*1E6),(int)(-6.000947*1E6));
		
		 //Dos arrays, uno de monumentos y otro de nombres de los monumentos que se usarán más tarde
		this.monumentos = new GeoPoint[]{campoDelBetis,catedral,etsii,estadioSevilla,escuelaPolitecnica,escuelaIngenieros,iglesia_SanEsteban,iglesia_SanMarcos};
		this.nombres = new String[]{"Estadio Benito Villamarín","Catedral de Sevilla","Escuela Técnica Superior de Ingeniería Informática","Estadio Ramón Sánchez Pizjuán","Escuela Politécnica","Escuela Técnica Superior de Ingeniería","Iglesia de San Esteban","Iglesia de San Marcos"};
		//anchura y altura del mapa
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		//contexto de la actividad
		this.context = ctx;
		//Creación del objeto que lee de textos referidos a los monumentos
		h = new Texto_monumentos(context);
//	º	List<Dato> l = xh.getDatos();
		//Iconos de 32 y 16 bits, para según estén más lejos o cerca del mapa
		if(icono32==null)icono32=BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		if(icono16==null)icono16=BitmapFactory.decodeResource(context.getResources(),R.drawable.icon);	
		if(icono_etsii == null)icono_etsii=BitmapFactory.decodeResource(context.getResources(), R.drawable.w_logo_etsii);
		if(icono_sevillafc == null)icono_sevillafc = BitmapFactory.decodeResource(context.getResources(),R.drawable.escudo_sevilla);
		//if(icono_etsii32 == null)icono_etsii32=BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_etsii_2);
		if(icono_betis == null) icono_betis=BitmapFactory.decodeResource(context.getResources(),R.drawable.icono_betis);
		/*Aqui poner un icono más pequeño para lo lejos! :D*/
		if(icono_politecnica == null) icono_politecnica=BitmapFactory.decodeResource(context.getResources(), R.drawable.icono_politecnica);
		if(icono_catedral == null) icono_catedral= BitmapFactory.decodeResource(context.getResources(), R.drawable.web_w_catedral_sevilla);
		if(icono_esi == null) icono_esi = BitmapFactory.decodeResource(context.getResources(),R.drawable.esi);
		if(icono_sanesteban == null) icono_sanesteban = BitmapFactory.decodeResource(context.getResources(), R.drawable.san_esteban);
		if(icono_sanmarcos == null) icono_sanmarcos = BitmapFactory.decodeResource(context.getResources(),R.drawable.w_san_marcos);
	}
	//Método para poder dibujar los "layouts" en el mapa
	public void draw(Canvas canvas,MapView mapView, boolean shadow)
	{
		Paint p1 = new Paint();
		GeoPoint posicion = null;
		
		int zoom = mapView.getZoomLevel();
		
		if(zoom>15)
		{
			if(zoom == 19 || zoom == 18)
			{
				icono = icono32;
			}
			else if(zoom == 17 || zoom == 16 )
			{
				icono = icono16;
			}
			for(int i = 0;i<monumentos.length;i++)
			{
				this.posicion = monumentos[i];
				Point pt = mapView.getProjection().toPixels(this.posicion, null);
				if(monumentos[i] == etsii)
				{
					canvas.drawBitmap(icono_etsii,pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == campoDelBetis)
				{
					canvas.drawBitmap(icono_betis, pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == estadioSevilla)
				{
					canvas.drawBitmap(icono_sevillafc, pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == escuelaPolitecnica)
				{
					canvas.drawBitmap(icono_politecnica, pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == catedral)
				{
					canvas.drawBitmap(icono_catedral, pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == escuelaIngenieros)
				{
					canvas.drawBitmap(icono_esi, pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == iglesia_SanEsteban)
				{
					canvas.drawBitmap(icono_sanesteban,pt.x-20,pt.y-20, p1);
				}
				else if(monumentos[i] == iglesia_SanMarcos)
				{
					canvas.drawBitmap(icono_sanmarcos, pt.x-20,pt.y-20, p1);
				}
				else
				{
					canvas.drawBitmap(icono, pt.x-20,pt.y-20, p1);
				}
			}
			super.draw(canvas, mapView, shadow);
			}
	}
	//Método para poder pulsar encima del dibujo y que haga algo
	public boolean onTap(GeoPoint p, MapView mapView) {	
		
		for (int i=0; i<monumentos.length;i++){
			Point pointTap = mapView.getProjection().toPixels(p, null);
			if (monumentos[i]!=null){
				Point pointMap = mapView.getProjection().toPixels(monumentos[i], null);
		 	        if( pointTap.x-pointMap.x>=-20
		 	            && pointTap.x-pointMap.x<=20
		 	            && pointMap.y-pointTap.y>=-20
		 	            && pointMap.y-pointTap.y<=20)
		 	        {
//		 	        	for(int j= 0;j< l.size();j++)
//		 	        	{
//		 	        		Toast.makeText(context, l.get(j)., Toast.LENGTH_SHORT).show();
//		 	        	}
//		 	        	for(Dato d:l)
//		 	   		{
//		 	        		if(d.getEscuelas().get(l))
//		 	   			Toast.makeText(context,d.getEscuelas().get(0).getDescripcion(),Toast.LENGTH_SHORT).show();
//		 	   		}
		 	        	if(nombres[i] == "Escuela Técnica Superior de Ingeniería Informática")
		 	        	{
		 	        		h.onInit1(0);
		 	        		
		 	        	}else if(nombres[i] == "Catedral de Sevilla")
		 	        	{
		 	        		h.onInit2(0);
		 	        	}else if(nombres[i] == "Estadio Ramón Sánchez Pizjuán")
		 	        	{
		 	        		h.onInit4(0);
		 	        	}else if(nombres[i] == "Escuela Politécnica")
		 	        	{
		 	        		h.onInit6(0);
		 	        	}else if(nombres[i] == "Escuela Técnica Superior de Ingeniería")
		 	        	{
		 	        		h.onInit8(0);
		 	        	}else if(nombres[i] == "Iglesia de San Esteban")
		 	        	{
		 	        		h.onInit9(0);
		 	        	}else if(nombres[i] == "Iglesia de San Marcos")
		 	        	{
		 	        		h.onInit10(0);
		 	        	}else
		 	        	{
		 	        		h.onInit3(0);
		 	        	}
		 	        	AlertDialog.Builder d = new AlertDialog.Builder(context);
		 	        	d2=new AlertDialog.Builder(context);
		 	        	d.setMessage(nombres[i]);
		 	        	if(nombres[i]== "Escuela Técnica Superior de Ingeniería Informática")
		 	        	{	String desc_breve_etsii = fxts.listaEscuelas().get(0).getDescripcion_breve().trim();
			 	        		d.setMessage(desc_breve_etsii)
			 	        	.setNeutralButton("Más..", new OnClickListener()
			 	        	{
			 	        		
								public void onClick(DialogInterface dialog,
										int which) {
									nuevoMensajeEscuelas(); 
									h.onInit5(0);
								}
			 	        	} );
		 	        	/*Se crea este botón por si no interesa que nos cuente más cosas sobre el edificio*/
			 	        	d.setNegativeButton("Cerrar", new OnClickListener()
			 	        	{
			 	        		public void onClick(DialogInterface dialog,int witch)
			 	        		{
			 	        			/*Si le damos a Cerrar. Para que no nos diga más nada*/
			 	        			h.para();
			 	        		}
			 	        	});
		 	        	}else if(nombres[i] == "Catedral de Sevilla")
		 	        	{
		 	        		d.setMessage("Es la catedral gótica cristiana más grande del mundo. La UNESCO la declaró, en 1987, Patrimonio de la Humanidad y, el 25 de julio de 2010, Bien de Valor Universal Excepcional. Si desea obtener más información de este edificio pulse el botón Más ")
			 	        	.setNeutralButton("Más..", new OnClickListener()
			 	        	{
			 	        		
								public void onClick(DialogInterface dialog,
										int which) {
									nuevoMensajeIglesias(); 
									h.onInit7(0);
								}
			 	        	} );
		 	        	/*Se crea este botón por si no interesa que nos cuente más cosas sobre el edificio*/
			 	        	d.setNegativeButton("Cerrar", new OnClickListener()
			 	        	{
			 	        		public void onClick(DialogInterface dialog,int witch)
			 	        		{
			 	        			/*Si le damos a Cerrar. Para que no nos diga más nada*/
			 	        			h.para();
			 	        		}
			 	        	});
		 	        	}else if(nombres[i] == "Escuela Técnica Superior de Ingeniería")
		 	        	{	
		 	        		String breve_ing = fxts.nom_esi()+fxts.desc_breve_esi();
		 	        		d.setMessage(breve_ing);
		 	        		d.setNeutralButton("Más", new OnClickListener()
		 	        		{

								public void onClick(DialogInterface dialog,int which) 
								{
									nuevoMensajeEscuelas3();
									
								}
		 	        			
		 	        		});
		 	        		d.setNegativeButton("Cerrar", new  OnClickListener()
		 	        		{
		 	        			public void onClick(DialogInterface dialog, int item)
		 	        			{
		 	        				
		 	        				h.para();
		 	        			}
		 	        		});
		 	        			
		 	        	}else if(nombres[i] == "Iglesia de San Esteban")
		 	        	{
		 	        		
		 	        	}else if(nombres[i] == "Iglesia de San Marcos")
		 	        	{
		 	        		
		 	        	}
		 	        	
		 	        	d.show();        		
		 			 	Toast.makeText(context, nombres[i], Toast.LENGTH_SHORT).show();
		 	        	return true;
		 	        } 	        			
		 	        		
		 	        	}
		 	        }
	
		return true;		
	}
	public Builder nuevoMensajeIglesias()
	{
		AlertDialog.Builder d = new AlertDialog.Builder(context);
		d.setMessage(fxts.desc_catedral() ).
		setNeutralButton("Edificios Relacionados", new OnClickListener()
		{	
			public void onClick(DialogInterface dialog, int which) {
//				SeleccionEscuelas se = new SeleccionEscuelas();				
//				se.Empieza();
				final CharSequence[] items = {"Iglesia de San Esteban","Iglesia de San Marcos"};
				/*Aquí debe aparecer las opciones para que escojamos las escuelas relacionadas*/
				AlertDialog.Builder d = new AlertDialog.Builder(context);
				d.setTitle("Escoje el edificio");
				d.setItems(items, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int item) 
					{
						Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
						switch(item)
						{
						case 0:
							//Toast.makeText(context,"Esto debería ir a la politécnica", Toast.LENGTH_SHORT).show();			
							
//							Intent i = new Intent(context,Iglesias.class);
//							context.startActivity(i);
//							break;
						case 1:
							Toast.makeText(context, "Esto al punto A", Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});
				d.create();
				d.show();
			}});
		d.show();
		d.setCancelable(true);
		return d;
	}
	
	public  Builder nuevoMensajeEscuelas()
	{
		h.onInit5(0);
		AlertDialog.Builder d = new AlertDialog.Builder(context);
		String etsii_desc = fxts.desc_etsii();
		
		d.setMessage(etsii_desc).
		setNeutralButton("Edificios Relacionados", new OnClickListener()
		{	
			public void onClick(DialogInterface dialog, int which) {
//				SeleccionEscuelas se = new SeleccionEscuelas();				
//				se.Empieza();
				final CharSequence[] items = {"Escuela Politécnica","Escuela Superior de Ingeniería"};
				/*Aquí debe aparecer las opciones para que escojamos las escuelas relacionadas*/
				AlertDialog.Builder d = new AlertDialog.Builder(context);
				d.setTitle("Escoje el edificio");
				d.setItems(items, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int item) 
					{
						Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
						switch(item)
						{
						case 0:
							/*Esto va a la escuela politécnica*/
						//	Toast.makeText(context,"Esto debería ir a la politécnica", Toast.LENGTH_SHORT).show();			
							Intent i = new Intent(context,Escuelas_inf_pol.class);
							context.startActivity(i);
							break;
						case 1:
							/*Esto va a la escuela de ingenieros */
							Intent i2 = new Intent(context,Escuelas_inf_ing.class);
							context.startActivity(i2);
							Toast.makeText(context, "Esto a la escuela de ingenieros", Toast.LENGTH_SHORT).show();
							
							break;
							/*Esto no va a ningún sitio*/
						}
					}
				});
				d.create();
				d.show();
			}});
		d.show();
		d.setCancelable(true);
		return d;
	}
	public Builder nuevoMensajeEscuelas2()
	{
		AlertDialog.Builder d = new AlertDialog.Builder(context);
		String pol_desc = fxts.desc_pol();
//		h.onInit10(0);
		d.setMessage(pol_desc);
		h.onInit10(0);	
		d.setNeutralButton("Edificios Relacionados", new OnClickListener()
		{	
			public void onClick(DialogInterface dialog, int which) {
				final CharSequence[] items = {"Escuela Técnica Superior de Ingeniería Informática","Escuela Superior de Ingeniería"};
				/*Aquí debe aparecer las opciones para que escojamos las escuelas relacionadas*/
				AlertDialog.Builder d = new AlertDialog.Builder(context);
				d.setTitle("Escoje el edificio");
				d.setItems(items, new DialogInterface.OnClickListener() 
				{	
					public void onClick(DialogInterface dialog, int item) 
					{
						Toast.makeText(context, items[item], Toast.LENGTH_SHORT).show();
						switch(item)
						{
						case 0:
							/*Esto va a la escuela de informática*/
							Intent i2 = new Intent(context,Escuelas_pol_inf.class);
							context.startActivity(i2);
							break;
						case 1:
							/*Esto va a la escuela de ingenieros */
							Toast.makeText(context, "Esto a la escuela de ingenieros", Toast.LENGTH_SHORT).show();
							Intent i3 = new Intent(context,Escuelas_inf_ing.class);
							context.startActivity(i3);
							break;
							/*Esto no va a ningún sitio*/
						case 2: 
							Toast.makeText(context, "Esto al punto B", Toast.LENGTH_SHORT).show();
							break;
						}
					}
				});	
				d.create();
				d.show();
			}});
		
		d.show();
		d.setCancelable(true);
		return d;
	}
	public Builder nuevoMensajeEscuelas3()
	{
		AlertDialog.Builder d = new AlertDialog.Builder(context);
		String desc_esi = fxts.desc_esi();
		d.setMessage(desc_esi);
		d.setNeutralButton("Edificios Relacionados", new OnClickListener() 
		{

			public void onClick(DialogInterface dialog, int item) 
			{
					CharSequence[] edificios_rel = {"Escuela Técnica Superior de Ingeniería Informática","Escuela Politécnica"};
					AlertDialog.Builder d = new AlertDialog.Builder(context);
					d.setTitle("Edificios Relacionados");
					d.setItems(edificios_rel,new OnClickListener()
					{

						public void onClick(DialogInterface dialog, int item) 
						{
							switch(item)
							{
								case 0:
									/*Esto va a la escuela de informática*/
									Intent i2 = new Intent(context,Escuelas_pol_inf.class);
									context.startActivity(i2);
									break;
								case 1:
									Intent i3 = new Intent(context,Escuelas_inf_ing.class);
									context.startActivity(i3);
									break;
							}
						}
						
					});
			}
			
		});
		return d;
	}
}



