package com.touriXta;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

public class fromXmlToString
{
//	private String nombre;
//	private String desc;
//	private String desc_breve;
	Context ctx;
	List<Estadio> list;
	List<Monumento> list2;
	List<Escuela> list3;
	String s,s2,s3;
	public fromXmlToString(Context cnt)
	{
		this.ctx = cnt;
		try {
			InputStream is_estadio= this.ctx.getResources().openRawResource(R.raw.estadios);	
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
			InputStream is_monumento = this.ctx.getResources().openRawResource(R.raw.monumentos);
			
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
			InputStream is_escuela = this.ctx.getResources().openRawResource(R.raw.escuelas);
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
/*Lista de los monumentos de las escuelas y de los estadios*/	
	public List<Monumento> listaMonumentos()
	{
		return this.list2;
	}
	public List<Estadio> listaEstadios()
	{
		return this.list;
	}
	public List<Escuela> listaEscuelas()
	{
		return this.list3;
	}
/*Lista de los nombres, descripciones y descripciones breves de los monumentos*/	
	public List<String> nombresMonumentos()
	{
		List<String> nom_monumentos = new ArrayList<String>();
		for(Monumento m:listaMonumentos())
		{
			nom_monumentos.add(m.getName());
		}
		return nom_monumentos;
	}
	public List<String> descripcionMonumentos()
	{
		List<String> desc_monumentos = new ArrayList<String>();
		for(Monumento m: listaMonumentos())
		{
			desc_monumentos.add(m.getDescripcion());
		}
		return desc_monumentos;
	}
	public List<String> desc_breve_monumentos()
	{
		List<String> desc_breve_monumentos = new ArrayList<String>();
		for(Monumento m:listaMonumentos())
		{
			desc_breve_monumentos.add(m.getDescripcion_breve());
		}
		return desc_breve_monumentos;
	}
/*Listas de los nombres, descripciones y descripciones breves de los estadios*/	
	public List<String> nombresEstadios()
	{
		List<String> nom_estadios =new ArrayList<String>();
		for(Estadio e:listaEstadios())
		{
			nom_estadios.add(e.getName());
		}
		return nom_estadios;
	}
	public List<String> descripcion_estadios()
	{
		List<String> desc_estadios = new ArrayList<String>();
		for(Estadio e:listaEstadios())
		{
			desc_estadios.add(e.getDescripcion());
		}
		return desc_estadios;
	}
	public List<String> desc_breve_estadios()
	{
		List<String> desc_breve_estadios = new ArrayList<String>();
		for(Estadio e:listaEstadios())
		{
			desc_breve_estadios.add(e.getDescripcion_breve());
		}
		return desc_breve_estadios;
	}
/*Listas de los nombres, descripciones y descripciones breves de las escuelas*/	
	public List<String> nombresEscuelas()
	{
		List<String> nom_escuelas = new ArrayList<String>();
		for(Escuela e:listaEscuelas())
		{
			nom_escuelas.add(e.getName());
		}
		return nom_escuelas;
	}
	public List<String> descripcion_escuelas()
	{
		List<String> desc_escuelas = new ArrayList<String>();
		for(Escuela e:listaEscuelas())
		{
			desc_escuelas.add(e.getDescripcion());
		}
		return desc_escuelas;
	}
	public List<String> desc_breve_escuelas()
	{
		List<String> desc_breve_escuelas = new ArrayList<String>();
		for(Escuela e:listaEscuelas())
		{
			desc_breve_escuelas.add(e.getDescripcion_breve());
		}
		return desc_breve_escuelas;
	}
/*Nombres de las escuelas*/
	public String nom_etsii()
	{
		return nombresEscuelas().get(0).trim();
	}
	public String nom_esc_pol()
	{
		return nombresEscuelas().get(1).trim();
	}
	public String nom_esi()
	{
		return nombresEscuelas().get(2).trim();
	}
/*Nombres de los monumentos- iglesias*/
	public String nom_catedral()
	{
		return nombresMonumentos().get(0).trim();
	}
	public String nom_sanEsteban()
	{
		return nombresMonumentos().get(1).trim();
	}
	public String nom_sanMarcos()
	{
		return nombresMonumentos().get(2).trim();
	}
/*Nombres de los estadios */	
	public String nom_Betis()
	{
		return nombresEstadios().get(0).trim();
	}
	public String nom_Sevilla()
	{
		return nombresEstadios().get(1).trim();
	}
	public String nom_estadio_olimpico()
	{
		return nombresEstadios().get(2).trim();
	}
/*Descripciones breves "introductorias" escuelas*/
	public String desc_breve_etsii()
	{
		return desc_breve_escuelas().get(0).trim();
	}
	public String desc_breve_pol()
	{
		return desc_breve_escuelas().get(1).trim();
	}
	public String desc_breve_esi()
	{
		return desc_breve_escuelas().get(2).trim();
	}
/*Descripciones de las escuelas*/	
	public String desc_etsii()
	{
		return descripcion_escuelas().get(0).trim();
	}
	public String desc_pol()
	{
		return descripcion_escuelas().get(1).trim();
	}
	public String desc_esi()
	{
		return descripcion_escuelas().get(2).trim();
	}
/*Descripciones breves "intro" de los monumentos*/
	public String desc_breve_catedral()
	{
		return desc_breve_monumentos().get(0).trim();
	}
	public String desc_breve_SanEsteban()
	{
		return desc_breve_monumentos().get(1).trim();
	}
	public String desc_breve_SanMarcos()
	{
		return desc_breve_monumentos().get(2).trim();
	}
/*Descripciones de  los monumentos*/
	public String desc_catedral()
	{
		return descripcionMonumentos().get(0).trim();
	}
	public String desc_sanEsteban()
	{
		return descripcionMonumentos().get(1).trim();
	}
	public String desc_sanMarcos()
	{
		return descripcionMonumentos().get(2).trim();
	}
/*Descripciones breves "intro" de los estadios*/
	public String desc_breve_betis()
	{
		return desc_breve_estadios().get(0).trim();
	}
	public String desc_breve_Sevilla()
	{
		return desc_breve_estadios().get(1).trim();
	}
	public String desc_breve_Olimpico()
	{
		return desc_breve_estadios().get(2).trim();
	}
/*Descripciones de los estadios*/
	public String desc_Betis()
	{
		return descripcion_estadios().get(0).trim();
	}
	public String desc_Sevilla()
	{
		return descripcion_estadios().get(1).trim();
	}
	public String desc_Olimpico()
	{
		return descripcion_estadios().get(2).trim();
	}
} 
