package com.touriXta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TiemposOverlay extends Overlay
{
	static Bitmap icono32 = null;
	static Bitmap icono16 = null;
	static Bitmap icono = null;
	static Bitmap icono_pol = null;
	static Bitmap icono_etsii = null;
	static Bitmap icono_esi = null;
	static final int offsetX = 18;
	static final int offsetY = 20;
	recorrido_inf_pol rec;
	int mapWidth;
	int mapHeight;
	boolean pintar_bicis = false; 
	fromXmlToString fxts;
	AlertDialog.Builder d;
	Texto_monumentos h;
	GeoPoint[] monumentos;
	String[] nombres;
	GeoPoint escuelaPolitecnica;
	GeoPoint etsii;
	GeoPoint esi;
	Context context;
	GeoPoint posicion;
	Texto_monumentos h1;
	public TiemposOverlay(Context context,recorrido_inf_pol rec,int mapWidth,int mapHeight)
	{
		esi = new GeoPoint((int)(37.411819*1E6),(int)(-6.000947*1E6));
		escuelaPolitecnica = new GeoPoint((int)(37.3766939*1E6),(int)(-6.0033106*1E6));
		etsii = new GeoPoint((int)(37.358088*1E6),(int)(-5.987817*1E6));
		fxts = new fromXmlToString(context);
		
		if(icono32 == null)icono32=BitmapFactory.decodeResource(context.getResources(),R.drawable.icon);
		if(icono16 == null)icono16=BitmapFactory.decodeResource(context.getResources(),R.drawable.icon);
		if(icono_pol == null)icono_pol=BitmapFactory.decodeResource(context.getResources(), R.drawable.icono_politecnica);
		if(icono_etsii == null)icono_etsii=BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_etsii_2);
		if(icono_esi == null)icono_esi=BitmapFactory.decodeResource(context.getResources(),R.drawable.esi);
		this.context =context;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		h1 = new Texto_monumentos(context);

		this.monumentos = new GeoPoint[]{etsii,escuelaPolitecnica,esi};
		this.nombres = new String[]{"Escuela Técnica Superior de Ingeniería Informática","Escuela Politécnica","Escuela Superior de Ingenieros"};
		
	}
	
	public void draw(Canvas canvas,MapView mapView, boolean shadow)
	{
		GeoPoint posicion;
		int zoom = mapView.getZoomLevel();
		Paint p1 = new Paint();
		if(zoom == 19 || zoom == 18)
			{
				icono = icono16;
			}else if(zoom == 17 || zoom == 16)
			{
				icono = icono32;
			}
			for(int i = 0;i<monumentos.length;i++)
			{
				this.posicion = monumentos[i];
				Point pt = mapView.getProjection().toPixels(this.posicion, null);
				if(monumentos[i] == etsii) 
				{
					canvas.drawBitmap(icono_etsii,pt.x-20,pt.y-20, p1);
				}else if(monumentos[i] == escuelaPolitecnica )
				{
					canvas.drawBitmap(icono_pol,pt.x-20,pt.y-20, p1);
				}else if(monumentos[i] == esi)
				{
					canvas.drawBitmap(icono_esi, pt.x-20,pt.y-20, p1);
				}
			}
				super.draw(canvas, mapView, shadow);
	}
	public boolean onTap(GeoPoint p, MapView mapView) 
	{		
		for (int i=0; i<monumentos.length;i++)
		{
			Point pointTap = mapView.getProjection().toPixels(p, null);
			if (monumentos[i]!=null)
			{
				Point pointMap = mapView.getProjection().toPixels(monumentos[i], null);
	 	        if( pointTap.x-pointMap.x>=-15
	 	            && pointTap.x-pointMap.x<=15
	 	            && pointMap.y-pointTap.y>=-15
	 	            && pointMap.y-pointTap.y<= 15)
	 	        {
	 	        	AlertDialog.Builder d = new AlertDialog.Builder(context);
	 	        	d=new AlertDialog.Builder(context);

	 	        	if(nombres[i] == "Escuela Politécnica")
	 	        	{
	 	        		h1.para();
	 	        		
	 	        		d.setMessage("Aquí puede escoger los medios de transporte que desea usar, La información del lugar o volver al punto de partida");
	 	        		h1.onInit13(0);
	 	        		d.setNeutralButton("Acciones", new OnClickListener()
	 	        		{
	 	        			CharSequence[] acciones= {"Medios de Transporte","Información","Atrás.."};
	 		 	        	
							public void onClick(DialogInterface dialog,int which) {
								AlertDialog.Builder d2 = new AlertDialog.Builder(context);
								d2.setNegativeButton("Cerrar", new OnClickListener()
								{

									public void onClick(DialogInterface dialog,int which) 
									{
										h1.para();
									}
									
								});
								d2.setTitle("Acciones").setItems(acciones, new OnClickListener()
								{
									public void onClick(DialogInterface dialog,int item) 
									{
										switch(item)
										{
										case 0:
											CharSequence[] transportes ={"Autobús","Sevici"};
											AlertDialog.Builder d3 = new AlertDialog.Builder(context);
											d3.setTitle("Medios de transportes");
											d3.setItems(transportes, new OnClickListener()
											{
												private GeoPoint posicion;

												public void onClick(DialogInterface dialog,int which)
												{
												switch(which)
												{
												case 0:
													/*Recorrido del 6*/
													Toast.makeText(context, "Recorrido Autobús", Toast.LENGTH_SHORT).show();
													DrawPath(Color.BLUE,Escuelas_inf_pol.mMapa,"rec_inf_pol_bus");
													DrawPath(Color.GREEN,Escuelas_inf_pol.mMapa,"rec_inf_pol_pie");
													break;
												case 1:
													
													Toast.makeText(context, "Recorrido Sevici", Toast.LENGTH_SHORT).show();
													DrawPath(Color.MAGENTA,Escuelas_inf_pol.mMapa,"rec_inf_pol_sevici");
													DrawPath(Color.RED,Escuelas_inf_pol.mMapa,"rec_inf_pol_pie");
													drawSevici(Escuelas_inf_pol.mMapa,"Escuelas_inf_pol");
											    	break;
												}	
												}
											});
											d3.show();
											break;
										case 1:
										 AlertDialog.Builder d4 = new AlertDialog.Builder(context);
										
										 d4.setTitle("Información");
										 	
											d4.setMessage(fxts.desc_breve_pol());
											h1.onInit11(0);
											d4.setNegativeButton("Cerrar", new OnClickListener()
											{

												public void onClick(DialogInterface dialog,int which) 
												{
													h1.para();
													
												}
												
											});
											d4.setNeutralButton("Más..", new OnClickListener()
											{
												
												public void onClick(DialogInterface dialog,int which)
												{
													AlertDialog.Builder d5 = new AlertDialog.Builder(context); 
													d5.setMessage(fxts.desc_pol());
													h1.onInit12(0);
													
													d5.show();
													d5.setNeutralButton("Edificios Relacionados", new OnClickListener()
													{
														
														public void onClick(DialogInterface dialog,int item) 
														{
															CharSequence[] Edificios_Relacionados = {"Escuela Técnica Superior de Ingeniería Informática","Escuela de Ingenieros"};
															AlertDialog.Builder d = new AlertDialog.Builder(context);
															d.setTitle("Escoge el edificio");
															d.setItems(Edificios_Relacionados, new OnClickListener()
															{

																public void onClick(DialogInterface dialog,int item) 
																{
																	switch(item)
																	{
																	case 0:
																		/*ETSII*/
																		/*Esto va a la escuela de informática*/
																		Intent i2 = new Intent(context,Escuelas_pol_inf.class);
																		context.startActivity(i2);
																		break;
																	case 1:
																		/*ESI*/
																		Intent i = new Intent(context,Escuelas_inf_ing.class);
																		context.startActivity(i);
																		break;
																	}	
																}
																
															});
															d.create();
															d.show();
																
														}
														
													});
													d5.create();
													d5.show();
												}	
											});
											d4.show();
											break;
										case 2:
											break;
										}
										
									}
								});
								d2.show();
							}
	 	        			
	 	        		});
	 	        		d.show();
//	 	        		h.onInit12(0);
	 	        		Toast.makeText(context, "Escuela Politécnica", Toast.LENGTH_SHORT).show();
	 	        	}
	 	        	else if(nombres[i] == "Escuela Técnica Superior de Ingeniería Informática")
	 	        	{	
	 	        		//Toast.makeText(context,"Escuela Técnica Superior de Ingeniería Informática", Toast.LENGTH_SHORT).show();
	 	        		h1.para();
	 	        		d.setMessage("Aquí puede escoger los medios de transporte que desea usar, La información del lugar o volver al punto de partida");
	 	        		h1.onInit13(0);
	 	        		d.setNeutralButton("Acciones", new OnClickListener()
	 	        		{
							public void onClick(DialogInterface dialog,	int which) 
							{
								h1.para();
								AlertDialog.Builder d2 =new AlertDialog.Builder(context);
								CharSequence[] acciones= {"Medios de Transporte","Información","Atrás.."};
								d2.setNegativeButton("Cerrar", new OnClickListener()
								{

									public void onClick(DialogInterface arg0,int arg1) 
									{
										h1.para();
										
									}
									
								});
								
								d2.setTitle("Acciones").setItems(acciones, new OnClickListener()
								{
									public void onClick(DialogInterface dialog,int item) 
									{
										switch(item)
										{
										case 0:
											CharSequence[] transportes ={"Autobús","Sevici"};
											AlertDialog.Builder d3 = new AlertDialog.Builder(context);
											d3.setTitle("Medios de transportes");
											d3.setItems(transportes, new OnClickListener()
											{
												private GeoPoint posicion;

												public void onClick(DialogInterface dialog,int which)
												{
												switch(which)
												{
												case 0:
													Toast.makeText(context, "Recorrido Autobús", Toast.LENGTH_SHORT).show();
													
													/*Recorrido del 6*/
													DrawPath(Color.BLUE,Escuelas_pol_inf.mMapa,"rec_inf_pol_bus");
													DrawPath(Color.GREEN,Escuelas_pol_inf.mMapa,"rec_inf_pol_pie");
													break;
												case 1:
													
													Toast.makeText(context, "Recorrido Sevici", Toast.LENGTH_SHORT).show();
													DrawPath(Color.MAGENTA,Escuelas_pol_inf.mMapa,"rec_inf_pol_sevici");
													DrawPath(Color.RED,Escuelas_pol_inf.mMapa,"rec_inf_pol_pie");
													drawSevici(Escuelas_pol_inf.mMapa,"Escuelas_pol_inf");
											    	break;
												}	
													
												}
												
											});
											d3.show();
											break;
										case 1:
											 AlertDialog.Builder d4 = new AlertDialog.Builder(context);
											 
											 d4.setTitle("Información");
											 h1.onInit5(0);
											 d4.setMessage(fxts.desc_breve_etsii()).setNeutralButton("Más..", new OnClickListener()
											{
												public void onClick(DialogInterface dialog,int which)
												{
													MonumentoOverlay mo = new MonumentoOverlay(context);
													mo.nuevoMensajeEscuelas();
													
												}
												
											 });
											 d4.show();
											break;
										}
									}
								});
								d2.show();	
							}
	 	        		});
	 	        		d.show();
	 	        		
	 	        		Toast.makeText(context, "Escuela Técnica Superior de Ingeniería Informática", Toast.LENGTH_SHORT).show();
	 	        	}else if(nombres[i] == "Escuela Superior de Ingenieros")
	 	        	{
	 	        		h1.para();
	 	        		AlertDialog.Builder d2 = new AlertDialog.Builder(context);
	 	        		d2.setMessage("Aquí puede escoger los medios de transporte que desea usar, La información del lugar o volver al punto de partida");
	 	        		h1.onInit13(0);
	 	        		d2.setNeutralButton("Acciones", new OnClickListener()
	 	        		{

							public void onClick(DialogInterface dialog,	int which) 
							{
								CharSequence[] acciones = {"Medios de Transporte","Información","Atrás"};
								h1.para();
								AlertDialog.Builder d3 = new AlertDialog.Builder(context);
								d3.setNegativeButton("Cerrar", new OnClickListener()
								{
									public void onClick(DialogInterface dialog,int which) 
									{
										h1.para();
									}
									
								});
								d3.setItems(acciones, new OnClickListener()
								{
									public void onClick(DialogInterface dialog,int items) 
									{
										switch(items)
										{
											case 0:
												CharSequence[] transportes = {"Autobús","Sevici"};
												AlertDialog.Builder d = new AlertDialog.Builder(context);
												d.setTitle("Medios de Transportes");
												d.setCancelable(true);
												d.setItems(transportes, new OnClickListener()
												{
													public void onClick(DialogInterface dialog,int which) 
													{
														switch(which)
														{
															case 0:
																/*Pintar recorrido bus*/
																/*Recorrido del 6 */
																DrawPath(Color.CYAN,Escuelas_inf_ing.mMapa,"rec_inf_ing_6");
																/*Recorrido del C1*/
																DrawPath(Color.YELLOW,Escuelas_inf_ing.mMapa,"rec_inf_ing_c1");
																break;
															case 1:
																/*Pintar recorrido sevici*/
																break;
														}
														
													}
													
												});
												d.create();
												d.show();
												break;
											case 1:
												/*Información*/
												
												break;
											case 2:
												/*Atrás*/
												break;
										}
									}
									
								});
								d3.create();
								d3.show();
							}
	 	        			
	 	        		});
	 	        		Toast.makeText(context, "Escuela Superior de Ingenieros", Toast.LENGTH_SHORT).show();
	 	        		d2.create();
	 	        		d2.show();
	 	        	}
	 	       }
			}
		}
	 			return true; 	        
	 }		
	private void drawSevici(MapView mapView,String nom)
	{
		Overlay sevici=new seviciOverlay(this.context,mapView);
		if(nom == "Escuelas_inf_pol"){
		Escuelas_inf_pol.mMapa.getOverlays().add(sevici);
		}else if(nom == "Escuelas_pol_inf"){
		Escuelas_pol_inf.mMapa.getOverlays().add(sevici);
		}
	}

	private void DrawPath(int color,MapView mMapView01,String identificador) 
	{
		Document doc = null;
		try
		{	
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			int ruta_id =mMapView01.getResources().getIdentifier(identificador,"raw", "com.touriXta");	
			doc = db.parse(mMapView01.getResources().openRawResource(ruta_id));
			
			if( doc.getElementsByTagName("coordinates").getLength()>0)
			{
				String path = doc.getElementsByTagName("coordinates").item(0).getFirstChild().getNodeValue();
				String[] pairs = path.split(" ");
				String[] lnglat = pairs[0].split(",");
				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lnglat[1])*1E6),(int)(Double.parseDouble(lnglat[0])*1E6));
				GeoPoint gp1;
				GeoPoint gp2 = startGP;
				for(int i=0;i<pairs.length;i++)
				{
					lnglat = pairs[i].split(",");
					gp1=gp2;
					gp2 = new GeoPoint((int)(Double.parseDouble(lnglat[1])*1E6),(int)(Double.parseDouble(lnglat[0])*1E6));
					mMapView01.getOverlays().add(new miOverLay(gp1,gp2,2,color));
				}
				
			}
		}catch (IOException e)
		{
			e.printStackTrace();
			}
			catch (ParserConfigurationException e)
			{
			e.printStackTrace();
			}
			catch (SAXException e)
			{
			e.printStackTrace();
			}
		}
