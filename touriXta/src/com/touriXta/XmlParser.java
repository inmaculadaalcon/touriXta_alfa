package com.touriXta;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser
{
	InputStream is;
	public XmlParser(InputStream is) throws MalformedURLException
	{
		this.is = is;
	}
	
	public List<Estadio> parse()
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();
			leeXmlEstadioHandler handler = new leeXmlEstadioHandler();
			parser.parse(this.is, handler);
			return handler.getEstadios();
		}catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	
	
}
