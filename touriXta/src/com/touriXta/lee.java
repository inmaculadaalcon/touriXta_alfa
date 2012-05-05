package com.touriXta;
import java.util.Locale;

import android.os.Bundle;
import android.speech.tts.*;
import android.app.Activity;

public class lee extends Activity implements TextToSpeech.OnInitListener{
	TextToSpeech tts;
	public void OnCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tts=new TextToSpeech(this,this);
	}
	
	public void onInit(int status)
	{
		Locale loc =new Locale("es","","");
		tts.setLanguage(loc);
		tts.speak("Hola Inma",TextToSpeech.QUEUE_FLUSH, null);
	}
}
