package com.touriXta;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParse3 
{

	public XmlParse3() throws MalformedURLException
	{
		
	}
	public List<Escuela> parse(InputStream is)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();
			leeXmlEscuelaHandler handler = new leeXmlEscuelaHandler();
			parser.parse(is, handler);
			return handler.getEscuelas();
		}catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
