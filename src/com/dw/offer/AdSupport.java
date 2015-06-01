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
	 * �Ǻ�������ʼ��
	 * @param context
	 */
	public static  void iniFAd(Context context){
		AppConnect.getInstance("883ae996c95468a8722b876262481ed5","waps",context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 if("true".equals(showAd)){
			 //��ʼ���������
				AppConnect.getInstance(context).initPopAd(context);
				//��ʼ���Զ�����
				AppConnect.getInstance(context).initAdInfo();
			 
		 }
		
		
	}
	
	/**
	 * ��������ʼ��
	 * @param context
	 */
	public static  void iniAd(Context context){
		 AppConnect.getInstance(context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 show(context, showAd);
		 if("true".equals(showAd)){
			 //��ʼ���������
				AppConnect.getInstance(context).initPopAd(context);
				//��ʼ���Զ�����
				AppConnect.getInstance(context).initAdInfo();
			 
		 }
		
		
	}
	
	/**
	 * �ͷ���Դ
	 * @param context
	 */
	public static void closeAd(Context context){
		AppConnect.getInstance(context).close();
	}
	/**
	 * ������
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
			//���������汳����ɫ
			//AppConnect.getInstance(context).setAdBackColor(Color.argb(50, 120, 240, 120));
			//���������������ɫ
			AppConnect.getInstance(context).setAdForeColor(Color.GRAY);
			AppConnect.getInstance(context).showMiniAd(context, adlayout, 10); //Ĭ��10 ���л�һ�ι��
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//���ö��˻�Ͷ�
			activity.addContentView(adlayout, layoutParams);
			}
		
	}
	/**
	 * �������
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
		
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//���ö��˻�Ͷ�
		activity.addContentView(adlayout, layoutParams);
		AppConnect.getInstance(context).showBannerAd(context, adlayout);
		}
	}
	/**
	 * �������
	 * @param context
	 */
	public static  void stickAd(Context context){
		if("true".equals(showAd)){
		  AppConnect.getInstance(context).showPopAd(context);
		}
		
	}
	/**
	 * �������
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
