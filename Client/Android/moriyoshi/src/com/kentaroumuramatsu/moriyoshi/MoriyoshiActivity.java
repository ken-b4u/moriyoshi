package com.kentaroumuramatsu.moriyoshi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MoriyoshiActivity extends Activity {
	
	SoundPool spPanchi = null;
	private int spPanchiId = 0;
	SoundPool spHow = null;
	private int spHowId = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// WebViewインスタンスの作成
		WebView webview = new WebView(this);
		
		// 使用するXMLを設定する
		setContentView(R.layout.main);
		
		// 指定されたIDにWebViewをセットする
		webview = (WebView) findViewById(R.id.webview);
		
		// WebViewClientをオーバーライドしたWebViewClientをセットする
		webview.setWebViewClient(new CustomWebViewClient());
		
		// URLを読み込む
		webview.loadUrl("http://blog.kentaroumuramatsu.com/files/moriyoshi/index.html");
		
		// javascriptを有効にする
		webview.getSettings().setJavaScriptEnabled(true);
		
		// javascriptから呼ばれるファンクションをセット
		webview.addJavascriptInterface(new JSSoundInterface(), "android_sound");
		
		// 効果音の初期化
		spPanchi = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		spPanchiId = spPanchi.load(this, R.raw.panchi, 1);
		spHow = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		spHowId = spHow.load(this, R.raw.how, 1);
	}
	
	// Javascriptから呼び出すファンクション
	class JSSoundInterface {
		
		// パンチ音再生
		public void playPanchi() {
			spPanchi.play(spPanchiId, 100, 100, 1, 0, 1);
		}
		
		// はう再生
		public void playHow() {
			spHow.play(spHowId, 100, 100, 1, 0, 1);
		}
	}
	
	// ページ読込中のダイヤログ表示など
	class CustomWebViewClient extends WebViewClient {
		ProgressDialog waitDialog = null;
		@Override
		//読み込み開始
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			waitDialog = new ProgressDialog(view.getContext());
			waitDialog.setMessage("読み込み中");
			waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			waitDialog.show();
		}
		@Override
		// 読み込み完了
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if(waitDialog != null){
				waitDialog.dismiss();
				waitDialog = null;
			}
		}
		@Override
		// 読み込み時にエラー
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