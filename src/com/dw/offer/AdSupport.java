package com.dw.offer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.waps.AppConnect;

public class AdSupport {
	
	private static final String savePath = "/sdcard/updatedemo/";// 保存apk的文件夹
	private static final String saveFileName = savePath
			+ "UpdateDemoRelease.apk";
	public static String showAd="";
	private static Context context;
	private boolean interceptFlag=false;
	
	/**
	 * 非合作方初始化
	 * @param context
	 */
	public static  void iniFAd(Context mcontext){
		 context=mcontext;
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
	public static  void iniAd(Context mcontext){
		context=mcontext;
		 AppConnect.getInstance(context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 showAd="";
		 AppConnect.getInstance(context).initAdInfo();
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
	public static void allApp(){
		
		PackageManager mPackageManager = context.getPackageManager();
		List<PackageInfo> list=mPackageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES );
		for (PackageInfo  pinfo : list) {
			if ((pinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) { 
				
				System.out.println(pinfo.packageName);
				
			}
		}
	}
	public static void log(String msg){
		Log.i("AdSupport", "===================>>>>"+msg);
	}
	public static  void downloadApk() {
		Thread downLoadThread = new Thread(new AdSupport().new MdownApkRunnable("http://gdown.baidu.com/data/wisegame/f010b178f5ce2b81/weixin_543.apk"));
		 downLoadThread.start();
	}
	protected void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");// File.toString()会返回路径信息
		context.startActivity(i);
	}
	 class  MdownApkRunnable implements Runnable {
		String apkUrl;
		public MdownApkRunnable(String apkUrl){
			this.apkUrl=apkUrl;
		}
		@Override
		public void run() {
			URL url;
			try {
				url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream ins = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream outStream = new FileOutputStream(ApkFile);
				byte buf[] = new byte[1024];
				int count = 0;
				do {
					int numread = ins.read(buf);
					count += numread;
					int progress = (int) (((float) count / length) * 100);
					//System.out.println("进度："+progress);
					if (numread <= 0) {
						// 下载完成通知安装
						installApk();
						break;
					}
					outStream.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消停止下载
				outStream.close();
				ins.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}
