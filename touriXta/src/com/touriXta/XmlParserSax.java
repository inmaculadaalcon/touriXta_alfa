//package com.touriXta;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//import javax.xml.parsers.SAXParser;
//import javax.xml.parsers.SAXParserFactory;
//
//import android.content.Context;
//import android.content.res.AssetManager;
//
//
//public class XmlParserSax 
//{
//	Context context;
//	private String  url;
//	
//	public XmlParserSax(String uri)
//	{
//		this.url = uri;
//	}
//	
//	public List<Dato> parse()
//	{
//		SAXParserFactory factory = SAXParserFactory.newInstance();
//		try{
//		SAXParser parser = factory.newSAXParser();
//		XmlHandler handler = new XmlHandler(context, null);
//		parser.parse(this.getInputStream(), handler);
//		return handler.getDatos();
//		}catch(Exception e)
//		{
//			throw new RuntimeException(e);
//		}
//	}
//	private  InputStream getInputStream()
//	{
//		AssetManager assetManager = context.getAssets();
//		InputStream is;
//		try
//		{
//			is = assetManager.open(url);
//			return	is ; 
//		}catch(IOException e)
//		{
//			throw new RuntimeException(e);	
//		}
//	}
//}
