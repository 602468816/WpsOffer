package com.dw.offer;

import java.util.List;

import cn.waps.AdInfo;
import cn.waps.AppConnect;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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
	public  void adList(View v){
		
		
		List<AdInfo> list= AppConnect.getInstance(this).getAdInfoList();
		AppConnect.getInstance(this).downloadAd(this, "da04f5e44e0436599cab4c7bf613259b");
		if(list!=null){
		AdInfo ad=list.get(0);
		
		System.out.println(ad.toString());
		}
	}
	public void download(View v){
		AdSupport.downloadApk();
		
	}
	public void allApp(View v){
		AdSupport.allApp();
		
	}
}
