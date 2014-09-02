package laurens.geo.scanner;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.*;
import android.content.*;

//import android.widget.Button;
//import android.widget.Toast;
//import android.view.View;
//import android.app.Dialog;

public class SplashScreen extends Activity {

	WebView wvSplashScreen;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splashscreen);
		wvSplashScreen = (WebView)findViewById(R.id.wvSplashScreen);
		wvSplashScreen.getSettings().setJavaScriptEnabled(true);
		wvSplashScreen.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wvSplashScreen.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url){
				Thread timer = new Thread() {
					public void run() {
						try {
							sleep(3000);
							finish();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							Intent i = new Intent(SplashScreen.this,MenuActivity.class);
							startActivity(i);
						}
					}
				};
				timer.start();
				}
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
            	wvSplashScreen.loadUrl("file:///android_asset/apps/public_html/error.html");	
            }
        });
		wvSplashScreen.loadUrl("file:///android_asset/apps/public_html/splashscreen.html");		
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
