package com.dw.offer;

import android.app.Application;
import android.widget.Toast;

public class App extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		show();
	}
	public void show(){
		Toast.makeText(this, "crack by mr.dawn",Toast.LENGTH_SHORT).show();
	}
}
