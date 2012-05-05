package com.touriXta;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class leeXmlEstadioHandler extends DefaultHandler
{
	private List<Estadio> estadios;
	private Estadio estadio;
	private StringBuilder text;
	private String nombre;
	private String descripcion;
	private String desc_breve;
	
	public List<Estadio> getEstadios()
	{
		return this.estadios;
	}
	
	public void characters(char[] ch,int start,int length) throws SAXException
	{
		super.characters(ch, start, length);
		if(this.estadio != null)
		{
			text.append(ch,start,length);
		}
	}
	public void endElement(String uri,String localName,String name) throws SAXException
	{
		super.endElement(uri, localName, name);
	if(this.estadios != null)
	{
		if(this.estadio != null)
		{
			if(localName.equals("Name"))
			{
				nombre =text.toString();
			}else if(localName.equals("descripcion"))
			{
			descripcion = text.toString();
			}else if(localName.equals("descripcion_breve"))
			{
			desc_breve=text.toString();
			}
			else if (localName.equals("Estadio")){
				estadio = new Estadio(nombre,descripcion,desc_breve);	
				this.estadios.add(estadio);
			}
			
			text.setLength(0);
		}
	}
		

	}
	
	public void startDocument() throws SAXException
	{
		super.startDocument();
		estadios = new ArrayList<Estadio>();
		text = new StringBuilder();
	}
	public void startElement(String uri,String localName,String name,Attributes atts) throws SAXException
	{
		super.startElement(uri, localName, name, atts);
		if(localName.equals("Estadio"))
		{
			estadio = new Estadio();
		}
	}
	
}
