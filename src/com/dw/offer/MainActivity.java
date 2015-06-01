package com.dw.offer;

import android.app.Activity;
import android.os.Bundle;

import com.dw.offer.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AdSupport.iniAd(this);
		AdSupport.bannerAd(this, this);
		AdSupport.miniAd( this);
		//AdSupport.stickAd(this);
		AdSupport.show(this, "ฑฌมห");
		AdSupport.log("big big bang ! ");
		
	}
	@Override
	public void onBackPressed() {
		 AdSupport.exitAd(this);
	}

}
