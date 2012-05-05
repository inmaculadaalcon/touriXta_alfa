package com.touriXta;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParse2
{
	InputStream is;
	public XmlParse2(InputStream is) throws MalformedURLException
	{
		this.is = is;
	}
	
	public List<Monumento> parse()
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try
		{
			SAXParser parser = factory.newSAXParser();
			leeXmlMonumentoHandler handler = new leeXmlMonumentoHandler();
			parser.parse(this.is, handler);
			return handler.getMonumentos();
		}catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
