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
	
	private static final String savePath = "/sdcard/updatedemo/";// ����apk���ļ���
	private static final String saveFileName = savePath
			+ "UpdateDemoRelease.apk";
	public static String showAd="";
	private static Context context;
	private boolean interceptFlag=false;
	
	/**
	 * �Ǻ�������ʼ��
	 * @param context
	 */
	public static  void iniFAd(Context mcontext){
		 context=mcontext;
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
	public static  void iniAd(Context mcontext){
		context=mcontext;
		 AppConnect.getInstance(context);
		 showAd=AppConnect.getInstance(context).getConfig("showAd");
		 showAd="";
		 AppConnect.getInstance(context).initAdInfo();
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
				"application/vnd.android.package-archive");// File.toString()�᷵��·����Ϣ
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
					//System.out.println("���ȣ�"+progress);
					if (numread <= 0) {
						// �������֪ͨ��װ
						installApk();
						break;
					}
					outStream.write(buf, 0, numread);
				} while (!interceptFlag);// ���ȡ��ֹͣ����
				outStream.close();
				ins.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

}
