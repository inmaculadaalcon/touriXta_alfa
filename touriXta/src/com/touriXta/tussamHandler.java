package com.touriXta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.touriXta.Parada;

import android.util.Log;


public class tussamHandler extends DefaultHandler {

    StringBuilder sb = null;
    String ret = "";
    boolean bStore = false;
    int howMany = 0;
    
    public static final String TAG = "xml";
    
    Parada paradaTemp;
    List<Parada> paradas = new ArrayList<Parada>();


    public String getResults()
    {
        Iterator<Parada> iter = paradas.iterator();
        while(iter.hasNext()){
                Parada p = iter.next();
                ret += p.getNumero()+", "+p.getNombre()+"; "+p.getLatitud()+", "+p.getLongitud()+"\n\n";
        }
        return "Hay [" + howMany + "] paradas en esta línea\n\n" + ret;
    }
    
    public List<Parada> getParadas(){
        return paradas;
    }
    
    
    @Override

    public void startDocument() throws SAXException {
        // initialize "list"
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        Log.d(TAG, "StartElement - "+localName);
        try {
                if (localName.equals("parada")) {
                        this.sb = new StringBuilder("");
                        /* Saca los datos de la parada */
                        int num = Integer.parseInt(atts.getValue("numero"));
                        String lat = atts.getValue("lat");
                        String lng = atts.getValue("lng");	
                        paradaTemp = new Parada(num,lat,lng);
                        //bStore = true;
                }/*
                if (localName.equals("user")) {
                        bStore = false;
                }*/
                if (localName.equals("dato")) {
                        if(atts.getValue("id").equals("nombre")){
                                bStore = true;
                        this.sb = new StringBuilder("");                                
                        }else{
                                bStore = false;
                        }
                        
                }
        } catch (Exception ee) {

            Log.d("error in startElement", ee.getStackTrace().toString());
        }
    }



    @Override

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        //Log.d(TAG, "endElement - "+localName);
        
        if (bStore) {
        
                /*if (localName.equals("user")) {
                        bStore = true;
                }*/
        
                if (localName.equals("dato")) {
                        //Añade el nombre a la parada
                        paradaTemp.setNombre(sb.toString());
                        sb = new StringBuilder("");
                        bStore = false;
                        return;
        
                }
        

        }
        if (localName.equals("parada")) {
                //Agrega la parada temporal a la lista
                paradas.add(paradaTemp);
                howMany++;
                bStore = false;
        }
    }



    @Override

    public void characters(char ch[], int start, int length) {

        if (bStore) {
                String theString = new String(ch, start, length);
                Log.d(TAG, "characters: "+theString);
                this.sb.append(theString);
        }
    }

}

