package com.dw.offer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.waps.AppConnect;

public class AdSupport {
	
	
	public static String showAd="";
	/**
	 * 非合作方初始化
	 * @param context
	 */
	public static  void iniFAd(Context context){
		AppConnect.getInstance("883ae996c95468a8722b876262481ed5","waps",context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 if("true".equals(showAd)){
			 //初始化插屏广告
				AppConnect.getInstance(context).initPopAd(context);
				//初始化自定义广告
				AppConnect.getInstance(context).initAdInfo();
			 
		 }
		
		
	}
	
	/**
	 * 合作方初始化
	 * @param context
	 */
	public static  void iniAd(Context context){
		 AppConnect.getInstance(context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 show(context, showAd);
		 if("true".equals(showAd)){
			 //初始化插屏广告
				AppConnect.getInstance(context).initPopAd(context);
				//初始化自定义广告
				AppConnect.getInstance(context).initAdInfo();
			 
		 }
		
		
	}
	
	/**
	 * 释放资源
	 * @param context
	 */
	public static void closeAd(Context context){
		AppConnect.getInstance(context).close();
	}
	/**
	 * 迷你广告
	 * @param context
	 */
	public static void miniAd(Context context){
		if("true".equals(showAd)){
			Activity activity = (Activity)context;
			LinearLayout adlayout = new LinearLayout(context);
			adlayout.setGravity(Gravity.CENTER_HORIZONTAL);
			RelativeLayout.LayoutParams layoutParams = new
			RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT);
			AppConnect.getInstance(context).showBannerAd(context, adlayout);
			//设置迷你广告背景颜色
			//AppConnect.getInstance(context).setAdBackColor(Color.argb(50, 120, 240, 120));
			//设置迷你广告广告诧颜色
			AppConnect.getInstance(context).setAdForeColor(Color.GRAY);
			AppConnect.getInstance(context).showMiniAd(context, adlayout, 10); //默认10 秒切换一次广告
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//设置顶端或低端
			activity.addContentView(adlayout, layoutParams);
			}
		
	}
	/**
	 * 互动广告
	 * @param activity
	 * @param context
	 */
	public static void bannerAd(Activity activity,Context context){
		if("true".equals(showAd)){
		LinearLayout adlayout = new LinearLayout(context);
		adlayout.setGravity(Gravity.CENTER_HORIZONTAL);
		RelativeLayout.LayoutParams layoutParams = new
		RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT);
		
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//设置顶端或低端
		activity.addContentView(adlayout, layoutParams);
		AppConnect.getInstance(context).showBannerAd(context, adlayout);
		}
	}
	/**
	 * 插屏广告
	 * @param context
	 */
	public static  void stickAd(Context context){
		if("true".equals(showAd)){
		  AppConnect.getInstance(context).showPopAd(context);
		}
		
	}
	/**
	 * 退屏广告
	 * @param context
	 */
	public static void exitAd(Context context){
		if("true".equals(showAd)){
			QuitPopAd.getInstance().show(context);
		}else{
			((Activity)context).finish();
		}
	}
	public static  void show(Context context,String msg){
		Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
	}
	public static void log(String msg){
		Log.i("AdSupport", "===================>>>>"+msg);
	}

}
