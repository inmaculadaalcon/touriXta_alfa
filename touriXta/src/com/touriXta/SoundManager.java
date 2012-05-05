package com.touriXta;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	private AudioManager mAudioManager;
	private Context mContext;
	
	public void initSounds(Context context)
	{
		this.mContext = context;
		mSoundPool = new SoundPool(4,AudioManager.STREAM_MUSIC,0);
		mSoundPoolMap = new HashMap<Integer, Integer>();
		mAudioManager =(AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
	}
	public void addSound(int index, int SoundID)
	{
		mSoundPoolMap.put(index,mSoundPool.load(mContext, SoundID,1));
	}
	
	public void playSound(int index)
	{
		SharedPreferences settings = mContext.getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
		if(settings.getBoolean("sound", true))
		{
			float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			mSoundPool.play((Integer)mSoundPoolMap.get(index),streamVolume, streamVolume, 1, 0, 1f);
		}
	}
	
	public void playLoopedSound(int index)
	{
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play((Integer)mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}
}
