package com.kentaroumuramatsu.moriyoshi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MoriyoshiActivity extends Activity {
	
	private MediaPlayer mpPanchi1 = null;
	private MediaPlayer mpPanchi2 = null;
	private MediaPlayer mpPanchi3 = null;
	private MediaPlayer mpPanchi4 = null;
	private MediaPlayer mpPanchi5 = null;
	private MediaPlayer mpHow1 = null;
	private MediaPlayer mpHow2 = null;
	private MediaPlayer mpHow3 = null;
	private MediaPlayer mpHow4 = null;
	private MediaPlayer mpHow5 = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		setContentView(webview);
		setContentView(R.layout.main);
		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new CustomWebViewClient());
		
		webview.loadUrl("http://blog.kentaroumuramatsu.com/files/moriyoshi/index.html");
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JSSoundInterface(), "android_sound");
		mpPanchi1 = MediaPlayer.create(this, R.raw.panchi);
		mpPanchi2 = MediaPlayer.create(this, R.raw.panchi);
		mpPanchi3 = MediaPlayer.create(this, R.raw.panchi);
		mpPanchi4 = MediaPlayer.create(this, R.raw.panchi);
		mpPanchi5 = MediaPlayer.create(this, R.raw.panchi);
		mpHow1 = MediaPlayer.create(this, R.raw.how);
		mpHow2 = MediaPlayer.create(this, R.raw.how);
		mpHow3 = MediaPlayer.create(this, R.raw.how);
		mpHow4 = MediaPlayer.create(this, R.raw.how);
		mpHow5 = MediaPlayer.create(this, R.raw.how);
	}
	
	class JSSoundInterface {
		public void playPanchi() {
			if(mpPanchi1.isPlaying() == true) {
				mpPanchi2.start();
			} else if(mpPanchi2.isPlaying() == true) {
				mpPanchi3.start();
			} else if(mpPanchi3.isPlaying() == true) {
				mpPanchi4.start();
			} else if(mpPanchi4.isPlaying() == true) {
				mpPanchi5.start();
			} else {
				mpPanchi1.start();
			}
		}
		
		public void playHow() {
			if(mpHow1.isPlaying() == true) {
				mpHow2.start();
			} else if(mpHow2.isPlaying() == true) {
				mpHow3.start();
			} else if(mpHow3.isPlaying() == true) {
				mpHow4.start();
			} else if(mpHow4.isPlaying() == true) {
				mpHow5.start();
			} else {
				mpHow1.start();
			}
		}
	}
	
	
	class CustomWebViewClient extends WebViewClient {
		ProgressDialog waitDialog = null;
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			waitDialog = new ProgressDialog(view.getContext());
			waitDialog.setMessage("読み込み中");
			waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			waitDialog.show();
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if(waitDialog != null){
				waitDialog.dismiss();
				waitDialog = null;
			}
		}
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
			dialog.setTitle("エラー");
			dialog.setMessage("読み込みに失敗しました。");
			dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			if(waitDialog != null){
				waitDialog.dismiss();
				waitDialog = null;
			}
			dialog.setCancelable(false).create().show();
		}
	}
}