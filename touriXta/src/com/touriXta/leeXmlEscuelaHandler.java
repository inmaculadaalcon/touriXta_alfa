package com.touriXta;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class leeXmlEscuelaHandler extends DefaultHandler
{
	private StringBuilder text;
	private List<Escuela> escuelas;
	private Escuela escuela;
	private String nombre;
	private String descripcion;
	private String desc_breve;
	
	
	public List<Escuela> getEscuelas()
	{
		return this.escuelas;
	}
	
	public void characters(char[] ch,int start,int length) throws SAXException
	{
		super.characters(ch, start, length);
		if(this.escuela != null)
		{
			text.append(ch,start,length);
		}
	}
	
	public void endElement(String uri,String localName,String name) throws SAXException
	{
		super.endElement(uri, localName, name);
		if(this.escuelas != null)
		{
			if(this.escuela != null)
				{
					if(localName.equals("Name"))
					{
						nombre = text.toString();
					}else if(localName.equals("descripcion_breve"))
					{
						desc_breve = text.toString();
					}else if(localName.equals("descripcion"))
					{
					descripcion = text.toString();
					}else if(localName.equals("Escuela"))
					{
						escuela = new Escuela(nombre,descripcion,desc_breve);
						this.escuelas.add(escuela);
					}
					text.setLength(0);
					
				}
		}
	}
	
	public void startDocument() throws SAXException
	{
		super.startDocument();
		escuelas = new ArrayList<Escuela>();
		text = new StringBuilder();
	}
	public void startElement(String uri,String localName,String name,Attributes atts) throws SAXException
	{
		super.startElement(uri, localName, name, atts);
		if(localName.equals("Escuela"))
		{
			escuela = new Escuela();
		}
	}
}