//	private void DrawPath2(int color,MapView mMapView01,String identificador) 
//	{
//		Document doc = null;
//		try
//		{	
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			
//			int ruta_id = Escuelas_pol_inf.mMapa.getResources().getIdentifier(identificador,"raw", "com.touriXta");	
//			doc = db.parse(Escuelas_pol_inf.mMapa.getResources().openRawResource(ruta_id));
//			
//			if( doc.getElementsByTagName("coordinates").getLength()>0)
//			{
//				String path = doc.getElementsByTagName("coordinates").item(0).getFirstChild().getNodeValue();
//				String[] pairs = path.split(" ");
//				String[] lnglat = pairs[0].split(",");
//				GeoPoint startGP = new GeoPoint((int)(Double.parseDouble(lnglat[1])*1E6),(int)(Double.parseDouble(lnglat[0])*1E6));
//				GeoPoint gp1;
//				GeoPoint gp2 = startGP;
//				for(int i=0;i<pairs.length;i++)
//				{
//					lnglat = pairs[i].split(",");
//					gp1=gp2;
//					gp2 = new GeoPoint((int)(Double.parseDouble(lnglat[1])*1E6),(int)(Double.parseDouble(lnglat[0])*1E6));
//					Escuelas_pol_inf.mMapa.getOverlays().add(new miOverLay(gp1,gp2,2,color));
//				}
//				
//			}
//		}catch (IOException e)
//		{
//			e.printStackTrace();
//			}
//			catch (ParserConfigurationException e)
//			{
//			e.printStackTrace();
//			}
//			catch (SAXException e)
//			{
//			e.printStackTrace();
//			}
//		}

	
	}
