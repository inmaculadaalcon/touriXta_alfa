package com.touriXta;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class leeXmlMonumentoHandler extends DefaultHandler
{	
	private StringBuilder text;
	private List<Monumento> monumentos;
	private Monumento monumento;
	private String nombre;
	private String descripcion;
	private String descripcion_breve;

	public List<Monumento> getMonumentos()
	{
		return this.monumentos;
	}
	public void characters(char[] ch,int start,int length) throws SAXException
	{
		super.characters(ch,start,length);
		if(this.monumento != null)
		{
			text.append(ch,start,length);
		}
	}
	
	public void endElement(String uri,String localName,String name) throws SAXException
	{
		super.endElement(uri, localName, name);
		if(this.monumentos != null)
		{
			if(this.monumento != null)
			{
				if(localName.equals("Name"))
				{
					nombre = text.toString();
				}else if(localName.equals("descripcion"))
				{
					descripcion = text.toString();
				}else if(localName.equals("descripcion_breve"))
				{
					descripcion_breve = text.toString();
				}else if(localName.equals("Monumento"))
				{
					monumento = new Monumento(nombre,descripcion,descripcion_breve);
					this.monumentos.add(monumento);
				}
				text.setLength(0);
			}
		}
		
	}
	public void startDocument() throws SAXException
	{
		super.startDocument();
		monumentos = new ArrayList<Monumento>();
		text = new StringBuilder();
	}
	public void startElement(String uri,String localName,String name,Attributes atts) throws SAXException
	{
		super.startElement(uri, localName, name, atts);
		if(localName.equals("Monumento"))
		{
			monumento = new Monumento();
		}
	}
}
