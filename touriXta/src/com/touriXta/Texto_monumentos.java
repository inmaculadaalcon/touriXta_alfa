package com.touriXta;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Texto_monumentos implements TextToSpeech.OnInitListener {
	Context cnt;
	TextToSpeech tts;
	String s;
	String cadena;
	List<Estadio> list_estadios;
	List<Escuela> list_escuelas;
	List<Monumento> list_monumentos;
	fromXmlToString fxts;
		public Texto_monumentos(Context cnt)
		{
			this.cnt = cnt;
			tts = new TextToSpeech(cnt,(OnInitListener)this);
			Locale loc = new Locale("es","","");
	       	tts.setLanguage(loc);   
	       	fxts=new fromXmlToString(cnt);
	       	try {
				InputStream is_estadio= this.cnt.getResources().openRawResource(R.raw.estadios);
				
				XmlParser xmlParser = new XmlParser(is_estadio);
				list_estadios = xmlParser.parse();
				cadena = list_estadios.get(1).getDescripcion();
			
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    public void onInit(int arg0) 
	    {
	        tts.speak(" ", TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit1(int arg0) 
	    {	
	    	cadena = fxts.nom_etsii() + fxts.desc_breve_etsii();
	    	tts.speak(cadena, TextToSpeech.QUEUE_FLUSH, null);
//	    	tts.speak("Escuela Técnica Superior De Ingeniería Informática. Es el centro de la Universidad de Sevilla donde se estudian las carreras de Ingeniería Informática, Ingeniería Técnica Informática de Sistemas e Ingeniería Técnica Informática de Gestión. Si desea obtener más información de este edificio pulse el botón más "+"", TextToSpeech.QUEUE_FLUSH, null);
	    }  	
	    public void onInit2(int arg0)
	    {
	       	tts.speak("Catedral de Sevilla. Es la catedral gótica cristiana más grande del mundo. La UNESCO la declaró, en 1987, Patrimonio de la Humanidad y, el 25 de julio de 2010, Bien de Valor Universal Excepcional. Si desea obtener más información de este edificio pulse el botón Más ", TextToSpeech.QUEUE_FLUSH, null);
    	}
	    public void onInit3(int arg0)
	    {
	    	tts.speak("Estadio Benito Villamarín", TextToSpeech.QUEUE_FLUSH, null);
    	}
	    public void onInit4(int arg0)
	    {
	    	tts.speak("Estadio Ramón Sánchez Pizjuán", TextToSpeech.QUEUE_FLUSH, null);
	    }
	    //Más de la escuela...
	    public void onInit5(int arg0)
	    {
	    	String descripcion_etsii = fxts.descripcion_escuelas().get(0).trim();
	    	tts.speak(descripcion_etsii, TextToSpeech.QUEUE_FLUSH, null);
	    	//tts.speak("La informática en la Universidad de Sevilla por fin había conseguido a mediados de los noventa su estatus de ingeniería, pero aún no contaba con un centro propio. El 3 de julio de 2001, el Consejo de Gobierno de la Junta de Andalucía aprobó la creación de la Escuela Técnica Superior de Ingeniería Informática, con sede en el edificio de la antigua Escuela Técnica Superior de Ingenieros Industriales. Para aquel entonces, esta escuela ya había terminado su mudanza a las nuevas instalaciones en la Isla de la Cartuja y había dejado libre el Edificio Blanco.Se trataba de un edificio antiguo. Por este motivo, desde el primer día fue necesario acometer múltiples reformas para rehabilitarlo y convertirlo en una gran sede para los estudios de Ingeniería Informática. Los estudios superiores de Informática en la Universidad de Sevilla inician su andadura en el curso académico 1985/1986, con la adscripción de la titulación de Diplomado en Informática, Especialidades Gestión y Sistemas Físicos a la antigua Escuela Universitaria de Ingeniería Técnica Industrial. durante el curso 1990/91, se creó la Facultad de Informática y Estadística, para la que se construyó un nuevo edificio en el campus de Reina Mercedes al que pronto se empezó a hacer referencia como el Edificio Rojo", TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit6(int arg0)
	    {
	    	tts.speak("Escuela Politécnica", TextToSpeech.QUEUE_FLUSH, null);
	    }
	    
	    public void onInit7(int arg0)
	    {
	    	tts.speak("Según la tradición, la construcción se inició en 1401, aunque no existe constancia documental del comienzo de los trabajos hasta 1433. La edificación se realizó en el solar que quedó tras la demolición de la antigua Mezquita Aljama de Sevilla. Uno de sus primeros maestros de obras fue Maese Carlín (Charles Galter), procedente de Normandía (Francia), que había trabajado previamente en otras grandes catedrales góticas europeas y llegó a España según se cree huyendo de la Guerra de los Cien Años. El 10 de octubre de 1506 se procedió a la colocación de la piedra postrera en la parte más alta del cimborio, con lo que simbólicamente la catedral quedó finalizada, aunque en realidad siguieron efectuándose trabajos de forma ininterrumpida a lo largo de los siglos, tanto para la decoración interior, como para añadir nuevas dependencias o consolidar y restaurar los desperfectos ocasionados por el paso del tiempo, o circunstancias extraordinarias, entre las que cabe destacar el terremoto de Lisboa de 1755 que produjo únicamente daños menores a pesar de su intensidad. En estas obras intervinieron los arquitectos Diego de Riaño, Martín de Gainza y Asensio de Maeda. También en esta etapa Hernán Ruiz edificó el último cuerpo de la Giralda. La catedral y sus dependencias quedaron terminadas en 1593." + "El Cabildo Metropolitano mantiene la liturgia diaria y la celebración de las festividades del Corpus, la Inmaculada y la Virgen de los Reyes. En este templo se encuentra el cuerpo del famoso navegante Cristóbal Colón y el del Rey Fernando III de Castilla (1199-1252), canonizado en 1671 como San Fernando, siendo papa Clemente X. La última obra de importancia realizada tuvo lugar en el año 2008 y consistió en la sustitución de 576 sillares que conformaban uno de los grandiosos pilares que sustentan el templo, por nuevos bloques de piedra de características similares pero con mucha mayor resistencia. Este difícil trabajo fue posible gracias al empleo de novedosos sistemas tecnológicos que demostraron que el edificio sufría diariamente unas oscilaciones de 2 cm como consecuencia de la dilatación de sus materiales." , TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit8(int arg0)
	    {
	    	String nombre_esi = fxts.nombresEscuelas().get(2).trim();
	    	tts.speak(nombre_esi,TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit9(int arg0)
	    {
	    	String nom_san_esteban = fxts.nom_sanEsteban();
	    	tts.speak(nom_san_esteban, TextToSpeech.QUEUE_FLUSH, null);
	    }
	    
	    public void onInit10(int arg0)
	    {
	    	String nom_sanmarcos = fxts.nom_sanMarcos();
	    	tts.speak(nom_sanmarcos, TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void para()
	    {
	    	tts.stop();
	    }
	    public void onInit11(int arg0)
	    {
	    	String desc_breve_pol = fxts.nom_esc_pol() + fxts.desc_breve_pol();
	    	tts.speak(desc_breve_pol, TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit12(int arg0)
	    {
	    	String desc_pol = fxts.desc_pol();
	    	tts.speak(desc_pol, TextToSpeech.QUEUE_FLUSH, null);
	    }
	    public void onInit13(int arg0)
	    {
	    	tts.speak("Aquí puede escoger los medios de transporte que desea usar, La información del lugar o volver al punto de partida", TextToSpeech.QUEUE_FLUSH, null);
	    }
}
