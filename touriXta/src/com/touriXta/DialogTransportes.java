package com.touriXta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DialogTransportes extends Activity implements OnItemClickListener
{
	String[] val = {"Autobuses","Sevici","Taxi"};
	Dialog listDialog;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		showDialog();
	}
	private void showDialog()
	{
		listDialog = new Dialog(this);
		listDialog.setTitle("Lista");
		LayoutInflater li = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.custom,null,false);
		listDialog.setContentView(v);
		listDialog.setCancelable(true);
	}
	
	public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete item"+arg2).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) 
			{
				System.out.println("OK Clicked");
				
			}
		});
		builder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				listDialog.cancel();
			}
		});
		AlertDialog alert =builder.create();
		alert.setTitle("Information");
		alert.show();
		
	}
}
