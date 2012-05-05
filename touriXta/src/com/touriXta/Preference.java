package com.touriXta;
import com.google.android.maps.MapView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.widget.Toast;
import android.media.AudioManager;

public class Preference extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	private CheckBoxPreference mBusCheckbox;
	private CheckBoxPreference mTaxiCheckbox;
	private CheckBoxPreference mSeviciCheckbox;
	private ListPreference listPreference1;
	private SharedPreferences sharedPreferences; 
	private String key;
	private Context context;
	boolean bus,taxi,sevici;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.custom2);
		MapView mMapa = (MapView)findViewById(com.touriXta.R.id.mapview);
	    mBusCheckbox = (CheckBoxPreference)getPreferenceScreen().findPreference("checkbox_preference1");
	  //  mTaxiCheckbox = (CheckBoxPreference)getPreferenceScreen().findPreference("checkbox_preference2");
		mSeviciCheckbox = (CheckBoxPreference)getPreferenceScreen().findPreference("checkbox_preference3");
		listPreference1 = (ListPreference)getPreferenceScreen().findPreference("list_preference");
		
		SharedPreferences settings = getSharedPreferences("Preferencias", MODE_PRIVATE);
		
		bus= settings.getBoolean("Bus", true);
		
		sevici = settings.getBoolean("Sevici", false);
//		if(settings.contains("valor1"))
//		{
//			mMapa.setSatellite(true);
//		}
		if(bus)
		{
			mBusCheckbox.setEnabled(true);
		}else
		{
			mBusCheckbox.setEnabled(false);
		}
			
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
		if(key.equals("checkbox_preference1"))
		{
			mBusCheckbox.setSummary(sharedPreferences.getBoolean(key, false) ? 	 "Setting deshabilitado" : "Setting habilitado");
		}else if (key.equals("checkbox_preference2"))
		{
			mTaxiCheckbox.setSummary(sharedPreferences.getBoolean(key,false)?"Setting deshabilitado":"Setting habilitado");
		}	
	}
	
	 
	
	
}
